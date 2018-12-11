package Mantenedores;

import Menúes.MenuPrincipal;
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
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.RowSorter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class MantTrabajadores extends javax.swing.JFrame {
    private Statement sentencia;
    private Connection conexion;
    private String nomBD="cementerio";
    private String usuario ="root";
    private String password ="";
    private String msj;
    Object[][] data = {};
    String[] columnNames={"Run","Nombre","Apellido Paterno","Apellido Materno","Cargo","Direccion","Telefono","Correo","Fecha nacimiento","Codigo contrato","Tipo contrato" };
    DefaultTableModel modelolista=new DefaultTableModel(data,columnNames){
        public boolean isCellEditable(int rowIndex, int vColIndex) {
            return false;
        }
     };    
    
    public MantTrabajadores() {
        initComponents();
        this.setLocationRelativeTo(null);
        conectar();
        lbl_estado.setVisible(false);
        lsttrab.setModel(modelolista);
        llenarlst(); 
        transparenciaButton();
        limpiarlbl();
        
    }
    public void conectar(){
         try{
           Class.forName("com.mysql.jdbc.Driver"); 
           String url="jdbc:mysql://localhost:3306/"+this.nomBD;
           this.conexion=DriverManager.getConnection(url,this.usuario,this.password);
           this.sentencia=(Statement)this.conexion.createStatement();
       }catch(Exception e){
           msj="error al conectar";
           JOptionPane.showMessageDialog(null, msj, "Datos Guardados", JOptionPane.INFORMATION_MESSAGE);
           
       }
    }
    
    public void transparenciaButton(){
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
        btn_contrato.setOpaque(false);
        btn_contrato.setContentAreaFilled(false);
        btn_contrato.setBorderPainted(false);
    }
    public void limpiarlbl(){
        lb_err1.setText("");
        lb_err2.setText("");
        lb_err3.setText("");
        lb_err4.setText("");
        lb_err5.setText("");
        lb_err6.setText("");
        lb_err7.setText("");
        lb_err8.setText("");
        lb_err9.setText("");
        lb_err10.setText("");
    }

     public void llenarlst(){
       String run,dv,nom,apet,amat,cargo,fechanac,tipo2="",dir,fono,correo,contr,tipo;
       Date fech=null;
       lsttrab.setPreferredScrollableViewportSize(new Dimension(500,70));
        RowSorter<TableModel> sorter=new TableRowSorter<TableModel>(modelolista);
        lsttrab.setRowSorter(sorter);
        try{
            sentencia=(Statement)conexion.createStatement();//permite ejecutar sentencias SQL
            ResultSet lista=sentencia.executeQuery("SELECT * FROM trabajadores");
            while(lista.next()){
                nom=(lista.getString("nombre_trabajador"));//nombre del campo de la BD
                run=(lista.getString("run_trabajador"));
                dv=(lista.getString("dv_trabajador"));
                apet=(lista.getString("ape_pat_trabajador"));
                amat=(lista.getString("ape_mat_trabajador"));
                cargo=(lista.getString("cargo_trabajador"));
                
                fechanac=(lista.getString("fecha_nac_trabajador"));
                SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    fech = parseador.parse(fechanac);
                } catch (ParseException ex) {
                    Logger.getLogger(MantSepultados.class.getName()).log(Level.SEVERE, null, ex);
                }
                fechanac = String.format(format.format(fech));
                
                
                
                
                dir=(lista.getString("direccion"));
                fono=(lista.getString("fono"));
                correo=(lista.getString("correo_trabajador"));
                contr=(lista.getString("cod_contrato"));
                
                
                tipo=(lista.getString("tipo_contrato"));
                switch (tipo) {
                    case "PF":
                        tipo2 = "Plazo Fijo";
                        break;
                    case "PI":
                        tipo2 = "Plazo Indefinido";
                        break;
                    case "PO":
                        tipo2 = "Por Obra";
                        break;
                    case "TA":
                        tipo2 = "Trabajo Atípico";
                        break;  
                    case "TE":
                        tipo2 = "Trabajos Especiales";
                        break;    
                        
                        
                }
                
                
                
                Object[] newRow={run+"-"+dv,nom,apet,amat,cargo,fechanac,dir,fono,correo,contr,tipo2};
                modelolista.addRow(newRow);
            }
        lsttrab.getRowSorter().toggleSortOrder(0);
        lsttrab.getRowSorter().toggleSortOrder(0);
        limpiarlbl();
        }catch (SQLException e){
            msj="no tiene elementos";
        }
        
    };
     
     public void llenarlst_consu(String consu){
       String run,dv,nom,apet,amat,cargo,fechanac,dir,fono,correo,contr,tipo,tipo2="";
       Date fech=null;
       lsttrab.setPreferredScrollableViewportSize(new Dimension(500,70));
        RowSorter<TableModel> sorter=new TableRowSorter<TableModel>(modelolista);
        lsttrab.setRowSorter(sorter);
        try{
            sentencia=(Statement)conexion.createStatement();//permite ejecutar sentencias SQL
            ResultSet lista=sentencia.executeQuery("SELECT * FROM trabajadores WHERE nombre_trabajador like '%"+consu+"%'or run_trabajador like '%"+consu+"%'"
                    + "or ape_pat_trabajador like '%"+consu+"%' or ape_mat_trabajador like '%"+consu+"%' or cargo_trabajador like '%"+consu+"%' or direccion like '%"+consu+"%' or tipo_contrato like '%"+consu+"%'");
           
            while(lista.next()){
                nom=(lista.getString("nombre_trabajador"));//nombre del campo de la BD
                run=(lista.getString("run_trabajador"));
                dv=(lista.getString("dv_trabajador"));
                apet=(lista.getString("ape_pat_trabajador"));
                amat=(lista.getString("ape_mat_trabajador"));
                cargo=(lista.getString("cargo_trabajador"));
                
                fechanac=(lista.getString("fecha_nac_trabajador"));
                SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    fech = parseador.parse(fechanac);
                } catch (ParseException ex) {
                    Logger.getLogger(MantSepultados.class.getName()).log(Level.SEVERE, null, ex);
                }
                fechanac = String.format(format.format(fech));
                
                dir=(lista.getString("direccion"));
                fono=(lista.getString("fono"));
                correo=(lista.getString("correo_trabajador"));
                contr=(lista.getString("cod_contrato"));
                tipo=(lista.getString("tipo_contrato"));
                switch (tipo) {
                    case "PF":
                        tipo2 = "Plazo Fijo";
                        break;
                    case "PI":
                        tipo2 = "Plazo Indefinido";
                        break;
                    case "PO":
                        tipo2 = "Por Obra";
                        break;
                    case "TA":
                        tipo2 = "Trabajo Atípico";
                        break;  
                    case "TE":
                        tipo2 = "Trabajos Especiales";
                        break;    
                        
                        
                }
                
                
                
                
                
                Object[] newRow={run+"-"+dv,nom,apet,amat,cargo,fech,dir,fono,correo,contr,tipo2};
                modelolista.addRow(newRow);
            }
            if (modelolista.getRowCount()==0 ||(txt_consulta.getText().startsWith(" "))){
                msj="No se encontró lo solicitado";
                JOptionPane.showMessageDialog(null, msj, "CONSULTA SIN DATOS",JOptionPane.INFORMATION_MESSAGE);
                modelolista.setNumRows(0);
                llenarlst();
                txt_consulta.setText("");
                txt_consulta.requestFocus();
            }
        lsttrab.getRowSorter().toggleSortOrder(0);
        lsttrab.getRowSorter().toggleSortOrder(0);
        }catch (SQLException e){
            msj="consulta errónea";
            JOptionPane.showMessageDialog(null, msj, "Falla Consulta",JOptionPane.INFORMATION_MESSAGE);
        }
        
    };
     
     public void limpiar(){
        txt_consulta.setText("");
        dc_fecha.setDate(null);
        txt_telefono.setText("");
        txt_dv.setText("");
        txt_materno.setText("");
        txt_nombre.setText("");
        txt_paterno.setText("");
        txt_rut.setText("");
        txt_cargo.setText("");
        txt_correo.setText("");
        txt_direccion.setText("");
        txt_contrato.setText("");
        txt_rut.enable();
        txt_dv.enable();
        cmb_tipo.setSelectedIndex(0);
        lsttrab.clearSelection();
        
    }
     
     
      public boolean valida_mail(String inputmail) {
        Boolean valida = false;

        Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher m = p.matcher(inputmail);
        if (!m.find()) {
            valida = m.find();
            lb_err8.setText("Formato no válido, debe ser: correo@dominio.com");
            txt_correo.requestFocus();
        }
        return valida;
    }
     
      public void formatearfecha() {
        dc_fecha.setDateFormatString("dd-MM-yyyy");

    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lsttrab = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        txt_correo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txt_dv = new javax.swing.JTextField();
        txt_rut = new javax.swing.JTextField();
        txt_paterno = new javax.swing.JTextField();
        txt_nombre = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_materno = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txt_telefono = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txt_cargo = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txt_direccion = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        cmb_tipo = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        txt_consulta = new javax.swing.JTextField();
        btn_agregar = new javax.swing.JButton();
        btn_modificar = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        btn_consultar = new javax.swing.JButton();
        lb_err2 = new javax.swing.JLabel();
        lb_err1 = new javax.swing.JLabel();
        lb_err3 = new javax.swing.JLabel();
        lb_err5 = new javax.swing.JLabel();
        lb_err4 = new javax.swing.JLabel();
        lb_err6 = new javax.swing.JLabel();
        lb_err7 = new javax.swing.JLabel();
        lb_err8 = new javax.swing.JLabel();
        lbl_estado = new javax.swing.JLabel();
        txt_contrato = new javax.swing.JTextField();
        lb_err9 = new javax.swing.JLabel();
        lb_err10 = new javax.swing.JLabel();
        dc_fecha = new com.toedter.calendar.JDateChooser();
        btn_contrato = new javax.swing.JButton();

        jButton1.setText("jButton1");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 0, 51));
        jLabel20.setText("jLabel2");

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
        jLabel5.setText("Trabajadores");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Rut Trabajador");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Nombre ");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Cargo");

        lsttrab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"7358327-6", "Jenny", "Suarez", "Zabaleta", "Secretaria", "Libertad 558", "976750996", "Jenny.suarez@gmail.com", "22/11/1986", null, "198", "Indefinido"},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Rut Trabajador", "Nombre", "Apellido Parterno", "Apellido Materno", "Cargo", "Dirección", "Telefono", "Correo", "Fecha Nac", "Telefono", "Cod contrato", "Tipo contrato"
            }
        ));
        lsttrab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lsttrabMouseClicked(evt);
            }
        });
        lsttrab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lsttrabKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(lsttrab);

        txt_correo.setBackground(new java.awt.Color(255, 255, 204));
        txt_correo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_correoFocusLost(evt);
            }
        });
        txt_correo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_correoActionPerformed(evt);
            }
        });
        txt_correo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_correoKeyTyped(evt);
            }
        });

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Correo Electronico (opcional)");

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

        txt_telefono.setBackground(new java.awt.Color(255, 255, 204));
        txt_telefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_telefonoActionPerformed(evt);
            }
        });
        txt_telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_telefonoKeyTyped(evt);
            }
        });

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Telefono");

        txt_cargo.setBackground(new java.awt.Color(255, 255, 204));
        txt_cargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cargoActionPerformed(evt);
            }
        });
        txt_cargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_cargoKeyTyped(evt);
            }
        });

        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Fecha Nacimiento");

        txt_direccion.setBackground(new java.awt.Color(255, 255, 204));
        txt_direccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_direccionActionPerformed(evt);
            }
        });
        txt_direccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_direccionKeyTyped(evt);
            }
        });

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Dirección");

        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Cod contrato");

        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Tipo contrato");

        cmb_tipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccionar>", "Plazo Fijo", "Plazo Indefinido", "Por Obra", "Trabajo Atípico", "Trabajos Especiales" }));

        jButton2.setBackground(new java.awt.Color(102, 0, 0));
        jButton2.setFont(new java.awt.Font("Franklin Gothic Medium", 3, 13)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cancelar.png"))); // NOI18N
        jButton2.setText("Cerrar");
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
        btn_consultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_consultarActionPerformed(evt);
            }
        });

        lb_err2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lb_err2.setForeground(new java.awt.Color(255, 0, 51));
        lb_err2.setText("jLabel2");

        lb_err1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lb_err1.setForeground(new java.awt.Color(255, 0, 51));
        lb_err1.setText("jLabel2");

        lb_err3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lb_err3.setForeground(new java.awt.Color(255, 0, 51));
        lb_err3.setText("jLabel2");

        lb_err5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lb_err5.setForeground(new java.awt.Color(255, 0, 51));
        lb_err5.setText("jLabel2");

        lb_err4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lb_err4.setForeground(new java.awt.Color(255, 0, 51));
        lb_err4.setText("jLabel2");

        lb_err6.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lb_err6.setForeground(new java.awt.Color(255, 0, 51));
        lb_err6.setText("jLabel2");

        lb_err7.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lb_err7.setForeground(new java.awt.Color(255, 0, 51));
        lb_err7.setText("jLabel2");

        lb_err8.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lb_err8.setForeground(new java.awt.Color(255, 0, 51));
        lb_err8.setText("jLabel2");

        txt_contrato.setBackground(new java.awt.Color(255, 255, 204));
        txt_contrato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_contratoActionPerformed(evt);
            }
        });
        txt_contrato.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_contratoKeyTyped(evt);
            }
        });

        lb_err9.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lb_err9.setForeground(new java.awt.Color(255, 0, 51));
        lb_err9.setText("jLabel2");

        lb_err10.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lb_err10.setForeground(new java.awt.Color(255, 0, 51));
        lb_err10.setText("jLabel2");

        dc_fecha.setForeground(new java.awt.Color(255, 255, 204));

        btn_contrato.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/contratos.png"))); // NOI18N
        btn_contrato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_contratoActionPerformed(evt);
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
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(176, 176, 176)
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(btn_agregar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_modificar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_eliminar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txt_consulta, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_consultar)
                                        .addGap(101, 101, 101)
                                        .addComponent(btn_contrato, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jSeparator2))
                                .addGap(54, 54, 54)
                                .addComponent(lbl_estado)
                                .addGap(62, 62, 62)))))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb_err2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lb_err3)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_paterno, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 163, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 646, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb_err6)
                            .addComponent(lb_err8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lb_err7)
                                    .addComponent(dc_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(jLabel6)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txt_rut, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_dv, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8)
                    .addComponent(lb_err1)
                    .addComponent(txt_materno, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel11)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel9)
                                .addComponent(txt_cargo, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txt_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel13)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(lb_err4)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(87, 87, 87)
                                .addComponent(jLabel15))
                            .addComponent(lb_err5)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lb_err9)
                                    .addComponent(txt_contrato, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(lb_err10)
                            .addComponent(cmb_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(45, 45, 45))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txt_consulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_consultar)
                                .addComponent(btn_contrato, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(17, 17, 17)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lbl_estado)
                                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(40, 40, 40)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btn_modificar)
                                        .addComponent(btn_eliminar)
                                        .addComponent(btn_agregar)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jLabel6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_dv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_rut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lb_err1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_paterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_err2)
                    .addComponent(lb_err3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addGap(5, 5, 5)
                .addComponent(txt_materno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_err4)
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_correo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_cargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_err5)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dc_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_err6)
                    .addComponent(lb_err7))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmb_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_contrato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lb_err8)
                            .addComponent(lb_err9)
                            .addComponent(lb_err10))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(62, 62, 62))))
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

    public void insertar(String sql){
      try{
            sentencia.executeUpdate(sql);
            msj="datos guardados";
            JOptionPane.showMessageDialog(null, msj, "Datos Guardados", JOptionPane.INFORMATION_MESSAGE);
            
        }
        catch(SQLException e){
            msj="No ingresó";
            JOptionPane.showMessageDialog(null, msj, "---", JOptionPane.INFORMATION_MESSAGE);
            
        }
        modelolista.setNumRows(0);
            //lstClie.remove();
        llenarlst();
        limpiar();
    }
    
     public void actualizar (String rut, String dv, String nom, String pater, String mater, String cargo, String fechanac, String dir, String fono, String correo, int contr, String tipo ){
     String upSQL="";
        
        try{
        upSQL="UPDATE trabajadores SET nombre_trabajador='"+ nom + "',ape_pat_trabajador='" + pater + "',ape_mat_trabajador='" + mater + "',cargo_trabajador='" + cargo + "',fecha_nac_trabajador'" + fechanac + "',direccion='" + dir + "',fono='" + fono + "',correo_trabajador='" + correo + "',cod_contrato="+ contr + ",tipo_contrato='" + tipo + "' WHERE run_trabajador='"+ rut +"'";
        sentencia.executeUpdate(upSQL);
        msj="Datos actualizados";
        JOptionPane.showMessageDialog(null, msj, "Datos actualizados",JOptionPane.INFORMATION_MESSAGE);
       
        }
       catch(SQLException e){
           msj="No actualizado";
           JOptionPane.showMessageDialog(null, msj, "Datos actualizados",JOptionPane.INFORMATION_MESSAGE);
       }   
        modelolista.setNumRows(0);
            //lstClie.remove();
            llenarlst();
        limpiar();
        
    }
    
    public void eliminar (String rut, String dv, String nom, String pater, String mater, String cargo, Date fecha, String dir, String fono, String correo, String contr, String tipo){
     String upSQL="";
        /*Mensaje message=new Mensaje();
        message.windowClosed(e);*/
        
        try{
            upSQL="DELETE FROM trabajadores WHERE run_trabajador ='"+ rut +"';";
            sentencia.executeUpdate(upSQL);
            msj="Datos Eliminados";
            JOptionPane.showMessageDialog(null, msj, "ELIMINACIÓN DE DATOS",JOptionPane.INFORMATION_MESSAGE);
       
        }
       catch(SQLException e){
           msj="No eliminado";
           JOptionPane.showMessageDialog(null, msj, "ALERTA",JOptionPane.INFORMATION_MESSAGE);
       }   
        modelolista.setNumRows(0);
            //lstClie.remove();
        llenarlst();
        limpiar();
        
    }
    
    
    private void txt_correoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_correoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_correoActionPerformed

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

    private void txt_telefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_telefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_telefonoActionPerformed

    private void txt_cargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cargoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cargoActionPerformed

    private void txt_direccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_direccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_direccionActionPerformed

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
        String rut,dv,nom,apepar,apemat,cargo,dir,fono,correo,contr,tipo="",fecha2;
        int contr2;
        Date fecha;
        int index=cmb_tipo.getSelectedIndex();;
        
        switch(index){
            case 1: tipo="PF";
                break;
            case 2: tipo="PI";
                break;
            case 3: tipo="PO";
                break;
            case 4: tipo="TA";
                break;
            case 5: tipo="TE";
                break;
            default: lb_err10.setText("Debe seleccionar un tipo de contrato!");
                break;
        }
        
        rut=txt_rut.getText();
        if(txt_rut.getText().isEmpty()||txt_rut.getText().startsWith(" ")){
            lb_err1.setText("Ingrese el rut");
            txt_rut.setFocusable(rootPaneCheckingEnabled);
            return;
        }
        dv=txt_dv.getText().toUpperCase();
        nom=txt_nombre.getText().toUpperCase();
        if(txt_nombre.getText().isEmpty()||txt_nombre.getText().startsWith(" ")){
            lb_err2.setText("Ingrese el nombre");
            txt_nombre.requestFocus();
            return;
        }
        apepar=txt_paterno.getText().toUpperCase();
        if(txt_paterno.getText().isEmpty()||txt_paterno.getText().startsWith(" ")){
            lb_err3.setText("Ingrese el apellido");
            txt_paterno.requestFocus();
            return;
        }
        apemat=txt_materno.getText().toUpperCase();
        if(txt_materno.getText().isEmpty()||txt_materno.getText().startsWith(" ")){
            lb_err4.setText("Ingrese el apellido materno");
            txt_materno.requestFocus();
            return;
        }
        cargo=txt_cargo.getText().toUpperCase();
        if(txt_cargo.getText().isEmpty()||txt_cargo.getText().startsWith(" ")){
            lb_err5.setText("Ingrese el cargo");
            txt_cargo.requestFocus();
            return;
        }
        dc_fecha.setDateFormatString("yyyy-MM-dd");
        fecha2 = dc_fecha.getDateFormatString();
        fecha = dc_fecha.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat(fecha2);
        fecha2 = String.valueOf(sdf.format(fecha));
        
        
        fono=txt_telefono.getText();
        if(txt_telefono.getText().isEmpty()||txt_telefono.getText().startsWith(" ")){
            lb_err7.setText("Ingrese el telefono");
            txt_telefono.requestFocus();
            return;
        }
        
        dir=txt_direccion.getText();
        if(txt_direccion.getText().isEmpty()||txt_direccion.getText().startsWith(" ")){
            lb_err8.setText("Ingrese la direccion");
            txt_direccion.requestFocus();
            return;
        }    
        
        correo=txt_correo.getText();
        if(txt_correo.getText().isEmpty()||txt_correo.getText().startsWith(" ")){
            
            txt_correo.requestFocus();
            return;
        } 
        contr=txt_contrato.getText();
       if(txt_contrato.getText().isEmpty()||txt_contrato.getText().startsWith(" ")){
            
            txt_contrato.setFocusable(rootPaneCheckingEnabled);
           
            return;
        }
       contr2 = parseInt(contr);
    
       
       
       
        String sql="INSERT INTO trabajadores(run_trabajador,dv_trabajador,nombre_trabajador,ape_pat_trabajador,ape_mat_trabajador,cargo_trabajador,fecha_nac_trabajador,direccion,fono,correo_trabajador,cod_contrato,tipo_contrato)"
                + "VALUES('" + rut + "','" + dv + "','" + nom + "','" + apepar + "','" + apemat + "','" + cargo + "','" + fecha2 + "','" + fono + "','" + dir + "','" + correo +  "'," + contr2 + ",'" + tipo + "')";
        /*msj=sql;
           JOptionPane.showMessageDialog(null, msj, "Datos Guardados", JOptionPane.INFORMATION_MESSAGE);*/
        insertar(sql);
       
     
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void btn_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificarActionPerformed
        String rut,dv,nom,apepar,apemat, cargo, fecha2, dir, fono, correo, contr, tipo="";
        Date fecha;
        
     if(lsttrab.getSelectedRowCount()>0){
         
        rut=txt_rut.getText();
        dv=txt_dv.getText().toUpperCase();
        nom=txt_nombre.getText().toUpperCase();
        if(txt_nombre.getText().isEmpty()||txt_nombre.getText().startsWith(" ")){
            lb_err2.setText("Ingrese el nombre");
            txt_nombre.requestFocus();
            return;
        }
        apepar=txt_paterno.getText().toUpperCase();
        if(txt_paterno.getText().isEmpty()||txt_paterno.getText().startsWith(" ")){
            lb_err3.setText("Ingrese el apellido");
            txt_paterno.requestFocus();
            return;
        }
        apemat=txt_materno.getText().toUpperCase();
        if(txt_materno.getText().isEmpty()||txt_materno.getText().startsWith(" ")){
            lb_err4.setText("Ingrese el apellido materno");
            txt_materno.requestFocus();
            return;
        }
        cargo=txt_cargo.getText().toUpperCase();
        if(txt_cargo.getText().isEmpty()||txt_cargo.getText().startsWith(" ")){
            lb_err5.setText("Ingrese el cargo");
            txt_cargo.requestFocus();
            return;
        }
       
        dc_fecha.setDateFormatString("yyyy-MM-dd");
        fecha2 = dc_fecha.getDateFormatString();
        fecha = dc_fecha.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat(fecha2);
        fecha2 = String.valueOf(sdf.format(fecha));
        
        
        fono=txt_telefono.getText();
        if(txt_telefono.getText().isEmpty()||txt_telefono.getText().startsWith(" ")){
            lb_err7.setText("Ingrese el telefono");
            txt_telefono.requestFocus();
            return;
        }
        
        dir=txt_direccion.getText();
        if(txt_direccion.getText().isEmpty()||txt_direccion.getText().startsWith(" ")){
            lb_err8.setText("Ingrese la direccion");
            txt_direccion.requestFocus();
            return;
        }    
        
        correo=txt_correo.getText();
        if(txt_correo.getText().isEmpty()||txt_correo.getText().startsWith(" ")){
            
            txt_correo.requestFocus();
            return;
        } 
        contr=txt_contrato.getText();
        if(txt_contrato.getText().isEmpty()||txt_contrato.getText().startsWith(" ")){
            
            txt_contrato.setFocusable(rootPaneCheckingEnabled);
            return;
        } 
        int contr2 = parseInt(contr);
        
        int index=cmb_tipo.getSelectedIndex();
        switch(index){
            case 1: tipo="PF";
                break;
            case 2: tipo="PI";
                break;
            case 3: tipo="PO";
                break;
            case 4: tipo="TA";
                break;
            case 5: tipo="TE";
                break;
        }
        //tipo=cmb_tipo.getSelectedItem();
       
        
       limpiarlbl();
        
        actualizar(rut,dv,nom,apepar,apemat,cargo,fecha2,dir,fono,correo,contr2,tipo);
        
        formatearfecha();
        
        
     }else{
         msj="Seleccione una fila para modificar";
            JOptionPane.showMessageDialog(null, msj, "MODIFICACIÓN DE DATOS",JOptionPane.INFORMATION_MESSAGE);
     }

     
    }//GEN-LAST:event_btn_modificarActionPerformed

    private void lsttrabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lsttrabMouseClicked
       String rut,dv,nom,apepar,apemat, cargo, fecha, dir, fono, correo, contr, tipo;
         java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("dd-MM-yyyy");
    
    if(lsttrab.getSelectedRowCount()>0){
        rut=lsttrab.getValueAt(lsttrab.getSelectedRow(), 0).toString();
        rut=rut.substring(0, rut.length()-2);
        txt_rut.setText(rut);
        txt_rut.disable();
        dv=lsttrab.getValueAt(lsttrab.getSelectedRow(), 0).toString();
        dv=dv.substring(dv.length()-1, dv.length());
        txt_dv.setText(dv);
        txt_dv.disable();
        txt_nombre.setText(lsttrab.getValueAt(lsttrab.getSelectedRow(), 1).toString());
        txt_paterno.setText(lsttrab.getValueAt(lsttrab.getSelectedRow(), 2).toString());
        txt_materno.setText(lsttrab.getValueAt(lsttrab.getSelectedRow(), 3).toString());
        txt_cargo.setText(lsttrab.getValueAt(lsttrab.getSelectedRow(), 4).toString());
        fecha = lsttrab.getValueAt(lsttrab.getSelectedRow(),5).toString();
            try {
                java.util.Date fechDate = formato.parse(fecha);
                dc_fecha.setDate(fechDate);
            } catch (ParseException ex) {
                Logger.getLogger(MantSepultados.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        txt_direccion.setText(lsttrab.getValueAt(lsttrab.getSelectedRow(), 6).toString());
        txt_telefono.setText(lsttrab.getValueAt(lsttrab.getSelectedRow(), 7).toString());
        txt_correo.setText(lsttrab.getValueAt(lsttrab.getSelectedRow(), 8).toString());
        txt_contrato.setText(lsttrab.getValueAt(lsttrab.getSelectedRow(), 9).toString());
        tipo=lsttrab.getValueAt(lsttrab.getSelectedRow(), 10).toString();
            /*msj=tipo;
            JOptionPane.showMessageDialog(null, msj, "ELIMINACIÓN DE DATOS", JOptionPane.INFORMATION_MESSAGE);*/
            switch (tipo) {
                case "Plazo Fijo":
                    cmb_tipo.setSelectedItem("Plazo Fijo");
                    break;
                case "Plazo Indefinido":
                    cmb_tipo.setSelectedItem("Plazo Indefinido");
                    break;
                case "Por Obra":
                    cmb_tipo.setSelectedItem("Por Obra");
                    break;
                case "Trabajo Atípico":
                    cmb_tipo.setSelectedItem("Trabajo Atípico");
                    break;
                 case "Trabajos Especiales":
                    cmb_tipo.setSelectedItem("Trabajos Especiales");
                    break;   

            }
        limpiarlbl();
        } 
    }//GEN-LAST:event_lsttrabMouseClicked

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        String rut,dv,nom,apepar,apemat, cargo, dir, fono, correo, contr, tipo="";
        Date fecha;
        int index=cmb_tipo.getSelectedIndex();
        if(lsttrab.getSelectedRowCount()>0){
            rut=txt_rut.getText();
            dv=txt_dv.getText();
            nom=txt_nombre.getText();
            apepar=txt_paterno.getText();
            apemat=txt_materno.getText();
            cargo=txt_cargo.getText();
            fecha=dc_fecha.getDate();
            dir=txt_direccion.getText();
            fono=txt_telefono.getText();
            correo=txt_correo.getText();
            contr=txt_contrato.getText();
            switch(index){
            case 1: tipo="PF";
                break;
            case 2: tipo="PI";
                break;
            case 3: tipo="PO";
                break;
            case 4: tipo="TA";
                break;
            case 5: tipo="TE";
                break;
        }
        
        if(JOptionPane.showConfirmDialog(rootPane, "Se eliminará el registro",
                "Eliminar", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION){
        
                eliminar(rut,dv,nom,apepar,apemat, cargo, fecha, dir, fono, correo, contr, tipo);
            }
        }else{
            msj="NO SE PUEDEN ELIMINAR DATOS";
            JOptionPane.showMessageDialog(null, msj, "ELIMINACIÓN DE DATOS",JOptionPane.INFORMATION_MESSAGE);
        }
        //llenarlst();
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void btn_consultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_consultarActionPerformed
        String consu;
        consu=txt_consulta.getText();
        //if(txt_consulta.getText()>='0' && txt_consulta.getText()<='30000000' ){
            
        //}
        if(txt_consulta.getText().startsWith(" ")){
            txt_consulta.setText("");
            txt_consulta.requestFocus();
        }
        modelolista.setNumRows(0);
            //lstClie.remove();
        llenarlst_consu(consu);
                                               

   
    }//GEN-LAST:event_btn_consultarActionPerformed

    private void txt_rutKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_rutKeyTyped
        char TipoTecla=evt.getKeyChar();
        if(txt_rut.getText().length()>=8){
            evt.consume();
        }
        if(Character.isDigit(TipoTecla)){
            
        }else{
               evt.consume();
        }
        if(txt_rut.getText().startsWith("0")){
            lb_err1.setText("CERO NO ES PRIMER DIGITO!");
            txt_rut.setText("");
            evt.consume();
            txt_rut.requestFocus();
            return;
        }else{
            lb_err1.setText("");
        }
        
        lb_err1.setText("");
    }//GEN-LAST:event_txt_rutKeyTyped

    private void txt_contratoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_contratoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_contratoActionPerformed

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        modelolista.setNumRows(0);
        llenarlst();
        txt_consulta.setText("");
        dc_fecha.setDate(null);
        
        txt_dv.setText("");

        txt_materno.setText("");
        txt_nombre.setText("");
        txt_paterno.setText("");
        txt_rut.setText("");
        txt_cargo.setText("");
        txt_contrato.setText("");
        txt_correo.setText("");
        txt_direccion.setText("");
        txt_telefono.setText("");
        cmb_tipo.setSelectedIndex(0);
        txt_rut.enable();
        txt_dv.enable();
        lsttrab.clearSelection();
        limpiarlbl();
        
    }//GEN-LAST:event_jPanel1MouseClicked

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

    private void txt_correoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_correoFocusLost
       if (txt_correo.getText().equals("")) {
            lb_err8.setText("");
        } else {

            valida_mail(txt_correo.getText());
        }
    }//GEN-LAST:event_txt_correoFocusLost

    private void txt_consultaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_consultaKeyPressed
         if (evt.VK_ENTER==evt.getKeyCode()){ 
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

    private void lsttrabKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lsttrabKeyReleased
         String rut,dv,nom,apepar,apemat, cargo, fecha, dir, fono, correo, contr, tipo;
         java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("dd-MM-yyyy");
    
    if(lsttrab.getSelectedRowCount()>0){
        
     if (evt.VK_DOWN == evt.getKeyCode() || evt.VK_UP == evt.getKeyCode()){
        rut=lsttrab.getValueAt(lsttrab.getSelectedRow(), 0).toString();
        rut=rut.substring(0, rut.length()-2);
        txt_rut.setText(rut);
        txt_rut.disable();
        dv=lsttrab.getValueAt(lsttrab.getSelectedRow(), 0).toString();
        dv=dv.substring(dv.length()-1, dv.length());
        txt_dv.setText(dv);
        //txt_dv.disable();
        txt_nombre.setText(lsttrab.getValueAt(lsttrab.getSelectedRow(), 1).toString());
        txt_paterno.setText(lsttrab.getValueAt(lsttrab.getSelectedRow(), 2).toString());
        txt_materno.setText(lsttrab.getValueAt(lsttrab.getSelectedRow(), 3).toString());
        txt_cargo.setText(lsttrab.getValueAt(lsttrab.getSelectedRow(), 4).toString());
        fecha = lsttrab.getValueAt(lsttrab.getSelectedRow(),5).toString();
            try {
                java.util.Date fechDate = formato.parse(fecha);
                dc_fecha.setDate(fechDate);
            } catch (ParseException ex) {
                Logger.getLogger(MantSepultados.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        txt_direccion.setText(lsttrab.getValueAt(lsttrab.getSelectedRow(), 6).toString());
        txt_telefono.setText(lsttrab.getValueAt(lsttrab.getSelectedRow(), 7).toString());
        txt_correo.setText(lsttrab.getValueAt(lsttrab.getSelectedRow(), 8).toString());
        txt_contrato.setText(lsttrab.getValueAt(lsttrab.getSelectedRow(), 9).toString());
        tipo=lsttrab.getValueAt(lsttrab.getSelectedRow(), 10).toString();
            /*msj=tipo;
            JOptionPane.showMessageDialog(null, msj, "ELIMINACIÓN DE DATOS", JOptionPane.INFORMATION_MESSAGE);*/
            switch (tipo) {
                case "Plazo Fijo":
                    cmb_tipo.setSelectedItem("Plazo Fijo");
                    break;
                case "Plazo Indefinido":
                    cmb_tipo.setSelectedItem("Plazo Indefinido");
                    break;
                case "Por Obra":
                    cmb_tipo.setSelectedItem("Por Obra");
                    break;
                case "Trabajo Atípico":
                    cmb_tipo.setSelectedItem("Trabajo Atípico");
                    break;
                 case "Trabajos Especiales":
                    cmb_tipo.setSelectedItem("Trabajos Especiales");
                    break;   

            }
        limpiarlbl();
        } 
     if (evt.VK_DELETE == evt.getKeyCode()){
         btn_eliminar.doClick();
     }
      }
    }//GEN-LAST:event_lsttrabKeyReleased

    private void txt_consultaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_consultaKeyReleased
        if ((evt.VK_BACK_SPACE == evt.getKeyCode() || evt.VK_DELETE == evt.getKeyCode()) && (txt_consulta.getText().isEmpty())) {
            modelolista.setNumRows(0);
            llenarlst();
        }
    }//GEN-LAST:event_txt_consultaKeyReleased

    private void txt_consultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_consultaMouseClicked
      
        limpiarlbl();
        txt_consulta.setText("");
        dc_fecha.setDate(null);
        
        txt_dv.setText("");

        txt_materno.setText("");
        txt_nombre.setText("");
        txt_paterno.setText("");
        txt_rut.setText("");
        txt_cargo.setText("");
        txt_contrato.setText("");
        txt_correo.setText("");
        txt_direccion.setText("");
        txt_telefono.setText("");
        cmb_tipo.setSelectedIndex(0);
        txt_rut.enable();
        //txt_dv.enable();
        lsttrab.clearSelection();
        limpiarlbl();
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
        if (Character.isDigit(TipoTecla) || !Character.isLetter(TipoTecla) && !(TipoTecla == evt.VK_SPACE)
                && !(TipoTecla == evt.VK_BACK_SPACE) && !(TipoTecla == evt.VK_MINUS) && !(evt.getKeyChar() == '\'')) {
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

    private void txt_direccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_direccionKeyTyped
       limpiarlbl();
        if (txt_direccion.getText().startsWith(" ")) {
            txt_direccion.setText("");
            txt_direccion.requestFocus();
        }
        if (txt_direccion.getText().length() >= 45) {
            evt.consume();
        } 
    }//GEN-LAST:event_txt_direccionKeyTyped

    private void txt_telefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telefonoKeyTyped
        char TipoTecla = evt.getKeyChar();
        if (txt_telefono.getText().startsWith(" ")) {
            txt_telefono.setText("");
            txt_telefono.requestFocus();
        }
        if (txt_telefono.getText().length() >= 12) {
            evt.consume();
        }
        limpiarlbl();
        if (Character.isDigit(TipoTecla)) {

        } else {
            evt.consume();
        }
    }//GEN-LAST:event_txt_telefonoKeyTyped

    private void txt_correoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_correoKeyTyped
       if (txt_correo.getText().startsWith(" ")) {
            txt_correo.setText("");
            txt_correo.requestFocus();
        }
        limpiarlbl();
        if (txt_correo.getText().length() >= 45) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_correoKeyTyped

    private void txt_rutKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_rutKeyReleased
       if ((evt.VK_BACK_SPACE == evt.getKeyCode() || evt.VK_DELETE == evt.getKeyCode()) && (txt_consulta.getText().isEmpty())) {
            txt_dv.setText("");
       }
    }//GEN-LAST:event_txt_rutKeyReleased

    private void txt_cargoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cargoKeyTyped
        if (txt_cargo.getText().length() >= 45) {
            evt.consume();
        }
        limpiarlbl();
        if (txt_cargo.getText().startsWith(" ")) {
            txt_cargo.setText("");
            txt_cargo.requestFocus();
        }
        char TipoTecla = evt.getKeyChar();
        if (Character.isDigit(TipoTecla) || !Character.isLetter(TipoTecla) && !(TipoTecla == evt.VK_SPACE) && !(TipoTecla == evt.VK_BACK_SPACE)) {
            evt.consume();
        } else {

        }
    }//GEN-LAST:event_txt_cargoKeyTyped

    private void txt_contratoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_contratoKeyTyped
        char TipoTecla = evt.getKeyChar();
        if (txt_contrato.getText().startsWith(" ")) {
            txt_contrato.setText("");
            txt_contrato.requestFocus();
        }
        if (txt_contrato.getText().length() >= 5) {
            evt.consume();
        }
        limpiarlbl();
        if (Character.isDigit(TipoTecla)) {

        } else {
            evt.consume();
        }
    }//GEN-LAST:event_txt_contratoKeyTyped

    private void btn_contratoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_contratoActionPerformed
        MantAnexosContr v1 = new MantAnexosContr();
        v1.setVisible(true);
        this.setVisible(false);
        
    }//GEN-LAST:event_btn_contratoActionPerformed

    
    
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
            java.util.logging.Logger.getLogger(MantTrabajadores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MantTrabajadores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MantTrabajadores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MantTrabajadores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MantTrabajadores().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_consultar;
    private javax.swing.JButton btn_contrato;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_modificar;
    private javax.swing.JComboBox<String> cmb_tipo;
    private com.toedter.calendar.JDateChooser dc_fecha;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lb_err1;
    private javax.swing.JLabel lb_err10;
    private javax.swing.JLabel lb_err2;
    private javax.swing.JLabel lb_err3;
    private javax.swing.JLabel lb_err4;
    private javax.swing.JLabel lb_err5;
    private javax.swing.JLabel lb_err6;
    private javax.swing.JLabel lb_err7;
    private javax.swing.JLabel lb_err8;
    private javax.swing.JLabel lb_err9;
    private javax.swing.JLabel lbl_estado;
    private javax.swing.JTable lsttrab;
    private javax.swing.JTextField txt_cargo;
    private javax.swing.JTextField txt_consulta;
    private javax.swing.JTextField txt_contrato;
    private javax.swing.JTextField txt_correo;
    private javax.swing.JTextField txt_direccion;
    private javax.swing.JTextField txt_dv;
    private javax.swing.JTextField txt_materno;
    private javax.swing.JTextField txt_nombre;
    private javax.swing.JTextField txt_paterno;
    private javax.swing.JTextField txt_rut;
    private javax.swing.JTextField txt_telefono;
    // End of variables declaration//GEN-END:variables
}
