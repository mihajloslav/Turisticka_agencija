/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 *
 * @author mihajlo
 */
public class Mesto implements GenericEntity {

    private Long idMesto;
    private String naziv;
    private String pttBroj;
    private String pozivniBroj;
    private String drzava;

    public Mesto() {
    }

    public Mesto(Long idMesto, String naziv, String pttBroj, String pozivniBroj, String drzava) {
        this.idMesto = idMesto;
        this.naziv = naziv;
        this.pttBroj = pttBroj;
        this.pozivniBroj = pozivniBroj;
        this.drzava = drzava;
    }

    public Long getIdMesto() {
        return idMesto;
    }

    public void setIdMesto(Long idMesto) {
        this.idMesto = idMesto;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getPttBroj() {
        return pttBroj;
    }

    public void setPttBroj(String pttBroj) {
        this.pttBroj = pttBroj;
    }

    public String getPozivniBroj() {
        return pozivniBroj;
    }

    public void setPozivniBroj(String pozivniBroj) {
        this.pozivniBroj = pozivniBroj;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    @Override
    public String toString() {
        return "Mesto{idMesto=" + idMesto + ", naziv=" + naziv + ", pttBroj=" + pttBroj
                + ", pozivniBroj=" + pozivniBroj + ", drzava=" + drzava + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idMesto);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Mesto)) {
            return false;
        }
        return Objects.equals(this.idMesto, ((Mesto) obj).idMesto);
    }

    @Override
    public String getTableName() {
        return "Mesto";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "naziv, pttBroj, pozivniBroj, drzava";
    }

    @Override
    public Object[] getInsertValues() {
        return new Object[]{naziv, pttBroj, pozivniBroj, drzava};
    }

    @Override
    public void setId(Long id) {
        this.idMesto = id;
    }

    @Override
    public String getPrimaryKeyColumnName() {
        return "idMesto";
    }

    @Override
    public String getPrimaryKeyClause() {
        return "idMesto = ?";
    }

    @Override
    public Object[] getPrimaryKeyParams() {
        return new Object[]{idMesto};
    }

    @Override
    public String getUpdateSetClause() {
        return "naziv = ?, pttBroj = ?, pozivniBroj = ?, drzava = ?";
    }

    @Override
    public Object[] getUpdateSetParams() {
        return new Object[]{naziv, pttBroj, pozivniBroj, drzava};
    }

    @Override
    public GenericEntity fromResultSet(ResultSet rs) throws SQLException {
        Mesto m = new Mesto();
        m.setIdMesto(rs.getLong("idMesto"));
        m.setNaziv(rs.getString("naziv"));
        m.setPttBroj(rs.getString("pttBroj"));
        m.setPozivniBroj(rs.getString("pozivniBroj"));
        m.setDrzava(rs.getString("drzava"));
        return m;
    }
}
