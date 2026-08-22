/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.form;

import javax.swing.JOptionPane;

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

    private void addActionListeners() {
        form.getMiKreirajRezervaciju().addActionListener(evt -> new KreirajRezervacijaForm().setVisible(true));
        form.getMiPretraziRezervaciju().addActionListener(evt -> new PretraziRezervacijaForm().setVisible(true));
        form.getMiKreirajPutnika().addActionListener(evt -> new KreirajPutnikForm().setVisible(true));
        form.getMiPretraziPutnika().addActionListener(evt -> new PretraziPutnikForm().setVisible(true));
        form.getMiRegion().addActionListener(evt -> new UbaciRegionForm().setVisible(true));
        form.getMiOProgramu().addActionListener(evt -> oProgramu());
    }

    private void oProgramu() {
        JOptionPane.showMessageDialog(form,
                "Turistička agencija\nSeminarski rad - Projektovanje softvera",
                "O programu", JOptionPane.INFORMATION_MESSAGE);
    }
}
