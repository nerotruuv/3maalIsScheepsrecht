#include <SPI.h>
#include <MFRC522.h>
#include <Keypad.h>
#include <AccelStepper.h>


#include "Adafruit_Thermal.h"
#include "sunk.h"
#include "adaqrcode.h"

#include "SoftwareSerial.h"
#define TX_PIN A3 // Arduino transmit  YELLOW WIRE  labeled RX on printer
#define RX_PIN A4 // Arduino receive   GREEN WIRE   labeled TX on printer


#define motorPin A4



SoftwareSerial mySerial(RX_PIN, TX_PIN); // Declare SoftwareSerial obj first
Adafruit_Thermal printer(&mySerial);     // Pass addr to printer constructor

char strbuf[30];

typedef enum {
  State_Card,
  State_Pin,
  State_Panel3,
  State_Panel4,
  State_Panel5,
  State_Panel6,
  State_Panel7,
  State_Panel8,
  State_Panel9,
  State_Panel10,
  State_Panel11,
  State_Panel12
  
}
current_state;
current_state state;
current_state key;

enum menu
{
pin = 1,
main = 2,
quick = 3,
balance = 4,
pinnen = 5,
exit_menu = 6
};
enum menu current_menu;


#define SS_PIN 10
#define RST_PIN 9
 

MFRC522 mfrc522(SS_PIN, RST_PIN);

const byte ROWS = 4;
const byte COLS = 4;
char hexaKeys[ROWS][COLS] = {
  {'1', '2', '3', 'A'},
  {'4', '5', '6', 'B'},
  {'7', '8', '9', 'C'},
  {'*', '0', '#', 'D'}
};
byte rowPins[ROWS] = {8, 7, 6, 5};
byte colPins[COLS] = {4, 3, 2, A5};
Keypad customKeypad = Keypad(makeKeymap(hexaKeys), rowPins, colPins, ROWS, COLS);

const String pincode;
String input_pincode;
int input_bedrag;
int wrong = 0;
int counter = 0;
int bedrag = 0;
void setup() {
  Serial.begin(9600);
  SPI.begin();
  mfrc522.PCD_Init();
  Serial.println("Scan your card");
  input_pincode.reserve(4);                                   //Maximale input van 32 characters voordat je niks meer kan invullen.
  pinMode(7, OUTPUT); digitalWrite(7, LOW);
  printer.begin(); // Init printer (same regardless of serial type)
}

