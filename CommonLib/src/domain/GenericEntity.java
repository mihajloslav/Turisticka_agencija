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

    String getPrimaryKeyClause(); 

    Object[] getPrimaryKeyParams();

    Map<String, Object> getChangedValues(GenericEntity original); 

    GenericEntity fromResultSet(ResultSet rs) throws SQLException;

    boolean hasGeneratedKey();
}
