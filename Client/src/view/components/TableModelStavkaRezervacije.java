/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.components;

import domain.StavkaRezervacije;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author mihajlo
 */
public class TableModelStavkaRezervacije extends AbstractTableModel {

    private List<StavkaRezervacije> stavke;
    private String[] columnNames = new String[]{"Aranžman", "Broj osoba", "Datum polaska", "Datum dolaska", "Popust", "Cena"};
    private Class[] columnClass = new Class[]{String.class, Integer.class, String.class, String.class, Double.class, Double.class};
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");

    public TableModelStavkaRezervacije(List<StavkaRezervacije> stavke) {
        this.stavke = stavke != null ? stavke : new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return stavke.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        if (column >= columnNames.length) {
            return "n/a";
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
        StavkaRezervacije stavka = stavke.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return stavka.getAranzman() == null ? "n/a" : stavka.getAranzman().getNaziv();
            case 1:
                return stavka.getBrojOsoba();
            case 2:
                return stavka.getDatumPolaska() == null ? "" : sdf.format(stavka.getDatumPolaska());
            case 3:
                return stavka.getDatumDolaska() == null ? "" : sdf.format(stavka.getDatumDolaska());
            case 4:
                return stavka.getPopust();
            case 5:
                return stavka.getCena();
            default:
                return "n/a";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    public void dodajStavku(StavkaRezervacije stavka) {
        stavke.add(stavka);
        fireTableRowsInserted(stavke.size() - 1, stavke.size() - 1);
    }

    public void ukloniStavku(int row) {
        stavke.remove(row);
        fireTableDataChanged();
    }

    public List<StavkaRezervacije> getStavke() {
        return stavke;
    }
}
