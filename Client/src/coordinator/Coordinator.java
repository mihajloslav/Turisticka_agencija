/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coordinator;

import controller.KreirajPutnikController;
import controller.KreirajRezervacijaController;
import controller.MainController;
import controller.ObrisiPutnikController;
import controller.OProgramuController;
import controller.PretraziPutnikController;
import controller.PretraziRezervacijaController;
import controller.PrijaviAgentController;
import controller.PrikaziPutnikController;
import controller.PromeniPutnikController;
import controller.PromeniRezervacijaController;
import controller.UbaciRegionController;
import domain.Agent;
import domain.Putnik;
import domain.Rezervacija;
import view.form.KreirajPutnikForm;
import view.form.KreirajRezervacijaForm;
import view.form.MainForm;
import view.form.ObrisiPutnikForm;
import view.form.OProgramuForm;
import view.form.PretraziPutnikForm;
import view.form.PretraziRezervacijaForm;
import view.form.PrijaviAgentForm;
import view.form.PrikaziPutnikForm;
import view.form.PromeniPutnikForm;
import view.form.PromeniRezervacijaForm;
import view.form.UbaciRegionForm;

/**
 *
 * @author mihajlo
 */
public class Coordinator {

    private static Coordinator instance;
    private Agent currentAgent;

    private PrijaviAgentController prijaviAgentController;
    private MainController mainController;
    private KreirajRezervacijaController kreirajRezervacijaController;
    private PretraziRezervacijaController pretraziRezervacijaController;
    private PromeniRezervacijaController promeniRezervacijaController;
    private KreirajPutnikController kreirajPutnikController;
    private PretraziPutnikController pretraziPutnikController;
    private PromeniPutnikController promeniPutnikController;
    private ObrisiPutnikController obrisiPutnikController;
    private PrikaziPutnikController prikaziPutnikController;
    private UbaciRegionController ubaciRegionController;
    private OProgramuController oProgramuController;

    private Coordinator() {
    }

    public static Coordinator getInstance() {
        if (instance == null) {
            instance = new Coordinator();
        }
        return instance;
    }

    public Agent getCurrentAgent() {
        return currentAgent;
    }

    public void setCurrentAgent(Agent currentAgent) {
        this.currentAgent = currentAgent;
    }

    public void otvoriLoginFormu() {
        prijaviAgentController = new PrijaviAgentController(new PrijaviAgentForm());
        prijaviAgentController.otvoriFormu();
    }

    public void otvoriGlavnuFormu() {
        mainController = new MainController(new MainForm());
        mainController.otvoriFormu();
    }

    public void otvoriKreirajRezervacijaFormu() {
        kreirajRezervacijaController = new KreirajRezervacijaController(new KreirajRezervacijaForm());
        kreirajRezervacijaController.otvoriFormu();
    }

    public void otvoriPretraziRezervacijaFormu() {
        pretraziRezervacijaController = new PretraziRezervacijaController(new PretraziRezervacijaForm());
        pretraziRezervacijaController.otvoriFormu();
    }

    public void otvoriPromeniRezervacijaFormu(Rezervacija rezervacija) {
        promeniRezervacijaController = new PromeniRezervacijaController(new PromeniRezervacijaForm(rezervacija), rezervacija);
        promeniRezervacijaController.otvoriFormu();
    }

    public void otvoriKreirajPutnikFormu() {
        kreirajPutnikController = new KreirajPutnikController(new KreirajPutnikForm());
        kreirajPutnikController.otvoriFormu();
    }

    public void otvoriPretraziPutnikFormu() {
        pretraziPutnikController = new PretraziPutnikController(new PretraziPutnikForm());
        pretraziPutnikController.otvoriFormu();
    }

    public void otvoriPromeniPutnikFormu(Putnik putnik) {
        promeniPutnikController = new PromeniPutnikController(new PromeniPutnikForm(putnik), putnik);
        promeniPutnikController.otvoriFormu();
    }

    public void otvoriObrisiPutnikFormu(Putnik putnik) {
        obrisiPutnikController = new ObrisiPutnikController(new ObrisiPutnikForm(putnik), putnik);
        obrisiPutnikController.otvoriFormu();
    }

    public void otvoriPrikaziPutnikFormu(Putnik putnik) {
        prikaziPutnikController = new PrikaziPutnikController(new PrikaziPutnikForm(putnik), putnik);
        prikaziPutnikController.otvoriFormu();
    }

    public void otvoriUbaciRegionFormu() {
        ubaciRegionController = new UbaciRegionController(new UbaciRegionForm());
        ubaciRegionController.otvoriFormu();
    }

    public void otvoriOProgramuFormu() {
        oProgramuController = new OProgramuController(new OProgramuForm());
        oProgramuController.otvoriFormu();
    }
}
