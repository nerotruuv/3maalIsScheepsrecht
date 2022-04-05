 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
 
public class connectDatabase {


	
 
    public static void main(String[] args) throws Exception {

       
        get();
        
 
    }
    public static ArrayList<String> get() throws Exception{
		String select = " ";
		int i = 0;
		if (i == 1){select = "SELECT Aantal_pogingen FROM accounts WHERE Account_number ==";};
		if (i == 2){select = "UPDATE accounts SET Aantal_pogingen = Aantal_pogingen + 1 WHERE Account_number ==";};
		if (i == 3){select = "UPDATE accounts SET Aantal_pogingen = 0 WHERE Account_number ==";};
		if (i == 4){select = "SELECT 'Balance' FROM `accounts` WHERE Account_number ==";};
		if (i == 5){select = "UPDATE 'accounts' SET Card_blocked = 1 WHERE Account_number ==";};
		if (i == 6){select = "SELECT 'Card_blocked' FROM `accounts a` WHERE Account_number ==";};
		if (i == 7){select = "SELECT 'Balance' FROM `accounts` WHERE Account_number ==";};
		if (i == 8){select = "SELECT C.Pincode FROM accounts A, customers C WHERE A.customer_id == C.customer_id AND A.Account_number ==";};
		if (i == 9){select = "UPDATE 'accounts' SET balance = "+" +str(balance - AMOUNT)+ "+" saldo WHERE Account_number == " +" + str(ACCNMR)";};
		if (i == 10){select = "SELECT 'Card_blocked' FROM `accounts a` WHERE Account_number ==";};


        try{
            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(select);
            
            ResultSet result = statement.executeQuery();
            
            ArrayList<String> array = new ArrayList<String>();
            while(result.next()){

            }
            System.out.println("All records have been selected!");
            return array;
            
        }catch(Exception e){System.out.println(e);}
        return null;
        
    }

    public static Connection getConnection() throws Exception{
        try{
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://24.196.52.166:3306/testdb";
            String username = "javadata";
            String password = "mypass";
            Class.forName(driver);
            
            Connection conn = DriverManager.getConnection(url,username,password);
            System.out.println("Connected");
            return conn;
        } catch(Exception e){System.out.println(e);}
        
        
        return null;
    }
 
}