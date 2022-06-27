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




public class banktest {

    private static final String withdraw_URL = "http://145.24.222.175:5000/withdraw";
    private static final String balance_URL = "http://145.24.222.175:5000/balance";
    private static HttpClient client = HttpClient.newHttpClient();

    private static Arduino AdruinoCon = new Arduino("COM11", 9600);


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
    private static void sendLogCommand(int pin, int IBAN)throws Exception{
        String commandToSend = "LOG," + Integer.toString(pin) + "," + Integer.toString(IBAN);
        sendCommand(commandToSend);
    }
    private static void sendEurCommand(int bil1, int bil2, int bil3)throws Exception{
        String commandToSend = "EUR," + Integer.toString(bil1) + "," + Integer.toString(bil2) + "," + Integer.toString(bil3);
        sendCommand(commandToSend);
    }
    private static void sendBonCommand(int total, int IBAN)throws Exception{
        String commandToSend = "BAN," + Integer.toString(total) + "," + Integer.toString(IBAN);
        sendCommand(commandToSend);
    }

    private static String postBalance(String inputs) throws Exception{

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(balance_URL+inputs))
            .POST(HttpRequest.BodyPublishers.noBody())
            .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        // System.out.println(response.statusCode());
        // System.out.println(response.body());

        return response.body();
    }
    private static String postWithdraw(String inputs) throws Exception{

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(withdraw_URL+inputs))
            .POST(HttpRequest.BodyPublishers.noBody())
            .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        // System.out.println(response.statusCode());
        // System.out.println(response.body());

        return response.body();
    }
    private static int parseBalance(String jsonString){
        JSONObject obj = new JSONObject(jsonString);
        String n = obj.getString("message");
        String res[] = n.substring(2).split(",");
        return Integer.parseInt(res[0]);
    }

    public static void main(String[] args) throws Exception {
        //display_port();
        sendCommand("70-2-3-4-0982");
        System.out.println("send");   
    }   
}

 