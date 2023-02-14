import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Prueba2B {
    private JPanel panel1;
    private JButton Crear1;
    private JButton Buscar2;
    private JButton Actualizar3;
    private JButton Borrar4;
    private JLabel CI;
    private JTextField CID;
    private JLabel Nom;
    private JTextField NomD;
    private JLabel Ape;
    private JTextField ApeD;
    private JTextField EdadD;
    private JLabel Edad;
    private JLabel Marca;
    private JComboBox Marcas;
    Statement ps;
    PreparedStatement st;

    public Prueba2B() {

        Buscar2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con;
                Actualizar3.setEnabled(true);
                try {
                    con = getConection();
                    ps = con.createStatement();
                    ResultSet rs;
                    rs = ps.executeQuery("select * from DATOS where CI_DUENIO=" + CID.getText() + ";");
                    while (rs.next()) {
                        NomD.setText(rs.getString("NOM_DUENIO"));
                        ApeD.setText(rs.getString("APE_DUENIO"));
                        EdadD.setText(rs.getString("EDAD"));
                    }
                } catch (Exception s) {

                }
            }
        });

        Actualizar3.addActionListener(new ActionListener() {
            Connection con2;
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    con2 = getConection();
                    st = con2.prepareStatement("UPDATE DATOS SET NOM_DUENIO = ?, APE_DUENIO = ?, EDAD = ? WHERE CI_DUENIO ="+CID.getText() );

                    st.setString(1,NomD.getText());
                    st.setString(2,ApeD.getText());
                    st.setString(3,EdadD.getText());

                    System.out.println(ps);
                    int res = st.executeUpdate();

                    if(res > 0 ){
                        JOptionPane.showMessageDialog(null,"La actualización se realizado con EXITO!");
                    }else{
                        JOptionPane.showMessageDialog(null,"Error, datos invalidos!! ERROR !!");
                    }
                    con2.close();
                }catch (HeadlessException | SQLException f){
                    System.out.println(f);
                }
            }
        });
        Crear1.addActionListener(new ActionListener() {
            Connection con3;
            @Override
            public void actionPerformed(ActionEvent e) {
                con3 = getConection();

                try{
                    st = con3.prepareStatement("INSERT INTO DATOS(CI_DUENIO,NOM_DUENIO,APE_DUENIO,EDAD) VALUES (?,?,?,?)");

                    st.setString(1,CID.getText());
                    st.setString(2,NomD.getText());
                    st.setString(3,ApeD.getText());
                    st.setString(4,EdadD.getText());

                    int res = st.executeUpdate();

                    if(res > 0){
                        JOptionPane.showMessageDialog(null, "Se creo de manera correta");
                    }else {
                        JOptionPane.showMessageDialog(null, "No se pudo crear");
                    }

                }catch (HeadlessException | SQLException f){
                    System.out.println(f);
                }
            }
        });
        Borrar4.addActionListener(new ActionListener() {
            Connection con4;
            @Override
            public void actionPerformed(ActionEvent e) {
                con4 = getConection();

                try {
                    st = con4.prepareStatement("DELETE FROM DATOS WHERE CI_DUENIO ="+CID.getText());
                    int res = st.executeUpdate();

                    if(res > 0 ){
                        JOptionPane.showMessageDialog(null,"Se elemino con exito");
                    }else{
                        JOptionPane.showMessageDialog(null,"Error, datos invalidos!! ERROR !!");
                    }
                }catch (HeadlessException | SQLException f){
                    System.out.println(f);
                }
            }
        });
        Marcas.addActionListener(new ActionListener() {
            Connection con5;

            @Override
            public void actionPerformed(ActionEvent e) {
                con5 = getConection();

                try {
                    PreparedStatement pst = null;

                    ps = con5.createStatement();
                    ResultSet rs;
                    rs = ps.executeQuery("select * from MARCAS =" + Marcas.getToolTipText() + ";");
                    while (rs.next()) {

                    }

                    } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
            });
        };


    public static Connection getConection() {
        Connection con = null;
        String base = "VEHICULOS";
        String url = "jdbc:mysql://localhost:3306/" + base;
        String user = "root";
        String password = "Pelota2002";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e);
        }
        return con;


    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Josue Salazar \t                            Vehiculos");
        frame.setContentPane(new Prueba2B().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500,500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
