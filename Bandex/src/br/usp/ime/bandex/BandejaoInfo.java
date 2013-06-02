package br.usp.ime.bandex;

public class BandejaoInfo {

	private double latitude;
	private double longitude;
	private String title;
	private String schedule;
	private int icon;
	
	public BandejaoInfo(double latitude, double longitude, String title, String schedule, int icon) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.title = title;
		this.schedule = schedule;
		this.icon = icon;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}
}
