package Mantenedores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.*;
import static java.lang.Integer.parseInt;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.RowSorter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
public class MantFacturas extends javax.swing.JFrame {
    private Statement sentencia;
    private Connection conexion;
    private String nomBD = "cementerio";
    private String usuario = "root";
    private String password = "";
    private String msj;
    Object[][] data = {};
    String[] columnNames = {"Numero factura", "Rut empresa", "Fecha emisión", "Forma pago", "Fecha vencimiento", "Valor IVA" , "Estado", "Valor neto"};
    DefaultTableModel modelolista = new DefaultTableModel(data, columnNames) {
        public boolean isCellEditable(int rowIndex, int vColIndex) {
            return false;
        }
    };
   
    public MantFacturas() {
       initComponents();
        this.setLocationRelativeTo(null);
        conectar();
        lbl_estado.setVisible(false);
        lstfact.setModel(modelolista);
        llenarlst();
        transparenciaButton();
    }

    public void conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/" + this.nomBD;
            this.conexion = DriverManager.getConnection(url, this.usuario, this.password);
            this.sentencia = (Statement) this.conexion.createStatement();
        } catch (Exception e) {
            msj = "error al conectar";
            JOptionPane.showMessageDialog(null, msj, "Datos Guardados", JOptionPane.INFORMATION_MESSAGE);

        }
    }
    
    
    public void transparenciaButton() {
        btn_agregar.setOpaque(false);
        btn_agregar.setContentAreaFilled(false);
        btn_agregar.setBorderPainted(false);
        btn_modificar.setOpaque(false);
        btn_modificar.setContentAreaFilled(false);
        btn_modificar.setBorderPainted(false);
        btn_eliminar.setOpaque(false);
        btn_eliminar.setContentAreaFilled(false);
        btn_eliminar.setBorderPainted(false);
       
    }
    
    public void limpiarlbl() {
        lb_err1.setText("");
        
    }
    
    
    
    public void llenarlst() {
        String nro, run, dv, fechaemi, pago, tipo2 = "", fechaven, iva, valor, estado, estado2 ="";
        Date fech = null;
        lstfact.setPreferredScrollableViewportSize(new Dimension(500, 70));
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelolista);
        lstfact.setRowSorter(sorter);
        try {
            sentencia = (Statement) conexion.createStatement();//permite ejecutar sentencias SQL
            ResultSet lista = sentencia.executeQuery("SELECT * FROM facturas");
            while (lista.next()) {
                
                nro = (lista.getString("nro_factura"));
                run = (lista.getString("run_empresa"));
                dv = (lista.getString("dv_empresa"));
                
                fechaemi = lista.getString("fecha_emision");
                SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    fech = parseador.parse(fechaemi);
                } catch (ParseException ex) {
                    Logger.getLogger(MantSepultados.class.getName()).log(Level.SEVERE, null, ex);
                }
                fechaemi = String.format(format.format(fech));
                
                pago = (lista.getString("forma_pago"));
                switch (pago) {
                    case "Efectivo":
                        tipo2 = "Efectivo";
                        break;
                    case "Tarjeta":
                        tipo2 = "Tarjeta";
                        break;
                     case "Cheque":
                        tipo2 = "Cheque";
                        break;   
                        
                }
                
                fechaven = lista.getString("fecha_vencimiento");
                 parseador = new SimpleDateFormat("yyyy-MM-dd");
                 format = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    fech = parseador.parse(fechaven);
                } catch (ParseException ex) {
                    Logger.getLogger(MantSepultados.class.getName()).log(Level.SEVERE, null, ex);
                }
                fechaven = String.format(format.format(fech));
                
                iva = (lista.getString("valor_iva"));
                valor = (lista.getString("valor_neto_factura"));
                
                estado = (lista.getString("estado"));
                switch (estado) {
                    case "V":
                        estado2 = "Vigente";
                        break;
                    case "A":
                        estado2 = "Anulada";
                        break;
                      
                        
                }
                
                
                
                Object[] newRow = {nro, run + "-" + dv, fechaemi, tipo2, fechaven, iva, estado2,  valor};
                modelolista.addRow(newRow);
            }
            lstfact.getRowSorter().toggleSortOrder(0);
            lstfact.getRowSorter().toggleSortOrder(0);
            limpiarlbl();
        } catch (SQLException e) {
            msj = "no tiene elementos";
        }
    }
    
    
    
    public void llenarlst_consu(String consu) {
        String nro, run, dv, pago, tipo2 = "",valor, estado, iva,  estado2 ="";
        Date fechaemi, fechaven;
        lstfact.setPreferredScrollableViewportSize(new Dimension(500, 70));
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelolista);
        lstfact.setRowSorter(sorter);
        try {
            sentencia = (Statement) conexion.createStatement();//permite ejecutar sentencias SQL
            ResultSet lista = sentencia.executeQuery("SELECT * FROM facturas WHERE run_empresa like '%" + consu + "%'or fecha_emision like '%" + consu + "%'"
                    + "or estado like '%" + consu + "%' or forma_pago like '%" + consu + "%'");
            while (lista.next()) {
               nro = (lista.getString("nro_factura"));
                run = (lista.getString("run_empresa"));
                dv = (lista.getString("dv_empresa"));
                
                fechaemi = lista.getDate("fecha_emision");
                
                
                pago = (lista.getString("forma_pago"));
                switch (pago) {
                    case "Efectivo":
                        tipo2 = "Efectivo";
                        break;
                    case "Tarjeta":
                        tipo2 = "Tarjeta";
                        break;
                     case "Cheque":
                        tipo2 = "Cheque";
                        break;   
                        
                }
                
                fechaven = lista.getDate("fecha_vencimiento");
                 
                iva = (lista.getString("valor_iva"));
                valor = (lista.getString("valor_neto_factura"));
                
                estado = (lista.getString("estado"));
                switch (estado) {
                    case "V":
                        estado2 = "Vigente";
                        break;
                    case "A":
                        estado2 = "Anulada";
                        break;
                      
                        
                }
                

                Object[] newRow = {nro, run + "-" + dv, fechaemi, tipo2, fechaven, iva, estado2, valor};
                modelolista.addRow(newRow);
            }
            if (modelolista.getRowCount() == 0 || (txt_consulta.getText().startsWith(" "))) {
                msj = "No se encontró lo solicitado";
                JOptionPane.showMessageDialog(null, msj, "CONSULTA SIN DATOS", JOptionPane.INFORMATION_MESSAGE);
                modelolista.setNumRows(0);
                llenarlst();
                txt_consulta.setText("");
                txt_consulta.requestFocus();
            }
            lstfact.getRowSorter().toggleSortOrder(0);
            lstfact.getRowSorter().toggleSortOrder(0);
        } catch (SQLException e) {
            msj = "consulta errónea";
            JOptionPane.showMessageDialog(null, msj, "Falla Consulta", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    ;
    
    public void limpiar() {
        txt_nro.setText("");
        txt_consulta.setText("");
        txt_rut.setText("");
        dc_fechaemi.setDate(null);
        cmb_pago.setSelectedIndex(0);
        jLabel6.enable(false);
        dc_fechaven.setDate(null);
        txt_valor.setText("");
        cmb_estado.setSelectedIndex(0);
        
        

        lstfact.clearSelection();
    }
    
    public void formatearfecha() {
        dc_fechaemi.setDateFormatString("dd-MM-yyyy");
        dc_fechaven.setDateFormatString("dd-MM-yyyy");
    }
    
    
    public void insertar(String sql) {
        try {
            sentencia.executeUpdate(sql);
            msj = "datos guardados";
            JOptionPane.showMessageDialog(null, msj, "Datos Guardados", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            msj = "No ingresó";
            JOptionPane.showMessageDialog(null, msj, "AlertaBD", JOptionPane.INFORMATION_MESSAGE);

        }
        modelolista.setNumRows(0);
        //lstClie.remove();
        llenarlst();
        limpiar();
    }
    
    
    public void actualizar(String nro, String rut, String dv, String fechaemi, String pago, String fechaven, int iva, String estado, int valor) {
        String upSQL = "";

        try {
            upSQL = "UPDATE facturas SET fecha_emision='" + fechaemi + "',forma_pago='" + pago + "',fecha_vencimiento='" + fechaven + "',valor_iva=" + iva + ",estado='" + estado + "',valor_neto_factura="+ valor  + " WHERE nro_factura=" + nro + "";
            sentencia.executeUpdate(upSQL);
            msj = "Datos actualizados";
            JOptionPane.showMessageDialog(null, msj, "Datos actualizados", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            msj = "No actualizado";
            JOptionPane.showMessageDialog(null, msj, "Datos actualizados", JOptionPane.INFORMATION_MESSAGE);
        }
        modelolista.setNumRows(0);
        //lstClie.remove();
        llenarlst();
        limpiar();
    }
    
    
    public void eliminar(String nro, String rut, String dv, Date fechaemi, String pago, Date fechaven, String iva , String estado, String valor) {
        String upSQL = "";
        /*Mensaje message=new Mensaje();
        message.windowClosed(e);*/

        try {
            upSQL = "DELETE FROM facturas WHERE nro_factura='" + nro + "';";
            sentencia.executeUpdate(upSQL);
            msj = "Datos Eliminados";
            JOptionPane.showMessageDialog(null, msj, "ELIMINACIÓN DE DATOS", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            msj = "No eliminado";
            JOptionPane.showMessageDialog(null, msj, "ALERTA", JOptionPane.INFORMATION_MESSAGE);
        }
        modelolista.setNumRows(0);
        //lstClie.remove();
        llenarlst();
        limpiar();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstfact = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        txt_rut = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txt_valor = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        cmb_estado = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        txt_nro = new javax.swing.JTextField();
        txt_consulta = new javax.swing.JTextField();
        dc_fechaemi = new com.toedter.calendar.JDateChooser();
        dc_fechaven = new com.toedter.calendar.JDateChooser();
        btn_agregar = new javax.swing.JButton();
        btn_modificar = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        txt_dv = new javax.swing.JTextField();
        cmb_pago = new javax.swing.JComboBox<>();
        lb_err1 = new javax.swing.JLabel();
        lbl_estado = new javax.swing.JLabel();
        txt_iva = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cruzmini.png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/consultar.png"))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Franklin Gothic Medium", 3, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Facturas");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Nro Factura");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Rut empresa");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Fecha Vencimiento");

        lstfact.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"4400", "30889765-0", "29/09/2017", "Efectivo", "02/12/2017", "300876", "Vigente"},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Nro Factura", "Rut Empresa", "Fecha emisión", "Forma pago", "Fecha vencimiento", "Valor Neto", "Estado"
            }
        ));
        lstfact.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstfactMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lstfact);

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Forma Pago");

        txt_rut.setBackground(new java.awt.Color(255, 255, 204));
        txt_rut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_rutActionPerformed(evt);
            }
        });

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Fecha emisión");

        txt_valor.setBackground(new java.awt.Color(255, 255, 204));
        txt_valor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_valorActionPerformed(evt);
            }
        });

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Estado");

        cmb_estado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccione una Opción>", "Vigente", "Anulada" }));

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Valor Neto");

        jButton2.setBackground(new java.awt.Color(102, 0, 0));
        jButton2.setFont(new java.awt.Font("Franklin Gothic Medium", 3, 13)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cancelar.png"))); // NOI18N
        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Franklin Gothic Medium", 3, 13)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/detalles.png"))); // NOI18N
        jButton3.setText("Detalle Factura");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txt_nro.setBackground(new java.awt.Color(255, 255, 204));
        txt_nro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nroActionPerformed(evt);
            }
        });

        txt_consulta.setBackground(new java.awt.Color(255, 255, 204));
        txt_consulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_consultaActionPerformed(evt);
            }
        });

        btn_agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/agregar.png"))); // NOI18N
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
            }
        });

        btn_modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/modificar.png"))); // NOI18N
        btn_modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modificarActionPerformed(evt);
            }
        });

        btn_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/eliminar.png"))); // NOI18N
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });

        txt_dv.setBackground(new java.awt.Color(255, 255, 204));
        txt_dv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_dvFocusLost(evt);
            }
        });
        txt_dv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_dvActionPerformed(evt);
            }
        });

        cmb_pago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccione una opción>", "Efectivo", "Tarjeta", "Cheque" }));

        lb_err1.setForeground(new java.awt.Color(255, 0, 51));
        lb_err1.setText("jLabel2");

        txt_iva.setBackground(new java.awt.Color(255, 255, 204));
        txt_iva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ivaActionPerformed(evt);
            }
        });

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Valor Iva");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel14)
                                                    .addComponent(jLabel9)
                                                    .addComponent(dc_fechaven, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(48, 48, 48))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(cmb_estado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(0, 0, 0)))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(98, 98, 98)
                                                .addComponent(lb_err1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txt_iva, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel12))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel10)
                                                    .addComponent(txt_valor, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(169, 169, 169)))
                                        .addComponent(jButton2)))
                                .addGap(78, 78, 78))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_nro, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txt_rut, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_dv, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel11)
                                    .addComponent(dc_fechaemi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmb_pago, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator2)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(176, 176, 176)
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lbl_estado)
                                        .addGap(114, 114, 114))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btn_agregar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_modificar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_eliminar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_consulta, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3)
                                .addGap(22, 22, 22)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(23, 23, 23))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lbl_estado)
                                .addGap(18, 18, 18)))
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txt_consulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btn_eliminar)
                        .addComponent(btn_modificar)
                        .addComponent(btn_agregar, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton3)
                        .addComponent(jLabel4)))
                .addGap(21, 21, 21)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 1, Short.MAX_VALUE)
                        .addComponent(txt_nro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_rut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_dv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dc_fechaemi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmb_pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_valor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_iva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(dc_fechaven, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmb_estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb_err1)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jButton2)))
                .addContainerGap(62, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_rutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rutActionPerformed

    private void txt_valorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_valorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_valorActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txt_nroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nroActionPerformed

    private void txt_consultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_consultaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_consultaActionPerformed

    private void txt_dvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_dvFocusLost
        String codigo;
        int multiplo = 2;
        int cont = 0;
        //módulo 11 para cálculo de DV
        for (int x = 0; x < txt_rut.getText().length(); x++) {
            cont = cont + (Integer.parseInt(txt_rut.getText().substring(txt_rut.getText().length() - x - 1, txt_rut.getText().length() - x)) * multiplo);
            multiplo++;
            if (multiplo == 8) {
                multiplo = 2;
            }
        }
        cont = 11 - (cont % 11);
        if (cont <= 9) {
            codigo = "" + cont;
        } else if (cont == 11) {
            codigo = "0";
        } else {
            codigo = "K";
        }
        if (!txt_dv.getText().equals(codigo)) {
            //lb_err1.setText("Run inválido, ingrese nuevamente!");
            txt_rut.setText("");
            txt_dv.setText("");
            txt_rut.requestFocus();
        }
    }//GEN-LAST:event_txt_dvFocusLost

    private void txt_dvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dvActionPerformed

    private void txt_ivaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ivaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ivaActionPerformed

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
         String nro, rut, dv, fechaemi2, pago = "", fechaven2, iva , estado = "", valor ;
        int nro2, iva2, valor2;
        Date fechaemi, fechaven;

        
        nro = txt_nro.getText();
        if (txt_nro.getText().isEmpty() || txt_nro.getText().startsWith(" ")) {
            lb_err1.setText("Ingrese un n° de factura");
            txt_nro.setFocusable(rootPaneCheckingEnabled);
            return;
        }

        nro2 = parseInt(nro);
        //
        
        rut = txt_rut.getText().toUpperCase();
        if (txt_rut.getText().isEmpty() || txt_rut.getText().startsWith(" ")) {
            lb_err1.setText("Ingrese un rut");
            txt_rut.requestFocus();
            return;
        }
         //
        dv = txt_dv.getText().toUpperCase(); 
        //
        dc_fechaemi.setDateFormatString("yyyy-MM-dd");
        fechaemi2 = dc_fechaemi.getDateFormatString();
        fechaemi = dc_fechaemi.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat(fechaemi2);
        fechaemi2 = String.valueOf(sdf.format(fechaemi)); 
        
        // 
        int index = cmb_pago.getSelectedIndex();

        switch (index) {
            case 1:
                pago = "Efectivo";
                break;
            case 2:
                pago = "Tarjeta";
                break;
            case 3:
                pago = "Cheque";
                break;       
            default:
                lb_err1.setText("Debe seleccionar un tipo de pago");
                break;
        }
        //
        dc_fechaven.setDateFormatString("yyyy-MM-dd");
        fechaven2 = dc_fechaven.getDateFormatString();
        fechaven = dc_fechaven.getDate();
        sdf = new SimpleDateFormat(fechaven2);
        fechaven2 = String.valueOf(sdf.format(fechaven)); 
        //
         iva = txt_iva.getText();
        if (txt_iva.getText().isEmpty() || txt_iva.getText().startsWith(" ")) {
            lb_err1.setText("Ingrese un iva");
            txt_iva.setFocusable(rootPaneCheckingEnabled);
            return;
        }
        iva2 = parseInt(iva);
        //
        int index2 = cmb_estado.getSelectedIndex();

        switch (index2) {
            case 1:
                estado = "V";
                break;
            case 2:
                estado = "A";
                break;
                  
            default:
                lb_err1.setText("Debe seleccionar un estado");
                break;
        }
        //
        
        valor = txt_valor.getText();
        if (txt_valor.getText().isEmpty() || txt_valor.getText().startsWith(" ")) {
            lb_err1.setText("Ingrese un valor");
            txt_valor.setFocusable(rootPaneCheckingEnabled);
            return;
        }
        valor2 = parseInt(valor);

        

       
        /*if(txt_fechnac.getDate().equals("")){
            lb_err5.setText("Ingrese la fecha de nacimiento");
            txt_fechnac.requestFocus();
            return;
        }*/

        
        String sql = "INSERT INTO facturas(nro_factura,rut_empresa,dv_empresa,fecha_emision,forma_pago,fecha_vencimiento,valor_iva,estado,valor_neto_factura) "
                + "VALUES("+ nro + ",'" + rut + "','" + dv + "','" + fechaemi2 + "','" + pago + "','" + fechaven2 + "', " + iva2 + ", '"+ estado + "', " + valor2 +")";
        //msj = sql;
        //JOptionPane.showMessageDialog(null, msj, "Datos Guardados", JOptionPane.INFORMATION_MESSAGE);
        insertar(sql);
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void btn_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificarActionPerformed
        String nro, rut, dv, fechaemi2, pago = "", fechaven2, iva , estado = "", valor ;
        Date fechaemi, fechaven;
        int nro2, iva2, valor2;
        if (lstfact.getSelectedRowCount() > 0) {
            
            nro = txt_nro.getText();
            nro2 = parseInt(nro);
            
            rut = txt_rut.getText();
            dv = txt_dv.getText().toUpperCase();
            
            
            dc_fechaemi.setDateFormatString("yyyy-MM-dd");
            fechaemi2 = dc_fechaemi.getDateFormatString();
            fechaemi = dc_fechaemi.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat(fechaemi2);
            fechaemi2 = String.valueOf(sdf.format(fechaemi));
            //
            int index = cmb_pago.getSelectedIndex();

            switch (index) {
                case 1:
                    pago = "Efectivo";
                    break;
                case 2:
                    pago = "Tarjeta";
                    break;
                case 3:
                    pago = "Cheque";
                    break;
                default:
                    lb_err1.setText("Debe seleccionar un tipo de pago");
                    break;
            }
            //
            dc_fechaven.setDateFormatString("yyyy-MM-dd");
            fechaven2 = dc_fechaven.getDateFormatString();
            fechaven = dc_fechaven.getDate();
            sdf = new SimpleDateFormat(fechaven2);
            fechaven2 = String.valueOf(sdf.format(fechaven)); 
            //
            iva = txt_iva.getText();
            if (txt_iva.getText().isEmpty() || txt_iva.getText().startsWith(" ")) {
            lb_err1.setText("Ingrese un iva");
            txt_iva.setFocusable(rootPaneCheckingEnabled);
            return;
        }
            iva2 = parseInt(iva);
            //
            int index2 = cmb_estado.getSelectedIndex();

            switch (index2) {
            case 1:
                estado = "V";
                break;
            case 2:
                estado = "A";
                break;
                  
            default:
                lb_err1.setText("Debe seleccionar un estado");
                break;
        }
            //
            valor = txt_valor.getText();
             if (txt_valor.getText().isEmpty() || txt_valor.getText().startsWith(" ")) {
            lb_err1.setText("Ingrese un valor");
            txt_valor.setFocusable(rootPaneCheckingEnabled);
            return;
        }
            valor2 = parseInt(valor);
            
            
            limpiarlbl();

            actualizar(nro, rut, dv, fechaemi2, pago, fechaven2, iva2, estado, valor2);
        } else {
            msj = "Seleccione una fila para modificar";
            JOptionPane.showMessageDialog(null, msj, "MODIFICACIÓN DE DATOS", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btn_modificarActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        String nro, rut, dv, fechaemi2, pago = "", fechaven2, iva , estado = "", valor ;
        Date fechaemi, fechaven;
        
        
        if (lstfact.getSelectedRowCount() > 0) {
            nro = txt_nro.getText();
            rut = txt_rut.getText();
            dv = txt_dv.getText().toUpperCase();
            fechaemi = dc_fechaemi.getDate();
            int index = cmb_pago.getSelectedIndex();
            switch (index) {
                case 1:
                    pago = "Efectivo";
                    break;
                case 2:
                    pago = "Tarjeta";
                    break;
                case 3:
                    pago = "Cheque";
                    break;   
            }
            fechaven = dc_fechaven.getDate();
            iva = txt_iva.getText();
            int index2 = cmb_estado.getSelectedIndex();
            switch (index2) {
                case 1:
                    estado = "V";
                    break;
                case 2:
                    estado = "A";
                    break;
                   
            }
            valor = txt_valor.getText();
            

            if (JOptionPane.showConfirmDialog(rootPane, "Se eliminará el registro",
                    "Eliminar", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                eliminar(nro, rut, dv, fechaemi, pago, fechaven, iva, estado, valor);
            }
        } else {
            msj = "NO SE PUEDEN ELIMINAR DATOS";
            JOptionPane.showMessageDialog(null, msj, "ELIMINACIÓN DE DATOS", JOptionPane.INFORMATION_MESSAGE);
        }
        llenarlst();
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void lstfactMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstfactMouseClicked
        String nro, rut, dv, fechaemi, pago = "", fechaven, iva , estado = "", valor ;
        java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("dd-MM-yyyy");

        if (lstfact.getSelectedRowCount() > 0) {
            nro = lstfact.getValueAt(lstfact.getSelectedRow(), 0).toString();
            jLabel6.enable();
            txt_nro.setText(nro);
            
            rut = lstfact.getValueAt(lstfact.getSelectedRow(), 1).toString();
            rut = rut.substring(0, rut.length() - 2);
            txt_rut.setText(rut);
            txt_rut.disable();
            dv = lstfact.getValueAt(lstfact.getSelectedRow(), 1).toString();
            dv = dv.substring(dv.length() - 1, dv.length());
            txt_dv.setText(dv);
            txt_dv.disable();
            fechaemi = lstfact.getValueAt(lstfact.getSelectedRow(), 2).toString();
            try {
                java.util.Date fechDate = formato.parse(fechaemi);
                dc_fechaemi.setDate(fechDate);
            } catch (ParseException ex) {
                Logger.getLogger(MantSepultados.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            pago=lstfact.getValueAt(lstfact.getSelectedRow(), 3).toString();
            switch (pago) {
                case "Efectivo":
                    cmb_pago.setSelectedItem("Efectivo");
                    break;
                case "Tarjeta":
                    cmb_pago.setSelectedItem("Tarjeta");
                    break;
                case "Cheque":
                    cmb_pago.setSelectedItem("Cheque");
                    break;    
            }
            fechaven = lstfact.getValueAt(lstfact.getSelectedRow(), 4).toString();
            try {
                java.util.Date fechDate = formato.parse(fechaven);
                dc_fechaven.setDate(fechDate);
            } catch (ParseException ex) {
                Logger.getLogger(MantSepultados.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            txt_iva.setText(lstfact.getValueAt(lstfact.getSelectedRow(), 5).toString());
            
            estado=lstfact.getValueAt(lstfact.getSelectedRow(), 6).toString();
            switch (estado) {
                case "V":
                    cmb_pago.setSelectedItem("Vigente");
                    break;
                case "A":
                    cmb_pago.setSelectedItem("Anulada");
                    break;
                    
            }
            
            
            txt_valor.setText(lstfact.getValueAt(lstfact.getSelectedRow(), 7).toString());
            

            limpiarlbl();
        }
    }//GEN-LAST:event_lstfactMouseClicked

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
            java.util.logging.Logger.getLogger(MantFacturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MantFacturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MantFacturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MantFacturas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MantFacturas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_modificar;
    private javax.swing.JComboBox<String> cmb_estado;
    private javax.swing.JComboBox<String> cmb_pago;
    private com.toedter.calendar.JDateChooser dc_fechaemi;
    private com.toedter.calendar.JDateChooser dc_fechaven;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lb_err1;
    private javax.swing.JLabel lbl_estado;
    private javax.swing.JTable lstfact;
    private javax.swing.JTextField txt_consulta;
    private javax.swing.JTextField txt_dv;
    private javax.swing.JTextField txt_iva;
    private javax.swing.JTextField txt_nro;
    private javax.swing.JTextField txt_rut;
    private javax.swing.JTextField txt_valor;
    // End of variables declaration//GEN-END:variables
}
