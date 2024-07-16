package Application;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class WorldController {
	@FXML
	private ImageView image;
	@FXML
	private ComboBox<String> combobox1;

	@FXML
	private ComboBox<String> combobox2;

	@FXML
	private TextArea textarea;
	@FXML
	private TextField textfield;
	@FXML
	private Pane Pane1;
	static ArrayList<vertex> Country = new ArrayList<>();
	int i;

	public void initialize() {
		File file = new File("Country.txt");
		readFile(file);
		// Add country names to ComboBoxes
		for (int i = 0; i < Country.size(); i++) {
			combobox1.getItems().add(Country.get(i).getCountry().getName());
			combobox2.getItems().add(Country.get(i).getCountry().getName());
		}
		drawPoint();//draw point on image 
	}

	@FXML
	public void RunB(ActionEvent event) {
		vertex vertx1 = null;
		vertex vertx2 = null;
		String s1 = combobox1.getSelectionModel().getSelectedItem();
		String s2 = combobox2.getSelectionModel().getSelectedItem();

		for (int i = 0; i < Country.size(); i++) {
			if (Country.get(i).getCountry().getName().equals(s1)) {
				vertx1 = Country.get(i);
			}
			if (Country.get(i).getCountry().getName().equals(s2)) {
				vertx2 = Country.get(i);
			}
		}

		if (vertx1 != null && vertx2 != null) {
			if (vertx1.equals(vertx2)) {// if sourse and target the same
				textarea.setText("No Path");// No Path
				textfield.setText("0");// No Distance

			} else {
				// Dijkstra's algorithm to find the shortest path between two vertex
				vertex destination = Dijkstra(vertx1, vertx2);
				if (destination == null || destination.previous == null) {// If there is no path or the destination is
																			// null
					textarea.setText("No Path");// No Path
					textfield.setText("0");// No Distance
				} else { // if there is path or distination is not null
					drawPath(destination);// draw path
					textfield.setText(String.valueOf(vertx2.distance));// display path
				}
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Source or target not found.");// If there is no Source or target in comboBoxs
			alert.showAndWait();
		}
	}

//Read file 
	public void readFile(File file) {
		try {
			Scanner sc = new Scanner(file);
			String[] l = sc.nextLine().split(" ");
			int numCounter = Integer.parseInt(l[0]);
			int numEdge = Integer.parseInt(l[1]);
			int count = 0;
			int num = 0;

			while (count < numCounter - 1) {
				String line = sc.nextLine();
				vertex ver = new vertex(new Country(line), num++);
				Country.add(ver);
				count++;

			}

			count = 0;
			while (count < numEdge) {
				String tokens[] = sc.nextLine().split(" ");
				for (int i = 0; i < Country.size(); i++) {
					if (Country.get(i).getCountry().getName().compareToIgnoreCase(tokens[0]) == 0) {
						for (int j = 0; j < Country.size(); j++) {

							if (Country.get(j).getCountry().getName().compareToIgnoreCase(tokens[1]) == 0) {

								Country.get(i).e.addLast(new edges(Country.get(i), Country.get(j),
										Distance(Country.get(i), Country.get(j))));

							}

						}
					}
				}
				count++;
			}
			sc.close();
		} catch (FileNotFoundException t) {
			System.out.println(t);
		}
	}

//Calculate distance between two vertex 
	public double Distance(vertex a, vertex b) {
		// Earth radius
		final double R = 6371.000;
		// convert latitude and longitude value for each vertex to Radians
		double lat1 = Math.toRadians(a.getCountry().getLatitude());
		double lat2 = Math.toRadians(b.getCountry().getLatitude());
		double lon1 = Math.toRadians(a.getCountry().getLongitude());
		double lon2 = Math.toRadians(b.getCountry().getLongitude());
		// different latitude and longitude value for each vertex
		double deltaLat = lat2 - lat1;
		double deltaLon = lon2 - lon1;
		double dis = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
				+ Math.cos(lat1) * Math.cos(lat2) * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(dis), Math.sqrt(1 - dis));

		return R * c;
	}

//Draw point on image 
	private void drawPoint() {
		for (i = 0; i < Country.size(); i++) {
			double x = Country.get(i).getCountry().getX();
			double y = Country.get(i).getCountry().getY();
			String name = Country.get(i).getCountry().getName();

			Circle circle = new Circle(3, Color.RED);
			circle.setUserData(name);
			circle.setCenterX(x);
			circle.setCenterY(y);

			circle.setOnMouseClicked(event -> handleCircleClick(Country.get(i).getCountry().getName()));
			circle.setOnMouseClicked(event -> {
				String selectedCountry = (String) circle.getUserData();
				if (combobox1.getValue() == null) {

					combobox1.setValue(selectedCountry);
					circle.setFill(Color.BLUE);
				} else if (combobox2.getValue() == null && !selectedCountry.equals(combobox1.getValue())) {

					combobox2.setValue(selectedCountry);
					circle.setFill(Color.GREEN);
				}

			});
			Text t = new Text(x + 1, y - 1, name);
			t.setFill(Color.BLACK);
			t.setFont(new Font(10));
			Pane1.getChildren().addAll(circle, t);

		}
	}

	// add country name in comboBox when circle click
	private void handleCircleClick(String CountryName) {

		if (combobox1.getValue() == null) {
			combobox1.setValue(CountryName);

		}

		else if (combobox2.getValue() == null) {
			combobox2.setValue(CountryName);
		}
	}

//Dijkstra's algorithm to find the shortest path between two vertex
	public vertex Dijkstra(vertex Source, vertex Target) {
		for (int i = 0; i < Country.size(); i++) {// Initialize distance,known and previous for each vertex
			Country.get(i).distance = Double.MAX_VALUE;
			Country.get(i).known = false;
			Country.get(i).previous = null;
		}
		Source.distance = 0;

		if (Source == Target) {// if Source and target are the same return null
			return null;
		}

		MinHeap<vertex> heap = new MinHeap<>();// initialize priorityQueue

		heap.add(Source);// add source to heap and give the lowest priority

		while (!heap.isEmpty()) {// if heap is not empty
			vertex u = heap.poll();// get top heap
			u.known = true;
			if (u.country.getName().equals(Target.getCountry().getName())) {// stopping if target is known
				break;
			}
			// looping the adjacent list
			for (int i = 0; i < u.getE().count; i++) {
				vertex v = u.getE().get(i).target;// target vertex of adjacent
				if (!v.known) {// if target vertex of adjacent is not known
					double distance = Distance(u, v);// calculate distance between top vertex and adjacent
					double distanceU = u.distance + distance;
					if (distanceU < v.distance) {
						v.distance = distanceU;
						v.previous = u;
						heap.add(v);// add the vertex v to heap
					}
				}
			}
		}

		return Target;
	}

//Draw path and display in textArea 
	private void drawPath(vertex Destination) {
		ArrayList<vertex> path = new ArrayList<>();
		for (vertex v = Destination; v != null; v = v.previous) {
			path.add(v);
		}
		path.reverse();

		StringBuilder pathText = new StringBuilder();
		/*for (int i = 0; i < path.size(); i++) {
			pathText.append(path.get(i).country.getName()).append(" -> ");
		}
		if (pathText.length() > 0) {
			pathText.setLength(pathText.length() - 4);
		}*/
		pathText.append("\n\n");
		for (int i = 0; i < path.size() - 1; i++) {
			pathText.append(path.get(i).country.getName()).append(" ->").append(path.get(i + 1).country.getName())
					.append("\n");
		}
		if (pathText.length() > 0 && pathText.charAt(pathText.length() - 1) == '\n') {
			pathText.setLength(pathText.length() - 1);
		}
		textarea.setText(pathText.toString());

		for (int i = 0; i < path.size() - 1; i++) {
			vertex u = path.get(i);
			vertex v = path.get(i + 1);

			Line line = new Line(u.country.getX(), u.country.getY(), v.country.getX(), v.country.getY());
			Pane1.getChildren().add(line);

		}

	}

// clear data 
	@FXML
	void reset(MouseEvent event) {
		combobox1.getSelectionModel().select(null);
		combobox2.getSelectionModel().select(null);
		textfield.setText("");
		textarea.setText(" ");
		image.toFront();
		for (int i = 0; i < Country.size(); i++) {
			Circle circle = new Circle(3, Color.RED);
			circle.setCenterX(Country.get(i).getCountry().getX());
			circle.setCenterY(Country.get(i).getCountry().getY());
		}
		for (i = 0; i < Country.size(); i++) {
			Country.get(i).previous = null;
			Country.get(i).known = false;
			Country.get(i).distance = Double.MAX_VALUE;
		}
		drawPoint();
	}

}
