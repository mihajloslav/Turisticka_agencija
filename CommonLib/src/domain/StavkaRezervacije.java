/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author mihajlo
 */
public class StavkaRezervacije implements GenericEntity {

    private Rezervacija rezervacija;
    private Long rb;
    private Integer brojOsoba;
    private Date datumPolaska;
    private Date datumDolaska;
    private Double popust;
    private Double cena;
    private Aranzman aranzman;

    public StavkaRezervacije() {
    }

    public StavkaRezervacije(Rezervacija rezervacija, Long rb, Integer brojOsoba, Date datumPolaska,
            Date datumDolaska, Double popust, Double cena, Aranzman aranzman) {
        this.rezervacija = rezervacija;
        this.rb = rb;
        this.brojOsoba = brojOsoba;
        this.datumPolaska = datumPolaska;
        this.datumDolaska = datumDolaska;
        this.popust = popust;
        this.cena = cena;
        this.aranzman = aranzman;
    }

    public Rezervacija getRezervacija() {
        return rezervacija;
    }

    public void setRezervacija(Rezervacija rezervacija) {
        this.rezervacija = rezervacija;
    }

    public Long getRb() {
        return rb;
    }

    public void setRb(Long rb) {
        this.rb = rb;
    }

    public Integer getBrojOsoba() {
        return brojOsoba;
    }

    public void setBrojOsoba(Integer brojOsoba) {
        this.brojOsoba = brojOsoba;
    }

    public Date getDatumPolaska() {
        return datumPolaska;
    }

    public void setDatumPolaska(Date datumPolaska) {
        this.datumPolaska = datumPolaska;
    }

    public Date getDatumDolaska() {
        return datumDolaska;
    }

    public void setDatumDolaska(Date datumDolaska) {
        this.datumDolaska = datumDolaska;
    }

    public Double getPopust() {
        return popust;
    }

    public void setPopust(Double popust) {
        this.popust = popust;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public Aranzman getAranzman() {
        return aranzman;
    }

    public void setAranzman(Aranzman aranzman) {
        this.aranzman = aranzman;
    }

    @Override
    public String toString() {
        return "StavkaRezervacije{idRezervacija=" + (rezervacija == null ? null : rezervacija.getIdRezervacija())
                + ", rb=" + rb
                + ", brojOsoba=" + brojOsoba + ", datumPolaska=" + datumPolaska
                + ", datumDolaska=" + datumDolaska + ", popust=" + popust
                + ", cena=" + cena
                + ", idAranzman=" + (aranzman == null ? null : aranzman.getIdAranzman()) + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(rezervacija == null ? null : rezervacija.getIdRezervacija(), rb);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StavkaRezervacije)) {
            return false;
        }
        StavkaRezervacije other = (StavkaRezervacije) obj;
        Long ovoId = rezervacija == null ? null : rezervacija.getIdRezervacija();
        Long drugoId = other.rezervacija == null ? null : other.rezervacija.getIdRezervacija();
        return Objects.equals(ovoId, drugoId) && Objects.equals(this.rb, other.rb);
    }

    @Override
    public String getTableName() {
        return "StavkaRezervacije";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "idRezervacija, rb, brojOsoba, datumPolaska, datumDolaska, popust, cena, idAranzman";
    }

    @Override
    public Object[] getInsertValues() {
        return new Object[]{
            rezervacija == null ? null : rezervacija.getIdRezervacija(),
            rb, brojOsoba,
            datumPolaska == null ? null : new java.sql.Date(datumPolaska.getTime()),
            datumDolaska == null ? null : new java.sql.Date(datumDolaska.getTime()),
            popust, cena,
            aranzman == null ? null : aranzman.getIdAranzman()
        };
    }

    @Override
    public void setId(Long id) {
    }

    @Override
    public boolean hasGeneratedKey() {
        return false;
    }

    @Override
    public String getPrimaryKeyClause() {
        return "idRezervacija = ? AND rb = ?";
    }

    @Override
    public Object[] getPrimaryKeyParams() {
        return new Object[]{rezervacija == null ? null : rezervacija.getIdRezervacija(), rb};
    }

    @Override
    public String getUpdateSetClause() {
        return "brojOsoba = ?, datumPolaska = ?, datumDolaska = ?, popust = ?, cena = ?, idAranzman = ?";
    }

    @Override
    public Object[] getUpdateSetParams() {
        return new Object[]{
            brojOsoba,
            datumPolaska == null ? null : new java.sql.Date(datumPolaska.getTime()),
            datumDolaska == null ? null : new java.sql.Date(datumDolaska.getTime()),
            popust, cena,
            aranzman == null ? null : aranzman.getIdAranzman()
        };
    }

    @Override
    public GenericEntity fromResultSet(ResultSet rs) throws SQLException {
        StavkaRezervacije s = new StavkaRezervacije();
        Rezervacija r = new Rezervacija();
        r.setIdRezervacija(rs.getLong("idRezervacija"));
        s.setRezervacija(r);
        s.setRb(rs.getLong("rb"));
        s.setBrojOsoba(rs.getInt("brojOsoba"));
        s.setDatumPolaska(rs.getDate("datumPolaska"));
        s.setDatumDolaska(rs.getDate("datumDolaska"));
        s.setPopust(rs.getDouble("popust"));
        s.setCena(rs.getDouble("cena"));
        Aranzman a = new Aranzman();
        a.setIdAranzman(rs.getLong("idAranzman"));
        s.setAranzman(a);
        return s;
    }
}
