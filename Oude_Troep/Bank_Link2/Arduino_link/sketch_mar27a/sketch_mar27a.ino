#include "StringSplitter.h"

//LOG, EUR, BON
enum {login, cashout, receipt}functie;

String readString = "";
String inputArray[5];

void setup() {
  Serial.begin(9600);
  pinMode(LED_BUILTIN, OUTPUT);
}

enum functie getFunction(String sub){
  enum functie funct;
  String temp = sub.substring(0,2);
  switch(temp){
    case "LOG":
      funct = login;
      return funct;
      break;
    case "EUR":
      funct = cashout;
      return funct;
      break;
    case "BON":
      funct = receipt;
      return funct;
      break;
   default:
     Serial.println("binnengekomen functie komt niet overeen");
     break;
  }
  return temp.toInt();
}

//input even nog binnenhalen ergens
void checkLogin(int input, int pin){
  
}

//bonprint funtie maken
void printCash(int bil1, int bil2, int bil3){
  
}

//verweizing naar de bonprint functie
void printReceipt(int total, String iban){
  
}


void functieParser(String input){
  functie = getFunction(input);
  StringSplitter *splitter;
  switch(functie){
    // LOG, PIN, IBAN
    case login:
      splitter = new StringSplitter(readString, ',', 3);
      int pin = splitter->getItemAtIndex(1).toInt();
      //checklogin(input, pin);
      break;

    // EUR, BIL1, BIL2, BIL3
    case cashout:
      splitter = new StringSplitter(readString, ',', 4);
      int BIL1 = splitter->getItemAtIndex(1).toInt();
      int BIL2 = splitter->getItemAtIndex(2).toInt();
      int BIL3 = splitter->getItemAtIndex(3).toInt();
      printCash(BIL1, BIL2, BIL3);
      break;

    // BON, TOTAL, IBAN
    case receipt:
      splitter = new StringSplitter(readString, ',', 3);
      
      break;
      
    default:
      Serial.println("binnengekomen functie komt niet overeen");
      break
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
    //split de string in een stringsplitter
    //StringSplitter *splitter = new StringSplitter(readString, '-', 5);
    // pinbedrag, biljet1, biljet2, biljet3, 4 cijfers accountnummer
    
    String item = splitter->getItemAtIndex(4); //pak het 4e element van verzonde bericht
    if(item == "0982"){
      digitalWrite(LED_BUILTIN, HIGH);   // turn the LED on (HIGH is the voltage level)
      delay(1000);                       // wait for a second
      digitalWrite(LED_BUILTIN, LOW);    // turn the LED off by making the voltage LOW
      delay(1000); 
    }
  }
}
