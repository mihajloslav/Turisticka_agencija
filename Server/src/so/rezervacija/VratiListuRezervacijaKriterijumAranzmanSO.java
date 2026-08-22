/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.rezervacija;

import domain.Aranzman;
import domain.GenericEntity;
import domain.Rezervacija;
import domain.StavkaRezervacije;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import so.AbstractSO;

/**
 *
 * @author mihajlo
 */
public class VratiListuRezervacijaKriterijumAranzmanSO extends AbstractSO {

    private List<Rezervacija> listaRezervacija;

    @Override
    protected void precondition(Object param) throws Exception {
        if (!(param instanceof Aranzman) || ((Aranzman) param).getIdAranzman() == null) {
            throw new Exception("Неисправан критеријум претраге");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Aranzman aranzman = (Aranzman) param;
        List<GenericEntity> stavkeRedovi = repository.getAll(new StavkaRezervacije(), "idAranzman = ?",
                new Object[]{aranzman.getIdAranzman()});
        Set<Long> idRezervacije = new LinkedHashSet<>();
        for (GenericEntity e : stavkeRedovi) {
            idRezervacije.add(((StavkaRezervacije) e).getRezervacija().getIdRezervacija());
        }
        listaRezervacija = new ArrayList<>();
        for (Long id : idRezervacije) {
            Rezervacija r = (Rezervacija) repository.getById(new Rezervacija(), id);
            if (r != null) {
                listaRezervacija.add(r);
            }
        }
    }

    public List<Rezervacija> getListaRezervacija() {
        return listaRezervacija;
    }
}
