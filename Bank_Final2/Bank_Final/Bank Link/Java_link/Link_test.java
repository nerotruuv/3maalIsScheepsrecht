import com.fazecast.jSerialComm.*;
import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.util.Scanner;


public class Link_test {
    static String ten = "abc";
    static String twenty = "abc";
    static String fifty = "abc";
    static String hundred = "abc";
    static String twohundred = "abc";
    static boolean received;
    static boolean test1 = true;
    static JFrame frame = new JFrame("My First GUI"); //UI window creation
    static JButton button1 = new JButton("Scan uw kaart");
    static JButton button2 = new JButton("");
    static JButton button2_2 = new JButton("[*] Annuleren                                [#] Verder");

    static JButton button3 = new JButton("");
    static JButton button4 = new JButton("[1] 10 EUR");
    static JButton button5 = new JButton("");
    static JButton button6 = new JButton("[3] 100 EUR");
    static JButton button7 = new JButton("[4] 20 EUR");
    static JButton button8 = new JButton("");
    static JButton button9 = new JButton("[6] 200 EUR");
    static JButton button10 = new JButton("[7] 50 EUR");
    static JButton button11 = new JButton("[*] Terug");
    static JButton button12 = new JButton("[9] 300 EUR");

    static JButton button13 = new JButton("[1] Snel 70 EUR pinnen");
    static JButton button14 = new JButton("[3]Ander bedrag pinnen");
    static JButton button15 = new JButton("[7]Saldo bekijken");
    static JButton button16 = new JButton("[*]Annuleren");

    static JButton button17 = new JButton("Gegevens page");
    static JButton button18 = new JButton("[*] Terug");
    static JButton button19 = new JButton("Uw geld komt eraan.");
    static JButton button20 = new JButton("Wilt u terug naar hoofdmenu of afsluiten?");
    static JButton button21 = new JButton("[*] Terug                                [#] Afsluiten");
    static JButton button22 = new JButton("[*] Test panel 8                                [#] Test panel 8");

    static JPanel panelCont = new JPanel();
    static JPanel panel1 = new JPanel(new GridLayout(1,1));
    static JPanel panel2 = new JPanel(new GridLayout(3,1));
    static JPanel panel3 = new JPanel(new GridLayout(3,3));
    static JPanel panel4 = new JPanel(new GridLayout(2,2));
    static JPanel panel5 = new JPanel(new GridLayout(2,1));
    static JPanel panel6 = new JPanel(new GridLayout(1,1));
    static JPanel panel7 = new JPanel(new GridLayout(2,1));
    static JPanel panel8 = new JPanel(new GridLayout(2,1));

    static CardLayout cl = new CardLayout();

    static int bt = 3; //Set text of individual buttons with Arduino (reserved for later)
    static int p = 1;
    static String SQLtest = "abc";
    static String SQLtest2 = "abc";
    static String SQLmoney = "0";

    public static int moneytransfer = 0;
    public static String moneydb = "0";
    public static boolean print = false;

    
    public static void main(String[] args) throws Exception {
        Startup s1 = new Startup();
        Thread thread1 = new Thread(s1);
        thread1.start();
        panelCont.setLayout(cl);
        Font arial40 = new Font("Arial", Font.PLAIN, 40);
        Font arial80 = new Font("Arial", Font.PLAIN, 80);
        button1.setFont(arial40);
        button2.setFont(arial40);
        button2_2.setFont(arial40);
        button3.setFont(arial80);
        button4.setFont(arial40);
        button5.setFont(arial40);
        button6.setFont(arial40);
        button7.setFont(arial40);
        button8.setFont(arial40);
        button9.setFont(arial40);
        button10.setFont(arial40);
        button11.setFont(arial40);
        button12.setFont(arial40);
        button13.setFont(arial40);
        button14.setFont(arial40);
        button15.setFont(arial40);
        button16.setFont(arial40);
        button17.setFont(arial40);
        button18.setFont(arial40);
        button19.setFont(arial40);
        button20.setFont(arial40);
        button21.setFont(arial40);
        button22.setFont(arial40);

        panel1.add(button1);

        panel2.add(button2);
        panel2.add(button3);
        panel2.add(button2_2);

        panel3.add(button4);
        panel3.add(button5);
        panel3.add(button6);
        panel3.add(button7);
        panel3.add(button8);
        panel3.add(button9);
        panel3.add(button10);
        panel3.add(button11);
        panel3.add(button12);

        panel4.add(button13);
        panel4.add(button14);
        panel4.add(button15);
        panel4.add(button16);

        panel5.add(button17);
        panel5.add(button18);

        panel6.add(button19);

        panel7.add(button20);
        panel7.add(button21);
        
        panel8.add(button22);

        panelCont.add(panel1, "1");
        panelCont.add(panel2, "2");
        panelCont.add(panel3, "3");
        panelCont.add(panel4, "4");
        panelCont.add(panel5, "5");
        panelCont.add(panel6, "6");
        panelCont.add(panel7, "7");
        panelCont.add(panel8, "8");
        cl.show(panelCont, "1");

        frame.add(panelCont);
        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(100,100); //UI window size (create in the main)
        frame.setVisible(true); //set the UI visible (can also be into the void serialEvent)

        SerialPort port = SerialPort.getCommPort("COM5");
        port.setComPortParameters(9600,8,1,0);
        port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER,0,0);
        System.out.println("Open port: " + port.openPort());
        Scanner in = new Scanner(port.getInputStream());
        PrintWriter out = new PrintWriter(port.getOutputStream(),true);
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

                input = in.nextLine();  //text from Arduino will be stored in here

                str1 = input;

                System.out.println("return: " + input);
                received=true;

                if (input.contains("Pincode")){
                    p = 2;
                    button2.setText(str1);
                }

                if (input.contains("*")){
                    button3.setText(str1);
                }

                if (input.contains("accept")){
                    button3.setText("");
                }

                    if (input.contains("switch")){
                        str_switch = input.substring(0, input.length() - 7);
                        p = Integer.parseInt(str_switch);
                    } 

                    if (input.contains("cash")){
                        str_cash = input.substring(0, input.length() - 5);
                        cash = Integer.parseInt(str_cash);

                        if (cash > intmoney){
                            button19.setText("Onvoldoende saldo.");


                        } else {
                            button19.setText("Uw geld komt eraan.");
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

                    switch (bt){
                        case 1:
                        button1.setText(str1);
                        break;

                        case 2:
                        str2 = input.substring(0, input.length() - 6);
                        button2.setText(str2);
                        break;
                    }

                    switch (p){
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
                    }



                
            }
        });

        

int counter =0;
        while(!received) {
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