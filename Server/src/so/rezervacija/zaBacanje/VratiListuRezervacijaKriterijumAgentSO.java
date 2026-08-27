/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.rezervacija;

import domain.Agent;
import domain.GenericEntity;
import domain.Rezervacija;
import java.util.ArrayList;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author mihajlo
 */
public class VratiListuRezervacijaKriterijumAgentSO extends AbstractSO {

    private List<Rezervacija> listaRezervacija;

    @Override
    protected void precondition(Object param) throws Exception {
        if (!(param instanceof Agent) || ((Agent) param).getIdAgent() == null) {
            throw new Exception("Неисправан критеријум претраге");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Agent agent = (Agent) param;
        List<GenericEntity> found = repository.getAll(new Rezervacija(), "idAgent = ?",
                new Object[]{agent.getIdAgent()});
        listaRezervacija = new ArrayList<>();
        for (GenericEntity e : found) {
            listaRezervacija.add((Rezervacija) e);
        }
    }

    public List<Rezervacija> getListaRezervacija() {
        return listaRezervacija;
    }
}
