/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.rezervacija;

import domain.Agent;
import domain.Aranzman;
import domain.GenericEntity;
import domain.Putnik;
import domain.Rezervacija;
import domain.StavkaRezervacije;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import so.AbstractSO;

/**
 *
 * @author mihajlo
 */
public class PretraziRezervacijaSO extends AbstractSO {

    private Rezervacija rezervacija;

    @Override
    protected void precondition(Object param) throws Exception {
        if (!(param instanceof Rezervacija) || ((Rezervacija) param).getIdRezervacija() == null) {
            throw new Exception("Систем не може да нађе резервацију");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Rezervacija criteria = (Rezervacija) param;
        Rezervacija found = (Rezervacija) repository.getById(new Rezervacija(), criteria.getIdRezervacija());
        if (found == null) {
            throw new Exception("Систем не може да нађе резервацију");
        }
        if (found.getAgent() != null && found.getAgent().getIdAgent() != null) {
            found.setAgent((Agent) repository.getById(new Agent(), found.getAgent().getIdAgent()));
        }
        if (found.getPutnik() != null && found.getPutnik().getIdPutnik() != null) {
            found.setPutnik((Putnik) repository.getById(new Putnik(), found.getPutnik().getIdPutnik()));
        }
        List<GenericEntity> stavkeRedovi = repository.getAll(new StavkaRezervacije(),
                "idRezervacija = ?", new Object[]{found.getIdRezervacija()});
        List<StavkaRezervacije> stavke = new ArrayList<>();  
        Map<Long, Aranzman> ucitaniAranzmani = new HashMap<>(); //Zbog zbog toga sto je  Aranzman plitak
        for (GenericEntity e : stavkeRedovi) {
            StavkaRezervacije s = (StavkaRezervacije) e;
            s.setRezervacija(found);
            Long idAranzman = s.getAranzman() == null ? null : s.getAranzman().getIdAranzman();
            if (idAranzman != null) {
                Aranzman aranzman = ucitaniAranzmani.get(idAranzman);
                if (aranzman == null) {
                    aranzman = (Aranzman) repository.getById(new Aranzman(), idAranzman);
                    ucitaniAranzmani.put(idAranzman, aranzman);
                }
                s.setAranzman(aranzman);
            }
            stavke.add(s);
        }
        found.setStavke(stavke);
        rezervacija = found;
    }

    public Rezervacija getRezervacija() {
        return rezervacija;
    }
}
