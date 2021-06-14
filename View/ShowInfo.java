package View;

import Model.My_Connection;
import Model.Show;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShowInfo extends JFrame {
    private JButton back;
    private JButton edit;
    private JButton button;
    
    private Show show;
    
    private JTextField description = new JTextField();
    private JTextField name = new JTextField();
    private JTextField price = new JTextField();
    private JTextField dateD = new JTextField(); //
    private JTextField id = new JTextField(); //
    
    private JLabel labelId = new JLabel("Id : ");
    private JLabel labelDescription = new JLabel("Opis : ");
    private JLabel labelName = new JLabel("Naziv predstave : ");
    private JLabel labelPrice = new JLabel("Cena karte : ");
    private JLabel labelDate = new JLabel("Datum: ");

    public ShowInfo(Show show, JButton button) {
        super("Informacije o predstavi");

        setDefaultLookAndFeelDecorated(true);
        setVisible(true);
        setBounds(500, 250, 500, 430);
        setLayout(null);

        this.show = show;
        this.button = button;
        
        add(back = new JButton("Nazad")).setBounds(220,320,100,40);

        Action returnAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            		button.setEnabled(true);
                    dispose();
                }
        };
        
        Action editAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
                    show.name = name.getText();
                    show.description = description.getText();
                    show.price = (float) Double.parseDouble(price.getText());
                    updateShow();
            	} catch(Exception exc) {
            		exc.printStackTrace();
            		System.out.println("here");
            	    JOptionPane.showMessageDialog(new JFrame(), "Wrong input", "Dialog",
            	            JOptionPane.ERROR_MESSAGE);
            	}

                }
        };

        back.addActionListener(returnAction);

        if(StateHolder.getInstance().getUserType() == "admin") {
            add(edit = new JButton("Edit")).setBounds(90,320,100,40);
            edit.addActionListener(editAction);
        }


        add(labelName).setBounds(50, 50, 150, 20);
        add(labelDescription).setBounds(50, 100, 150, 20);
        add(labelDate).setBounds(50,150,150,20);
        add(labelPrice).setBounds(50,200,150,20);
        add(labelId).setBounds(50,10,150,20);

        name.setEditable(true);
        name.setText(show.name);

        description.setEditable(true);
        description.setText(show.description);

        price.setEditable(true);
        price.setText(String.valueOf(show.price));

        dateD.setEditable(false);
        dateD.setText(String.valueOf(show.date));
        
        id.setEditable(false);
        id.setText(String.valueOf(show.id));


        add(name).setBounds(150, 50, 150, 20);
        add(description).setBounds(150, 100, 150, 20);
        add(dateD).setBounds(150, 150,  200, 20);
        add(price).setBounds(150, 200,150,20);
        add(id).setBounds(150, 10,150,20);
    }

    public void updateShow() {
        PreparedStatement st;
        int rs;
        
        String query = "UPDATE `show` SET name = ?, price = ?, description = ? WHERE show_id = ?";

        try {
            st = My_Connection.getInstance().prepareStatement(query);
            st.setString(1, show.name);
            st.setDouble(2, show.price);
            st.setString(3, show.description);
            st.setLong(4, show.id);
            rs = st.executeUpdate();

            if(rs == 1) {
            	System.out.println("here");
            	Listing listing = new Listing();
            	listing.display(button);
            }
            
            this.dispose();
            
        } catch (SQLException ex) {
            Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
