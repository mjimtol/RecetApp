package dad.recetapp.ui.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.impl.TipoIngredienteService;
import dad.recetapp.services.items.TipoAnotacionesItem;
import dad.recetapp.services.items.TipoIngredienteItem;
import dad.recetapp.services.items.TipoIngredienteItem;
import dad.recetapp.services.items.TipoIngredienteItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class TabIngredientesController {

	//TipoIngredienteService IS = new TipoIngredienteService();
	
	private List<TipoIngredienteItem> tipoIngredientes = new ArrayList<TipoIngredienteItem>();

	// lista "observable" que envuelve a la lista "variables" 
	private ObservableList<TipoIngredienteItem> tipoIngredientesList = FXCollections.observableList(tipoIngredientes);

	@FXML
	private TableView<TipoIngredienteItem> tipoIngredientesTable;

	@FXML
	private TableColumn<TipoIngredienteItem, String> nombreColumn;

	@FXML
	private TextField ing_nombreText;
	
	@FXML
	public void initialize() {	
		try {
			tipoIngredientes = ServiceLocator.getITiposIngredientesService().listarTipoIngrediente();
			for (TipoIngredienteItem i: tipoIngredientes)
				tipoIngredientesList.add(i);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		tipoIngredientesTable.setItems(tipoIngredientesList);

		nombreColumn.setCellValueFactory(new PropertyValueFactory<TipoIngredienteItem, String>("Nombre"));
		nombreColumn.setCellFactory(TextFieldTableCell.<TipoIngredienteItem>forTableColumn());
		nombreColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TipoIngredienteItem,String>>() {
			public void handle(CellEditEvent<TipoIngredienteItem, String> t) {
				((TipoIngredienteItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).setNombre(t.getNewValue());
				modificar();
			};
		});
	}
	private void cargarDB() {
		tipoIngredientes = new ArrayList<TipoIngredienteItem>();
		tipoIngredientesList.clear();
		try {
			tipoIngredientes = ServiceLocator.getITiposIngredientesService().listarTipoIngrediente();
			for (TipoIngredienteItem c: tipoIngredientes)
				tipoIngredientesList.add(c);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void anadir() {
		if (!ing_nombreText.getText().equals(""))
		{
			TipoIngredienteItem tipoIngrediente = new TipoIngredienteItem();
			tipoIngrediente.setNombre(ing_nombreText.getText());

			try {
				ServiceLocator.getITiposIngredientesService().crearTipoIngrediente(tipoIngrediente);
				cargarDB();
			} catch (ServiceException e) {
				e.printStackTrace();
			}

			ing_nombreText.clear();
		}
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Atención");
			alert.setHeaderText("Problemas al introducir tipo de ingrediente");
			alert.setContentText("La descripción no puede estar en blanco");

			alert.showAndWait();
		}
	}

	@FXML
	public void eliminar() {
		TipoIngredienteItem item = tipoIngredientesTable.getSelectionModel().getSelectedItem();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar");
		alert.setHeaderText("Eliminando " + item.getNombre());
		alert.setContentText("¿Está seguro que desea eliminarlo?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				ServiceLocator.getITiposIngredientesService().eliminarTipoIngrediente(item.getId());
				tipoIngredientesList.remove(item);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		} 
	}
	@FXML
	public void modificar(){
		//System.out.println("Modificando");
		TipoIngredienteItem item = tipoIngredientesTable.getSelectionModel().getSelectedItem();
		try {
			ServiceLocator.getITiposIngredientesService().modificarTipoIngrediente(item);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}