void printBon(int printbedrag, int pasNr, int saldo){
          pinMode(7, OUTPUT); digitalWrite(7, LOW);
          mySerial.begin(9600);  // Initialize SoftwareSerial
          printer.begin();        // Init printer (same regardless of serial type)
        
        
         
          //header
          printer.justify('C');
          printer.setSize('L'); 
          printer.println(F("3 Maal is\nScheepsrecht\n"));
          
          //logo is super traag
//          printer.printBitmap(adalogo_width, adalogo_height, adalogo_data);
  //        printer.setSize('M');
    //      printer.println(F("OPNAME BILJETTEN\n"));
        
          //time and date
          printer.setSize('S');
          printer.justify('L');
          printer.println(F("Datum\t\tTijd"));
          printer.println(F("23-03-22\t13:37\n"));
        
          //pinfo 
          sprintf(strbuf, "PAS NUMMER\t\t: *****%d",1234);
          printer.println(strbuf);
          sprintf(strbuf, "BEDRAG\t\t\t: EUR %d,00",bedrag);
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
  switch (state) {
    case State_Card: {
      if ( ! mfrc522.PICC_IsNewCardPresent()) {
        return;
      }
      if ( ! mfrc522.PICC_ReadCardSerial()) {
        return;
      }
      Serial.print("UID tag:");
      String content = "";
      byte letter;
      for (byte i = 0; i < mfrc522.uid.size; i++) {
         Serial.print(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " ");
         Serial.print(mfrc522.uid.uidByte[i], HEX);
         content.concat(String(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " "));
         content.concat(String(mfrc522.uid.uidByte[i], HEX));
      }
      Serial.println();
      content.toUpperCase();
      if (content.substring(1) == "A2 E1 75 E9"||"09 77 E8 C1") {
        pincode = "1234";                                        //Wanneer deze pas wordt herkend geeft moet de wachtwoord 1234 worden ingevuld.
        
        Serial.println("Authorized access");
        Serial.println("Enter pincode: ");
        String content = "";
        state = State_Pin;                                          //Als de pas wordt geaccepteerd mag die zijn pincode invullen.
      }
      else {
        Serial.println("Access denied");                            //Bij een verkeerde pas geeft die dit aan. (Er kan geen pincode ingevoerd worden.) 
      }
    }
    break;
    case State_Pin: {
      char pin = customKeypad.getKey();
      if (pin) {          
         
        if (pin != '#' && pin != 'A') {
          Serial.println('*');                                        //Als er een pin wordt ingetoets komt er een * in zijn plaats te staan. Verander ("*") naar (pin) om de getallen te kunnen zien.
        }
        if (pin == 'A') {
          input_pincode = "";
          Serial.println("Annuleren 2");
          Serial.println("qwerty");
          state = State_Card;
        }
        
        else if (pin == '#') {
          counter++;                                                  //Telt hoevaak er is geprobeerd om in te loggen.
          Serial.print("COUNT: ");
          Serial.println(counter);
          if (pincode == input_pincode) {
            Serial.println("Correcte pincode");
            Serial.println("qwerty");
            wrong = 0;                                                //Als het wachtwoord klopt wordt de teller van foute wachtwoorden terug naar 0 gezet.
            counter = 0;
            input_pincode = "";
            state = State_Panel3;
          }
          else if (pincode != input_pincode) {
            wrong++;                                                  //Telt hoevaak het wachtwoord verkeerd is ingevuld.
          }
          if (wrong == 1) {
            Serial.println("Incorrect pincode, 2 tries left!");
          }
          else if (wrong == 2) {
            Serial.println("Incorrect pincode, 1 try left!");
          }
          else if (wrong == 3) {
            Serial.println("Card is blocked");                         //Bij 3 foutmeldingen wordt de pas 'geblokkeerd'. (Nu wordt je alleen uitgelogd.)
            state = State_Card;
          }
        }
        else {
          input_pincode += pin;
        }
      }
     }
    

    break;
    case State_Panel3: {
      char pin = customKeypad.getKey();
      if (pin) {
        if (pin == '1'){
          Serial.println("Snel 70 pinnen");
          bedrag = 70;
          state = State_Panel9;
        }
        else if (pin == '2') {
          Serial.println("Ander bedrag pinnen");
          state = State_Panel4;
        }
        else if (pin == '3') {
          Serial.println("Saldo bekijken");
          state = State_Panel5;
        }
        else if (pin == 'B') {
          Serial.println("Afsluiten 3");
          state = State_Card;
          return loop();
        }
        else {
        }
      }
    }
    
{
    break;
    case State_Panel4: //pinnen
      char pin = customKeypad.getKey();
      if (pin) {
        if (pin == '1'){
          Serial.println("20 EUR");
          bedrag = 20;
          state = State_Panel9;
        }
        else if (pin == '3') {
          Serial.println("200 EUR");
          bedrag = 200;
          state = State_Panel9;
        }
        else if (pin == '4') {
          Serial.println("50 EUR");
          bedrag = 50;
          state = State_Panel9;
        }
        else if (pin == '6') {
          Serial.println("300 EUR");
          bedrag = 300;
          state = State_Panel9;
        }
        else if (pin == '7') {
          Serial.println("100 EUR");
          bedrag = 100;
          state = State_Panel9;
        }
        else if (pin == 'A') {
          Serial.println("Terug 5");
          state = State_Panel3;
        }
        else if (pin == '9') {
          Serial.println("Ander bedrag invoeren");
          state = State_Panel12;
        }
        else {
        }
      }
    }
{

break;

    case State_Panel5: //Saldo bekijken
      char pin = customKeypad.getKey();
      if (pin) {
        if (pin == 'A'){
          Serial.println("Terug 5");
          state = State_Panel3;
        }
        else if (pin == 'B') {
          Serial.println("Afsluiten 5");
          state = State_Card;
        }
        else {
        }
      }
    }
{

break;

    case State_Panel6: //Wilt U de bon?
      char pin = customKeypad.getKey();
      if (pin) {
        if (pin == 'A'){
          Serial.println("Ja 10");
          printBon(bedrag,1234,750);
          
          state = State_Panel10;
        }
        else if (pin == 'B') {
          Serial.println("Nee 10");
          state = State_Panel3;
        }
        else {
        }
      }
}

{

break;

    case State_Panel9: //Weet u het zeker?
      char pin = customKeypad.getKey();
      if (pin) {
        if (pin == 'A'){
          Serial.println("Ja 9");
          delay(3000);
          Serial.println("bonprinten");
          state = State_Panel6;
        }
        else if (pin == 'B') {
          Serial.println("Nee 9");
          state = State_Panel3;
        }
        else {
        }
      }
}

{

break;

case State_Panel12: {
       char pin = customKeypad.getKey();
      if (pin) {          
         
          Serial.print(pin);                                        //Als er een pin wordt ingetoets komt er een * in zijn plaats te staan. Verander ("*") naar (pin) om de getallen te kunnen zien.
        
        
        if (pin == '#') {

          if ((input_bedrag%10)==0) {
            Serial.println("kaas");
          input_bedrag = bedrag;
          state = State_Panel11;
          }
          else {
            Serial.println("Kan niet");                               //Telt hoevaak het wachtwoord verkeerd is ingevuld.
          }

        }
        else {
          input_bedrag += pin;
        }
      }
     }
}
      

      

      {

break;

    case State_Panel11: //Ander bedrag
      char pin = customKeypad.getKey();
      if (pin) {
        if (pin == 'D'){
          Serial.println("Terug 11");
          state = State_Panel3;
        }
        if (pin == 'A'){
          Serial.println(bedrag);
          Serial.println(input_bedrag);
        }

        else {
        }
      }
}


}
}
  
