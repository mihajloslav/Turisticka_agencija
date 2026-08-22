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
import java.text.SimpleDateFormat;
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
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");

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
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
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
                    "Sistem ne može da nađe rezervacije po zadatim kriterijumima",
                    "Rezultat pretrage", JOptionPane.INFORMATION_MESSAGE);
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
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Rezervacija selektovanaRezervacija() {
        int red = form.getTblRezervacije().getSelectedRow();
        if (red < 0) {
            JOptionPane.showMessageDialog(form, "Izaberite rezervaciju iz liste.", "Greška", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String formatirajDetalje(Rezervacija rezervacija) {
        StringBuilder sb = new StringBuilder();
        sb.append("ID rezervacije: ").append(rezervacija.getIdRezervacija()).append("\n");
        sb.append("Datum kreiranja: ").append(rezervacija.getDatumKreiranja() == null ? "" : sdf.format(rezervacija.getDatumKreiranja())).append("\n");
        sb.append("Status plaćanja: ").append(rezervacija.getStatusPlacanja()).append("\n");
        sb.append("Ukupan iznos: ").append(rezervacija.getUkupanIznos()).append("\n");
        sb.append("Napomena: ").append(rezervacija.getNapomena() == null ? "" : rezervacija.getNapomena()).append("\n");
        sb.append("Stavke:\n");
        for (StavkaRezervacije s : rezervacija.getStavke()) {
            sb.append("  - rb ").append(s.getRb())
                    .append(", broj osoba: ").append(s.getBrojOsoba())
                    .append(", polazak: ").append(s.getDatumPolaska() == null ? "" : sdf.format(s.getDatumPolaska()))
                    .append(", dolazak: ").append(s.getDatumDolaska() == null ? "" : sdf.format(s.getDatumDolaska()))
                    .append(", popust: ").append(s.getPopust())
                    .append(", cena: ").append(s.getCena())
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
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
