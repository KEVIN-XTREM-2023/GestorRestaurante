/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import DecoradorPatronJugos.IJugos;
import DecoradorPatronJugos.JugoSimple;
import DecoradorPatronJugos.Mora;
import DecoradorPatronJugos.Naranja;
import DecoradorPatronJugos.Pinia;
import DecoradorPatronMalteadas.Chocolate;
import DecoradorPatronMalteadas.IMalteadas;
import DecoradorPatronMalteadas.MalteadaSimple;
import DecoradorPatronMalteadas.Natural;
import DecoradorPatronMalteadas.Vainilla;
import DecoradorPatronSnack.Doritos;
import DecoradorPatronSnack.ISnack;
import DecoradorPatronSnack.Papas;
import DecoradorPatronSnack.SnackSimple;
import DecoradorPatronSnack.Yogurt;
import DecoradorPatronTostadas.ITostadas;
import DecoradorPatronTostadas.Peperoni;
import DecoradorPatronTostadas.Queso;
import DecoradorPatronTostadas.TostadaSimple;
import PatronFactory.fabrica.FabricaComida;
import PatronFactory.interfaz.IComida;
import PatronSingletonConexion.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ===> kevin <=====
 */
public class Restaurante extends javax.swing.JFrame {

    Conexion cn = Conexion.getInstancia();
    Connection cx = cn.conectar();
    DefaultTableModel datosPersonas = new DefaultTableModel();
    DefaultComboBoxModel menuRestaurante = new DefaultComboBoxModel();
    DefaultComboBoxModel menuPromociones = new DefaultComboBoxModel();
    DefaultListModel promocionJugos = new DefaultListModel();
    DefaultListModel promocionMalteadas = new DefaultListModel();
    DefaultListModel promocionSnack = new DefaultListModel();
    DefaultListModel promocionTostadas = new DefaultListModel();

    double totalPagarMenu = 0;
    double totalPagarPromociones = 0;
    String detalles = "";
    String detallesPromociones = "";

    public Restaurante() {
        initComponents();
        this.setLocation(100, 100);
        this.setTitle("Restaurante");
        cargarDatosTabla();
        bloquearTextos();
        //llenarListaMenu();
        llenarListaMenuComboBox();
        this.jatxtDetalles.setEditable(false);
        llenarPromociones();
        setResizable(false);
        llenarListasV2();
    }

    void salir() {
        this.dispose();
    }

    void bloquearTextos() {
        jtxtCedula.setEditable(false);
        jtxtNombre.setEditable(false);
        jtxtApellido.setEditable(false);
    }

    void cargarDatosTabla() {
        try {
            String[] titulos = {"CEDULA", "NOMBRE", "APELLIDO"};
            String[] registros = new String[3];
            datosPersonas = new DefaultTableModel(null, titulos);

            String sql = "";
            sql = "select * from personas";
            Statement psd = cx.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                registros[0] = rs.getString("CED_PER");
                registros[1] = rs.getString("NOM_PER");
                registros[2] = rs.getString("APE_PER");

                datosPersonas.addRow(registros);
            }
            jtblPersonas.setModel(datosPersonas);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }

