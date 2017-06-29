package basic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Testes {

	public static void main(String[] args) {
		Date dtInicial, dtFinal;
		try {
			dtInicial = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("27/06/2017 18:43");
			dtFinal = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("28/06/2017 20:12");
			
			//Convertendo as datas para milisegundos
	        long milisecondBegin = dtInicial.getTime();
	        long milisecondEnd = dtFinal.getTime();
	        //Subtraindo os milisegundos, para obter a diferença
	        long milisecondResult = -milisecondBegin + milisecondEnd;
	        System.out.println(milisecondResult /3600000.0);
	        
	        
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
}

}
