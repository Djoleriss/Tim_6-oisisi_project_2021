package View;

import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Model.My_Connection;
import Model.Show;

public class CreateShow extends JFrame {
    private JFrame frame;
    private JTextField description = new JTextField();
    private JTextField name = new JTextField();
    private JTextField price = new JTextField();
    private JTextField dateD = new JTextField(); //
    private JButton addShow;

    private Show show;
    
    private JLabel labelDescription = new JLabel("Opis : ");
    private JLabel labelName = new JLabel("Naziv predstave : ");
    private JLabel labelPrice = new JLabel("Cena karte : ");
    private JLabel labelDate = new JLabel("Datum: ");
    
    public CreateShow() {
        super("Informacije o predstavi");
    }

    public void createShow(Show show) {
    	PreparedStatement ps;
        ResultSet rs;
        String registerUserQuery = "INSERT INTO `show`(`name`, `date`, `price`, `description`) VALUES (?,now(),?,?)";

        try {
            ps = My_Connection.getInstance().prepareStatement(registerUserQuery);

            ps.setString(1, show.name);
            ps.setDouble(2,  Double.valueOf(show.price));
            ps.setString(3, show.name);

            try{
                if(ps.executeUpdate() != 0){
                    System.out.println("complete");
                }
                }catch(SQLException exept){
                Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE,null,exept);
            }

        }catch (SQLException ex){
            Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

	public void display(JButton button) {
	        setDefaultLookAndFeelDecorated(true);
	        setVisible(true);
	        setBounds(500, 250, 500, 430);
	        setLayout(null);

	        add(addShow = new JButton("Add Show")).setBounds(220,320,100,40);

	        Action addAction = new AbstractAction() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	try {
	            		Show show = new Show();
	                    show.name = name.getText();
	                    show.description = description.getText();
	                    show.price = (float) Double.parseDouble(price.getText());
	                    show.date = new Date();
	                    createShow(show);
	                    button.setEnabled(true);
	                    dispose();
	            	} catch(Exception exc) {
	            		exc.printStackTrace();
	            		System.out.println("here");
	            	    JOptionPane.showMessageDialog(new JFrame(), "Wrong input", "Dialog",
	            	            JOptionPane.ERROR_MESSAGE);
	            	}

	                }
	        };
	        
	        addShow.addActionListener(addAction);

	        add(labelName).setBounds(50, 50, 150, 20);
	        add(labelDescription).setBounds(50, 100, 150, 20);
	        add(labelDate).setBounds(50,150,150,20);
	        add(labelPrice).setBounds(50,200,150,20);

	        name.setEditable(true);
	        name.setText("");

	        description.setEditable(true);
	        description.setText("");

	        price.setEditable(true);
	        price.setText(String.valueOf(""));

	        dateD.setEditable(false);
	        dateD.setText(String.valueOf(""));


	        add(name).setBounds(150, 50, 150, 20);
	        add(description).setBounds(150, 100, 150, 20);
	        add(dateD).setBounds(150, 150,  200, 20);
	        add(price).setBounds(150, 200,150,20);
	        
	        
	        addWindowListener(new java.awt.event.WindowAdapter() {
	            @Override
	            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
	                if (JOptionPane.showConfirmDialog(frame,
	                        "Are you sure you want to close this window?", "Close Window?",
	                        JOptionPane.YES_NO_OPTION,
	                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
	                    button.setEnabled(true);
	                    dispose();
	                }
	            }
	        });
	  
		
	}
    
}
