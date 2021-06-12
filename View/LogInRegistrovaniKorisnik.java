package View;

import Model.My_Connection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogInRegistrovaniKorisnik implements ActionListener {

    private static JFrame frame = new JFrame();
    //JLabel label = new JLabel("View.Registracija");
    private static JPanel panel = new JPanel();
    private static JButton login = new JButton("Prijavite se");
    private static JTextField t1 = new JTextField();
    private static JPasswordField p1 = new JPasswordField();

    public LogInRegistrovaniKorisnik() {
        //label.setBounds(0,0,100,50);
        JFrame.setDefaultLookAndFeelDecorated(true);
        //frame.add(label);

        JLabel PrijaviteSe = new JLabel("Unesite vaše korisničko ime i lozinku");
        PrijaviteSe.setBounds(120, 35, 300, 30);
        frame.add(PrijaviteSe);

        JLabel label = new JLabel("Korisničko ime:");
        label.setBounds(50, 100, 200, 30);
        JLabel label2 = new JLabel("Lozinka:");
        label2.setBounds(50, 150, 200, 30);


        frame.add(label);
        frame.add(label2);



        t1.setBounds(150, 100, 200, 30);
        p1.setBounds(150, 150, 200, 30);


        panel.setBorder(BorderFactory.createEmptyBorder(190, 150, 100, 150));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel.add(login);
        login.addActionListener(this);

        frame.add(t1);
        frame.add(p1);


        frame.setSize(400, 350);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Prijava");
        //frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        PreparedStatement st;
        ResultSet rs;

        //get the username and password
        String username = t1.getText();
        String password = p1.getText();

        //create a select query to check if the username and the password exist in the database
        String query = "SELECT * FROM `users` WHERE `username` = ? AND `password` = ?";



        try {
            st = My_Connection.getConnection().prepareStatement(query);

            st.setString(1, username);
            st.setString(2, password);
            rs = st.executeQuery();

            if(rs.next()){
                HomePage gui = new HomePage();
                this.dispose();
            }else{
                //error message
                JOptionPane.showMessageDialog(null,"Pogresno korisnicko ime/lozinka");
            }

        } catch (SQLException ex) {
            Logger.getLogger(LogInRegistrovaniKorisnik.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    private void dispose() {
    }

    public void setVisible(boolean b) {
    }

    public void pack() {
    }

    public void setDefaultCloseOperation(int exitOnClose) {
    }
}

