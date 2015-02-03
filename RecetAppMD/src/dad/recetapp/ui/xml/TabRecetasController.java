package dad.recetapp.ui.xml;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.RecetaListItem;
import dad.recetapp.services.items.TipoAnotacionesItem;

public class TabRecetasController{
	private List<RecetaListItem> recetas = new ArrayList<RecetaListItem>();
	private ObservableList<RecetaListItem> recetasList = FXCollections.observableList(recetas);
	
	private List<TipoAnotacionesItem> categorias = new ArrayList<TipoAnotacionesItem>();
	private ObservableList<TipoAnotacionesItem> categoriasList = FXCollections.observableList(categorias);
		
	private RecetaListItem recetaSeleccionada;
	
	@FXML
	private TableView<RecetaListItem> recetasTableView;
	@FXML
	private TableColumn<RecetaListItem, String> nombreColumn;
	@FXML
	private TableColumn<RecetaListItem, String> paraColumn;
	@FXML
	private TableColumn<RecetaListItem, String> tiempototalColumn;
	@FXML
	private TableColumn<RecetaListItem, String> fechacreacionColumn;
	@FXML
	private TableColumn<RecetaListItem, String> categoriaColumn;
	@FXML
	private TextField nombreText;
	@FXML
	private ComboBox<Integer> minutosCombobox;
	@FXML
	private ComboBox<Integer> segundosCombobox;
	@FXML
	private ComboBox<String> categoriaCombobox;
	
	@FXML
	public void initialize() {	
		cargarCombos();
		cargarDB();
				
		recetasTableView.setItems(recetasList);

		nombreColumn.setCellValueFactory(new PropertyValueFactory<RecetaListItem, String>("nombre"));
		nombreColumn.setCellFactory(TextFieldTableCell.<RecetaListItem>forTableColumn());
		
		paraColumn.setCellValueFactory(new PropertyValueFactory<RecetaListItem, String>("para"));
		paraColumn.setCellFactory(TextFieldTableCell.<RecetaListItem>forTableColumn());
		
//		tiempototalColumn.setCellValueFactory(new PropertyValueFactory<RecetaListItem, String>("tiempoTotal"));
//		tiempototalColumn.setCellFactory(TextFieldTableCell.<RecetaListItem>forTableColumn());
		
//		fechacreacionColumn.setCellValueFactory(new PropertyValueFactory<RecetaListItem, Date>("fechaCreacion"));
//		fechacreacionColumn.setCellFactory(TextFieldTableCell.<RecetaListItem, Date> forTableColumn(new DateStringConverter("dd/MM/yyyy")));
		
		
		categoriaColumn.setCellValueFactory(new PropertyValueFactory<RecetaListItem, String>("categoria"));
		categoriaColumn.setCellFactory(TextFieldTableCell.<RecetaListItem>forTableColumn());
	}
	
	private void cargarCombos() {
		int i = 0;
		List <Integer> minutos = new ArrayList<Integer>();
		for (; i <= 60; i++)
			minutos.add(i);
		segundosCombobox.getItems().addAll(minutos);
		segundosCombobox.setValue(0);
		
		for (; i <= 120; i++)
			minutos.add(i);
		minutosCombobox.getItems().addAll(minutos);
		minutosCombobox.setValue(0);
		
		for (TipoAnotacionesItem c: categorias)
			categoriaCombobox.getItems().add(c.getDescripcion());	
	}

	@FXML
	private void nuevaReceta(){
	    try {
	    	Stage stage = new Stage();
	    	stage.setTitle("Nueva Receta");
	    	stage.getIcons().add(new Image(getClass().getResourceAsStream("../images/logo.png")));
	    	Parent root = FXMLLoader.load(getClass().getResource("NuevaReceta.fxml"));
	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.show();
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
	@FXML
	private void eliminarReceta(){
		RecetaListItem receta = recetasTableView.getSelectionModel().getSelectedItem();
		try {
			ServiceLocator.getIRecetasService().eliminarReceta(receta.getId());
			cargarDB();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void editarReceta(){
		try {
			RecetaListItem receta = recetasTableView.getSelectionModel().getSelectedItem();

			if (receta != null)
			{
				setRecetaSeleccionada(receta);

				FXMLLoader loader = new FXMLLoader(TabRecetasController.class.getResource("NuevaReceta.fxml"));	 

				AnchorPane rootPane = (AnchorPane) loader.load();	    	
				Scene scene = new Scene(rootPane);

				Stage stagePrincipal = new Stage();
				stagePrincipal.setTitle("Editar Receta");
				stagePrincipal.setScene(scene);

				NuevaRecetaController controller = loader.getController();
				controller.setRecetasController(this);

				stagePrincipal.show();
			}
			else
			{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Atención");
				alert.setHeaderText("Problemas al intentar editar");
				alert.setContentText("Debe escoger una receta primero");

				alert.showAndWait();
			}
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
	@FXML
	public void cargarDB() {
		recetas = new ArrayList<RecetaListItem>();
		recetasList.clear();		
		
		categorias = new ArrayList<TipoAnotacionesItem>();
		categoriasList.clear();
		try {
			categorias = ServiceLocator.getICategoriasService().listarCategorias();
			for (TipoAnotacionesItem c: categorias)
				categoriasList.add(c);
			recetas = ServiceLocator.getIRecetasService().listarRecetas();
			for (RecetaListItem r: recetas)
				recetasList.add(r);
		} catch (ServiceException e) {
			e.printStackTrace();
		}		
	}
	
	//Cada vez que se cambie una categoria hay que cambiar el comboBox (al hacer click)
	@FXML
	private void recargarCategorias(){
		cargarDB();
		categoriaCombobox.getItems().clear();
		for (TipoAnotacionesItem c: categorias)
			categoriaCombobox.getItems().add(c.getDescripcion());	
	}

	public RecetaListItem getRecetaSeleccionada() {
		return recetaSeleccionada;
	}

	public void setRecetaSeleccionada(RecetaListItem recetaSeleccionada) {
		this.recetaSeleccionada = recetaSeleccionada;
	}
	
}