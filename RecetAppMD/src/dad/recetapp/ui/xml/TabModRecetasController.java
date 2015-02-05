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
import javafx.scene.control.SelectionMode;
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

	@FXML	private Parent root;
	
	@FXML	private TextField seccionText;
	@FXML	private ImageView closeTabImage;
	@FXML	private Button closeTabButton;
	
	@FXML	private TableView<IngredienteItem> ingredientesTable;
	@FXML	private TableColumn<IngredienteItem, String> ingCantidadColumn;
	@FXML	private TableColumn<IngredienteItem, String> ingMedidaColumn;
	@FXML	private TableColumn<IngredienteItem, String> ingTipoColumn;

	@FXML	private TableView<TipoAnotacionItem> instruccionsTable;
	@FXML	private TableColumn<TipoAnotacionItem, String> insOrdenColumn;
	@FXML	private TableColumn<TipoAnotacionItem, String> insDescripcionColumn;

	private Stage stage;
	private NuevaRecetaController recetasController;
	private Tab parentTab;
	
	private List<IngredienteItem> ingredientes = new ArrayList<IngredienteItem>();
	private ObservableList<IngredienteItem> ingredientesTableList = FXCollections.observableList(ingredientes);
	
	@FXML
	public void initialize() {
		try{
			ingredientesTable.setItems(ingredientesTableList);
			ingredientesTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			
			closeTabButton.setBackground(Background.EMPTY);
			
			ingCantidadColumn.setCellValueFactory(new PropertyValueFactory<IngredienteItem, String>("cantidad"));
			ingCantidadColumn.setCellFactory(TextFieldTableCell.<IngredienteItem>forTableColumn());
			
			ingMedidaColumn.setCellValueFactory(new PropertyValueFactory<IngredienteItem, String>("id_medida"));
			ingMedidaColumn.setCellFactory(TextFieldTableCell.<IngredienteItem>forTableColumn());
			
			ingTipoColumn.setCellValueFactory(new PropertyValueFactory<IngredienteItem, String>("id_tipo"));
			ingTipoColumn.setCellFactory(TextFieldTableCell.<IngredienteItem>forTableColumn());
			
			
			insOrdenColumn.setCellValueFactory(new PropertyValueFactory<TipoAnotacionItem, String>("descripcion"));
			insOrdenColumn.setCellFactory(TextFieldTableCell.<TipoAnotacionItem>forTableColumn());
			
			insDescripcionColumn.setCellValueFactory(new PropertyValueFactory<TipoAnotacionItem, String>("orden"));
			insDescripcionColumn.setCellFactory(TextFieldTableCell.<TipoAnotacionItem>forTableColumn());
			
		}catch(Exception e){}
	}

	@FXML public void onSeccionTextChange(){
		parentTab.setText(seccionText.getText());
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
	private ComboBox<String> medidaCombo;
	@FXML
	private ComboBox<String> tipoCombo;// = new ComboBox<TipoIngredienteItem>();
	
	private List<TipoIngredienteItem> tipoIngredientes = new ArrayList<TipoIngredienteItem>();
	private ObservableList<TipoIngredienteItem> tipoIngredientesList = FXCollections.observableList(tipoIngredientes);

	private List<MedidaItem> medidas = new ArrayList<MedidaItem>();
	private ObservableList<MedidaItem> medidasList = FXCollections.observableList(medidas);
	
	@FXML
	private void AddIngrediente(){		
		ServiceLocator.getIRecetasService();		
		try{			
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

	@FXML private void cargarCombos(){		
		if (tipoCombo.getItems().size() == 0 && medidaCombo.getItems().size() == 0)
		{
			cargarDBIngredientes();
			for (TipoIngredienteItem i : tipoIngredientesList)
				tipoCombo.getItems().add(i.getNombre());
			for (MedidaItem m: medidasList)
			medidaCombo.getItems().add(m.getNombre());
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
		MedidaItem medida = (MedidaItem) getItems(1,medidaCombo.getSelectionModel().getSelectedItem());
		
		ingrediente.setMedida(medida);
		
		TipoIngredienteItem tipoIng = (TipoIngredienteItem) getItems(2,tipoCombo.getSelectionModel().getSelectedItem());
		
		ingrediente.setTipo(tipoIng);		
		
//		try {
//			ServiceLocator.getITiposIngredientesService().crearTipoIngrediente(tipoIngrediente);
//		} catch (ServiceException e) {
//			e.printStackTrace();
//		}
		
		ingredientesTableList.add(ingrediente);
		
		System.out.println(ingrediente.getCantidad() + ", " + ingrediente.getMedida().getNombre() + ", " + ingrediente.getTipo().getNombre());
		cerrar();
	}
	
	private Object getItems(int tipo, String nombre){
		if (tipo == 1)
		{
			for (MedidaItem m : medidasList)
				if (m.getNombre().equals(nombre))
					return m;
		}
		else
		{
			for (TipoIngredienteItem i : tipoIngredientesList)
				if (i.getNombre().equals(nombre))
					return i;
		}
		
		return null;
	}
	
	private void cargarDBIngredientes() {		
		try {
			List<TipoIngredienteItem> tipoIngredientes = ServiceLocator.getITiposIngredientesService().listarTipoIngrediente();  
			for (TipoIngredienteItem t: tipoIngredientes)
				tipoIngredientesList.add(t);
			List <MedidaItem> medidas = ServiceLocator.getIMedidasService().listarMedidas();
			for (MedidaItem m: medidas)
			{	medidasList.add(m);	}			
		} catch (ServiceException e) {
			e.printStackTrace();
		}
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
		seccionText.setText(parentTab.getText());
	}
	
}