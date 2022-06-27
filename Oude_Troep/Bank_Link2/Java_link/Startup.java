package Bank_Link2.Java_link;

import com.fazecast.jSerialComm.SerialPort;

/**
 * Simple application that is part of an tutorial. 
 * The tutorial shows how to establish a serial connection between a Java and Arduino program with the help of an USB-to-TTL Module.
 * @author Michael Schoeffler (www.mschoeffler.de)
 *
 */
public class Startup implements Runnable {

    //throws IOException, InterruptedException

    public void run() {
        try {

        SerialPort sp = SerialPort.getCommPort("COM13");
        sp.setComPortParameters(9600, 8, 1, 0);
        sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);

        if (sp.openPort()) {
            System.out.println("Port is open :)");
        } else {
            System.out.println("Failed to open port :(");
            return;
        }
        Integer i;

            while (true) {
            Thread.sleep(500);
            if (Link_test.print == true) {
            i = Link_test.moneytransfer;
            sp.getOutputStream().write(i.byteValue());
            sp.getOutputStream().flush();
            System.out.println("Sent number: " + i);
            Link_test.print = false;
            Link_test.moneytransfer = 0;
            }

        if (sp.closePort()) {
            System.out.println("Port is closed :)");
        } else {
            System.out.println("Failed to close port :(");
            return;
        } 

    }

    } catch (Exception e) {
        System.out.println("Epic fail");
    }


    }
}
