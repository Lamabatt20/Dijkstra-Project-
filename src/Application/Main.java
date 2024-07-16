package Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		Application.launch(args);

	}

	@Override
	public void start(Stage arg0) throws Exception {
		arg0.setMaximized(true);
		Parent root=FXMLLoader.load(getClass().getResource("World.fxml"));
		Scene s=new Scene(root);
		arg0.setScene(s);
		arg0.show();
		
	}
}
