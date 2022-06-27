package Bank_Link2.Java_link;

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


    private static void sendCommand(String command) throws InterruptedException {
        AdruinoCon.openConnection();    
        Thread.sleep(2000);
        AdruinoCon.serialWrite(command);
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

    private static void display_port()
    {
        SerialPort[] ports = SerialPort.getCommPorts();
        for (int i = 0; i < ports.length; i++) 
        {
        System.out.println(ports[i].getSystemPortName());
        }
    }

    public static void OutputStreamByteSubSequence(String com, String data) throws Exception {
        byte[] bytes = data.getBytes();
        System.out.println(bytes.length);
        SerialPort port = SerialPort.getCommPort(com);
        port.setComPortParameters(9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        port.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0);

        if (port.openPort()){
            System.out.println("Port is open");
        }else{
            System.out.println("Failed to open port");
            return;
        }

        PrintWriter output = new PrintWriter(port.getOutputStream());
        output.print(data);
        output.flush();
        
        if (port.closePort()){
            System.out.println("Port is closed");
        }else{
            System.out.println("Failed to close port");
            return; 
        }

    }

    public static void main(String[] args) throws Exception {
        //display_port();
        sendCommand("70-2-3-4-0982");
        System.out.println("send");

        //OutputStreamByteSubSequence("COM11", "on");    
    }   
}

    // Thread thread = new Thread(){
    //     @Override public void run(){
    //         try {Thread sleep(100); } catch(Exception e){}
    //         PrintWriter output = new PrintWriter(port.getOutputStream());
    //         while(true){
    //             output.print("Hello");
    //             output.flush();
    //         }
    //     }
    // }
    // thread.start();

            // public static void OutputStreamByteSubSequence(String com, String data) throws Exception {
    //     byte[] bytes = data.getBytes();
    //     System.out.println(bytes.length);
    //     SerialPort port = SerialPort.getCommPort(com);
    //     port.setComPortParameters(9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
    //     port.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 10000, 10000);

    //     if (port.openPort()){
    //         System.out.println("Port is open");
    //     }else{
    //         System.out.println("Failed to open port");
    //         return;
    //     }

    //     for(int i = 0; i < bytes.length; i++){
    //         port.getOutputStream().write(bytes[i]);
    //         port.getOutputStream().flush();
    //         Thread.sleep(500);
    //     }

    //     if (port.closePort()){
    //         System.out.println("Port is closed");
    //     }else{
    //         System.out.println("Failed to close port");
    //         return; 
    //     }
    // }