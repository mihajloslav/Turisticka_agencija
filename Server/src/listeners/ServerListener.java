/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package listeners;

import threads.HandleClientThread;

/**
 *
 * @author mihajlo
 */
public interface ServerListener {

    void onLog(String poruka);

    void onKlijentPovezan(HandleClientThread klijent);

    void onKlijentAzuriran(HandleClientThread klijent);

    void onKlijentOdjavljen(HandleClientThread klijent);

    void onPromenaStatusa(boolean pokrenut);
}
