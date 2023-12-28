/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import PatronSingletonConexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ===> kevin <=====
 */
public class RegistroPersonas extends javax.swing.JFrame {

    DefaultTableModel tablaDatos = new DefaultTableModel();

    Conexion conexion = Conexion.getInstancia();
    Connection cx = conexion.conectar();

    public RegistroPersonas() {
        initComponents();
        cargarTablaPersonas();
        setTitle("Registro de Personas");
        bloquearBotones();
        this.bloquearTexto();
        this.setLocation(600, 100);
    }

    void bloquearTexto() {
        jtxtCedula.setEnabled(false);
        jtxtNombre.setEnabled(false);
        jtxtApellido.setEnabled(false);
    }

    void bloquearBotones() {
        jbtnInsertar.setEnabled(false);
        jbtnCancelar.setEnabled(false);
        jbtnNuevo.setEnabled(true);
        jbtnEditar.setEnabled(false);
        jbtnEliminar.setEnabled(false);

    }

    void desbloquearbOtones() {
        jbtnInsertar.setEnabled(true);
        jbtnCancelar.setEnabled(true);
        jbtnNuevo.setEnabled(false);
        jbtnEditar.setEnabled(false);
        jbtnEliminar.setEnabled(false);
    }

    void desbloquearTexto() {
        jtxtCedula.setEnabled(true);
        jtxtNombre.setEnabled(true);
        jtxtApellido.setEnabled(true);
        jtxtCedula.requestFocus();
    }

    void desbloquearTextoEdi() {
        jtxtCedula.setEnabled(false);
        jtxtNombre.setEnabled(true);
        jtxtApellido.setEnabled(true);
        jtxtCedula.requestFocus();
    }

    void limpiarTexto() {
        jtxtCedula.setText("");
        jtxtNombre.setText("");
        jtxtApellido.setText("");

    }

    void desbloquearBOtonesEditElim() {
        jbtnEditar.setEnabled(true);
        jbtnEliminar.setEnabled(true);
        jbtnCancelar.setEnabled(true);
        jbtnNuevo.setEnabled(false);
    }

    void salir() {
        this.dispose();
    }

    void soloNumeros(java.awt.event.KeyEvent evt) {
        char caracter = evt.getKeyChar();
        if (((caracter < '0') || (caracter > '9'))) {
            evt.consume();
        }
    }

    void insertar() {
        if (jtxtCedula.getText().isEmpty() || jtxtCedula.getText() == "") {

            JOptionPane.showMessageDialog(this, "Ingrese una cedula");
            jtxtCedula.requestFocus();

        } else if (jtxtNombre.getText().isEmpty() || jtxtNombre.getText() == "") {
            JOptionPane.showMessageDialog(this, "Ingrese un Nombre");
            jtxtNombre.requestFocus();
        } else if (jtxtApellido.getText().isEmpty() || jtxtApellido.getText() == "") {
            JOptionPane.showMessageDialog(this, "Ingrese un Apellido");
            jtxtApellido.requestFocus();

        } else {
            if (jtxtCedula.getText().length() > 10) {
                JOptionPane.showMessageDialog(this, "Solo puede  ingresar 10 digitos en la cedula");
                jtxtCedula.requestFocus();

            } else if (jtxtNombre.getText().length() > 30) {
                JOptionPane.showMessageDialog(this, "Solo puede  ingresar 30 caracteres en el nombre");
                jtxtNombre.requestFocus();
            } else if (jtxtApellido.getText().length() > 30) {
                JOptionPane.showMessageDialog(this, "Solo puede  ingresar 30 caracteres en el apellido");
                jtxtApellido.requestFocus();

            } else {

                try {

                    String sql = "";
                    sql = "insert into personas(CED_PER,NOM_PER,APE_PER)values(?,?,?)";
                    PreparedStatement psd = cx.prepareStatement(sql);

                    psd.setString(1, jtxtCedula.getText());
                    psd.setString(2, jtxtNombre.getText());
                    psd.setString(3, jtxtApellido.getText());

                    int n = psd.executeUpdate();

                    if (n > 0) {
                        JOptionPane.showMessageDialog(this, "Se inserto Correctamente");
                        bloquearBotones();
                        bloquearTexto();
                        limpiarTexto();
                        cargarTablaPersonas();
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "No se puede guardar los datos por que ya existe un estudiante con la misma cedula");
                    jtxtCedula.requestFocus();
                }
            }
        }
        conexion.desconectar();
    }

