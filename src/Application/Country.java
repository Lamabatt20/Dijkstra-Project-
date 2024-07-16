package Application;

public class Country {
	private String name;
	private double x;
	private double y;
	private double longitude;
	private double latitude;
	
	public Country() {
		super();
	}
	public Country(String line) {
		String[] arr = line.split(" ");

		this.name = arr[0];

		this.latitude = Double.parseDouble(arr[1]);
		this.longitude = Double.parseDouble(arr[2]);
	}
	public Country(String name, double x, double y, double longitude, double latitude) {
		super();
		this.name = name;
		this.x = x;
		this.y = y;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public double getLongitude() {
		return longitude;
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public double getLatitude() {
		return latitude;
	}


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	public double getX() {
		 return ((this.longitude +198.0) * (772.0 - 0) / (195.0 +198.0)) + 0;
	}
	public void setX(double x) {
		this.x = x;
	}

	public double getY() { 
		return ((this.latitude -99.0) * (434.4 - 0) / (-55.0 -99.0)) + 0;
	}
	
	public void setY(double y) {
		this.y = y;
	}

}
