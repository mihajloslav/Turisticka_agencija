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
        String query = "UPDATE " + entity.getTableName()
                + " SET " + entity.getUpdateSetClause()
                + " WHERE " + entity.getPrimaryKeyClause();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            int index = 1;
            for (Object value : entity.getUpdateSetParams()) {
                statement.setObject(index++, value);
            }
            for (Object value : entity.getPrimaryKeyParams()) {
                statement.setObject(index++, value);
            }
            statement.executeUpdate();
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
                + " WHERE " + entity.getPrimaryKeyColumnName() + " = ?";
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
