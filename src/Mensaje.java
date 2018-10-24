
import javax.swing.JOptionPane;
import javax.swing.*;
import javax.swing.*;  
import java.awt.event.*; 
//public class Mensaje {
//   public static void main(String[] args) {
//            JOptionPane.showMessageDialog(null, "Hello World");
//        } 
//}

    
    
//public class Mensaje {  
//JFrame f;  
//Mensaje(){  
//    f=new JFrame();  
//    JOptionPane.showMessageDialog(f,"Venta Modificada Correctamente","Modificar",JOptionPane.WARNING_MESSAGE);     
//}  
//public static void main(String[] args) {  
//    new Mensaje();  
//}  
//}  

 
public class Mensaje extends WindowAdapter{  
JFrame f;  
Mensaje(){  
    f=new JFrame();   
    f.addWindowListener(this);  
    f.setSize(300, 300);  
    f.setLayout(null);  
    f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  
    f.setVisible(true);  
}  
public void windowClosing(WindowEvent e) {  
    int a=JOptionPane.showConfirmDialog(f,"¿Esta seguro que quiere eliminar esta cliente?");  
    if(a==JOptionPane.YES_OPTION){  
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    }  
}  
public static void main(String[] args) {  
    new  Mensaje();  
}     
}