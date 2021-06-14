package Model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Show {

    public static int idCounter;
    public String name;
    public Date date;
    public float price;
    public Long id;
    public String description;
    public Map<Integer, Ticket> seats; //prvi broj - red, drugij broj - kolona
    public boolean soldOut;

    public Show(){
   
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
    
    public Show(Long id, String name, String description, Date date, float price) {
    	this.id = id;
        this.name = name;
        this.date = date;
        this.price = price;
        this.description = description;
    }
    
    
    

}
