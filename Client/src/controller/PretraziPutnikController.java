/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import client.communication.Communication;
import coordinator.Coordinator;
import domain.Mesto;
import domain.Putnik;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JOptionPane;
import validation.Validator;
import view.components.TableModelPutnik;
import view.form.PretraziPutnikForm;

/**
 *
 * @author mihajlo
 */
public class PretraziPutnikController {

    private final PretraziPutnikForm form;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT);

    public PretraziPutnikController(PretraziPutnikForm form) {
        this.form = form;
        ucitajMesta();
        addActionListeners();
    }

    public void otvoriFormu() {
        form.setVisible(true);
    }

    private void ucitajMesta() {
        try {
            List<Mesto> mesta = Communication.getInstance().vratiListuSviMesto();
            DefaultComboBoxModel<Mesto> model = new DefaultComboBoxModel<>();
            model.addElement(null);
            for (Mesto m : mesta) {
                model.addElement(m);
            }
            form.getCmbMestoKriterijum().setModel(model);
            form.getCmbMestoKriterijum().setRenderer(new DefaultListCellRenderer() {
                @Override
                public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value,
                        int index, boolean isSelected, boolean cellHasFocus) {
                    String text = value instanceof Mesto ? ((Mesto) value).getNaziv() : "Сва места";
                    return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
                }
            });
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Грешка", JOptionPane.ERROR_MESSAGE);
        }
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
            String email = form.getTxtEmailKriterijum().getText().trim();
            String telefon = form.getTxtTelefonKriterijum().getText().trim();
            String jmbg = form.getTxtJmbgKriterijum().getText().trim();
            String brojPasosa = form.getTxtBrojPasosaKriterijum().getText().trim();
            Mesto mesto = (Mesto) form.getCmbMestoKriterijum().getSelectedItem();
            String datumRodjenjaText = form.getTxtDatumRodjenjaKriterijum().getText().trim();

            if (!datumRodjenjaText.isEmpty()) {
                Validator.startValidation()
                        .validateValueIsDate(datumRodjenjaText, "dd.MM.uuuu", "Датум рођења није исправан.")
                        .throwIfInvalide();
            }

            Putnik kriterijum = new Putnik();
            kriterijum.setIme(ime.isEmpty() ? null : ime);
            kriterijum.setPrezime(prezime.isEmpty() ? null : prezime);
            kriterijum.setEmail(email.isEmpty() ? null : email);
            kriterijum.setTelefon(telefon.isEmpty() ? null : telefon);
            kriterijum.setJmbg(jmbg.isEmpty() ? null : jmbg);
            kriterijum.setBrojPasosa(brojPasosa.isEmpty() ? null : brojPasosa);
            kriterijum.setMesto(mesto);
            kriterijum.setDatumRodjenja(datumRodjenjaText.isEmpty() ? null : LocalDate.parse(datumRodjenjaText, dtf));

            List<Putnik> rezultat = Communication.getInstance().vratiListuPutnikKriterijumPutnik(kriterijum);

            if (rezultat.isEmpty()) {
                JOptionPane.showMessageDialog(form,
                        "Систем не може да нађе путнике по задатим критеријумима",
                        "Резултат претраге", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(form,
                        "Систем је нашао путнике по задатим критеријумима",
                        "Резултат претраге", JOptionPane.INFORMATION_MESSAGE);
            }
            form.getTblPutnici().setModel(new TableModelPutnik(rezultat));
        } catch (validation.ValidationException vex) {
            JOptionPane.showMessageDialog(form, vex.getMessage(), "Упозорење", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Putnik selektovaniPutnik() {
        int red = form.getTblPutnici().getSelectedRow();
        if (red < 0) {
            JOptionPane.showMessageDialog(form, "Изаберите путника из листе.", "Упозорење", JOptionPane.WARNING_MESSAGE);
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
            JOptionPane.showMessageDialog(form, "Систем је нашао путника", "Резултат претраге", JOptionPane.INFORMATION_MESSAGE);
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
            JOptionPane.showMessageDialog(form, "Систем је нашао путника", "Резултат претраге", JOptionPane.INFORMATION_MESSAGE);
            Coordinator.getInstance().otvoriObrisiPutnikFormu(putnik);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
