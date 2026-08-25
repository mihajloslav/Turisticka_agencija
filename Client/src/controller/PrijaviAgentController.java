/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import client.communication.Communication;
import coordinator.Coordinator;
import domain.Agent;
import javax.swing.JOptionPane;
import validation.Validator;
import view.form.PrijaviAgentForm;

/**
 *
 * @author mihajlo
 */
public class PrijaviAgentController {

    private final PrijaviAgentForm form;

    public PrijaviAgentController(PrijaviAgentForm form) {
        this.form = form;
        addActionListeners();
    }

    public void otvoriFormu() {
        form.setVisible(true);
    }

    private void addActionListeners() {
        form.getBtnPrijaviSe().addActionListener(evt -> prijaviSe());
    }

    private void prijaviSe() {
        try {
            String korisnickoIme = form.getTxtKorisnickoIme().getText().trim();
            String sifra = String.valueOf(form.getTxtSifra().getPassword());

            Validator.startValidation()
                    .validateNotNullOrEmpty(korisnickoIme, "Корисничко име је обавезно.")
                    .validateNotNullOrEmpty(sifra, "Шифра је обавезна.")
                    .throwIfInvalide();

            Agent agent = Communication.getInstance().prijaviAgent(korisnickoIme, sifra);
            Coordinator.getInstance().setCurrentAgent(agent);

            form.dispose();
            Coordinator.getInstance().otvoriGlavnuFormu();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
