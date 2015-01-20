package dad.recetapp.ui.xml;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TabRecetasController {
	
	@FXML
	private void nuevaReceta(){
	    try {
	    	Stage stage = new Stage();
	    	Parent root = FXMLLoader.load(getClass().getResource("NuevaReceta.fxml"));
	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.show();
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
}
