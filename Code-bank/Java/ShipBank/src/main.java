import com.fazecast.jSerialComm.*;
import arduino.Arduino;
import org.json.*;

import javax.swing.*;
import java.awt.*;

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

    /*
    * alle gui declaraties
    */
    static String saldo = "something";
    static String costumAmount = "";

    static JFrame frame = new JFrame("My First GUI"); // UI window creation
    static JButton button1 = new JButton("Scan uw kaart");

    static JButton button2_1 = new JButton("");
    static JButton button2_2 = new JButton("[#] Verder");
    static JButton button2_3 = new JButton("[*] Annuleren");

    static JButton button3_1 = new JButton("[1] Snel 70 EUR pinnen");
    static JButton button3_2 = new JButton("[2]Ander bedrag pinnen");
    static JButton button3_3 = new JButton("[3]Saldo bekijken");
    static JButton button3_4 = new JButton("[B] Afsluiten");

    static JButton button4_1 = new JButton("[1] 20 EUR");
    static JButton button4_2 = new JButton("");
    static JButton button4_3 = new JButton("[3] 200 EUR");
    static JButton button4_4 = new JButton("[4] 50 EUR");
    static JButton button4_5 = new JButton("");
    static JButton button4_6 = new JButton("[6] 300 EUR");
    static JButton button4_7 = new JButton("[7] 100 EUR");
    static JButton button4_8 = new JButton("[A] Terug");
    static JButton button4_9 = new JButton("[9] Ander bedrag invoeren");

    static JButton button5_1 = new JButton(saldo); // show de balance
    static JButton button5_2 = new JButton("[A] Terug                                [B] Afsluiten");

    static JButton button6_1 = new JButton("Uw geld komt eraan.");

    static JButton button7_1 = new JButton("Wilt u terug naar hoofdmenu of afsluiten?");
    static JButton button7_2 = new JButton("[A] Terug                                [B] Afsluiten");

    static JButton button8_1 = new JButton("[*] Fijne dag!");

    static JButton button9_1 = new JButton("Weet u het zeker?");
    static JButton button9_2 = new JButton("[A] Ja                                [B] Nee");

    static JButton button10_1 = new JButton("Wilt u de bon?");
    static JButton button10_2 = new JButton("[A] Ja                                [B] Nee");

    static JButton button11_1 = new JButton("Voer uw gewenste bedrag in");
    static JButton button11_2 = new JButton(costumAmount);
    static JButton button11_3 = new JButton("[#] Verder");

    static JPanel panelCont = new JPanel();
    static JPanel panel1 = new JPanel(new GridLayout(1, 1));
    static JPanel panel2 = new JPanel(new GridLayout(3, 1));
    static JPanel panel3 = new JPanel(new GridLayout(2, 2));

    static JPanel panel4 = new JPanel(new GridLayout(3, 2));
    static JPanel panel5 = new JPanel(new GridLayout(2, 1));
    static JPanel panel6 = new JPanel(new GridLayout(1, 1));
    static JPanel panel7 = new JPanel(new GridLayout(2, 1));
    static JPanel panel8 = new JPanel(new GridLayout(1, 1));
    static JPanel panel9 = new JPanel(new GridLayout(2, 1));
    static JPanel panel10 = new JPanel(new GridLayout(2, 1));
    static JPanel panel11 = new JPanel(new GridLayout(3, 1));

    static CardLayout cl = new CardLayout();

    //bill in machine
    static int bim10 = 10, bim20 = 10, bim50 = 10; 

    private static enum menu{
        scan,
        login,
        main,
        quick,
        costum,
        saldo,
        bon,
        end
    }

    private static menu currentScreen;

    private static final String withdraw_URL = "http://145.24.222.175:5000/withdraw";
    private static final String balance_URL = "http://145.24.222.175:5000/balance";
    private static final String login_URL = "http://145.24.222.175:5000/login";
    private static HttpClient client = HttpClient.newHttpClient();

    private static Arduino AdruinoCon = new Arduino("COM9", 9600);


    /*
     * GUI function 
     */
    public static void setPanelStuff(){
        panelCont.setLayout(cl);
        Font arial40 = new Font("Arial", Font.PLAIN, 40);
        Font arial25 = new Font("Arial", Font.PLAIN, 25);
        Font arial80 = new Font("Arial", Font.PLAIN, 80);

        button1.setFont(arial80);
        button1.setBackground(Color.DARK_GRAY);
        button1.setForeground(Color.WHITE);
        button2_1.setFont(arial40);
        button2_1.setBackground(Color.DARK_GRAY);
        button2_1.setForeground(Color.WHITE);
        button2_2.setFont(arial40);
        button2_2.setBackground(Color.DARK_GRAY);
        button2_2.setForeground(Color.WHITE);
        button2_3.setFont(arial40);
        button2_3.setBackground(Color.DARK_GRAY);
        button2_3.setForeground(Color.WHITE);
        button3_1.setFont(arial40);
        button3_1.setBackground(Color.DARK_GRAY);
        button3_1.setForeground(Color.WHITE);
        button3_2.setFont(arial40);
        button3_2.setBackground(Color.DARK_GRAY);
        button3_2.setForeground(Color.WHITE);
        button3_3.setFont(arial40);
        button3_3.setBackground(Color.DARK_GRAY);
        button3_3.setForeground(Color.WHITE);
        button3_4.setFont(arial40);
        button3_4.setBackground(Color.DARK_GRAY);
        button3_4.setForeground(Color.WHITE);
        button4_1.setFont(arial40);
        button4_1.setBackground(Color.DARK_GRAY);
        button4_1.setForeground(Color.WHITE);
        button4_2.setFont(arial40);
        button4_2.setBackground(Color.DARK_GRAY);
        button4_2.setForeground(Color.WHITE);
        button4_3.setFont(arial40);
        button4_3.setBackground(Color.DARK_GRAY);
        button4_3.setForeground(Color.WHITE);
        button4_4.setFont(arial40);
        button4_4.setBackground(Color.DARK_GRAY);
        button4_4.setForeground(Color.WHITE);
        button4_5.setFont(arial40);
        button4_5.setBackground(Color.DARK_GRAY);
        button4_5.setForeground(Color.WHITE);
        button4_6.setFont(arial40);
        button4_6.setBackground(Color.DARK_GRAY);
        button4_6.setForeground(Color.WHITE);
        button4_7.setFont(arial40);
        button4_7.setBackground(Color.DARK_GRAY);
        button4_7.setForeground(Color.WHITE);
        button4_8.setFont(arial40);
        button4_8.setBackground(Color.DARK_GRAY);
        button4_8.setForeground(Color.WHITE);
        button4_9.setFont(arial25);
        button4_9.setBackground(Color.DARK_GRAY);
        button4_9.setForeground(Color.WHITE);
        button5_1.setFont(arial40);
        button5_1.setBackground(Color.DARK_GRAY);
        button5_1.setForeground(Color.WHITE);
        button5_2.setFont(arial40);
        button5_2.setBackground(Color.DARK_GRAY);
        button5_2.setForeground(Color.WHITE);
        button6_1.setFont(arial40);
        button6_1.setBackground(Color.DARK_GRAY);
        button6_1.setForeground(Color.WHITE);
        button7_1.setFont(arial40);
        button7_1.setBackground(Color.DARK_GRAY);
        button7_1.setForeground(Color.WHITE);
        button7_2.setFont(arial40);
        button7_2.setBackground(Color.DARK_GRAY);
        button7_2.setForeground(Color.WHITE);
        button8_1.setFont(arial40);
        button8_1.setBackground(Color.DARK_GRAY);
        button8_1.setForeground(Color.WHITE);
        button9_1.setFont(arial40);
        button9_1.setBackground(Color.DARK_GRAY);
        button9_1.setForeground(Color.WHITE);
        button9_2.setFont(arial40);
        button9_2.setBackground(Color.DARK_GRAY);
        button9_2.setForeground(Color.WHITE);
        button10_1.setFont(arial40);
        button10_1.setBackground(Color.DARK_GRAY);
        button10_1.setForeground(Color.WHITE);
        button10_2.setFont(arial40);
        button10_2.setBackground(Color.DARK_GRAY);
        button10_2.setForeground(Color.WHITE);
        button11_1.setFont(arial40);
        button11_1.setBackground(Color.DARK_GRAY);
        button11_1.setForeground(Color.WHITE);
        button11_2.setFont(arial40);
        button11_2.setBackground(Color.DARK_GRAY);
        button11_2.setForeground(Color.WHITE);
        button11_3.setFont(arial40);
        button11_3.setBackground(Color.DARK_GRAY);
        button11_3.setForeground(Color.WHITE);

        panel1.add(button1);

        panel2.add(button2_1);
        panel2.add(button2_2);
        panel2.add(button2_3);

        panel3.add(button3_1);
        panel3.add(button3_2);
        panel3.add(button3_3);
        panel3.add(button3_4);

        panel4.add(button4_1);
        panel4.add(button4_2);
        panel4.add(button4_3);
        panel4.add(button4_4);
        panel4.add(button4_5);
        panel4.add(button4_6);
        panel4.add(button4_7);
        panel4.add(button4_8);
        panel4.add(button4_9);

        panel5.add(button5_1);
        panel5.add(button5_2);

        panel6.add(button6_1);

        panel7.add(button7_1);
        panel7.add(button7_2);

        panel8.add(button8_1);

        panel9.add(button9_1);
        panel9.add(button9_2);

        panel10.add(button10_1);
        panel10.add(button10_2);

        panel11.add(button11_1);
        panel11.add(button11_2);
        panel11.add(button11_3);

        panelCont.add(panel1, "scan");      // panel 1 = scan kaart
        panelCont.add(panel2, "login");     // panel 2 = verder/annuleren
        panelCont.add(panel3, "main");      // panel 3 = keuzemenu 
        panelCont.add(panel4, "quick");     // panel 4 = keuzemenu geld 
        panelCont.add(panel5, "saldo");     // panel 5 = terug / afsluiten
        panelCont.add(panel6, "printing");  // panel 6= geld komt er aan
        panelCont.add(panel7, "backToMenu");// panel 7 = wilt u terug naar het hoofd menu of afsluiten
        panelCont.add(panel8, "end");       // panel 8= fijne dag
        panelCont.add(panel9, "confirm");   // panel 9 = weet u het zeker, ja of nee
        panelCont.add(panel10, "bon");      // panel 10 = wilt u de bon
        panelCont.add(panel11, "costum");   // panel 11 = voer uw gewenste bedrag in

    }


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
    private static String postBalance(String IBAN) throws Exception{

        String inputs = ("/" + IBAN);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(balance_URL+inputs))
            .POST(HttpRequest.BodyPublishers.noBody())
            .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        // System.out.println(response.statusCode());
        System.out.println(response.body());

        return response.body();
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

    public static String getBalance(String iban){ //"/CH33SHIP0354400312"
        String temp1 = "", temp2 = "";

        try {
            temp1 = postBalance(iban);
            temp2 = String.valueOf(parseBalance(temp1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp2;
    }


    private static void newLogin(HttpResponse response) throws Exception {
        if(response.statusCode() == 403){ // geblokkeerd
            currentScreen = menu.end;
            System.out.print("get cucked");

        }else if(response.statusCode() == 401){// verkeerde pin
            System.out.println("verkeerde pin");
            sendCommand("PIN");

        }else if(response.statusCode() == 200){
            System.out.println("Pin succesvol");
            currentScreen = menu.main;
            //sendLogCommand(1);
        }
    }

    // private static menu navigate(String nav) throws Exception{
    //     menu toNav;
    //     if()


    // }



    /*
     * This is the serial interpreter, it receives the incomming information and interpretes it
     * to figure out which funtion the seponse is meant for
     */

    // private static void readSerial(String inputString) throws Exception {
         

    // }
    


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
        
        setPanelStuff();
        
        String inputString;
        int costumbil10 = 0, costumbil20 = 0, costumbil50 = 0;
        

        currentScreen = menu.scan;

        frame.add(panelCont);
        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720); // UI window size (create in the main)
        frame.setVisible(true); // set the UI visible (can also be into the void serialEvent)

        while(true){
            inputString = "";
            String ibanInUse = "";

            if(AdruinoCon.openConnection()){
                inputString = AdruinoCon.serialRead();
                
                System.out.println("received message:" + inputString);

                String inputSplit[] = inputString.substring(0).split(",");
                System.out.println(inputSplit[0]);
                //hier kunnen terug gestuurde commandos verwerkt worden
                if(inputSplit[0].equals("NEW")){
                    System.out.println("new pass");
                    ibanInUse = inputSplit[1];
                    currentScreen = menu.login;

                }else if(inputSplit[0].equals("PIN")){
                    //hier moet de code om de login ter verifieren of om terug te reageren
                    // postlogin, iban, pin            
                    HttpResponse response = postLogin(ibanInUse, Integer.parseInt(inputSplit[1]));
                    newLogin(response);

                }else if(inputSplit[0].equals("MON")){
                    costumAmount = inputSplit[1];
                    int costumInt = Integer.parseInt(costumAmount);
                    

                    while((costumInt % 50 > 0) && bim50 > 1){
                        bim50--;
                        costumbil50++;
                        costumInt-=50;
                    }
                    while((costumInt % 20 > 0) && bim20 > 1){
                        bim20--;
                        costumbil20++;
                        costumInt-=20;
                    }
                    while((costumInt % 10 > 0) && bim10 > 1){
                        bim10--;
                        costumbil10++;
                        costumInt-=10;
                    }
                    costumAmount = String.valueOf(costumbil50 + costumbil20 + costumbil10);


                }
            }

            switch(currentScreen){
                
                case scan:
                    cl.show(panelCont, "scan");
                    //wanneer er wordt ingelogt navigeer naar 
                    break;

                case login:
                    cl.show(panelCont, "login");
                    System.out.println("logging in");
                    sendCommand("PIN");
                    break;

                case main:
                    cl.show(panelCont, "main");
                    if(inputString.equals("1")){
                        currentScreen = menu.quick;
                    }
                    if(inputString.equals("2")){
                        currentScreen = menu.costum;
                    }
                    if(inputString.equals("3")){
                        saldo = getBalance(ibanInUse);

                        currentScreen = menu.saldo;
                    }
                    if(inputString.equals("B")){
                        currentScreen = menu.end;
                    }
                    break;

                case quick:
                    cl.show(panelCont, "quick");
                    if(inputString.equals("1")){
                        if(AdruinoCon.openConnection()){ 
                            if(bim20 > 1 ){
                                bim20--;
                                sendEurCommand(0,1,0);
                            }else if(bim10 > 2){
                                bim10 -= 2;
                                sendEurCommand(2,0,0);
                            }
                                   
                        }
                    }
                    if(inputString.equals("4")){
                        if(AdruinoCon.openConnection()){ 
                            if(bim50 > 1 ){
                                bim50--;
                                sendEurCommand(0,0,1);
                            }else if(bim20 > 2 && bim10 > 1){
                                bim20 -= 2;
                                bim10 -= 1;
                                sendEurCommand(1,2,0);
                            }       
                        }
                    }
                    if(inputString.equals("7")){
                        if(AdruinoCon.openConnection()){ 
                            if(bim50 > 2){
                                bim50 -= 2;
                                sendEurCommand(0,0,2); 
                            }        
                        }
                    }
                    if(inputString.equals("3")){
                        if(AdruinoCon.openConnection()){ 
                            if(bim50 > 4){
                                bim50 -= 4;
                                sendEurCommand(0,0,4); 
                            }       
                        }
                    }
                    if(inputString.equals("6")){
                        if(AdruinoCon.openConnection()){ 
                            if(bim50 > 6){
                                bim50 -= 2;
                                sendEurCommand(0,0,6); 
                            }       
                        }
                    }
                    if(inputString.equals("9")){
                        currentScreen = menu.costum;
                    }
                    if(inputString.equals("A")){
                        currentScreen = menu.main; 
                    }
                    break;

                case costum:
                    cl.show(panelCont, "costum");
                    
                    sendCommand("MON");
                    if(inputString.equals("C")){
                        sendCommand("MON");
                    }
                    if(inputString.equals("#")){
                        sendEurCommand(costumbil10, costumbil20, costumbil50);
                        currentScreen = menu.bon;
                    }
                    break;

                case saldo:
                    //pas de saldostring aan
                    cl.show(panelCont, "saldo");
                    if(inputString.equals("A")){
                        currentScreen = menu.main;
                    }
                    if(inputString.equals("B")){
                        currentScreen = menu.end;
                    }
                    break;

                case bon:
                    cl.show(panelCont, "bon");
                    if(inputString.equals("A")){
                        currentScreen = menu.end;
                        sendBonCommand(Integer.parseInt(costumAmount), ibanInUse);
                    }
                    if(inputString.equals("B")){
                        currentScreen = menu.end;
                    }
                    break;

                case end:
                    cl.show(panelCont, "end");
                    break;
            }
        }
    } 
}
