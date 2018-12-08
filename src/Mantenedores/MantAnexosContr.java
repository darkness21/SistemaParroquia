package Mantenedores;

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
public class MantAnexosContr extends javax.swing.JFrame {

    private Statement sentencia;
    private Connection conexion;
    private String nomBD = "cementerio";
    private String usuario = "root";
    private String password = "";
    private String msj;
    Object[][] data = {};
    String[] columnNames = {"Rut", "Nombre"};
    DefaultTableModel modelolista1 = new DefaultTableModel(data, columnNames) {
        public boolean isCellEditable(int rowIndex, int vColIndex) {
            return false;
        }
    };
    
    Object[][] data2 = {};
    String[] columnNames2 = {"Cod anexo", "Rut", "Descripcion"};
    DefaultTableModel modelolista2 = new DefaultTableModel(data2, columnNames2) {
        public boolean isCellEditable(int rowIndex, int vColIndex) {
            return false;
        }
    };
    
    
    
    public MantAnexosContr() {
        initComponents();
        this.setLocationRelativeTo(null);
        conectar();
        lbl_estado.setVisible(false);
        lstcontra.setModel(modelolista2);
        lsttrab.setModel(modelolista1);
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
      
    }
  
  
  
  public void llenarlst1() {
        String run, dv, nom, apet;
        lsttrab.setPreferredScrollableViewportSize(new Dimension(500, 70));
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelolista1);
        lsttrab.setRowSorter(sorter);
        try {
            sentencia = (Statement) conexion.createStatement();//permite ejecutar sentencias SQL
            ResultSet lista = sentencia.executeQuery("SELECT * FROM trabajadores");
            while (lista.next()) {
                run = (lista.getString("run_trabajador"));
                dv = (lista.getString("dv_trabajador"));//nombre del campo de la BD
                nom = (lista.getString("nombre_trabajador"));
                apet = (lista.getString("ape_pat_trabajador"));
             
                Object[] newRow = {run + "-" + dv, nom + " "+ apet};
                modelolista1.addRow(newRow);
            }
            lsttrab.getRowSorter().toggleSortOrder(0);
            lsttrab.getRowSorter().toggleSortOrder(0);
            limpiarlbl();
        } catch (SQLException e) {
            msj = "no tiene elementos";
        }

    }
  
   public void llenarlst2() {
        String run, dv, cod, desc;
        lstcontra.setPreferredScrollableViewportSize(new Dimension(500, 70));
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelolista2);
        lstcontra.setRowSorter(sorter);
        try {
            sentencia = (Statement) conexion.createStatement();//permite ejecutar sentencias SQL
            ResultSet lista = sentencia.executeQuery("SELECT * FROM anexo_contrato");
            while (lista.next()) {
                cod = (lista.getString("cod_contrato"));
                run = (lista.getString("run_trabajador"));
                dv = (lista.getString("dv_trabajador"));
                
                desc = (lista.getString("descripcion"));
             
                Object[] newRow = {cod, run + "-" + dv, desc};
                modelolista2.addRow(newRow);
            }
            lstcontra.getRowSorter().toggleSortOrder(0);
            lstcontra.getRowSorter().toggleSortOrder(0);
            limpiarlbl();
        } catch (SQLException e) {
            msj = "no tiene elementos";
        }

    }

    public void llenarlst_consu(String consu) {
        String run, dv, cod, desc;
       
        lstcontra.setPreferredScrollableViewportSize(new Dimension(500, 70));
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelolista2);
        lstcontra.setRowSorter(sorter);
        try {
            sentencia = (Statement) conexion.createStatement();//permite ejecutar sentencias SQL
            ResultSet lista = sentencia.executeQuery("SELECT * FROM anexo_contrato WHERE run_trabajador like '%" + consu + "%'or descripcion like '%" + consu + "%'");
            while (lista.next()) {
                cod = (lista.getString("cod_contrato"));
                run = (lista.getString("run_trabajador"));
                dv = (lista.getString("dv_trabajador"));
                
                desc = (lista.getString("descripcion"));
                

                Object[] newRow = {cod, run + "-" + dv, desc};
                modelolista2.addRow(newRow);
            }
            if (modelolista2.getRowCount() == 0 || (txt_consulta.getText().startsWith(" "))) {
                msj = "No se encontró lo solicitado";
                JOptionPane.showMessageDialog(null, msj, "CONSULTA SIN DATOS", JOptionPane.INFORMATION_MESSAGE);
                modelolista2.setNumRows(0);
                llenarlst2();
                txt_consulta.setText("");
                txt_consulta.requestFocus();
            }
            lstcontra.getRowSorter().toggleSortOrder(0);
            lstcontra.getRowSorter().toggleSortOrder(0);
        } catch (SQLException e) {
            msj = "consulta errónea";
            JOptionPane.showMessageDialog(null, msj, "Falla Consulta", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    ;
   
    public void limpiar() {
        
        lb_cod.setText("");
        txt_rut.setText("");
        txt_dv.setText("");
        txt_cod.setText("");
        txt_descripcion.setText("");
        txt_rut.enable();
        txt_dv.enable();
        lsttrab.clearSelection();
        lstcontra.clearSelection();
        
        
    
        
    }
    
    
    
    public void insertar(String sql) {

        try {
            sentencia.executeUpdate(sql);
            msj = "datos guardados";
            JOptionPane.showMessageDialog(null, msj, "Datos Guardados", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            msj = "No ingresó";
            JOptionPane.showMessageDialog(null, msj, "---", JOptionPane.INFORMATION_MESSAGE);

        }
        modelolista2.setNumRows(0);
        //lstClie.remove();
        llenarlst2();
        limpiar();

        //modelolista.removeTableModelListener(lstClie);
    }
    
    public void actualizar(int cod, String rut, String dv, String desc) {
        String upSQL = "";

        try {
            upSQL = "UPDATE anexo_contrato SET descripcion='" + desc + "' WHERE run_trabajador='" + rut + "' and cod_contrato='" + cod + "'";
            sentencia.executeUpdate(upSQL);
            msj = "Datos actualizados";
            JOptionPane.showMessageDialog(null, msj, "Datos actualizados", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            msj = "No actualizado";
            JOptionPane.showMessageDialog(null, msj, "Datos actualizados", JOptionPane.INFORMATION_MESSAGE);
        }
        modelolista2.setNumRows(0);
        //lstClie.remove();
        llenarlst2();
        limpiar();
    }
    
    public void eliminar(String cod, String rut, String dv, String desc) {
        String upSQL = "";
        /*Mensaje message=new Mensaje();
        message.windowClosed(e);*/

        try {
            upSQL = "DELETE FROM anexo_contrato WHERE cod_contrato='" + cod + "' and run_trabajador='"+ rut + "';";
            sentencia.executeUpdate(upSQL);
            msj = "Datos Eliminados";
            JOptionPane.showMessageDialog(null, msj, "ELIMINACIÓN DE DATOS", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            msj = "No eliminado";
            JOptionPane.showMessageDialog(null, msj, "ALERTA", JOptionPane.INFORMATION_MESSAGE);
        }
        modelolista2.setNumRows(0);
        //lstClie.remove();
        llenarlst2();
        limpiar();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lsttrab = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        txt_rut = new javax.swing.JTextField();
        lb_cod = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txt_cod = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        txt_consulta = new javax.swing.JTextField();
        btn_agregar = new javax.swing.JButton();
        btn_modificar = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        txt_dv = new javax.swing.JTextField();
        lbl_estado = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstcontra = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lb_err1 = new javax.swing.JLabel();
        txt_descripcion = new javax.swing.JTextField();

        jButton5.setBackground(new java.awt.Color(102, 204, 0));
        jButton5.setFont(new java.awt.Font("Franklin Gothic Medium", 3, 13)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/aceptar.png"))); // NOI18N
        jButton5.setText("Aceptar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(102, 0, 0));
        jButton6.setFont(new java.awt.Font("Franklin Gothic Medium", 3, 13)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cancelar.png"))); // NOI18N
        jButton6.setText("Cancelar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jTextField1.setBackground(new java.awt.Color(255, 255, 204));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cruzmini.png"))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Franklin Gothic Medium", 3, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Anexo contratos");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Cod Anexo");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Run trabajador");

        lsttrab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"18178983-0", ""},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Run Trabajador", "Nombre"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        lsttrab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lsttrabMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lsttrab);
        if (lsttrab.getColumnModel().getColumnCount() > 0) {
            lsttrab.getColumnModel().getColumn(1).setResizable(false);
        }

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

        lb_cod.setForeground(new java.awt.Color(255, 255, 255));

        jLabel17.setForeground(new java.awt.Color(255, 255, 255));

        txt_cod.setBackground(new java.awt.Color(255, 255, 204));
        txt_cod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_codActionPerformed(evt);
            }
        });
        txt_cod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_codKeyTyped(evt);
            }
        });

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/consultar.png"))); // NOI18N

        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Descripción");

        jButton8.setBackground(new java.awt.Color(102, 0, 0));
        jButton8.setFont(new java.awt.Font("Franklin Gothic Medium", 3, 13)); // NOI18N
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cancelar.png"))); // NOI18N
        jButton8.setText("Cancelar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        txt_consulta.setBackground(new java.awt.Color(255, 255, 204));
        txt_consulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_consultaMouseClicked(evt);
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

        lstcontra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"0232", "18178983-0", ""},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Cod Anexo", "Run Trabajador", "Descripción"
            }
        ));
        lstcontra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstcontraMouseClicked(evt);
            }
        });
        lstcontra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lstcontraKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(lstcontra);
        if (lstcontra.getColumnModel().getColumnCount() > 0) {
            lstcontra.getColumnModel().getColumn(0).setResizable(false);
            lstcontra.getColumnModel().getColumn(0).setHeaderValue("Cod Anexo");
            lstcontra.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Datos trabajadores");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Detalles anexos contratos");

        lb_err1.setForeground(new java.awt.Color(255, 0, 51));
        lb_err1.setText("jLabel4");

        txt_descripcion.setBackground(new java.awt.Color(255, 255, 204));
        txt_descripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_descripcionActionPerformed(evt);
            }
        });
        txt_descripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_descripcionKeyTyped(evt);
            }
        });

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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator2)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(176, 176, 176)
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lbl_estado)
                                        .addGap(78, 78, 78))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_agregar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_modificar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_eliminar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addComponent(txt_consulta, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9)
                                .addGap(10, 10, 10)))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(185, 185, 185))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lb_err1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel6)
                                    .addComponent(lb_cod)
                                    .addComponent(jLabel8)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txt_rut, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_dv, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txt_cod, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(42, 42, 42))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(162, 162, 162))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton8)
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(lbl_estado))
                        .addGap(23, 23, 23)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_consulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(25, 25, 25)
                                        .addComponent(jLabel9))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btn_eliminar)
                                            .addComponent(btn_modificar)
                                            .addComponent(btn_agregar, javax.swing.GroupLayout.Alignment.TRAILING))))
                                .addGap(12, 12, 12))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(76, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_cod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lb_cod)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_rut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_dv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(jLabel17))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lb_err1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton8)
                        .addGap(35, 35, 35))))
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

    private void txt_codActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_codActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_codActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
       System.exit(0);
    }//GEN-LAST:event_jButton8ActionPerformed

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

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
         String cod, rut, dv, desc;
         
        cod = txt_cod.getText();
        if (txt_cod.getText().isEmpty() || txt_cod.getText().startsWith(" ")) {
            lb_err1.setText("Ingrese un Codigo de anexo");
            txt_cod.setFocusable(rootPaneCheckingEnabled);
            return;
        }
        
        rut = txt_rut.getText();
        if (txt_rut.getText().isEmpty() || txt_rut.getText().startsWith(" ")) {
            lb_err1.setText("Ingrese un rut");
            txt_rut.setFocusable(rootPaneCheckingEnabled);
            return;
        }
        dv = txt_dv.getText().toUpperCase();
        
        desc = txt_descripcion.getText().toUpperCase();
        if (txt_descripcion.getText().isEmpty() || txt_descripcion.getText().startsWith(" ")) {
            lb_err1.setText("Ingrese una descripcion");
            txt_descripcion.requestFocus();
            return;
        }
        
        

        String sql = "INSERT INTO anexo_contrato(cod_contrato,run_trabajador,dv_trabajador,descripcion) "
                + "VALUES('" + cod + "', '" + rut + "','" + dv + "','" + desc +  "')";
        //msj = sql;
        //JOptionPane.showMessageDialog(null, msj, "Datos Guardados", JOptionPane.INFORMATION_MESSAGE);
        insertar(sql);
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void btn_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificarActionPerformed
      String cod, rut, dv, desc;
      int codigo;
        if (lstcontra.getSelectedRowCount() > 0) {
//            cod = lstcontra.getValueAt(lstcontra.getSelectedRow(), 0).toString();
//            codigo=parseInt(cod); 
            cod = txt_cod.getText();
            codigo = parseInt (cod);
            
            rut = txt_rut.getText();
            
            dv = txt_dv.getText().toUpperCase();
            
            desc = txt_descripcion.getText();
           
            limpiarlbl();

            actualizar(codigo, rut, dv, desc);
        } else {
            msj = "Seleccione una fila para modificar";
            JOptionPane.showMessageDialog(null, msj, "MODIFICACIÓN DE DATOS", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btn_modificarActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        String cod, rut, dv, desc;
        
    
        if (lstcontra.getSelectedRowCount() > 0) {
//            cod = lstcontra.getValueAt(lstcontra.getSelectedRow(), 0).toString();
            cod = txt_cod.getText();
            rut = txt_rut.getText();
            dv = txt_dv.getText();
            desc = txt_descripcion.getText();
            
            
            
            
            

            if (JOptionPane.showConfirmDialog(rootPane, "Se eliminará el registro",
                    "Eliminar", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                eliminar(cod, rut, dv, desc);
            }
        } else {
            msj = "NO SE PUEDEN ELIMINAR DATOS";
            JOptionPane.showMessageDialog(null, msj, "ELIMINACIÓN DE DATOS", JOptionPane.INFORMATION_MESSAGE);
        }
        //llenarlst2();
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void lsttrabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lsttrabMouseClicked
       String rut, dv;

        if (lsttrab.getSelectedRowCount() > 0) {
            
            
     
            rut = lsttrab.getValueAt(lsttrab.getSelectedRow(), 0).toString();
            rut = rut.substring(0, rut.length() - 2);
            txt_rut.setText(rut);
            txt_rut.disable();
            dv = lsttrab.getValueAt(lsttrab.getSelectedRow(), 0).toString();
            dv = dv.substring(dv.length() - 1, dv.length());
            txt_dv.setText(dv);
            txt_dv.disable();
            txt_cod.setText("");
            lb_cod.setText("");
            txt_descripcion.setText("");
            txt_cod.enable();
            btn_eliminar.setVisible(false);
            btn_modificar.setVisible(false);
            
            limpiarlbl();
        }
    }//GEN-LAST:event_lsttrabMouseClicked

    private void lstcontraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstcontraMouseClicked
       String cod, rut, dv, desc;

        if (lstcontra.getSelectedRowCount() > 0) {
            
            
            cod = lstcontra.getValueAt(lstcontra.getSelectedRow(), 0).toString();   
            //lb_cod.setText(cod);
            txt_cod.setText(cod);
            rut = lstcontra.getValueAt(lstcontra.getSelectedRow(), 1).toString();
            rut = rut.substring(0, rut.length() - 2);
            txt_rut.setText(rut);
            txt_rut.disable();
            dv = lstcontra.getValueAt(lstcontra.getSelectedRow(), 1).toString();
            dv = dv.substring(dv.length() - 1, dv.length());
            txt_dv.setText(dv);
            txt_dv.disable();
            desc = lstcontra.getValueAt(lstcontra.getSelectedRow(), 2).toString();
            txt_descripcion.setText(desc);
            txt_cod.disable();
             btn_eliminar.setVisible(true);
            btn_modificar.setVisible(true);
            limpiarlbl();
        }
    }//GEN-LAST:event_lstcontraMouseClicked

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

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
         modelolista1.setNumRows(0);
         modelolista2.setNumRows(0);
        llenarlst1();
        llenarlst2();
        lb_cod.setText("");
        txt_rut.setText("");
        txt_cod.enable();
        txt_rut.enable();
        txt_dv.setText("");
        txt_dv.enable();
        txt_cod.setText("");
        txt_descripcion.setText("");
        lsttrab.clearSelection();
        lstcontra.clearSelection();
        btn_eliminar.setVisible(true);
        btn_modificar.setVisible(true);
        
        //txt_dv.disable();
        
        
        
        
       
        
        //txt_dv.enable();
       
    }//GEN-LAST:event_jPanel1MouseClicked

    private void txt_descripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_descripcionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_descripcionActionPerformed

    private void txt_codKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_codKeyTyped
        char TipoTecla = evt.getKeyChar();
        if (txt_cod.getText().startsWith(" ")) {
            txt_cod.setText("");
            txt_cod.requestFocus();
        }
        if (txt_cod.getText().length() >= 5) {
            evt.consume();
        }
        limpiarlbl();
        if (Character.isDigit(TipoTecla)) {

        } else {
            evt.consume();
        }
    }//GEN-LAST:event_txt_codKeyTyped

    private void txt_descripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_descripcionKeyTyped
          if (txt_descripcion.getText().startsWith(" ")) {
            txt_descripcion.setText("");
            txt_descripcion.requestFocus();
        }
        limpiarlbl();
        if (txt_descripcion.getText().length() >= 20) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_descripcionKeyTyped

    private void txt_rutKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_rutKeyReleased
         if ((evt.VK_BACK_SPACE == evt.getKeyCode() || evt.VK_DELETE == evt.getKeyCode()) && (txt_consulta.getText().isEmpty())) {
            txt_dv.setText("");
            //txt_rut.setForeground(Color.GRAY);
            //txt_rut.setText("12345678");
        }
    }//GEN-LAST:event_txt_rutKeyReleased

    private void lstcontraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lstcontraKeyReleased
        
            
        String cod, rut, dv, desc;

        if (lstcontra.getSelectedRowCount() > 0) {
            
          if (evt.VK_DOWN == evt.getKeyCode() || evt.VK_UP == evt.getKeyCode()) {
            cod = lstcontra.getValueAt(lstcontra.getSelectedRow(), 0).toString();   
            //lb_cod.setText(cod);
            txt_cod.setText(cod);
            rut = lstcontra.getValueAt(lstcontra.getSelectedRow(), 1).toString();
            rut = rut.substring(0, rut.length() - 2);
            txt_rut.setText(rut);
            txt_rut.disable();
            dv = lstcontra.getValueAt(lstcontra.getSelectedRow(), 1).toString();
            dv = dv.substring(dv.length() - 1, dv.length());
            txt_dv.setText(dv);
            txt_dv.disable();
            desc = lstcontra.getValueAt(lstcontra.getSelectedRow(), 2).toString();
            txt_descripcion.setText(desc);
            txt_cod.disable();
             btn_eliminar.setVisible(true);
            btn_modificar.setVisible(true);
            limpiarlbl();
          }
           if (evt.VK_DELETE == evt.getKeyCode()) {
                btn_eliminar.doClick();
           }
        }
    }//GEN-LAST:event_lstcontraKeyReleased

    private void txt_consultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_consultaMouseClicked
        limpiarlbl();
        
        
        lb_cod.setText("");
        txt_rut.setText("");
        txt_cod.enable();
        txt_rut.enable();
        txt_dv.setText("");
        txt_dv.enable();
        txt_cod.setText("");
        txt_descripcion.setText("");
        lsttrab.clearSelection();
        lstcontra.clearSelection();
        btn_eliminar.setVisible(true);
        btn_modificar.setVisible(true);
    }//GEN-LAST:event_txt_consultaMouseClicked

    private void txt_consultaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_consultaKeyReleased
         if ((evt.VK_BACK_SPACE == evt.getKeyCode() || evt.VK_DELETE == evt.getKeyCode()) && (txt_consulta.getText().isEmpty())) {
            modelolista2.setNumRows(0);
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
        modelolista2.setNumRows(0);
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
            java.util.logging.Logger.getLogger(MantAnexosContr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MantAnexosContr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MantAnexosContr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MantAnexosContr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MantAnexosContr().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_modificar;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lb_cod;
    private javax.swing.JLabel lb_err1;
    private javax.swing.JLabel lbl_estado;
    private javax.swing.JTable lstcontra;
    private javax.swing.JTable lsttrab;
    private javax.swing.JTextField txt_cod;
    private javax.swing.JTextField txt_consulta;
    private javax.swing.JTextField txt_descripcion;
    private javax.swing.JTextField txt_dv;
    private javax.swing.JTextField txt_rut;
    // End of variables declaration//GEN-END:variables
}
