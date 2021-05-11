package Model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Show {

    public static int idCounter = 0;
    public String name;
    public Date date;
    public float price;
    public int id;
    public String description;
    public Map<Integer, Ticket> seats; //prvi broj - red, drugij broj - kolona
    public boolean soldOut;

    public Show(){
        seats = new HashMap<Integer, Ticket>();
        this.id = ++idCounter;
    }

    public Show(String name, Date date, float price) {
        this.name = name;
        this.date = date;
        this.price = price;
    }

    public Show(String name, String description, Date date, float price) {
        this.name = name;
        this.date = date;
        this.price = price;
        this.description = description;
    }

}
