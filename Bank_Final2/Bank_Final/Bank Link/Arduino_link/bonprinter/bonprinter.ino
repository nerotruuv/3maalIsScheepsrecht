#include "Adafruit_Thermal.h"
#include "potKlein.h"
#include <AccelStepper.h>

#define motorPinA1 2
#define motorPinA2 3
#define motorPinA3 4
#define motorPinA4 5

#define motorPinB1 7
#define motorPinB2 8
#define motorPinB3 9
#define motorPinB4 10
boolean randomwaarde= false;


int stepsPerRevolution = 64;
float degreePerRevolution = 5.625;

long biljetTwin = 0;
long biljetTien = 0;

int geld_waarde;
int geld_waarde2;
int aantaltwin;
int aantaltien;


String naam = "AlphaTest";
String userId = "AlphaTest";
String datum = "03-06-2021" ;
int geldWaarde = 300;

AccelStepper stepper1(AccelStepper::HALF4WIRE, motorPinA1, motorPinA3, motorPinA2, motorPinA4);
AccelStepper stepper2(AccelStepper::HALF4WIRE, motorPinB1, motorPinB3, motorPinB2, motorPinB4);

#include "SoftwareSerial.h"
#define TX_PIN 11 // Arduino transmit  YELLOW WIRE  labeled RX on printer
#define RX_PIN 12 // Arduino receive   GREEN WIRE   labeled TX on printer

SoftwareSerial mySerial(RX_PIN, TX_PIN); // Declare SoftwareSerial obj first
Adafruit_Thermal printer(&mySerial);     // Pass addr to printer constructor
// Then see setup() function regarding serial & printer begin() calls.

// Here's the syntax for hardware serial (e.g. Arduino Due) --------------
// Un-comment the following line if using hardware serial:

//Adafruit_Thermal printer(&Serial1);      // Or Serial2, Serial3, etc.

// -----------------------------------------------------------------------

    float degToSteps(float deg) {
    return (stepsPerRevolution / degreePerRevolution)* deg;
  }

void setup() {

  Serial.begin(9600); // used for printing to serial monitor of the Arduino IDE
  //sserial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. 
  }

  stepper1.setMaxSpeed(3000.0);      // stel de maximale motorsnelheid in
  stepper1.setAcceleration(50.0);   // stel de acceleratie in
  stepper1.setSpeed(200); 

  stepper2.setMaxSpeed(3000.0);      // stel de maximale motorsnelheid in
  stepper2.setAcceleration(50.0);   // stel de acceleratie in
  stepper2.setSpeed(200); 

 
 

}

void loop() {


   
  
    if (Serial.available() > 0) {
    byte incomingByte = 0;
    byte moneyByte = 0;
    incomingByte = Serial.read();
    if (incomingByte != -1) {
      Serial.print("I received: "); // print out to serial monitor
      Serial.println(incomingByte); // print out to serial monitor
      moneyByte = incomingByte;
    }
    
  if(moneyByte >= 4){
    geldWaarde = moneyByte;
    geld_waarde = moneyByte;
   pinMode(7, OUTPUT); digitalWrite(7, LOW);
 
   aantaltwin = geld_waarde  / 20;
   geld_waarde2 = geld_waarde % 20 ;
   aantaltien = geld_waarde2 / 10;
   biljetTwin = aantaltwin * 760;
   biljetTien = aantaltien * 760;
  
  // NOTE: SOME PRINTERS NEED 9600 BAUD instead of 19200, check test page.
  mySerial.begin(9600);  // Initialize SoftwareSerial
  //Serial1.begin(19200); // Use this instead if using hardware serial
  printer.begin();        // Init printer (same regardless of serial type)

  // The following calls are in setup(), but don't *need* to be.  Use them
  // anywhere!  They're just here so they run one time and are not printed
  // over and over (which would happen if they were in loop() instead).
  // Some functions will feed a line when called, this is normal.

  printer.wake();       // MUST wake() before printing again, even if reset
  printer.setSize('L');        // Set type size, accepts 'S', 'M', 'L'
  printer.println(F("Bank of gold"));
printer.feed(1);

    
  printer.setSize('S');
  printer.print(F("user ID :"));
  printer.println(userId);
  
  printer.print(F("Naam :"));
  printer.println(naam);
  
  printer.print(F("transactie datum: "));
  printer.println(datum);
  
  printer.print(F("transactie waarde :"));
    printer.print(F("$"));
  printer.println(geldWaarde);
  printer.feed(1);
  printer.justify('C');
  printer.printBitmap(80, 69, pot);
//  printer.setSize('L');
//  printer.println(F(" "));
  printer.feed(2);
  printer.sleep();      // Tell printer to sleep
  

  
  }
  }

   Serial.println(biljetTwin);
   Serial.println(biljetTien);
   Serial.println(aantaltwin);
   Serial.println(aantaltien);
 
    stepper1.moveTo(degToSteps(biljetTwin));
    stepper1.run();
    
    stepper2.moveTo(degToSteps(biljetTien));
    stepper2.run();

      geld_waarde = 0;
      geld_waarde2 = 0;
      aantaltwin = 0;
      aantaltien = 0;

}
