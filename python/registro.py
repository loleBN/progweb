# Autor: Lois Lene Braga Nascimento
# Data: 15/05/2017 14:54:58 
# Linguagem: Python
 

 
#! /usr/bin/env python
from datetime import datetime
import time
import sys
import urllib2
#######################################################################

#########################################################################
def sendToServer():

		args_ID = sys.argv[1]
		objeto_hoje = datetime.today()
		dt = str(objeto_hoje.strftime('%d/%m/%Y-%H:%M'))
		print args_ID + dt +"\n"

		url = 'http://ufam-automation.net/loislene/addRegistro.php?tag_rfid='+args_ID+'&date_time='+dt
		f = urllib2.urlopen(url)
		contents = f.read()
		f.close
       if(contents==-1)
			print "tag nao cadastrada" #retornar isso pro arduino??
 
################################ MAIN ####################################
if __name__=='__main__':
 	
        sendToServer()
	
