/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author mihajlo
 */
public class Aranzman implements GenericEntity {

    private Long idAranzman;
    private String naziv;
    private String opis;
    private String tipAranzmana;
    private Double cenaPoOsobi;

    public Aranzman() {
    }

    public Aranzman(Long idAranzman, String naziv, String opis, String tipAranzmana, Double cenaPoOsobi) {
        this.idAranzman = idAranzman;
        this.naziv = naziv;
        this.opis = opis;
        this.tipAranzmana = tipAranzmana;
        this.cenaPoOsobi = cenaPoOsobi;
    }

    public Long getIdAranzman() {
        return idAranzman;
    }

    public void setIdAranzman(Long idAranzman) {
        this.idAranzman = idAranzman;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getTipAranzmana() {
        return tipAranzmana;
    }

    public void setTipAranzmana(String tipAranzmana) {
        this.tipAranzmana = tipAranzmana;
    }

    public Double getCenaPoOsobi() {
        return cenaPoOsobi;
    }

    public void setCenaPoOsobi(Double cenaPoOsobi) {
        this.cenaPoOsobi = cenaPoOsobi;
    }

    @Override
    public String toString() {
        return "Aranzman{idAranzman=" + idAranzman + ", naziv=" + naziv
                + ", tipAranzmana=" + tipAranzmana + ", cenaPoOsobi=" + cenaPoOsobi + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idAranzman);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Aranzman)) {
            return false;
        }
        return Objects.equals(this.idAranzman, ((Aranzman) obj).idAranzman);
    }

    @Override
    public String getTableName() {
        return "Aranzman";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "naziv, opis, tipAranzmana, cenaPoOsobi";
    }

    @Override
    public Object[] getInsertValues() {
        return new Object[]{naziv, opis, tipAranzmana, cenaPoOsobi};
    }

    @Override
    public void setId(Long id) {
        this.idAranzman = id;
    }

    @Override
    public String getPrimaryKeyColumnName() {
        return "idAranzman";
    }

    @Override
    public String getPrimaryKeyClause() {
        return "idAranzman = ?";
    }

    @Override
    public Object[] getPrimaryKeyParams() {
        return new Object[]{idAranzman};
    }

    @Override
    public Map<String, Object> getChangedValues(GenericEntity original) {
        Map<String, Object> changes = new LinkedHashMap<>();
        Aranzman o = (original instanceof Aranzman) ? (Aranzman) original : null;
        if (o == null) {
            changes.put("naziv", naziv);
            changes.put("opis", opis);
            changes.put("tipAranzmana", tipAranzmana);
            changes.put("cenaPoOsobi", cenaPoOsobi);
            return changes;
        }
        if (!Objects.equals(naziv, o.naziv)) {
            changes.put("naziv", naziv);
        }
        if (!Objects.equals(opis, o.opis)) {
            changes.put("opis", opis);
        }
        if (!Objects.equals(tipAranzmana, o.tipAranzmana)) {
            changes.put("tipAranzmana", tipAranzmana);
        }
        if (!Objects.equals(cenaPoOsobi, o.cenaPoOsobi)) {
            changes.put("cenaPoOsobi", cenaPoOsobi);
        }
        return changes;
    }

    @Override
    public GenericEntity fromResultSet(ResultSet rs) throws SQLException {
        Aranzman a = new Aranzman();
        a.setIdAranzman(rs.getLong("idAranzman"));
        a.setNaziv(rs.getString("naziv"));
        a.setOpis(rs.getString("opis"));
        a.setTipAranzmana(rs.getString("tipAranzmana"));
        a.setCenaPoOsobi(rs.getDouble("cenaPoOsobi"));
        return a;
    }
}
