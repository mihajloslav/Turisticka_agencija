/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.rezervacija;

import domain.GenericEntity;
import domain.Rezervacija;
import domain.StavkaRezervacije;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author mihajlo
 */
public class PromeniRezervacijaSO extends AbstractSO {

    private Rezervacija rezervacija;

    @Override
    protected void precondition(Object param) throws Exception {
        if (!(param instanceof Rezervacija)) {
            throw new Exception("Систем не може да запамти резервацију");
        }
        Rezervacija r = (Rezervacija) param;
        if (r.getIdRezervacija() == null
                || r.getAgent() == null || r.getAgent().getIdAgent() == null
                || r.getPutnik() == null || r.getPutnik().getIdPutnik() == null
                || r.getStatusPlacanja() == null
                || (!"Плаћено".equals(r.getStatusPlacanja()) && !"На чекању".equals(r.getStatusPlacanja()))
                || r.getStavke() == null || r.getStavke().isEmpty()) {
            throw new Exception("Систем не може да запамти резервацију");
        }
        for (StavkaRezervacije s : r.getStavke()) {
            if (s.getBrojOsoba() == null || s.getBrojOsoba() <= 0
                    || s.getDatumPolaska() == null || s.getDatumDolaska() == null
                    || !s.getDatumDolaska().isAfter(s.getDatumPolaska())
                    || s.getAranzman() == null || s.getAranzman().getIdAranzman() == null
                    || s.getAranzman().getCenaPoOsobi() == null) {
                throw new Exception("Систем не може да запамти резервацију");
            }
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        rezervacija = (Rezervacija) param;
        double ukupanIznos = 0;
        for (StavkaRezervacije s : rezervacija.getStavke()) {
            double popust = s.getBrojOsoba() >= 3 ? 0.1 : 0.0;
            double cena = s.getBrojOsoba() * s.getAranzman().getCenaPoOsobi() * (1 - popust);
            s.setPopust(popust);
            s.setCena(cena);
            ukupanIznos += cena;
        }
        rezervacija.setUkupanIznos(ukupanIznos);
        try {
            repository.edit(rezervacija);
            List<GenericEntity> postojece = repository.getAll(new StavkaRezervacije(),
                    "idRezervacija = ?", new Object[]{rezervacija.getIdRezervacija()});
            for (GenericEntity e : postojece) {
                repository.delete(e);
            }
            int rb = 1;
            for (StavkaRezervacije s : rezervacija.getStavke()) {
                s.setRezervacija(rezervacija);
                s.setRb(rb++);
                repository.add(s);
            }
        } catch (Exception ex) {
            throw new Exception("Систем не може да запамти резервацију");
        }
    }

    public Rezervacija getRezervacija() {
        return rezervacija;
    }
}
