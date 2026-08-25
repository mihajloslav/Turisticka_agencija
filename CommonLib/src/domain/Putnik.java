/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author mihajlo
 */
public class Putnik implements GenericEntity {

    private Long idPutnik;
    private String ime;
    private String prezime;
    private String email;
    private String telefon;
    private String jmbg;
    private String brojPasosa;
    private Mesto mesto;
    private LocalDate datumRodjenja;

    public Putnik() {
    }

    public Putnik(Long idPutnik, String ime, String prezime, String email, String telefon,
            String jmbg, String brojPasosa, Mesto mesto, LocalDate datumRodjenja) {
        this.idPutnik = idPutnik;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.telefon = telefon;
        this.jmbg = jmbg;
        this.brojPasosa = brojPasosa;
        this.mesto = mesto;
        this.datumRodjenja = datumRodjenja;
    }

    public Long getIdPutnik() {
        return idPutnik;
    }

    public void setIdPutnik(Long idPutnik) {
        this.idPutnik = idPutnik;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getBrojPasosa() {
        return brojPasosa;
    }

    public void setBrojPasosa(String brojPasosa) {
        this.brojPasosa = brojPasosa;
    }

    public Mesto getMesto() {
        return mesto;
    }

    public void setMesto(Mesto mesto) {
        this.mesto = mesto;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    @Override
    public String toString() {
        return "Putnik{idPutnik=" + idPutnik + ", ime=" + ime + ", prezime=" + prezime
                + ", email=" + email + ", telefon=" + telefon + ", jmbg=" + jmbg
                + ", brojPasosa=" + brojPasosa + ", datumRodjenja=" + datumRodjenja
                + ", idMesto=" + (mesto == null ? null : mesto.getIdMesto()) + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idPutnik);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Putnik)) {
            return false;
        }
        return Objects.equals(this.idPutnik, ((Putnik) obj).idPutnik);
    }

    @Override
    public String getTableName() {
        return "Putnik";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "ime, prezime, email, telefon, jmbg, brojPasosa, idMesto, datumRodjenja";
    }

    @Override
    public Object[] getInsertValues() {
        return new Object[]{
            ime, prezime, email, telefon, jmbg, brojPasosa,
            mesto == null ? null : mesto.getIdMesto(),
            datumRodjenja == null ? null : java.sql.Date.valueOf(datumRodjenja)
        };
    }

    @Override
    public void setId(Long id) {
        this.idPutnik = id;
    }

    @Override
    public String getPrimaryKeyColumnName() {
        return "idPutnik";
    }

    @Override
    public String getPrimaryKeyClause() {
        return "idPutnik = ?";
    }

    @Override
    public Object[] getPrimaryKeyParams() {
        return new Object[]{idPutnik};
    }

    @Override
    public String getUpdateSetClause() {
        return "ime = ?, prezime = ?, email = ?, telefon = ?, jmbg = ?, brojPasosa = ?, idMesto = ?, datumRodjenja = ?";
    }

    @Override
    public Object[] getUpdateSetParams() {
        return getInsertValues();
    }

    @Override
    public GenericEntity fromResultSet(ResultSet rs) throws SQLException {
        Putnik p = new Putnik();
        p.setIdPutnik(rs.getLong("idPutnik"));
        p.setIme(rs.getString("ime"));
        p.setPrezime(rs.getString("prezime"));
        p.setEmail(rs.getString("email"));
        p.setTelefon(rs.getString("telefon"));
        p.setJmbg(rs.getString("jmbg"));
        p.setBrojPasosa(rs.getString("brojPasosa"));
        Mesto m = new Mesto();
        m.setIdMesto(rs.getLong("idMesto"));
        p.setMesto(m);
        java.sql.Date datumRodjenjaSql = rs.getDate("datumRodjenja");
        p.setDatumRodjenja(datumRodjenjaSql == null ? null : datumRodjenjaSql.toLocalDate());
        return p;
    }
}
