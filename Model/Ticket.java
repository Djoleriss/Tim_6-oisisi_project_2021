package Model;

public class Ticket {
    public static int idCounter = 0;

    public int id;

    public Show show;

    public float price;

    public Ticket(){
        this.id = ++idCounter;
    }

}