    void cargarDatosTxt() {
        int filaSelec = jtblPersonas.getSelectedRow();
        if (filaSelec >= 0) {
            jtxtCedula.setText(jtblPersonas.getValueAt(filaSelec, 0).toString());
            jtxtNombre.setText(jtblPersonas.getValueAt(filaSelec, 1).toString());
            jtxtApellido.setText(jtblPersonas.getValueAt(filaSelec, 2).toString());
        }
    }

    void eliminar() {
        if (JOptionPane.showConfirmDialog(new JInternalFrame(), "Esta seguro de borrar el registro ", "Borrar regristros", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                
                String sql = " ";
                sql = "delete from personas where CED_PER= '" + jtxtCedula.getText() + "'";
                PreparedStatement psd = cx.prepareStatement(sql);
                int n = psd.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(this, "Se elimino correctamente");
                    limpiarTexto();
                    cargarTablaPersonas();
                    bloquearBotones();
                    bloquearTexto();
                }

            } catch (SQLException ex) {

            }
        }
    }

    void editar() {

        if (jtxtCedula.getText().isEmpty() || jtxtCedula.getText() == "") {

            JOptionPane.showMessageDialog(null, "Ingrese una cedula");
            jtxtCedula.requestFocus();
        } else if (jtxtNombre.getText().isEmpty() || jtxtNombre.getText() == "") {
            JOptionPane.showMessageDialog(this, "Ingrese un Nombre");
            jtxtNombre.requestFocus();
        } else if (jtxtApellido.getText().isEmpty() || jtxtApellido.getText() == "") {
            JOptionPane.showMessageDialog(this, "Ingrese un Apellido");
            jtxtApellido.requestFocus();
        } else {
            try {
                if (jtxtCedula.getText().length() > 10) {
                    JOptionPane.showMessageDialog(this, "Solo puede  ingresar 10 digitos en la cedula");
                    jtxtCedula.requestFocus();
                } else if (jtxtNombre.getText().length() > 30) {
                    JOptionPane.showMessageDialog(this, "Solo puede  ingresar 30 caracteres en el nombre");
                    jtxtNombre.requestFocus();
                } else if (jtxtApellido.getText().length() > 30) {
                    JOptionPane.showMessageDialog(this, "Solo puede  ingresar 30 caracteres en el apellido");
                    jtxtApellido.requestFocus();
                } else {

                    String sql = "";

                    sql = "update personas set NOM_PER='" + jtxtNombre.getText() + "',APE_PER='" + jtxtApellido.getText() + "'Where CED_PER='" + jtxtCedula.getText() + "'";
                    
                    PreparedStatement psd = cx.prepareStatement(sql);
                    int n = psd.executeUpdate();
                    if (n > 0) {
                        JOptionPane.showMessageDialog(this, "Datos Actualizados");
                        cargarTablaPersonas();
                        limpiarTexto();
                        bloquearBotones();
                        bloquearTexto();
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegistroPersonas.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    
    public void cargarTablaPersonas() {
        try {
            String[] titulos = {"CEDULA", "NOMBRE", "APELLIDO"};
            String[] datosPersonas = new String[3];
            tablaDatos = new DefaultTableModel(null, titulos);

            String sql = "";
            sql = "select * from personas";
            Statement psd = cx.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                datosPersonas[0] = rs.getString("CED_PER");
                datosPersonas[1] = rs.getString("NOM_PER");
                datosPersonas[2] = rs.getString("APE_PER");

                tablaDatos.addRow(datosPersonas);
            }
            jtblPersonas.setModel(tablaDatos);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblPersonas = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jtxtCedula = new javax.swing.JTextField();
        jtxtNombre = new javax.swing.JTextField();
        jtxtApellido = new javax.swing.JTextField();
        jbnRegresar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jbtnNuevo = new javax.swing.JButton();
        jbtnInsertar = new javax.swing.JButton();
        jbtnEditar = new javax.swing.JButton();
        jbtnEliminar = new javax.swing.JButton();
        jbtnCancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtblPersonas.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jtblPersonas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtblPersonas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtblPersonasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtblPersonas);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 500, 220));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel2.setText("REGISTRO DE PERSONAS");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Ingrese Cedula:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, 30));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Ingrese Nombre: ");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Ingrese Apelldio:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, -1, -1));

        jtxtCedula.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jtxtCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtCedulaActionPerformed(evt);
            }
        });
        jtxtCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtCedulaKeyTyped(evt);
            }
        });
        jPanel1.add(jtxtCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 161, -1));

        jtxtNombre.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel1.add(jtxtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 159, -1));

        jtxtApellido.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jtxtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtApellidoActionPerformed(evt);
            }
        });
        jPanel1.add(jtxtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, 158, -1));

        jbnRegresar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/regresar4.jpg"))); // NOI18N
        jbnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnRegresarActionPerformed(evt);
            }
        });
        jPanel1.add(jbnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 400, 70, 70));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jbtnNuevo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbtnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/new.jpg"))); // NOI18N
        jbtnNuevo.setToolTipText("");
        jbtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNuevoActionPerformed(evt);
            }
        });

        jbtnInsertar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbtnInsertar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/insertar.png"))); // NOI18N
        jbtnInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnInsertarActionPerformed(evt);
            }
        });

        jbtnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/editar.png"))); // NOI18N
        jbtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEditarActionPerformed(evt);
            }
        });

        jbtnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        jbtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEliminarActionPerformed(evt);
            }
        });

        jbtnCancelar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbtnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelar.jpg"))); // NOI18N
        jbtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbtnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbtnInsertar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jbtnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jbtnInsertar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnEliminar))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jbtnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbtnEditar)))
                .addGap(18, 18, 18)
                .addComponent(jbtnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 90, 240, 290));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo1.jpg"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 510));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtxtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtApellidoActionPerformed

    private void jtxtCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtCedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtCedulaActionPerformed

    private void jtxtCedulaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtCedulaKeyTyped
        soloNumeros(evt);
    }//GEN-LAST:event_jtxtCedulaKeyTyped

    private void jbtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNuevoActionPerformed
        limpiarTexto();
        desbloquearTexto();
        desbloquearbOtones();
    }//GEN-LAST:event_jbtnNuevoActionPerformed

    private void jbtnInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnInsertarActionPerformed
        insertar();

    }//GEN-LAST:event_jbtnInsertarActionPerformed

    private void jbtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelarActionPerformed
        bloquearBotones();
        bloquearTexto();
        limpiarTexto();
    }//GEN-LAST:event_jbtnCancelarActionPerformed

    private void jbnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnRegresarActionPerformed
        MainPrincipal r = new MainPrincipal();
        r.setVisible(true);
        salir();
    }//GEN-LAST:event_jbnRegresarActionPerformed

    private void jtblPersonasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtblPersonasMouseClicked
        cargarDatosTxt();
        desbloquearTextoEdi();
        desbloquearBOtonesEditElim();
    }//GEN-LAST:event_jtblPersonasMouseClicked

    private void jbtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEditarActionPerformed
        editar();
    }//GEN-LAST:event_jbtnEditarActionPerformed

    private void jbtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEliminarActionPerformed
        eliminar();
    }//GEN-LAST:event_jbtnEliminarActionPerformed

    
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
            java.util.logging.Logger.getLogger(RegistroPersonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroPersonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroPersonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroPersonas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroPersonas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbnRegresar;
    private javax.swing.JButton jbtnCancelar;
    private javax.swing.JButton jbtnEditar;
    private javax.swing.JButton jbtnEliminar;
    private javax.swing.JButton jbtnInsertar;
    private javax.swing.JButton jbtnNuevo;
    private javax.swing.JTable jtblPersonas;
    private javax.swing.JTextField jtxtApellido;
    private javax.swing.JTextField jtxtCedula;
    private javax.swing.JTextField jtxtNombre;
    // End of variables declaration//GEN-END:variables
}
