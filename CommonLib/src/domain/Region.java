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
public class Region implements GenericEntity {

    private Long idRegion;
    private String naziv;
    private String oznaka;
    private String kontinent;
    private String opis;

    public Region() {
    }

    public Region(Long idRegion, String naziv, String oznaka, String kontinent, String opis) {
        this.idRegion = idRegion;
        this.naziv = naziv;
        this.oznaka = oznaka;
        this.kontinent = kontinent;
        this.opis = opis;
    }

    public Long getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(Long idRegion) {
        this.idRegion = idRegion;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOznaka() {
        return oznaka;
    }

    public void setOznaka(String oznaka) {
        this.oznaka = oznaka;
    }

    public String getKontinent() {
        return kontinent;
    }

    public void setKontinent(String kontinent) {
        this.kontinent = kontinent;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    @Override
    public String toString() {
        return "Region{idRegion=" + idRegion + ", naziv=" + naziv + ", oznaka=" + oznaka
                + ", kontinent=" + kontinent + ", opis=" + opis + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idRegion);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Region)) {
            return false;
        }
        return Objects.equals(this.idRegion, ((Region) obj).idRegion);
    }

    @Override
    public String getTableName() {
        return "Region";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "naziv, oznaka, kontinent, opis";
    }

    @Override
    public Object[] getInsertValues() {
        return new Object[]{naziv, oznaka, kontinent, opis};
    }

    @Override
    public void setId(Long id) {
        this.idRegion = id;
    }

    @Override
    public String getPrimaryKeyColumnName() {
        return "idRegion";
    }

    @Override
    public String getPrimaryKeyClause() {
        return "idRegion = ?";
    }

    @Override
    public Object[] getPrimaryKeyParams() {
        return new Object[]{idRegion};
    }

    @Override
    public String getUpdateSetClause() {
        return "naziv = ?, oznaka = ?, kontinent = ?, opis = ?";
    }

    @Override
    public Object[] getUpdateSetParams() {
        return new Object[]{naziv, oznaka, kontinent, opis};
    }

    @Override
    public GenericEntity fromResultSet(ResultSet rs) throws SQLException {
        Region r = new Region();
        r.setIdRegion(rs.getLong("idRegion"));
        r.setNaziv(rs.getString("naziv"));
        r.setOznaka(rs.getString("oznaka"));
        r.setKontinent(rs.getString("kontinent"));
        r.setOpis(rs.getString("opis"));
        return r;
    }
}
