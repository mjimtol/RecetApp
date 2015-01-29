package dad.recetapp.ui.xml;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.RecetaListItem;
import dad.recetapp.services.items.TipoAnotacionItem;
import dad.recetapp.services.items.TipoAnotacionesItem;

public class TabRecetasController {
	ServiceLocator Sl = new ServiceLocator();
	
	private List<RecetaListItem> recetas = new ArrayList<RecetaListItem>();
	private ObservableList<RecetaListItem> recetasList = FXCollections.observableList(recetas);
	
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
	private ComboBox minutosCombobox;
	@FXML
	private ComboBox segundosCombobox;
	@FXML
	private ComboBox categoriaCombobox;

	// lista que contiene los datos
	private List<TipoAnotacionesItem> categorias = new ArrayList<TipoAnotacionesItem>();

	// lista "observable" que envuelve a la lista "variables" 
	private ObservableList<TipoAnotacionesItem> categoriasList = FXCollections.observableList(categorias);

	@FXML
	public void initialize() {	
		cargarDB();
		
		int i = 1;
		List <Integer> minutos = new ArrayList<Integer>();
		for (; i <= 60; i++)
			minutos.add(i);
		segundosCombobox.getItems().addAll(minutos);
		
		for (; i <= 120; i++)
			minutos.add(i);
		minutosCombobox.getItems().addAll(minutos);
		
		for (TipoAnotacionesItem c: categorias)
			categoriaCombobox.getItems().add(c.getDescripcion());				
		
		recetasTableView.setItems(recetasList);

		nombreColumn.setCellValueFactory(new PropertyValueFactory<RecetaListItem, String>("nombre"));
		nombreColumn.setCellFactory(TextFieldTableCell.<RecetaListItem>forTableColumn());
		
		paraColumn.setCellValueFactory(new PropertyValueFactory<RecetaListItem, String>("para"));
		paraColumn.setCellFactory(TextFieldTableCell.<RecetaListItem>forTableColumn());
		
//		tiempototalColumn.setCellValueFactory(new PropertyValueFactory<RecetaListItem, String>("tiempoTotal"));
//		tiempototalColumn.setCellFactory(TextFieldTableCell.<RecetaListItem>forTableColumn());
		
//		fechacreacionColumn.setCellValueFactory(new PropertyValueFactory<RecetaListItem, Date>("fechaCreacion"));
//		fechacreacionColumn.setCellFactory(TextFieldTableCell.<RecetaListItem>forTableColumn());
		
		categoriaColumn.setCellValueFactory(new PropertyValueFactory<RecetaListItem, String>("categoria"));
		categoriaColumn.setCellFactory(TextFieldTableCell.<RecetaListItem>forTableColumn());
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
	private void editarReceta(){
	    try {
	    	Stage stage = new Stage();
	    	stage.setTitle("Editar Receta");
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
	private void cargarDB() {
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
	
		
	public List<TipoAnotacionesItem> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<TipoAnotacionesItem> categorias) {
		this.categorias = categorias;
	}
}


/*package dad.recetapp.ui.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.impl.RecetasService;
import dad.recetapp.services.items.CategoriaItem;
import dad.recetapp.services.items.RecetaItem;
import dad.recetapp.services.items.RecetaListItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class TabRecetasController {
	
	private RecetasService RS = new RecetasService();
	
	//lista con los datos
	private List<RecetaListItem> recetas = new ArrayList<RecetaListItem>();
	
	private ObservableList<RecetaListItem> recetasList = FXCollections.observableArrayList(recetas);
	//Table
	@FXML
	private TableView<RecetaListItem> recetasTable;
	//Columnas
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
	private ComboBox minutosCombobox;
	@FXML
	private ComboBox segundosCombobox;
	@FXML
	private ComboBox categoriaCombobox;
	//Cargamos los datos
	@FXML
	public void initialize() {	
		try {
			recetas = RS.listarRecetas();
			for (RecetaListItem c: recetas)
				recetas.add(c);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		recetasTable.setItems(recetasList);
	}
	@FXML
	public void eliminar() {
		RecetaListItem item = recetasTable.getSelectionModel().getSelectedItem();
		recetasList.remove(item);
	}	
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
*/