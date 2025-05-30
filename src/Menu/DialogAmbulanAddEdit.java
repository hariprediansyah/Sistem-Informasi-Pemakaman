/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Menu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.awt.event.KeyEvent;
import koneksi.Koneksi;
import appcode.form.RoundedGradientButton;
import javax.swing.SwingUtilities;

/**
 *
 * @author coyha
 */
public class DialogAmbulanAddEdit extends javax.swing.JDialog {
    private Connection conn = new Koneksi().connect();
    /**
     * Creates new form DialogAddEdit
     */
    
    int id = 0;
    
    public DialogAmbulanAddEdit(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    
        if (id == 0) {
            cmbStatus.setSelectedItem("Tersedia");
//            cmbStatus.setEnabled(false);
        }
        txtJudul.setText("Tambah Ambulan");
    }
    
    public void setData(int id){
        try {
            this.id = id;
            
            String sql = "SELECT * FROM ambulan WHERE id = " + id;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                txtNoPolisi.setText(rs.getString("nomor_polisi"));
                txtSopir.setText(rs.getString("sopir"));
                txtKapasitas.setText(String.valueOf(rs.getInt("kapasitas")));
                txtKeterangan.setText(rs.getString("keterangan"));
                cmbStatus.setSelectedItem(rs.getString("status"));
                txtJudul.setText("Ubah Ambulan");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DialogAmbulanAddEdit.class.getName()).log(Level.SEVERE, null, ex);
        }
   }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtJudul = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnSave = new RoundedGradientButton("Simpan");
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNoPolisi = new appcode.form.CustomTextField();
        txtSopir = new appcode.form.CustomTextField();
        txtKapasitas = new appcode.form.CustomTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtKeterangan = new appcode.form.CustomTextArea();
        cmbStatus = new appcode.form.CustomComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(45, 48, 51));

        txtJudul.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtJudul.setForeground(new java.awt.Color(255, 255, 255));
        txtJudul.setText("Tambah Ambulan");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nomor Polisi");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Sopir");

        btnSave.setBackground(new java.awt.Color(0, 91, 99));
        btnSave.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSave.setText("Simpan");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Kapasitas");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Keterangan");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Status");

        txtNoPolisi.setBackground(new java.awt.Color(138, 138, 138));
        txtNoPolisi.setForeground(new java.awt.Color(255, 255, 255));
        txtNoPolisi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNoPolisi.setPlaceholder("Nomor Polisi");
        txtNoPolisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoPolisiActionPerformed(evt);
            }
        });
        txtNoPolisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNoPolisiKeyTyped(evt);
            }
        });

        txtSopir.setBackground(new java.awt.Color(138, 138, 138));
        txtSopir.setForeground(new java.awt.Color(255, 255, 255));
        txtSopir.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSopir.setPlaceholder("Nama Sopir");

        txtKapasitas.setBackground(new java.awt.Color(138, 138, 138));
        txtKapasitas.setForeground(new java.awt.Color(255, 255, 255));
        txtKapasitas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtKapasitas.setPlaceholder("Kapasitas");
        txtKapasitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKapasitasActionPerformed(evt);
            }
        });
        txtKapasitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtKapasitasKeyTyped(evt);
            }
        });

        txtKeterangan.setBackground(new java.awt.Color(138, 138, 138));
        txtKeterangan.setColumns(20);
        txtKeterangan.setForeground(new java.awt.Color(255, 255, 255));
        txtKeterangan.setRows(5);
        txtKeterangan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtKeterangan.setPlaceholder("Keterangan");
        jScrollPane1.setViewportView(txtKeterangan);

        cmbStatus.setBackground(new java.awt.Color(138, 138, 138));
        cmbStatus.setForeground(new java.awt.Color(255, 255, 255));
        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tersedia", "Terpakai" }));
        cmbStatus.setArrowColor(new java.awt.Color(204, 213, 209));
        cmbStatus.setEnabled(false);
        cmbStatus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbStatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                            .addComponent(txtNoPolisi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSopir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSave, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKapasitas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23))))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(23, 23, 23)
                    .addComponent(txtJudul)
                    .addContainerGap(389, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNoPolisi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSopir, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtKapasitas, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(24, 24, 24)
                    .addComponent(txtJudul)
                    .addContainerGap(511, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        String sql = "INSERT INTO ambulan(nomor_polisi, sopir, kapasitas, keterangan, status) VALUES (?,?,?,?,?)";
        if (id > 0){
            sql = "UPDATE ambulan "
                    + "SET "
                        + "nomor_polisi = ?, "
                        + "sopir = ?, "
                        + "kapasitas = ?, "
                        + "keterangan = ?, "
                        + "status = ? "
                    + "WHERE id = ?";
        }
            try{
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.setString(1, txtNoPolisi.getText());
                stat.setString(2, txtSopir.getText());
                int kapasitasNya = Integer.parseInt(txtKapasitas.getText());
                stat.setInt(3, kapasitasNya);  
                stat.setString(4, txtKeterangan.getText());
                stat.setString(5, cmbStatus.getSelectedItem().toString());
                if (id > 0){
                    stat.setLong(6, id);    
                }
                stat.executeUpdate();
                
                if (id > 0) {
                    JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
                } else {
                    JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
                }
                
                dispose();
            }catch (SQLException e){
                JOptionPane.showMessageDialog(null, "Data Gagal Disimpan " + e);
            }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void cmbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbStatusActionPerformed

    private void txtKapasitasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKapasitasKeyTyped
        // TODO add your handling code here:        
        char c = evt.getKeyChar();

        if (!Character.isDigit(c)) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Input harus berupa angka!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Jalankan pengecekan setelah karakter masuk ke text field
        SwingUtilities.invokeLater(() -> {
            String text = txtKapasitas.getText();

            // Cek jika tidak kosong dan angka
            if (!text.isEmpty()) {
                try {
                    int kapasitas = Integer.parseInt(text);
                    if (kapasitas > 5) {
                        JOptionPane.showMessageDialog(null, "Kapasitas maksimal hanya 5 orang.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                        txtKapasitas.setText("5");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Input tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
                    txtKapasitas.setText("");
                }
            }
        });
    }//GEN-LAST:event_txtKapasitasKeyTyped

    private void txtKapasitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKapasitasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKapasitasActionPerformed

    private void txtNoPolisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoPolisiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoPolisiActionPerformed

    private void txtNoPolisiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoPolisiKeyTyped
        // TODO add your handling code here:                                   
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
    }//GEN-LAST:event_txtNoPolisiKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DialogAmbulanAddEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogAmbulanAddEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogAmbulanAddEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogAmbulanAddEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogAmbulanAddEdit dialog = new DialogAmbulanAddEdit(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private appcode.form.CustomComboBox cmbStatus;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel txtJudul;
    private appcode.form.CustomTextField txtKapasitas;
    private appcode.form.CustomTextArea txtKeterangan;
    private appcode.form.CustomTextField txtNoPolisi;
    private appcode.form.CustomTextField txtSopir;
    // End of variables declaration//GEN-END:variables
}
