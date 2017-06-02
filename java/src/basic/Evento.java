package basic;

import java.awt.Color;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.toedter.calendar.IDateEvaluator;

public class Evento implements IDateEvaluator{
	
	private int eventoId;
	private String titulo, email, nome;
	private String dataInicio;
	private String dataFim;
	
	public Evento(int eventoId, String titulo, String email, String nome, String dataInicio, String dataFim) {
		super();
		this.eventoId = eventoId;
		this.titulo = titulo;
		this.email = email;
		this.nome = nome;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
	}
	
	public int getEventoId() {
		return eventoId;
	}
	public void setEventoId(int eventoId) {
		this.eventoId = eventoId;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}
	public String getDataFim() {
		return dataFim;
	}
	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}
	
	public String getSoData(String data) {
		Date date;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data);
			String soData = new SimpleDateFormat("dd-MM-yyyy").format(date);
			return soData;
		} catch (ParseException e) {
			System.out.println("Erro = "+e.getMessage());
		}
		return null;
	}
	
	public Date convertStringToDate(String dateInString){
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = format.parse(dateInString);
        } catch (ParseException e) {
        	System.out.println("Erro = "+e.getMessage());
        }
        return date;
    }
	
	public String convertDateToString(Date dateInString){
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String date = null;
        try {
            date = format.format(dateInString);
        } catch (Exception e) {
        	System.out.println("Erro = "+e.getMessage());
        }
        return date;
    }
	
	public int getSoDia(String data) {
		Date date;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data);
			String soDia = new SimpleDateFormat("dd").format(date);
			return Integer.parseInt(soDia);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public String getSoHorario(String data) {
		Date date;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data);
			String SoHorario = new SimpleDateFormat("HH:mm").format(date);
			return SoHorario;
		} catch (ParseException e) {
			System.out.println("Erro = "+e.getMessage());
		}
		return null;
	}
	
	@Override
	public String toString() {
		if (eventoId == -2){
			return "Sem eventos pendentes!";
		}
		if (eventoId == -1) {
			return "Sem eventos neste dia!";
		} 
		if (eventoId == -3) {
			return "Erro ao carregar dados do servidor!";
		} else {
			return "<html>Titulo: " + titulo  + "<br>"
					+ "Data: " + getSoData(dataInicio) + ";       " + getSoHorario(dataInicio) + "~" + getSoHorario(dataFim) + "<br>"
					+ "Nome: " + nome + "<br>" 
					+ "------------------------------------------" + " </span></html>";
		}
	}

	public Color getInvalidBackroundColor() {
		// TODO Auto-generated method stub
		return null;
	}

	public Color getInvalidForegroundColor() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getInvalidTooltip() {
		// TODO Auto-generated method stub
		return null;
	}


	public Color getSpecialBackroundColor() {
		// TODO Auto-generated method stub
		return null;
	}

	public Color getSpecialForegroundColor() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSpecialTooltip() {
		// TODO Auto-generated method stub
		return null;
	}


	public boolean isInvalid(Date arg0) {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean isSpecial(Date arg0) {
		// TODO Auto-generated method stub
		return false;
	}	

}
