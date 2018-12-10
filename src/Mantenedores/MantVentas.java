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

public class MantVentas extends javax.swing.JFrame {
    private Statement sentencia;
    private Connection conexion;
    private String nomBD = "cementerio";
    private String usuario = "root";
    private String password = "";
    private String msj;
    Object[][] data = {};
    String[] columnNames = {"Codigo venta", "Fecha venta", "Monto total", "Rut cliente"};
    DefaultTableModel modelolista = new DefaultTableModel(data, columnNames) {
        public boolean isCellEditable(int rowIndex, int vColIndex) {
            return false;
        }
    };
    
    public MantVentas() {
       initComponents();
        this.setLocationRelativeTo(null);
        conectar();
        lbl_estado.setVisible(false);
        lstventa.setModel(modelolista);
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
        lb_err2.setText("");
        lb_err3.setText("");

    }
      
    public void llenarlst() {
        String cod, fecha, monto, rut, dv;
        Date fech = null;
        lstventa.setPreferredScrollableViewportSize(new Dimension(500, 70));
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelolista);
        lstventa.setRowSorter(sorter);
        try {
            sentencia = (Statement) conexion.createStatement();//permite ejecutar sentencias SQL
            ResultSet lista = sentencia.executeQuery("SELECT * FROM venta");
            while (lista.next()) {

                cod = (lista.getString("cod_venta"));
                //
                fecha = lista.getString("fecha_venta");
                SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    fech = parseador.parse(fecha);
                } catch (ParseException ex) {
                    Logger.getLogger(MantSepultados.class.getName()).log(Level.SEVERE, null, ex);
                }
                fecha = String.format(format.format(fech));
                
                //
                monto = (lista.getString("monto_total"));
                //
                
                
                rut = (lista.getString("run_cliente"));
                dv = (lista.getString("dv_cliente"));

                
                Object[] newRow = {cod, fecha, monto, rut + "-" + dv};
                modelolista.addRow(newRow);
            }
            lstventa.getRowSorter().toggleSortOrder(0);
            lstventa.getRowSorter().toggleSortOrder(0);
            limpiarlbl();
        } catch (SQLException e) {
            msj = "no tiene elementos";
            JOptionPane.showMessageDialog(null, msj, "Datos Guardados", JOptionPane.INFORMATION_MESSAGE);
        }
    }  
   
    public void llenarlst_consu(String consu) {
        String cod, monto, rut, dv;
        Date fecha;
        lstventa.setPreferredScrollableViewportSize(new Dimension(500, 70));
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelolista);
        lstventa.setRowSorter(sorter);
        try {
            sentencia = (Statement) conexion.createStatement();//permite ejecutar sentencias SQL
            ResultSet lista = sentencia.executeQuery("SELECT * FROM venta WHERE run_cliente like '%" + consu + "%'or fecha_venta like '%" + consu + "%'"
                    + "or monto_total like '%" + consu + "%' or cod_venta like '%" + consu + "%'");
            while (lista.next()) {
                cod = (lista.getString("cod_venta"));
                
                fecha = lista.getDate("fecha_venta");
                
                monto = (lista.getString("monto_total"));
                
                rut = (lista.getString("run_cliente"));
                dv = (lista.getString("dv_cliente"));

                

                Object[] newRow = {cod, fecha, monto, rut + "-" + dv};
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
            lstventa.getRowSorter().toggleSortOrder(0);
            lstventa.getRowSorter().toggleSortOrder(0);
        } catch (SQLException e) {
            msj = "consulta errónea";
            JOptionPane.showMessageDialog(null, msj, "Falla Consulta", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    ;
    
    
    public void limpiar() {
        lb_cod.setText("");
        txt_consulta.setText("");
        txt_rut.setText("");
        txt_dv.setText("");
        dc_fecha.setDate(null);
        txt_monto.setText("");
        jLabel6.enable(false);
        
       

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
        llenarlst();
        limpiar();
    }
    
    
     public void actualizar(int cod, String fecha, int monto, String rut, String dv) {
        String upSQL = "";
        //and rut_cliente="+ rut + ""
        try {
            upSQL = "UPDATE venta SET fecha_venta='" + fecha + "',monto_total=" + monto + " WHERE cod_venta=" + cod + "";
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
     
     public void eliminar(String cod, Date fecha, String monto, String rut, String dv) {
        String upSQL = "";
        /*Mensaje message=new Mensaje();
        message.windowClosed(e);*/

        try {
            upSQL = "DELETE FROM venta WHERE cod_venta='" + cod + "';";
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
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstventa = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        lb_cod = new javax.swing.JLabel();
        txt_rut = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txt_monto = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        txt_consulta = new javax.swing.JTextField();
        btn_agregar = new javax.swing.JButton();
        btn_modificar = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        dc_fecha = new com.toedter.calendar.JDateChooser();
        txt_dv = new javax.swing.JTextField();
        lbl_estado = new javax.swing.JLabel();
        lb_err1 = new javax.swing.JLabel();
        lb_err2 = new javax.swing.JLabel();
        lb_err3 = new javax.swing.JLabel();

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
        jLabel5.setText("Ventas");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Codigo Venta");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Fecha Venta");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Monto Total");

        lstventa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"001", null, "630.000", "6867828-k"},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Codigo venta", "Fecha venta", "Monto Total", "Rut cliente"
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

        lb_cod.setForeground(new java.awt.Color(255, 255, 255));

        txt_rut.setBackground(new java.awt.Color(255, 255, 204));
        txt_rut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_rutActionPerformed(evt);
            }
        });
        txt_rut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_rutKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_rutKeyTyped(evt);
            }
        });

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Rut cliente");

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

        jButton2.setBackground(new java.awt.Color(102, 0, 0));
        jButton2.setFont(new java.awt.Font("Franklin Gothic Medium", 3, 13)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cancelar.png"))); // NOI18N
        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
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
        txt_dv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_dvKeyTyped(evt);
            }
        });

        lb_err1.setForeground(new java.awt.Color(255, 0, 51));
        lb_err1.setText("jLabel2");

        lb_err2.setForeground(new java.awt.Color(255, 0, 51));
        lb_err2.setText("jLabel2");

        lb_err3.setForeground(new java.awt.Color(255, 0, 51));
        lb_err3.setText("jLabel2");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                .addGap(28, 28, 28)
                                .addComponent(btn_agregar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_modificar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_eliminar)
                                .addGap(26, 26, 26)
                                .addComponent(txt_consulta)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addGap(22, 22, 22))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(176, 176, 176)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_estado)
                                .addGap(45, 45, 45)))))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel9)
                        .addGap(67, 638, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_rut, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_dv, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jLabel11))
                            .addComponent(lb_err2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(53, 53, 53))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addComponent(lb_cod))
                            .addComponent(dc_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_err1)
                            .addComponent(lb_err3)
                            .addComponent(txt_monto, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(23, 23, 23))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lbl_estado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(txt_consulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btn_eliminar)
                                .addComponent(btn_modificar)
                                .addComponent(btn_agregar, javax.swing.GroupLayout.Alignment.TRAILING)))))
                .addGap(32, 32, 32)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(lb_cod)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dc_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_err1)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_monto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lb_err2)
                        .addGap(8, 8, 8)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_rut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_dv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lb_err3)
                .addContainerGap())
        );

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

    private void txt_rutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rutActionPerformed

    private void txt_montoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_montoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_montoActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
         MenuPrincipal mp = new MenuPrincipal();
        mp.setVisible(true);
        this.setVisible(false);
        //System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txt_consultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_consultaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_consultaActionPerformed

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
        String cod, monto, rut, dv, fecha2;
        int monto2;
        Date fecha;
