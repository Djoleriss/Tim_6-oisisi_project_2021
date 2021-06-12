package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage implements ActionListener {
    JFrame frame = new JFrame();

    JButton dugme1 = new JButton("Prijava");
    JButton dugme2 = new JButton("Registracija");

    JPanel panel = new JPanel();

    public HomePage(){
        JFrame.setDefaultLookAndFeelDecorated(true);

        dugme1.addActionListener(this);
        dugme2.addActionListener(this);

        panel.setBorder(BorderFactory.createEmptyBorder(100,150,100,150));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JLabel l1,l2;
        l1=new JLabel("Predstave");
        l1.setBounds(210,30, 100,30);
        l2=new JLabel("Dobro došli!");
        l2.setBounds(205,65, 100,30);
        frame.add(l1); frame.add(l2);

        panel.add(dugme1);
        panel.add(dugme2);


        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Aplikacija za predstave");
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==dugme1) {
            LogIn prijava = new LogIn();
        }
        if(e.getSource()==dugme2) {
            Registracija registracija = new Registracija();
        }
    }
}
