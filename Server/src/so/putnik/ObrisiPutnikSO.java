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
public class ObrisiPutnikSO extends AbstractSO {

    @Override
    protected void precondition(Object param) throws Exception {
        if (!(param instanceof Putnik) || ((Putnik) param).getIdPutnik() == null) {
            throw new Exception("Систем не може да обрише путника");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Putnik putnik = (Putnik) param;
        try {
            repository.delete(putnik);
        } catch (Exception ex) {
            throw new Exception("Систем не може да обрише путника");
        }
    }
}
