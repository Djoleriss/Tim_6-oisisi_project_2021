package oisisi_project_2021;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import oisisi_project_2021.Model.Show;
import oisisi_project_2021.View.Listing;
import oisisi_project_2021.View.SearchShow;

class GUI {

    Listing listing;
    SearchShow searchShow;
    private ArrayList<Show> shows = new ArrayList<>();

    public GUI() {
        
        JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame frame = new JFrame();
        JPanel panel = new JPanel(null);

        JButton button = new JButton("Prikazi predstave");
        JButton button2 = new JButton("Pretrazi predstave");

        populateShows();

        button.addActionListener(e ->
        {
           listing = new Listing();
           listing.display(button, shows);
           button.setEnabled(false);
        });

        button2.addActionListener(e ->
        {
           searchShow = new SearchShow();
           searchShow.display(button2, shows);
           button2.setEnabled(false);
        });


        panel.setBorder(BorderFactory.createEmptyBorder(100, 150, 100, 150));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(button);
        panel.add(button2);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Aplikacija za predstave");
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void populateShows() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 15);
        
        shows.add(new Show("predstava1", new Date(), 200));
        shows.add(new Show("predstava2", cal.getTime(), 400));
    }

    public static void main(String[] args) {
        System.out.println("here");
        GUI gui = new GUI();
    }


}