        this.cn.desconectar();

    }

    void cargarDatosTxt() {
        int filaSelec = jtblPersonas.getSelectedRow();
        if (filaSelec >= 0) {
            jtxtCedula.setText(jtblPersonas.getValueAt(filaSelec, 0).toString());
            jtxtNombre.setText(jtblPersonas.getValueAt(filaSelec, 1).toString());
            jtxtApellido.setText(jtblPersonas.getValueAt(filaSelec, 2).toString());
        }
    }

    void llenarListaMenuComboBox() {
        String[] modelo = {"Seleccione", "SOPA", "ARROZ", "POSTRE", "HAMBURGUESAS"};

        for (int i = 0; i < modelo.length; i++) {
            menuRestaurante.addElement(modelo[i]);
        }
        jcbxMenu.setModel(menuRestaurante);
    }

    void ordenar() {

        if (verificar()) {

            FabricaComida comidafabrica = new FabricaComida();
            String comida = (String) jcbxMenu.getSelectedItem();
            IComida cm = comidafabrica.obtnerComida(comida);

            if (!"Seleccione".equals(comida)) {
                if ("SOPA".equals(comida)) {
                    double preciSopa = Double.parseDouble(jlblPrecioSopa.getText());
                    detalles += cm.descripcion() + "\n";

                    jatxtDetalles.setText(detalles + cm.comer());
                    cm.setPrecio(preciSopa);
                    totalPagarMenu += cm.getprecio();

                }
                if ("ARROZ".equals(comida)) {
                    double preciArroz = Double.parseDouble(jlblPrecioArroz.getText());
                    detalles += cm.descripcion() + "\n";

                    jatxtDetalles.setText(detalles + cm.comer());
                    cm.setPrecio(preciArroz);
                    totalPagarMenu += cm.getprecio();

                }
                if ("HAMBURGUESAS".equals(comida)) {
                    double preciHamburguesas = Double.parseDouble(jlblPrecioHamburguesas.getText());
                    detalles += cm.descripcion() + "\n";

                    jatxtDetalles.setText(detalles + cm.comer());
                    cm.setPrecio(preciHamburguesas);
                    totalPagarMenu += cm.getprecio();

                }
                if ("POSTRE".equals(comida)) {
                    double precioPostre = Double.parseDouble(jlblPrecioPostre.getText());
                    detalles += cm.descripcion() + "\n";

                    jatxtDetalles.setText(detalles + cm.comer());
                    cm.setPrecio(precioPostre);
                    totalPagarMenu += cm.getprecio();

                }

            } else {
                detalles += cm.descripcion() + "\n";
                jatxtDetalles.setText(detalles + "\n");
            }

        }
    }

    void total() {
        if (verificar()) {
            jlblTotal.setText("El total a pagar es de " + (totalPagarMenu + totalPagarPromociones) + " $");
        }

    }

    boolean verificar() {
        if (jtxtCedula.getText().equals("") && jtxtNombre.getText().equals("") && jtxtApellido.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Seleccione a un Cliente");
            return false;
        }
        return true;
    }

    void llenarPromociones() {
        String[] datosPromociones = {"Seleccione", "JUGOS", "MALTEADAS", "TOSTADAS", "SNACK"};

        for (int i = 0; i < datosPromociones.length; i++) {
            menuPromociones.addElement(datosPromociones[i]);
        }
        jcbxPromociones.setModel(menuPromociones);

    }

    void llenarListas() {
        String opcion = (String) jcbxPromociones.getSelectedItem();
        if (!"Seleccione".equals(opcion)) {
            if ("JUGOS".equals(opcion)) {
                //JOptionPane.showMessageDialog(this,"ESTA AQUI");
                promocionJugos.addElement("PIÑA");
                promocionJugos.addElement("MORA");
                promocionJugos.addElement("NARANJA");
                jlistJugos.setModel(promocionJugos);
                opcion = "xx";
            }
            if ("MALTEADAS".equals(opcion)) {
                promocionMalteadas.addElement("NATURAL");
                promocionMalteadas.addElement("VAINILLA");
                promocionMalteadas.addElement("CHOCOLATE");
                promocionMalteadas.addElement("FRESA");
                jlistMalteadas.setModel(promocionMalteadas);

            }
            if ("TOSTADAS".equals(opcion)) {
                promocionTostadas.addElement("QUESO");
                promocionTostadas.addElement("PEPERONI");

                jlistTostadas.setModel(promocionTostadas);
            }
            if ("SNACK".equals(opcion)) {
                promocionSnack.addElement("PAPAS");
                promocionSnack.addElement("DORITOS");
                promocionSnack.addElement("YOGURT");
                jlistSnack.setModel(promocionSnack);
            }
        }

    }

    void llenarListasV2() {

        //JOptionPane.showMessageDialog(this,"ESTA AQUI");
        promocionJugos.addElement("PIÑA");
        promocionJugos.addElement("MORA");
        promocionJugos.addElement("NARANJA");
        jlistJugos.setModel(promocionJugos);

        promocionMalteadas.addElement("NATURAL");
        promocionMalteadas.addElement("VAINILLA");
        promocionMalteadas.addElement("CHOCOLATE");
        promocionMalteadas.addElement("FRESA");
        jlistMalteadas.setModel(promocionMalteadas);

        promocionTostadas.addElement("QUESO");
        promocionTostadas.addElement("PEPERONI");

        jlistTostadas.setModel(promocionTostadas);

        promocionSnack.addElement("PAPAS");
        promocionSnack.addElement("DORITOS");
        promocionSnack.addElement("YOGURT");
        jlistSnack.setModel(promocionSnack);

    }

    void ordenarPromocion() {
        if (verificar()) {
            if (jcbxPromociones.getSelectedItem().equals("Seleccione")) {
                JOptionPane.showMessageDialog(this, "Eliga una Promocion");
            }
            if (jcbxPromociones.getSelectedItem().equals("JUGOS")) {
                double precioJugo = Double.parseDouble(jlblPrecioJugo.getText());
                double precioJugoMora = Double.parseDouble(jlblPrecioJugoMora.getText());
                double precioJugoPinia = Double.parseDouble(jlblPrecioJugoPinia.getText());
                double precioJugoNaranja = Double.parseDouble(jlblPrecioNaranja.getText());

                int filaLista = jlistJugos.getSelectedIndex();
                IJugos jugos = new JugoSimple("Jugo", precioJugo);
                //detallesPromociones += jugos.getDescripcion() + " " + jugos.getPrecio() + " $" +"\n";
                //jatxtDetallePromocion.setText(jugos.getDescripcion() + " " + jugos.getPrecio() + " $");
                totalPagarPromociones += jugos.getPrecio();
                if (filaLista >= 0) {
                    if (jlistJugos.getSelectedValue().equals("MORA")) {
                        IJugos jugoMora = new Mora(jugos, "Mora", precioJugoMora);
                        detallesPromociones += jugoMora.getDescripcion() + "  " + (jugoMora.getPrecio() + jugos.getPrecio()) + " $" +"\n";
                        //jatxtDetallePromocion.setText(jugoMora.getDescripcion() + "  " + (jugoMora.getPrecio() + jugos.getPrecio()) + " $");
                        totalPagarPromociones += (jugoMora.getPrecio() + jugos.getPrecio());

                    }
                    if (jlistJugos.getSelectedValue().equals("PIÑA")) {
                        IJugos jugoPiña = new Pinia(jugos, "Piña", precioJugoPinia);
                        detallesPromociones += jugoPiña.getDescripcion() + "  " + (jugoPiña.getPrecio() + jugos.getPrecio()) + " $" +"\n";
                        //jatxtDetallePromocion.setText(jugoPiña.getDescripcion() + "  " + (jugoPiña.getPrecio() + jugos.getPrecio()) + " $");
                        totalPagarPromociones += (jugoPiña.getPrecio() + jugos.getPrecio());
                    }
                    if (jlistJugos.getSelectedValue().equals("NARANJA")) {
                        IJugos jugoNaranja = new Naranja(jugos, "Naranja", precioJugoNaranja);
                        detallesPromociones += jugoNaranja.getDescripcion() + "  " + (jugoNaranja.getPrecio() + jugos.getPrecio()) + " $" +"\n";
                        //jatxtDetallePromocion.setText(jugoNaranja.getDescripcion() + "  " + (jugoNaranja.getPrecio() + jugos.getPrecio()) + " $");
                        totalPagarPromociones += (jugoNaranja.getPrecio() + jugos.getPrecio());
                    }
                    totalPagarPromociones = totalPagarPromociones - 0.5;
                }

            }

            if (jcbxPromociones.getSelectedItem().equals("MALTEADAS")) {
                double precioMalteada = Double.parseDouble(jlblPrecioMalteada.getText());
                double precioMalteadaChocolate = Double.parseDouble(jlblPrecioChocolate.getText());
                double precioMalteadaFresa = Double.parseDouble(jlblPrecioFresa.getText());
                double precioMalteadaNatural = Double.parseDouble(jlblPrecioNatural.getText());
                double precioMalteadaVainilla = Double.parseDouble(jlblPrecioVainilla.getText());

                int filaLista = jlistMalteadas.getSelectedIndex();
                IMalteadas malteadas = new MalteadaSimple("Malteada Simple", precioMalteada);
                detallesPromociones += malteadas.getDescripcion() + " " + malteadas.getPrecio() + " $" +"\n";
                //jatxtDetallePromocion.setText(malteadas.getDescripcion() + " " + malteadas.getPrecio() + " $");
                totalPagarPromociones += malteadas.getPrecio();
                if (filaLista >= 0) {
                    if (jlistMalteadas.getSelectedValue().equals("CHOCOLATE")) {
                        IMalteadas malteadaChocolate = new Chocolate(malteadas, "Chocolate", precioMalteadaChocolate);
                        detallesPromociones += malteadaChocolate.getDescripcion() + "  " + (malteadaChocolate.getPrecio() + malteadas.getPrecio()) + " $" +"\n";
                        //jatxtDetallePromocion.setText(malteadaChocolate.getDescripcion() + "  " + (malteadaChocolate.getPrecio() + malteadas.getPrecio()) + " $");
                        totalPagarPromociones += (malteadaChocolate.getPrecio() + malteadas.getPrecio());
                    }
                    if (jlistMalteadas.getSelectedValue().equals("VAINILLA")) {
                        IMalteadas malteadaVainilla = new Vainilla(malteadas, "Vainilla", precioMalteadaVainilla);
                        detallesPromociones += malteadaVainilla.getDescripcion() + "  " + (malteadaVainilla.getPrecio() + malteadas.getPrecio()) + " $" +"\n";
                        //jatxtDetallePromocion.setText(malteadaVainilla.getDescripcion() + "  " + (malteadaVainilla.getPrecio() + malteadas.getPrecio()) + " $");
                        totalPagarPromociones += (malteadaVainilla.getPrecio() + malteadas.getPrecio());

                    }
                    if (jlistMalteadas.getSelectedValue().equals("NATURAL")) {
                        IMalteadas malteadaNatural = new Natural(malteadas, "Natural", precioMalteadaNatural);
                        detallesPromociones += malteadaNatural.getDescripcion() + "  " + (malteadaNatural.getPrecio() + malteadas.getPrecio()) + " $" +"\n";
                        //jatxtDetallePromocion.setText(malteadaNatural.getDescripcion() + "  " + (malteadaNatural.getPrecio() + malteadas.getPrecio()) + " $");
                        totalPagarPromociones += (malteadaNatural.getPrecio() + malteadas.getPrecio());
                    }
                    if (jlistMalteadas.getSelectedValue().equals("FRESA")) {
                        IMalteadas malteadaFresa = new Chocolate(malteadas, "Fresa", precioMalteadaFresa);
                        detallesPromociones += malteadaFresa.getDescripcion() + "  " + (malteadaFresa.getPrecio() + malteadas.getPrecio()) + " $"+"\n";
                        //jatxtDetallePromocion.setText(malteadaFresa.getDescripcion() + "  " + (malteadaFresa.getPrecio() + malteadas.getPrecio()) + " $");
                        totalPagarPromociones += (malteadaFresa.getPrecio() + malteadas.getPrecio());
                    }
                    totalPagarPromociones = totalPagarPromociones - 1.50;
                    
                }
                
            }

            if (jcbxPromociones.getSelectedItem().equals("TOSTADAS")) {
                double precioTostada = Double.parseDouble(jlblPrecioTostada.getText());
                double precioTostadaQueso = Double.parseDouble(jlblPrecioQueso.getText());
                double precioTostadaPeperoni = Double.parseDouble(jlblPrecioPeperoni.getText());

                int filaLista = jlistTostadas.getSelectedIndex();
                ITostadas tostadas = new TostadaSimple("Tostada Simple", precioTostada);
                detallesPromociones += tostadas.getDescripcion() + " " + tostadas.getPrecio() + " $" +"\n";
                //jatxtDetallePromocion.setText(tostadas.getDescripcion() + " " + tostadas.getPrecio() + " $");
                totalPagarPromociones += tostadas.getPrecio();
                
                if (filaLista >= 0) {
                    if (jlistTostadas.getSelectedValue().equals("QUESO")) {
                        ITostadas tostadasQueso = new Queso(tostadas, "Queso", precioTostadaQueso);
                        detallesPromociones += tostadasQueso.getDescripcion() + "  " + (tostadasQueso.getPrecio() + tostadas.getPrecio()) + " $" +"\n";
                        //jatxtDetallePromocion.setText(tostadasQueso.getDescripcion() + "  " + (tostadasQueso.getPrecio() + tostadas.getPrecio()) + " $");
                        totalPagarPromociones += (tostadasQueso.getPrecio() + tostadas.getPrecio());
                    }
                    if (jlistTostadas.getSelectedValue().equals("PEPERONI")) {
                        ITostadas tostadasPeperoni = new Peperoni(tostadas, "Peperoni", precioTostadaPeperoni);
                        detallesPromociones += tostadasPeperoni.getDescripcion() + "  " + (tostadasPeperoni.getPrecio() + tostadas.getPrecio()) + " $" +"\n";
                        //jatxtDetallePromocion.setText(tostadasPeperoni.getDescripcion() + "  " + (tostadasPeperoni.getPrecio() + tostadas.getPrecio()) + " $");
                        totalPagarPromociones += (tostadasPeperoni.getPrecio() + tostadas.getPrecio());
                    }
                    totalPagarPromociones = totalPagarPromociones - 3.00;

                }

            }
            if (jcbxPromociones.getSelectedItem().equals("SNACK")) {
                double precioSnack = Double.parseDouble(jlblPrecioSnack.getText());
                double precioSnackDoritos = Double.parseDouble(jlblPrecioDoritos.getText());
                double precioSnackPapas = Double.parseDouble(jlblPrecioPapas.getText());
                double precioSnackYogurt = Double.parseDouble(jlblPrecioYogurt.getText());

                int filaLista = jlistSnack.getSelectedIndex();
                ISnack snacks = new SnackSimple("Snack", precioSnack);
                detallesPromociones += snacks.getDescripcion() + " " + snacks.getPrecio() + " $" +"\n";
                //jatxtDetallePromocion.setText(snacks.getDescripcion() + " " + snacks.getPrecio() + " $");
                totalPagarPromociones += snacks.getPrecio();
                if (filaLista >= 0) {
                    if (jlistSnack.getSelectedValue().equals("PAPAS")) {
                        ISnack snacksPapas = new Papas(snacks, "Papas", precioSnackPapas);
                        detallesPromociones += snacksPapas.getDescripcion() + "  " + (snacksPapas.getPrecio() + snacks.getPrecio()) + " $" +"\n";
                        //jatxtDetallePromocion.setText(snacksPapas.getDescripcion() + "  " + (snacksPapas.getPrecio() + snacks.getPrecio()) + " $");
                        totalPagarPromociones += (snacksPapas.getPrecio() + snacks.getPrecio());
                    }
                    if (jlistSnack.getSelectedValue().equals("DORITOS")) {
                        ISnack snacksDoritos = new Doritos(snacks, "Doritos", precioSnackDoritos);
                        detallesPromociones += snacksDoritos.getDescripcion() + "  " + (snacksDoritos.getPrecio() + snacks.getPrecio()) + " $" +"\n";
                        //jatxtDetallePromocion.setText(snacksDoritos.getDescripcion() + "  " + (snacksDoritos.getPrecio() + snacks.getPrecio()) + " $");
                        totalPagarPromociones += (snacksDoritos.getPrecio() + snacks.getPrecio());
                    }
                    if (jlistSnack.getSelectedValue().equals("YOGURT")) {
                        ISnack snacksYogurt = new Yogurt(snacks, "Yogurt", precioSnackYogurt);
                        detallesPromociones += snacksYogurt.getDescripcion() + "  " + (snacksYogurt.getPrecio() + snacks.getPrecio()) + " $" +"\n";
                        //jatxtDetallePromocion.setText(snacksYogurt.getDescripcion() + "  " + (snacksYogurt.getPrecio() + snacks.getPrecio()) + " $");
                        totalPagarPromociones += (snacksYogurt.getPrecio() + snacks.getPrecio());
                    }
                    totalPagarPromociones = totalPagarPromociones - 0.50;
                }
            }
            jatxtDetallePromocion.setText(detallesPromociones);
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un Cliente");
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblPersonas = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jbnRegresar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jtxtCedula = new javax.swing.JTextField();
        jtxtNombre = new javax.swing.JTextField();
        jtxtApellido = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jcbxMenu = new javax.swing.JComboBox<>();
        jbtnOrdenar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jlblPrecioSopa = new javax.swing.JLabel();
        jlblPrecioArroz = new javax.swing.JLabel();
        jlblPrecioPostre = new javax.swing.JLabel();
        jlblPrecioHamburguesas = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jbtnTotal = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jlblTotal = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jcbxPromociones = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jatxtDetalles = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jlistJugos = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        jlistMalteadas = new javax.swing.JList<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        jlistTostadas = new javax.swing.JList<>();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jbtnOrdenarPromocion = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jlistSnack = new javax.swing.JList<>();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jlblPrecioJugo = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jlblPrecioJugoMora = new javax.swing.JLabel();
        jlblPrecioJugoPinia = new javax.swing.JLabel();
        jlblPrecioNaranja = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jlblPrecioMalteada = new javax.swing.JLabel();
        jlblPrecioChocolate = new javax.swing.JLabel();
        jlblPrecioFresa = new javax.swing.JLabel();
        jlblPrecioNatural = new javax.swing.JLabel();
        jlblPrecioVainilla = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jlblPrecioTostada = new javax.swing.JLabel();
        jlblPrecioQueso = new javax.swing.JLabel();
        jlblPrecioPeperoni = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jlblPrecioSnack = new javax.swing.JLabel();
        jlblPrecioDoritos = new javax.swing.JLabel();
        jlblPrecioPapas = new javax.swing.JLabel();
        jlblPrecioYogurt = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jatxtDetallePromocion = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, 610, 240));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("CLIENTES: ");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        jbnRegresar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/regresar4.jpg"))); // NOI18N
        jbnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnRegresarActionPerformed(evt);
            }
        });
        jPanel2.add(jbnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1450, 770, 70, 70));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setText("BIENVENIDOS AL RESTAURANTE UTA");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(485, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(462, 462, 462))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 26, Short.MAX_VALUE)
                .addComponent(jLabel1))
        );

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1550, 70));

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel6.setText("CEDULA:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setText("NOMBRE:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setText("APELLIDO:");

        jtxtCedula.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jtxtCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtCedulaActionPerformed(evt);
            }
        });

        jtxtNombre.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jtxtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtNombreActionPerformed(evt);
            }
        });

        jtxtApellido.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jtxtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtApellidoActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel7.setText("DATOS PERSONALES DEL CLIENTE");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtApellido))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtxtCedula, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                            .addComponent(jtxtNombre))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(50, 50, 50))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jtxtCedula, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jtxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jtxtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 390, 170));

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setText("MENU: ");

        jcbxMenu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jbtnOrdenar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbtnOrdenar.setText("ORDENAR");
        jbtnOrdenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnOrdenarActionPerformed(evt);
            }
        });

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("SOPA");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("ARROZ");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("POSTRE");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("HAMBGURGUESAS");

        jlblPrecioSopa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlblPrecioSopa.setText("1.50");

        jlblPrecioArroz.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlblPrecioArroz.setText("2.25");

        jlblPrecioPostre.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlblPrecioPostre.setText("1.25");

        jlblPrecioHamburguesas.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlblPrecioHamburguesas.setText("2.50");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel17.setText("$");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setText("$");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setText("$");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel20.setText("$");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9))
                .addGap(7, 7, 7)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jlblPrecioPostre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel19))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jlblPrecioHamburguesas)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel20))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jlblPrecioArroz)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jlblPrecioSopa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jlblPrecioSopa)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblPrecioArroz)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(jLabel18)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jlblPrecioPostre)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(jlblPrecioHamburguesas))
                    .addComponent(jLabel20))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnOrdenar)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jcbxMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(106, 106, 106))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jcbxMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnOrdenar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 350, 370, 210));

        jbtnTotal.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jbtnTotal.setText("Total");
        jbtnTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnTotalActionPerformed(evt);
            }
        });
        jPanel2.add(jbtnTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 770, -1, -1));

        jlblTotal.setBackground(new java.awt.Color(102, 255, 102));
        jlblTotal.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 770, 350, 40));

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel13.setText("PROMOCIONES:");

        jcbxPromociones.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jcbxPromociones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jcbxPromocionesMouseClicked(evt);
            }
        });
        jcbxPromociones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbxPromocionesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jcbxPromociones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcbxPromociones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 120, 200, 100));

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jatxtDetalles.setColumns(20);
        jatxtDetalles.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jatxtDetalles.setRows(5);
        jScrollPane2.setViewportView(jatxtDetalles);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 660, 470, 150));

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("DETALLES COMIDA");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 610, 170, 40));

        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jlistJugos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jScrollPane3.setViewportView(jlistJugos);

        jlistMalteadas.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jScrollPane4.setViewportView(jlistMalteadas);

        jlistTostadas.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jScrollPane6.setViewportView(jlistTostadas);

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("JUGOS");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("MALETEADAS");

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel21.setText("SNACK");

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel22.setText("TOSTADAS");

        jbtnOrdenarPromocion.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbtnOrdenarPromocion.setText("ORDENAR PROMOCION");
        jbtnOrdenarPromocion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnOrdenarPromocionActionPerformed(evt);
            }
        });

        jlistSnack.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jScrollPane8.setViewportView(jlistSnack);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(32, 32, 32))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jbtnOrdenarPromocion)))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane6)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnOrdenarPromocion, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );

        jPanel2.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 460, 580, 300));

        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel23.setText("JUGO:");

        jlblPrecioJugo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlblPrecioJugo.setText("0.50");

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel25.setText("$");

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel26.setText("MORA:");

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel27.setText("PIÑA:");

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel28.setText("NARANJA:");

        jlblPrecioJugoMora.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlblPrecioJugoMora.setText("0.50");

        jlblPrecioJugoPinia.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlblPrecioJugoPinia.setText("1.50");

        jlblPrecioNaranja.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlblPrecioNaranja.setText("0.75");

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel32.setText("$");

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel33.setText("$");

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel34.setText("$");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28))
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jlblPrecioNaranja)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel34))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jlblPrecioJugoMora)
                                    .addComponent(jlblPrecioJugoPinia))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel32)
                                    .addComponent(jLabel33)))))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addGap(38, 38, 38)
                        .addComponent(jlblPrecioJugo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel25)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jlblPrecioJugo)
                    .addComponent(jLabel25))
                .addGap(3, 3, 3)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jlblPrecioJugoMora)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jlblPrecioJugoPinia)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jlblPrecioNaranja)
                    .addComponent(jLabel34))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel35.setText("CHOCOLATE:");

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel36.setText("FRESA:");

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel37.setText("NATURAL:");

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel38.setText("VAINILLA:");

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel39.setText("MALTEADA:");

        jlblPrecioMalteada.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlblPrecioMalteada.setText("1.50");

        jlblPrecioChocolate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlblPrecioChocolate.setText("2.00");

        jlblPrecioFresa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlblPrecioFresa.setText("1.50");

        jlblPrecioNatural.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlblPrecioNatural.setText("0.50");

        jlblPrecioVainilla.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlblPrecioVainilla.setText("0.75");

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel45.setText("$");

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel46.setText("$");

        jLabel47.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel47.setText("$");

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel48.setText("$");

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel49.setText("$");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addGap(24, 24, 24)
                        .addComponent(jlblPrecioMalteada)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel45))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel38)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jlblPrecioVainilla))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel37)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jlblPrecioNatural))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel36)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jlblPrecioFresa))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel35)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jlblPrecioChocolate)))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel47)
                            .addComponent(jLabel46)
                            .addComponent(jLabel48)
                            .addComponent(jLabel49))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(jlblPrecioMalteada)
                    .addComponent(jLabel45))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(jlblPrecioChocolate)
                    .addComponent(jLabel46))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(jlblPrecioFresa)
                    .addComponent(jLabel47))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlblPrecioNatural)
                            .addComponent(jLabel48))
                        .addGap(3, 3, 3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(jlblPrecioVainilla)
                    .addComponent(jLabel49))
                .addGap(23, 23, 23))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel50.setText("TOSTADAS:");

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel51.setText("QUESO:");

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel52.setText("PEPERONI:");

        jlblPrecioTostada.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlblPrecioTostada.setText("3.00");

        jlblPrecioQueso.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlblPrecioQueso.setText("1.25");

        jlblPrecioPeperoni.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlblPrecioPeperoni.setText("1.50");

        jLabel56.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel56.setText("$");

        jLabel57.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel57.setText("$");

        jLabel58.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel58.setText("$");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel52)
                        .addGap(18, 18, 18)
                        .addComponent(jlblPrecioPeperoni)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel58))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel50)
                            .addComponent(jLabel51))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jlblPrecioTostada)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel56))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jlblPrecioQueso)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel57)))))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(jlblPrecioTostada)
                    .addComponent(jLabel56))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(jlblPrecioQueso)
                    .addComponent(jLabel57))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(jlblPrecioPeperoni)
                    .addComponent(jLabel58))
                .addContainerGap(69, Short.MAX_VALUE))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel59.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel59.setText("SNACK:");

        jLabel60.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel60.setText("DORITOS:");

        jLabel61.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel61.setText("PAPAS:");

        jLabel62.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel62.setText("YOGURT:");

        jlblPrecioSnack.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlblPrecioSnack.setText("0.50");

        jlblPrecioDoritos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlblPrecioDoritos.setText("0.75");

        jlblPrecioPapas.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlblPrecioPapas.setText("1.00");

        jlblPrecioYogurt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlblPrecioYogurt.setText("1.50");

        jLabel67.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel67.setText("$");

        jLabel68.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel68.setText("$");

        jLabel69.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel69.setText("$");

        jLabel70.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel70.setText("$");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel62)
                        .addGap(13, 13, 13)
                        .addComponent(jlblPrecioYogurt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel70))
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel14Layout.createSequentialGroup()
                            .addComponent(jLabel59)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlblPrecioSnack)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel67))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel14Layout.createSequentialGroup()
                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel14Layout.createSequentialGroup()
                                    .addComponent(jLabel60)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jlblPrecioDoritos))
                                .addGroup(jPanel14Layout.createSequentialGroup()
                                    .addComponent(jLabel61)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jlblPrecioPapas)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel69)
                                .addComponent(jLabel68)))))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59)
                    .addComponent(jlblPrecioSnack)
                    .addComponent(jLabel67))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60)
                    .addComponent(jlblPrecioDoritos)
                    .addComponent(jLabel68))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel61)
                    .addComponent(jlblPrecioPapas)
                    .addComponent(jLabel69))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel62)
                    .addComponent(jlblPrecioYogurt)
                    .addComponent(jLabel70))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 80, 500, 370));

        jPanel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel71.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel71.setText("DETALLE DE LA PROMOCION");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel71)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel71)
                .addContainerGap())
        );

        jPanel2.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 610, 260, 40));

        jPanel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jatxtDetallePromocion.setColumns(20);
        jatxtDetallePromocion.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jatxtDetallePromocion.setRows(5);
        jScrollPane7.setViewportView(jatxtDetallePromocion);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 660, 440, 150));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo2.jpg"))); // NOI18N
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 1550, 850));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 330, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnRegresarActionPerformed
        MainPrincipal menuPrincipal = new MainPrincipal();
        menuPrincipal.setVisible(true);
        salir();
    }//GEN-LAST:event_jbnRegresarActionPerformed

    private void jtxtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtApellidoActionPerformed

    private void jtxtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtNombreActionPerformed

    private void jtxtCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtCedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtCedulaActionPerformed

    private void jtblPersonasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtblPersonasMouseClicked
        cargarDatosTxt();
    }//GEN-LAST:event_jtblPersonasMouseClicked

    private void jbtnOrdenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnOrdenarActionPerformed
        ordenar();
    }//GEN-LAST:event_jbtnOrdenarActionPerformed

    private void jbtnTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnTotalActionPerformed
        total();
    }//GEN-LAST:event_jbtnTotalActionPerformed

    private void jcbxPromocionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbxPromocionesActionPerformed


    }//GEN-LAST:event_jcbxPromocionesActionPerformed

    private void jcbxPromocionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcbxPromocionesMouseClicked
        // llenarListas();
    }//GEN-LAST:event_jcbxPromocionesMouseClicked

    private void jbtnOrdenarPromocionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnOrdenarPromocionActionPerformed
        ordenarPromocion();

    }//GEN-LAST:event_jbtnOrdenarPromocionActionPerformed

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
            java.util.logging.Logger.getLogger(Restaurante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Restaurante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Restaurante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Restaurante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Restaurante().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTextArea jatxtDetallePromocion;
    private javax.swing.JTextArea jatxtDetalles;
    private javax.swing.JButton jbnRegresar;
    private javax.swing.JButton jbtnOrdenar;
    private javax.swing.JButton jbtnOrdenarPromocion;
    private javax.swing.JButton jbtnTotal;
    private javax.swing.JComboBox<String> jcbxMenu;
    private javax.swing.JComboBox<String> jcbxPromociones;
    private javax.swing.JLabel jlblPrecioArroz;
    private javax.swing.JLabel jlblPrecioChocolate;
    private javax.swing.JLabel jlblPrecioDoritos;
    private javax.swing.JLabel jlblPrecioFresa;
    private javax.swing.JLabel jlblPrecioHamburguesas;
    private javax.swing.JLabel jlblPrecioJugo;
    private javax.swing.JLabel jlblPrecioJugoMora;
    private javax.swing.JLabel jlblPrecioJugoPinia;
    private javax.swing.JLabel jlblPrecioMalteada;
    private javax.swing.JLabel jlblPrecioNaranja;
    private javax.swing.JLabel jlblPrecioNatural;
    private javax.swing.JLabel jlblPrecioPapas;
    private javax.swing.JLabel jlblPrecioPeperoni;
    private javax.swing.JLabel jlblPrecioPostre;
    private javax.swing.JLabel jlblPrecioQueso;
    private javax.swing.JLabel jlblPrecioSnack;
    private javax.swing.JLabel jlblPrecioSopa;
    private javax.swing.JLabel jlblPrecioTostada;
    private javax.swing.JLabel jlblPrecioVainilla;
    private javax.swing.JLabel jlblPrecioYogurt;
    private javax.swing.JLabel jlblTotal;
    private javax.swing.JList<String> jlistJugos;
    private javax.swing.JList<String> jlistMalteadas;
    private javax.swing.JList<String> jlistSnack;
    private javax.swing.JList<String> jlistTostadas;
    private javax.swing.JTable jtblPersonas;
    private javax.swing.JTextField jtxtApellido;
    private javax.swing.JTextField jtxtCedula;
    private javax.swing.JTextField jtxtNombre;
    // End of variables declaration//GEN-END:variables
}
