/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import view.form.OProgramuForm;

/**
 *
 * @author mihajlo
 */
public class OProgramuController {

    private final OProgramuForm form;

    public OProgramuController(OProgramuForm form) {
        this.form = form;
        addActionListeners();
    }

    public void otvoriFormu() {
        form.setVisible(true);
    }

    private void addActionListeners() {
        form.getBtnZatvori().addActionListener(evt -> form.dispose());
    }
}
