package basic;

import java.util.ArrayList;

public class Tag {
	private int tagId;
	private String taf_rfid;
	private String nome;
	private int frequencia_semanal;
	private int frequencia_mensal;
	private ArrayList<RegIN> registros;
		
	public Tag(String taf_rfid, String nome) {
		super();
		this.taf_rfid = taf_rfid;
		this.nome = nome;
		this.frequencia_semanal = 0;
		this.frequencia_mensal = 0;
		this.registros = new ArrayList<RegIN>();
	}
	
	public String getTaf_rfid() {
		return taf_rfid;
	}
	public void setTaf_rfid(String taf_rfid) {
		this.taf_rfid = taf_rfid;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getFrequencia_semanal() {
		return frequencia_semanal;
	}

	public void setFrequencia_semanal(ArrayList<String> datas) {
		double horas = 0.0;
		if ((datas.size()%2)==0){
			for (int i = 0; i < datas.size(); i=i+2) {
				horas=horas+Utils.getDiferencaData(datas.get(i), datas.get(i+1));
			}
		}else{
			horas=-1;
		}
		this.frequencia_semanal = (int) horas;
	}

	public int getFrequencia_mensal() {
		return frequencia_mensal;
	}

	public void setFrequencia_mensal(ArrayList<String> datas) {
		double horas = 0.0;
		if ((datas.size()%2)==0){
			for (int i = 0; i < datas.size(); i=i+2) {
				horas=horas+Utils.getDiferencaData(datas.get(i), datas.get(i+1));
			}
		}else{
			horas=-1;
		}
		this.frequencia_semanal = (int) horas;
	}

	public ArrayList<RegIN> getRegistros() {
		return registros;
	}

	public void setRegistros(ArrayList<RegIN> registros) {
		this.registros = registros;
	}
	
	@Override
	public String toString() {
		if (tagId==-4){
			return "";//"<html> &emsp Nome: " + nome+"&emsp&emsp&emsp&emsp&emsp entrada: "+Utils.getSoHorario(data_hora)+"<br>"
				//+ "-----------------------------------------------------------------------" + " </span></html>";
		}
		if (tagId == -1) {
			return "Nenhum registro realizado";
		} 
		if (tagId == -3) {
			return "Erro ao carregar dados do servidor!";
		} else {						
			return "<html> &emsp " + nome+"&emsp&emsp&emsp&emsp CH: "+frequencia_semanal+"h<br>"
					+ " </span></html>";
		}
	}
	
}
