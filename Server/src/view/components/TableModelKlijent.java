/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.components;

import domain.Agent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import threads.HandleClientThread;

/**
 *
 * @author mihajlo
 */
public class TableModelKlijent extends AbstractTableModel {

    private List<HandleClientThread> klijenti = new ArrayList<>();
    private String[] columnNames = new String[]{"ИП адреса", "Порт", "Пријављени агент"};
    private Class[] columnClass = new Class[]{String.class, String.class, String.class};

    @Override
    public int getRowCount() {
        return klijenti.size();
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
        HandleClientThread klijent = klijenti.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return klijent.getSocket().getInetAddress().getHostAddress();
            case 1:
                return String.valueOf(klijent.getSocket().getPort());
            case 2:
                Agent agent = klijent.getPrijavljeniAgent();
                return agent == null ? "-" : agent.getIme() + " " + agent.getPrezime();
            default:
                return "н/д";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    public void dodajKlijenta(HandleClientThread klijent) {
        klijenti.add(klijent);
        fireTableRowsInserted(klijenti.size() - 1, klijenti.size() - 1);
    }

    public void azurirajKlijenta(HandleClientThread klijent) {
        int red = klijenti.indexOf(klijent);
        if (red >= 0) {
            fireTableRowsUpdated(red, red);
        }
    }

    public void ukloniKlijenta(HandleClientThread klijent) {
        int red = klijenti.indexOf(klijent);
        if (red >= 0) {
            klijenti.remove(red);
            fireTableRowsDeleted(red, red);
        }
    }
}
