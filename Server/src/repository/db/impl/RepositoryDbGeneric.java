/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.db.impl;

import domain.GenericEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import repository.db.DbConnectionFactory;
import repository.db.DbRepository;

/**
 *
 * @author mihajlo
 */
public class RepositoryDbGeneric implements DbRepository<GenericEntity, Long> {

    @Override
    public List<GenericEntity> getAll() throws Exception {
        throw new UnsupportedOperationException("Није подржано.");
    }

    @Override
    public List<GenericEntity> getAll(GenericEntity entity, String whereClause, Object[] params) throws Exception {
        List<GenericEntity> result = new ArrayList<>();
        Connection connection = DbConnectionFactory.getInstance().getConnection();
        String query = "SELECT * FROM " + entity.getTableName()
                + (whereClause == null || whereClause.isEmpty() ? "" : " WHERE " + whereClause);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    statement.setObject(i + 1, params[i]);
                }
            }
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    result.add(entity.fromResultSet(rs));
                }
            }
        }
        return result;
    }

    @Override
    public void add(GenericEntity entity) throws Exception {
        Connection connection = DbConnectionFactory.getInstance().getConnection();
        Object[] values = entity.getInsertValues();
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            placeholders.append(i == 0 ? "?" : ", ?");
        }
        String query = "INSERT INTO " + entity.getTableName()
                + " (" + entity.getColumnNamesForInsert() + ") VALUES (" + placeholders + ")";
        PreparedStatement statement = entity.hasGeneratedKey()
                ? connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
                : connection.prepareStatement(query);
        try {
            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }
            statement.executeUpdate();
            if (entity.hasGeneratedKey()) {
                try (ResultSet keys = statement.getGeneratedKeys()) {
                    if (keys.next()) {
                        entity.setId(keys.getLong(1));
                    }
                }
            }
        } finally {
            statement.close();
        }
    }

    @Override
    public void edit(GenericEntity entity) throws Exception {
        Connection connection = DbConnectionFactory.getInstance().getConnection();

        // Read the row's current state from the database so we can compute
        // exactly which columns were actually changed, and update only those.
        GenericEntity original = fetchByPrimaryKey(entity, connection);
        Map<String, Object> changedValues = entity.getChangedValues(original);
        if (changedValues.isEmpty()) {
            // Nothing changed - no UPDATE needs to be executed.
            return;
        }

        StringBuilder setClause = new StringBuilder();
        for (String column : changedValues.keySet()) {
            if (setClause.length() > 0) {
                setClause.append(", ");
            }
            setClause.append(column).append(" = ?");
        }

        String query = "UPDATE " + entity.getTableName()
                + " SET " + setClause
                + " WHERE " + entity.getPrimaryKeyClause();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            int index = 1;
            for (Object value : changedValues.values()) {
                statement.setObject(index++, value);
            }
            for (Object value : entity.getPrimaryKeyParams()) {
                statement.setObject(index++, value);
            }
            statement.executeUpdate();
        }
    }

    /**
     * Reads the row currently in the database matching entity's primary key
     * (which may be a single column or a composite key), reconstructed via
     * entity.fromResultSet(...). Returns null if no such row exists.
     */
    private GenericEntity fetchByPrimaryKey(GenericEntity entity, Connection connection) throws Exception {
        String query = "SELECT * FROM " + entity.getTableName() + " WHERE " + entity.getPrimaryKeyClause();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            int index = 1;
            for (Object value : entity.getPrimaryKeyParams()) {
                statement.setObject(index++, value);
            }
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? entity.fromResultSet(rs) : null;
            }
        }
    }

    @Override
    public void delete(GenericEntity entity) throws Exception {
        Connection connection = DbConnectionFactory.getInstance().getConnection();
        String query = "DELETE FROM " + entity.getTableName()
                + " WHERE " + entity.getPrimaryKeyClause();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            int index = 1;
            for (Object value : entity.getPrimaryKeyParams()) {
                statement.setObject(index++, value);
            }
            statement.executeUpdate();
        }
    }

    @Override
    public GenericEntity getById(Long k) throws Exception {
        throw new UnsupportedOperationException("Није подржано.");
    }

    @Override
    public GenericEntity getById(GenericEntity entity, Long k) throws Exception {
        Connection connection = DbConnectionFactory.getInstance().getConnection();
        String query = "SELECT * FROM " + entity.getTableName()
                + " WHERE " + entity.getPrimaryKeyClause();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, k);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return entity.fromResultSet(rs);
                }
                return null;
            }
        }
    }
}
