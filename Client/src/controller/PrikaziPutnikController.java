/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import client.communication.Communication;
import domain.Mesto;
import domain.Putnik;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import view.form.PrikaziPutnikForm;

/**
 *
 * @author mihajlo
 */
public class PrikaziPutnikController {

    private final PrikaziPutnikForm form;
    private final Putnik putnik;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.uuuu");

    public PrikaziPutnikController(PrikaziPutnikForm form, Putnik putnik) {
        this.form = form;
        this.putnik = putnik;
        prikaziPodatke();
        addActionListeners();
    }

    public void otvoriFormu() {
        form.setVisible(true);
    }

    private void prikaziPodatke() {
        String nazivMesta = nazivMesta();
        String html = "<html>"
                + "Име: " + putnik.getIme() + "<br>"
                + "Презиме: " + putnik.getPrezime() + "<br>"
                + "Имејл: " + putnik.getEmail() + "<br>"
                + "Телефон: " + putnik.getTelefon() + "<br>"
                + "ЈМБГ: " + putnik.getJmbg() + "<br>"
                + "Број пасоша: " + putnik.getBrojPasosa() + "<br>"
                + "Датум рођења: " + (putnik.getDatumRodjenja() == null ? "" : putnik.getDatumRodjenja().format(dtf)) + "<br>"
                + "Место: " + nazivMesta
                + "</html>";
        form.getLblPodaci().setText(html);
    }

    private String nazivMesta() {
        if (putnik.getMesto() == null || putnik.getMesto().getIdMesto() == null) {
            return "";
        }
        try {
            List<Mesto> mesta = Communication.getInstance().vratiListuSviMesto();
            for (Mesto m : mesta) {
                if (m.getIdMesto().equals(putnik.getMesto().getIdMesto())) {
                    return m.getNaziv();
                }
            }
        } catch (Exception ex) {
            // ако не можемо да учитамо листу места, приказујемо празно поље уместо грешке
        }
        return "";
    }

    private void addActionListeners() {
        form.getBtnZatvori().addActionListener(evt -> form.dispose());
    }
}
