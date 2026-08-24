/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package client;

import client.communication.Communication;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import coordinator.Coordinator;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author mihajlo
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FlatMacLightLaf.setup();
        Client client = new Client();
        try {
            client.connect();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void connect() throws IOException {
        Socket socket = new Socket("127.0.0.1", 9000);
        Communication.getInstance().setSocket(socket);
        Coordinator.getInstance().otvoriLoginFormu();
    }

}
