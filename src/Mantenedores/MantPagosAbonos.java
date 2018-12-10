package Mantenedores;

import Menúes.MenuPrincipal;
import com.toedter.calendar.JDateChooser;
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
import javax.swing.RowSorter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MantPagosAbonos extends javax.swing.JFrame {
    private Statement sentencia;
    private Connection conexion;
    private String nomBD = "cementerio";
    private String usuario = "root";
    private String password = "";
    private String msj;
    Object[][] data = {};
    String[] columnNames = {"Codigo pago", "Fecha pago", "Tipo Trnasacción", "Monto pago", "Codigo venta"};
    DefaultTableModel modelolista = new DefaultTableModel(data, columnNames) {
        public boolean isCellEditable(int rowIndex, int vColIndex) {
            return false;
        }
    };
    
    Object[][] data2 = {};
    String[] columnNames2 = {"Rut cliente", "Codigo venta"};
    DefaultTableModel modelolista2 = new DefaultTableModel(data2, columnNames2) {
        public boolean isCellEditable(int rowIndex, int vColIndex) {
            return false;
        }
    };
   
    public MantPagosAbonos() {
        initComponents();
        this.setLocationRelativeTo(null);
        conectar();
        lbl_estado.setVisible(false);
        lstpago.setModel(modelolista);
        lstventa.setModel(modelolista2);
        llenarlst1();
        llenarlst2();
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
        lb_err2.setText("");
       lb_err3.setText("");
       lb_err4.setText("");
    }
  public void llenarlst2() {
        String cod, fecha, tipo, tipo2 = "", monto, codventa;
        Date fech = null;

        lstpago.setPreferredScrollableViewportSize(new Dimension(500, 70));
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelolista);
        lstpago.setRowSorter(sorter);
        try {
            sentencia = (Statement) conexion.createStatement();//permite ejecutar sentencias SQL
            ResultSet lista = sentencia.executeQuery("SELECT * FROM  pagos_y_abonos");
            while (lista.next()) {
                cod = (lista.getString("cod_pago"));//nombre del campo de la BD
                tipo = (lista.getString("tipo_transaccion"));
                switch (tipo) {
                    case "P":
                        tipo2 = "Pago";
                        break;
                    case "A":
                        tipo2 = "Abono";
                        break;
                }
                monto = (lista.getString("monto_pago"));
                codventa = (lista.getString("cod_venta"));
                
                //Código para transformar la fecha rescatada y formatearla como String 
                fecha = lista.getString("fecha_pago");
                SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    fech = parseador.parse(fecha);
                } catch (ParseException ex) {
                    Logger.getLogger(MantSepultados.class.getName()).log(Level.SEVERE, null, ex);
                }
                fecha = String.format(format.format(fech));

                Object[] newRow = {cod, fecha, tipo2, monto, codventa};
                modelolista.addRow(newRow);
            }
            lstpago.getRowSorter().toggleSortOrder(0);
            lstpago.getRowSorter().toggleSortOrder(0);
            limpiarlbl();
        } catch (SQLException e) {
            msj = "no tiene elementos";
        }

    }

    ;
     
    
  
  public void llenarlst1() {
        String rut, cod;
        

        lstventa.setPreferredScrollableViewportSize(new Dimension(500, 70));
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelolista2);
        lstventa.setRowSorter(sorter);
        try {
            sentencia = (Statement) conexion.createStatement();//permite ejecutar sentencias SQL
            ResultSet lista = sentencia.executeQuery("SELECT * FROM  venta");
            while (lista.next()) {
                
                rut = (lista.getString("run_cliente"));
                cod = (lista.getString("cod_venta"));//nombre del campo de la BD
                
                Object[] newRow = {rut, cod};
                modelolista2.addRow(newRow);
            }
            lstventa.getRowSorter().toggleSortOrder(0);
            lstventa.getRowSorter().toggleSortOrder(0);
            limpiarlbl();
        } catch (SQLException e) {
            msj = "no tiene elementos";
        }

    }

    ;
    
    public void llenarlst_consu(String consu) {
        String cod, tipo, tipo2="", valor, nrofac;
        Date fecha;
        lstpago.setPreferredScrollableViewportSize(new Dimension(500, 70));
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelolista);
        lstpago.setRowSorter(sorter);
        try {
            sentencia = (Statement) conexion.createStatement();//permite ejecutar sentencias SQL
            ResultSet lista = sentencia.executeQuery("SELECT * FROM pagos_y_abonos WHERE tipo_transaccion like '%" + consu + "%'or monto_pago like '%" + consu + "%'"
                    + "or cod_venta like '%" + consu + "%' or fecha_pago like '%" + consu + "%'");
            while (lista.next()) {
                cod = (lista.getString("cod_pago"));//nombre del campo de la BD
                tipo = (lista.getString("tipo_transaccion"));
                switch (tipo) {
                    case "P":
                        tipo2 = "Pago";
                        break;
                    case "A":
                        tipo2 = "Abono";
                        break;
                }
                valor = (lista.getString("monto_pago"));
                nrofac = (lista.getString("cod_venta"));


                fecha = lista.getDate("fecha_pago");

                Object[] newRow = {cod, fecha, tipo2, valor, nrofac};
                modelolista.addRow(newRow);
            }
            if (modelolista.getRowCount() == 0 || (txt_consulta.getText().startsWith(" "))) {
                msj = "No se encontró lo solicitado";
                JOptionPane.showMessageDialog(null, msj, "CONSULTA SIN DATOS", JOptionPane.INFORMATION_MESSAGE);
                modelolista.setNumRows(0);
                llenarlst1();
                txt_consulta.setText("");
                txt_consulta.requestFocus();
            }
            lstpago.getRowSorter().toggleSortOrder(0);
            lstpago.getRowSorter().toggleSortOrder(0);
        } catch (SQLException e) {
            msj = "consulta errónea";
            JOptionPane.showMessageDialog(null, msj, "Falla Consulta", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    ;
    
    public void limpiar() {
        txt_consulta.setText("");
        dc_fecha.setDate(null);
        lb_cod.setText("");
        jLabel6.enable(false);
        txt_venta.setText("");

        txt_monto.setText("");
        cmb_tipo.setSelectedIndex(0);

        lstventa.clearSelection();
    }

    public void formatearfecha() {
        dc_fecha.setDateFormatString("dd-MM-yyyy");

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
        llenarlst2();
        limpiar();
    }
    
    
    public void actualizar(int cod, String fecha, String tipo, int valor, int nrofac) {
        String upSQL = "";

        try {
            upSQL = "UPDATE pagos_y_abonos SET fecha_pago='" + fecha + "',tipo_transaccion='" + tipo + "',monto_pago=" + valor + ",cod_venta=" + nrofac + " WHERE cod_pago=" + cod + "";
            sentencia.executeUpdate(upSQL);
            msj = "Datos actualizados";
            JOptionPane.showMessageDialog(null, msj, "Datos actualizados", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            msj = "No actualizado";
            JOptionPane.showMessageDialog(null, msj, "Datos actualizados", JOptionPane.INFORMATION_MESSAGE);
        }
        modelolista.setNumRows(0);
        //lstClie.remove();
        llenarlst2();
        limpiar();
    }
    
    public void eliminar(String cod, Date fecha, String tipo, String valor, String nrofac) {
        String upSQL = "";
        /*Mensaje message=new Mensaje();
        message.windowClosed(e);*/

        try {
            upSQL = "DELETE FROM pagos_y_abonos WHERE cod_pago='" + cod + "';";
            sentencia.executeUpdate(upSQL);
            msj = "Datos Eliminados";
            JOptionPane.showMessageDialog(null, msj, "ELIMINACIÓN DE DATOS", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            msj = "No eliminado";
            JOptionPane.showMessageDialog(null, msj, "ALERTA", JOptionPane.INFORMATION_MESSAGE);
        }
        modelolista.setNumRows(0);
        //lstClie.remove();
        llenarlst2();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        lstventa = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_monto = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        cmb_tipo = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        txt_consulta = new javax.swing.JTextField();
        dc_fecha = new com.toedter.calendar.JDateChooser();
        btn_agregar = new javax.swing.JButton();
        btn_modificar = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        lbl_estado = new javax.swing.JLabel();
        lb_err1 = new javax.swing.JLabel();
        txt_venta = new javax.swing.JTextField();
        lb_cod = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstpago = new javax.swing.JTable();
        lb_err2 = new javax.swing.JLabel();
        lb_err3 = new javax.swing.JLabel();
        lb_err4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cruzmini.png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/consultar.png"))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Franklin Gothic Medium", 3, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Pagos y Abonos");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Cod Pago");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Fecha pago");

        lstventa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"02/05/2018", "032"},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Run cliente", "Cod Pago"
            }
        ));
        lstventa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstventaMouseClicked(evt);
            }
        });
        lstventa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lstventaKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(lstventa);

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Tipo transacción");

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Cod venta");

        txt_monto.setBackground(new java.awt.Color(255, 255, 204));
        txt_monto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_montoActionPerformed(evt);
            }
        });
        txt_monto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_montoKeyTyped(evt);
            }
        });

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Monto Pago");

        cmb_tipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccione una opción>", "Pago", "Abono" }));

        jButton4.setBackground(new java.awt.Color(102, 0, 0));
        jButton4.setFont(new java.awt.Font("Franklin Gothic Medium", 3, 13)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cancelar.png"))); // NOI18N
        jButton4.setText("Cancelar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        txt_consulta.setBackground(new java.awt.Color(255, 255, 204));
        txt_consulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_consultaMouseClicked(evt);
            }
        });
        txt_consulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_consultaActionPerformed(evt);
            }
        });
        txt_consulta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_consultaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_consultaKeyTyped(evt);
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

        lb_err1.setForeground(new java.awt.Color(255, 0, 51));
        lb_err1.setText("jLabel2");

        txt_venta.setBackground(new java.awt.Color(255, 255, 204));
        txt_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ventaActionPerformed(evt);
            }
        });
        txt_venta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_ventaKeyTyped(evt);
            }
        });

        lb_cod.setForeground(new java.awt.Color(255, 255, 255));

        lstpago.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"032", "02/05/2018", "Abono", "100.000", "001"},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Cod Pago", "Fecha pago", "Tipo Transacción", "Monto Pago", "Cod venta"
            }
        ));
        lstpago.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstpagoMouseClicked(evt);
            }
        });
        lstpago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lstpagoKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(lstpago);
        if (lstpago.getColumnModel().getColumnCount() > 0) {
            lstpago.getColumnModel().getColumn(2).setHeaderValue("Tipo Transacción");
            lstpago.getColumnModel().getColumn(3).setHeaderValue("Monto Pago");
            lstpago.getColumnModel().getColumn(4).setHeaderValue("Cod venta");
        }

        lb_err2.setForeground(new java.awt.Color(255, 0, 51));
        lb_err2.setText("jLabel2");

        lb_err3.setForeground(new java.awt.Color(255, 0, 51));
        lb_err3.setText("jLabel2");

        lb_err4.setForeground(new java.awt.Color(255, 0, 51));
        lb_err4.setText("jLabel2");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_monto, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel13)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(dc_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lb_err1))
                    .addComponent(jLabel6)
                    .addComponent(jLabel16)
                    .addComponent(jLabel14)
                    .addComponent(cmb_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_cod)
                    .addComponent(txt_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_err2)
                    .addComponent(lb_err3)
                    .addComponent(lb_err4))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton4)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32))))
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
                                .addComponent(btn_agregar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_modificar)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txt_consulta, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_eliminar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(jLabel4)
                                .addGap(25, 25, 25))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(176, 176, 176)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_estado)
                                .addGap(89, 89, 89)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(23, 23, 23))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(lbl_estado)
                                        .addGap(18, 18, 18)))
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel4)
                                            .addComponent(txt_consulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(7, 7, 7)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btn_eliminar)
                                            .addComponent(btn_modificar)))))
                            .addComponent(btn_agregar, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(26, 26, 26)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(jLabel6)
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lb_cod)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dc_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lb_err1))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmb_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lb_err2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_monto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lb_err3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4)
                    .addComponent(lb_err4))
                .addContainerGap(27, Short.MAX_VALUE))
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

    private void txt_montoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_montoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_montoActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
          MenuPrincipal mp = new MenuPrincipal();
        mp.setVisible(true);
        this.setVisible(false);
        
        //System.exit(0);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txt_consultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_consultaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_consultaActionPerformed

    private void txt_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ventaActionPerformed

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
        String cod, tipo = "", valor, nrofac, fecha2;
        int valor2, nrofact2;
        Date fecha;

        //cod=lb_cod.getText();
        nrofac = txt_venta.getText();
        if (txt_venta.getText().isEmpty() || txt_venta.getText().startsWith(" ")) {
            lb_err4.setText("");
            txt_venta.setFocusable(rootPaneCheckingEnabled);
            return;
        }

        nrofact2 = parseInt(nrofac);
        
        valor = txt_monto.getText();
        if (txt_monto.getText().isEmpty() || txt_monto.getText().startsWith(" ")) {
            lb_err3.setText("Ingrese un monto");
            txt_monto.setFocusable(rootPaneCheckingEnabled);
            return;
        }
        valor2 = parseInt(valor);

        

        dc_fecha.setDateFormatString("yyyy-MM-dd");
        fecha2 = dc_fecha.getDateFormatString();
        fecha = dc_fecha.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat(fecha2);
        fecha2 = String.valueOf(sdf.format(fecha));
        /*if(txt_fechnac.getDate().equals("")){
            lb_err5.setText("Ingrese la fecha de nacimiento");
            txt_fechnac.requestFocus();
            return;
        }*/

        int index = cmb_tipo.getSelectedIndex();

        switch (index) {
            case 1:
                tipo = "P";
                break;
            case 2:
                tipo = "A";
                break;

            default:
                lb_err2.setText("Debe seleccionar un tipo de venta!");
                break;
        }
        String sql = "INSERT INTO pagos_y_abonos(fecha_pago,tipo_transaccion,monto_pago,cod_venta) "
                + "VALUES('" + fecha2 + "','" + tipo + "'," + valor2 + "," + nrofact2 + ")";
