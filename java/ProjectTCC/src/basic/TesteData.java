package basic;

import java.util.Date;
import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.util.Calendar;


public class TesteData {

	public static void main(String[] args) throws ParseException {
		//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //Date ontem = sdf.parse("30/04/2017");
		Date data = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);  
		int day = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, -day+1);
		
		Date inicio = cal.getTime();
		System.out.println(Utils.convertDateToString(inicio));
		
		System.out.println(Utils.getSoData("31/05/2017-17:26"));
		System.out.println(Utils.getSoHorario("31/05/2017-17:26"));
	}

}
