/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import client.communication.Communication;
import communication.Operations;
import communication.Request;
import communication.Response;
import communication.ResponseType;
import domain.Agent;
import domain.Aranzman;
import domain.Mesto;
import domain.Putnik;
import domain.Region;
import domain.Rezervacija;
import java.util.List;

/**
 *
 * @author mihajlo
 */
public class Controller {

    private static Controller instance;
    private Agent currentAgent;

    private Controller() {
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void setCurrentAgent(Agent agent) {
        this.currentAgent = agent;
    }

    public Agent getCurrentAgent() {
        return currentAgent;
    }

    public Agent prijaviAgent(String korisnickoIme, String sifra) throws Exception {
        Request request = new Request(Operations.PRIJAVI_AGENT, new String[]{korisnickoIme, sifra});
        Response response = Communication.getInstance().prijaviAgent(request);
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (Agent) response.getResult();
        }
        throw response.getException();
    }

    public Rezervacija kreirajRezervacija(Rezervacija rezervacija) throws Exception {
        Request request = new Request(Operations.KREIRAJ_REZERVACIJA, rezervacija);
        Response response = Communication.getInstance().kreirajRezervacija(request);
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (Rezervacija) response.getResult();
        }
        throw response.getException();
    }

    public Rezervacija promeniRezervacija(Rezervacija rezervacija) throws Exception {
        Request request = new Request(Operations.PROMENI_REZERVACIJA, rezervacija);
        Response response = Communication.getInstance().promeniRezervacija(request);
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (Rezervacija) response.getResult();
        }
        throw response.getException();
    }

    public Rezervacija pretraziRezervacija(Rezervacija kriterijum) throws Exception {
        Request request = new Request(Operations.PRETRAZI_REZERVACIJA, kriterijum);
        Response response = Communication.getInstance().pretraziRezervacija(request);
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (Rezervacija) response.getResult();
        }
        throw response.getException();
    }

    @SuppressWarnings("unchecked")
    public List<Rezervacija> vratiListuRezervacijaKriterijumRezervacija(Rezervacija kriterijum) throws Exception {
        Request request = new Request(Operations.VRATI_LISTU_REZERVACIJA_KRITERIJUM_REZERVACIJA, kriterijum);
        Response response = Communication.getInstance().vratiListuRezervacijaKriterijumRezervacija(request);
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Rezervacija>) response.getResult();
        }
        throw response.getException();
    }

    @SuppressWarnings("unchecked")
    public List<Rezervacija> vratiListuRezervacijaKriterijumAgent(Agent kriterijum) throws Exception {
        Request request = new Request(Operations.VRATI_LISTU_REZERVACIJA_KRITERIJUM_AGENT, kriterijum);
        Response response = Communication.getInstance().vratiListuRezervacijaKriterijumAgent(request);
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Rezervacija>) response.getResult();
        }
        throw response.getException();
    }

    @SuppressWarnings("unchecked")
    public List<Rezervacija> vratiListuRezervacijaKriterijumPutnik(Putnik kriterijum) throws Exception {
        Request request = new Request(Operations.VRATI_LISTU_REZERVACIJA_KRITERIJUM_PUTNIK, kriterijum);
        Response response = Communication.getInstance().vratiListuRezervacijaKriterijumPutnik(request);
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Rezervacija>) response.getResult();
        }
        throw response.getException();
    }

    @SuppressWarnings("unchecked")
    public List<Rezervacija> vratiListuRezervacijaKriterijumAranzman(Aranzman kriterijum) throws Exception {
        Request request = new Request(Operations.VRATI_LISTU_REZERVACIJA_KRITERIJUM_ARANZMAN, kriterijum);
        Response response = Communication.getInstance().vratiListuRezervacijaKriterijumAranzman(request);
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Rezervacija>) response.getResult();
        }
        throw response.getException();
    }

    @SuppressWarnings("unchecked")
    public List<Agent> vratiListuSviAgent() throws Exception {
        Request request = new Request(Operations.VRATI_LISTU_SVI_AGENT, null);
        Response response = Communication.getInstance().vratiListuSviAgent(request);
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Agent>) response.getResult();
        }
        throw response.getException();
    }

    @SuppressWarnings("unchecked")
    public List<Putnik> vratiListuSviPutnik() throws Exception {
        Request request = new Request(Operations.VRATI_LISTU_SVI_PUTNIK, null);
        Response response = Communication.getInstance().vratiListuSviPutnik(request);
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Putnik>) response.getResult();
        }
        throw response.getException();
    }

    @SuppressWarnings("unchecked")
    public List<Aranzman> vratiListuSviAranzman() throws Exception {
        Request request = new Request(Operations.VRATI_LISTU_SVI_ARANZMAN, null);
        Response response = Communication.getInstance().vratiListuSviAranzman(request);
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Aranzman>) response.getResult();
        }
        throw response.getException();
    }

    @SuppressWarnings("unchecked")
    public List<Mesto> vratiListuSviMesto() throws Exception {
        Request request = new Request(Operations.VRATI_LISTU_SVI_MESTO, null);
        Response response = Communication.getInstance().vratiListuSviMesto(request);
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Mesto>) response.getResult();
        }
        throw response.getException();
    }

    public Putnik kreirajPutnik(Putnik putnik) throws Exception {
        Request request = new Request(Operations.KREIRAJ_PUTNIK, putnik);
        Response response = Communication.getInstance().kreirajPutnik(request);
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (Putnik) response.getResult();
        }
        throw response.getException();
    }

    public Putnik promeniPutnik(Putnik putnik) throws Exception {
        Request request = new Request(Operations.PROMENI_PUTNIK, putnik);
        Response response = Communication.getInstance().promeniPutnik(request);
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (Putnik) response.getResult();
        }
        throw response.getException();
    }

    public void obrisiPutnik(Putnik putnik) throws Exception {
        Request request = new Request(Operations.OBRISI_PUTNIK, putnik);
        Response response = Communication.getInstance().obrisiPutnik(request);
        if (!response.getResponseType().equals(ResponseType.SUCCESS)) {
            throw response.getException();
        }
    }

    public Putnik pretraziPutnik(Putnik kriterijum) throws Exception {
        Request request = new Request(Operations.PRETRAZI_PUTNIK, kriterijum);
        Response response = Communication.getInstance().pretraziPutnik(request);
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (Putnik) response.getResult();
        }
        throw response.getException();
    }

    @SuppressWarnings("unchecked")
    public List<Putnik> vratiListuPutnikKriterijumPutnik(Putnik kriterijum) throws Exception {
        Request request = new Request(Operations.VRATI_LISTU_PUTNIK_KRITERIJUM_PUTNIK, kriterijum);
        Response response = Communication.getInstance().vratiListuPutnikKriterijumPutnik(request);
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Putnik>) response.getResult();
        }
        throw response.getException();
    }

    @SuppressWarnings("unchecked")
    public List<Putnik> vratiListuPutnikKriterijumMesto(Mesto kriterijum) throws Exception {
        Request request = new Request(Operations.VRATI_LISTU_PUTNIK_KRITERIJUM_MESTO, kriterijum);
        Response response = Communication.getInstance().vratiListuPutnikKriterijumMesto(request);
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Putnik>) response.getResult();
        }
        throw response.getException();
    }

    public Region ubaciRegion(Region region) throws Exception {
        Request request = new Request(Operations.UBACI_REGION, region);
        Response response = Communication.getInstance().ubaciRegion(request);
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (Region) response.getResult();
        }
        throw response.getException();
    }
}
