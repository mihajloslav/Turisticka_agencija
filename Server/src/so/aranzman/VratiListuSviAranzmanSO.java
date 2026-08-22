/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.aranzman;

import domain.Aranzman;
import domain.GenericEntity;
import java.util.ArrayList;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author mihajlo
 */
public class VratiListuSviAranzmanSO extends AbstractSO {

    private List<Aranzman> aranzmani;

    @Override
    protected void precondition(Object param) throws Exception {
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        List<GenericEntity> found = repository.getAll(new Aranzman(), null, null);
        aranzmani = new ArrayList<>();
        for (GenericEntity e : found) {
            aranzmani.add((Aranzman) e);
        }
    }

    public List<Aranzman> getAranzmani() {
        return aranzmani;
    }
}
