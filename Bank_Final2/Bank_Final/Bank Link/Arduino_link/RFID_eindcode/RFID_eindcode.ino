  #include <arduinio.h>
#include <SPI.h>
#include <MFRC522.h>
 
#define SS_PIN 10
#define RST_PIN 9
MFRC522 mfrc522(SS_PIN, RST_PIN);   // Create MFRC522 instance.
 
void setup() 
{
  Serial.begin(9600);   // Initiate a serial communication
  SPI.begin();      // Initiate  SPI bus
  mfrc522.PCD_Init();   // Initiate MFRC522
  Serial.println("Approximate your card to the reader...");
  byte in;
  int count=0;

}
void loop() 
{
  // Look for new cards
  if ( ! mfrc522.PICC_IsNewCardPresent()) 
  {
    return;
  }
  // Select one of the cards
  if ( ! mfrc522.PICC_ReadCardSerial()) 
  {
    return;
  }
  //Show UID on serial monitor
  Serial.print("UID tag :");
  String content= "";
  String pincode="0000";
  int geld;
  int ssaldo=250;
  int nsaldo= (ssaldo-geld);
  
  byte letter;
  for (byte i = 0; i < mfrc522.uid.size; i++) 
  {
     Serial.print(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " ");
     Serial.print(mfrc522.uid.uidByte[i], HEX);
     content.concat(String(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " "));
     content.concat(String(mfrc522.uid.uidByte[i], HEX));
  }
  Serial.println();
  Serial.print("Message : ");
  content.toUpperCase();
  if (content.substring(1) == "7B E9 33 09") //change here the UID of the card/cards that you want to give access
  {
          Serial.println("Welkom");
          Serial.println();
          delay(3000);
          cout << "Toets uw Pincode: ";
          cin >> pincode;
          if (pincode == "7878"){
            Serial.println("Pass access accepted");
            Serial.println("test");
            Serial.println(ssaldo);
            Serial.println("");

            delay(3000);
            cout << "Hoeveel geld wilt u opnemen? ";
            cin >> geld;
            Serial.println(geld);       
            Serial.println(nsaldo);
            Serial.println(ssaldo);
            delay(3000);
            
          }
          
          else{
          Serial.println("Pincode incorrect, Uw heeft nog 2 pogingen.");
          Serial.println("");
          delay(3000);
          cout << "Toets uw Pincode: ";
          cin >> pincode;
          if (pincode == "7878"){
            Serial.println("Pass access accepted");
            Serial.println("");
            Serial.println(+ssaldo);
            delay(3000);
            cout << "Hoeveel geld wilt u opnemen? €";
            cin >> geld;
            Serial.println(geld+" wordt gepint.");
            Serial.println("Uw nieuwe saldo is: €"+nsaldo);
          
          
         }
         
         else{
          Serial.println("Pincode incorrect, Uw heeft nog 1 pogingen.");
          Serial.println("");
          delay(3000);
          cout << "Toets uw Pincode: ";
          cin >> pincode;
          if (pincode == "7878"){
            Serial.println("Pass access accepted");
            Serial.println("");
            Serial.println("Uw saldo: €" + ssaldo);
            delay(3000);
             cout << "Hoeveel geld wilt u opnemen? ";
            cin >> geld;
            Serial.println(geld+" wordt gepint");
            Serial.println("Uw nieuwe saldo is: €"+nsaldo);
            delay(3000);
            }
 
  else {
    Serial.println(" Access denied");
    delay(3000);
  }
}
          }
  }

 
  else if (content.substring(1) == "B9 8B BA C2"){
          Serial.println("Welkom Bij N.B BANK");
          Serial.println();
          delay(3000);
          cout << "Toets uw Pincode: ";
          cin >> pincode;
          if (pincode == "6565"){
            Serial.println("Pass access accepted");
            Serial.println("");
            delay(3000);
          }
          
          else{
          Serial.println("Pincode incorrect, Uw heeft nog 2 pogingen.");
          Serial.println("");
          delay(3000);
          cout << "Toets uw Pincode: ";
          cin >> pincode;
          if (pincode == "6565"){
            Serial.println("Pass access accepted");
            Serial.println("");
            delay(3000);
         }
         
         else{
          Serial.println("Pincode incorrect, Uw heeft nog 1 pogingen.");
          Serial.println("");
          delay(3000);
          cout << "Toets uw Pincode: ";
          cin >> pincode;
          if (pincode == "6565"){
            Serial.println("Pass access accepted");
            Serial.println("");
            delay(3000);
            }
 
  else {
    Serial.println(" Access denied");
    delay(3000);
  }
}
          }
  }
}
          
