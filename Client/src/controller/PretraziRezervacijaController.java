/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import client.communication.Communication;
import coordinator.Coordinator;
import domain.Agent;
import domain.Aranzman;
import domain.Putnik;
import domain.Rezervacija;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JOptionPane;
import validation.Validator;
import view.components.TableModelRezervacija;
import view.components.TableModelStavkaRezervacije;
import view.form.PretraziRezervacijaForm;

/**
 *
 * @author mihajlo
 */
public class PretraziRezervacijaController {

    private final PretraziRezervacijaForm form;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.uuuu");

    public PretraziRezervacijaController(PretraziRezervacijaForm form) {
        this.form = form;
        ucitajListe();
        addActionListeners();
    }

    public void otvoriFormu() {
        form.setVisible(true);
    }

    private void ucitajListe() {
        try {
            List<Agent> agenti = Communication.getInstance().vratiListuSviAgent();
            DefaultComboBoxModel<Agent> agentModel = new DefaultComboBoxModel<>();
            agentModel.addElement(null);
            for (Agent a : agenti) {
                agentModel.addElement(a);
            }
            form.getCmbAgentKriterijum().setModel(agentModel);
            form.getCmbAgentKriterijum().setRenderer(new DefaultListCellRenderer() {
                @Override
                public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value,
                        int index, boolean isSelected, boolean cellHasFocus) {
                    String text = value instanceof Agent ? ((Agent) value).getIme() + " " + ((Agent) value).getPrezime() : "";
                    return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
                }
            });

            List<Putnik> putnici = Communication.getInstance().vratiListuSviPutnik();
            DefaultComboBoxModel<Putnik> putnikModel = new DefaultComboBoxModel<>();
            putnikModel.addElement(null);
            for (Putnik p : putnici) {
                putnikModel.addElement(p);
            }
            form.getCmbPutnikKriterijum().setModel(putnikModel);
            form.getCmbPutnikKriterijum().setRenderer(new DefaultListCellRenderer() {
                @Override
                public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value,
                        int index, boolean isSelected, boolean cellHasFocus) {
                    String text = value instanceof Putnik ? ((Putnik) value).getIme() + " " + ((Putnik) value).getPrezime() : "";
                    return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
                }
            });

