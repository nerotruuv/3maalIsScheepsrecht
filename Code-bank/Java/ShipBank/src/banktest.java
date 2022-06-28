import com.fazecast.jSerialComm.*;
import arduino.Arduino;
import org.json.*;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.text.SimpleDateFormat;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.time.LocalDate;



public class banktest {

    private static final String withdraw_URL = "http://145.24.222.175:5000/withdraw";
    private static final String balance_URL = "http://145.24.222.175:5000/balance";
    private static final String login_URL = "http://145.24.222.175:5000/login";

    private static HttpClient client = HttpClient.newHttpClient();

    private static Arduino AdruinoCon = new Arduino("COM5", 9600);

    private static void display_port()
    {
        SerialPort[] ports = SerialPort.getCommPorts();
        for (int i = 0; i < ports.length; i++) 
        {
        System.out.println(ports[i].getSystemPortName());
        }
    }

    private static void sendCommand(String command) throws InterruptedException {
        AdruinoCon.openConnection();    
        Thread.sleep(2000);
        AdruinoCon.serialWrite(command);
    }
    //LOG,JA?NEE
    private static void sendLogCommand(int result)throws Exception{

        String commandToSend = ("LOG," + Integer.toString(result));
        System.out.println("Send: " + commandToSend);
        sendCommand(commandToSend);
    }
    private static void sendEurCommand(int bil1, int bil2, int bil3)throws Exception{
        String commandToSend = ("EUR," + Integer.toString(bil1) + "," + Integer.toString(bil2) + "," + Integer.toString(bil3));
        System.out.println("Send: " + commandToSend);
        sendCommand(commandToSend);
    }
    private static void sendBonCommand(int total, String IBAN)throws Exception{
        String commandToSend = ("BAN," + Integer.toString(total) + "," + IBAN);
        System.out.println("Send: " + commandToSend);
        sendCommand(commandToSend);
    }

    private static String postBalance(String IBAN) throws Exception{

        String inputs = ("/" + IBAN);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(balance_URL+inputs))
            .POST(HttpRequest.BodyPublishers.noBody())
            .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        // System.out.println(response.statusCode());
        // System.out.println(response.body());

        return response.body();
    }
    
    private static String postWithdraw(String IBAN, int amount) throws Exception{

        String inputs = ("/" + IBAN + "/" + Integer.toString(amount));
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(withdraw_URL+inputs))
            .POST(HttpRequest.BodyPublishers.noBody())
            .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        // System.out.println(response.statusCode());
        // System.out.println(response.body());

        return response.body();
    }
    
    private static int postLogin(String IBAN, int PIN) throws Exception{

        String inputs = ("/" + IBAN + "/" + Integer.toString(PIN));
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(withdraw_URL+inputs))
        .POST(HttpRequest.BodyPublishers.noBody())
        .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        return response.statusCode();
    }
    
    //niet af kon nog niet testen
    private static int parseLogin(String jsonString){
        JSONObject obj = new JSONObject(jsonString);
        String n = obj.getString("message");
        String res[] = n.substring(2).split(",");
        return Integer.parseInt(res[0]);
    }

    private static int parseBalance(String jsonString){
        JSONObject obj = new JSONObject(jsonString);
        String n = obj.getString("message");
        String res[] = n.substring(2).split(",");
        return Integer.parseInt(res[0]);
    }

    private static void readSerial() throws Exception {
        String inputString = AdruinoCon.serialRead(); 
        String inputSplit[] = inputString.substring(0).split(",");
        //hier kunnen terug gestuurde commandos verwerkt worden
        if(inputSplit[0] == "PIN"){
            //hier moet de code om de login ter verifieren of om terug te reageren
            int response = postLogin(inputSplit[1], Integer.parseInt(inputSplit[2]));
            
            if(response == 403){ // geblokkeerd
                System.out.print("get cucked");
            }else if(response == 401){// verkeerde pin
                System.out.println("verkeerde pin");
                sendLogCommand(0);
            }else if(response == 200){
                System.out.println("Pin succesvol");
                sendLogCommand(1);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if(AdruinoCon.openConnection()){
            sendBonCommand(50, "NL76RABO0354400312");    
        }

        //dit stukje is om de response van arduino te ontvangen, als test
        // while(true){
        //     String inputString = AdruinoCon.serialRead();
            
        //     String inputSplit[] = inputString.substring(0).split(",");

        //     if(inputString != ""){
        //         System.out.println(inputString);
        //         break;
        //     }
        // }  
    }   
}

 