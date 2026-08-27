/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client.communication;

import communication.Operations;
import communication.Receiver;
import communication.Request;
import communication.Response;
import communication.ResponseType;
import communication.Sender;
import domain.Agent;
import domain.Aranzman;
import domain.Mesto;
import domain.Putnik;
import domain.Region;
import domain.Rezervacija;
import java.net.Socket;
import java.util.List;

/**
 *
 * @author mihajlo
 */
public class Communication {

    private static Communication instance;
    private Socket socket;

    private Communication() {
    }

    public static Communication getInstance() {
        if (instance == null) {
            instance = new Communication();
        }
        return instance;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Agent prijaviAgent(String korisnickoIme, String sifra) throws Exception {
        Request request = new Request(Operations.PRIJAVI_AGENT, new String[]{korisnickoIme, sifra});
        new Sender(socket).send(request);
        Response response = (Response) new Receiver(socket).receive();
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (Agent) response.getResult();
        }
        throw response.getException();
    }

    public Rezervacija kreirajRezervacija(Rezervacija rezervacija) throws Exception {
        Request request = new Request(Operations.KREIRAJ_REZERVACIJA, rezervacija);
        new Sender(socket).send(request);
        Response response = (Response) new Receiver(socket).receive();
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (Rezervacija) response.getResult();
        }
        throw response.getException();
    }

    public Rezervacija promeniRezervacija(Rezervacija rezervacija) throws Exception {
        Request request = new Request(Operations.PROMENI_REZERVACIJA, rezervacija);
        new Sender(socket).send(request);
        Response response = (Response) new Receiver(socket).receive();
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (Rezervacija) response.getResult();
        }
        throw response.getException();
    }

    public Rezervacija pretraziRezervacija(Rezervacija kriterijum) throws Exception {
        Request request = new Request(Operations.PRETRAZI_REZERVACIJA, kriterijum);
        new Sender(socket).send(request);
        Response response = (Response) new Receiver(socket).receive();
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (Rezervacija) response.getResult();
        }
        throw response.getException();
    }

    @SuppressWarnings("unchecked")
    public List<Rezervacija> vratiListuRezervacija(Rezervacija kriterijum) throws Exception {
        Request request = new Request(Operations.VRATI_LISTU_REZERVACIJA, kriterijum);
        new Sender(socket).send(request);
        Response response = (Response) new Receiver(socket).receive();
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Rezervacija>) response.getResult();
        }
        throw response.getException();
    }

    /*@SuppressWarnings("unchecked")
    public List<Rezervacija> vratiListuRezervacijaKriterijumAgent(Agent kriterijum) throws Exception {
        Request request = new Request(Operations.VRATI_LISTU_REZERVACIJA_KRITERIJUM_AGENT, kriterijum);
        new Sender(socket).send(request);
        Response response = (Response) new Receiver(socket).receive();
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Rezervacija>) response.getResult();
        }
        throw response.getException();
    }

    @SuppressWarnings("unchecked")
    public List<Rezervacija> vratiListuRezervacijaKriterijumPutnik(Putnik kriterijum) throws Exception {
        Request request = new Request(Operations.VRATI_LISTU_REZERVACIJA_KRITERIJUM_PUTNIK, kriterijum);
        new Sender(socket).send(request);
        Response response = (Response) new Receiver(socket).receive();
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Rezervacija>) response.getResult();
        }
        throw response.getException();
    }

    @SuppressWarnings("unchecked")
    public List<Rezervacija> vratiListuRezervacijaKriterijumAranzman(Aranzman kriterijum) throws Exception {
        Request request = new Request(Operations.VRATI_LISTU_REZERVACIJA_KRITERIJUM_ARANZMAN, kriterijum);
        new Sender(socket).send(request);
        Response response = (Response) new Receiver(socket).receive();
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Rezervacija>) response.getResult();
        }
        throw response.getException();
    }*/

    @SuppressWarnings("unchecked")
    public List<Agent> vratiListuSviAgent() throws Exception {
        Request request = new Request(Operations.VRATI_LISTU_SVI_AGENT, null);
        new Sender(socket).send(request);
        Response response = (Response) new Receiver(socket).receive();
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Agent>) response.getResult();
        }
        throw response.getException();
    }

    @SuppressWarnings("unchecked")
    public List<Putnik> vratiListuSviPutnik() throws Exception {
        Request request = new Request(Operations.VRATI_LISTU_SVI_PUTNIK, null);
        new Sender(socket).send(request);
        Response response = (Response) new Receiver(socket).receive();
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Putnik>) response.getResult();
        }
        throw response.getException();
    }

    @SuppressWarnings("unchecked")
    public List<Aranzman> vratiListuSviAranzman() throws Exception {
        Request request = new Request(Operations.VRATI_LISTU_SVI_ARANZMAN, null);
        new Sender(socket).send(request);
        Response response = (Response) new Receiver(socket).receive();
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Aranzman>) response.getResult();
        }
        throw response.getException();
    }

    @SuppressWarnings("unchecked")
    public List<Mesto> vratiListuSviMesto() throws Exception {
        Request request = new Request(Operations.VRATI_LISTU_SVI_MESTO, null);
        new Sender(socket).send(request);
        Response response = (Response) new Receiver(socket).receive();
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Mesto>) response.getResult();
        }
        throw response.getException();
    }

    public Putnik kreirajPutnik(Putnik putnik) throws Exception {
        Request request = new Request(Operations.KREIRAJ_PUTNIK, putnik);
        new Sender(socket).send(request);
        Response response = (Response) new Receiver(socket).receive();
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (Putnik) response.getResult();
        }
        throw response.getException();
    }

    public Putnik promeniPutnik(Putnik putnik) throws Exception {
        Request request = new Request(Operations.PROMENI_PUTNIK, putnik);
        new Sender(socket).send(request);
        Response response = (Response) new Receiver(socket).receive();
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (Putnik) response.getResult();
        }
        throw response.getException();
    }

    public void obrisiPutnik(Putnik putnik) throws Exception {
        Request request = new Request(Operations.OBRISI_PUTNIK, putnik);
        new Sender(socket).send(request);
        Response response = (Response) new Receiver(socket).receive();
        if (!response.getResponseType().equals(ResponseType.SUCCESS)) {
            throw response.getException();
        }
    }

    public Putnik pretraziPutnik(Putnik kriterijum) throws Exception {
        Request request = new Request(Operations.PRETRAZI_PUTNIK, kriterijum);
        new Sender(socket).send(request);
        Response response = (Response) new Receiver(socket).receive();
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (Putnik) response.getResult();
        }
        throw response.getException();
    }

    @SuppressWarnings("unchecked")
    public List<Putnik> vratiListuPutnikKriterijumPutnik(Putnik kriterijum) throws Exception {
        Request request = new Request(Operations.VRATI_LISTU_PUTNIK_KRITERIJUM_PUTNIK, kriterijum);
        new Sender(socket).send(request);
        Response response = (Response) new Receiver(socket).receive();
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Putnik>) response.getResult();
        }
        throw response.getException();
    }

    @SuppressWarnings("unchecked")
    public List<Putnik> vratiListuPutnikKriterijumMesto(Mesto kriterijum) throws Exception {
        Request request = new Request(Operations.VRATI_LISTU_PUTNIK_KRITERIJUM_MESTO, kriterijum);
        new Sender(socket).send(request);
        Response response = (Response) new Receiver(socket).receive();
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Putnik>) response.getResult();
        }
        throw response.getException();
    }

    public Region ubaciRegion(Region region) throws Exception {
        Request request = new Request(Operations.UBACI_REGION, region);
        new Sender(socket).send(request);
        Response response = (Response) new Receiver(socket).receive();
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (Region) response.getResult();
        }
        throw response.getException();
    }
}
