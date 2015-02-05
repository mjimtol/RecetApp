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
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.stage.Stage;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.IngredienteItem;
import dad.recetapp.services.items.InstruccionItem;
import dad.recetapp.services.items.MedidaItem;
import dad.recetapp.services.items.TipoAnotacionItem;
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
	private TableColumn<TipoAnotacionItem, String> insOrdenColumn;
	@FXML
	private TableColumn<TipoAnotacionItem, String> insDescripcionColumn;

	private Stage stage;	
	
	private NuevaRecetaController recetasController;
	
	private Tab parentTab;
	
	@FXML
	public void initialize() {
		try{
			closeTabButton.setBackground(Background.EMPTY);
			
			ingCantidadColumn.setCellValueFactory(new PropertyValueFactory<TipoIngredienteItem, String>("cantidad"));
			ingCantidadColumn.setCellFactory(TextFieldTableCell.<TipoIngredienteItem>forTableColumn());
			
			ingMedidaColumn.setCellValueFactory(new PropertyValueFactory<TipoIngredienteItem, String>("id_medida"));
			ingMedidaColumn.setCellFactory(TextFieldTableCell.<TipoIngredienteItem>forTableColumn());
			
			ingTipoColumn.setCellValueFactory(new PropertyValueFactory<TipoIngredienteItem, String>("id_tipo"));
			ingTipoColumn.setCellFactory(TextFieldTableCell.<TipoIngredienteItem>forTableColumn());
			
			
			insOrdenColumn.setCellValueFactory(new PropertyValueFactory<TipoAnotacionItem, String>("descripcion"));
			insOrdenColumn.setCellFactory(TextFieldTableCell.<TipoAnotacionItem>forTableColumn());
			
			insDescripcionColumn.setCellValueFactory(new PropertyValueFactory<TipoAnotacionItem, String>("orden"));
			insDescripcionColumn.setCellFactory(TextFieldTableCell.<TipoAnotacionItem>forTableColumn());
			
		}catch(Exception e){}
	}

	/**************************** INSTRUCCION ****************************/

	@FXML private TextField ordenText;
	@FXML private TextField descText;
	
	@FXML
	private void AddInstruccion(){
		try{
			Stage stage = new Stage();
			stage.setTitle("Nueva instrucción para " + seccionText.getText());
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
			stage.setTitle("Editar instrucción para " + seccionText.getText());
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
		parentTab.getTabPane().getTabs().remove(parentTab);
	}
	
	@FXML
	private void guardarInstruccion(){
		InstruccionItem instruccion = new InstruccionItem();
		instruccion.setOrden(Integer.parseInt(ordenText.getText()));
		instruccion.setDescripcion(descText.getText());
	}
	
	private void cargarDBInstrucciones() {
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
	private ComboBox<TipoIngredienteItem> tipoCombo = new ComboBox<TipoIngredienteItem>();
	
	private List<TipoIngredienteItem> tipoIngredientes = new ArrayList<TipoIngredienteItem>();
	private ObservableList<TipoIngredienteItem> tipoIngredientesList = FXCollections.observableList(tipoIngredientes);

	private List<MedidaItem> medidas = new ArrayList<MedidaItem>();
	private ObservableList<MedidaItem> medidasList = FXCollections.observableList(medidas);
	
	@FXML
	private void AddIngrediente(){		
		try{
			cargarDBIngredientes();		
			stage = new Stage();
			stage.setTitle("Nuevo ingrediente para " + seccionText.getText());
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
	private void EditIngrediente(){
		try{
			Stage stage = new Stage();
			stage.setTitle("Editar ingrediente " + seccionText.getText());
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
	private void guardarIngrediente(){
		IngredienteItem ingrediente = new IngredienteItem();
		
		ingrediente.setCantidad(Integer.parseInt(cantidadText.getText()));
		ingrediente.setMedida(medidaCombo.getSelectionModel().getSelectedItem());
		
		TipoIngredienteItem tipoIng = new TipoIngredienteItem();
		tipoIng.setNombre(tipoCombo.getSelectionModel().getSelectedItem().getNombre());
		tipoIng.setId(tipoCombo.getSelectionModel().getSelectedItem().getId());
		
		ingrediente.setTipo(tipoIng);
		
//		try {
//			ServiceLocator.getITiposIngredientesService().crearTipoIngrediente(tipoIngrediente);
//		} catch (ServiceException e) {
//			e.printStackTrace();
//		}
	}
	
	private void cargarDBIngredientes() {
//		tipoIngredientes = new ArrayList<TipoIngredienteItem>();
//		tipoIngredientesList.clear();
//		medidas = new ArrayList<MedidaItem>();
//		medidasList.clear();
//		try {
//			tipoIngredientes = ServiceLocator.getITiposIngredientesService().listarTipoIngrediente();
//			for (TipoIngredienteItem c: tipoIngredientes)
//			{	tipoIngredientesList.add(c);	}
//			medidas = ServiceLocator.getIMedidasService().listarMedidas();
//			for (MedidaItem m: medidas)
//			{	medidasList.add(m);		}			
//		} catch (ServiceException e) {
//			e.printStackTrace();
//		}
//		tipoCombo.getItems().addAll(tipoIngredientesList);
//		medidaCombo.getItems().addAll(medidasList);
		
		

		List<MedidaItem> categorias = new ArrayList<MedidaItem>(); 
		try { 
			categorias = ServiceLocator.getIMedidasService().listarMedidas(); 
		} catch (ServiceException e) { 
//			Logs.log(e); 
		} 
		medidasList = FXCollections.observableArrayList(); 
		medidasList.addAll(categorias); 


		List<TipoIngredienteItem> tipoIngredientes = new ArrayList<TipoIngredienteItem>(); 
		try { 
			tipoIngredientes = ServiceLocator.getITiposIngredientesService().listarTipoIngrediente(); 
		} catch (ServiceException e) { 
//			Logs.log(e); 
		} 
		tipoIngredientesList = FXCollections.observableArrayList(); 
		tipoIngredientesList.addAll(tipoIngredientes); 

		tipoCombo.getItems().addAll(tipoIngredientesList);
	}
	
	/****/
	
	public void setRecetaController(NuevaRecetaController recetaController) {
		this.recetasController = recetaController;
	}
	
	@FXML
	private void cerrar(){
		Stage stage = (Stage) root.getScene().getWindow(); 
		stage.close();
	}
		
	
	/***************************************************************************************/
	
	public Tab getParentTab() {
		return parentTab;
	}

	public void setParentTab(Tab parentTab) {
		this.parentTab = parentTab;
	}
	
}