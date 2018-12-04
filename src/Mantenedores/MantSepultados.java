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
import javax.swing.RowSorter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MantSepultados extends javax.swing.JFrame {

    private Statement sentencia;
    private Connection conexion;
    private String nomBD = "cementerio";
    private String usuario = "root";
    private String password = "";
    private String msj;
    Object[][] data = {};
    String[] columnNames = {"Run", "Nombre", "Apellido Paterno", "Apellido Materno", "Fecha Nacimiento", "Fecha Defunción", "Fecha Sepultación"};
    DefaultTableModel modelolista = new DefaultTableModel(data, columnNames) {
        public boolean isCellEditable(int rowIndex, int vColIndex) {
            return false;
        }
    };

    /**
     * Creates new form MantSepultados
     */
    public MantSepultados() {
        initComponents();
        this.setLocationRelativeTo(null);
        conectar();
        lbl_estado.setVisible(false);
        lstsep.setModel(modelolista);
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
        lb_err6.setText("");
        lb_err7.setText("");

    }

    public void llenarlst() {
        String run, dv, nom, apet, amat, fechanac, fechadef, fechasep;
        Date fech = null;
        lstsep.setPreferredScrollableViewportSize(new Dimension(500, 70));
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelolista);
        lstsep.setRowSorter(sorter);
        try {
            sentencia = (Statement) conexion.createStatement();//permite ejecutar sentencias SQL
            ResultSet lista = sentencia.executeQuery("SELECT * FROM sepultados");
            while (lista.next()) {
                nom = (lista.getString("nom_sepultado"));//nombre del campo de la BD
                run = (lista.getString("run_sepultado"));
                dv = (lista.getString("dv_sepultado"));
                apet = (lista.getString("ape_pat_sepultado"));
                amat = (lista.getString("ape_mat_sepultado"));
                //Código para transformar la fecha rescatada y formatearla como String 
                fechanac = lista.getString("fecha_nac_sepultado");
                SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    fech = parseador.parse(fechanac);
                } catch (ParseException ex) {
                    Logger.getLogger(MantSepultados.class.getName()).log(Level.SEVERE, null, ex);
                }
                fechanac = String.format(format.format(fech));

                fechadef = (lista.getString("fecha_defun_sepultado"));
                parseador = new SimpleDateFormat("yyyy-MM-dd");
                format = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    fech = parseador.parse(fechadef);
                } catch (ParseException ex) {
                    Logger.getLogger(MantSepultados.class.getName()).log(Level.SEVERE, null, ex);
                }
                fechadef = String.format(format.format(fech));

                fechasep = (lista.getString("fecha_sepultacion"));
                parseador = new SimpleDateFormat("yyyy-MM-dd");
                format = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    fech = parseador.parse(fechasep);
                } catch (ParseException ex) {
                    Logger.getLogger(MantSepultados.class.getName()).log(Level.SEVERE, null, ex);
                }
                fechasep = String.format(format.format(fech));

                Object[] newRow = {run + "-" + dv, nom, apet, amat, fechanac, fechadef, fechasep};
                modelolista.addRow(newRow);
            }
            lstsep.getRowSorter().toggleSortOrder(0);
            lstsep.getRowSorter().toggleSortOrder(0);
            limpiarlbl();
        } catch (SQLException e) {
            msj = "no tiene elementos";
        }

    }

    ;
    
    public void llenarlst_consu(String consu) {
        String run, dv, nom, apet, amat, fechadef, fechasep;
        Date fechanac;
        lstsep.setPreferredScrollableViewportSize(new Dimension(500, 70));
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelolista);
        lstsep.setRowSorter(sorter);
        try {
            sentencia = (Statement) conexion.createStatement();//permite ejecutar sentencias SQL
            ResultSet lista = sentencia.executeQuery("SELECT * FROM sepultados WHERE nom_sepultado like '%" + consu + "%'or run_sepultado like '%" + consu + "%'"
                    + "or ape_pat_sepultado like '%" + consu + "%' or ape_mat_sepultado like '%" + consu + "%' or fecha_sepultacion like '%" + consu + "%'");
            while (lista.next()) {
                nom = (lista.getString("nom_sepultado"));//nombre del campo de la BD
                run = (lista.getString("run_sepultado"));
                dv = (lista.getString("dv_sepultado"));
                apet = (lista.getString("ape_pat_sepultado"));
                amat = (lista.getString("ape_mat_sepultado"));
                fechanac = (lista.getDate("fecha_nac_sepultado"));
                fechadef = (lista.getString("fecha_defun_sepultado"));
                fechasep = (lista.getString("fecha_sepultacion"));
                Object[] newRow = {run + "-" + dv, nom, apet, amat, fechanac, fechadef, fechasep};
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
            lstsep.getRowSorter().toggleSortOrder(0);
            lstsep.getRowSorter().toggleSortOrder(0);
        } catch (SQLException e) {
            msj = "consulta errónea";
            JOptionPane.showMessageDialog(null, msj, "Falla Consulta", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    ;
    
    public void limpiar() {
        txt_consulta.setText("");
        dc_fechnac.setDate(null);
        dc_fechadef.setDate(null);
        txt_dv.setText("");
        txt_materno.setText("");
        txt_nombre.setText("");
        txt_paterno.setText("");
        txt_rut.setText("");
        dc_fechasepul.setDate(null);
        txt_rut.enable();
        txt_dv.enable();
        lstsep.clearSelection();
    }

    public void formatearfecha() {
        dc_fechnac.setDateFormatString("dd-MM-yyyy");
        dc_fechadef.setDateFormatString("dd-MM-yyyy");
        dc_fechasepul.setDateFormatString("dd-MM-yyyy");
    }

    /*private JDateChooser dateChooser;
    dateChooser=new JDateChooser();
    dateChooser.setBounds(r);
    JPanel.add(calendar);*/
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        main = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstsep = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        txt_dv = new javax.swing.JTextField();
        txt_rut = new javax.swing.JTextField();
        txt_paterno = new javax.swing.JTextField();
        txt_nombre = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_materno = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        txt_consulta = new javax.swing.JTextField();
        btn_agregar = new javax.swing.JButton();
        btn_modificar = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        btn_consultar = new javax.swing.JButton();
        lbl_estado = new javax.swing.JLabel();
        lb_err1 = new javax.swing.JLabel();
        lb_err2 = new javax.swing.JLabel();
        lb_err4 = new javax.swing.JLabel();
        lb_err3 = new javax.swing.JLabel();
        lb_err5 = new javax.swing.JLabel();
        lb_err6 = new javax.swing.JLabel();
        lb_err7 = new javax.swing.JLabel();
        dc_fechnac = new com.toedter.calendar.JDateChooser();
        dc_fechadef = new com.toedter.calendar.JDateChooser();
        dc_fechasepul = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        main.setBackground(new java.awt.Color(51, 51, 51));
        main.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mainMouseClicked(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cruzmini.png"))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Franklin Gothic Medium", 3, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Sepultados");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Rut Sepultado");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Nombre ");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Fecha defunción");

        lstsep.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"6036497-9", "Felipito", "Flores", "Rebolledo", "02/07/1942", "27/03/2016", "28/03/2016"},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Rut Sepultado", "Nombre", "Apellido Parterno", "Apellido Materno", "Fecha Nacimiento", "Fecha defunción", "Fecha Sepultación"
            }
        ));
        lstsep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstsepMouseClicked(evt);
            }
        });
        lstsep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lstsepKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(lstsep);

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Fecha Nacimiento");

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

        txt_rut.setBackground(new java.awt.Color(255, 255, 204));
        txt_rut.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_rutFocusLost(evt);
            }
        });
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

        txt_paterno.setBackground(new java.awt.Color(255, 255, 204));
        txt_paterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_paternoActionPerformed(evt);
            }
        });
        txt_paterno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_paternoKeyTyped(evt);
            }
        });

        txt_nombre.setBackground(new java.awt.Color(255, 255, 204));
        txt_nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nombreActionPerformed(evt);
            }
        });
        txt_nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombreKeyTyped(evt);
            }
        });

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Apellido Paterno ");

        txt_materno.setBackground(new java.awt.Color(255, 255, 204));
        txt_materno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_maternoActionPerformed(evt);
            }
        });
        txt_materno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_maternoKeyTyped(evt);
            }
        });

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Apellido Materno ");

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Fecha Sepultación");

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
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_consultaKeyPressed(evt);
            }
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
        btn_consultar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_consultarMouseClicked(evt);
            }
        });
        btn_consultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_consultarActionPerformed(evt);
            }
        });

        lb_err1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lb_err1.setForeground(new java.awt.Color(255, 0, 51));
        lb_err1.setText("jLabel2");

        lb_err2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lb_err2.setForeground(new java.awt.Color(255, 0, 51));
        lb_err2.setText("jLabel2");

        lb_err4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lb_err4.setForeground(new java.awt.Color(255, 0, 51));
        lb_err4.setText("jLabel2");

        lb_err3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lb_err3.setForeground(new java.awt.Color(255, 0, 51));
        lb_err3.setText("jLabel2");

        lb_err5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lb_err5.setForeground(new java.awt.Color(255, 0, 51));
        lb_err5.setText("jLabel2");

        lb_err6.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lb_err6.setForeground(new java.awt.Color(255, 0, 51));
        lb_err6.setText("jLabel2");

        lb_err7.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lb_err7.setForeground(new java.awt.Color(255, 0, 51));
        lb_err7.setText("jLabel2");

        javax.swing.GroupLayout mainLayout = new javax.swing.GroupLayout(main);
        main.setLayout(mainLayout);
        mainLayout.setHorizontalGroup(
            mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainLayout.createSequentialGroup()
                .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addGroup(mainLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel1)
                                .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(mainLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jSeparator2)
                                            .addGroup(mainLayout.createSequentialGroup()
                                                .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(mainLayout.createSequentialGroup()
                                                        .addGap(176, 176, 176)
                                                        .addComponent(jLabel5))
                                                    .addGroup(mainLayout.createSequentialGroup()
                                                        .addGap(64, 64, 64)
                                                        .addComponent(btn_agregar)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(btn_modificar)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(btn_eliminar)
                                                        .addGap(30, 30, 30)
                                                        .addComponent(txt_consulta, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(btn_consultar)))
                                                .addGap(0, 213, Short.MAX_VALUE))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lbl_estado)
                                        .addGap(107, 107, 107))))))
                    .addGroup(mainLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainLayout.createSequentialGroup()
                                .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(mainLayout.createSequentialGroup()
                                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addGroup(mainLayout.createSequentialGroup()
                                                .addComponent(txt_rut, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txt_dv, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(mainLayout.createSequentialGroup()
                                                .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txt_nombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(mainLayout.createSequentialGroup()
                                                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(lb_err1)
                                                            .addComponent(jLabel8)
                                                            .addComponent(lb_err2))
                                                        .addGap(57, 57, 57)))
                                                .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(mainLayout.createSequentialGroup()
                                                        .addGap(16, 16, 16)
                                                        .addComponent(jLabel10))
                                                    .addGroup(mainLayout.createSequentialGroup()
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(lb_err3)
                                                            .addComponent(txt_paterno, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(mainLayout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton2)))
                                .addGap(104, 104, 104))
                            .addGroup(mainLayout.createSequentialGroup()
                                .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(txt_materno, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel9)
                                    .addComponent(lb_err4)
                                    .addGroup(mainLayout.createSequentialGroup()
                                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(dc_fechasepul, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                                            .addComponent(dc_fechnac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(dc_fechadef, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(mainLayout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(lb_err5)
                                                    .addComponent(lb_err6)))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainLayout.createSequentialGroup()
                                                .addGap(12, 12, 12)
                                                .addComponent(lb_err7)))))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        mainLayout.setVerticalGroup(
            mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainLayout.createSequentialGroup()
                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txt_consulta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(mainLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_estado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(mainLayout.createSequentialGroup()
                                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(16, 16, 16)
                                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btn_agregar)
                                            .addComponent(btn_modificar)
                                            .addComponent(btn_eliminar)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainLayout.createSequentialGroup()
                                        .addGap(33, 33, 33)
                                        .addComponent(btn_consultar)))))
                        .addGap(52, 52, 52)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jLabel6)
                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_dv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_rut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lb_err1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_paterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_err2)
                    .addComponent(lb_err3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_materno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_err4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(lb_err6)))
                    .addGroup(mainLayout.createSequentialGroup()
                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dc_fechnac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb_err5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addGap(13, 13, 13)
                        .addComponent(dc_fechadef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_err7)
                    .addComponent(dc_fechasepul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(100, 100, 100))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(main, javax.swing.GroupLayout.PREFERRED_SIZE, 712, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
            JOptionPane.showMessageDialog(null, msj, "---", JOptionPane.INFORMATION_MESSAGE);

        }
        modelolista.setNumRows(0);
        //lstClie.remove();
        llenarlst();
        limpiar();
    }

    public void actualizar(String rut, String dv, String nom, String pater, String mater, String fechanac, String fechadef, String fechasep) {
        String upSQL = "";

        try {
            upSQL = "UPDATE sepultados SET nom_sepultado='" + nom + "',ape_pat_sepultado='" + pater + "',ape_mat_sepultado='" + mater + "',fecha_nac_sepultado='" + fechanac + "',fecha_defun_sepultado='" + fechadef + "',fecha_sepultacion='" + fechasep + "' WHERE run_sepultado='" + rut + "'";
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

    public void eliminar(String rut, String dv, String nom, String pater, String mater, Date fechanac, Date fechadef, Date fechasep) {
        String upSQL = "";
        /*Mensaje message=new Mensaje();
        message.windowClosed(e);*/

        try {
            upSQL = "DELETE FROM sepultados WHERE run_sepultado='" + rut + "';";
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
    private void txt_dvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dvActionPerformed

    private void txt_rutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rutActionPerformed

    private void txt_paternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_paternoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_paternoActionPerformed

    private void txt_nombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombreActionPerformed

    private void txt_maternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_maternoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_maternoActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txt_consultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_consultaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_consultaActionPerformed

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
        String rut, dv, nom, apepar, apemat, fechanac2, fechadef2, fechasep2;
        Date fechanac, fechadef, fechasep;
        rut = txt_rut.getText();
        if (txt_rut.getText().isEmpty() || txt_rut.getText().startsWith(" ")) {
            lb_err1.setText("Ingrese el rut");
            txt_rut.setFocusable(rootPaneCheckingEnabled);
            return;
        }
        dv = txt_dv.getText().toUpperCase();
        nom = txt_nombre.getText().toUpperCase();
        if (txt_nombre.getText().isEmpty() || txt_nombre.getText().startsWith(" ")) {
            lb_err2.setText("Ingrese el nombre");
            txt_nombre.requestFocus();
            return;
        }
        apepar = txt_paterno.getText().toUpperCase();
        if (txt_paterno.getText().isEmpty() || txt_paterno.getText().startsWith(" ")) {
            lb_err3.setText("Ingrese el apellido");
            txt_paterno.requestFocus();
            return;
        }
        apemat = txt_materno.getText().toUpperCase();
        if (txt_materno.getText().isEmpty() || txt_materno.getText().startsWith(" ")) {
            lb_err4.setText("Ingrese el apellido materno");
            txt_materno.requestFocus();
            return;
        }
        dc_fechnac.setDateFormatString("yyyy-MM-dd");
        fechanac2 = dc_fechnac.getDateFormatString();
        fechanac = dc_fechnac.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat(fechanac2);
        fechanac2 = String.valueOf(sdf.format(fechanac));
        /*if(txt_fechnac.getDate().equals("")){
            lb_err5.setText("Ingrese la fecha de nacimiento");
            txt_fechnac.requestFocus();
            return;
        }*/

        dc_fechadef.setDateFormatString("yyyy-MM-dd");
        fechadef2 = dc_fechadef.getDateFormatString();
        fechadef = dc_fechadef.getDate();
        sdf = new SimpleDateFormat(fechadef2);
        fechadef2 = String.valueOf(sdf.format(fechadef));

        dc_fechasepul.setDateFormatString("yyyy-MM-dd");
        fechasep2 = dc_fechasepul.getDateFormatString();
        fechasep = dc_fechasepul.getDate();
        sdf = new SimpleDateFormat(fechasep2);
        fechasep2 = String.valueOf(sdf.format(fechasep));

        String sql = "INSERT INTO sepultados(run_sepultado,dv_sepultado,nom_sepultado,ape_pat_sepultado,ape_mat_sepultado,fecha_nac_sepultado,fecha_defun_sepultado,fecha_sepultacion) "
                + "VALUES('" + rut + "','" + dv + "','" + nom + "','" + apepar + "','" + apemat + "','" + fechanac2 + "','" + fechadef2 + "','" + fechasep2 + "')";
        //msj = sql;
        //JOptionPane.showMessageDialog(null, msj, "Datos Guardados", JOptionPane.INFORMATION_MESSAGE);
        insertar(sql);
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void btn_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificarActionPerformed
        String rut, dv, nom, apepar, apemat, fechanac2, fechadef2, fechasep2;
        Date fechanac, fechadef, fechasep;
        //java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("yyyy-MM-dd");
        if (lstsep.getSelectedRowCount() > 0) {

            rut = txt_rut.getText();
            dv = txt_dv.getText().toUpperCase();
            nom = txt_nombre.getText().toUpperCase();
            if (txt_nombre.getText().isEmpty() || txt_nombre.getText().startsWith(" ")) {
                lb_err2.setText("Ingrese el nombre");
                txt_nombre.requestFocus();
                return;
            }
            apepar = txt_paterno.getText().toUpperCase();
            if (txt_paterno.getText().isEmpty() || txt_paterno.getText().startsWith(" ")) {
                lb_err3.setText("Ingrese el apellido");
                txt_paterno.requestFocus();
                return;
            }
            apemat = txt_materno.getText().toUpperCase();
            if (txt_materno.getText().isEmpty() || txt_materno.getText().startsWith(" ")) {
                lb_err4.setText("Ingrese el apellido materno");
                txt_materno.requestFocus();
                return;
            }

            dc_fechnac.setDateFormatString("yyyy-MM-dd");
            fechanac2 = dc_fechnac.getDateFormatString();
            fechanac = dc_fechnac.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat(fechanac2);
            fechanac2 = String.valueOf(sdf.format(fechanac));

            if (dc_fechnac.getDate().equals(null)) {
                lb_err5.setText("Ingrese la fecha de nacimiento");

                return;
            }
            dc_fechadef.setDateFormatString("yyyy-MM-dd");
            fechadef2 = dc_fechadef.getDateFormatString();
            fechadef = dc_fechadef.getDate();
            sdf = new SimpleDateFormat(fechadef2);
            fechadef2 = String.valueOf(sdf.format(fechadef));

            if (dc_fechadef.getDate().equals(null)) {
                lb_err6.setText("Ingrese la fecha de defunción");

                return;
            }
            dc_fechasepul.setDateFormatString("yyyy-MM-dd");
            fechasep2 = dc_fechasepul.getDateFormatString();
            fechasep = dc_fechasepul.getDate();
            sdf = new SimpleDateFormat(fechasep2);
            fechasep2 = String.valueOf(sdf.format(fechasep));

            if (dc_fechasepul.getDate().equals(null)) {
                lb_err7.setText("Ingrese la fecha de sepultacion");

                return;
            }

            limpiarlbl();

            actualizar(rut, dv, nom, apepar, apemat, fechanac2, fechadef2, fechasep2);
            formatearfecha();
        } else {
            msj = "Seleccione una fila para modificar";
            JOptionPane.showMessageDialog(null, msj, "MODIFICACIÓN DE DATOS", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btn_modificarActionPerformed

    private void lstsepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstsepMouseClicked
        String rut, dv, nom, apepar, apemat, fechanac, fechadef, fechasep;
        java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("dd-MM-yyyy");

        if (lstsep.getSelectedRowCount() > 0) {
            rut = lstsep.getValueAt(lstsep.getSelectedRow(), 0).toString();
            rut = rut.substring(0, rut.length() - 2);
            txt_rut.setText(rut);
            txt_rut.disable();
            dv = lstsep.getValueAt(lstsep.getSelectedRow(), 0).toString();
            dv = dv.substring(dv.length() - 1, dv.length());
            txt_dv.setText(dv);
            txt_dv.disable();
            txt_nombre.setText(lstsep.getValueAt(lstsep.getSelectedRow(), 1).toString());
            txt_paterno.setText(lstsep.getValueAt(lstsep.getSelectedRow(), 2).toString());
            txt_materno.setText(lstsep.getValueAt(lstsep.getSelectedRow(), 3).toString());
            fechanac = lstsep.getValueAt(lstsep.getSelectedRow(), 4).toString();
            try {
                java.util.Date fechDate = formato.parse(fechanac);
                dc_fechnac.setDate(fechDate);
            } catch (ParseException ex) {
                Logger.getLogger(MantSepultados.class.getName()).log(Level.SEVERE, null, ex);
            }

            fechadef = lstsep.getValueAt(lstsep.getSelectedRow(), 5).toString();
            try {
                java.util.Date fechDate = formato.parse(fechadef);
                dc_fechadef.setDate(fechDate);
            } catch (ParseException ex) {
                Logger.getLogger(MantSepultados.class.getName()).log(Level.SEVERE, null, ex);
            }
            fechasep = lstsep.getValueAt(lstsep.getSelectedRow(), 6).toString();
            try {
                java.util.Date fechDate = formato.parse(fechasep);
                dc_fechasepul.setDate(fechDate);
            } catch (ParseException ex) {
                Logger.getLogger(MantSepultados.class.getName()).log(Level.SEVERE, null, ex);
            }
            limpiarlbl();
        }
    }//GEN-LAST:event_lstsepMouseClicked

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        String rut, dv, nom, apepar, apemat;
        Date fechanac, fechadef, fechasep;
        if (lstsep.getSelectedRowCount() > 0) {
            rut = txt_rut.getText();
            dv = txt_dv.getText();
            nom = txt_nombre.getText();
            apepar = txt_paterno.getText();
            apemat = txt_materno.getText();
            fechanac = dc_fechnac.getDate();
            fechadef = dc_fechadef.getDate();
            fechasep = dc_fechasepul.getDate();

            if (JOptionPane.showConfirmDialog(rootPane, "Se eliminará el registro",
                    "Eliminar", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                eliminar(rut, dv, nom, apepar, apemat, fechanac, fechadef, fechasep);
            }
        } else {
            msj = "NO SE PUEDEN ELIMINAR DATOS";
            JOptionPane.showMessageDialog(null, msj, "ELIMINACIÓN DE DATOS", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btn_eliminarActionPerformed

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

    private void txt_consultaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_consultaKeyPressed
        if (evt.VK_ENTER == evt.getKeyCode()) {
            btn_consultar.doClick();
        }
    }//GEN-LAST:event_txt_consultaKeyPressed

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
    }//GEN-LAST:event_txt_consultaKeyTyped

    private void mainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainMouseClicked
        modelolista.setNumRows(0);
        llenarlst();
        txt_consulta.setText("");
        dc_fechnac.setDate(null);
        dc_fechadef.setDate(null);
        txt_dv.setText("");

        txt_materno.setText("");
        txt_nombre.setText("");
        txt_paterno.setText("");
        txt_rut.setText("");
        dc_fechasepul.setDate(null);
        txt_rut.enable();
        txt_dv.enable();
        lstsep.clearSelection();
    }//GEN-LAST:event_mainMouseClicked

    private void lstsepKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lstsepKeyReleased
        String rut, dv, nom, apepar, apemat, fechanac, fechadef, fechasep;
        java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("dd-MM-yyyy");
        if (lstsep.getSelectedRowCount() > 0) {

            if (evt.VK_DOWN == evt.getKeyCode() || evt.VK_UP == evt.getKeyCode()) {

                rut = lstsep.getValueAt(lstsep.getSelectedRow(), 0).toString();
                rut = rut.substring(0, rut.length() - 2);
                txt_rut.setText(rut);
                txt_rut.disable();
                dv = lstsep.getValueAt(lstsep.getSelectedRow(), 0).toString();
                dv = dv.substring(dv.length() - 1, dv.length());
                txt_dv.setText(dv);
                txt_dv.disable();
                txt_nombre.setText(lstsep.getValueAt(lstsep.getSelectedRow(), 1).toString());
                txt_paterno.setText(lstsep.getValueAt(lstsep.getSelectedRow(), 2).toString());
                txt_materno.setText(lstsep.getValueAt(lstsep.getSelectedRow(), 3).toString());
                fechanac = lstsep.getValueAt(lstsep.getSelectedRow(), 4).toString();
                try {
                    java.util.Date fechDate = formato.parse(fechanac);
                    dc_fechnac.setDate(fechDate);
                } catch (ParseException ex) {
                    Logger.getLogger(MantSepultados.class.getName()).log(Level.SEVERE, null, ex);
                }

                fechadef = lstsep.getValueAt(lstsep.getSelectedRow(), 5).toString();
                try {
                    java.util.Date fechDate = formato.parse(fechadef);
                    dc_fechadef.setDate(fechDate);
                } catch (ParseException ex) {
                    Logger.getLogger(MantSepultados.class.getName()).log(Level.SEVERE, null, ex);
                }
                fechasep = lstsep.getValueAt(lstsep.getSelectedRow(), 6).toString();
                try {
                    java.util.Date fechDate = formato.parse(fechasep);
                    dc_fechasepul.setDate(fechDate);
                } catch (ParseException ex) {
                    Logger.getLogger(MantSepultados.class.getName()).log(Level.SEVERE, null, ex);
                }
                limpiarlbl();
            }
            if (evt.VK_DELETE == evt.getKeyCode()) {
                btn_eliminar.doClick();
            }
        }
    }//GEN-LAST:event_lstsepKeyReleased

    private void txt_consultaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_consultaKeyReleased
        if ((evt.VK_BACK_SPACE == evt.getKeyCode() || evt.VK_DELETE == evt.getKeyCode()) && (txt_consulta.getText().isEmpty())) {
            modelolista.setNumRows(0);
            llenarlst();
        }
    }//GEN-LAST:event_txt_consultaKeyReleased

    private void txt_consultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_consultaMouseClicked
        limpiarlbl();
        dc_fechnac.setDate(null);
        dc_fechasepul.setDate(null);
        txt_dv.setText("");
        txt_materno.setText("");
        txt_nombre.setText("");
        txt_paterno.setText("");
        txt_rut.setText("");
        dc_fechadef.setDate(null);
        txt_rut.enable();
        //txt_dv.enable();
        lstsep.clearSelection();
    }//GEN-LAST:event_txt_consultaMouseClicked

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

    private void txt_nombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombreKeyTyped
        char TipoTecla = evt.getKeyChar();
        if (txt_nombre.getText().length() >= 45) {
            evt.consume();
        }
        limpiarlbl();
        if (txt_nombre.getText().startsWith(" ")) {
            txt_nombre.setText("");
            txt_nombre.requestFocus();
        }
        if (Character.isDigit(TipoTecla) || !Character.isLetter(TipoTecla) && !(TipoTecla == evt.VK_SPACE) && !(TipoTecla == evt.VK_BACK_SPACE)) {
            evt.consume();
        } else {

        }
    }//GEN-LAST:event_txt_nombreKeyTyped

    private void txt_paternoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_paternoKeyTyped
        char TipoTecla = evt.getKeyChar();
        if (txt_paterno.getText().length() >= 45) {
            evt.consume();
        }
        limpiarlbl();
        if (txt_paterno.getText().startsWith(" ")) {
            txt_paterno.setText("");
            txt_paterno.requestFocus();
        }
        if (Character.isDigit(TipoTecla) || !Character.isLetter(TipoTecla) && !(TipoTecla == evt.VK_SPACE) && !(TipoTecla == evt.VK_BACK_SPACE)) {
            evt.consume();
        } else {

        }
    }//GEN-LAST:event_txt_paternoKeyTyped

    private void txt_maternoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_maternoKeyTyped
        if (txt_materno.getText().length() >= 45) {
            evt.consume();
        }
        limpiarlbl();
        if (txt_materno.getText().startsWith(" ")) {
            txt_materno.setText("");
            txt_materno.requestFocus();
        }
        char TipoTecla = evt.getKeyChar();
        if (Character.isDigit(TipoTecla) || !Character.isLetter(TipoTecla) && !(TipoTecla == evt.VK_SPACE) && !(TipoTecla == evt.VK_BACK_SPACE)) {
            evt.consume();
        } else {

        }
    }//GEN-LAST:event_txt_maternoKeyTyped

    private void txt_rutFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_rutFocusLost

    }//GEN-LAST:event_txt_rutFocusLost

    private void txt_rutKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_rutKeyReleased
        if ((evt.VK_BACK_SPACE == evt.getKeyCode() || evt.VK_DELETE == evt.getKeyCode()) && (txt_consulta.getText().isEmpty())) {
            txt_dv.setText("");
            //txt_rut.setForeground(Color.GRAY);
            //txt_rut.setText("12345678");
        }
    }//GEN-LAST:event_txt_rutKeyReleased

    private void btn_consultarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_consultarMouseClicked
        txt_consulta.setText("");
    }//GEN-LAST:event_btn_consultarMouseClicked

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
            java.util.logging.Logger.getLogger(MantSepultados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MantSepultados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MantSepultados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MantSepultados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MantSepultados().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_consultar;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_modificar;
    private com.toedter.calendar.JDateChooser dc_fechadef;
    private com.toedter.calendar.JDateChooser dc_fechasepul;
    private com.toedter.calendar.JDateChooser dc_fechnac;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lb_err1;
    private javax.swing.JLabel lb_err2;
    private javax.swing.JLabel lb_err3;
    private javax.swing.JLabel lb_err4;
    private javax.swing.JLabel lb_err5;
    private javax.swing.JLabel lb_err6;
    private javax.swing.JLabel lb_err7;
    private javax.swing.JLabel lbl_estado;
    private javax.swing.JTable lstsep;
    private javax.swing.JPanel main;
    private javax.swing.JTextField txt_consulta;
    private javax.swing.JTextField txt_dv;
    private javax.swing.JTextField txt_materno;
    private javax.swing.JTextField txt_nombre;
    private javax.swing.JTextField txt_paterno;
    private javax.swing.JTextField txt_rut;
    // End of variables declaration//GEN-END:variables

}
