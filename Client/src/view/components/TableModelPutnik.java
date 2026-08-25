/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.components;

import domain.Putnik;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author mihajlo
 */
public class TableModelPutnik extends AbstractTableModel {

    private List<Putnik> putnici;
    private String[] columnNames = new String[]{"Име", "Презиме", "Имејл", "Телефон", "ЈМБГ", "Број пасоша"};
    private Class[] columnClass = new Class[]{String.class, String.class, String.class, String.class, String.class, String.class};

    public TableModelPutnik(List<Putnik> putnici) {
        this.putnici = putnici;
    }

    @Override
    public int getRowCount() {
        if (putnici == null) {
            return 0;
        } else {
            return putnici.size();
        }
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        if (column >= columnNames.length) {
            return "н/д";
        } else {
            return columnNames[column];
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex >= columnClass.length) {
            return Object.class;
        } else {
            return columnClass[columnIndex];
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Putnik putnik = putnici.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return putnik.getIme();
            case 1:
                return putnik.getPrezime();
            case 2:
                return putnik.getEmail();
            case 3:
                return putnik.getTelefon();
            case 4:
                return putnik.getJmbg();
            case 5:
                return putnik.getBrojPasosa();
            default:
                return "н/д";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    public Putnik getPutnikAt(int row) {
        return putnici.get(row);
    }

    public List<Putnik> getPutnici() {
        return putnici;
    }
}
