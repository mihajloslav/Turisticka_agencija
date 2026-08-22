/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.mesto;

import domain.GenericEntity;
import domain.Mesto;
import java.util.ArrayList;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author mihajlo
 */
public class VratiListuSviMestoSO extends AbstractSO {

    private List<Mesto> mesta;

    @Override
    protected void precondition(Object param) throws Exception {
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        List<GenericEntity> found = repository.getAll(new Mesto(), null, null);
        mesta = new ArrayList<>();
        for (GenericEntity e : found) {
            mesta.add((Mesto) e);
        }
    }

    public List<Mesto> getMesta() {
        return mesta;
    }
}
