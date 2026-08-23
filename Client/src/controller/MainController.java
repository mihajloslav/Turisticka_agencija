/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import coordinator.Coordinator;
import domain.Agent;
import javax.swing.JOptionPane;
import view.form.MainForm;

/**
 *
 * @author mihajlo
 */
public class MainController {

    private final MainForm form;

    public MainController(MainForm form) {
        this.form = form;
        prikaziPrijavljenogAgenta();
        addActionListeners();
    }

    public void otvoriFormu() {
        form.setVisible(true);
    }

    private void prikaziPrijavljenogAgenta() {
        Agent agent = Coordinator.getInstance().getCurrentAgent();
        if (agent != null) {
            form.getLblAgent().setText("Agent: " + agent.getIme() + " " + agent.getPrezime());
        }
    }

    private void addActionListeners() {
        form.getBtnKreirajRezervaciju().addActionListener(evt -> Coordinator.getInstance().otvoriKreirajRezervacijaFormu());
        form.getBtnPretraziRezervaciju().addActionListener(evt -> Coordinator.getInstance().otvoriPretraziRezervacijaFormu());
        form.getBtnKreirajPutnika().addActionListener(evt -> Coordinator.getInstance().otvoriKreirajPutnikFormu());
        form.getBtnPretraziPutnika().addActionListener(evt -> Coordinator.getInstance().otvoriPretraziPutnikFormu());
        form.getBtnUbaciRegion().addActionListener(evt -> Coordinator.getInstance().otvoriUbaciRegionFormu());
        form.getBtnOProgramu().addActionListener(evt -> oProgramu());
    }

    private void oProgramu() {
        JOptionPane.showMessageDialog(form,
                "Turistička agencija\nSeminarski rad - Projektovanje softvera",
                "O programu", JOptionPane.INFORMATION_MESSAGE);
    }
}