            List<Aranzman> aranzmani = Communication.getInstance().vratiListuSviAranzman();
            DefaultComboBoxModel<Aranzman> aranzmanModel = new DefaultComboBoxModel<>();
            aranzmanModel.addElement(null);
            for (Aranzman a : aranzmani) {
                aranzmanModel.addElement(a);
            }
            form.getCmbAranzmanKriterijum().setModel(aranzmanModel);
            form.getCmbAranzmanKriterijum().setRenderer(new DefaultListCellRenderer() {
                @Override
                public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value,
                        int index, boolean isSelected, boolean cellHasFocus) {
                    String text = value instanceof Aranzman ? ((Aranzman) value).getNaziv() : "";
                    return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
                }
            });
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addActionListeners() {
        form.getBtnPretrazi().addActionListener(evt -> pretrazi());
        form.getBtnPrikazi().addActionListener(evt -> prikaziDetalje());
        form.getBtnPromeni().addActionListener(evt -> promeni());
    }

    private void prikaziRezultat(List<Rezervacija> rezultat) {
        if (rezultat.isEmpty()) {
            JOptionPane.showMessageDialog(form,
                    "Систем не може да нађе резервације по задатим критеријумима",
                    "Резултат претраге", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(form,
                    "Систем је нашао резервације по задатим критеријумима",
                    "Резултат претраге", JOptionPane.INFORMATION_MESSAGE);
        }
        form.getTblRezervacije().setModel(new TableModelRezervacija(rezultat));
        form.getTxtAreaDetalji().setText("");
        form.getTblStavke().setModel(new TableModelStavkaRezervacije(null));
    }

    private void pretrazi() {
        try {
            String statusDisplay = (String) form.getCmbStatusKriterijum().getSelectedItem();
            Agent agent = (Agent) form.getCmbAgentKriterijum().getSelectedItem();
            Putnik putnik = (Putnik) form.getCmbPutnikKriterijum().getSelectedItem();
            Aranzman aranzman = (Aranzman) form.getCmbAranzmanKriterijum().getSelectedItem();
            String iznosOdText = form.getTxtIznosOd().getText().trim();
            String iznosDoText = form.getTxtIznosDo().getText().trim();
            String datumOdText = form.getTxtDatumOd().getText().trim();
            String datumDoText = form.getTxtDatumDo().getText().trim();

            Validator validator = Validator.startValidation();
            if (!iznosOdText.isEmpty()) {
                validator.validateValueIsNumber(iznosOdText, "Износ од мора бити исправан број.");
            }
            if (!iznosDoText.isEmpty()) {
                validator.validateValueIsNumber(iznosDoText, "Износ до мора бити исправан број.");
            }
            if (!datumOdText.isEmpty()) {
                validator.validateValueIsDate(datumOdText, "dd.MM.uuuu", "Датум од није исправан.");
            }
            if (!datumDoText.isEmpty()) {
                validator.validateValueIsDate(datumDoText, "dd.MM.uuuu", "Датум до није исправан.");
            }
            validator.throwIfInvalide();

            Double iznosOd = iznosOdText.isEmpty() ? null : Double.parseDouble(iznosOdText);
            Double iznosDo = iznosDoText.isEmpty() ? null : Double.parseDouble(iznosDoText);
            if (iznosOd != null && iznosDo != null && iznosOd > iznosDo) {
                throw new validation.ValidationException("Износ од не сме бити већи од износа до.");
            }

            LocalDate datumOd = datumOdText.isEmpty() ? null : LocalDate.parse(datumOdText, dtf);
            LocalDate datumDo = datumDoText.isEmpty() ? null : LocalDate.parse(datumDoText, dtf);
            if (datumOd != null && datumDo != null && datumOd.isAfter(datumDo)) {
                throw new validation.ValidationException("Датум од не сме бити после датума до.");
            }

            Rezervacija kriterijum = new Rezervacija();
            kriterijum.setStatusPlacanja(statusDisplay == null || statusDisplay.isEmpty() ? null : statusDisplay);
            kriterijum.setAgent(agent);
            kriterijum.setPutnik(putnik);
            kriterijum.setAranzman(aranzman);
            kriterijum.setUkupanIznosOd(iznosOd);
            kriterijum.setUkupanIznosDo(iznosDo);
            kriterijum.setDatumKreiranjaOd(datumOd);
            kriterijum.setDatumKreiranjaDo(datumDo);

            prikaziRezultat(Communication.getInstance().vratiListuRezervacijaKriterijumRezervacija(kriterijum));
        } catch (validation.ValidationException vex) {
            JOptionPane.showMessageDialog(form, vex.getMessage(), "Упозорење", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Rezervacija selektovanaRezervacija() {
        int red = form.getTblRezervacije().getSelectedRow();
        if (red < 0) {
            JOptionPane.showMessageDialog(form, "Изаберите резервацију из листе.", "Упозорење", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        TableModelRezervacija model = (TableModelRezervacija) form.getTblRezervacije().getModel();
        return model.getRezervacijaAt(red);
    }

    private void prikaziDetalje() {
        Rezervacija selektovana = selektovanaRezervacija();
        if (selektovana == null) {
            return;
        }
        try {
            Rezervacija rezervacija = Communication.getInstance().pretraziRezervacija(selektovana);
            form.getTxtAreaDetalji().setText(formatirajDetalje(rezervacija));
            form.getTblStavke().setModel(new TableModelStavkaRezervacije(rezervacija.getStavke()));
            JOptionPane.showMessageDialog(form, "Систем је нашао резервацију", "Резултат претраге", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String formatirajDetalje(Rezervacija rezervacija) {
        StringBuilder sb = new StringBuilder();
        sb.append("ИД резервације: ").append(rezervacija.getIdRezervacija()).append("\n");
        sb.append("Датум креирања: ").append(rezervacija.getDatumKreiranja() == null ? "" : rezervacija.getDatumKreiranja().format(dtf)).append("\n");
        sb.append("Статус плаћања: ").append(rezervacija.getStatusPlacanja()).append("\n");
        sb.append("Укупан износ: ").append(rezervacija.getUkupanIznos()).append("\n");
        sb.append("Напомена: ").append(rezervacija.getNapomena() == null ? "" : rezervacija.getNapomena()).append("\n");
        return sb.toString();
    }

    private void promeni() {
        Rezervacija selektovana = selektovanaRezervacija();
        if (selektovana == null) {
            return;
        }
        try {
            Rezervacija rezervacija = Communication.getInstance().pretraziRezervacija(selektovana);
            JOptionPane.showMessageDialog(form, "Систем је нашао резервацију", "Резултат претраге", JOptionPane.INFORMATION_MESSAGE);
            Coordinator.getInstance().otvoriPromeniRezervacijaFormu(rezervacija);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
