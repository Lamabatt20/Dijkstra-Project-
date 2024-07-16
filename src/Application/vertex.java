package Application;


public class vertex implements Comparable<vertex> {
	Country country;
	vertex previous;
	int num;
	double distance=Double.MAX_VALUE;
	boolean known;
	LinkedList<edges> e = new LinkedList<edges>();

	public vertex(Country country, int number) {
		super();
		this.country = country;
		this.num = number;
	}

	public Country getCountry() {
		return country;
	}


	public void setCountry(Country country) {
		this.country = country;
	}


	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public LinkedList<edges> getE() {
		return e;
	}

	public void setE(LinkedList<edges> e) {
		this.e = e;
	}

	public boolean FindEdge(String ss) {

		for (int i = 0; i < e.getCount(); i++) {
			if (e.get(i).getD().getCountry().getName().compareToIgnoreCase(ss) == 0)
				return true;
		}
		return false;
	}

	public String ttoString() {
		String r = country.getName()+":";
		for (int i = 0; i < e.getCount(); i++) {
			r = r + e.get(i).target.country.getName() + ",";
		}
		return r;
	}

	@Override
    public int compareTo(vertex other) {
        return Double.compare(this.distance, other.distance);
    }


}
