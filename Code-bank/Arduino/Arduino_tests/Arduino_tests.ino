#include "Adafruit_Thermal.h"
#include "sunk.h"
#include "StringSplitter.h"

#include "SoftwareSerial.h"
#define TX_PIN A3 // Arduino transmit  YELLOW WIRE  labeled RX on printer
#define RX_PIN A4// Arduino receive   GREEN WIRE   labeled TX on printer
#define cashTime 20

#define motor1 20
#define motor2 21
#define motor3 22


SoftwareSerial mySerial(RX_PIN, TX_PIN); // Declare SoftwareSerial obj first
Adafruit_Thermal printer(&mySerial);     // Pass addr to printer constructor

String readString = "";
String inputArray[5];
char strbuf[30];

void setup() {
  Serial.begin(9600);
  pinMode(LED_BUILTIN, OUTPUT);
}

//  input binnenhalen (pin en IBAN), versturen naar java met PIN,pin,IBAN
//  en vervolgens wachte op nieuwe input
void checkLogin(int pin, String IBAN){
  
}

void cashMotor(int motorPin, int rotations){
  for(int i = 0; i < rotations; i++){
    digitalWrite(motorPin, HIGH);
    delay(cashTime);
    digitalWrite(motorPin, LOW);
  }
}

//bonprint funtie maken
void printCash(int bil1, int bil2, int bil3){  
  cashMotor(motor1, bil1);
  cashMotor(motor2, bil2);
  cashMotor(motor3, bil3);
}

//verweizing naar de bonprint functie
void printReceipt(int total, String IBAN){
  //header
  printer.justify('C');
  printer.setSize('L'); 
  printer.println(F("3 Maal is\nScheepsrecht\n"));
  
  //logo is super traag
  printer.printBitmap(adalogo_width, adalogo_height, adalogo_data);
  printer.setSize('M');
  printer.println(F("OPNAME BILJETTEN\n"));

  //time and date
  printer.setSize('S');
  printer.justify('L');
  printer.println(F("Datum\t\tTijd"));
  printer.println(F("23-03-22\t13:37\n"));

  //pinfo 
  sprintf(strbuf, "PAS NUMMER\t\t: %s",IBAN);
  printer.println(strbuf);
  sprintf(strbuf, "BEDRAG\t\t\t: EUR %d,00",total);
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


void functieParser(String input){
  String function = input.substring(0,2);
  StringSplitter *splitter;
  // LOG, PIN, IBAN
  if(function.equals("LOG")){
    splitter = new StringSplitter(readString, ',', 3);
    int pin = splitter->getItemAtIndex(1).toInt();
    String IBAN = splitter->getItemAtIndex(2);
    checkLogin(pin, IBAN);

  // EUR, BIL1, BIL2, BIL3
  }else if(function.equals("EUR")){
    splitter = new StringSplitter(readString, ',', 4);
    int BIL1 = splitter->getItemAtIndex(1).toInt();
    int BIL2 = splitter->getItemAtIndex(2).toInt();
    int BIL3 = splitter->getItemAtIndex(3).toInt();
    printCash(BIL1, BIL2, BIL3);

  // BON, TOTAL, IBAN
  }else if(function.equals("BON")){
    splitter = new StringSplitter(readString, ',', 3);
    int total = splitter->getItemAtIndex(1).toInt();
    String IBAN = splitter->getItemAtIndex(2);
    printReceipt(total, IBAN);
  
  }else{
    Serial.println("binnengekomen functie komt niet overeen");
  }
}

void loop() {
// voor onvangen
// readString = "";
//  while (Serial.available()) {
//    char c = Serial.read();  // current char from serial
//    readString += c; // add to string
//    delay(2);  //slow looping to allow buffer to fill with next character
//  }
//  
//  if (readString.length() >0) {
//    functieParser(readString);    
//  }

////verstuur test
//  Serial.print("PIN,1234,NL76RABO0354400312");
//  delay(2000);
}

//    String item = splitter->getItemAtIndex(4); //pak het 4e element van verzonde bericht
//    if(item == "0982"){
//      digitalWrite(LED_BUILTIN, HIGH);   // turn the LED on (HIGH is the voltage level)
//      delay(1000);                       // wait for a second
//      digitalWrite(LED_BUILTIN, LOW);    // turn the LED off by making the voltage LOW
//      delay(1000); 
