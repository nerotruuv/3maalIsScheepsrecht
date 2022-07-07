import javax.swing.*;

import java.awt.*;

public class Test {
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

    static JButton button5_1 = new JButton(); // show de balance
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
    static JButton button11_2 = new JButton("");
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

    // static int bt = 3; // Set text of individual buttons with Arduino (reserved for later)

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

        panelCont.add(panel1, "scan");
        panelCont.add(panel2, "2");
        panelCont.add(panel3, "3");
        panelCont.add(panel4, "4");
        panelCont.add(panel5, "5");
        panelCont.add(panel6, "6");
        panelCont.add(panel7, "7");
        panelCont.add(panel8, "8");
        panelCont.add(panel9, "9");
        panelCont.add(panel10, "10");
        panelCont.add(panel11, "11");

    }

    public static void main(String[] args) throws Exception {

        setPanelStuff();
        cl.show(panelCont, "7");
        
        // panel 1 = scan kaart
        // panel 2 = verder/annuleren
        // panel 3 = keuzemenu 
        // panel 4 = keuzemenu geld 
        // panel 5 = terug / afsluiten
        // panel 6= geld komt er aan 
        // panel 7 = wilt u terug naar het hoofd menu of afsluiten
        // panel 8= fijne dag
        // panel 9 = weet u het zeker, ja of nee
        // panel 10 = wilt u de bon
        // panel 11 = voer uw gewenste bedrag in

        frame.add(panelCont);
        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720); // UI window size (create in the main)
        frame.setVisible(true); // set the UI visible (can also be into the void serialEvent)

    }
}
