package dad.recetapp.ui.xml;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.MedidaItem;
import dad.recetapp.services.items.TipoAnotacionesItem;
import dad.recetapp.services.items.TipoIngredienteItem;

public class TabModRecetasController {

	@FXML
	private TextField seccionText;

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
	
	@FXML
	public void initialize() {	
		
	}

	@FXML
	private void AddIngrediente(){
		cargarDBIngredientes();
		try{
			stage = new Stage();
			stage.setTitle("Nuevo iIngrediente para 'X'");
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

	
	/**************************** INGREDIENTE ****************************/
	@FXML
	private TextField cantidadText;
	@FXML
	private ComboBox medidaCombo;
	@FXML
	private ComboBox tipoCombo = new ComboBox<TipoIngredienteItem>();
	
	private List<TipoIngredienteItem> tipoIngredientes = new ArrayList<TipoIngredienteItem>();
	private ObservableList<TipoIngredienteItem> tipoIngredientesList = FXCollections.observableList(tipoIngredientes);

	@FXML
	private void crearIngr(){
		//ingredientesTable
	}
	
	@FXML
	private void cancelar(){
		System.out.println("Cancelar");
		stage.close();
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
	
	
	/**************************** INSTRUCCION ****************************/
	
}


