/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import client.communication.Communication;
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
import view.form.PromeniPutnikForm;

/**
 *
 * @author mihajlo
 */
public class PromeniPutnikController {

    private static final String TELEFON_REGEX = "^\\+[0-9]{12}$";

    private final PromeniPutnikForm form;
    private final Putnik putnik;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT);

    public PromeniPutnikController(PromeniPutnikForm form, Putnik putnik) {
        this.form = form;
        this.putnik = putnik;
        ucitajMesta();
        popuniPolja();
        addActionListeners();
    }

    public void otvoriFormu() {
        form.setVisible(true);
    }

    private void ucitajMesta() {
        try {
            List<Mesto> mesta = Communication.getInstance().vratiListuSviMesto();
            form.getCmbMesto().setModel(new DefaultComboBoxModel<>(mesta.toArray(new Mesto[0])));
            form.getCmbMesto().setRenderer(new DefaultListCellRenderer() {
                @Override
                public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value,
                        int index, boolean isSelected, boolean cellHasFocus) {
                    String text = value instanceof Mesto ? ((Mesto) value).getNaziv() : "";
                    return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
                }
            });
            for (Mesto m : mesta) {
                if (m.getIdMesto().equals(putnik.getMesto().getIdMesto())) {
                    form.getCmbMesto().setSelectedItem(m);
                    break;
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void popuniPolja() {
        form.getTxtIme().setText(putnik.getIme());
        form.getTxtPrezime().setText(putnik.getPrezime());
        form.getTxtEmail().setText(putnik.getEmail());
        form.getTxtTelefon().setText(putnik.getTelefon());
        form.getTxtJmbg().setText(putnik.getJmbg());
        form.getTxtBrojPasosa().setText(putnik.getBrojPasosa());
        if (putnik.getDatumRodjenja() != null) {
            form.getTxtDatumRodjenja().setText(putnik.getDatumRodjenja().format(dtf));
        }
    }

    private void addActionListeners() {
        form.getBtnSacuvaj().addActionListener(evt -> sacuvaj());
    }

    private void sacuvaj() {
        try {
            String ime = form.getTxtIme().getText().trim();
            String prezime = form.getTxtPrezime().getText().trim();
            String email = form.getTxtEmail().getText().trim();
            String telefon = form.getTxtTelefon().getText().trim();
            String jmbg = form.getTxtJmbg().getText().trim();
            String brojPasosa = form.getTxtBrojPasosa().getText().trim();
            Mesto mesto = (Mesto) form.getCmbMesto().getSelectedItem();
            String datumRodjenjaText = form.getTxtDatumRodjenja().getText().trim();

            Validator.startValidation()
                    .validateNotNullOrEmpty(ime, "Име је обавезно.")
                    .validateNotNullOrEmpty(prezime, "Презиме је обавезно.")
                    .validateNotNullOrEmpty(email, "Имејл је обавезан.")
                    .validateNotNullOrEmpty(telefon, "Телефон је обавезан.")
                    .validateNotNullOrEmpty(jmbg, "ЈМБГ је обавезан.")
                    .validateNotNullOrEmpty(brojPasosa, "Број пасоша је обавезан.")
                    .validateNotNull(mesto, "Место је обавезно.")
                    .validateValueIsDate(datumRodjenjaText, "dd.MM.uuuu", "Датум рођења није исправан.")
                    .throwIfInvalide();

            if (!email.contains("@")) {
                throw new validation.ValidationException("Имејл мора садржати знак '@'.");
            }
            if (jmbg.length() != 13) {
                throw new validation.ValidationException("ЈМБГ мора имати тачно 13 карактера.");
            }
            if (!telefon.matches(TELEFON_REGEX)) {
                throw new validation.ValidationException("Телефон мора бити у формату +381123456789 (+ и тачно 12 цифара).");
            }
            LocalDate datumRodjenja = LocalDate.parse(datumRodjenjaText, dtf);

            putnik.setIme(ime);
            putnik.setPrezime(prezime);
            putnik.setEmail(email);
            putnik.setTelefon(telefon);
            putnik.setJmbg(jmbg);
            putnik.setBrojPasosa(brojPasosa);
            putnik.setMesto(mesto);
            putnik.setDatumRodjenja(datumRodjenja);

            Communication.getInstance().promeniPutnik(putnik);
            JOptionPane.showMessageDialog(form, "Систем је запамтио путника.", "Успех", JOptionPane.INFORMATION_MESSAGE);
            form.dispose();
        } catch (validation.ValidationException vex) {
            JOptionPane.showMessageDialog(form, vex.getMessage(), "Упозорење", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, ex.getMessage(), "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
