package dad.recetapp.ui.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.impl.CategoriasService;
import dad.recetapp.services.impl.TipoAnotacionService;
import dad.recetapp.services.items.AnotacionItem;
import dad.recetapp.services.items.TipoAnotacionesItem;
import dad.recetapp.services.items.TipoAnotacionItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class TabAnotacionesController {
	
	private TipoAnotacionService AS = new TipoAnotacionService();
	
	// lista que contiene los datos
	private List<TipoAnotacionItem> anotaciones = new ArrayList<TipoAnotacionItem>();

	// lista "observable" que envuelve a la lista "variables" 
	private ObservableList<TipoAnotacionItem> anotacionesList = FXCollections.observableList(anotaciones);

	@FXML
	private TableView<TipoAnotacionItem> anotacionesTable;

	@FXML
	private TableColumn<TipoAnotacionItem, String> descripcionColumn;

	@FXML
	private TextField descripcionText;
	

	@FXML
	public void initialize() {	
		try {
			anotaciones = AS.listarTipoAnotacion();
			for (TipoAnotacionItem a: anotaciones)
				anotacionesList.add(a);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		anotacionesTable.setItems(anotacionesList);

		descripcionColumn.setCellValueFactory(new PropertyValueFactory<TipoAnotacionItem, String>("Descripcion"));
		descripcionColumn.setCellFactory(TextFieldTableCell.<TipoAnotacionItem>forTableColumn());
		descripcionColumn.setOnEditCommit(t -> ((TipoAnotacionItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDescripcion(t.getNewValue()));
	}

	@FXML
	public void anadir() {
		if (!descripcionText.getText().equals(""))
		{
			TipoAnotacionItem anotacion = new TipoAnotacionItem();
			anotacion.setDescripcion(descripcionText.getText());

			try {
				AS.crearTipoAnotacion(anotacion);
				anotacionesList.add(anotacion);
			} catch (ServiceException e) {
				e.printStackTrace();
			}

			descripcionText.clear();
		}
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Atención");
			alert.setHeaderText("Problemas al introducir categoría");
			alert.setContentText("La descripción no puede estar en blanco");

			alert.showAndWait();
		}
	}

	@FXML
	public void eliminar() {
		TipoAnotacionItem item = anotacionesTable.getSelectionModel().getSelectedItem();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar");
		alert.setHeaderText("Eliminando " + item.getDescripcion());
		alert.setContentText("¿Está seguro que desea eliminarlo?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				AS.eliminarTipoAnotacion(item.getId());
				anotacionesList.remove(item);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		} 
	}	
	
	
}
