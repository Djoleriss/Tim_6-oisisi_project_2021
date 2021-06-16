package View;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ShowSeat{
	//public static void main(String[] args)
     {
    	 
        JFrame framenew = new JFrame();
        framenew.setSize(480,500);
        framenew.setTitle("Prikaz sedista");
        framenew.setLayout(null);
        framenew.setVisible(true);

        int x,y;
        final int[] value = new int[1];
        value[0]=0;
        x=1;
        y=1;
        for(int i=1;i<=30;i++) {

            JButton btn = new JButton(String.valueOf(i));
            btn.setBounds(x * 60, y * 60, 50, 50);
            btn.setBackground(Color.GREEN);
            int finalI = i;
            btn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    value[0] = Integer.valueOf(finalI);
                    btn.setBackground(Color.RED);
                    btn.setEnabled(false);
                    int num = Integer.parseInt(btn.getActionCommand());
                    System.out.println(num);
                    framenew.dispose();
                   
                }
            });
            framenew.add(btn);
            x++;
            if (x == 7) {
                x = 1;
                y++;
            }
        }
        JButton btn2 = new JButton("Izlazak");
        btn2.setBounds(180,370,100,20);
        framenew.add(btn2);
        btn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                framenew.dispose();
            }
        });
       
      // return num;             vraca vrednost num sto je broj tog sedista
    }

}