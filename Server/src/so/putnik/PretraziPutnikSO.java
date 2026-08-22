/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.putnik;

import domain.Putnik;
import so.AbstractSO;

/**
 *
 * @author mihajlo
 */
public class PretraziPutnikSO extends AbstractSO {

    private Putnik putnik;

    @Override
    protected void precondition(Object param) throws Exception {
        if (!(param instanceof Putnik) || ((Putnik) param).getIdPutnik() == null) {
            throw new Exception("Систем не може да нађе путника");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Putnik criteria = (Putnik) param;
        Putnik found = (Putnik) repository.getById(new Putnik(), criteria.getIdPutnik());
        if (found == null) {
            throw new Exception("Систем не може да нађе путника");
        }
        putnik = found;
    }

    public Putnik getPutnik() {
        return putnik;
    }
}
