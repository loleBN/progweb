package webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import basic.FreqGraph;
import basic.RegIN;
import basic.Utils;

public class WebService {
	public static ArrayList<RegIN> getRegsNow() throws IOException, JSONException {
		ArrayList<RegIN> regs = new ArrayList<RegIN>();
		URL urlEventos = new URL("http://ufam-automation.net/loislene/getStatusIN.php");
		BufferedReader in = new BufferedReader(new InputStreamReader(urlEventos.openStream()));
		String inputLine;
		inputLine = in.readLine();
		if(!inputLine.equals("-1")){
			JSONArray regsL = new JSONArray(inputLine);
			JSONObject r;
			System.out.println("RegsNOW: "+inputLine);
			for (int i = 0; i < regsL.length(); i++) {
				r = new JSONObject(regsL.getString(i));
				regs.add(new RegIN(-4,r.getString("tag_rfid"), r.getString("nome"),r.getString("date_time"),1));			
			}
		}else{
			regs.add(new RegIN(-1,"","", "",-1));		
	}
		return regs;
	}
	
	public static ArrayList<RegIN> getRegsByDate(String date,int id) throws IOException, JSONException {
		ArrayList<RegIN> regs = new ArrayList<RegIN>();
		URL urlRegs = new URL("http://ufam-automation.net/loislene/getRegistroByData.php?date=" + date);
		BufferedReader in = new BufferedReader(new InputStreamReader(urlRegs.openStream()));
		String inputLine;
		inputLine = in.readLine();

		if(!inputLine.equals("-1")){
			JSONArray regsL = new JSONArray(inputLine);
			JSONObject r;
			if(id==1){
				for (int i = regsL.length()-1; i >= 0 ; i--) {
					r = new JSONObject(regsL.getString(i));
					regs.add(new RegIN(i,r.getString("tag_rfid"), r.getString("nome"),r.getString("date_time"), r.getInt("status")));			
				}
			}else{
				for (int i=0; i<regsL.length(); i++) {
					r = new JSONObject(regsL.getString(i));
					regs.add(new RegIN(i,r.getString("tag_rfid"), r.getString("nome"),r.getString("date_time"), r.getInt("status")));			
				}
			}
		} else{
				regs.add(new RegIN(-1,"","", "",-1));		
		}
		
		return regs;
	}
	public static ArrayList<RegIN> getRegsByMonth(String month) throws IOException, JSONException {
		System.out.println("month do getBYMonth:"+month);
		ArrayList<RegIN> regs = new ArrayList<RegIN>();
		URL urlRegs = new URL("http://ufam-automation.net/loislene/getRegistroMensal.php?mes=" + month);
		BufferedReader in = new BufferedReader(new InputStreamReader(urlRegs.openStream()));
		String inputLine;
		inputLine = in.readLine();

		if(!inputLine.equals("-1")){
			JSONArray regsL = new JSONArray(inputLine);
			JSONObject r;
	
			for (int i = 0; i < regsL.length(); i++) {
				r = new JSONObject(regsL.getString(i));
				regs.add(new RegIN(i,r.getString("tag_rfid"), r.getString("nome"),r.getString("date_time"), r.getInt("status")));			
			}
		} else{
				regs.add(new RegIN(-1,"","", "",-1));		
		}
		
		return regs;
	}
	public static ArrayList<RegIN> getRegsByWeek(Date date_) throws IOException, JSONException {
		ArrayList<RegIN> regs = new ArrayList<RegIN>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date_);  
		int day = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, -day);
		
		for(int it=0;it<day;it++){
			cal.add(Calendar.DATE, +1);
			String date = Utils.convertDateToString(cal.getTime());
			
			
			URL urlRegs = new URL("http://ufam-automation.net/loislene/getRegistroByData.php?date=" + date);
			BufferedReader in = new BufferedReader(new InputStreamReader(urlRegs.openStream()));
			String inputLine;
			inputLine = in.readLine();
	
			if(!inputLine.equals("-1")){
				JSONArray regsL = new JSONArray(inputLine);
				JSONObject r;
		
				for (int i = 0; i < regsL.length(); i++) {
					r = new JSONObject(regsL.getString(i));
					regs.add(new RegIN(i,r.getString("tag_rfid"), r.getString("nome"),r.getString("date_time"), r.getInt("status")));			
				}
			} 
		}
		return regs;
	}
	public static ArrayList<FreqGraph> getFreqByWeek(Date date_) throws IOException, JSONException {
		ArrayList<FreqGraph> regs = new ArrayList<FreqGraph>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date_);  
		int day = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, -day);
		
		cal.add(Calendar.DATE, +1);
		String d1=Utils.convertDateToString(cal.getTime());
		cal.add(Calendar.DATE, +1);
		String d2=Utils.convertDateToString(cal.getTime());
		cal.add(Calendar.DATE, +1);
		String d3=Utils.convertDateToString(cal.getTime());
		cal.add(Calendar.DATE, +1);
		String d4=Utils.convertDateToString(cal.getTime());
		cal.add(Calendar.DATE, +1);
		String d5=Utils.convertDateToString(cal.getTime());
		cal.add(Calendar.DATE, +1);
		String d6=Utils.convertDateToString(cal.getTime());
		cal.add(Calendar.DATE, +1);
		String d7 =Utils.convertDateToString(cal.getTime());
				
			
			URL urlRegs = new URL("http://ufam-automation.net/loislene/getFreqSemanal.php?d1="+d1+"&d2="+d2+"&d3="+d3+"&d4="+d4+"&d5="+d5+"&d6="+d6+"&d7="+d7);
			BufferedReader in = new BufferedReader(new InputStreamReader(urlRegs.openStream()));
			String inputLine;
			inputLine = in.readLine();
			System.out.println(inputLine);
	
			if(!inputLine.equals("-1")){
				JSONArray regsL = new JSONArray(inputLine);
				JSONObject r;
		
				for (int i = 0; i < regsL.length(); i++) {
					r = new JSONObject(regsL.getString(i));
					regs.add(new FreqGraph(r.getString("d"), r.getInt("f")));			
				}
			} 
		
		return regs;
	}
	
	
}
