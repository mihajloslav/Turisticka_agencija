/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package server;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import view.ServerAdminForm;

/**
 *
 * @author mihajlo
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        FlatMacLightLaf.setup();
        new ServerAdminForm().setVisible(true);
    }

}
