/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.form;

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
import validation.Validator;
import view.components.TableModelStavkaRezervacije;

/**
 *
 * @author mihajlo
 */
public class PromeniRezervacijaController {

    private final PromeniRezervacijaForm form;
    private final Rezervacija rezervacija;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
    private TableModelStavkaRezervacije tableModel;

    public PromeniRezervacijaController(PromeniRezervacijaForm form, Rezervacija rezervacija) {
        this.form = form;
        this.rezervacija = rezervacija;
        sdf.setLenient(false);
        tableModel = new TableModelStavkaRezervacije(rezervacija.getStavke());
        form.getTblStavke().setModel(tableModel);
        ucitajListe();
        popuniPolja();
        addActionListeners();
    }

    private void ucitajListe() {
        try {
            List<Agent> agenti = controller.Controller.getInstance().vratiListuSviAgent();
            form.getCmbAgent().setModel(new DefaultComboBoxModel<>(agenti.toArray(new Agent[0])));
            form.getCmbAgent().setRenderer(new DefaultListCellRenderer() {
                @Override
                public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value,
                        int index, boolean isSelected, boolean cellHasFocus) {
                    String text = value instanceof Agent ? ((Agent) value).getIme() + " " + ((Agent) value).getPrezime() : "";
                    return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
                }
            });

            List<Putnik> putnici = controller.Controller.getInstance().vratiListuSviPutnik();
            form.getCmbPutnik().setModel(new DefaultComboBoxModel<>(putnici.toArray(new Putnik[0])));
            form.getCmbPutnik().setRenderer(new DefaultListCellRenderer() {
                @Override
                public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value,
                        int index, boolean isSelected, boolean cellHasFocus) {
                    String text = value instanceof Putnik ? ((Putnik) value).getIme() + " " + ((Putnik) value).getPrezime() : "";
                    return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
                }
            });

            List<Aranzman> aranzmani = controller.Controller.getInstance().vratiListuSviAranzman();
            form.getCmbAranzman().setModel(new DefaultComboBoxModel<>(aranzmani.toArray(new Aranzman[0])));
            form.getCmbAranzman().setRenderer(new DefaultListCellRenderer() {
                @Override
                public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value,
                        int index, boolean isSelected, boolean cellHasFocus) {
                    String text = value instanceof Aranzman ? ((Aranzman) value).getNaziv() : "";
                    return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
                }
            });

            for (Agent a : agenti) {
                if (a.getIdAgent().equals(rezervacija.getAgent().getIdAgent())) {
                    form.getCmbAgent().setSelectedItem(a);
                    break;
                }
            }
            for (Putnik p : putnici) {
                if (p.getIdPutnik().equals(rezervacija.getPutnik().getIdPutnik())) {
                    form.getCmbPutnik().setSelectedItem(p);
                    break;
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void popuniPolja() {
        form.getCmbStatusPlacanja().setSelectedItem(rezervacija.getStatusPlacanja());
        form.getTxtNapomena().setText(rezervacija.getNapomena());
    }

    private void addActionListeners() {
        form.getBtnDodajStavku().addActionListener(evt -> dodajStavku());
        form.getBtnUkloniStavku().addActionListener(evt -> ukloniStavku());
        form.getBtnSacuvajRezervaciju().addActionListener(evt -> sacuvajRezervaciju());
    }

    private void dodajStavku() {
        try {
            Aranzman aranzman = (Aranzman) form.getCmbAranzman().getSelectedItem();
            String brojOsobaText = form.getTxtBrojOsoba().getText().trim();
            String datumPolaskaText = form.getTxtDatumPolaska().getText().trim();
            String datumDolaskaText = form.getTxtDatumDolaska().getText().trim();

            Validator.startValidation()
                    .validateNotNull(aranzman, "Aranžman je obavezan.")
                    .validateValueIsNumber(brojOsobaText, "Broj osoba mora biti broj.")
                    .validateValueIsDate(datumPolaskaText, "dd.MM.yyyy.", "Datum polaska nije ispravan.")
                    .validateValueIsDate(datumDolaskaText, "dd.MM.yyyy.", "Datum dolaska nije ispravan.")
                    .throwIfInvalide();

            int brojOsoba = Integer.parseInt(brojOsobaText);
            if (brojOsoba <= 0) {
                throw new validation.ValidationException("Broj osoba mora biti veći od 0.");
            }
            java.util.Date datumPolaska = sdf.parse(datumPolaskaText);
            java.util.Date datumDolaska = sdf.parse(datumDolaskaText);
            if (!datumDolaska.after(datumPolaska)) {
                throw new validation.ValidationException("Datum dolaska mora biti posle datuma polaska.");
            }

            StavkaRezervacije stavka = new StavkaRezervacije();
            stavka.setAranzman(aranzman);
            stavka.setBrojOsoba(brojOsoba);
            stavka.setDatumPolaska(datumPolaska);
            stavka.setDatumDolaska(datumDolaska);
            tableModel.dodajStavku(stavka);

            form.getTxtBrojOsoba().setText("");
            form.getTxtDatumPolaska().setText("");
            form.getTxtDatumDolaska().setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ukloniStavku() {
        int red = form.getTblStavke().getSelectedRow();
        if (red < 0) {
            JOptionPane.showMessageDialog(form, "Izaberite stavku iz liste.", "Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }
        tableModel.ukloniStavku(red);
    }

    private void sacuvajRezervaciju() {
        try {
            Agent agent = (Agent) form.getCmbAgent().getSelectedItem();
            Putnik putnik = (Putnik) form.getCmbPutnik().getSelectedItem();
            String statusPlacanja = (String) form.getCmbStatusPlacanja().getSelectedItem();
            String napomena = form.getTxtNapomena().getText().trim();

            Validator.startValidation()
                    .validateNotNull(agent, "Agent je obavezan.")
                    .validateNotNull(putnik, "Putnik je obavezan.")
                    .validateListIsNotEmpty(tableModel.getStavke(), "Rezervacija mora imati bar jednu stavku.")
                    .throwIfInvalide();

            rezervacija.setAgent(agent);
            rezervacija.setPutnik(putnik);
            rezervacija.setStatusPlacanja(statusPlacanja);
            rezervacija.setNapomena(napomena.isEmpty() ? null : napomena);
            rezervacija.setStavke(tableModel.getStavke());

            controller.Controller.getInstance().promeniRezervacija(rezervacija);
            JOptionPane.showMessageDialog(form, "Rezervacija je sačuvana.");
            form.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }
}
