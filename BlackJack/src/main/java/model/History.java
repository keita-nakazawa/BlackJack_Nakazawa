package model;

public class History {

	private String time;
	private int result;
	
	public History(String time, int result) {
		this.time = time;
		this.result = result;
	}
	
	public String getTime() {
		return time;
	}
	
	public int getResult() {
		return result;
	}
}
