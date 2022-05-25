import com.fazecast.jSerialComm.*;
import com.mysql.cj.xdevapi.Client;


import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.util.Scanner;


import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.URI;


public class Link_test {
    static String ten = "abc";
    static String twenty = "abc";
    static String fifty = "abc";
    static String hundred = "abc";
    static String twohundred = "abc";
    static boolean received;
    static boolean test1 = true;
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

    static JButton button5_1 = new JButton("'Saldo'");
    static JButton button5_2 = new JButton("[A] Terug                                [B] Afsluiten");

    static JButton button6_1 = new JButton("Uw geld komt eraan.");

    static JButton button7_1 = new JButton("Wilt u terug naar hoofdmenu of afsluiten?");
    static JButton button7_2 = new JButton("[A] Terug                                [B] Afsluiten");

    static JButton button8_1 = new JButton("[*] Fijne dag!");

    static JButton button9_1 = new JButton("Weet u het zeker?");
    static JButton button9_2 = new JButton("[A] Ja                                [B] Nee");

    static JPanel panelCont = new JPanel();
    static JPanel panel1 = new JPanel(new GridLayout(1, 1));
    static JPanel panel2 = new JPanel(new GridLayout(3, 1));
    static JPanel panel3 = new JPanel(new GridLayout(2, 2));

    static JPanel panel4 = new JPanel(new GridLayout(3, 2));
    static JPanel panel5 = new JPanel(new GridLayout(2, 1));
    static JPanel panel6 = new JPanel(new GridLayout(1, 1));
    static JPanel panel7 = new JPanel(new GridLayout(2, 1));
    static JPanel panel8 = new JPanel(new GridLayout(2, 1));
    static JPanel panel9 = new JPanel(new GridLayout(2, 1));

    static CardLayout cl = new CardLayout();

    static int bt = 3; // Set text of individual buttons with Arduino (reserved for later)
    static int p = 1;
    static String SQLtest = "abc";
    static String SQLtest2 = "abc";
    static String SQLmoney = "0";

    public static int moneytransfer = 0;
    public static String moneydb = "0";
    public static boolean print = false;

    

    public void request(String uri) throws Exception {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .build();
    
        HttpResponse<String> response =
                client.send(request, BodyHandler.asString());
    
        System.out.println(response.body());
    }

    public void withdrawURL(String account, int amount){
        
        request()
    }

    public void balanceURL(String account){
        
        request()
    }



    public static void main(String[] args) throws Exception {

        Startup s1 = new Startup();
        Thread thread1 = new Thread(s1);
        thread1.start();
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

        panelCont.add(panel1, "1");
        panelCont.add(panel2, "2");
        panelCont.add(panel3, "3");
        panelCont.add(panel4, "4");
        panelCont.add(panel5, "5");
        panelCont.add(panel6, "6");
        panelCont.add(panel7, "7");
        panelCont.add(panel8, "8");
        panelCont.add(panel9, "9");
        cl.show(panelCont, "1");

        frame.add(panelCont);
        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720); // UI window size (create in the main)
        frame.setVisible(true); // set the UI visible (can also be into the void serialEvent)

        SerialPort port = SerialPort.getCommPort("COM4");
        port.setComPortParameters(9600, 8, 1, 0);
        port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        System.out.println("Open port: " + port.openPort());
        Scanner in = new Scanner(port.getInputStream());
        PrintWriter out = new PrintWriter(port.getOutputStream(), true);
        port.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent serialPortEvent) {
                String input = "";
                String str1 = "";
                String str2 = "";
                String str_switch = "";
                String str_cash = "";
                int cash = 0;
                int intmoney = Integer.parseInt(SQLmoney);

                input = in.nextLine(); // text from Arduino will be stored in here

                str1 = input;

                System.out.println("return: " + input);
                received = true;

                if (input.contains("access")) {
                    p = 2;
                    button1.setText("");
                }

                if (input.contains("Correcte pincode")) {
                    p = 3;
                    button2_1.setText("");
                }

                if (input.contains("Annuleren 2")) {
                    p = 1;
                    button1.setText("Scan uw kaart");

                }

                if (input.contains("Snel 70 pinnen")) {
                    p = 9;
                }

                if (input.contains("Ander bedrag pinnen")) {
                    p = 4;
                }

                if (input.contains("Saldo bekijken")) {
                    p = 5;
                }

                if (input.contains("0 EUR")) {
                    p = 9;
                }

                if (input.contains("Ander bedrag invoeren")) {
                    p = 7;
                }

                if (input.contains("Terug 3")) {
                    p = 2;
                    button2_1.setText(" ");
                }

                if (input.contains("Afsluiten 3")) {
                    p = 1;
                    button1.setText("Scan uw kaart");
                }
                if (input.contains("Terug 5")) {
                    p = 3;
                    button2_1.setText(" ");
                }
                if (input.contains("Terug 6")) {
                    p = 3;
                    button2_1.setText(" ");
                }
                if (input.contains("Afsluiten 5")) {
                    p = 1;
                    button1.setText("Scan uw kaart");
                }
                if (input.contains("Ja 9")) {
                    p = 6;
                }
                if (input.contains("Nee 9")) {
                    p = 3;
                }

                if (input.contains("*")) {
                    button2_1.setText(str1);
                }

                if (input.contains("accept")) {
                    button2_3.setText("");
                }

                if (input.contains("switch")) {
                    str_switch = input.substring(0, input.length() - 7);
                    p = Integer.parseInt(str_switch);
                }

                if (input.contains("cash")) {
                    str_cash = input.substring(0, input.length() - 5);
                    cash = Integer.parseInt(str_cash);

                    if (cash > intmoney) {
                        button5_1.setText("Onvoldoende saldo.");

                    } else {
                        button5_1.setText("Uw geld komt eraan.");
                        moneytransfer = cash;
                        intmoney = intmoney - cash;
                        moneydb = Integer.toString(intmoney);
                        print = true;
                        System.out.println(moneytransfer);
                        System.out.println(print);
                        try {
                        } catch (Exception e) {
                            System.out.println("Epic fail");
                        }

                    }
                }

                switch (bt) {
                    case 1:
                        button1.setText(str1);
                        break;

                    case 2:
                        str2 = input.substring(0, input.length() - 6);
                        button2_1.setText(str2);
                        break;
                }

                switch (p) {
                    case 1:
                        cl.show(panelCont, "1");
                        break;
                    case 2:
                        cl.show(panelCont, "2");
                        break;
                    case 3:
                        cl.show(panelCont, "3");
                        break;
                    case 4:
                        cl.show(panelCont, "4");
                        break;
                    case 5:
                        cl.show(panelCont, "5");
                        try {
                        } catch (Exception e) {
                            System.out.println("Epic fail");
                        }
                        break;
                    case 6:
                        cl.show(panelCont, "6");
                        break;
                    case 7:
                        cl.show(panelCont, "7");
                        break;
                    case 8:
                        cl.show(panelCont, "8");
                        break;
                    case 9:
                        cl.show(panelCont, "9");
                        break;
                }

            }
        });

        int counter = 0;
        while (!received) {
            System.out.println(counter);
            out.println(counter);
            out.flush();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter++;
        }
    }
}