package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogIn implements ActionListener {

        JFrame frame = new JFrame();
        JLabel label = new JLabel("Prijava");
        JPanel panel = new JPanel();
        JButton Registrovani_Korisnik = new JButton("Registrovani korisnik");
        JButton Admin = new JButton("View.Administrator");

        public  LogIn(){
            label.setBounds(265,35,100,30);
            JFrame.setDefaultLookAndFeelDecorated(true);
            frame.add(label);

            panel.setBorder(BorderFactory.createEmptyBorder(100,150,100,150));
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

            panel.add(Registrovani_Korisnik);
            panel.add(Admin);

            Registrovani_Korisnik.addActionListener(this);
            Admin.addActionListener(this);



            frame.setSize(150,150);
            frame.add(panel, BorderLayout.CENTER);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Prijava");
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==Registrovani_Korisnik) {
            LogInRegistrovaniKorisnik prijava = new LogInRegistrovaniKorisnik(0);
        }
        if(e.getSource()==Admin) {
        	LogInRegistrovaniKorisnik prijava = new LogInRegistrovaniKorisnik(1);
        }
    }
}
