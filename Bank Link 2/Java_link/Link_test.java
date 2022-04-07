import com.fazecast.jSerialComm.*;
import javax.swing.*;
import java.awt.*;

import java.io.PrintWriter;
import java.util.Scanner;


public class Link_test {
    static boolean received;
    static boolean test1 = true;
    static JFrame frame = new JFrame("My First GUI"); //UI window creation
    static JButton button = new JButton(); //Button variable creation
    static JMenuBar mb = new JMenuBar();
    static JMenu m1 = new JMenu();
    static JMenu m2 = new JMenu("Help");
    static JMenuItem m11 = new JMenuItem("Open");
    static JMenuItem m22 = new JMenuItem("Save as");

    public static void main(String[] args) {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600); //UI window size (create in the main)
        frame.getContentPane().add(button); //add a button to the UI (can also be put into the void serialEvent)
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.setVisible(true); //set the UI visible (can also be into the void serialEvent)
        mb.add(m1);
        mb.add(m2);
        m1.add(m11);
        m1.add(m22);

        SerialPort port = SerialPort.getCommPort("COM3");
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
                String str = "";
                int m = 2;

                input = in.nextLine();  //text from Arduino will be stored in here

                System.out.println("return: " + input);
                received=true;

                    if (input.contains("money")){
                        m = 3;
                    } else {
                        m = 2;
                    }


                    if (m == 2){
                    button.setText(input); //Changes the text of the button (set into void serialEvent for it to update to what Arduino sends)
                    }

                    if (m == 3){
                    str = input.substring(0, input.length() - 6);
                    m1.setText(str);
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