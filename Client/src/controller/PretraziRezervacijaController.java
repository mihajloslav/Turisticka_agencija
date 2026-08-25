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
import domain.StavkaRezervacije;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JOptionPane;
import view.components.TableModelRezervacija;
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
            form.getCmbAgentKriterijum().setModel(new DefaultComboBoxModel<>(agenti.toArray(new Agent[0])));
            form.getCmbAgentKriterijum().setRenderer(new DefaultListCellRenderer() {
                @Override
                public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value,
                        int index, boolean isSelected, boolean cellHasFocus) {
                    String text = value instanceof Agent ? ((Agent) value).getIme() + " " + ((Agent) value).getPrezime() : "";
                    return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
                }
            });

            List<Putnik> putnici = Communication.getInstance().vratiListuSviPutnik();
            form.getCmbPutnikKriterijum().setModel(new DefaultComboBoxModel<>(putnici.toArray(new Putnik[0])));
            form.getCmbPutnikKriterijum().setRenderer(new DefaultListCellRenderer() {
                @Override
                public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value,
                        int index, boolean isSelected, boolean cellHasFocus) {
                    String text = value instanceof Putnik ? ((Putnik) value).getIme() + " " + ((Putnik) value).getPrezime() : "";
                    return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
                }
            });

            List<Aranzman> aranzmani = Communication.getInstance().vratiListuSviAranzman();
            form.getCmbAranzmanKriterijum().setModel(new DefaultComboBoxModel<>(aranzmani.toArray(new Aranzman[0])));
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
        form.getBtnPretraziStatus().addActionListener(evt -> pretraziPoStatusu());
        form.getBtnPretraziAgent().addActionListener(evt -> pretraziPoAgentu());
        form.getBtnPretraziPutnik().addActionListener(evt -> pretraziPoPutniku());
        form.getBtnPretraziAranzman().addActionListener(evt -> pretraziPoAranzmanu());
        form.getBtnPrikazi().addActionListener(evt -> prikaziDetalje());
        form.getBtnPromeni().addActionListener(evt -> promeni());
    }

    private void prikaziRezultat(List<Rezervacija> rezultat) {
        if (rezultat.isEmpty()) {
            JOptionPane.showMessageDialog(form,
                    "Систем не може да нађе резервације по задатим критеријумима",
                    "Резултат претраге", JOptionPane.INFORMATION_MESSAGE);
        }
        form.getTblRezervacije().setModel(new TableModelRezervacija(rezultat));
        form.getTxtAreaDetalji().setText("");
    }

    private void pretraziPoStatusu() {
        try {
            String status = form.getTxtStatusKriterijum().getText().trim();
            Rezervacija kriterijum = new Rezervacija();
            kriterijum.setStatusPlacanja(status.isEmpty() ? null : status);
            prikaziRezultat(Communication.getInstance().vratiListuRezervacijaKriterijumRezervacija(kriterijum));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pretraziPoAgentu() {
        try {
            Agent agent = (Agent) form.getCmbAgentKriterijum().getSelectedItem();
            if (agent == null) {
                return;
            }
            prikaziRezultat(Communication.getInstance().vratiListuRezervacijaKriterijumAgent(agent));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pretraziPoPutniku() {
        try {
            Putnik putnik = (Putnik) form.getCmbPutnikKriterijum().getSelectedItem();
            if (putnik == null) {
                return;
            }
            prikaziRezultat(Communication.getInstance().vratiListuRezervacijaKriterijumPutnik(putnik));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pretraziPoAranzmanu() {
        try {
            Aranzman aranzman = (Aranzman) form.getCmbAranzmanKriterijum().getSelectedItem();
            if (aranzman == null) {
                return;
            }
            prikaziRezultat(Communication.getInstance().vratiListuRezervacijaKriterijumAranzman(aranzman));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Rezervacija selektovanaRezervacija() {
        int red = form.getTblRezervacije().getSelectedRow();
        if (red < 0) {
            JOptionPane.showMessageDialog(form, "Изаберите резервацију из листе.", "Грешка", JOptionPane.ERROR_MESSAGE);
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
        sb.append("Ставке:\n");
        for (StavkaRezervacije s : rezervacija.getStavke()) {
            sb.append("  - рб ").append(s.getRb())
                    .append(", број особа: ").append(s.getBrojOsoba())
                    .append(", полазак: ").append(s.getDatumPolaska() == null ? "" : s.getDatumPolaska().format(dtf))
                    .append(", долазак: ").append(s.getDatumDolaska() == null ? "" : s.getDatumDolaska().format(dtf))
                    .append(", попуст: ").append(s.getPopust())
                    .append(", цена: ").append(s.getCena())
                    .append("\n");
        }
        return sb.toString();
    }

    private void promeni() {
        Rezervacija selektovana = selektovanaRezervacija();
        if (selektovana == null) {
            return;
        }
        try {
            Rezervacija rezervacija = Communication.getInstance().pretraziRezervacija(selektovana);
            Coordinator.getInstance().otvoriPromeniRezervacijaFormu(rezervacija);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
