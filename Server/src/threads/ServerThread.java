/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threads;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mihajlo
 */
public class ServerThread extends Thread {

    private ServerSocket serverSocket;
    private List<HandleClientThread> clients;
    private ServerListener listener;

    public ServerThread(int port, ServerListener listener) throws IOException {
        serverSocket = new ServerSocket(port);
        clients = new ArrayList<>();
        this.listener = listener;
    }

    @Override
    public void run() {
        while (!serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();
                HandleClientThread thread = new HandleClientThread(socket, listener);
                thread.start();
                clients.add(thread);
                if (listener != null) {
                    listener.onLog("Клијент повезан: " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
                    listener.onKlijentPovezan(thread);
                }
            } catch (IOException ex) {
            }
        }
        stopAllThreads();
    }

    private void stopAllThreads() {
        for (HandleClientThread client : clients) {
            try {
                client.getSocket().close();
            } catch (IOException ex) {
            }
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
}
