/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threads;

import communication.Operations;
import communication.Receiver;
import communication.Request;
import communication.Response;
import communication.ResponseType;
import communication.Sender;
import controller.Controller;
import domain.Agent;
import domain.Aranzman;
import domain.Mesto;
import domain.Putnik;
import domain.Region;
import domain.Rezervacija;
import java.net.Socket;

/**
 *
 * @author mihajlo
 */
public class HandleClientThread extends Thread {

    private Socket socket;
    private ServerListener listener;
    private Agent prijavljeniAgent;

    public HandleClientThread(Socket socket, ServerListener listener) {
        this.socket = socket;
        this.listener = listener;
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                try {
                    Request request = (Request) new Receiver(socket).receive();
                    if (listener != null) {
                        listener.onLog("Zahtev [" + socket.getInetAddress().getHostAddress() + ":" + socket.getPort()
                                + "]: " + nazivOperacije(request.getOperation()));
                    }
                    Response response = handleRequest(request);
                    new Sender(socket).send(response);
                } catch (Exception ex) {
                    try {
                        socket.close();
                    } catch (Exception closeEx) {
                    }
                    break;
                }
            }
        } finally {
            if (prijavljeniAgent != null) {
                Controller.getInstance().odjaviAgenta(prijavljeniAgent.getIdAgent());
            }
            if (listener != null) {
                listener.onLog("Klijent diskonektovan: " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
                listener.onKlijentOdjavljen(this);
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public Agent getPrijavljeniAgent() {
        return prijavljeniAgent;
    }

    private String nazivOperacije(int operacija) {
        switch (operacija) {
            case Operations.PRIJAVI_AGENT:
                return "Prijavi agenta";
            case Operations.KREIRAJ_REZERVACIJA:
                return "Kreiraj rezervaciju";
            case Operations.PROMENI_REZERVACIJA:
                return "Promeni rezervaciju";
            case Operations.PRETRAZI_REZERVACIJA:
                return "Pretraži rezervaciju";
            case Operations.VRATI_LISTU_REZERVACIJA_KRITERIJUM_REZERVACIJA:
            case Operations.VRATI_LISTU_REZERVACIJA_KRITERIJUM_AGENT:
            case Operations.VRATI_LISTU_REZERVACIJA_KRITERIJUM_PUTNIK:
            case Operations.VRATI_LISTU_REZERVACIJA_KRITERIJUM_ARANZMAN:
                return "Vrati listu rezervacija";
            case Operations.VRATI_LISTU_SVI_AGENT:
                return "Vrati listu svih agenata";
            case Operations.VRATI_LISTU_SVI_PUTNIK:
                return "Vrati listu svih putnika";
            case Operations.VRATI_LISTU_SVI_ARANZMAN:
                return "Vrati listu svih aranžmana";
            case Operations.VRATI_LISTU_SVI_MESTO:
                return "Vrati listu svih mesta";
            case Operations.KREIRAJ_PUTNIK:
                return "Kreiraj putnika";
            case Operations.PROMENI_PUTNIK:
                return "Promeni putnika";
            case Operations.OBRISI_PUTNIK:
                return "Obriši putnika";
            case Operations.PRETRAZI_PUTNIK:
                return "Pretraži putnika";
            case Operations.VRATI_LISTU_PUTNIK_KRITERIJUM_PUTNIK:
            case Operations.VRATI_LISTU_PUTNIK_KRITERIJUM_MESTO:
                return "Vrati listu putnika";
            case Operations.UBACI_REGION:
                return "Ubaci region";
            default:
                return "Nepoznata operacija (#" + operacija + ")";
        }
    }

    private Response handleRequest(Request request) {
        switch (request.getOperation()) {
            case Operations.PRIJAVI_AGENT:
                return prijaviAgent(request);
            case Operations.KREIRAJ_REZERVACIJA:
                return kreirajRezervacija(request);
            case Operations.PROMENI_REZERVACIJA:
                return promeniRezervacija(request);
            case Operations.PRETRAZI_REZERVACIJA:
                return pretraziRezervacija(request);
            case Operations.VRATI_LISTU_REZERVACIJA_KRITERIJUM_REZERVACIJA:
                return vratiListuRezervacijaKriterijumRezervacija(request);
            case Operations.VRATI_LISTU_REZERVACIJA_KRITERIJUM_AGENT:
                return vratiListuRezervacijaKriterijumAgent(request);
            case Operations.VRATI_LISTU_REZERVACIJA_KRITERIJUM_PUTNIK:
                return vratiListuRezervacijaKriterijumPutnik(request);
            case Operations.VRATI_LISTU_REZERVACIJA_KRITERIJUM_ARANZMAN:
                return vratiListuRezervacijaKriterijumAranzman(request);
            case Operations.VRATI_LISTU_SVI_AGENT:
                return vratiListuSviAgent(request);
            case Operations.VRATI_LISTU_SVI_PUTNIK:
                return vratiListuSviPutnik(request);
            case Operations.VRATI_LISTU_SVI_ARANZMAN:
                return vratiListuSviAranzman(request);
            case Operations.VRATI_LISTU_SVI_MESTO:
                return vratiListuSviMesto(request);
            case Operations.KREIRAJ_PUTNIK:
                return kreirajPutnik(request);
            case Operations.PROMENI_PUTNIK:
                return promeniPutnik(request);
            case Operations.OBRISI_PUTNIK:
                return obrisiPutnik(request);
            case Operations.PRETRAZI_PUTNIK:
                return pretraziPutnik(request);
            case Operations.VRATI_LISTU_PUTNIK_KRITERIJUM_PUTNIK:
                return vratiListuPutnikKriterijumPutnik(request);
            case Operations.VRATI_LISTU_PUTNIK_KRITERIJUM_MESTO:
                return vratiListuPutnikKriterijumMesto(request);
            case Operations.UBACI_REGION:
                return ubaciRegion(request);
            default:
                return null;
        }
    }

    private Response prijaviAgent(Request request) {
        Response response = new Response();
        String[] credentials = (String[]) request.getArgument();
        try {
            Agent agent = Controller.getInstance().prijaviAgent(credentials[0], credentials[1]);
            prijavljeniAgent = agent;
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(agent);
            if (listener != null) {
                listener.onLog("Uspešna prijava agenta: " + credentials[0]);
                listener.onKlijentAzuriran(this);
            }
        } catch (Exception ex) {
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
            if (listener != null) {
                listener.onLog("Odbijena prijava agenta: " + credentials[0] + " (" + ex.getMessage() + ")");
            }
        }
        return response;
    }

    private Response kreirajRezervacija(Request request) {
        Response response = new Response();
        Rezervacija rezervacija = (Rezervacija) request.getArgument();
        try {
            Rezervacija sacuvana = Controller.getInstance().kreirajRezervacija(rezervacija);
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(sacuvana);
        } catch (Exception ex) {
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response promeniRezervacija(Request request) {
        Response response = new Response();
        Rezervacija rezervacija = (Rezervacija) request.getArgument();
        try {
            Rezervacija sacuvana = Controller.getInstance().promeniRezervacija(rezervacija);
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(sacuvana);
        } catch (Exception ex) {
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response pretraziRezervacija(Request request) {
        Response response = new Response();
        Rezervacija kriterijum = (Rezervacija) request.getArgument();
        try {
            Rezervacija rezervacija = Controller.getInstance().pretraziRezervacija(kriterijum);
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(rezervacija);
        } catch (Exception ex) {
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response vratiListuRezervacijaKriterijumRezervacija(Request request) {
        Response response = new Response();
        Rezervacija kriterijum = (Rezervacija) request.getArgument();
        try {
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(Controller.getInstance().vratiListuRezervacijaKriterijumRezervacija(kriterijum));
        } catch (Exception ex) {
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response vratiListuRezervacijaKriterijumAgent(Request request) {
        Response response = new Response();
        Agent kriterijum = (Agent) request.getArgument();
        try {
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(Controller.getInstance().vratiListuRezervacijaKriterijumAgent(kriterijum));
        } catch (Exception ex) {
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response vratiListuRezervacijaKriterijumPutnik(Request request) {
        Response response = new Response();
        Putnik kriterijum = (Putnik) request.getArgument();
        try {
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(Controller.getInstance().vratiListuRezervacijaKriterijumPutnik(kriterijum));
        } catch (Exception ex) {
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response vratiListuRezervacijaKriterijumAranzman(Request request) {
        Response response = new Response();
        Aranzman kriterijum = (Aranzman) request.getArgument();
        try {
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(Controller.getInstance().vratiListuRezervacijaKriterijumAranzman(kriterijum));
        } catch (Exception ex) {
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response vratiListuSviAgent(Request request) {
        Response response = new Response();
        try {
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(Controller.getInstance().vratiListuSviAgent());
        } catch (Exception ex) {
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response vratiListuSviPutnik(Request request) {
        Response response = new Response();
        try {
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(Controller.getInstance().vratiListuSviPutnik());
        } catch (Exception ex) {
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response vratiListuSviAranzman(Request request) {
        Response response = new Response();
        try {
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(Controller.getInstance().vratiListuSviAranzman());
        } catch (Exception ex) {
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response vratiListuSviMesto(Request request) {
        Response response = new Response();
        try {
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(Controller.getInstance().vratiListuSviMesto());
        } catch (Exception ex) {
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response kreirajPutnik(Request request) {
        Response response = new Response();
        Putnik putnik = (Putnik) request.getArgument();
        try {
            Putnik sacuvan = Controller.getInstance().kreirajPutnik(putnik);
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(sacuvan);
        } catch (Exception ex) {
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response promeniPutnik(Request request) {
        Response response = new Response();
        Putnik putnik = (Putnik) request.getArgument();
        try {
            Putnik sacuvan = Controller.getInstance().promeniPutnik(putnik);
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(sacuvan);
        } catch (Exception ex) {
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response obrisiPutnik(Request request) {
        Response response = new Response();
        Putnik putnik = (Putnik) request.getArgument();
        try {
            Controller.getInstance().obrisiPutnik(putnik);
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(null);
        } catch (Exception ex) {
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response pretraziPutnik(Request request) {
        Response response = new Response();
        Putnik kriterijum = (Putnik) request.getArgument();
        try {
            Putnik putnik = Controller.getInstance().pretraziPutnik(kriterijum);
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(putnik);
        } catch (Exception ex) {
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response vratiListuPutnikKriterijumPutnik(Request request) {
        Response response = new Response();
        Putnik kriterijum = (Putnik) request.getArgument();
        try {
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(Controller.getInstance().vratiListuPutnikKriterijumPutnik(kriterijum));
        } catch (Exception ex) {
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response vratiListuPutnikKriterijumMesto(Request request) {
        Response response = new Response();
        Mesto kriterijum = (Mesto) request.getArgument();
        try {
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(Controller.getInstance().vratiListuPutnikKriterijumMesto(kriterijum));
        } catch (Exception ex) {
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response ubaciRegion(Request request) {
        Response response = new Response();
        Region region = (Region) request.getArgument();
        try {
            Region sacuvan = Controller.getInstance().ubaciRegion(region);
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(sacuvan);
        } catch (Exception ex) {
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }
}
