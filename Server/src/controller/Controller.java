/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import configuration.Configuration;
import constant.MyServerConstants;
import domain.Agent;
import domain.Aranzman;
import domain.Mesto;
import domain.Putnik;
import domain.Region;
import domain.Rezervacija;
import java.util.List;
import threads.ServerThread;
import so.agent.PrijaviAgentSO;
import so.agent.VratiListuSviAgentSO;
import so.aranzman.VratiListuSviAranzmanSO;
import so.mesto.VratiListuSviMestoSO;
import so.putnik.KreirajPutnikSO;
import so.putnik.ObrisiPutnikSO;
import so.putnik.PretraziPutnikSO;
import so.putnik.PromeniPutnikSO;
import so.putnik.VratiListuPutnikKriterijumMestoSO;
import so.putnik.VratiListuPutnikKriterijumPutnikSO;
import so.putnik.VratiListuSviPutnikSO;
import so.region.UbaciRegionSO;
import so.rezervacija.KreirajRezervacijaSO;
import so.rezervacija.PretraziRezervacijaSO;
import so.rezervacija.PromeniRezervacijaSO;
import so.rezervacija.VratiListuRezervacijaKriterijumAgentSO;
import so.rezervacija.VratiListuRezervacijaKriterijumAranzmanSO;
import so.rezervacija.VratiListuRezervacijaKriterijumPutnikSO;
import so.rezervacija.VratiListuRezervacijaKriterijumRezervacijaSO;

/**
 *
 * @author mihajlo
 */
public class Controller {

    private static Controller instance;
    private ServerThread serverThread;

    private Controller() {
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void startServer() throws Exception {
        if (serverThread == null || !serverThread.isAlive()) {
            int port = Integer.parseInt(Configuration.getInstance().getServerProperty(MyServerConstants.SERVER_CONFIG_PORT));
            serverThread = new ServerThread(port);
            serverThread.start();
        }
    }

    public void stopServer() throws Exception {
        if (serverThread != null && serverThread.getServerSocket() != null
                && !serverThread.getServerSocket().isClosed()) {
            serverThread.getServerSocket().close();
        }
    }

    public boolean isServerRunning() {
        return serverThread != null && serverThread.isAlive();
    }

    public String getDbProperty(String key) {
        return Configuration.getInstance().getDbProperty(key);
    }

    public void setDbProperty(String key, String value) {
        Configuration.getInstance().setDbProperty(key, value);
    }

    public String getServerProperty(String key) {
        return Configuration.getInstance().getServerProperty(key);
    }

    public void setServerProperty(String key, String value) {
        Configuration.getInstance().setServerProperty(key, value);
    }

    public void sacuvajKonfiguraciju() throws Exception {
        Configuration.getInstance().sacuvajIzmene();
    }

    public Agent prijaviAgent(String korisnickoIme, String sifra) throws Exception {
        PrijaviAgentSO so = new PrijaviAgentSO();
        so.execute(new String[]{korisnickoIme, sifra});
        return so.getAgent();
    }

    public Rezervacija kreirajRezervacija(Rezervacija rezervacija) throws Exception {
        KreirajRezervacijaSO so = new KreirajRezervacijaSO();
        so.execute(rezervacija);
        return so.getRezervacija();
    }

    public Rezervacija promeniRezervacija(Rezervacija rezervacija) throws Exception {
        PromeniRezervacijaSO so = new PromeniRezervacijaSO();
        so.execute(rezervacija);
        return so.getRezervacija();
    }

    public Rezervacija pretraziRezervacija(Rezervacija kriterijum) throws Exception {
        PretraziRezervacijaSO so = new PretraziRezervacijaSO();
        so.execute(kriterijum);
        return so.getRezervacija();
    }

    public List<Rezervacija> vratiListuRezervacijaKriterijumRezervacija(Rezervacija kriterijum) throws Exception {
        VratiListuRezervacijaKriterijumRezervacijaSO so = new VratiListuRezervacijaKriterijumRezervacijaSO();
        so.execute(kriterijum);
        return so.getListaRezervacija();
    }

    public List<Rezervacija> vratiListuRezervacijaKriterijumAgent(Agent kriterijum) throws Exception {
        VratiListuRezervacijaKriterijumAgentSO so = new VratiListuRezervacijaKriterijumAgentSO();
        so.execute(kriterijum);
        return so.getListaRezervacija();
    }

    public List<Rezervacija> vratiListuRezervacijaKriterijumPutnik(Putnik kriterijum) throws Exception {
        VratiListuRezervacijaKriterijumPutnikSO so = new VratiListuRezervacijaKriterijumPutnikSO();
        so.execute(kriterijum);
        return so.getListaRezervacija();
    }

    public List<Rezervacija> vratiListuRezervacijaKriterijumAranzman(Aranzman kriterijum) throws Exception {
        VratiListuRezervacijaKriterijumAranzmanSO so = new VratiListuRezervacijaKriterijumAranzmanSO();
        so.execute(kriterijum);
        return so.getListaRezervacija();
    }

    public List<Agent> vratiListuSviAgent() throws Exception {
        VratiListuSviAgentSO so = new VratiListuSviAgentSO();
        so.execute(null);
        return so.getAgenti();
    }

    public List<Putnik> vratiListuSviPutnik() throws Exception {
        VratiListuSviPutnikSO so = new VratiListuSviPutnikSO();
        so.execute(null);
        return so.getPutnici();
    }

    public List<Aranzman> vratiListuSviAranzman() throws Exception {
        VratiListuSviAranzmanSO so = new VratiListuSviAranzmanSO();
        so.execute(null);
        return so.getAranzmani();
    }

    public List<Mesto> vratiListuSviMesto() throws Exception {
        VratiListuSviMestoSO so = new VratiListuSviMestoSO();
        so.execute(null);
        return so.getMesta();
    }

    public Putnik kreirajPutnik(Putnik putnik) throws Exception {
        KreirajPutnikSO so = new KreirajPutnikSO();
        so.execute(putnik);
        return so.getPutnik();
    }

    public Putnik promeniPutnik(Putnik putnik) throws Exception {
        PromeniPutnikSO so = new PromeniPutnikSO();
        so.execute(putnik);
        return so.getPutnik();
    }

    public void obrisiPutnik(Putnik putnik) throws Exception {
        ObrisiPutnikSO so = new ObrisiPutnikSO();
        so.execute(putnik);
    }

    public Putnik pretraziPutnik(Putnik kriterijum) throws Exception {
        PretraziPutnikSO so = new PretraziPutnikSO();
        so.execute(kriterijum);
        return so.getPutnik();
    }

    public List<Putnik> vratiListuPutnikKriterijumPutnik(Putnik kriterijum) throws Exception {
        VratiListuPutnikKriterijumPutnikSO so = new VratiListuPutnikKriterijumPutnikSO();
        so.execute(kriterijum);
        return so.getListaPutnika();
    }

    public List<Putnik> vratiListuPutnikKriterijumMesto(Mesto kriterijum) throws Exception {
        VratiListuPutnikKriterijumMestoSO so = new VratiListuPutnikKriterijumMestoSO();
        so.execute(kriterijum);
        return so.getListaPutnika();
    }

    public Region ubaciRegion(Region region) throws Exception {
        UbaciRegionSO so = new UbaciRegionSO();
        so.execute(region);
        return so.getRegion();
    }
}
