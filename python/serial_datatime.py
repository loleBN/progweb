# Autor: Fernando Krein Pinheiro
# Data: 07/09/2011
# Linguagem: Python
 
# ========= IMPORTANTE ===========
# O codigo esta livre para usar,
# citar e compartilhar desde que
# mantida sua fonte e seu autor.
# Obrigado.
 
#! /usr/bin/env python
from datetime import datetime
import time
import serial
porta = '/dev/ttyACM0'
baud_rate = 9600
#######################################################################
def escrever_porta():
 
   try:
       valor = (raw_input("Digite 1 para ligar o led.\nDigite 2 para desligar o led.\n"))
       Obj_porta = serial.Serial(porta, baud_rate)
       Obj_porta.write(valor)
       ler_porta()
       Obj_porta.close()
 
   except serial.SerialException:
       print"ERRO: Verifique se ha algum dispositivo conectado na porta!"
 
#########################################################################
def ler_porta():
 
   try:
       Obj_porta = serial.Serial(porta, baud_rate)
       valor = Obj_porta.readline()
       print"Arduino disse: ",valor
       #Obj_porta.flushInput()
       Obj_porta.close()

       objeto_hoje = datetime.today()
       dia = str(objeto_hoje.day)
       mes = str(objeto_hoje.month)
       ano = str(objeto_hoje.year)
       horario = str(objeto_hoje.strftime("%X"))
       print dia,"/",mes,"/",ano 
       print horario
 
   except serial.SerialException:
       print"ERRO: Verifique se ha algum dispositivo conectado na porta!"
 
################################ MAIN ####################################
if __name__=='__main__':
 	
        ler_porta()
	
