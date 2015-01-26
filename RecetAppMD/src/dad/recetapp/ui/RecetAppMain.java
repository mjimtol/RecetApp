package dad.recetapp.ui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import javax.swing.Timer;

public class RecetAppMain extends Application {

	private Stage primaryStage;
	private AnchorPane rootLayout;
	private Timeline timeline;
	private Timer timer;
	
	@Override
	public void start(Stage primaryStage) throws SQLException {
		this.primaryStage = primaryStage;
		initApp();
	}

	private void initApp() {
		mostrarVentanaPrincipal();
/*
		timer = new Timer (4000, new ActionListener () 
		{ 
			public void actionPerformed(ActionEvent e) 
			{ 
				mostrarVentanaSecundaria();
			} 
		}); 
		timer.start();
*/
		
		timeline = new Timeline(new KeyFrame(
				Duration.millis(4000),
				ae -> mostrarVentanaSecundaria()));
		timeline.play();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void mostrarVentanaPrincipal() {
		try {
			URL url = getClass().getResource("../resources/Harmony.mp3");
			Media media = new Media(url.toString());
			MediaPlayer mediaPlayer = new MediaPlayer(media);
			mediaPlayer.play();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(RecetAppMain.class.getResource("xml/PantallaCarga.fxml"));
			rootLayout = (AnchorPane) loader.load();

			Scene scene = new Scene(rootLayout);
			scene.setFill(Color.TRANSPARENT);
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("images/logo.png"))); //icono
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void mostrarVentanaSecundaria() {
		try {
			primaryStage.close();
			Stage stage = new Stage();
			stage.setTitle("RecetApp"); //titulo
			stage.getIcons().add(new Image(getClass().getResourceAsStream("images/logo.png"))); //icono
			Parent root = FXMLLoader.load(getClass().getResource("xml/RecetAppFrame.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Mostrar ventana principal al hacer click en la imagen
	@FXML
	private void handleClick(){
		System.out.println("Click");
		timeline.stop();
		mostrarVentanaSecundaria();
	}
}
