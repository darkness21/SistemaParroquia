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

public class MantGastos extends javax.swing.JFrame {

    private Statement sentencia;
    private Connection conexion;
    private String nomBD = "cementerio";
    private String usuario = "root";
    private String password = "";
    private String msj;
    Object[][] data = {};
    String[] columnNames = {"Codigo gasto", "Fecha gasto", "Tipo venta", "Valor total", "Nro Factura", "Descripción"};
    DefaultTableModel modelolista = new DefaultTableModel(data, columnNames) {
        public boolean isCellEditable(int rowIndex, int vColIndex) {
            return false;
        }
    };

    public MantGastos() {
        initComponents();
        this.setLocationRelativeTo(null);
        conectar();
        lbl_estado.setVisible(false);
        lstgast.setModel(modelolista);
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
        btn_consultar.setOpaque(false);
        btn_consultar.setContentAreaFilled(false);
        btn_consultar.setBorderPainted(false);
    }

    public void limpiarlbl() {
        lb_err1.setText("");
        lb_err2.setText("");
        lb_err3.setText("");
        lb_err4.setText("");
        lb_err5.setText("");

    }

    public void llenarlst() {
        String cod, fecha, tipo, tipo2 = "", valor, nrofac, descripcion;
        Date fech = null;

        lstgast.setPreferredScrollableViewportSize(new Dimension(500, 70));
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelolista);
        lstgast.setRowSorter(sorter);
        try {
            sentencia = (Statement) conexion.createStatement();//permite ejecutar sentencias SQL
            ResultSet lista = sentencia.executeQuery("SELECT * FROM gastos");
            while (lista.next()) {
                cod = (lista.getString("cod_gasto"));//nombre del campo de la BD
                tipo = (lista.getString("tipo_venta"));
                switch (tipo) {
                    case "B":
                        tipo2 = "Boleta";
                        break;
                    case "F":
                        tipo2 = "Factura";
                        break;
                }
                valor = (lista.getString("valor_total"));
                nrofac = (lista.getString("nro_factura"));
                descripcion = (lista.getString("descripcion"));
                //Código para transformar la fecha rescatada y formatearla como String 
                fecha = lista.getString("fecha_gasto");
                SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    fech = parseador.parse(fecha);
                } catch (ParseException ex) {
                    Logger.getLogger(MantSepultados.class.getName()).log(Level.SEVERE, null, ex);
                }
                fecha = String.format(format.format(fech));

                Object[] newRow = {cod, fecha, tipo2, valor, nrofac, descripcion};
                modelolista.addRow(newRow);
            }
            lstgast.getRowSorter().toggleSortOrder(0);
            lstgast.getRowSorter().toggleSortOrder(0);
            limpiarlbl();
        } catch (SQLException e) {
            msj = "no tiene elementos";
        }

    }

    ;
    
    public void llenarlst_consu(String consu) {
        String cod, tipo, tipo2="", valor, nrofac, descripcion;
        Date fecha;
        lstgast.setPreferredScrollableViewportSize(new Dimension(500, 70));
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelolista);
        lstgast.setRowSorter(sorter);
        try {
            sentencia = (Statement) conexion.createStatement();//permite ejecutar sentencias SQL
            ResultSet lista = sentencia.executeQuery("SELECT * FROM gastos WHERE tipo_venta like '%" + consu + "%'or valor_total like '%" + consu + "%'"
                    + "or nro_factura like '%" + consu + "%' or fecha_gasto like '%" + consu + "%'");
            while (lista.next()) {
                cod = (lista.getString("cod_gasto"));//nombre del campo de la BD
                tipo = (lista.getString("tipo_venta"));
                switch (tipo) {
                    case "B":
                        tipo2 = "Boleta";
                        break;
                    case "F":
                        tipo2 = "Factura";
                        break;
                }
                valor = (lista.getString("valor_total"));
                nrofac = (lista.getString("nro_factura"));
                descripcion = (lista.getString("descripcion"));

                fecha = lista.getDate("fecha_gasto");

                Object[] newRow = {cod, fecha, tipo2, valor, nrofac, descripcion};
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
            lstgast.getRowSorter().toggleSortOrder(0);
            lstgast.getRowSorter().toggleSortOrder(0);
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
        txt_factura.setText("");
        txt_descripcion.setText("");
        txt_valor.setText("");
        cmb_tipo.setSelectedIndex(0);

        lstgast.clearSelection();
    }

    public void formatearfecha() {
        dc_fecha.setDateFormatString("dd-MM-yyyy");

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel12 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstgast = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_factura = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        lb_cod = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txt_descripcion = new javax.swing.JTextField();
        cmb_tipo = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        txt_consulta = new javax.swing.JTextField();
        dc_fecha = new com.toedter.calendar.JDateChooser();
        btn_agregar = new javax.swing.JButton();
        btn_modificar = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        btn_consultar = new javax.swing.JButton();
        lb_err4 = new javax.swing.JLabel();
        lb_err3 = new javax.swing.JLabel();
        lb_err2 = new javax.swing.JLabel();
        lb_err1 = new javax.swing.JLabel();
        lbl_estado = new javax.swing.JLabel();
        txt_valor = new javax.swing.JTextField();
        lb_err5 = new javax.swing.JLabel();

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/agregar.png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/consultar.png"))); // NOI18N

        jButton3.setBackground(new java.awt.Color(102, 204, 0));
        jButton3.setFont(new java.awt.Font("Franklin Gothic Medium", 3, 13)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/aceptar.png"))); // NOI18N
        jButton3.setText("Aceptar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(102, 0, 0));
        jButton4.setFont(new java.awt.Font("Franklin Gothic Medium", 3, 13)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cancelar.png"))); // NOI18N
        jButton4.setText("Cancelar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

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
        jLabel5.setText("Gastos");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Cod Gasto");
        jLabel6.setEnabled(false);

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Fecha gasto");

        lstgast.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"0130", "16/06/2018", "Factura", "86.598", "111", "Luz"},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Cod Gasto", "Fecha gasto", "Tipo venta", "Valor Total", "Nro factura", "Descripción"
            }
        ));
        lstgast.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstgastMouseClicked(evt);
            }
        });
        lstgast.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lstgastKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(lstgast);

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Tipo venta");

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Nro Factura");

        txt_factura.setBackground(new java.awt.Color(255, 255, 204));
        txt_factura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_facturaActionPerformed(evt);
            }
        });
        txt_factura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_facturaKeyTyped(evt);
            }
        });

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Valor Total");

        lb_cod.setForeground(new java.awt.Color(255, 255, 255));

        jLabel17.setForeground(new java.awt.Color(255, 255, 255));

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

        cmb_tipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccione una opción>", "Boleta", "Factura" }));

        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Descripción");

        jButton6.setBackground(new java.awt.Color(102, 0, 0));
        jButton6.setFont(new java.awt.Font("Franklin Gothic Medium", 3, 13)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cancelar.png"))); // NOI18N
        jButton6.setText("Cancelar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
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

        btn_consultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/consultar.png"))); // NOI18N
        btn_consultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_consultarActionPerformed(evt);
            }
        });

        lb_err4.setForeground(new java.awt.Color(255, 0, 51));
        lb_err4.setText("jLabel2");

        lb_err3.setForeground(new java.awt.Color(255, 0, 51));
        lb_err3.setText("jLabel2");

        lb_err2.setForeground(new java.awt.Color(255, 0, 51));
        lb_err2.setText("jLabel2");

        lb_err1.setForeground(new java.awt.Color(255, 0, 51));
        lb_err1.setText("jLabel2");

        txt_valor.setBackground(new java.awt.Color(255, 255, 204));
        txt_valor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_valorActionPerformed(evt);
            }
        });
        txt_valor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_valorKeyTyped(evt);
            }
        });

        lb_err5.setForeground(new java.awt.Color(255, 0, 51));
        lb_err5.setText("jLabel2");

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
                                .addComponent(btn_agregar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_modificar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_eliminar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                                .addComponent(txt_consulta, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_consultar)
                                .addGap(12, 12, 12))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(176, 176, 176)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_estado)
                                .addGap(56, 56, 56)))))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel14)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_factura, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lb_err4)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6)
                        .addGap(64, 64, 64))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(lb_cod)
                            .addComponent(jLabel8)
                            .addComponent(dc_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_valor, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lb_err3))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lb_err5))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cmb_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lb_err2)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel17))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(82, 82, 82)
                                .addComponent(lb_err1)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(97, 97, 97)
                        .addComponent(txt_consulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lbl_estado)
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btn_agregar)
                                .addComponent(btn_modificar)
                                .addComponent(btn_eliminar))
                            .addComponent(btn_consultar, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(38, 38, 38)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel6)
                        .addGap(0, 10, Short.MAX_VALUE)
                        .addComponent(lb_cod)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dc_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jLabel13))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lb_err1)
                                .addComponent(jLabel17)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_err2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lb_err3)
                            .addComponent(txt_valor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_factura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb_err4))
                        .addGap(1, 1, 1)
                        .addComponent(jLabel19))
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_err5))
                .addGap(28, 28, 28))
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

    public void actualizar(int cod, String fecha, String tipo, int valor, int nrofac, String descripcion) {
        String upSQL = "";

        try {
            upSQL = "UPDATE gastos SET fecha_gasto='" + fecha + "',tipo_venta='" + tipo + "',valor_total=" + valor + ",nro_factura=" + nrofac + ",descripcion='" + descripcion + "' WHERE cod_gasto=" + cod + "";
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

    public void eliminar(String cod, Date fecha, String tipo, String valor, String nrofac, String descripcion) {
        String upSQL = "";
        /*Mensaje message=new Mensaje();
        message.windowClosed(e);*/

        try {
            upSQL = "DELETE FROM gastos WHERE cod_gasto='" + cod + "';";
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


    private void txt_facturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_facturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_facturaActionPerformed

    private void txt_descripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_descripcionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_descripcionActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        MenuPrincipal mp = new MenuPrincipal();
        mp.setVisible(true);
        this.setVisible(false);
        

    //System.exit(0);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void txt_consultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_consultaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_consultaActionPerformed

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
        String cod, tipo = "", valor, nrofac, descripcion, fecha2;
        int valor2, nrofact2;
        Date fecha;

        //cod=lb_cod.getText();
        nrofac = txt_factura.getText();
        if (txt_factura.getText().isEmpty() || txt_factura.getText().startsWith(" ")) {
            lb_err4.setText("Ingrese un n° de factura");
            txt_factura.setFocusable(rootPaneCheckingEnabled);
            return;
        }

        nrofact2 = parseInt(nrofac);
        valor = txt_valor.getText();
        if (txt_valor.getText().isEmpty() || txt_valor.getText().startsWith(" ")) {
            lb_err3.setText("Ingrese un valor");
            txt_valor.setFocusable(rootPaneCheckingEnabled);
            return;
        }
        valor2 = parseInt(valor);

        descripcion = txt_descripcion.getText().toUpperCase();
        if (txt_descripcion.getText().isEmpty() || txt_descripcion.getText().startsWith(" ")) {
            lb_err5.setText("Ingrese una descripción");
            txt_descripcion.requestFocus();
            return;
        }

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
                tipo = "B";
                break;
            case 2:
                tipo = "F";
                break;

            default:
                lb_err2.setText("Debe seleccionar un tipo de venta!");
                break;
        }
        String sql = "INSERT INTO gastos(fecha_gasto,tipo_venta,valor_total,nro_factura,descripcion) "
                + "VALUES('" + fecha2 + "','" + tipo + "'," + valor2 + "," + nrofact2 + ",'" + descripcion + "')";
        //msj = sql;
        //JOptionPane.showMessageDialog(null, msj, "Datos Guardados", JOptionPane.INFORMATION_MESSAGE);
        insertar(sql);
        formatearfecha();
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void btn_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificarActionPerformed
        String cod, tipo = "", valor, nrofac, descripcion, fecha2;
        Date fecha;
         int codigo;
        if (lstgast.getSelectedRowCount() > 0) {

            cod = lstgast.getValueAt(lstgast.getSelectedRow(), 0).toString();
            codigo=parseInt(cod);

            nrofac = txt_factura.getText();
            if (txt_factura.getText().isEmpty() || txt_factura.getText().startsWith(" ")) {
                lb_err4.setText("Ingrese un valor");
                txt_factura.setFocusable(rootPaneCheckingEnabled);
                return;
            }
            int nrofact2 = parseInt(nrofac);

            valor = txt_valor.getText();
            if (txt_valor.getText().isEmpty() || txt_valor.getText().startsWith(" ")) {
                lb_err3.setText("Ingrese un valor");
                txt_valor.setFocusable(rootPaneCheckingEnabled);
                return;
            }
            int valor2 = parseInt(valor);

            descripcion = txt_descripcion.getText().toUpperCase();
            if (txt_descripcion.getText().isEmpty() || txt_descripcion.getText().startsWith(" ")) {
                lb_err4.setText("Ingrese una descripción");
                txt_descripcion.requestFocus();
                return;
            }

            dc_fecha.setDateFormatString("yyyy-MM-dd");
            fecha2 = dc_fecha.getDateFormatString();
            fecha = dc_fecha.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat(fecha2);
            fecha2 = String.valueOf(sdf.format(fecha));

            int index = cmb_tipo.getSelectedIndex();

            switch (index) {
                case 1:
                    tipo = "B";
                    break;
                case 2:
                    tipo = "F";
                    break;

                default:
                    lb_err2.setText("Debe seleccionar un tipo de venta!");
                    break;
            }

            limpiarlbl();

            actualizar(codigo, fecha2, tipo, valor2, nrofact2, descripcion);
            formatearfecha();
        } else {
            msj = "Seleccione una fila para modificar";
            JOptionPane.showMessageDialog(null, msj, "MODIFICACIÓN DE DATOS", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btn_modificarActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        String cod, tipo = "", valor, nrofac, descripcion;
        Date fecha;
        int index = cmb_tipo.getSelectedIndex();
        if (lstgast.getSelectedRowCount() > 0) {
            cod = lstgast.getValueAt(lstgast.getSelectedRow(), 0).toString();
            valor = txt_valor.getText();
            nrofac = txt_factura.getText();
            descripcion = txt_descripcion.getText();
            fecha = dc_fecha.getDate();
            switch (index) {
                case 1:
                    tipo = "B";
                    break;
                case 2:
                    tipo = "F";
                    break;

            }

            if (JOptionPane.showConfirmDialog(rootPane, "Se eliminará el registro",
                    "Eliminar", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                eliminar(cod, fecha, tipo, valor, nrofac, descripcion);
                formatearfecha();
            }
        } else {
            msj = "NO SE PUEDEN ELIMINAR DATOS";
            JOptionPane.showMessageDialog(null, msj, "ELIMINACIÓN DE DATOS", JOptionPane.INFORMATION_MESSAGE);
        }
        //llenarlst();
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void btn_consultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_consultarActionPerformed
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
    }//GEN-LAST:event_btn_consultarActionPerformed

    private void txt_valorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_valorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_valorActionPerformed

    private void lstgastMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstgastMouseClicked
        String cod, fecha, tipo = "", valor, nrofac, descripcion;
        java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("dd-MM-yyyy");

        if (lstgast.getSelectedRowCount() > 0) {
            cod = lstgast.getValueAt(lstgast.getSelectedRow(), 0).toString();
            jLabel6.enable();
            lb_cod.setText(cod);
            txt_valor.setText(lstgast.getValueAt(lstgast.getSelectedRow(), 3).toString());
            txt_factura.setText(lstgast.getValueAt(lstgast.getSelectedRow(), 4).toString());
            txt_descripcion.setText(lstgast.getValueAt(lstgast.getSelectedRow(), 5).toString());
            fecha = lstgast.getValueAt(lstgast.getSelectedRow(), 1).toString();
            try {
                java.util.Date fechDate = formato.parse(fecha);
                dc_fecha.setDate(fechDate);
            } catch (ParseException ex) {
                Logger.getLogger(MantSepultados.class.getName()).log(Level.SEVERE, null, ex);
            }
            tipo=lstgast.getValueAt(lstgast.getSelectedRow(), 2).toString();
            /*msj=tipo;
            JOptionPane.showMessageDialog(null, msj, "ELIMINACIÓN DE DATOS", JOptionPane.INFORMATION_MESSAGE);*/
            switch (tipo) {
                case "Boleta":
                    cmb_tipo.setSelectedItem("Boleta");
                    break;
                case "Factura":
                    cmb_tipo.setSelectedItem("Factura");
                    break;

            }

            limpiarlbl();
        }
    }//GEN-LAST:event_lstgastMouseClicked

    private void txt_valorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_valorKeyTyped
       char TipoTecla = evt.getKeyChar();
        if (txt_valor.getText().startsWith(" ")) {
            txt_valor.setText("");
            txt_valor.requestFocus();
        }
        if (txt_valor.getText().length() >= 7) {
            evt.consume();
        }
        limpiarlbl();
        if (Character.isDigit(TipoTecla)) {

        } else {
            evt.consume();
        }
    }//GEN-LAST:event_txt_valorKeyTyped

    private void txt_facturaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_facturaKeyTyped
         char TipoTecla = evt.getKeyChar();
        if (txt_factura.getText().startsWith(" ")) {
            txt_factura.setText("");
            txt_factura.requestFocus();
        }
        if (txt_factura.getText().length() >= 10) {
            evt.consume();
        }
        limpiarlbl();
        if (Character.isDigit(TipoTecla)) {

        } else {
            evt.consume();
        }
    }//GEN-LAST:event_txt_facturaKeyTyped

    private void txt_descripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_descripcionKeyTyped
        if (txt_descripcion.getText().startsWith(" ")) {
            txt_descripcion.setText("");
            txt_descripcion.requestFocus();
        }
        limpiarlbl();
        if (txt_descripcion.getText().length() >= 45) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_descripcionKeyTyped

    private void lstgastKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lstgastKeyReleased
        String cod, fecha, tipo = "", valor, nrofac, descripcion;
        java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("dd-MM-yyyy");
        if (lstgast.getSelectedRowCount() > 0) {

            if (evt.VK_DOWN == evt.getKeyCode() || evt.VK_UP == evt.getKeyCode()) {

                cod = lstgast.getValueAt(lstgast.getSelectedRow(), 0).toString();
            jLabel6.enable();
            lb_cod.setText(cod);
            txt_valor.setText(lstgast.getValueAt(lstgast.getSelectedRow(), 3).toString());
            txt_factura.setText(lstgast.getValueAt(lstgast.getSelectedRow(), 4).toString());
            txt_descripcion.setText(lstgast.getValueAt(lstgast.getSelectedRow(), 5).toString());
            fecha = lstgast.getValueAt(lstgast.getSelectedRow(), 1).toString();
            try {
                java.util.Date fechDate = formato.parse(fecha);
                dc_fecha.setDate(fechDate);
            } catch (ParseException ex) {
                Logger.getLogger(MantSepultados.class.getName()).log(Level.SEVERE, null, ex);
            }
            tipo=lstgast.getValueAt(lstgast.getSelectedRow(), 2).toString();
            /*msj=tipo;
            JOptionPane.showMessageDialog(null, msj, "ELIMINACIÓN DE DATOS", JOptionPane.INFORMATION_MESSAGE);*/
            switch (tipo) {
                case "Boleta":
                    cmb_tipo.setSelectedItem("Boleta");
                    break;
                case "Factura":
                    cmb_tipo.setSelectedItem("Factura");
                    break;

            }
                limpiarlbl();
            }
            if (evt.VK_DELETE == evt.getKeyCode()) {
                btn_eliminar.doClick();
            }
        }    
    }//GEN-LAST:event_lstgastKeyReleased

    private void txt_consultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_consultaMouseClicked
        limpiarlbl();
        txt_consulta.setText("");
        lb_cod.setText("");
        dc_fecha.setDate(null);
        cmb_tipo.setSelectedIndex(0);
        txt_valor.setText("");
        txt_factura.setText("");
        txt_descripcion.setText("");
        
        
        lstgast.clearSelection();
    }//GEN-LAST:event_txt_consultaMouseClicked

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        modelolista.setNumRows(0);
        llenarlst();
        txt_consulta.setText("");
        lb_cod.setText("");
        dc_fecha.setDate(null);
        cmb_tipo.setSelectedIndex(0);
        txt_valor.setText("");
        txt_factura.setText("");
        txt_descripcion.setText("");
        
        
        lstgast.clearSelection();
    }//GEN-LAST:event_jPanel1MouseClicked

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
            java.util.logging.Logger.getLogger(MantGastos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MantGastos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MantGastos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MantGastos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MantGastos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_consultar;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_modificar;
    private javax.swing.JComboBox<String> cmb_tipo;
    private com.toedter.calendar.JDateChooser dc_fecha;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lb_cod;
    private javax.swing.JLabel lb_err1;
    private javax.swing.JLabel lb_err2;
    private javax.swing.JLabel lb_err3;
    private javax.swing.JLabel lb_err4;
    private javax.swing.JLabel lb_err5;
    private javax.swing.JLabel lbl_estado;
    private javax.swing.JTable lstgast;
    private javax.swing.JTextField txt_consulta;
    private javax.swing.JTextField txt_descripcion;
    private javax.swing.JTextField txt_factura;
    private javax.swing.JTextField txt_valor;
    // End of variables declaration//GEN-END:variables
}
