#include <SPI.h>
#include <MFRC522.h>
#include <Keypad.h>

typedef enum {
  State_Card,
  State_Pin,
  State_Control_1,
  State_Control_2,
  State_Control_3,
  State_Control_4
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
int stars = 0;

void setup() {
  Serial.begin(9600);
  SPI.begin();
  mfrc522.PCD_Init();
  Serial.println("Scan your card.");
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
      if (content.substring(1) == "7B E9 33 09" || content.substring(1) == "14 F1 D4 2B" || content.substring(1) == "C9 0C 72 C2" || content.substring(1) == "12 76 50 34") { 
        if (content.substring(1) == "7B E9 33 09") {
          password = "1234";                                        //Wanneer deze pas wordt herkend geeft moet de wachtwoord 1234 worden ingevuld.
        }
        else if (content.substring(1) == "12 76 50 34") {
          password = "0000";                                        //Wanneer deze pas wordt herkend geeft moet de wachtwoord 0000 worden ingevuld.
        }
        Serial.println("Voer uw Pincode in: ");
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
          stars++;
          switch (stars) { 
            case 0:
            Serial.println("");
            break;         
            case 1:
            Serial.println("*");
            break;
            case 2:
            Serial.println("* *");
            break;
            case 3:
            Serial.println("* * *");
            break;
            case 4:
            Serial.println("* * * *");
            break;
            }
        }
        if (pin == '*') {
          Serial.println("1 switch");
          state = State_Card;
          input_password = "";
          
        }
        else if (pin == '#') {
          counter++;                                                  //Telt hoevaak er is geprobeerd om in te loggen.
          stars = 0;
          Serial.print("COUNT: ");
          Serial.println(counter);
          if (password == input_password) {
            Serial.println("Pincode geaccepteerd");
            delay(2000);
            Serial.println("4 switch");
            wrong = 0;                                                //Als het wachtwoord klopt wordt de teller van foute wachtwoorden terug naar 0 gezet.
            state = State_Control_1;
          }
          else if (password != input_password) {
            wrong++;                                                  //Telt hoevaak het wachtwoord verkeerd is ingevuld.
          }
          if (wrong == 1) {
            Serial.println("Verkeerde Pincode, u heeft nog 2 pogingen!");
          }
          else if (wrong == 2) {
            Serial.println("Verkeerdde Pincode, u heeft nog 1 poging!");
          }
          else if (wrong == 3) {
            Serial.println("Deze kaart is geblokeerd");                         //Bij 3 foutmeldingen wordt de pas 'geblokkeerd'. (Nu wordt je alleen uitgelogd.)
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
   case State_Control_1: {
     char pin = customKeypad.getKey();
      if (pin) {
        if (pin == '1') {
        Serial.println("70 cash");
        delay(500);
        Serial.println("6 switch");
                delay(3000);
        Serial.println("7 switch");
        state = State_Control_4; 
        }
        else if (pin == '3') {
        Serial.println("3 switch");
        state = State_Control_2;
        }
        else if (pin == '7') {
        Serial.println("5 switch");
        state = State_Control_3;
        }
        else if (pin == '*') {
          state = State_Card;
          Serial.println("1 switch");
        }
      
      }
    }
    break;
   case State_Control_2: {
     char pin = customKeypad.getKey();
      if (pin) {
        if (pin == '1') {
        Serial.println("10 cash");
        delay(500);
        Serial.println("6 switch");
                delay(3000);
        Serial.println("7 switch");
        state = State_Control_4;
        }
        else if (pin == '3') {
        Serial.println("100 cash");
        delay(500);
        Serial.println("6 switch");
                delay(3000);
        Serial.println("7 switch");
        state = State_Control_4;
        }
        else if (pin == '4') {
        Serial.println("20 cash");
        delay(500);
        Serial.println("6 switch");
                delay(3000);
        Serial.println("7 switch");
        state = State_Control_4;
        }
        else if (pin == '6') {
        Serial.println("200 cash");
        delay(500);
        Serial.println("6 switch");
                delay(3000);
        Serial.println("7 switch");
        state = State_Control_4;
        }
        else if (pin == '7') {
        Serial.println("50 cash");
        delay(500);
        Serial.println("6 switch");
                delay(3000);
        Serial.println("7 switch");
        state = State_Control_4;
        }
        else if (pin == '9') {
        Serial.println("500 cash");
        delay(500);
        Serial.println("6 switch");
                delay(3000);
        Serial.println("7 switch");
        state = State_Control_4;
        }
        else if (pin == '*') {
          state = State_Control_1;
          Serial.println("4 switch");
        }
      
      }
    }
    break;
   case State_Control_3: {
     char pin = customKeypad.getKey();
      if (pin) {
        if (pin == '1') {
        Serial.println("Placeholder");                                        //Als er een pin wordt ingetoets komt er een * in zijn plaats te staan. Verander ("*") naar (pin) om de getallen te kunnen zien.
        }
        else if (pin == '3') {
        Serial.println("Placeholder");
        }
        else if (pin == '7') {
        Serial.println("Placeholder");
        }
        else if (pin == '*') {
          state = State_Control_1;
          Serial.println("4 switch");
        }
      
      }
    }
    break;

   case State_Control_4: {
     char pin = customKeypad.getKey();
      if (pin) {
        if (pin == '#') {
        state = State_Card;
        Serial.println("1 switch");                                        //Als er een pin wordt ingetoets komt er een * in zijn plaats te staan. Verander ("*") naar (pin) om de getallen te kunnen zien.
        }
        else if (pin == '*') {
          state = State_Control_1;
          Serial.println("4 switch");
        }
      
      }
    }
    break;
        default: {
      break;
    }
  }
  }
