void setup() {
  Serial.begin(9600);
  Serial.println("First message");
  delay(4000);

}
byte in;
int count=0;
void loop() {
    Serial.println("Second message");
    delay(2000);
    Serial.println("$1000 money");
    delay(2000);
    Serial.println("Third message");
    delay(2000);
    Serial.println("$2000 money");
    delay(2000);
    Serial.println("$1000 money");
    delay(2000);
  }
