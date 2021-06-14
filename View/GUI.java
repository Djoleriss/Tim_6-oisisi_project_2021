package View;

import Model.Show;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


class GUI {

    Listing listing;
    SearchShow searchShow;
    private ArrayList<Show> shows = new ArrayList<>();

    public GUI() {
        
        JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame frame = new JFrame();
        JPanel panel = new JPanel(null);

        JButton button = new JButton("Prikazi predstave");
        JButton button2 = new JButton("Pretrazi predstave po nazivu");
        JButton button3 = new JButton("Pretrazi predstave po ceni");
        JButton button4 = new JButton("Pretrazi predstave po datumu");
        JButton button5 = new JButton("Dodaj predstavu");
        listing = new Listing();
        searchShow = new SearchShow();
        CreateShow cs = new CreateShow();
        
        populateShows();

        button.addActionListener(e ->
        {
           listing.display(button);
           button.setEnabled(false);
        });

        button2.addActionListener(e ->
        {
           searchShow.display(button2, shows, 1);
           button2.setEnabled(false);
        });

        button3.addActionListener(e ->
        {
           searchShow.display(button3, shows, 2);
           button3.setEnabled(false);
        });

        button4.addActionListener(e ->
        {
            searchShow.display(button4, shows, 3);
            button4.setEnabled(false);
        });
        
        button5.addActionListener(e ->
        {
            cs.display(button5);
            button5.setEnabled(false);
        });

        panel.setBorder(BorderFactory.createEmptyBorder(100, 150, 100, 150));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(button);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        
        if(StateHolder.getInstance().getUserType() == "admin") {
            panel.add(button5);
         }

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
        
        shows.add(new Show("predstava1", "description1", new Date(), 200));
        shows.add(new Show("predstava2", "description2", cal.getTime(), 400));
    }


}
