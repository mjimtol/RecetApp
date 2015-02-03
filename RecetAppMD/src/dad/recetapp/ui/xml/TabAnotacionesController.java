package dad.recetapp.ui.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.impl.CategoriasService;
import dad.recetapp.services.impl.TipoAnotacionService;
import dad.recetapp.services.items.AnotacionItem;
import dad.recetapp.services.items.TipoAnotacionesItem;
import dad.recetapp.services.items.TipoAnotacionItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class TabAnotacionesController {
	
	//private TipoAnotacionService AS = new TipoAnotacionService();
	
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
		anotacionesTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		try {
			anotaciones = ServiceLocator.getITiposAnotacionesService().listarTipoAnotacion();
			for (TipoAnotacionItem a: anotaciones)
				anotacionesList.add(a);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		anotacionesTable.setItems(anotacionesList);

		descripcionColumn.setCellValueFactory(new PropertyValueFactory<TipoAnotacionItem, String>("Descripcion"));
		descripcionColumn.setCellFactory(TextFieldTableCell.<TipoAnotacionItem>forTableColumn());
		descripcionColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TipoAnotacionItem,String>>() {
			public void handle(CellEditEvent<TipoAnotacionItem, String> t) {
				((TipoAnotacionItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDescripcion(t.getNewValue());
				modificar();
			};
		});
	}
	private void cargarDB() {
		anotaciones = new ArrayList<TipoAnotacionItem>();
		anotacionesList.clear();
		try {
			anotaciones = ServiceLocator.getITiposAnotacionesService().listarTipoAnotacion();
			for (TipoAnotacionItem c: anotaciones)
				anotacionesList.add(c);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void anadir() {
		if (!descripcionText.getText().equals(""))
		{
			TipoAnotacionItem anotacion = new TipoAnotacionItem();
			anotacion.setDescripcion(descripcionText.getText());

			try {
				ServiceLocator.getITiposAnotacionesService().crearTipoAnotacion(anotacion);
				cargarDB();
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
		//TipoAnotacionItem item = anotacionesTable.getSelectionModel().getSelectedItem();
		ObservableList<TipoAnotacionItem> seleccionados = anotacionesTable.getSelectionModel().getSelectedItems();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar");
		alert.setHeaderText("Eliminando " + seleccionados.size() +" registro(s).");
		alert.setContentText("¿Está seguro que desea eliminarlo?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				for(TipoAnotacionItem seleccionado : seleccionados){
					System.out.println("Eliminando: "+seleccionado.getDescripcion());
					ServiceLocator.getITiposAnotacionesService().eliminarTipoAnotacion(seleccionado.getId());
				}
				anotacionesList.removeAll(seleccionados);
				/*ServiceLocator.getITiposAnotacionesService().eliminarTipoAnotacion(item.getId());
				anotacionesList.remove(item);*/
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		} 
	}	
	@FXML
	public void modificar(){
		//System.out.println("Modificando");
		TipoAnotacionItem item = anotacionesTable.getSelectionModel().getSelectedItem();
		try {
			ServiceLocator.getITiposAnotacionesService().modificarTipoAnotacion(item);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
}
