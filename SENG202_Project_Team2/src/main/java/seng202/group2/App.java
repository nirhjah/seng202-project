package seng202.group2;
import com.google.gson.Gson;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        System.out.println( "Hello2 SENG202_Project_Team2 !" );

        MyObject myObject = new MyObject("chair", 3);
        Gson gson = new Gson();
        String jsonString = gson.toJson( myObject );

        System.out.println("myObject = " + myObject);
        System.out.println("myObject stringfyied = " + jsonString);
    }
}
