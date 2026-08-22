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
public class VratiListuSviPutnikSO extends AbstractSO {

    private List<Putnik> putnici;

    @Override
    protected void precondition(Object param) throws Exception {
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        List<GenericEntity> found = repository.getAll(new Putnik(), null, null);
        putnici = new ArrayList<>();
        for (GenericEntity e : found) {
            putnici.add((Putnik) e);
        }
    }

    public List<Putnik> getPutnici() {
        return putnici;
    }
}
