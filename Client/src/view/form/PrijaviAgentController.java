/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.form;

import domain.Agent;
import javax.swing.JOptionPane;
import validation.Validator;

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

    private void addActionListeners() {
        form.getBtnPrijaviSe().addActionListener(evt -> prijaviSe());
    }

    private void prijaviSe() {
        try {
            String korisnickoIme = form.getTxtKorisnickoIme().getText().trim();
            String sifra = String.valueOf(form.getTxtSifra().getPassword());

            Validator.startValidation()
                    .validateNotNullOrEmpty(korisnickoIme, "Korisničko ime je obavezno.")
                    .validateNotNullOrEmpty(sifra, "Šifra je obavezna.")
                    .throwIfInvalide();

            Agent agent = controller.Controller.getInstance().prijaviAgent(korisnickoIme, sifra);
            controller.Controller.getInstance().setCurrentAgent(agent);

            form.dispose();
            new MainForm().setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
