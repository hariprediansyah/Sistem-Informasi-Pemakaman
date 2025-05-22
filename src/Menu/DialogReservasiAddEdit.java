/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Menu;
import appcode.Session;
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
import java.util.List;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author coyha
 */
public class DialogReservasiAddEdit extends javax.swing.JDialog {
    private Connection conn = new Koneksi().connect();
    /**
     * Creates new form DialogAddEdit
     */
    
    int id = 0;
    List<String> listMakam = new ArrayList<>();
    List<String> listBlok = new ArrayList<>();
    List<String> listPetak = new ArrayList<>();
    
    public DialogReservasiAddEdit(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        txtJudul.setText("Tambah Reservasi");
        isiCombo();
    }
    
    public void isiCombo(){
        isiComboMakam();
        isiComboBlok();
        isiComboPetak();
    }
    
    public void isiComboMakam(){
        try {
            String sql = "SELECT id, nama_lokasi FROM lokasi_makam order by nama_lokasi asc";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            cmbLokasiMakam.removeAllItems();
            cmbLokasiMakam.addItem("-- Pilih Makam --");

            while (rs.next()) {
                listMakam.add(rs.getString("id"));
                cmbLokasiMakam.addItem(rs.getString("nama_lokasi"));
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal isi makam: " + e.getMessage());
        }
    }
    
    public void isiComboBlok(){
        try {
            cmbBlok.removeAllItems();
            if (cmbLokasiMakam.getSelectedIndex() < 1){
                return;
            }
            String sql = "SELECT id, kode_blok FROM blok_makam where lokasi_id = " + listMakam.get(cmbLokasiMakam.getSelectedIndex() - 1) + " order by kode_blok asc";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            cmbBlok.addItem("-- Pilih Blok --");

            while (rs.next()) {
                listBlok.add(rs.getString("id"));
                cmbBlok.addItem(rs.getString("kode_blok"));
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal isi makam: " + e.getMessage());
        }
    }
    
    public void isiComboPetak(){
        try {
            cmbPetak.removeAllItems();
            if (cmbBlok.getSelectedIndex() < 1){
                return;
            }
            String sql = "SELECT id, nomor_petak FROM petak_makam where blok_id = " + listBlok.get(cmbBlok.getSelectedIndex() - 1) + " order by nomor_petak asc";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            cmbPetak.addItem("-- Pilih Petak --");
            while (rs.next()) {
                listPetak.add(rs.getString("id"));
                cmbPetak.addItem(rs.getString("nomor_petak"));
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal isi makam: " + e.getMessage());
        }
    }
    
    public void setData(int id){
        try {
            this.id = id;

            String sql = "SELECT r.*, "
                        + "p.nomor_petak, "
                        + "b.kode_blok, "
                        + "l.nama_lokasi, "
                        + "FROM reservasi r "
                            + "INNER JOIN petak_makam p ON r.petak_id = p.id "
                            + "INNER JOIN blok_makam b ON p.blok_id = b.id "
                            + "INNER JOIN lokasi_makam l ON b.lokasi_id = l.id"
                        + "WHERE r.id = " + id;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()){
                Date tanggalReservasi = Date.valueOf(rs.getString("tanggal_reservasi"));
                txtTanggalReservasi.setDate(tanggalReservasi);
                cmbLokasiMakam.setSelectedItem(rs.getString("nama_lokasi"));
                cmbBlok.setSelectedItem(rs.getString("kode_blok"));
                cmbPetak.setSelectedItem(rs.getString("nomor_petak"));
                txtCatatan.setText(rs.getString("catatan"));
                txtJudul.setText("Ubah Reservasi");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DialogReservasiAddEdit.class.getName()).log(Level.SEVERE, null, ex);
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
        txtTanggalReservasi = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtCatatan = new appcode.form.CustomTextArea();
        cmbLokasiMakam = new appcode.form.CustomComboBox();
        cmbBlok = new appcode.form.CustomComboBox();
        jLabel5 = new javax.swing.JLabel();
        cmbPetak = new appcode.form.CustomComboBox();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(45, 48, 51));

        txtJudul.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtJudul.setForeground(new java.awt.Color(255, 255, 255));
        txtJudul.setText("Tambah Reservasi");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tanggal Reservasi");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Lokasi Makam");

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
        jLabel4.setText("Catatan");

        txtTanggalReservasi.setBackground(new java.awt.Color(138, 138, 138));

        txtCatatan.setBackground(new java.awt.Color(138, 138, 138));
        txtCatatan.setColumns(20);
        txtCatatan.setForeground(new java.awt.Color(255, 255, 255));
        txtCatatan.setRows(5);
        txtCatatan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCatatan.setPlaceholder("Catatan");
        jScrollPane1.setViewportView(txtCatatan);

        cmbLokasiMakam.setBackground(new java.awt.Color(138, 138, 138));
        cmbLokasiMakam.setForeground(new java.awt.Color(255, 255, 255));
        cmbLokasiMakam.setArrowColor(new java.awt.Color(204, 213, 209));
        cmbLokasiMakam.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbLokasiMakam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbLokasiMakamActionPerformed(evt);
            }
        });

        cmbBlok.setBackground(new java.awt.Color(138, 138, 138));
        cmbBlok.setForeground(new java.awt.Color(255, 255, 255));
        cmbBlok.setArrowColor(new java.awt.Color(204, 213, 209));
        cmbBlok.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbBlok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBlokActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Blok");

        cmbPetak.setBackground(new java.awt.Color(138, 138, 138));
        cmbPetak.setForeground(new java.awt.Color(255, 255, 255));
        cmbPetak.setArrowColor(new java.awt.Color(204, 213, 209));
        cmbPetak.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbPetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPetakActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Petak");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTanggalReservasi, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbLokasiMakam, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbBlok, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbPetak, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(82, 82, 82))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTanggalReservasi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbLokasiMakam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(7, 7, 7)
                        .addComponent(cmbBlok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbPetak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(57, 57, 57)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(24, 24, 24)
                    .addComponent(txtJudul)
                    .addContainerGap(341, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (petakId == -1) {
            JOptionPane.showMessageDialog(null, "Silakan pilih petak terlebih dahulu.");
            return;
        }
        
        String sql = "INSERT INTO reservasi(tanggal_reservasi, catatan, petak_id, user_id) VALUES (?,?,?,?)";
        if (id > 0){
            sql = "UPDATE reservasi "
                + "SET "
                    + "tanggal_reservasi = ?, "
                    + "catatan = ?, "
                    + "petak_id = ? "
                + "WHERE id = ?";
        }
            try{
                java.util.Date selectedDate = txtTanggalReservasi.getDate();
                LocalDate localDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                String formatted = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.setString(1, formatted);
                stat.setString(2, txtCatatan.getText());
                stat.setInt(3, petakId);
                
                if (id > 0){
                    stat.setLong(4, id);    
                } else {
                    stat.setInt(4, Session.getUser_id());
                }
                stat.executeUpdate();
                
                if (id > 0) {
                    JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
                } else {
                    JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
                }
                
                dispose();
            }catch (SQLException e){
                JOptionPane.showMessageDialog(null, "Data Gagal Disimpan "+e);
            }
    }//GEN-LAST:event_btnSaveActionPerformed
    
    private int petakId = -1;
    private void cmbLokasiMakamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbLokasiMakamActionPerformed
        // TODO add your handling code here:
        isiComboBlok();
        isiComboPetak();
    }//GEN-LAST:event_cmbLokasiMakamActionPerformed

    private void cmbBlokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBlokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbBlokActionPerformed

    private void cmbPetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPetakActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPetakActionPerformed

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
            java.util.logging.Logger.getLogger(DialogReservasiAddEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogReservasiAddEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogReservasiAddEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogReservasiAddEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                DialogReservasiAddEdit dialog = new DialogReservasiAddEdit(new javax.swing.JFrame(), true);
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
    private appcode.form.CustomComboBox cmbBlok;
    private appcode.form.CustomComboBox cmbLokasiMakam;
    private appcode.form.CustomComboBox cmbPetak;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private appcode.form.CustomTextArea txtCatatan;
    private javax.swing.JLabel txtJudul;
    private com.toedter.calendar.JDateChooser txtTanggalReservasi;
    // End of variables declaration//GEN-END:variables
}
