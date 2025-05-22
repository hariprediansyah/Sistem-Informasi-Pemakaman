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
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author coyha
 */
public class DialogTransaksiAddEdit extends javax.swing.JDialog {
    private Connection conn = new Koneksi().connect();
    /**
     * Creates new form DialogAddEdit
     */
    
    int id = 0;
    String idPetakLama = "0";
    String idJenazahLama = "0";
    List<String> listJenazah = new ArrayList<>();
    List<String> listMakam = new ArrayList<>();
    List<String> listBlok = new ArrayList<>();
    List<String> listHarga = new ArrayList<>();
    List<String> listPetak = new ArrayList<>();
    
    public DialogTransaksiAddEdit(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        txtJudul.setText("Tambah Reservasi");
        txtTanggalTransaksi.setDate(new Date());
        isiCombo();
    }
    
    public void isiCombo(){
        isiComboJenazah();
        isiComboMakam();
        isiComboBlok();
        isiComboPetak();
    }
    
    public void isiComboJenazah(){
        try {
            String sql = "SELECT id, nama_jenazah FROM jenazah WHERE 1 = 1 ";
            if (Session.getRole().equalsIgnoreCase("guest")){
                sql += " AND user_id = " + String.valueOf(Session.getUser_id());
            }
            sql += " AND (status_pemakaman = 0 OR id = " + idJenazahLama + ") order by nama_jenazah asc";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            cmbJenazah.removeAllItems();
            cmbJenazah.addItem("-- Pilih Jenazah --");

            while (rs.next()) {
                listJenazah.add(rs.getString("id"));
                cmbJenazah.addItem(rs.getString("nama_jenazah"));
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal isi jenazah: " + e.getMessage());
        }
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
            listBlok = new ArrayList<>();
            listHarga = new ArrayList<>();
            if (cmbLokasiMakam.getSelectedIndex() < 1){
                return;
            }
            String sql = "SELECT id, kode_blok, harga FROM blok_makam where lokasi_id = " + listMakam.get(cmbLokasiMakam.getSelectedIndex() - 1) + " order by kode_blok asc";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            cmbBlok.addItem("-- Pilih Blok --");

            while (rs.next()) {
                listHarga.add(rs.getString("harga"));
                listBlok.add(rs.getString("id"));
                cmbBlok.addItem(rs.getString("kode_blok"));
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal isi blok: " + e.getMessage());
        }
    }
    
    public void isiComboPetak(){
        try {
            cmbPetak.removeAllItems();
            listPetak = new ArrayList<>();
            if (cmbBlok.getSelectedIndex() < 1){
                return;
            }
            String sql = "SELECT id, nomor_petak FROM petak_makam where (status = 'Kosong' OR id = " + idPetakLama + ") AND blok_id = " + listBlok.get(cmbBlok.getSelectedIndex() - 1) + " order by nomor_petak asc";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            boolean empty = true;
            cmbPetak.addItem("-- Pilih Petak --");
            while (rs.next()) {
                empty = false;
                listPetak.add(rs.getString("id"));
                cmbPetak.addItem(rs.getString("nomor_petak"));
            }
            
            if (empty){
                JOptionPane.showMessageDialog(null, "TIdak ada petak yang Kosong");
                cmbPetak.removeAllItems();
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal isi petak: " + e.getMessage());
        }
    }
    
    public void setData(int id){
        try {
            this.id = id;

            String sql = "SELECT r.*, "
                        + "p.id AS idPetak, "
                        + "p.nomor_petak, "
                        + "b.id AS idBlok, "
                        + "b.kode_blok, "
                        + "l.id AS idLokasi, "
                        + "l.nama_lokasi, "
                        + "j.nama_jenazah "
                        + "FROM transaksi r "
                            + "INNER JOIN petak_makam p ON r.petak_id = p.id "
                            + "INNER JOIN blok_makam b ON p.blok_id = b.id "
                            + "INNER JOIN lokasi_makam l ON b.lokasi_id = l.id "
                            + "INNER JOIN jenazah j ON r.jenazah_id = j.id "
                        + "WHERE r.id = " + id;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()){
                idPetakLama = rs.getString("idPetak");
                idJenazahLama = rs.getString("jenazah_id");
                isiComboJenazah();
                
                java.sql.Date tanggalTransaksi = java.sql.Date.valueOf(rs.getString("tanggal_pemesanan"));
                txtTanggalTransaksi.setDate(tanggalTransaksi);
                cmbLokasiMakam.setSelectedItem(rs.getString("nama_lokasi"));
                cmbBlok.setSelectedItem(rs.getString("kode_blok"));
                cmbPetak.setSelectedItem(rs.getString("nomor_petak"));
                cmbJenazah.setSelectedItem(rs.getString("nama_jenazah"));
                txtCatatan.setText(rs.getString("catatan"));
                txtPemilikKartu.setText(rs.getString("nama_kartu"));
                txtVCC.setText(rs.getString("vcc_kartu"));
                txtNomorKartu.setText(rs.getString("no_kartu"));
                txtJudul.setText("Ubah Reservasi");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DialogTransaksiAddEdit.class.getName()).log(Level.SEVERE, null, ex);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        txtCatatan = new appcode.form.CustomTextArea();
        cmbLokasiMakam = new appcode.form.CustomComboBox();
        cmbBlok = new appcode.form.CustomComboBox();
        jLabel5 = new javax.swing.JLabel();
        cmbPetak = new appcode.form.CustomComboBox();
        jLabel6 = new javax.swing.JLabel();
        txtTanggalTransaksi = new appcode.form.CustomDateChooser();
        jLabel7 = new javax.swing.JLabel();
        txtJumlahBayar = new appcode.form.CustomTextField();
        cmbJenazah = new appcode.form.CustomComboBox();
        jLabel8 = new javax.swing.JLabel();
        txtPemilikKartu = new appcode.form.CustomTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtVCC = new appcode.form.CustomTextField();
        txtNomorKartu = new appcode.form.CustomTextField();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(45, 48, 51));

        txtJudul.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtJudul.setForeground(new java.awt.Color(255, 255, 255));
        txtJudul.setText("Tambah Transaksi");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tanggal Transaksi");

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

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Petak");

        txtTanggalTransaksi.setBackground(new java.awt.Color(138, 138, 138));
        txtTanggalTransaksi.setForeground(new java.awt.Color(255, 255, 255));
        txtTanggalTransaksi.setDateFormatString("d MMMM yyyy");
        txtTanggalTransaksi.setEnabled(false);
        txtTanggalTransaksi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTanggalTransaksi.setPreferredSize(new java.awt.Dimension(45, 35));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Jumlah Bayar");

        txtJumlahBayar.setBackground(new java.awt.Color(138, 138, 138));
        txtJumlahBayar.setForeground(new java.awt.Color(255, 255, 255));
        txtJumlahBayar.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtJumlahBayar.setEnabled(false);
        txtJumlahBayar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtJumlahBayar.setPlaceholder("Terisi otomatis");

        cmbJenazah.setBackground(new java.awt.Color(138, 138, 138));
        cmbJenazah.setForeground(new java.awt.Color(255, 255, 255));
        cmbJenazah.setArrowColor(new java.awt.Color(204, 213, 209));
        cmbJenazah.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbJenazah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbJenazahActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Jenazah");

        txtPemilikKartu.setBackground(new java.awt.Color(138, 138, 138));
        txtPemilikKartu.setForeground(new java.awt.Color(255, 255, 255));
        txtPemilikKartu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPemilikKartu.setPlaceholder("Pemilik Kartu");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Nama Pemilik Kartu");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("VCC");

        txtVCC.setBackground(new java.awt.Color(138, 138, 138));
        txtVCC.setForeground(new java.awt.Color(255, 255, 255));
        txtVCC.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtVCC.setPlaceholder("VCC");

        txtNomorKartu.setBackground(new java.awt.Color(138, 138, 138));
        txtNomorKartu.setForeground(new java.awt.Color(255, 255, 255));
        txtNomorKartu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNomorKartu.setPlaceholder("Nomor Kartu");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Nomor Kartu");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtPemilikKartu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtVCC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNomorKartu, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                                .addComponent(txtTanggalTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbJenazah, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbLokasiMakam, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbBlok, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbPetak, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtJumlahBayar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(36, 36, 36))))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(23, 23, 23)
                    .addComponent(txtJudul, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(355, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTanggalTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbLokasiMakam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(7, 7, 7)
                        .addComponent(cmbBlok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(7, 7, 7)
                        .addComponent(cmbJenazah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbPetak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtJumlahBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVCC, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPemilikKartu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNomorKartu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(24, 24, 24)
                    .addComponent(txtJudul)
                    .addContainerGap(547, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        try{
            if (cmbJenazah.getSelectedIndex() < 1) {
                throw new Exception("Silakan pilih jenazah terlebih dahulu.");
            }

            if (cmbLokasiMakam.getSelectedIndex() < 1) {
                throw new Exception("Silakan pilih lokasi makam terlebih dahulu.");
            }

            if (cmbBlok.getSelectedIndex() < 1) {
                throw new Exception("Silakan pilih blok terlebih dahulu.");
            }

            if (cmbPetak.getSelectedIndex() < 1) {
                throw new Exception("Silakan pilih petak terlebih dahulu.");
            }

            if (txtPemilikKartu.getText().isEmpty()){
                throw new Exception("Silakan isi pemilik kartu terlebih dahulu.");
            }
            if (txtVCC.getText().isEmpty()){
                throw new Exception("Silakan isi VCC terlebih dahulu.");
            }
            if (txtNomorKartu.getText().isEmpty()){
                throw new Exception("Silakan isi nomor kartu terlebih dahulu.");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;
        }
        
        try {
            String sqlPetak = "UPDATE petak_makam SET status = 'Kosong' WHERE id = " + idPetakLama;
            PreparedStatement st = conn.prepareStatement(sqlPetak);
            st.executeUpdate();
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan "+e);
            return;
        }
        
        try {
            String sqlJenazah = "UPDATE jenazah SET status_pemakaman = 0 WHERE id = " + idJenazahLama;
            PreparedStatement st = conn.prepareStatement(sqlJenazah);
            st.executeUpdate();
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan "+e);
            return;
        }
        
        String sql = "INSERT INTO transaksi(jenazah_id, tanggal_pemesanan, petak_id, jumlah_bayar, no_kartu, vcc_kartu, nama_kartu, catatan, user_input) VALUES (?,?,?,?,?,?,?,?,?)";
        String idPetak = listPetak.get(cmbPetak.getSelectedIndex() - 1);
        String idJenazah = listJenazah.get(cmbJenazah.getSelectedIndex() - 1);
        if (id > 0){
            sql = "UPDATE transaksi "
                + "SET "
                    + "jenazah_id = ?, "
                    + "tanggal_pemesanan = ?, "
                    + "petak_id = ?, "
                    + "jumlah_bayar = ?, "
                    + "no_kartu = ?, "
                    + "vcc_kartu = ?, "
                    + "nama_kartu = ?, "
                    + "catatan = ?"
                + "WHERE id = ?";
        }
            try{
                java.util.Date selectedDate = txtTanggalTransaksi.getDate();
                LocalDate localDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                String formatted = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.setString(1, idJenazah);
                stat.setString(2, formatted);
                stat.setString(3, idPetak);
                stat.setString(4, txtJumlahBayar.getText());
                stat.setString(5, txtNomorKartu.getText());
                stat.setString(6, txtVCC.getText());
                stat.setString(7, txtPemilikKartu.getText());
                stat.setString(8, txtCatatan.getText());
                if (id > 0){
                    stat.setLong(9, id);    
                } else {
                    stat.setInt(9, Session.getUser_id());
                }
                stat.executeUpdate();
                
                String sqlPetak = "UPDATE petak_makam SET status = 'Terisi' WHERE id = " + idPetak;
                PreparedStatement stPetak = conn.prepareStatement(sqlPetak);
                stPetak.executeUpdate();
                
                String sqlJenazah = "UPDATE jenazah SET status_pemakaman = 1 WHERE id = " + idJenazah;
                PreparedStatement stJenazah = conn.prepareStatement(sqlJenazah);
                stJenazah.executeUpdate();
                
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
    
    private void cmbLokasiMakamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbLokasiMakamActionPerformed
        // TODO add your handling code here:
        txtJumlahBayar.setText("");
        isiComboBlok();
        isiComboPetak();
    }//GEN-LAST:event_cmbLokasiMakamActionPerformed

    private void cmbBlokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBlokActionPerformed
        // TODO add your handling code here:
        isiComboPetak();
        if (cmbBlok.getSelectedIndex() >= 1){
            txtJumlahBayar.setText(listHarga.get(cmbBlok.getSelectedIndex() - 1));
        }
    }//GEN-LAST:event_cmbBlokActionPerformed

    private void cmbJenazahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbJenazahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbJenazahActionPerformed

                                                 

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
            java.util.logging.Logger.getLogger(DialogTransaksiAddEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogTransaksiAddEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogTransaksiAddEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogTransaksiAddEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                DialogTransaksiAddEdit dialog = new DialogTransaksiAddEdit(new javax.swing.JFrame(), true);
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
    private appcode.form.CustomComboBox cmbJenazah;
    private appcode.form.CustomComboBox cmbLokasiMakam;
    private appcode.form.CustomComboBox cmbPetak;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private appcode.form.CustomTextArea txtCatatan;
    private javax.swing.JLabel txtJudul;
    private appcode.form.CustomTextField txtJumlahBayar;
    private appcode.form.CustomTextField txtNomorKartu;
    private appcode.form.CustomTextField txtPemilikKartu;
    private appcode.form.CustomDateChooser txtTanggalTransaksi;
    private appcode.form.CustomTextField txtVCC;
    // End of variables declaration//GEN-END:variables
}
