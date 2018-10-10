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



public class MantClientes extends javax.swing.JFrame {
    private Statement sentencia;
    private Connection conexion;
    private String nomBD="cementerio";
    private String usuario ="root";
    private String password ="";
    private String msj;
    Object[][] data = {};
    String[] columnNames={"Run","Nombre","Apellido Paterno","Apellido Materno","Dirección","Teléfono","Correo"};
    DefaultTableModel modelolista=new DefaultTableModel(data,columnNames){
        public boolean isCellEditable(int rowIndex, int vColIndex) {
            return false;
        }
    };
  
    public MantClientes(){
        initComponents();
        this.setLocationRelativeTo(null);
        conectar();
        lbl_estado.setVisible(false);
        lstClie.setModel(modelolista);
        llenarlst();  
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

    
    public void llenarlst(){
       String run,dv,nom,apet,amat,dir,fono,mail;
       lstClie.setPreferredScrollableViewportSize(new Dimension(500,70));
        RowSorter<TableModel> sorter=new TableRowSorter<TableModel>(modelolista);
        lstClie.setRowSorter(sorter);
        try{
            sentencia=(Statement)conexion.createStatement();//permite ejecutar sentencias SQL
            ResultSet lista=sentencia.executeQuery("SELECT * FROM cliente");
            while(lista.next()){
                nom=(lista.getString("nom_cliente"));//nombre del campo de la BD
                run=(lista.getString("run_cliente"));
                dv=(lista.getString("dv_cliente"));
                apet=(lista.getString("ape_pat_cliente"));
                amat=(lista.getString("ape_mat_cliente"));
                dir=(lista.getString("dir_cliente"));
                fono=(lista.getString("fono_cliente"));
                mail=(lista.getString("correo_cliente"));
                Object[] newRow={run+"-"+dv,nom,apet,amat,dir,fono,mail};
                modelolista.addRow(newRow);
            }
        lstClie.getRowSorter().toggleSortOrder(0);
        }catch (SQLException e){
            msj="no tiene elementos";
        }
        
    };
    public void llenarlst_consu(String consu){
       String run,dv,nom,apet,amat,dir,fono,mail;
       lstClie.setPreferredScrollableViewportSize(new Dimension(500,70));
        RowSorter<TableModel> sorter=new TableRowSorter<TableModel>(modelolista);
        lstClie.setRowSorter(sorter);
        try{
            sentencia=(Statement)conexion.createStatement();//permite ejecutar sentencias SQL
            ResultSet lista=sentencia.executeQuery("SELECT * FROM cliente WHERE nom_cliente like '%"+consu+"%'or run_cliente like '%"+consu+"%'"
                    + "or ape_pat_cliente like '%"+consu+"%' or ape_mat_cliente like '%"+consu+"%' or dir_cliente like '%"+consu+"%'");
            while(lista.next()){
                nom=(lista.getString("nom_cliente"));//nombre del campo de la BD
                run=(lista.getString("run_cliente"));
                dv=(lista.getString("dv_cliente"));
                apet=(lista.getString("ape_pat_cliente"));
                amat=(lista.getString("ape_mat_cliente"));
                dir=(lista.getString("dir_cliente"));
                fono=(lista.getString("fono_cliente"));
                mail=(lista.getString("correo_cliente"));
                Object[] newRow={run+"-"+dv,nom,apet,amat,dir,fono,mail};
                modelolista.addRow(newRow);
            }
            if (lista.getRow()==0){
                msj="No se encontró lo solicitado";
                JOptionPane.showMessageDialog(null, msj, "CONSULTA SIN DATOS",JOptionPane.INFORMATION_MESSAGE);
                modelolista.setNumRows(0);
                llenarlst();
                txt_consulta.setText("");
                txt_consulta.requestFocus();
            }
        lstClie.getRowSorter().toggleSortOrder(0);
        }catch (SQLException e){
            msj="consulta errónea";
            JOptionPane.showMessageDialog(null, msj, "Falla Consulta",JOptionPane.INFORMATION_MESSAGE);
        }
        
    };
    
    public void limpiar(){
        txt_consulta.setText("");
        txt_correo.setText("");
        txt_direccion.setText("");
        txt_dv.setText("");
        txt_materno.setText("");
        txt_nombre.setText("");
        txt_paterno.setText("");
        txt_rut.setText("");
        txt_telefono.setText("");
        txt_rut.enable();
        txt_dv.enable();
        lstClie.clearSelection();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        main = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        ScrollClie = new javax.swing.JScrollPane();
        lstClie = new javax.swing.JTable();
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
        txt_direccion = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        btn_agregar = new javax.swing.JButton();
        btn_modificar = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        btn_consultar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lbl_estado = new javax.swing.JLabel();
        txt_consulta = new javax.swing.JTextField();
        lb_err1 = new javax.swing.JLabel();
        lb_err2 = new javax.swing.JLabel();
        lb_err3 = new javax.swing.JLabel();
        lb_err4 = new javax.swing.JLabel();
        lb_err5 = new javax.swing.JLabel();
        lb_err6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        main.setBackground(new java.awt.Color(51, 51, 51));
        main.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mainMouseClicked(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cruzmini.png"))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Franklin Gothic Medium", 3, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Clientes");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Rut cliente");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Nombre ");

        lstClie.setAutoCreateRowSorter(true);
        lstClie.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"16658427-8", "Bastian", "Neira", "Moreno", "España 777", "976583420", "bastian.neira@gmail.com"},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Rut cliente", "Nombre", "Apellido Parterno", "Apellido Materno", "Dirección", "Telefono", "Correo"
            }
        ));
        lstClie.setCellSelectionEnabled(true);
        lstClie.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstClieMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lstClieMouseEntered(evt);
            }
        });
        lstClie.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                lstCliePropertyChange(evt);
            }
        });
        lstClie.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lstClieKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lstClieKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lstClieKeyTyped(evt);
            }
        });
        ScrollClie.setViewportView(lstClie);

        txt_correo.setBackground(new java.awt.Color(255, 255, 204));
        txt_correo.setNextFocusableComponent(txt_telefono);
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
        txt_dv.setEnabled(false);
        txt_dv.setFocusable(false);
        txt_dv.setNextFocusableComponent(txt_nombre);
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
        txt_rut.setNextFocusableComponent(txt_dv);
        txt_rut.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_rutFocusLost(evt);
            }
        });
        txt_rut.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txt_rutInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
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
        txt_paterno.setNextFocusableComponent(txt_materno);
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
        txt_nombre.setNextFocusableComponent(txt_paterno);
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
        txt_materno.setNextFocusableComponent(txt_direccion);
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
        txt_telefono.setNextFocusableComponent(btn_agregar);
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

        txt_direccion.setBackground(new java.awt.Color(255, 255, 204));
        txt_direccion.setNextFocusableComponent(txt_correo);
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

        jButton4.setBackground(new java.awt.Color(102, 0, 0));
        jButton4.setFont(new java.awt.Font("Franklin Gothic Medium", 3, 13)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cancelar.png"))); // NOI18N
        jButton4.setText("Cancelar");
        jButton4.setNextFocusableComponent(txt_rut);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
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
        btn_eliminar.setNextFocusableComponent(txt_consulta);
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

        txt_consulta.setBackground(new java.awt.Color(255, 255, 204));
        txt_consulta.setToolTipText("");
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

        lb_err1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lb_err1.setForeground(new java.awt.Color(255, 0, 51));

        lb_err2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lb_err2.setForeground(new java.awt.Color(255, 0, 51));

        lb_err3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lb_err3.setForeground(new java.awt.Color(255, 0, 51));

        lb_err4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lb_err4.setForeground(new java.awt.Color(255, 0, 51));

        lb_err5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lb_err5.setForeground(new java.awt.Color(255, 0, 51));

        lb_err6.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lb_err6.setForeground(new java.awt.Color(255, 0, 51));

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
                                                .addGap(176, 176, 176)
                                                .addComponent(jLabel5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lbl_estado)
                                                .addGap(67, 67, 67))))
                                    .addGroup(mainLayout.createSequentialGroup()
                                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(mainLayout.createSequentialGroup()
                                                .addGap(68, 68, 68)
                                                .addComponent(btn_agregar)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btn_modificar)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btn_eliminar)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainLayout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(txt_consulta, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(9, 9, 9)))
                                        .addComponent(btn_consultar))))))
                    .addGroup(mainLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainLayout.createSequentialGroup()
                                .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(mainLayout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton4))
                                    .addGroup(mainLayout.createSequentialGroup()
                                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(mainLayout.createSequentialGroup()
                                                .addComponent(txt_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(lb_err6)))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(61, 61, 61))
                            .addGroup(mainLayout.createSequentialGroup()
                                .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(mainLayout.createSequentialGroup()
                                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(mainLayout.createSequentialGroup()
                                                .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lb_err4)
                                                    .addComponent(jLabel16))
                                                .addGap(81, 81, 81))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainLayout.createSequentialGroup()
                                                .addComponent(txt_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel11)))
                                    .addComponent(lb_err5))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(mainLayout.createSequentialGroup()
                                .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addGroup(mainLayout.createSequentialGroup()
                                        .addComponent(txt_rut, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_dv, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txt_materno, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lb_err1)
                                    .addComponent(jLabel13)
                                    .addGroup(mainLayout.createSequentialGroup()
                                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel8)
                                            .addComponent(lb_err2))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lb_err3)
                                            .addComponent(txt_paterno, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel10))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                                .addComponent(ScrollClie, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)))))
                .addContainerGap())
        );
        mainLayout.setVerticalGroup(
            mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(mainLayout.createSequentialGroup()
                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(lbl_estado))
                        .addGap(23, 23, 23)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btn_agregar)
                                    .addComponent(btn_modificar)
                                    .addComponent(btn_eliminar))
                                .addGap(6, 6, 6)
                                .addComponent(txt_consulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(mainLayout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(btn_consultar)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainLayout.createSequentialGroup()
                        .addComponent(ScrollClie, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(30, 30, 30))
                    .addGroup(mainLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_dv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_rut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lb_err1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_paterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(mainLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_err2)
                            .addComponent(lb_err3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_materno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)))
                .addComponent(lb_err4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_correo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainLayout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addGap(39, 39, 39)
                        .addComponent(jLabel2))
                    .addGroup(mainLayout.createSequentialGroup()
                        .addComponent(lb_err5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb_err6))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(main, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        
        //modelolista.removeTableModelListener(lstClie);
    }

    public void actualizar (String rut, String dv, String nom, String pater, String mater, String dir, String fono, String correo){
     String upSQL="";
        
        try{
        upSQL="UPDATE cliente SET nom_cliente='"+ nom + "',ape_pat_cliente='" + pater + "',ape_mat_cliente='" + mater + "',dir_cliente='" + dir + "',fono_cliente='" + fono + "',correo_cliente='" + correo + "' WHERE run_cliente='"+ rut +"'";
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

    public void eliminar (String rut, String dv, String nom, String pater, String mater, String dir, String fono, String correo){
     String upSQL="";
        /*Mensaje message=new Mensaje();
        message.windowClosed(e);*/
        
        try{
            upSQL="DELETE FROM cliente WHERE run_cliente='"+ rut +"';";
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

    private void txt_direccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_direccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_direccionActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
           System.exit(0);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
       String rut,dv,nom,apepar,apemat,direccion,fono,correo;
        rut=txt_rut.getText();
        if(txt_rut.getText().isEmpty()){
            lb_err1.setText("OIGA, PONGA EL RUT!");
            txt_rut.setFocusable(rootPaneCheckingEnabled);
            return;
        }
        dv=txt_dv.getText().toUpperCase();
        nom=txt_nombre.getText().toUpperCase();
        if(txt_nombre.getText().isEmpty()){
            lb_err2.setText("OIGA, PONGA EL NOMBRE!");
            txt_nombre.requestFocus();
            return;
        }
        apepar=txt_paterno.getText().toUpperCase();
        if(txt_paterno.getText().isEmpty()){
            lb_err3.setText("OIGA, PONGA EL APELLIDO!");
            txt_paterno.requestFocus();
            return;
        }
        apemat=txt_materno.getText().toUpperCase();
        if(txt_materno.getText().isEmpty()){
            lb_err4.setText("OIGA, PONGA EL APELLIDO DE LA MAAAAMI!");
            txt_materno.requestFocus();
            return;
        }
        direccion=txt_direccion.getText().toUpperCase();
        if(txt_direccion.getText().isEmpty()){
            lb_err5.setText("OIGA, Y LA DIRECCIÓN?");
            txt_direccion.requestFocus();
            return;
        }
        fono=txt_telefono.getText();
        if(txt_telefono.getText().isEmpty()){
            lb_err6.setText("DEME EL NÚMERO PAL WASÁ!");
            txt_telefono.requestFocus();
            return;
        }
        correo=txt_correo.getText().toUpperCase();
        
        String sql="INSERT INTO CLIENTE(run_cliente,dv_cliente,nom_cliente,ape_pat_cliente,ape_mat_cliente,dir_cliente,fono_cliente,correo_cliente) "
                + "VALUES('" + rut + "','" + dv + "','" + nom + "','" + apepar + "','" + apemat + "','" + direccion + "','" + fono + "','" + correo + "')";
        insertar(sql);           
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void btn_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificarActionPerformed
        String rut,dv,nom,apepar,apemat,direccion,fono,correo;
        
        rut=txt_rut.getText();
        dv=txt_dv.getText();
        nom=txt_nombre.getText();
        apepar=txt_paterno.getText();
        apemat=txt_materno.getText();
        direccion=txt_direccion.getText();
        fono=txt_telefono.getText();
        correo=txt_correo.getText();
        
        actualizar(rut,dv,nom,apepar,apemat,direccion,fono,correo);
    }//GEN-LAST:event_btn_modificarActionPerformed

    private void txt_consultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_consultaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_consultaActionPerformed

    private void txt_rutInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txt_rutInputMethodTextChanged
        // TODO add your handling code here:        
                
    }//GEN-LAST:event_txt_rutInputMethodTextChanged

    private void lstClieMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstClieMouseClicked
                // TODO add your handling code here:
    String rut,dv,nom,apepar,apemat,direccion,fono,correo;
    
    if(lstClie.getSelectedRowCount()>0){
        rut=lstClie.getValueAt(lstClie.getSelectedRow(), 0).toString();
        rut=rut.substring(0, rut.length()-2);
        txt_rut.setText(rut);
        txt_rut.disable();
        dv=lstClie.getValueAt(lstClie.getSelectedRow(), 0).toString();
        dv=dv.substring(dv.length()-1, dv.length());
        txt_dv.setText(dv);
        txt_dv.disable();
        txt_nombre.setText(lstClie.getValueAt(lstClie.getSelectedRow(), 1).toString());
        txt_paterno.setText(lstClie.getValueAt(lstClie.getSelectedRow(), 2).toString());
        txt_materno.setText(lstClie.getValueAt(lstClie.getSelectedRow(), 3).toString());
        txt_direccion.setText(lstClie.getValueAt(lstClie.getSelectedRow(), 4).toString());
        txt_telefono.setText(lstClie.getValueAt(lstClie.getSelectedRow(), 5).toString());
        txt_correo.setText(lstClie.getValueAt(lstClie.getSelectedRow(), 6).toString());
        } 
    }//GEN-LAST:event_lstClieMouseClicked

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
    String rut,dv,nom,apepar,apemat,direccion,fono,correo;
        
        rut=txt_rut.getText();
        dv=txt_dv.getText();
        nom=txt_nombre.getText();
        apepar=txt_paterno.getText();
        apemat=txt_materno.getText();
        direccion=txt_direccion.getText();
        fono=txt_telefono.getText();
        correo=txt_correo.getText();
        
        if(JOptionPane.showConfirmDialog(rootPane, "Se eliminará el registro",
                "Eliminar", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION){
        
                eliminar(rut,dv,nom,apepar,apemat,direccion,fono,correo);
    }
   
                // TODO add your handling code here:
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void txt_rutKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_rutKeyTyped
            // TODO add your handling code here:
        char TipoTecla=evt.getKeyChar();
        if(txt_rut.getText().length()>=8){
            evt.consume();
        }
        if(Character.isDigit(TipoTecla)){
            
        }else{
               evt.consume();
        }
        
        lb_err1.setText("");
    }//GEN-LAST:event_txt_rutKeyTyped

    private void btn_consultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_consultarActionPerformed
        // TODO add your handling code here:
        String consu;
        consu=txt_consulta.getText();
        //if(txt_consulta.getText()>='0' && txt_consulta.getText()<='30000000' ){
            
        //}
        modelolista.setNumRows(0);
            //lstClie.remove();
        llenarlst_consu(consu);
                
                
    }//GEN-LAST:event_btn_consultarActionPerformed

    private void txt_consultaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_consultaKeyPressed
            // TODO add your handling code here:
    if (evt.VK_ENTER==evt.getKeyCode()){ 
        btn_consultar.doClick();
    }
    }//GEN-LAST:event_txt_consultaKeyPressed

    private void txt_consultaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_consultaKeyTyped
        char TipoTecla=evt.getKeyChar();
        if(Character.isDigit(TipoTecla)){
            if(txt_consulta.getText().length()>=8){
                evt.consume();
            }
        }else{
               //evt.consume();
        }        
        
      
    }//GEN-LAST:event_txt_consultaKeyTyped

    private void mainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainMouseClicked
         // TODO add your handling code here:
        modelolista.setNumRows(0);
        llenarlst();
        txt_consulta.setText("");
        txt_correo.setText("");
        txt_direccion.setText("");
        txt_dv.setText("");
        txt_dv.disable();
        txt_materno.setText("");
        txt_nombre.setText("");
        txt_paterno.setText("");
        txt_rut.setText("");
        txt_telefono.setText("");
        txt_rut.enable();
        //txt_dv.enable();
        lstClie.clearSelection();
        
    }//GEN-LAST:event_mainMouseClicked

    private void lstCliePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_lstCliePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_lstCliePropertyChange

    private void lstClieMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstClieMouseEntered
        // TODO add your handling code here:
        
    }//GEN-LAST:event_lstClieMouseEntered

    private void lstClieKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lstClieKeyPressed
        // TODO add your handling code here:
        

    }//GEN-LAST:event_lstClieKeyPressed

    private void lstClieKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lstClieKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_lstClieKeyTyped

    private void lstClieKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lstClieKeyReleased
        String rut,dv,nom,apepar,apemat,direccion,fono,correo;
        if(lstClie.getSelectedRowCount()>0){
            
            if (evt.VK_DOWN==evt.getKeyCode()||evt.VK_UP==evt.getKeyCode()){    
        
            rut=lstClie.getValueAt(lstClie.getSelectedRow(), 0).toString();
            rut=rut.substring(0, rut.length()-2);
            txt_rut.setText(rut);
            txt_rut.disable();
            dv=lstClie.getValueAt(lstClie.getSelectedRow(), 0).toString();
            dv=dv.substring(dv.length()-1, dv.length());
            txt_dv.setText(dv);
            txt_dv.disable();
            txt_nombre.setText(lstClie.getValueAt(lstClie.getSelectedRow(), 1).toString());
            txt_paterno.setText(lstClie.getValueAt(lstClie.getSelectedRow(), 2).toString());
            txt_materno.setText(lstClie.getValueAt(lstClie.getSelectedRow(), 3).toString());
            txt_direccion.setText(lstClie.getValueAt(lstClie.getSelectedRow(), 4).toString());
            txt_telefono.setText(lstClie.getValueAt(lstClie.getSelectedRow(), 5).toString());
            txt_correo.setText(lstClie.getValueAt(lstClie.getSelectedRow(), 6).toString());
        } 
        if (evt.VK_DELETE==evt.getKeyCode()){
            btn_eliminar.doClick();
        }
    }
    //JOptionPane.showMessageDialog(null, evt.getKeyCode(), "ALERTA",JOptionPane.INFORMATION_MESSAGE);
    // TODO add your handling code here:
    }//GEN-LAST:event_lstClieKeyReleased

    private void txt_consultaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_consultaKeyReleased
              // TODO add your handling code here:
    if ((evt.VK_BACK_SPACE==evt.getKeyCode()||evt.VK_DELETE==evt.getKeyCode())&&(txt_consulta.getText().isEmpty())){ 
        modelolista.setNumRows(0);
        llenarlst();
    }
    }//GEN-LAST:event_txt_consultaKeyReleased

    private void txt_consultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_consultaMouseClicked
        // TODO add your handling code here:
        //txt_consulta.setText("");
        txt_correo.setText("");
        txt_direccion.setText("");
        txt_dv.setText("");
        txt_materno.setText("");
        txt_nombre.setText("");
        txt_paterno.setText("");
        txt_rut.setText("");
        txt_telefono.setText("");
        txt_rut.enable();
        txt_dv.enable();
        lstClie.clearSelection();
    }//GEN-LAST:event_txt_consultaMouseClicked

    private void txt_dvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dvKeyTyped
        // TODO add your handling code here:
        char TipoTecla=evt.getKeyChar();
        if(txt_dv.getText().length()>=1){
            evt.consume();
        }
        if(Character.isDigit(TipoTecla)||evt.getKeyChar()=='k'||evt.getKeyChar()=='K'){
            
        }else{
               evt.consume();
        }
    }//GEN-LAST:event_txt_dvKeyTyped

    private void txt_nombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombreKeyTyped
        char TipoTecla=evt.getKeyChar();
        if(txt_nombre.getText().length()>=45){
            evt.consume();
        }

        if(Character.isDigit(TipoTecla)||!Character.isLetter(TipoTecla) &&!(TipoTecla == evt.VK_SPACE)&&!(TipoTecla==evt.VK_BACK_SPACE)){
               evt.consume();
        }else{
            
        }
    }//GEN-LAST:event_txt_nombreKeyTyped

    private void txt_paternoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_paternoKeyTyped
        char TipoTecla=evt.getKeyChar();
        if(txt_paterno.getText().length()>=45){
            evt.consume();
        }
        if(Character.isDigit(TipoTecla)||!Character.isLetter(TipoTecla) &&!(TipoTecla == evt.VK_SPACE)&&!(TipoTecla==evt.VK_BACK_SPACE)){
               evt.consume();
        }else{
            
        }
    }//GEN-LAST:event_txt_paternoKeyTyped

    private void txt_maternoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_maternoKeyTyped
        if(txt_materno.getText().length()>=45){
            evt.consume();
        }
        char TipoTecla=evt.getKeyChar();
        if(Character.isDigit(TipoTecla)||!Character.isLetter(TipoTecla) &&!(TipoTecla == evt.VK_SPACE)&&!(TipoTecla==evt.VK_BACK_SPACE)){
               evt.consume();
        }else{
            
        }
    }//GEN-LAST:event_txt_maternoKeyTyped

    private void txt_direccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_direccionKeyTyped
        if(txt_direccion.getText().length()>=45){
            evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txt_direccionKeyTyped

    private void txt_telefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telefonoKeyTyped
        char TipoTecla=evt.getKeyChar();
        if(txt_telefono.getText().length()>=12){
            evt.consume();
        }
        if(Character.isDigit(TipoTecla)){
            
        }else{
               evt.consume();
        }
    }//GEN-LAST:event_txt_telefonoKeyTyped

    private void txt_correoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_correoKeyTyped
        if(txt_correo.getText().length()>=100){
            evt.consume();
        }
    }//GEN-LAST:event_txt_correoKeyTyped

    private void txt_rutFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_rutFocusLost
        // TODO add your handling code here:
         String codigo;
        int multiplo=2;
        int cont=0;
        for (int x=0;x<txt_rut.getText().length();x++){
                cont=cont+(Integer.parseInt(txt_rut.getText().substring(txt_rut.getText().length()-x-1,txt_rut.getText().length()-x))*multiplo);
                multiplo++;
                if (multiplo==8){
                    multiplo=2;
                }
            }
            cont=11-(cont%11);
            if(cont<=9){
                codigo=""+cont;
            }else if (cont==11){
                codigo="0";
            }else{
                codigo="K";
            }
            
            if(codigo!=null){
                txt_dv.setText(codigo);
            }
            
        
    }//GEN-LAST:event_txt_rutFocusLost

    private void txt_rutKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_rutKeyReleased
        // TODO add your handling code here:
    if ((evt.VK_BACK_SPACE==evt.getKeyCode()||evt.VK_DELETE==evt.getKeyCode())&&(txt_consulta.getText().isEmpty())){ 
        txt_dv.setText("");
    }
    }//GEN-LAST:event_txt_rutKeyReleased
    
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
            java.util.logging.Logger.getLogger(MantClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MantClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MantClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MantClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MantClientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollClie;
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_consultar;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_modificar;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lb_err1;
    private javax.swing.JLabel lb_err2;
    private javax.swing.JLabel lb_err3;
    private javax.swing.JLabel lb_err4;
    private javax.swing.JLabel lb_err5;
    private javax.swing.JLabel lb_err6;
    private javax.swing.JLabel lbl_estado;
    private javax.swing.JTable lstClie;
    private javax.swing.JPanel main;
    private javax.swing.JTextField txt_consulta;
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
