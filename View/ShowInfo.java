package View;

import Model.Show;

import javax.swing.*;
import java.util.Date;

public class ShowInfo extends JFrame {
    private JButton back;

    private JTextField description = new JTextField();
    private JTextField name = new JTextField();
    private JTextField price = new JTextField();
    private JTextField dateD = new JTextField(); //

    private JLabel labelDescription = new JLabel("Opis : ");
    private JLabel labelName = new JLabel("Naziv predstave : ");
    private JLabel labelPrice = new JLabel("Cena karte : ");
    private JLabel labelDate = new JLabel("Datum: ");

    public ShowInfo(Show show) {
        super("Informacije o predstavi");

        setDefaultLookAndFeelDecorated(true);
        setVisible(true);
        setBounds(500, 250, 500, 430);
        setLayout(null);

        add(back = new JButton("Nazad")).setBounds(220,320,100,40);

        add(labelName).setBounds(50, 50, 150, 20);
        add(labelDescription).setBounds(50, 100, 150, 20);
        add(labelDate).setBounds(50,150,150,20);
        add(labelPrice).setBounds(50,200,150,20);

        name.setEditable(false);
        name.setText(show.name);

        description.setEditable(false);
        description.setText(show.description);

        price.setEditable(false);
        price.setText(String.valueOf(show.price));

        dateD.setEditable(false);
        dateD.setText(String.valueOf(show.date));



        add(name).setBounds(150, 50, 150, 20);
        add(description).setBounds(150, 100, 150, 20);
        add(dateD).setBounds(150, 150,  200, 20);
        add(price).setBounds(150, 200,150,20);

    }


}