//
//        cod = lb_cod.getText();
//        if (txt_cod.getText().isEmpty() || txt_cod.getText().startsWith(" ")) {
//            lb_err1.setText("Ingrese un Codigo de anexo");
//            txt_cod.setFocusable(rootPaneCheckingEnabled);
//            return;
//        }
        dc_fecha.setDateFormatString("yyyy-MM-dd");
        fecha2 = dc_fecha.getDateFormatString();
        fecha = dc_fecha.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat(fecha2);
        fecha2 = String.valueOf(sdf.format(fecha));


        monto = txt_monto.getText();
        if (txt_monto.getText().isEmpty() || txt_monto.getText().startsWith(" ")) {
            lb_err2.setText("Ingrese un monto");
            txt_monto.setFocusable(rootPaneCheckingEnabled);
            return;
        }
        monto2 = parseInt(monto);

        rut = txt_rut.getText();
        if (txt_rut.getText().isEmpty() || txt_rut.getText().startsWith(" ")) {
            lb_err3.setText("Ingrese un rut");
            txt_rut.setFocusable(rootPaneCheckingEnabled);
            return;
        }
        dv = txt_dv.getText().toUpperCase();

        

        String sql = "INSERT INTO venta(fecha_venta,monto_total,run_cliente,dv_cliente) "
        + "VALUES('" + fecha2 + "', " + monto2 + ",'" + rut + "','" + dv +  "')";
