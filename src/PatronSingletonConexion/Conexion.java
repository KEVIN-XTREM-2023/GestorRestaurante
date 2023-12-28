/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PatronSingletonConexion;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author ===> kevin <=====
 */
public class Conexion {

    private static Conexion intancia;
    Connection connect = null;

    private Conexion() {

    }

    public static Conexion getInstancia() {
        if (intancia == null) {
            intancia = new Conexion();
        }
        return intancia = new Conexion();
    }

    public Connection conectar() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/restaurante", "root", "");
            //System.out.println("Conextado");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error" + ex);
        }
        return connect;
    }

    public void desconectar() {

        this.connect = null;
        if (this.connect == null) {
            //System.out.println("Se desconectó de MySQL");
        }
    }

}
