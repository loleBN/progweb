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
import basic.Tag;
import basic.Utils;

public class WebService {
	public static ArrayList<RegIN> getRegsNow() throws IOException, JSONException {
		System.out.println("Baixando Dados...");
		ArrayList<RegIN> regs = new ArrayList<RegIN>();
		URL urlEventos = new URL("http://ufam-automation.net/loislene/getStatusIN.php");
		BufferedReader in = new BufferedReader(new InputStreamReader(urlEventos.openStream()));
		String inputLine;
		inputLine = in.readLine();
		if(!inputLine.equals("-1")){
			JSONArray regsL = new JSONArray(inputLine);
			JSONObject r;
			
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
		System.out.println("Baixando Dados...");
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
		System.out.println("Baixando Dados...");
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
		System.out.println("Baixando Dados...");
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
		System.out.println("Baixando Dados...");
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
	
	public static ArrayList<Tag> getFHByWeek(Date date_) throws IOException, JSONException {
		System.out.println("Baixando Dados...");
		
		ArrayList<Tag> tags = new ArrayList<Tag>();
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
						
		String inputLine ;
		URL urlRegs = new URL("http://ufam-automation.net/loislene/getTags.php");
		BufferedReader in = new BufferedReader(new InputStreamReader(urlRegs.openStream()));
		inputLine = in.readLine();
		if(!inputLine.equals("-1")){
			JSONArray tagsL = new JSONArray(inputLine);
			JSONObject tag;
	
			for (int i = 0; i < tagsL.length(); i++) {
				
				ArrayList<RegIN> regs = new ArrayList<RegIN>();
				ArrayList<String> datas = new ArrayList<String>();
				
				tag = new JSONObject(tagsL.getString(i));
				tags.add(new Tag(tag.getString("tag_rfid"), tag.getString("nome")));			
				
				urlRegs = new URL("http://ufam-automation.net/loislene/getFHSem.php?tag_rfid="+tag.getString("tag_rfid")+"&d1="+d1+"&d2="+d2+"&d3="+d3+"&d4="+d4+"&d5="+d5+"&d6="+d6+"&d7="+d7);
				in = new BufferedReader(new InputStreamReader(urlRegs.openStream()));
				inputLine = in.readLine();
						
				if(!inputLine.equals("-1")){
					JSONArray regsL = new JSONArray(inputLine);
					JSONObject reg;
					
					for (int c = 0; c < regsL.length(); c++) {
						reg = new JSONObject(regsL.getString(c));
						datas.add(reg.getString("dt"));	
						regs.add(new RegIN(-2,reg.getString("tag"), reg.getString("nome"),reg.getString("dt"), reg.getInt("status")));
					}
					tags.get(i).setFrequencia_semanal(datas);
					tags.get(i).setRegistros(regs);
										
				} 
			}
		}
		return tags;
	}
	
public static ArrayList<Tag> getFHByMonth(String month) throws IOException, JSONException {
	System.out.println("Baixando Dados... Mes: "+month);
		ArrayList<Tag> tags = new ArrayList<Tag>();
								
		String inputLine ;
		URL urlRegs = new URL("http://ufam-automation.net/loislene/getTags.php");
		BufferedReader in = new BufferedReader(new InputStreamReader(urlRegs.openStream()));
		inputLine = in.readLine();
		if(!inputLine.equals("-1")){
			JSONArray tagsL = new JSONArray(inputLine);
			JSONObject tag;
	
			for (int i = 0; i < tagsL.length(); i++) {
				
				ArrayList<String> datas = new ArrayList<String>();
				ArrayList<RegIN> regs = new ArrayList<RegIN>();
				
				tag = new JSONObject(tagsL.getString(i));
				tags.add(new Tag(tag.getString("tag_rfid"), tag.getString("nome")));			
				
				urlRegs = new URL("http://ufam-automation.net/loislene/getFHMen.php?tag_rfid="+tag.getString("tag_rfid")+"&mes="+month);
				in = new BufferedReader(new InputStreamReader(urlRegs.openStream()));
				inputLine = in.readLine();
						
				if(!inputLine.equals("-1")){
					JSONArray regsL = new JSONArray(inputLine);
					JSONObject reg;
					for (int c = 0; c < regsL.length(); c++) {
						reg = new JSONObject(regsL.getString(c));
						datas.add(reg.getString("dt"));	
						regs.add(new RegIN(-10,reg.getString("tag"), reg.getString("nome"),reg.getString("dt"), reg.getInt("status")));		
					}
				} 
				tags.get(i).setFrequencia_mensal(datas);
				tags.get(i).setRegistros(regs);
			}
		}
		return tags;
	}
}
