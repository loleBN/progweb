package basic;

public class FreqGraph {
	private String data;
	private int frequencia;
	
	public FreqGraph(String data, int freq) {
		this.data=data;
		this.frequencia=freq;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getFrequencia() {
		return frequencia;
	}
	public void setFrequencia(int frequencia) {
		this.frequencia = frequencia;
	}
	public void upFreq() {
		this.frequencia ++;
	}
}
