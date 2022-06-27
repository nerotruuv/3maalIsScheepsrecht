#include "sunk.h"
#include "StringSplitter.h"

String readString = "";
String inputArray[5];

void setup() {
  Serial.begin(9600);
  pinMode(LED_BUILTIN, OUTPUT);
}


//input even nog binnenhalen ergens
void checkLogin(int pin, String IBAN){
  
}

//bonprint funtie maken
void printCash(int bil1, int bil2, int bil3){
  
}

//verweizing naar de bonprint functie
void printReceipt(int total, String IBAN){
  
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
 readString = "";
  while (Serial.available()) {
    char c = Serial.read();  // current char from serial
    readString += c; // add to string
    delay(2);  //slow looping to allow buffer to fill with next character
  }
  
  if (readString.length() >0) {
    functieParser(readString);    
  }
}

//    String item = splitter->getItemAtIndex(4); //pak het 4e element van verzonde bericht
//    if(item == "0982"){
//      digitalWrite(LED_BUILTIN, HIGH);   // turn the LED on (HIGH is the voltage level)
//      delay(1000);                       // wait for a second
//      digitalWrite(LED_BUILTIN, LOW);    // turn the LED off by making the voltage LOW
//      delay(1000); 
