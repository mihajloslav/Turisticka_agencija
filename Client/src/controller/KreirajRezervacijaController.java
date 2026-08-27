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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JOptionPane;
import validation.Validator;
import view.components.TableModelStavkaRezervacije;
import view.form.KreirajRezervacijaForm;

/**
 *
 * @author mihajlo
 */
public class KreirajRezervacijaController {

    private final KreirajRezervacijaForm form;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT);
    private final TableModelStavkaRezervacije tableModel = new TableModelStavkaRezervacije(new ArrayList<>());

    public KreirajRezervacijaController(KreirajRezervacijaForm form) {
        this.form = form;
        form.getTblStavke().setModel(tableModel);
        ucitajListe();
        addActionListeners();
        azurirajUkupanIznos();
    }

    public void otvoriFormu() {
        form.setVisible(true);
    }

    private void ucitajListe() {
        try {
            Agent trenutni = Coordinator.getInstance().getCurrentAgent();
            form.getCmbAgent().setModel(new DefaultComboBoxModel<>(new Agent[]{trenutni}));
            form.getCmbAgent().setRenderer(new DefaultListCellRenderer() {
                @Override
                public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value,
                        int index, boolean isSelected, boolean cellHasFocus) {
                    String text = value instanceof Agent ? ((Agent) value).getIme() + " " + ((Agent) value).getPrezime() : "";
                    return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
                }
            });
            form.getCmbAgent().setSelectedItem(trenutni);

            List<Putnik> putnici = Communication.getInstance().vratiListuSviPutnik();
            form.getCmbPutnik().setModel(new DefaultComboBoxModel<>(putnici.toArray(new Putnik[0])));
            form.getCmbPutnik().setRenderer(new DefaultListCellRenderer() {
                @Override
                public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value,
                        int index, boolean isSelected, boolean cellHasFocus) {
                    String text = value instanceof Putnik ? ((Putnik) value).getIme() + " " + ((Putnik) value).getPrezime() : "";
                    return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
                }
            });

            List<Aranzman> aranzmani = Communication.getInstance().vratiListuSviAranzman();
            form.getCmbAranzman().setModel(new DefaultComboBoxModel<>(aranzmani.toArray(new Aranzman[0])));
            form.getCmbAranzman().setRenderer(new DefaultListCellRenderer() {
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
                    .validateNotNull(aranzman, "Аранжман је обавезан.")
                    .validateValueIsNumber(brojOsobaText, "Број особа мора бити број.")
                    .validateValueIsDate(datumPolaskaText, "dd.MM.uuuu", "Датум поласка није исправан.")
                    .validateValueIsDate(datumDolaskaText, "dd.MM.uuuu", "Датум доласка није исправан.")
                    .throwIfInvalide();

            int brojOsoba = Integer.parseInt(brojOsobaText);
            if (brojOsoba <= 0) {
                throw new validation.ValidationException("Број особа мора бити већи од 0.");
            }
            LocalDate datumPolaska = LocalDate.parse(datumPolaskaText, dtf);
            LocalDate datumDolaska = LocalDate.parse(datumDolaskaText, dtf);
            if (!datumDolaska.isAfter(datumPolaska)) {
                throw new validation.ValidationException("Датум доласка мора бити после датума поласка.");
            }

            double popust = brojOsoba >= 3 ? 0.1 : 0.0;
            double cena = brojOsoba * aranzman.getCenaPoOsobi() * (1 - popust);

            StavkaRezervacije stavka = new StavkaRezervacije();
            stavka.setRb(tableModel.getStavke().size() + 1);
            stavka.setAranzman(aranzman);
            stavka.setBrojOsoba(brojOsoba);
            stavka.setDatumPolaska(datumPolaska);
            stavka.setDatumDolaska(datumDolaska);
            stavka.setPopust(popust);
            stavka.setCena(cena);
            tableModel.dodajStavku(stavka);
            azurirajUkupanIznos();

            form.getTxtBrojOsoba().setText("");
            form.getTxtDatumPolaska().setText("");
            form.getTxtDatumDolaska().setText("");
        } catch (validation.ValidationException vex) {
            JOptionPane.showMessageDialog(form, vex.getMessage(), "Упозорење", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ukloniStavku() {
        int red = form.getTblStavke().getSelectedRow();
        if (red < 0) {
            JOptionPane.showMessageDialog(form, "Изаберите ставку из листе.", "Упозорење", JOptionPane.WARNING_MESSAGE);
            return;
        }
        tableModel.ukloniStavku(red);
        renumerisiStavke();
        tableModel.fireTableDataChanged();
        azurirajUkupanIznos();
    }

    private void renumerisiStavke() {
        List<StavkaRezervacije> stavke = tableModel.getStavke();
        for (int i = 0; i < stavke.size(); i++) {
            stavke.get(i).setRb(i + 1);
        }
    }

    private void azurirajUkupanIznos() {
        double ukupno = 0;
        for (StavkaRezervacije s : tableModel.getStavke()) {
            if (s.getCena() != null) {
                ukupno += s.getCena();
            }
        }
        form.getTxtUkupanIznos().setText(String.format(java.util.Locale.US, "%.2f", ukupno));
    }

    private void sacuvajRezervaciju() {
        try {
            Agent agent = (Agent) form.getCmbAgent().getSelectedItem();
            Putnik putnik = (Putnik) form.getCmbPutnik().getSelectedItem();
            String statusPlacanja = (String) form.getCmbStatusPlacanja().getSelectedItem();
            String napomena = form.getTxtNapomena().getText().trim();

            Validator.startValidation()
                    .validateNotNull(agent, "Агент је обавезан.")
                    .validateNotNull(putnik, "Путник је обавезан.")
                    .validateListIsNotEmpty(tableModel.getStavke(), "Резервација мора имати бар једну ставку.")
                    .throwIfInvalide();

            Rezervacija rezervacija = new Rezervacija();
            rezervacija.setAgent(agent);
            rezervacija.setPutnik(putnik);
            rezervacija.setStatusPlacanja(statusPlacanja);
            rezervacija.setNapomena(napomena.isEmpty() ? null : napomena);
            rezervacija.setStavke(tableModel.getStavke());

            Communication.getInstance().kreirajRezervacija(rezervacija);
            JOptionPane.showMessageDialog(form, "Систем је креирао резервацију", "Успех", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(form, "Систем је запамтио резервацију.", "Успех", JOptionPane.INFORMATION_MESSAGE);
            form.dispose();
        } catch (validation.ValidationException vex) {
            JOptionPane.showMessageDialog(form, vex.getMessage(), "Упозорење", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
