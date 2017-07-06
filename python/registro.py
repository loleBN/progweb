# Autor: Lois Lene Braga Nascimento
# Data: 06/07/2017 09:52:01
# Linguagem: Python
 

 
#! /usr/bin/env python
import calendar
from datetime import datetime, timedelta
import sys
import urllib2
#######################################################################
def getDT():
	d = datetime.now()
	dia = d.day
	mes = d.month
	ano = str(d.year)
	date=""
	if dia<10 :
		date = "0"+str(dia)
	else :
		date = ""+str(dia)
	if  mes<10 :
		date = date+"/0"+str(mes)
	else :
		date = "/"+date+str(mes)
	date = date +"/"+ str(d.year)
	if (d.hour-4)<10:
		hora = "0"+str(getattr(d,'hour')-4)
	else:
		hora = str(getattr(d,'hour')-4)
	if (d.minute<10):
		time = hora+":0"+str(d.minute)
	else:
		time = hora+":"+str(d.minute)
	dt = date +"-"+ time
	return dt

if __name__=='__main__':
 	
	args_ID = sys.argv[1]
	dt = getDT()
#	print args_ID +" "+ dt +"\n"
	url = 'http://ufam-automation.net/loislene/addRegistro.php?tag_rfid='+args_ID+'&date_time='+dt
	f = urllib2.urlopen(url)
	contents = f.read()
	f.close()
	if contents == "-1":
		print "-1" #retornar isso pro arduino??
	if contents == "1":
		print "1" 	
