/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import client.communication.Communication;
import domain.Mesto;
import domain.Putnik;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JOptionPane;
import validation.Validator;
import view.form.KreirajPutnikForm;

/**
 *
 * @author mihajlo
 */
public class KreirajPutnikController {

    private final KreirajPutnikForm form;

    public KreirajPutnikController(KreirajPutnikForm form) {
        this.form = form;
        ucitajMesta();
        addActionListeners();
    }

    public void otvoriFormu() {
        form.setVisible(true);
    }

    private void ucitajMesta() {
        try {
            List<Mesto> mesta = Communication.getInstance().vratiListuSviMesto();
            form.getCmbMesto().setModel(new DefaultComboBoxModel<>(mesta.toArray(new Mesto[0])));
            form.getCmbMesto().setRenderer(new DefaultListCellRenderer() {
                @Override
                public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value,
                        int index, boolean isSelected, boolean cellHasFocus) {
                    String text = value instanceof Mesto ? ((Mesto) value).getNaziv() : "";
                    return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
                }
            });
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addActionListeners() {
        form.getBtnSacuvaj().addActionListener(evt -> sacuvaj());
    }

    private void sacuvaj() {
        try {
            String ime = form.getTxtIme().getText().trim();
            String prezime = form.getTxtPrezime().getText().trim();
            String email = form.getTxtEmail().getText().trim();
            String telefon = form.getTxtTelefon().getText().trim();
            String jmbg = form.getTxtJmbg().getText().trim();
            String brojPasosa = form.getTxtBrojPasosa().getText().trim();
            Mesto mesto = (Mesto) form.getCmbMesto().getSelectedItem();

            Validator.startValidation()
                    .validateNotNullOrEmpty(ime, "Ime je obavezno.")
                    .validateNotNullOrEmpty(prezime, "Prezime je obavezno.")
                    .validateNotNullOrEmpty(email, "Email je obavezan.")
                    .validateNotNullOrEmpty(telefon, "Telefon je obavezan.")
                    .validateNotNullOrEmpty(jmbg, "JMBG je obavezan.")
                    .validateNotNullOrEmpty(brojPasosa, "Broj pasoša je obavezan.")
                    .validateNotNull(mesto, "Mesto je obavezno.")
                    .throwIfInvalide();

            if (!email.contains("@")) {
                throw new validation.ValidationException("Email mora sadržati znak '@'.");
            }
            if (jmbg.length() != 13) {
                throw new validation.ValidationException("JMBG mora imati tačno 13 karaktera.");
            }

            Putnik putnik = new Putnik();
            putnik.setIme(ime);
            putnik.setPrezime(prezime);
            putnik.setEmail(email);
            putnik.setTelefon(telefon);
            putnik.setJmbg(jmbg);
            putnik.setBrojPasosa(brojPasosa);
            putnik.setMesto(mesto);

            Communication.getInstance().kreirajPutnik(putnik);
            JOptionPane.showMessageDialog(form, "Sistem je zapamtio putnika.");
            form.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
