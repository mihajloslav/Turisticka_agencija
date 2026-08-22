/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client.communication;

import communication.Receiver;
import communication.Request;
import communication.Response;
import communication.Sender;
import java.net.Socket;

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

    public Response prijaviAgent(Request request) throws Exception {
        new Sender(socket).send(request);
        return (Response) new Receiver(socket).receive();
    }

    public Response kreirajRezervacija(Request request) throws Exception {
        new Sender(socket).send(request);
        return (Response) new Receiver(socket).receive();
    }

    public Response promeniRezervacija(Request request) throws Exception {
        new Sender(socket).send(request);
        return (Response) new Receiver(socket).receive();
    }

    public Response pretraziRezervacija(Request request) throws Exception {
        new Sender(socket).send(request);
        return (Response) new Receiver(socket).receive();
    }

    public Response vratiListuRezervacijaKriterijumRezervacija(Request request) throws Exception {
        new Sender(socket).send(request);
        return (Response) new Receiver(socket).receive();
    }

    public Response vratiListuRezervacijaKriterijumAgent(Request request) throws Exception {
        new Sender(socket).send(request);
        return (Response) new Receiver(socket).receive();
    }

    public Response vratiListuRezervacijaKriterijumPutnik(Request request) throws Exception {
        new Sender(socket).send(request);
        return (Response) new Receiver(socket).receive();
    }

    public Response vratiListuRezervacijaKriterijumAranzman(Request request) throws Exception {
        new Sender(socket).send(request);
        return (Response) new Receiver(socket).receive();
    }

    public Response vratiListuSviAgent(Request request) throws Exception {
        new Sender(socket).send(request);
        return (Response) new Receiver(socket).receive();
    }

    public Response vratiListuSviPutnik(Request request) throws Exception {
        new Sender(socket).send(request);
        return (Response) new Receiver(socket).receive();
    }

    public Response vratiListuSviAranzman(Request request) throws Exception {
        new Sender(socket).send(request);
        return (Response) new Receiver(socket).receive();
    }

    public Response vratiListuSviMesto(Request request) throws Exception {
        new Sender(socket).send(request);
        return (Response) new Receiver(socket).receive();
    }

    public Response kreirajPutnik(Request request) throws Exception {
        new Sender(socket).send(request);
        return (Response) new Receiver(socket).receive();
    }

    public Response promeniPutnik(Request request) throws Exception {
        new Sender(socket).send(request);
        return (Response) new Receiver(socket).receive();
    }

    public Response obrisiPutnik(Request request) throws Exception {
        new Sender(socket).send(request);
        return (Response) new Receiver(socket).receive();
    }

    public Response pretraziPutnik(Request request) throws Exception {
        new Sender(socket).send(request);
        return (Response) new Receiver(socket).receive();
    }

    public Response vratiListuPutnikKriterijumPutnik(Request request) throws Exception {
        new Sender(socket).send(request);
        return (Response) new Receiver(socket).receive();
    }

    public Response vratiListuPutnikKriterijumMesto(Request request) throws Exception {
        new Sender(socket).send(request);
        return (Response) new Receiver(socket).receive();
    }

    public Response ubaciRegion(Request request) throws Exception {
        new Sender(socket).send(request);
        return (Response) new Receiver(socket).receive();
    }
}
