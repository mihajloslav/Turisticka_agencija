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
public class PromeniPutnikSO extends AbstractSO {

    private Putnik putnik;

    @Override
    protected void precondition(Object param) throws Exception {
        if (!(param instanceof Putnik)) {
            throw new Exception("Систем не може да запамти путника");
        }
        Putnik p = (Putnik) param;
        if (p.getIdPutnik() == null
                || p.getIme() == null || p.getIme().isEmpty()
                || p.getPrezime() == null || p.getPrezime().isEmpty()
                || p.getEmail() == null || !p.getEmail().contains("@")
                || p.getTelefon() == null || !p.getTelefon().matches("^\\+[0-9]{12}$")
                || p.getJmbg() == null || p.getJmbg().length() != 13
                || p.getBrojPasosa() == null || p.getBrojPasosa().isEmpty()
                || p.getMesto() == null || p.getMesto().getIdMesto() == null
                || p.getDatumRodjenja() == null) {
            throw new Exception("Систем не може да запамти путника");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        putnik = (Putnik) param;
        try {
            repository.edit(putnik);
        } catch (Exception ex) {
            throw new Exception("Систем не може да запамти путника");
        }
    }

    public Putnik getPutnik() {
        return putnik;
    }
}