//        msj = sql;
//        JOptionPane.showMessageDialog(null, msj, "Datos Guardados", JOptionPane.INFORMATION_MESSAGE);
        insertar(sql);
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void btn_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificarActionPerformed
        String cod, fecha2, monto, rut, dv;
        Date fecha;
        int codigo;
        if (lstventa.getSelectedRowCount() > 0) {
            cod = lstventa.getValueAt(lstventa.getSelectedRow(), 0).toString();
            codigo=parseInt(cod);
            
            dc_fecha.setDateFormatString("yyyy-MM-dd");
            fecha2 = dc_fecha.getDateFormatString();
            fecha = dc_fecha.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat(fecha2);
            fecha2 = String.valueOf(sdf.format(fecha));
            
             monto = txt_monto.getText();
            if (txt_monto.getText().isEmpty() || txt_monto.getText().startsWith(" ")) {
                lb_err2.setText("Ingrese un monto");
                txt_monto.setFocusable(rootPaneCheckingEnabled);
                return;
            }
            int monto2 = parseInt(monto);
            
            rut = txt_rut.getText();

            dv = txt_dv.getText().toUpperCase();

            

            limpiarlbl();
            
            actualizar(codigo, fecha2, monto2, rut, dv);
            formatearfecha();
        } else {
            msj = "Seleccione una fila para modificar";
            JOptionPane.showMessageDialog(null, msj, "MODIFICACIÓN DE DATOS", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btn_modificarActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        String cod, monto,  rut, dv;
        Date fecha;
               
        if (lstventa.getSelectedRowCount() > 0) {
           
            cod = lstventa.getValueAt(lstventa.getSelectedRow(), 0).toString();
            fecha = dc_fecha.getDate();
            monto = txt_monto.getText();
            rut = txt_rut.getText();
            dv = txt_dv.getText();
            

            if (JOptionPane.showConfirmDialog(rootPane, "Se eliminará el registro",
                "Eliminar", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            eliminar(cod, fecha, monto, rut, dv);
        }
        } else {
            msj = "NO SE PUEDEN ELIMINAR DATOS";
            JOptionPane.showMessageDialog(null, msj, "ELIMINACIÓN DE DATOS", JOptionPane.INFORMATION_MESSAGE);
        }
        //llenarlst2();
    }//GEN-LAST:event_btn_eliminarActionPerformed

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
            lb_err1.setText("Run inválido, ingrese nuevamente!");
            txt_rut.setText("");
            txt_dv.setText("");
            txt_rut.requestFocus();
        }
    }//GEN-LAST:event_txt_dvFocusLost

    private void txt_dvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dvActionPerformed

    private void lstventaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstventaMouseClicked
    String cod, fecha, monto, rut, dv;
        java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("dd-MM-yyyy");
        if (lstventa.getSelectedRowCount() > 0) {
            cod = lstventa.getValueAt(lstventa.getSelectedRow(), 0).toString();
            jLabel6.enable();
            lb_cod.setText(cod);
            
            txt_monto.setText(lstventa.getValueAt(lstventa.getSelectedRow(), 2).toString());
            
            rut = lstventa.getValueAt(lstventa.getSelectedRow(), 3).toString();
            rut = rut.substring(0, rut.length() - 2);
            txt_rut.setText(rut);
            txt_rut.disable();
            dv = lstventa.getValueAt(lstventa.getSelectedRow(), 3).toString();
            dv = dv.substring(dv.length() - 1, dv.length());
            txt_dv.setText(dv);
            txt_dv.disable();
            
            fecha = lstventa.getValueAt(lstventa.getSelectedRow(), 1).toString();
            try {
                java.util.Date fechDate = formato.parse(fecha);
                dc_fecha.setDate(fechDate);
            } catch (ParseException ex) {
                Logger.getLogger(MantSepultados.class.getName()).log(Level.SEVERE, null, ex);
            }
            

            limpiarlbl();
        }
        
        
    }//GEN-LAST:event_lstventaMouseClicked

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
       modelolista.setNumRows(0);
        llenarlst();
        txt_consulta.setText("");
        lb_cod.setText("");
        dc_fecha.setDate(null);
        txt_monto.setText("");
        txt_rut.setText("");
        txt_dv.setText("");
        txt_rut.enable();
        txt_dv.enable();
        
        
        
       
        
        
        
        lstventa.clearSelection();
    }//GEN-LAST:event_jPanel1MouseClicked

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

    private void txt_rutKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_rutKeyReleased
         if ((evt.VK_BACK_SPACE == evt.getKeyCode() || evt.VK_DELETE == evt.getKeyCode()) && (txt_consulta.getText().isEmpty())) {
            txt_dv.setText("");
         }
    }//GEN-LAST:event_txt_rutKeyReleased

    private void txt_rutKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_rutKeyTyped
        char TipoTecla = evt.getKeyChar();
        if (txt_rut.getText().length() >= 8) {
            evt.consume();
        }
        if (Character.isDigit(TipoTecla)) {

        } else {
            evt.consume();
        }
        if (txt_rut.getText().startsWith("0")) {
            lb_err1.setText("CERO NO ES PRIMER DIGITO!");
            txt_rut.setText("");
            evt.consume();
            txt_rut.requestFocus();
            return;
        } else {
            lb_err1.setText("");
        }

        lb_err1.setText("");
    }//GEN-LAST:event_txt_rutKeyTyped

    private void txt_dvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dvKeyTyped
       char TipoTecla = evt.getKeyChar();
        if (txt_dv.getText().length() >= 1) {
            evt.consume();
        }
        if (Character.isDigit(TipoTecla) || evt.getKeyChar() == 'k' || evt.getKeyChar() == 'K') {

        } else {
            evt.consume();
        }
    }//GEN-LAST:event_txt_dvKeyTyped

    private void lstventaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lstventaKeyReleased
        String cod, fecha, monto, rut, dv;
        java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("dd-MM-yyyy");
        if (lstventa.getSelectedRowCount() > 0) {
            
          if (evt.VK_DOWN == evt.getKeyCode() || evt.VK_UP == evt.getKeyCode()) {  
            cod = lstventa.getValueAt(lstventa.getSelectedRow(), 0).toString();
            jLabel6.enable();
            lb_cod.setText(cod);
            
            txt_monto.setText(lstventa.getValueAt(lstventa.getSelectedRow(), 2).toString());
            
            rut = lstventa.getValueAt(lstventa.getSelectedRow(), 3).toString();
            rut = rut.substring(0, rut.length() - 2);
            txt_rut.setText(rut);
            txt_rut.disable();
            dv = lstventa.getValueAt(lstventa.getSelectedRow(), 3).toString();
            dv = dv.substring(dv.length() - 1, dv.length());
            txt_dv.setText(dv);
            txt_dv.disable();
            
            fecha = lstventa.getValueAt(lstventa.getSelectedRow(), 1).toString();
            try {
                java.util.Date fechDate = formato.parse(fecha);
                dc_fecha.setDate(fechDate);
            } catch (ParseException ex) {
                Logger.getLogger(MantSepultados.class.getName()).log(Level.SEVERE, null, ex);
            }
            

            limpiarlbl();
          }
          if (evt.VK_DELETE == evt.getKeyCode()) {
                btn_eliminar.doClick();
          }
        }
    }//GEN-LAST:event_lstventaKeyReleased

    private void txt_consultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_consultaMouseClicked
     
        limpiarlbl();
        txt_consulta.setText("");
        lb_cod.setText("");
        dc_fecha.setDate(null);
        txt_monto.setText("");
        txt_rut.setText("");
        txt_dv.setText("");
        txt_rut.enable();
        //txt_dv.enable();
        lstventa.clearSelection();
        
        
       
        
        
        
        
    }//GEN-LAST:event_txt_consultaMouseClicked

    private void txt_consultaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_consultaKeyReleased
    if ((evt.VK_BACK_SPACE == evt.getKeyCode() || evt.VK_DELETE == evt.getKeyCode()) && (txt_consulta.getText().isEmpty())) {
            modelolista.setNumRows(0);
            llenarlst();
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
            java.util.logging.Logger.getLogger(MantVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MantVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MantVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MantVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MantVentas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_modificar;
    private com.toedter.calendar.JDateChooser dc_fecha;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lb_cod;
    private javax.swing.JLabel lb_err1;
    private javax.swing.JLabel lb_err2;
    private javax.swing.JLabel lb_err3;
    private javax.swing.JLabel lbl_estado;
    private javax.swing.JTable lstventa;
    private javax.swing.JTextField txt_consulta;
    private javax.swing.JTextField txt_dv;
    private javax.swing.JTextField txt_monto;
    private javax.swing.JTextField txt_rut;
    // End of variables declaration//GEN-END:variables
}
