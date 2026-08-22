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
import view.form.PromeniPutnikForm;

/**
 *
 * @author mihajlo
 */
public class PromeniPutnikController {

    private final PromeniPutnikForm form;
    private final Putnik putnik;

    public PromeniPutnikController(PromeniPutnikForm form, Putnik putnik) {
        this.form = form;
        this.putnik = putnik;
        ucitajMesta();
        popuniPolja();
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
            for (Mesto m : mesta) {
                if (m.getIdMesto().equals(putnik.getMesto().getIdMesto())) {
                    form.getCmbMesto().setSelectedItem(m);
                    break;
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void popuniPolja() {
        form.getTxtIme().setText(putnik.getIme());
        form.getTxtPrezime().setText(putnik.getPrezime());
        form.getTxtEmail().setText(putnik.getEmail());
        form.getTxtTelefon().setText(putnik.getTelefon());
        form.getTxtJmbg().setText(putnik.getJmbg());
        form.getTxtBrojPasosa().setText(putnik.getBrojPasosa());
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

            putnik.setIme(ime);
            putnik.setPrezime(prezime);
            putnik.setEmail(email);
            putnik.setTelefon(telefon);
            putnik.setJmbg(jmbg);
            putnik.setBrojPasosa(brojPasosa);
            putnik.setMesto(mesto);

            Communication.getInstance().promeniPutnik(putnik);
            JOptionPane.showMessageDialog(form, "Sistem je zapamtio putnika.");
            form.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
