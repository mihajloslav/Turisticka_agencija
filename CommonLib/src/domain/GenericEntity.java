/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    String getUpdateSetClause();

    Object[] getUpdateSetParams();

    GenericEntity fromResultSet(ResultSet rs) throws SQLException;
}
