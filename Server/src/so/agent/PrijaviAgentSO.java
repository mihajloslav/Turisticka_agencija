/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.agent;

import domain.Agent;
import domain.GenericEntity;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author mihajlo
 */
public class PrijaviAgentSO extends AbstractSO {

    private Agent agent;

    @Override
    protected void precondition(Object param) throws Exception {
        if (!(param instanceof String[])) {
            throw new Exception("Корисничко име и шифра нису исправни");
        }
        String[] credentials = (String[]) param;
        if (credentials.length != 2
                || credentials[0] == null || credentials[0].isEmpty()
                || credentials[1] == null || credentials[1].isEmpty()) {
            throw new Exception("Корисничко име и шифра нису исправни");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        String[] credentials = (String[]) param;
        List<GenericEntity> found = repository.getAll(new Agent(),
                "korisnickoIme = ? AND sifra = ?",
                new Object[]{credentials[0], credentials[1]});
        if (found.isEmpty()) {
            throw new Exception("Корисничко име и шифра нису исправни");
        }
        agent = (Agent) found.get(0);
    }

    public Agent getAgent() {
        return agent;
    }
}
