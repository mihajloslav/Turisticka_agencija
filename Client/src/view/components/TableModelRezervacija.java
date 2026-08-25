/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.components;

import domain.Rezervacija;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author mihajlo
 */
public class TableModelRezervacija extends AbstractTableModel {

    private List<Rezervacija> rezervacije;
    private String[] columnNames = new String[]{"ИД", "Датум креирања", "Статус плаћања", "Укупан износ"};
    private Class[] columnClass = new Class[]{Long.class, String.class, String.class, Double.class};
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.uuuu");

    public TableModelRezervacija(List<Rezervacija> rezervacije) {
        this.rezervacije = rezervacije;
    }

    @Override
    public int getRowCount() {
        if (rezervacije == null) {
            return 0;
        } else {
            return rezervacije.size();
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
        Rezervacija rezervacija = rezervacije.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rezervacija.getIdRezervacija();
            case 1:
                return rezervacija.getDatumKreiranja() == null ? "" : rezervacija.getDatumKreiranja().format(dtf);
            case 2:
                return rezervacija.getStatusPlacanja();
            case 3:
                return rezervacija.getUkupanIznos();
            default:
                return "н/д";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    public Rezervacija getRezervacijaAt(int row) {
        return rezervacije.get(row);
    }

    public List<Rezervacija> getRezervacije() {
        return rezervacije;
    }
}
