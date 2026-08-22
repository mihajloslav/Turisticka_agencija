/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.form;

import domain.Region;
import javax.swing.JOptionPane;
import validation.Validator;

/**
 *
 * @author mihajlo
 */
public class UbaciRegionController {

    private final UbaciRegionForm form;

    public UbaciRegionController(UbaciRegionForm form) {
        this.form = form;
        addActionListeners();
    }

    private void addActionListeners() {
        form.getBtnSacuvaj().addActionListener(evt -> sacuvaj());
    }

    private void sacuvaj() {
        try {
            String naziv = form.getTxtNaziv().getText().trim();
            String oznaka = form.getTxtOznaka().getText().trim();
            String kontinent = form.getTxtKontinent().getText().trim();
            String opis = form.getTxtOpis().getText().trim();

            Validator.startValidation()
                    .validateNotNullOrEmpty(naziv, "Naziv je obavezan.")
                    .validateNotNullOrEmpty(kontinent, "Kontinent je obavezan.")
                    .throwIfInvalide();

            if (!oznaka.isEmpty() && oznaka.length() > 6) {
                throw new validation.ValidationException("Oznaka može imati najviše 6 karaktera.");
            }

            Region region = new Region();
            region.setNaziv(naziv);
            region.setOznaka(oznaka.isEmpty() ? null : oznaka);
            region.setKontinent(kontinent);
            region.setOpis(opis.isEmpty() ? null : opis);

            controller.Controller.getInstance().ubaciRegion(region);
            JOptionPane.showMessageDialog(form, "Region je sačuvan.");
            form.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
