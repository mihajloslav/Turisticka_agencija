/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import coordinator.Coordinator;
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
        addActionListeners();
    }

    public void otvoriFormu() {
        form.setVisible(true);
    }

    private void addActionListeners() {
        form.getMiKreirajRezervaciju().addActionListener(evt -> Coordinator.getInstance().otvoriKreirajRezervacijaFormu());
        form.getMiPretraziRezervaciju().addActionListener(evt -> Coordinator.getInstance().otvoriPretraziRezervacijaFormu());
        form.getMiKreirajPutnika().addActionListener(evt -> Coordinator.getInstance().otvoriKreirajPutnikFormu());
        form.getMiPretraziPutnika().addActionListener(evt -> Coordinator.getInstance().otvoriPretraziPutnikFormu());
        form.getMiRegion().addActionListener(evt -> Coordinator.getInstance().otvoriUbaciRegionFormu());
        form.getMiOProgramu().addActionListener(evt -> oProgramu());
    }

    private void oProgramu() {
        JOptionPane.showMessageDialog(form,
                "Turistička agencija\nSeminarski rad - Projektovanje softvera",
                "O programu", JOptionPane.INFORMATION_MESSAGE);
    }
}
