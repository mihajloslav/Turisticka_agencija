/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import client.communication.Communication;
import coordinator.Coordinator;
import domain.Putnik;
import java.util.List;
import javax.swing.JOptionPane;
import view.components.TableModelPutnik;
import view.form.PretraziPutnikForm;

/**
 *
 * @author mihajlo
 */
public class PretraziPutnikController {

    private final PretraziPutnikForm form;

    public PretraziPutnikController(PretraziPutnikForm form) {
        this.form = form;
        addActionListeners();
    }

    public void otvoriFormu() {
        form.setVisible(true);
    }

    private void addActionListeners() {
        form.getBtnPretrazi().addActionListener(evt -> pretrazi());
        form.getBtnPromeni().addActionListener(evt -> promeni());
        form.getBtnObrisi().addActionListener(evt -> obrisi());
    }

    private void pretrazi() {
        try {
            String ime = form.getTxtImeKriterijum().getText().trim();
            String prezime = form.getTxtPrezimeKriterijum().getText().trim();

            Putnik kriterijum = new Putnik();
            kriterijum.setIme(ime.isEmpty() ? null : ime);
            kriterijum.setPrezime(prezime.isEmpty() ? null : prezime);

            List<Putnik> rezultat = Communication.getInstance().vratiListuPutnikKriterijumPutnik(kriterijum);

            if (rezultat.isEmpty()) {
                JOptionPane.showMessageDialog(form,
                        "Систем не може да нађе путнике по задатим критеријумима",
                        "Резултат претраге", JOptionPane.INFORMATION_MESSAGE);
            }
            form.getTblPutnici().setModel(new TableModelPutnik(rezultat));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Putnik selektovaniPutnik() {
        int red = form.getTblPutnici().getSelectedRow();
        if (red < 0) {
            JOptionPane.showMessageDialog(form, "Изаберите путника из листе.", "Грешка", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        TableModelPutnik model = (TableModelPutnik) form.getTblPutnici().getModel();
        return model.getPutnikAt(red);
    }

    private void promeni() {
        Putnik selektovan = selektovaniPutnik();
        if (selektovan == null) {
            return;
        }
        try {
            Putnik putnik = Communication.getInstance().pretraziPutnik(selektovan);
            Coordinator.getInstance().otvoriPromeniPutnikFormu(putnik);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void obrisi() {
        Putnik selektovan = selektovaniPutnik();
        if (selektovan == null) {
            return;
        }
        try {
            Putnik putnik = Communication.getInstance().pretraziPutnik(selektovan);
            Coordinator.getInstance().otvoriObrisiPutnikFormu(putnik);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
