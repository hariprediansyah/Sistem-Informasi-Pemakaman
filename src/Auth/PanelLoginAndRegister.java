/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Auth;

import appcode.CustomTextField;
import javax.swing.GroupLayout;

/**
 *
 * @author MyBook Z Series
 */
public class PanelLoginAndRegister extends javax.swing.JPanel {

    /**
     * Creates new form PanelLoginAndRegister
     */
    public PanelLoginAndRegister() {
        initComponents();
        initLogin();
        initRegister();
        panelLogin.setVisible(true);
        panelRegister.setVisible(false);
    }
    
    private void initLogin(){
        CustomTextField txtUser = new CustomTextField();
        txtUser.setPrefixIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/email.png")));
        panelLogin.add(txtUser, "w 60%");
    }
    
    private void initRegister(){
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelLogin = new javax.swing.JPanel();
        panelRegister = new javax.swing.JPanel();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        panelLogin.setLayout(new javax.swing.BoxLayout(panelLogin, javax.swing.BoxLayout.LINE_AXIS));
        add(panelLogin);

        javax.swing.GroupLayout panelRegisterLayout = new javax.swing.GroupLayout(panelRegister);
        panelRegister.setLayout(panelRegisterLayout);
        panelRegisterLayout.setHorizontalGroup(
            panelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 455, Short.MAX_VALUE)
        );
        panelRegisterLayout.setVerticalGroup(
            panelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 374, Short.MAX_VALUE)
        );

        add(panelRegister);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelLogin;
    private javax.swing.JPanel panelRegister;
    // End of variables declaration//GEN-END:variables
}
