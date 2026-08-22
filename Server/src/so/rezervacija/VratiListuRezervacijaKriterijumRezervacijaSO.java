/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.rezervacija;

import domain.GenericEntity;
import domain.Rezervacija;
import java.util.ArrayList;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author mihajlo
 */
public class VratiListuRezervacijaKriterijumRezervacijaSO extends AbstractSO {

    private List<Rezervacija> listaRezervacija;

    @Override
    protected void precondition(Object param) throws Exception {
        if (param != null && !(param instanceof Rezervacija)) {
            throw new Exception("Неисправан критеријум претраге");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Rezervacija criteria = (Rezervacija) param;
        StringBuilder where = new StringBuilder();
        List<Object> params = new ArrayList<>();
        if (criteria != null) {
            if (criteria.getStatusPlacanja() != null && !criteria.getStatusPlacanja().isEmpty()) {
                where.append(where.length() == 0 ? "" : " AND ").append("statusPlacanja = ?");
                params.add(criteria.getStatusPlacanja());
            }
            if (criteria.getDatumKreiranja() != null) {
                where.append(where.length() == 0 ? "" : " AND ").append("datumKreiranja = ?");
                params.add(new java.sql.Date(criteria.getDatumKreiranja().getTime()));
            }
        }
        List<GenericEntity> found = repository.getAll(new Rezervacija(), where.toString(), params.toArray());
        listaRezervacija = new ArrayList<>();
        for (GenericEntity e : found) {
            listaRezervacija.add((Rezervacija) e);
        }
    }

    public List<Rezervacija> getListaRezervacija() {
        return listaRezervacija;
    }
}
