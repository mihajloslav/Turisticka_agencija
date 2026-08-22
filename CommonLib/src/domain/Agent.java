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
public class Agent implements GenericEntity {

    private Long idAgent;
    private String ime;
    private String prezime;
    private String email;
    private String telefon;
    private String korisnickoIme;
    private String sifra;

    public Agent() {
    }

    public Agent(Long idAgent, String ime, String prezime, String email, String telefon,
            String korisnickoIme, String sifra) {
        this.idAgent = idAgent;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.telefon = telefon;
        this.korisnickoIme = korisnickoIme;
        this.sifra = sifra;
    }

    public Long getIdAgent() {
        return idAgent;
    }

    public void setIdAgent(Long idAgent) {
        this.idAgent = idAgent;
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

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    @Override
    public String toString() {
        return "Agent{idAgent=" + idAgent + ", ime=" + ime + ", prezime=" + prezime
                + ", email=" + email + ", telefon=" + telefon
                + ", korisnickoIme=" + korisnickoIme + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idAgent);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Agent)) {
            return false;
        }
        return Objects.equals(this.idAgent, ((Agent) obj).idAgent);
    }

    @Override
    public String getTableName() {
        return "Agent";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "ime, prezime, email, telefon, korisnickoIme, sifra";
    }

    @Override
    public Object[] getInsertValues() {
        return new Object[]{ime, prezime, email, telefon, korisnickoIme, sifra};
    }

    @Override
    public void setId(Long id) {
        this.idAgent = id;
    }

    @Override
    public String getPrimaryKeyColumnName() {
        return "idAgent";
    }

    @Override
    public String getPrimaryKeyClause() {
        return "idAgent = ?";
    }

    @Override
    public Object[] getPrimaryKeyParams() {
        return new Object[]{idAgent};
    }

    @Override
    public String getUpdateSetClause() {
        return "ime = ?, prezime = ?, email = ?, telefon = ?, korisnickoIme = ?, sifra = ?";
    }

    @Override
    public Object[] getUpdateSetParams() {
        return new Object[]{ime, prezime, email, telefon, korisnickoIme, sifra};
    }

    @Override
    public GenericEntity fromResultSet(ResultSet rs) throws SQLException {
        Agent a = new Agent();
        a.setIdAgent(rs.getLong("idAgent"));
        a.setIme(rs.getString("ime"));
        a.setPrezime(rs.getString("prezime"));
        a.setEmail(rs.getString("email"));
        a.setTelefon(rs.getString("telefon"));
        a.setKorisnickoIme(rs.getString("korisnickoIme"));
        a.setSifra(rs.getString("sifra"));
        return a;
    }
}
