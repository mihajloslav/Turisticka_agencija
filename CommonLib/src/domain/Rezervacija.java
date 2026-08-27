/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author mihajlo
 */
public class Rezervacija implements GenericEntity {

    private Long idRezervacija;
    private LocalDate datumKreiranja;
    private Double ukupanIznos;
    private String statusPlacanja;
    private String napomena;
    private Agent agent;
    private Putnik putnik;
    private List<StavkaRezervacije> stavke = new ArrayList<>();


    /*Za pretraživanje*/
    private Aranzman aranzman;
    private Double ukupanIznosOd;
    private Double ukupanIznosDo;
    private LocalDate datumKreiranjaOd;
    private LocalDate datumKreiranjaDo;

    public Rezervacija() {
    }

    public Rezervacija(Long idRezervacija, LocalDate datumKreiranja, Double ukupanIznos,
            String statusPlacanja, String napomena, Agent agent, Putnik putnik) {
        this.idRezervacija = idRezervacija;
        this.datumKreiranja = datumKreiranja;
        this.ukupanIznos = ukupanIznos;
        this.statusPlacanja = statusPlacanja;
        this.napomena = napomena;
        this.agent = agent;
        this.putnik = putnik;
    }

    public Long getIdRezervacija() {
        return idRezervacija;
    }

    public void setIdRezervacija(Long idRezervacija) {
        this.idRezervacija = idRezervacija;
    }

    public LocalDate getDatumKreiranja() {
        return datumKreiranja;
    }

    public void setDatumKreiranja(LocalDate datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
    }

    public Double getUkupanIznos() {
        return ukupanIznos;
    }

    public void setUkupanIznos(Double ukupanIznos) {
        this.ukupanIznos = ukupanIznos;
    }

    public String getStatusPlacanja() {
        return statusPlacanja;
    }

    public void setStatusPlacanja(String statusPlacanja) {
        this.statusPlacanja = statusPlacanja;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Putnik getPutnik() {
        return putnik;
    }

    public void setPutnik(Putnik putnik) {
        this.putnik = putnik;
    }

    public List<StavkaRezervacije> getStavke() {
        return stavke;
    }

    public void setStavke(List<StavkaRezervacije> stavke) {
        this.stavke = stavke;
    }

    public Aranzman getAranzman() {
        return aranzman;
    }

    public void setAranzman(Aranzman aranzman) {
        this.aranzman = aranzman;
    }

    public Double getUkupanIznosOd() {
        return ukupanIznosOd;
    }

    public void setUkupanIznosOd(Double ukupanIznosOd) {
        this.ukupanIznosOd = ukupanIznosOd;
    }

    public Double getUkupanIznosDo() {
        return ukupanIznosDo;
    }

    public void setUkupanIznosDo(Double ukupanIznosDo) {
        this.ukupanIznosDo = ukupanIznosDo;
    }

    public LocalDate getDatumKreiranjaOd() {
        return datumKreiranjaOd;
    }

    public void setDatumKreiranjaOd(LocalDate datumKreiranjaOd) {
        this.datumKreiranjaOd = datumKreiranjaOd;
    }

    public LocalDate getDatumKreiranjaDo() {
        return datumKreiranjaDo;
    }

    public void setDatumKreiranjaDo(LocalDate datumKreiranjaDo) {
        this.datumKreiranjaDo = datumKreiranjaDo;
    }

    @Override
    public String toString() {
        return "Rezervacija{idRezervacija=" + idRezervacija + ", datumKreiranja=" + datumKreiranja
                + ", ukupanIznos=" + ukupanIznos + ", statusPlacanja=" + statusPlacanja
                + ", idAgent=" + (agent == null ? null : agent.getIdAgent())
                + ", idPutnik=" + (putnik == null ? null : putnik.getIdPutnik()) + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idRezervacija);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Rezervacija)) {
            return false;
        }
        return Objects.equals(this.idRezervacija, ((Rezervacija) obj).idRezervacija);
    }

    @Override
    public String getTableName() {
        return "Rezervacija";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "datumKreiranja, ukupanIznos, statusPlacanja, napomena, idAgent, idPutnik";
    }

    @Override
    public Object[] getInsertValues() {
        return new Object[]{
            datumKreiranja == null ? null : java.sql.Date.valueOf(datumKreiranja),
            ukupanIznos, statusPlacanja, napomena,
            agent == null ? null : agent.getIdAgent(),
            putnik == null ? null : putnik.getIdPutnik()
        };
    }

    @Override
    public void setId(Long id) {
        this.idRezervacija = id;
    }

    @Override
    public String getPrimaryKeyColumnName() {
        return "idRezervacija";
    }

    @Override
    public String getPrimaryKeyClause() {
        return "idRezervacija = ?";
    }

    @Override
    public Object[] getPrimaryKeyParams() {
        return new Object[]{idRezervacija};
    }

    @Override
    public String getUpdateSetClause() {
        return "datumKreiranja = ?, ukupanIznos = ?, statusPlacanja = ?, napomena = ?, idAgent = ?, idPutnik = ?";
    }

    @Override
    public Object[] getUpdateSetParams() {
        return getInsertValues();
    }

    @Override
    public GenericEntity fromResultSet(ResultSet rs) throws SQLException {
        Rezervacija r = new Rezervacija();
        r.setIdRezervacija(rs.getLong("idRezervacija"));
        java.sql.Date datumKreiranjaSql = rs.getDate("datumKreiranja");
        r.setDatumKreiranja(datumKreiranjaSql == null ? null : datumKreiranjaSql.toLocalDate());
        r.setUkupanIznos(rs.getDouble("ukupanIznos"));
        r.setStatusPlacanja(rs.getString("statusPlacanja"));
        r.setNapomena(rs.getString("napomena"));
        Agent a = new Agent();
        a.setIdAgent(rs.getLong("idAgent"));
        r.setAgent(a);
        Putnik p = new Putnik();
        p.setIdPutnik(rs.getLong("idPutnik"));
        r.setPutnik(p);
        return r;
    }
}
