/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package repository;

import java.util.List;

/**
 *
 * @author mihajlo
 */
public interface Repository<T, K> {

    List<T> getAll() throws Exception;

    List<T> getAll(T prototype, String whereClause, Object[] params) throws Exception;

    void add(T t) throws Exception;

    void edit(T t) throws Exception;

    void delete(T t) throws Exception;

    T getById(K k) throws Exception;

    T getById(T prototype, K k) throws Exception;
}
