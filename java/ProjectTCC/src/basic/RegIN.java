package basic;

public class RegIN {
	private String taf_rfid;
	private String data_hora;
	private int status;
		
	public RegIN(String taf_rfid, String data_hora, int status) {
		super();
		this.taf_rfid = taf_rfid;
		this.data_hora = data_hora;
		this.status = status;
	}
	
	public String getTaf_rfid() {
		return taf_rfid;
	}
	public void setTaf_rfid(String taf_rfid) {
		this.taf_rfid = taf_rfid;
	}
	public String getData_hora() {
		return data_hora;
	}
	public void setData_hora(String data_hora) {
		this.data_hora = data_hora;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
