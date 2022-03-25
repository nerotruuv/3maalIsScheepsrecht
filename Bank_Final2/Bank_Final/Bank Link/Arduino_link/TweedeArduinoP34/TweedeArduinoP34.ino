#include <SoftwareSerial.h>
 
SoftwareSerial sserial(5,6); // receive pin (used), transmit pin (unused)
int led = 13;

void setup() {
  Serial.begin(9600); // used for printing to serial monitor of the Arduino IDE
  //sserial.begin(9600);
  pinMode(led, OUTPUT); 
  while (!Serial) {
    ; // wait for serial port to connect. 
  }
}

void loop() {
  if (Serial.available() > 0) {
    byte incomingByte = 0;
    byte test = 3;
    incomingByte = Serial.read();
    if (incomingByte != -1) {
      Serial.print("I received: "); // print out to serial monitor
      Serial.println(incomingByte); // print out to serial monitor
    }

    if (incomingByte == 3) {
      digitalWrite(led, HIGH);   // turn the LED on (HIGH is the voltage level)
      delay(1000);               // wait for a second
      digitalWrite(led, LOW);    // turn the LED off by making the voltage LOW
      delay(1000);
    }
  }
}
