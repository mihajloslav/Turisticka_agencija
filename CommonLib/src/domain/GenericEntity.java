/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author mihajlo
 */
public interface GenericEntity extends Serializable {

    String getTableName();

    String getColumnNamesForInsert();

    Object[] getInsertValues();

    void setId(Long id);

    default boolean hasGeneratedKey() {
        return true;
    }

    default String getPrimaryKeyColumnName() {
        throw new UnsupportedOperationException(
                getClass().getSimpleName() + " has no single-column primary key");
    }

    String getPrimaryKeyClause();

    Object[] getPrimaryKeyParams();

    /**
     * Compares this (modified) entity against {@code original} — the row's
     * current state as read from the database — and returns only the
     * column/value pairs that actually differ, so that
     * {@link repository.db.impl.RepositoryDbGeneric#edit} can build an
     * UPDATE statement touching only the columns that were really changed.
     * The primary-key column(s) are never included.
     *
     * If {@code original} is {@code null} (e.g. the row could not be read
     * back), every non-key column is returned, matching the previous
     * always-update-everything behavior as a safe fallback.
     */
    Map<String, Object> getChangedValues(GenericEntity original);

    GenericEntity fromResultSet(ResultSet rs) throws SQLException;
}
