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
import javafx.stage.Stage;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.CategoriaItem;
import dad.recetapp.services.items.RecetaItem;
import dad.recetapp.services.items.RecetaListItem;

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
	@FXML
	public void initialize() {	
		try {
			recetas = Sl.getIRecetasService().listarRecetas();
			for (RecetaListItem c: recetas)
				recetas.add(c);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		recetasTableView.setItems(recetasList);
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