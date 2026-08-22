/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.region;

import domain.Region;
import so.AbstractSO;

/**
 *
 * @author mihajlo
 */
public class UbaciRegionSO extends AbstractSO {

    private Region region;

    @Override
    protected void precondition(Object param) throws Exception {
        if (!(param instanceof Region)) {
            throw new Exception("Систем не може да запамти регион");
        }
        Region r = (Region) param;
        if (r.getNaziv() == null || r.getNaziv().isEmpty()
                || r.getKontinent() == null || r.getKontinent().isEmpty()
                || (r.getOznaka() != null && r.getOznaka().length() > 6)) {
            throw new Exception("Систем не може да запамти регион");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        region = (Region) param;
        try {
            repository.add(region);
        } catch (Exception ex) {
            throw new Exception("Систем не може да запамти регион");
        }
    }

    public Region getRegion() {
        return region;
    }
}
