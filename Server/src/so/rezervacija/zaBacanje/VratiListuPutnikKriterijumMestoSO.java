/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.putnik;

import domain.GenericEntity;
import domain.Mesto;
import domain.Putnik;
import java.util.ArrayList;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author mihajlo
 */
public class VratiListuPutnikKriterijumMestoSO extends AbstractSO {

    private List<Putnik> listaPutnika;

    @Override
    protected void precondition(Object param) throws Exception {
        if (!(param instanceof Mesto) || ((Mesto) param).getIdMesto() == null) {
            throw new Exception("Неисправан критеријум претраге");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Mesto mesto = (Mesto) param;
        List<GenericEntity> found = repository.getAll(new Putnik(), "idMesto = ?",
                new Object[]{mesto.getIdMesto()});
        listaPutnika = new ArrayList<>();
        for (GenericEntity e : found) {
            listaPutnika.add((Putnik) e);
        }
    }

    public List<Putnik> getListaPutnika() {
        return listaPutnika;
    }
}
