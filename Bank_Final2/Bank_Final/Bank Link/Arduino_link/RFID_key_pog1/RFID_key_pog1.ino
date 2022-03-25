#include <SPI.h>
#include <MFRC522.h>
#include <Keypad.h>

typedef enum {
  State_Card,
  State_Pin
}
current_state;
current_state state;

#define SS_PIN 10
#define RST_PIN 9 

MFRC522 mfrc522(SS_PIN, RST_PIN);

const byte ROWS = 4;
const byte COLS = 3;
char hexaKeys[ROWS][COLS] = {
  {'1', '2', '3'},
  {'4', '5', '6'},
  {'7', '8', '9'},
  {'*', '0', '#'}
};
byte rowPins[ROWS] = {8, 7, 6, 5};
byte colPins[COLS] = {4, 3, 2};
Keypad customKeypad = Keypad(makeKeymap(hexaKeys), rowPins, colPins, ROWS, COLS);

const String password;
String input_password;
int wrong = 0;
int counter = 0;

void setup() {
  Serial.begin(9600);
  SPI.begin();
  mfrc522.PCD_Init();
  Serial.println("Scan your card");
  input_password.reserve(32);                                   //Maximale input van 32 characters voordat je niks meer kan invullen.
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
      if (content.substring(1) == "7B E9 33 09" || content.substring(1) == "14 F1 D4 2B" || content.substring(1) == "C9 0C 72 C2" || content.substring(1) == "99 4A D1 B9") {
        if (content.substring(1) == "7B E9 33 09") {
          password = "1234";                                        //Wanneer deze pas wordt herkend geeft moet de wachtwoord 1234 worden ingevuld.
        }
        else if (content.substring(1) == "99 4A D1 B9") {
          password = "0000";                                        //Wanneer deze pas wordt herkend geeft moet de wachtwoord 0000 worden ingevuld.
        }
        Serial.println("Authorized access");
        Serial.println("Enter password: ");
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
        if (pin != '#' && pin != '*') {
          Serial.println("*");                                        //Als er een pin wordt ingetoets komt er een * in zijn plaats te staan. Verander ("*") naar (pin) om de getallen te kunnen zien.
        }
        if (pin == '*') {
          input_password = "";
        }
        else if (pin == '#') {
          counter++;                                                  //Telt hoevaak er is geprobeerd om in te loggen.
          Serial.print("COUNT: ");
          Serial.println(counter);
          if (password == input_password) {
            Serial.println("Correct password");
            wrong = 0;                                                //Als het wachtwoord klopt wordt de teller van foute wachtwoorden terug naar 0 gezet.
            state = State_Card;
          }
          else if (password != input_password) {
            wrong++;                                                  //Telt hoevaak het wachtwoord verkeerd is ingevuld.
          }
          if (wrong == 1) {
            Serial.println("Incorrect password, 2 tries left!");
          }
          else if (wrong == 2) {
            Serial.println("Incorrect password, 1 try left!");
          }
          else if (wrong == 3) {
            Serial.println("Card is blocked");                         //Bij 3 foutmeldingen wordt de pas 'geblokkeerd'. (Nu wordt je alleen uitgelogd.)
            state = State_Card;
          }
          input_password = "";
        }
        else {
          input_password += pin;
        }
      }
    }
    break;
    default: {
      break;
    }
  }
}
