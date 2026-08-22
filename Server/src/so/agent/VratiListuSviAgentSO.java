/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.agent;

import domain.Agent;
import domain.GenericEntity;
import java.util.ArrayList;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author mihajlo
 */
public class VratiListuSviAgentSO extends AbstractSO {

    private List<Agent> agenti;

    @Override
    protected void precondition(Object param) throws Exception {
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        List<GenericEntity> found = repository.getAll(new Agent(), null, null);
        agenti = new ArrayList<>();
        for (GenericEntity e : found) {
            agenti.add((Agent) e);
        }
    }

    public List<Agent> getAgenti() {
        return agenti;
    }
}
