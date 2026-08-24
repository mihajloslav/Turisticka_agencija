/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import coordinator.Coordinator;
import domain.Agent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import view.form.MainForm;

/**
 *
 * @author mihajlo
 */
public class MainController {

    private static final String OZNAKA = "✔ ";

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

        form.getMiTemaLight().addActionListener(evt -> primeniTemu(new FlatLightLaf(), form.getMiTemaLight()));
        form.getMiTemaDark().addActionListener(evt -> primeniTemu(new FlatDarkLaf(), form.getMiTemaDark()));
        form.getMiTemaIntelliJ().addActionListener(evt -> primeniTemu(new FlatIntelliJLaf(), form.getMiTemaIntelliJ()));
        form.getMiTemaDarcula().addActionListener(evt -> primeniTemu(new FlatDarculaLaf(), form.getMiTemaDarcula()));
        form.getMiTemaMacLight().addActionListener(evt -> primeniTemu(new FlatMacLightLaf(), form.getMiTemaMacLight()));
        form.getMiTemaMacDark().addActionListener(evt -> primeniTemu(new FlatMacDarkLaf(), form.getMiTemaMacDark()));
    }

    private void primeniTemu(LookAndFeel laf, JMenuItem izabrana) {
        try {
            UIManager.setLookAndFeel(laf);
            FlatLaf.updateUI();
            oznaciIzabranuTemu(izabrana);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void oznaciIzabranuTemu(JMenuItem izabrana) {
        JMenuItem[] sve = {form.getMiTemaLight(), form.getMiTemaDark(), form.getMiTemaIntelliJ(),
            form.getMiTemaDarcula(), form.getMiTemaMacLight(), form.getMiTemaMacDark()};
        for (JMenuItem stavka : sve) {
            String tekst = stavka.getText().replace(OZNAKA, "");
            stavka.setText(stavka == izabrana ? OZNAKA + tekst : tekst);
        }
    }

    private void oProgramu() {
        JOptionPane.showMessageDialog(form,
                "Turistička agencija\nSeminarski rad - Projektovanje softvera",
                "O programu", JOptionPane.INFORMATION_MESSAGE);
    }
}
