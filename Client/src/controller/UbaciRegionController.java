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
            String kontinent = form.getTxtKontinent().getText().trim();
            String opis = form.getTxtOpis().getText().trim();

            Validator.startValidation()
                    .validateNotNullOrEmpty(naziv, "Назив је обавезан.")
                    .validateNotNullOrEmpty(kontinent, "Континент је обавезан.")
                    .throwIfInvalide();

            if (!oznaka.isEmpty() && oznaka.length() > 6) {
                throw new validation.ValidationException("Ознака може имати највише 6 карактера.");
            }

            Region region = new Region();
            region.setNaziv(naziv);
            region.setOznaka(oznaka.isEmpty() ? null : oznaka);
            region.setKontinent(kontinent);
            region.setOpis(opis.isEmpty() ? null : opis);

            Communication.getInstance().ubaciRegion(region);
            JOptionPane.showMessageDialog(form, "Регион је сачуван.");
            form.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
