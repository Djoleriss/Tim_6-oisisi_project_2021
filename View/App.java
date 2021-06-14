package View;
import Model.My_Connection;

public class App {

    public static void main(String args[]){
    	My_Connection.getConnection();
        HomePage homepage = new HomePage();
    }
}
