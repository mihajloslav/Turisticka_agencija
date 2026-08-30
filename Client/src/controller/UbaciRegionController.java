/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import client.communication.Communication;
import domain.Region;
import javax.swing.JOptionPane;
import validation.Validator;
import view.form.UbaciRegionForm;

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

    public void otvoriFormu() {
        form.setVisible(true);
    }

    private void addActionListeners() {
        form.getBtnSacuvaj().addActionListener(evt -> sacuvaj());
    }

    private void sacuvaj() {
        try {
            String naziv = form.getTxtNaziv().getText().trim();
            String oznaka = form.getTxtOznaka().getText().trim();
            String kontinent = (String) form.getCmbKontinent().getSelectedItem();
            String opis = form.getTxtOpis().getText().trim();

            Validator.startValidation()
                    .validateNotNullOrEmpty(naziv, "Назив је обавезан.")
                    .validateNotNullOrEmpty(oznaka, "Ознака је обавезна.")
                    .validateNotNullOrEmpty(kontinent, "Континент је обавезан.")
                    .throwIfInvalide();

            if (oznaka.length() > 6) {
                throw new validation.ValidationException("Ознака може имати највише 6 карактера.");
            }

            Region region = new Region();
            region.setNaziv(naziv);
            region.setOznaka(oznaka);
            region.setKontinent(kontinent);
            region.setOpis(opis.isEmpty() ? null : opis);

            Communication.getInstance().ubaciRegion(region);
            JOptionPane.showMessageDialog(form, "Систем је запамтио регион.", "Успех", JOptionPane.INFORMATION_MESSAGE);
            form.dispose();
        } catch (validation.ValidationException vex) {
            JOptionPane.showMessageDialog(form, vex.getMessage(), "Упозорење", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, "Систем не може да запамти регион", "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
