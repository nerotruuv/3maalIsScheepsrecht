import com.fazecast.jSerialComm.*;
import arduino.Arduino;
import org.json.*;

import javax.swing.*;

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


public class main {
    private static final String withdraw_URL = "http://145.24.222.175:5000/withdraw";
    private static final String balance_URL = "http://145.24.222.175:5000/balance";
    private static final String login_URL = "http://145.24.222.175:5000/login";
    private static HttpClient client = HttpClient.newHttpClient();

    private static Arduino AdruinoCon = new Arduino("COM9", 9600);



    /*
     * Sending commands to the arduino, sendcommand is for writing to serial, other functions
     * are for formatting the messages to be send
     */
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
    // maak nog een functie voor het uivoglen van de benodigde 70 euro biljette
    private static void sendEurCommand(int bil1, int bil2, int bil3)throws Exception{
        String commandToSend = ("EUR," + Integer.toString(bil1) + "," + Integer.toString(bil2) + "," + Integer.toString(bil3));
        System.out.println("Send: " + commandToSend);
        sendCommand(commandToSend);
    }
    private static void sendBonCommand(int total, String IBAN)throws Exception{
        System.out.println(LocalDate.now());
        String commandToSend = ("BAN," + Integer.toString(total) + "," + IBAN + "," + LocalDate.now());
        System.out.println("Send: " + commandToSend);
        sendCommand(commandToSend);
    }
    /*
     * POST requesnts to be send to the banks database, it is predetermined if the iban is meant for our database
     * so that does not have to be checked here
     * the functions return the whole response so it can be discected in other functions
     */
    private static HttpResponse postBalance(String IBAN) throws Exception{

        String inputs = ("/" + IBAN);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(balance_URL+inputs))
            .POST(HttpRequest.BodyPublishers.noBody())
            .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        // System.out.println(response.statusCode());
        // System.out.println(response.body());

        return response;
    }
    
    private static HttpResponse postWithdraw(String IBAN, int amount) throws Exception{

        String inputs = ("/" + IBAN + "/" + Integer.toString(amount));
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(withdraw_URL+inputs))
            .POST(HttpRequest.BodyPublishers.noBody())
            .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        // System.out.println(response.statusCode());
        // System.out.println(response.body());

        return response;
    }
    
    private static HttpResponse postLogin(String IBAN, int PIN) throws Exception{

        String inputs = ("/" + IBAN + "/" + Integer.toString(PIN));
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(login_URL+inputs))
        .POST(HttpRequest.BodyPublishers.noBody())
        .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        return response;
    }

    /*
     * Parsing functions for gettind information out of the response body,
     * the response is written in json so this function extract the single usefull values out if the json
     */
    private static int parseBalance(String jsonString){
        JSONObject obj = new JSONObject(jsonString);
        String n = obj.getString("message");
        String res[] = n.substring(2).split(",");
        return Integer.parseInt(res[0]);
    }



    private static void newLogin(HttpResponse response) throws Exception {
        if(response.statusCode() == 403){ // geblokkeerd
            System.out.print("get cucked");
        }else if(response.statusCode() == 401){// verkeerde pin
            System.out.println("verkeerde pin");
            sendLogCommand(0);
        }else if(response.statusCode() == 200){
            System.out.println("Pin succesvol");
            sendLogCommand(1);
        }
    }




    /*
     * This is the serial interpreter, it receives the incomming information and interpretes it
     * to figure out which funtion the seponse is meant for
     */
    private static void readSerial() throws Exception {
        String inputString = AdruinoCon.serialRead(); 
        String inputSplit[] = inputString.substring(0).split(",");
        //hier kunnen terug gestuurde commandos verwerkt worden
        if(inputSplit[0] == "PIN"){
            //hier moet de code om de login ter verifieren of om terug te reageren
            // postlogin, iban, pin            
            HttpResponse response = postLogin(inputSplit[1], Integer.parseInt(inputSplit[2]));
            newLogin(response);
        
        //menu navigatie
        }
        else if(inputSplit[0] == "NAV"){
            
        }
        else if(inputSplit[0] == "MON"){

        }
    }
    


    /*
    * if a command is to be send to the arduino, it can be done by checking if the connection is open
    * meaning that the arduino or java isnt writing on the same serial connection
    *   if(AdruinoCon.openConnection()){ 
    *       sendBonCommand(50, "NL76RABO0354400312");       
    *   }
    * is an example of sending the information for printing to the arduino 
    */

    /*
     * send out command to get pin and iban info -> receive info
     * check for iban to determine the databse to check against
     *  -> not ours, forward with all neccecery info to landserver
     *  -> continue
     * check info against database  -> 401 go again, wrong pin
     *                              -> 403 pas blocked, ftfu
     *                              -> 200 continue to next menu
     * open main menu, wait for new input
     *  -> saldo 
     *      postBalance(ingevoerder iban) -> geef weer op scherm
     *      wacht op invoer
     *  -> snel pinnen
     *      postWithdraw(ingevoerde iban, 70 euro)
     *          -> 406 onvoldoende saldo, terug naar menu
     *          -> 200 bonprint ja/nee, wacht op invoer
     *             -> ja, sendBonCommand(), sendEurCommand(), uitloggen en naar default scherm 
     *             -> nee, sendEurCommand(), uitloggen en naar default scherm 
     *  -> bedrag pinnen
     *      wacht op invoer
     *      postWithdraw(invoer)
     *          -> 406 onvoldoende saldo, terug naar menu
     *          -> 200 bonprint ja/nee, wacht op invoer
     *             -> ja, sendBonCommand(), sendEurCommand(), uitloggen en naar default scherm 
     *             -> nee, sendEurCommand(), uitloggen en naar default scherm 
     *  -> uitloggen
     */
    public static void main(String[] args) throws Exception {
        

        /*
         * This is meant to be running constantly the collect information from the serial port
         * the information comes in as a string and is split up into the inputsplit array
         * this seperates all the single values seperated on every, 
         */
        while(true){
           
            readSerial();

        }
    } 
}
