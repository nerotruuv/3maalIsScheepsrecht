/*------------------------------------------------------------------------
  Example sketch for Adafruit Thermal Printer library for Arduino.
  Demonstrates a few text styles & layouts, bitmap printing, etc.

  IMPORTANT: DECLARATIONS DIFFER FROM PRIOR VERSIONS OF THIS LIBRARY.
  This is to support newer & more board types, especially ones that don't
  support SoftwareSerial (e.g. Arduino Due).  You can pass any Stream
  (e.g. Serial1) to the printer constructor.  See notes below.
  ------------------------------------------------------------------------*/

#include "Adafruit_Thermal.h"
#include "sunk.h"
#include "adaqrcode.h"

// Here's the new syntax when using SoftwareSerial (e.g. Arduino Uno) ----
// If using hardware serial instead, comment out or remove these lines:

#include "SoftwareSerial.h"
#define TX_PIN 6 // Arduino transmit  YELLOW WIRE  labeled RX on printer
#define RX_PIN 5 // Arduino receive   GREEN WIRE   labeled TX on printer

SoftwareSerial mySerial(RX_PIN, TX_PIN); // Declare SoftwareSerial obj first
Adafruit_Thermal printer(&mySerial);     // Pass addr to printer constructor

char strbuf[30];

void setup() {

  pinMode(7, OUTPUT); digitalWrite(7, LOW);
  mySerial.begin(9600);  // Initialize SoftwareSerial
  printer.begin();        // Init printer (same regardless of serial type)


 
  //header
  printer.justify('C');
  printer.setSize('L'); 
  printer.println(F("3 Maal is\nScheepsrecht\n"));
  
  //logo is super traag
  //printer.printBitmap(adalogo_width, adalogo_height, adalogo_data);
  printer.setSize('M');
  printer.println(F("OPNAME BILJETTEN\n"));

  //time and date
  printer.setSize('S');
  printer.justify('L');
  printer.println(F("Datum\t\tTijd"));
  printer.println(F("23-03-22\t13:37\n"));

  //pinfo 
  sprintf(strbuf, "PAS NUMMER\t\t: *****%d",1234);
  printer.println(strbuf);
  sprintf(strbuf, "BEDRAG\t\t\t: EUR %d,00",70);
  printer.println(strbuf);
  
  printer.justify('C');
  printer.setSize('M');
  printer.println(F("\nTOT ZIENS"));
  
  printer.feed(2);

  printer.sleep();      // Tell printer to sleep
  delay(3000L);         // Sleep for 3 seconds
  printer.wake();       // MUST wake() before printing again, even if reset
  printer.setDefault(); // Restore printer to defaults
}

void loop() {
}
