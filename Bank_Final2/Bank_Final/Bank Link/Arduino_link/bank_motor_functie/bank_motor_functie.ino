#include <AccelStepper.h>

#define motorPinA1 2
#define motorPinA2 3
#define motorPinA3 4
#define motorPinA4 5

#define motorPinB1 7
#define motorPinB2 8
#define motorPinB3 9
#define motorPinB4 10

int stepsPerRevolution = 64;        // stappen per omwenteling
float degreePerRevolution = 5.625;

long biljetTwin = 0;
long biljetTien = 0;

int geld_waarde = 50;
int geld_waarde2;
int aantaltwin;
int aantaltien;
  

AccelStepper stepper1(AccelStepper::HALF4WIRE, motorPinA1, motorPinA3, motorPinA2, motorPinA4);
AccelStepper stepper2(AccelStepper::HALF4WIRE, motorPinB1, motorPinB3, motorPinB2, motorPinB4);


void setup() {


 
}

void loop() {
    Serial.begin(9600);
  if(geld_waarde > 0){

  stepper1.setMaxSpeed(750.0);      // stel de maximale motorsnelheid in
  stepper1.setAcceleration(50.0);   // stel de acceleratie in
  stepper1.setSpeed(100); 

  stepper2.setMaxSpeed(750.0);      // stel de maximale motorsnelheid in
  stepper2.setAcceleration(50.0);   // stel de acceleratie in
  stepper2.setSpeed(100); 
   aantaltwin = geld_waarde  / 20;
   geld_waarde2 = geld_waarde % 20 ;
   aantaltien = geld_waarde2 / 10;
   biljetTwin = aantaltwin * 760;
   biljetTien = aantaltien * 760;
   Serial.println(biljetTwin);
   Serial.println(biljetTien);
   Serial.println(aantaltwin);
   Serial.println(aantaltien);
    }
 
    stepper1.moveTo(degToSteps(biljetTwin));
    stepper1.run();
    
    stepper2.moveTo(degToSteps(biljetTien));
    stepper2.run();
    
}

  float degToSteps(float deg) {
    return (stepsPerRevolution / degreePerRevolution)* deg;
  }