//        msj = sql;
//        JOptionPane.showMessageDialog(null, msj, "Datos Guardados", JOptionPane.INFORMATION_MESSAGE);
        insertar(sql);
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void btn_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificarActionPerformed
       String cod, tipo = "", valor, nrofac, fecha2;
        Date fecha;
         int codigo;
        if (lstpago.getSelectedRowCount() > 0) {

            cod = lstpago.getValueAt(lstpago.getSelectedRow(), 0).toString();
            codigo=parseInt(cod);

            nrofac = txt_venta.getText();
            if (txt_venta.getText().isEmpty() || txt_venta.getText().startsWith(" ")) {
                lb_err4.setText("");
                txt_venta.setFocusable(rootPaneCheckingEnabled);
                return;
            }
            int nrofact2 = parseInt(nrofac);

            valor = txt_monto.getText();
            if (txt_monto.getText().isEmpty() || txt_monto.getText().startsWith(" ")) {
                lb_err3.setText("Ingrese un monto");
                txt_monto.setFocusable(rootPaneCheckingEnabled);
                return;
            }
            int valor2 = parseInt(valor);

           

            dc_fecha.setDateFormatString("yyyy-MM-dd");
            fecha2 = dc_fecha.getDateFormatString();
            fecha = dc_fecha.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat(fecha2);
            fecha2 = String.valueOf(sdf.format(fecha));

            int index = cmb_tipo.getSelectedIndex();

            switch (index) {
                case 1:
                    tipo = "P";
                    break;
                case 2:
                    tipo = "A";
                    break;

                default:
                    lb_err2.setText("Debe seleccionar un tipo de venta!");
                    break;
            }

            limpiarlbl();

            actualizar(codigo, fecha2, tipo, valor2, nrofact2);
        } else {
            msj = "Seleccione una fila para modificar";
            JOptionPane.showMessageDialog(null, msj, "MODIFICACIÓN DE DATOS", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btn_modificarActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
         String cod, tipo = "", valor, nrofac;
        Date fecha;
        int index = cmb_tipo.getSelectedIndex();
        if (lstpago.getSelectedRowCount() > 0) {
            cod = lstpago.getValueAt(lstpago.getSelectedRow(), 0).toString();
            valor = txt_monto.getText();
            nrofac = txt_venta.getText();

            fecha = dc_fecha.getDate();
            switch (index) {
                case 1:
                    tipo = "P";
                    break;
                case 2:
                    tipo = "A";
                    break;

            }

            if (JOptionPane.showConfirmDialog(rootPane, "Se eliminará el registro",
                    "Eliminar", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                eliminar(cod, fecha, tipo, valor, nrofac);
            }
        } else {
            msj = "NO SE PUEDEN ELIMINAR DATOS";
            JOptionPane.showMessageDialog(null, msj, "ELIMINACIÓN DE DATOS", JOptionPane.INFORMATION_MESSAGE);
        }
        //llenarlst2();
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void lstventaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstventaMouseClicked
           String cod;
        java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("dd-MM-yyyy");

        if (lstventa.getSelectedRowCount() > 0) {
            
            cod = lstventa.getValueAt(lstventa.getSelectedRow(), 1).toString();
            //String msj = cod;
            //JOptionPane.showMessageDialog(null, msj, "ELIMINACIÓN DE DATOS", JOptionPane.INFORMATION_MESSAGE);
            jLabel6.enable();
            txt_venta.setText(cod);
            txt_venta.disable();
            

            limpiarlbl();
        }
    }//GEN-LAST:event_lstventaMouseClicked

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
      modelolista.setNumRows(0);
        limpiarlbl();
        llenarlst2();
        txt_consulta.setText("");
        dc_fecha.setDate(null);
        txt_monto.setText("");
        txt_venta.setText("");
        cmb_tipo.setSelectedIndex(0);
        lb_cod.setText("");
       
        
        txt_venta.enable();
        
        lstventa.clearSelection();
    }//GEN-LAST:event_jPanel1MouseClicked

    private void lstpagoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstpagoMouseClicked
        String cod, fecha, tipo = "", valor, nrofac, descripcion;
        java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("dd-MM-yyyy");

        if (lstpago.getSelectedRowCount() > 0) {
            cod = lstpago.getValueAt(lstpago.getSelectedRow(), 0).toString();
            jLabel6.enable();
            lb_cod.setText(cod);
            txt_monto.setText(lstpago.getValueAt(lstpago.getSelectedRow(), 3).toString());
            txt_venta.setText(lstpago.getValueAt(lstpago.getSelectedRow(), 4).toString());
            txt_venta.disable();
            
            fecha = lstpago.getValueAt(lstpago.getSelectedRow(), 1).toString();
            try {
                java.util.Date fechDate = formato.parse(fecha);
                dc_fecha.setDate(fechDate);
            } catch (ParseException ex) {
                Logger.getLogger(MantSepultados.class.getName()).log(Level.SEVERE, null, ex);
            }
            tipo=lstpago.getValueAt(lstpago.getSelectedRow(), 2).toString();
            /*msj=tipo;
            JOptionPane.showMessageDialog(null, msj, "ELIMINACIÓN DE DATOS", JOptionPane.INFORMATION_MESSAGE);*/
            switch (tipo) {
                case "Pago":
                    cmb_tipo.setSelectedItem("Pago");
                    break;
                case "Abono":
                    cmb_tipo.setSelectedItem("Abono");
                    break;

            }

            limpiarlbl();
        }
    }//GEN-LAST:event_lstpagoMouseClicked

    private void txt_montoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_montoKeyTyped
        char TipoTecla = evt.getKeyChar();
        if (txt_monto.getText().startsWith(" ")) {
            txt_monto.setText("");
            txt_monto.requestFocus();
        }
        if (txt_monto.getText().length() >= 7) {
            evt.consume();
        }
        limpiarlbl();
        if (Character.isDigit(TipoTecla)) {

        } else {
            evt.consume();
        }
    }//GEN-LAST:event_txt_montoKeyTyped

    private void txt_ventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ventaKeyTyped
        char TipoTecla = evt.getKeyChar();
        if (txt_venta.getText().startsWith(" ")) {
            txt_venta.setText("");
            txt_venta.requestFocus();
        }
        if (txt_venta.getText().length() >= 5) {
            evt.consume();
        }
        limpiarlbl();
        if (Character.isDigit(TipoTecla)) {

        } else {
            evt.consume();
        }
    }//GEN-LAST:event_txt_ventaKeyTyped

    private void lstpagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lstpagoKeyReleased
        String cod, fecha, tipo = "", valor, nrofac, descripcion;
        java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("dd-MM-yyyy");

        if (lstpago.getSelectedRowCount() > 0) {
            
          if (evt.VK_DOWN == evt.getKeyCode() || evt.VK_UP == evt.getKeyCode()) {   
            cod = lstpago.getValueAt(lstpago.getSelectedRow(), 0).toString();
            jLabel6.enable();
            lb_cod.setText(cod);
            txt_monto.setText(lstpago.getValueAt(lstpago.getSelectedRow(), 3).toString());
            txt_venta.setText(lstpago.getValueAt(lstpago.getSelectedRow(), 4).toString());
            txt_venta.disable();
            
            fecha = lstpago.getValueAt(lstpago.getSelectedRow(), 1).toString();
            try {
                java.util.Date fechDate = formato.parse(fecha);
                dc_fecha.setDate(fechDate);
            } catch (ParseException ex) {
                Logger.getLogger(MantSepultados.class.getName()).log(Level.SEVERE, null, ex);
            }
            tipo=lstpago.getValueAt(lstpago.getSelectedRow(), 2).toString();
            /*msj=tipo;
            JOptionPane.showMessageDialog(null, msj, "ELIMINACIÓN DE DATOS", JOptionPane.INFORMATION_MESSAGE);*/
            switch (tipo) {
                case "Pago":
                    cmb_tipo.setSelectedItem("Pago");
                    break;
                case "Abono":
                    cmb_tipo.setSelectedItem("Abono");
                    break;

            }

            limpiarlbl();
            
          }
          
           if (evt.VK_DELETE == evt.getKeyCode()) {
                btn_eliminar.doClick();
        }
        }   
    }//GEN-LAST:event_lstpagoKeyReleased

    private void lstventaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lstventaKeyReleased
           String cod, fecha, tipo = "", valor, nrofac, descripcion;
        

        if (lstventa.getSelectedRowCount() > 0) {
            
          if (evt.VK_DOWN == evt.getKeyCode() || evt.VK_UP == evt.getKeyCode()) {   
            
            txt_venta.setText(lstventa.getValueAt(lstventa.getSelectedRow(), 4).toString());
            txt_venta.disable();
            
            

            limpiarlbl();
            
          }
          
          
        } 
    }//GEN-LAST:event_lstventaKeyReleased

    private void txt_consultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_consultaMouseClicked
        limpiarlbl();
        
        txt_consulta.setText("");
        dc_fecha.setDate(null);
        txt_monto.setText("");
        txt_venta.setText("");
        cmb_tipo.setSelectedIndex(0);
        lb_cod.setText("");
       
        
        txt_venta.enable();
        
        lstventa.clearSelection();
    
    }//GEN-LAST:event_txt_consultaMouseClicked

    private void txt_consultaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_consultaKeyReleased
        if ((evt.VK_BACK_SPACE == evt.getKeyCode() || evt.VK_DELETE == evt.getKeyCode()) && (txt_consulta.getText().isEmpty())) {
            modelolista.setNumRows(0);
            llenarlst2();
        }
    }//GEN-LAST:event_txt_consultaKeyReleased

    private void txt_consultaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_consultaKeyTyped
            char TipoTecla = evt.getKeyChar();

        /*if(Character.isSpace(TipoTecla)){
            txt_consulta.setText("");
            txt_consulta.requestFocus();
        }*/
        if (Character.isDigit(TipoTecla)) {
            if (txt_consulta.getText().length() >= 8) {
                evt.consume();
            }
        } else {
            //evt.consume();
        }

        String consu;
        consu = txt_consulta.getText();
        //if(txt_consulta.getText()>='0' && txt_consulta.getText()<='30000000' ){

        //}
        if (txt_consulta.getText().startsWith(" ")) {
            txt_consulta.setText("");
            txt_consulta.requestFocus();
        }
        modelolista.setNumRows(0);
        //lstClie.remove();
        llenarlst_consu(consu);
    }//GEN-LAST:event_txt_consultaKeyTyped

    
    
    
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
            java.util.logging.Logger.getLogger(MantPagosAbonos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MantPagosAbonos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MantPagosAbonos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MantPagosAbonos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MantPagosAbonos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_modificar;
    private javax.swing.JComboBox<String> cmb_tipo;
    private com.toedter.calendar.JDateChooser dc_fecha;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lb_cod;
    private javax.swing.JLabel lb_err1;
    private javax.swing.JLabel lb_err2;
    private javax.swing.JLabel lb_err3;
    private javax.swing.JLabel lb_err4;
    private javax.swing.JLabel lbl_estado;
    private javax.swing.JTable lstpago;
    private javax.swing.JTable lstventa;
    private javax.swing.JTextField txt_consulta;
    private javax.swing.JTextField txt_monto;
    private javax.swing.JTextField txt_venta;
    // End of variables declaration//GEN-END:variables
}
