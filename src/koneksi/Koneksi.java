/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author JokiJMS
 */
public class Koneksi {
    private Connection koneksi;
    public Connection connect(){
        try{
//            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Berhasil Koneksi!");
        }catch(Exception ex){
            System.out.println("Gagal Koneksi ! "+ex);
        }
        
        String url = "jdbc:mysql://localhost/pemakaman";
        
        try{
            koneksi = (Connection) DriverManager.getConnection(url,"root","");
            System.out.println("Berhasil Koneksi Database!");
        }catch(SQLException ex){
            System.out.println("Gagal koneksi Database! "+ex);
            
        }
        
    return koneksi;    
    }
}
