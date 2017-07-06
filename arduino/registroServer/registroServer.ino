#include <SPI.h>
#include <RFID.h>

RFID rfid(10,5); 
String cmd;
void setup()
{ 
  Serial.begin(9600);
  SPI.begin(); 
  rfid.init();
}

void loop()
{
    if (rfid.isCard()) {
        cmd=String("python /home/tcclolebn/registro_plus_led.py ");
          String strID="";
          if (rfid.readCardSerial()) {
            for(int i=0;i<5;i++) 
              strID=strID+rfid.serNum[i];
 
            cmd+=strID;   
            Serial.println(strID.buffer);
            //Serial.println(cmd.buffer);
            system(cmd.buffer);  
            cmd="";
            delay(3000);     
          }
          
    }
    
    rfid.halt();
}
