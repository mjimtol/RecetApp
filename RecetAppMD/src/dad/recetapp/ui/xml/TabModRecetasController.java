package dad.recetapp.ui.xml;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.stage.Stage;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.MedidaItem;
import dad.recetapp.services.items.TipoIngredienteItem;

public class TabModRecetasController {

	@FXML
	private Parent root;
	
	@FXML
	private TextField seccionText;
	@FXML
	private ImageView closeTabImage;
	@FXML
	private Button closeTabButton;
	
	@FXML
	private TableView<MedidaItem> ingredientesTable;
	@FXML
	private TableColumn<TipoIngredienteItem, String> ingCantidadColumn;
	@FXML
	private TableColumn<TipoIngredienteItem, String> ingMedidaColumn;
	@FXML
	private TableColumn<TipoIngredienteItem, String> ingTipoColumn;

	@FXML
	private TableView<MedidaItem> instruccionsTable;
	@FXML
	private TableColumn<TipoIngredienteItem, String> insOrdenColumn;
	@FXML
	private TableColumn<TipoIngredienteItem, String> insDescripcionColumn;

	private Stage stage;	
	
	NuevaRecetaController recetaController;
	
	@FXML
	public void initialize() {
		try{
			closeTabButton.setBackground(Background.EMPTY);
		}catch(Exception e){}
	}

	/**************************** INSTRUCCION ****************************/

	@FXML
	private void AddInstruccion(){
		try{
			Stage stage = new Stage();
			stage.setTitle("Nueva instrucción para 'X'");
			stage.getIcons().add(new Image(getClass().getResourceAsStream("../images/logo.png")));
			Parent root = FXMLLoader.load(getClass().getResource("Instruccion.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void EditInstruccion(){
		try{
			Stage stage = new Stage();
			stage.setTitle("Editar instrucción para 'X'");
			stage.getIcons().add(new Image(getClass().getResourceAsStream("../images/logo.png")));
			Parent root = FXMLLoader.load(getClass().getResource("Instruccion.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void suprInstruccion(){

	}	


	@FXML
	private void DeleteTab(){

	}
	
	/*******/
	
	@FXML
	private void onMouseOver()
	{	closeTabImage.setImage(new Image("dad/recetapp/ui/images/closeTabIcon.png"));	}

	@FXML
	private void onMouseExit()
	{	closeTabImage.setImage(new Image("dad/recetapp/ui/images/closeTabOverIcon.png"));	}
	
	
	/**************************** INGREDIENTE ****************************/
	
	@FXML
	private TextField cantidadText;
	@FXML
	private ComboBox<MedidaItem> medidaCombo;
	@FXML
	private ComboBox<String> tipoCombo = new ComboBox<String>();
	
	private List<TipoIngredienteItem> tipoIngredientes = new ArrayList<TipoIngredienteItem>();
	private ObservableList<TipoIngredienteItem> tipoIngredientesList = FXCollections.observableList(tipoIngredientes);

	@FXML
	private void AddIngrediente(){
		cargarDBIngredientes();
		try{
			stage = new Stage();
			stage.setTitle("Nuevo ingrediente para 'X'");
			stage.getIcons().add(new Image(getClass().getResourceAsStream("../images/logo.png")));
			Parent root = FXMLLoader.load(getClass().getResource("Ingrediente.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (TipoIngredienteItem c: tipoIngredientesList)
			tipoCombo.getItems().add(c.getNombre());
	}

	@FXML
	private void EditIngrediente(){
		try{
			Stage stage = new Stage();
			stage.setTitle("Editar ingrediente 'X'");
			stage.getIcons().add(new Image(getClass().getResourceAsStream("../images/logo.png")));
			Parent root = FXMLLoader.load(getClass().getResource("Ingrediente.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void suprIngrediente(){

	}
	
	private void cargarDBIngredientes() {
		tipoIngredientes = new ArrayList<TipoIngredienteItem>();
		tipoIngredientesList.clear();
		try {
			tipoIngredientes = ServiceLocator.getITiposIngredientesService().listarTipoIngrediente();
			for (TipoIngredienteItem c: tipoIngredientes)
			{	
				tipoIngredientesList.add(c);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
	}
	
	/****/
	
	public void setRecetaController(NuevaRecetaController recetaController) {
		this.recetaController = recetaController;
	}
	
	@FXML
	private void cerrar(){
		Stage stage = (Stage) root.getScene().getWindow(); 
		stage.close();
	}
}


