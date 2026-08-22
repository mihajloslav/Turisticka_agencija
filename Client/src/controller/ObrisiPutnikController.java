/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import client.communication.Communication;
import domain.Putnik;
import javax.swing.JOptionPane;
import view.form.ObrisiPutnikForm;

/**
 *
 * @author mihajlo
 */
public class ObrisiPutnikController {

    private final ObrisiPutnikForm form;
    private final Putnik putnik;

    public ObrisiPutnikController(ObrisiPutnikForm form, Putnik putnik) {
        this.form = form;
        this.putnik = putnik;
        prikaziPodatke();
        addActionListeners();
    }

    public void otvoriFormu() {
        form.setVisible(true);
    }

    private void prikaziPodatke() {
        String html = "<html>"
                + "Ime: " + putnik.getIme() + "<br>"
                + "Prezime: " + putnik.getPrezime() + "<br>"
                + "Email: " + putnik.getEmail() + "<br>"
                + "Telefon: " + putnik.getTelefon() + "<br>"
                + "JMBG: " + putnik.getJmbg() + "<br>"
                + "Broj pasoša: " + putnik.getBrojPasosa()
                + "</html>";
        form.getLblPodaci().setText(html);
    }

    private void addActionListeners() {
        form.getBtnOtkazi().addActionListener(evt -> form.dispose());
        form.getBtnPotvrdi().addActionListener(evt -> obrisi());
    }

    private void obrisi() {
        try {
            Communication.getInstance().obrisiPutnik(putnik);
            JOptionPane.showMessageDialog(form, "Putnik je obrisan.");
            form.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
