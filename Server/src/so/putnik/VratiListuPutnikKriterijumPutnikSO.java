/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.putnik;

import domain.GenericEntity;
import domain.Putnik;
import java.util.ArrayList;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author mihajlo
 */
public class VratiListuPutnikKriterijumPutnikSO extends AbstractSO {

    private List<Putnik> listaPutnika;

    @Override
    protected void precondition(Object param) throws Exception {
        if (param != null && !(param instanceof Putnik)) {
            throw new Exception("Неисправан критеријум претраге");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Putnik criteria = (Putnik) param;
        StringBuilder where = new StringBuilder();
        List<Object> params = new ArrayList<>();
        if (criteria != null) {
            if (criteria.getIme() != null && !criteria.getIme().isEmpty()) {
                where.append(where.length() == 0 ? "" : " AND ").append("ime = ?");
                params.add(criteria.getIme());
            }
            if (criteria.getPrezime() != null && !criteria.getPrezime().isEmpty()) {
                where.append(where.length() == 0 ? "" : " AND ").append("prezime = ?");
                params.add(criteria.getPrezime());
            }
            if (criteria.getEmail() != null && !criteria.getEmail().isEmpty()) {
                where.append(where.length() == 0 ? "" : " AND ").append("email = ?");
                params.add(criteria.getEmail());
            }
            if (criteria.getTelefon() != null && !criteria.getTelefon().isEmpty()) {
                where.append(where.length() == 0 ? "" : " AND ").append("telefon = ?");
                params.add(criteria.getTelefon());
            }
            if (criteria.getJmbg() != null && !criteria.getJmbg().isEmpty()) {
                where.append(where.length() == 0 ? "" : " AND ").append("jmbg = ?");
                params.add(criteria.getJmbg());
            }
            if (criteria.getBrojPasosa() != null && !criteria.getBrojPasosa().isEmpty()) {
                where.append(where.length() == 0 ? "" : " AND ").append("brojPasosa = ?");
                params.add(criteria.getBrojPasosa());
            }
            if (criteria.getMesto() != null && criteria.getMesto().getIdMesto() != null) {
                where.append(where.length() == 0 ? "" : " AND ").append("idMesto = ?");
                params.add(criteria.getMesto().getIdMesto());
            }
            if (criteria.getDatumRodjenja() != null) {
                where.append(where.length() == 0 ? "" : " AND ").append("datumRodjenja = ?");
                params.add(java.sql.Date.valueOf(criteria.getDatumRodjenja()));
            }
        }
        List<GenericEntity> found = repository.getAll(new Putnik(), where.toString(), params.toArray());
        listaPutnika = new ArrayList<>();
        for (GenericEntity e : found) {
            listaPutnika.add((Putnik) e);
        }
    }

    public List<Putnik> getListaPutnika() {
        return listaPutnika;
    }
}
