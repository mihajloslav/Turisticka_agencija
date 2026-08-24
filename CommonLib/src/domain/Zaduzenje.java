/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author mihajlo
 */
public class Zaduzenje implements GenericEntity {

    private Agent agent;
    private Region region;
    private LocalDate datumOd;
    private LocalDate datumDo;
    private Integer mesecnaKvota;
    private String napomena;

    public Zaduzenje() {
    }

    public Zaduzenje(Agent agent, Region region, LocalDate datumOd, LocalDate datumDo,
            Integer mesecnaKvota, String napomena) {
        this.agent = agent;
        this.region = region;
        this.datumOd = datumOd;
        this.datumDo = datumDo;
        this.mesecnaKvota = mesecnaKvota;
        this.napomena = napomena;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public LocalDate getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(LocalDate datumOd) {
        this.datumOd = datumOd;
    }

    public LocalDate getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(LocalDate datumDo) {
        this.datumDo = datumDo;
    }

    public Integer getMesecnaKvota() {
        return mesecnaKvota;
    }

    public void setMesecnaKvota(Integer mesecnaKvota) {
        this.mesecnaKvota = mesecnaKvota;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    @Override
    public String toString() {
        return "Zaduzenje{idAgent=" + (agent == null ? null : agent.getIdAgent())
                + ", idRegion=" + (region == null ? null : region.getIdRegion())
                + ", datumOd=" + datumOd + ", datumDo=" + datumDo
                + ", mesecnaKvota=" + mesecnaKvota + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                agent == null ? null : agent.getIdAgent(),
                region == null ? null : region.getIdRegion(),
                datumOd);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Zaduzenje)) {
            return false;
        }
        Zaduzenje other = (Zaduzenje) obj;
        return Objects.equals(this.agent, other.agent)
                && Objects.equals(this.region, other.region)
                && Objects.equals(this.datumOd, other.datumOd);
    }

    @Override
    public String getTableName() {
        return "Zaduzenje";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "idAgent, idRegion, datumOd, datumDo, mesecnaKvota, napomena";
    }

    @Override
    public Object[] getInsertValues() {
        return new Object[]{
            agent == null ? null : agent.getIdAgent(),
            region == null ? null : region.getIdRegion(),
            datumOd == null ? null : java.sql.Date.valueOf(datumOd),
            datumDo == null ? null : java.sql.Date.valueOf(datumDo),
            mesecnaKvota, napomena
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
        return "idAgent = ? AND idRegion = ? AND datumOd = ?";
    }

    @Override
    public Object[] getPrimaryKeyParams() {
        return new Object[]{
            agent == null ? null : agent.getIdAgent(),
            region == null ? null : region.getIdRegion(),
            datumOd == null ? null : java.sql.Date.valueOf(datumOd)
        };
    }

    @Override
    public String getUpdateSetClause() {
        return "datumDo = ?, mesecnaKvota = ?, napomena = ?";
    }

    @Override
    public Object[] getUpdateSetParams() {
        return new Object[]{
            datumDo == null ? null : java.sql.Date.valueOf(datumDo),
            mesecnaKvota, napomena
        };
    }

    @Override
    public GenericEntity fromResultSet(ResultSet rs) throws SQLException {
        Zaduzenje z = new Zaduzenje();
        Agent a = new Agent();
        a.setIdAgent(rs.getLong("idAgent"));
        z.setAgent(a);
        Region r = new Region();
        r.setIdRegion(rs.getLong("idRegion"));
        z.setRegion(r);
        java.sql.Date datumOdSql = rs.getDate("datumOd");
        z.setDatumOd(datumOdSql == null ? null : datumOdSql.toLocalDate());
        java.sql.Date datumDoSql = rs.getDate("datumDo");
        z.setDatumDo(datumDoSql == null ? null : datumDoSql.toLocalDate());
        z.setMesecnaKvota(rs.getInt("mesecnaKvota"));
        z.setNapomena(rs.getString("napomena"));
        return z;
    }
}
