package Menúes;

import Mantenedores.MantAranceles;
import Mantenedores.MantClientes;
import Mantenedores.MantFacturas;
import Mantenedores.MantGastos;
import Mantenedores.MantInventario;
import Mantenedores.MantPagosAbonos;
import Mantenedores.MantProductos;
import Mantenedores.MantSepultados;
import Mantenedores.MantSepulturas;
import Mantenedores.MantTrabajadores;
import Mantenedores.MantUsuarios;
import Mantenedores.MantVentas;



public class MenuPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form MenuPrincipal
     */
    public MenuPrincipal() {
        initComponents();
        transparenciaButton();
        this.setLocationRelativeTo(null);
    }
 public void transparenciaButton(){
        btn_user.setOpaque(false);
        btn_user.setContentAreaFilled(false);
        btn_user.setBorderPainted(false);
        btn_cliente.setOpaque(false);
        btn_cliente.setContentAreaFilled(false);
        btn_cliente.setBorderPainted(false);
        btn_arancel.setOpaque(false);
        btn_arancel.setContentAreaFilled(false);
        btn_arancel.setBorderPainted(false);
        btn_sepulturas.setOpaque(false);
        btn_sepulturas.setContentAreaFilled(false);
        btn_sepulturas.setBorderPainted(false);
        btn_sepultados.setOpaque(false);
        btn_sepultados.setContentAreaFilled(false);
        btn_sepultados.setBorderPainted(false);
        btn_venta.setOpaque(false);
        btn_venta.setContentAreaFilled(false);
        btn_venta.setBorderPainted(false);
        btn_pago.setOpaque(false);
        btn_pago.setContentAreaFilled(false);
        btn_pago.setBorderPainted(false);
        btn_gasto.setOpaque(false);
        btn_gasto.setContentAreaFilled(false);
        btn_gasto.setBorderPainted(false);
        btn_trabajadores.setOpaque(false);
        btn_trabajadores.setContentAreaFilled(false);
        btn_trabajadores.setBorderPainted(false);
        btn_factura.setOpaque(false);
        btn_factura.setContentAreaFilled(false);
        btn_factura.setBorderPainted(false);
        btn_productos.setOpaque(false);
        btn_productos.setContentAreaFilled(false);
        btn_productos.setBorderPainted(false);
        btn_inventario.setOpaque(false);
        btn_inventario.setContentAreaFilled(false);
        btn_inventario.setBorderPainted(false);
    }
    @SuppressWarnings("unchecked") 
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btn_inventario = new javax.swing.JButton();
        btn_user = new javax.swing.JButton();
        btn_cliente = new javax.swing.JButton();
        btn_arancel = new javax.swing.JButton();
        btn_sepulturas = new javax.swing.JButton();
        btn_sepultados = new javax.swing.JButton();
        btn_venta = new javax.swing.JButton();
        btn_pago = new javax.swing.JButton();
        btn_gasto = new javax.swing.JButton();
        btn_trabajadores = new javax.swing.JButton();
        btn_factura = new javax.swing.JButton();
        btn_productos = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        jLabel5.setFont(new java.awt.Font("Franklin Gothic Medium", 3, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Usuarios");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cruzmini.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 70, 70));

        jLabel6.setFont(new java.awt.Font("Franklin Gothic Medium", 3, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Menú Principal");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, -1, -1));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, 530, 20));

        btn_inventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/inventario.png"))); // NOI18N
        btn_inventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_inventarioActionPerformed(evt);
            }
        });
        jPanel1.add(btn_inventario, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 230, 100, 100));

        btn_user.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/user.png"))); // NOI18N
        btn_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_userActionPerformed(evt);
            }
        });
        jPanel1.add(btn_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 100, 100));

        btn_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/clientes.png"))); // NOI18N
        btn_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clienteActionPerformed(evt);
            }
        });
        jPanel1.add(btn_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 100, 100));

        btn_arancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/aranceles.png"))); // NOI18N
        btn_arancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_arancelActionPerformed(evt);
            }
        });
        jPanel1.add(btn_arancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, 100, 100));

        btn_sepulturas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/sepulturas.png"))); // NOI18N
        btn_sepulturas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sepulturasActionPerformed(evt);
            }
        });
        jPanel1.add(btn_sepulturas, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 110, 100, 100));

        btn_sepultados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/sepultados.png"))); // NOI18N
        btn_sepultados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sepultadosActionPerformed(evt);
            }
        });
        jPanel1.add(btn_sepultados, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 110, 100, 100));

        btn_venta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ventas.png"))); // NOI18N
        btn_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ventaActionPerformed(evt);
            }
        });
        jPanel1.add(btn_venta, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 110, 100, 100));

        btn_pago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/pagos.png"))); // NOI18N
        btn_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pagoActionPerformed(evt);
            }
        });
        jPanel1.add(btn_pago, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 100, 100));

        btn_gasto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/GASTOS.png"))); // NOI18N
        btn_gasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_gastoActionPerformed(evt);
            }
        });
        jPanel1.add(btn_gasto, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, 100, 100));

        btn_trabajadores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/trabajadores.png"))); // NOI18N
        btn_trabajadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_trabajadoresActionPerformed(evt);
            }
        });
        jPanel1.add(btn_trabajadores, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 230, 100, 100));

        btn_factura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/facturas.png"))); // NOI18N
        btn_factura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_facturaActionPerformed(evt);
            }
        });
        jPanel1.add(btn_factura, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 230, 100, 100));

        btn_productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/productos.png"))); // NOI18N
        btn_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_productosActionPerformed(evt);
            }
        });
        jPanel1.add(btn_productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 230, 100, 100));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 390));

        jMenu1.setText("Generador de informes");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Cerrar sesión");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clienteActionPerformed
        MantClientes vc = new MantClientes();
        vc.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btn_clienteActionPerformed

    private void btn_sepultadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sepultadosActionPerformed
        MantSepultados vs = new MantSepultados();
        vs.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btn_sepultadosActionPerformed

    private void btn_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ventaActionPerformed
        MantVentas vv = new MantVentas();
        vv.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btn_ventaActionPerformed

    private void btn_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pagoActionPerformed
        MantPagosAbonos vp = new MantPagosAbonos();
        vp.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btn_pagoActionPerformed

    private void btn_trabajadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_trabajadoresActionPerformed
        MantTrabajadores vt = new MantTrabajadores();
        vt.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btn_trabajadoresActionPerformed

    private void btn_facturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_facturaActionPerformed
        MantFacturas vf = new MantFacturas();
        vf.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btn_facturaActionPerformed

    private void btn_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_userActionPerformed
        MantUsuarios vu = new MantUsuarios();
        vu.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btn_userActionPerformed

    private void btn_arancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_arancelActionPerformed
        MantAranceles var = new MantAranceles();
        var.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btn_arancelActionPerformed

    private void btn_sepulturasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sepulturasActionPerformed
        MantSepulturas vse = new MantSepulturas();
        vse.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btn_sepulturasActionPerformed

    private void btn_gastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_gastoActionPerformed
        MantGastos vg = new MantGastos();
        vg.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btn_gastoActionPerformed

    private void btn_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_productosActionPerformed
       MantProductos vpr = new MantProductos();
        vpr.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btn_productosActionPerformed

    private void btn_inventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_inventarioActionPerformed
        MantInventario vi = new MantInventario();
        vi.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btn_inventarioActionPerformed
  
    
    /*public void transparenciaButton(){
        btn_user.setOpaque(false);
        btn_user.setContentAreaFilled(false);
        btn_user.setBorderPainted(false);
        
        
    }*/
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
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_arancel;
    private javax.swing.JButton btn_cliente;
    private javax.swing.JButton btn_factura;
    private javax.swing.JButton btn_gasto;
    private javax.swing.JButton btn_inventario;
    private javax.swing.JButton btn_pago;
    private javax.swing.JButton btn_productos;
    private javax.swing.JButton btn_sepultados;
    private javax.swing.JButton btn_sepulturas;
    private javax.swing.JButton btn_trabajadores;
    private javax.swing.JButton btn_user;
    private javax.swing.JButton btn_venta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
