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


public class Registracija implements ActionListener {

    /*Object ob = null;
    Object ob2 = null;
    Object ob3 = null;
    Object ob4 = null;*/

    JFrame frame = new JFrame();
    //JLabel label = new JLabel("View.Registracija");
    JPanel panel = new JPanel();
    JButton Potvrdite = new JButton("Potvrdite");
    JTextField korisnickoime = new JTextField();
    JTextField ime = new JTextField();
    JTextField prezime = new JTextField();
    JPasswordField password = new JPasswordField();


    public Registracija() {
//label.setBounds(0,0,100,50);
        JFrame.setDefaultLookAndFeelDecorated(true);
        //frame.add(label);

        JLabel RegistrujteSe = new JLabel("Registrujte se");
        RegistrujteSe.setBounds(225, 35, 200, 30);

        Potvrdite.addActionListener(this);

        frame.add(RegistrujteSe);

        JLabel label = new JLabel("Korisničko ime:");
        label.setBounds(100, 100, 200, 30);
        JLabel label2 = new JLabel("Ime:");
        label2.setBounds(100, 150, 200, 30);
        JLabel label3 = new JLabel("Prezime:");
        label3.setBounds(100, 200, 200, 30);
        JLabel label4 = new JLabel("Password:");
        label4.setBounds(100, 250, 200, 30);

        frame.add(label);
        frame.add(label2);
        frame.add(label3);
        frame.add(label4);


        korisnickoime.setBounds(200, 100, 200, 30);
        ime.setBounds(200, 150, 200, 30);
        prezime.setBounds(200, 200, 200, 30);
        password.setBounds(200, 250, 200, 30);


        panel.setBorder(BorderFactory.createEmptyBorder(300, 225, 100, 150));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel.add(Potvrdite);

        frame.add(korisnickoime);
        frame.add(ime);
        frame.add(prezime);
        frame.add(password);

        frame.setSize(500, 500);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("View.Registracija");
        //frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public boolean verifyFields() {
        String username = korisnickoime.getText();
        String name = ime.getText();
        String surname = prezime.getText();
        String pass1 = password.getText();

        if (username.trim().equals("") || name.trim().equals("") || surname.equals("") || pass1.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Sva polja moraju biti popunjena!");
            return false;
        } else {
            return true;
        }

    }

    public boolean checkUsername(String username) {
        PreparedStatement st;
        ResultSet rs;
        boolean username_exist = false;

        String query = "SELECT * FROM `users` WHERE `username` = ?";

        try {
            st = My_Connection.getConnection().prepareStatement(query);
            st.setString(1, username);
            rs = st.executeQuery();

            if (rs.next()) {
                username_exist = true;
                JOptionPane.showMessageDialog(null, "Korisnicko ime vec postoji!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE, null, ex);
        }
        return username_exist;
    }





    @Override
    public void actionPerformed(ActionEvent e) {

        String username = korisnickoime.getText();
        String name = ime.getText();
        String surname = prezime.getText();
        String pass1 = password.getText();

        if(verifyFields()){
            if(!checkUsername(username))
            {
                PreparedStatement ps;
                ResultSet rs;
                String registerUserQuery = "INSERT INTO `users`(`name`, `surname`, `username`, `password`) VALUES (?,?,?,?)";

                try {
                    ps = My_Connection.getConnection().prepareStatement(registerUserQuery);

                    ps.setString(1, name);
                    ps.setString(2,surname);
                    ps.setString(3, username);
                    ps.setString(4,pass1);

                    try{
                        if(ps.executeUpdate() != 0){
                            JOptionPane.showMessageDialog(null,"Uspesno ste se registrovali!");
                            LogInRegistrovaniKorisnik login = new LogInRegistrovaniKorisnik();
                        }
                    }catch(SQLException exept){
                        Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE,null,exept);
                    }

                }catch (SQLException ex){
                    Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE,null,ex);
                }
            }

        }


    }




}
