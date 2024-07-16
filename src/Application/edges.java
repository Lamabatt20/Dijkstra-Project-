package Application;

public class edges {
	vertex target;
	vertex source;
	double weight;
	public edges(vertex source,vertex target, double weight) {
		super();
	
		this.target = target;
		this.weight = weight;
	}
	
	public vertex getD() {
		return target;
	}
	public void setD(vertex desination) {
		this.target = desination;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}

	public vertex getSource() {
		return source;
	}

	public void setSource(vertex s) {
		this.source = s;
	}

}
