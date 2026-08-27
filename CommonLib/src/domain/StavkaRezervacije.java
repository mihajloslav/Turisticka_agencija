/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author mihajlo
 */
public class StavkaRezervacije implements GenericEntity {

    private Rezervacija rezervacija;
    private Integer rb;
    private Integer brojOsoba;
    private LocalDate datumPolaska;
    private LocalDate datumDolaska;
    private Double popust;
    private Double cena;
    private Aranzman aranzman;

    public StavkaRezervacije() {
    }

    public StavkaRezervacije(Rezervacija rezervacija, Integer rb, Integer brojOsoba, LocalDate datumPolaska,
            LocalDate datumDolaska, Double popust, Double cena, Aranzman aranzman) {
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

    public Integer getRb() {
        return rb;
    }

    public void setRb(Integer rb) {
        this.rb = rb;
    }

    public Integer getBrojOsoba() {
        return brojOsoba;
    }

    public void setBrojOsoba(Integer brojOsoba) {
        this.brojOsoba = brojOsoba;
    }

    public LocalDate getDatumPolaska() {
        return datumPolaska;
    }

    public void setDatumPolaska(LocalDate datumPolaska) {
        this.datumPolaska = datumPolaska;
    }

    public LocalDate getDatumDolaska() {
        return datumDolaska;
    }

    public void setDatumDolaska(LocalDate datumDolaska) {
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
            datumPolaska == null ? null : java.sql.Date.valueOf(datumPolaska),
            datumDolaska == null ? null : java.sql.Date.valueOf(datumDolaska),
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
    public Map<String, Object> getChangedValues(GenericEntity original) {
        Map<String, Object> changes = new LinkedHashMap<>();
        StavkaRezervacije o = (original instanceof StavkaRezervacije) ? (StavkaRezervacije) original : null;
        Long idAranzman = aranzman == null ? null : aranzman.getIdAranzman();
        java.sql.Date datumPolaskaSql = datumPolaska == null ? null : java.sql.Date.valueOf(datumPolaska);
        java.sql.Date datumDolaskaSql = datumDolaska == null ? null : java.sql.Date.valueOf(datumDolaska);
        if (o == null) {
            changes.put("brojOsoba", brojOsoba);
            changes.put("datumPolaska", datumPolaskaSql);
            changes.put("datumDolaska", datumDolaskaSql);
            changes.put("popust", popust);
            changes.put("cena", cena);
            changes.put("idAranzman", idAranzman);
            return changes;
        }
        Long oldIdAranzman = o.aranzman == null ? null : o.aranzman.getIdAranzman();
        if (!Objects.equals(brojOsoba, o.brojOsoba)) {
            changes.put("brojOsoba", brojOsoba);
        }
        if (!Objects.equals(datumPolaska, o.datumPolaska)) {
            changes.put("datumPolaska", datumPolaskaSql);
        }
        if (!Objects.equals(datumDolaska, o.datumDolaska)) {
            changes.put("datumDolaska", datumDolaskaSql);
        }
        if (!Objects.equals(popust, o.popust)) {
            changes.put("popust", popust);
        }
        if (!Objects.equals(cena, o.cena)) {
            changes.put("cena", cena);
        }
        if (!Objects.equals(idAranzman, oldIdAranzman)) {
            changes.put("idAranzman", idAranzman);
        }
        return changes;
    }

    @Override
    public GenericEntity fromResultSet(ResultSet rs) throws SQLException {
        StavkaRezervacije s = new StavkaRezervacije();
        Rezervacija r = new Rezervacija();
        r.setIdRezervacija(rs.getLong("idRezervacija"));
        s.setRezervacija(r);
        s.setRb(rs.getInt("rb"));
        s.setBrojOsoba(rs.getInt("brojOsoba"));
        java.sql.Date datumPolaskaSql = rs.getDate("datumPolaska");
        s.setDatumPolaska(datumPolaskaSql == null ? null : datumPolaskaSql.toLocalDate());
        java.sql.Date datumDolaskaSql = rs.getDate("datumDolaska");
        s.setDatumDolaska(datumDolaskaSql == null ? null : datumDolaskaSql.toLocalDate());
        s.setPopust(rs.getDouble("popust"));
        s.setCena(rs.getDouble("cena"));
        Aranzman a = new Aranzman();
        a.setIdAranzman(rs.getLong("idAranzman"));
        s.setAranzman(a);
        return s;
    }
}
