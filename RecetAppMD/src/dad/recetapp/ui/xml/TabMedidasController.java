package dad.recetapp.ui.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.impl.CategoriasService;
import dad.recetapp.services.impl.MedidasService;
import dad.recetapp.services.items.TipoAnotacionesItem;
import dad.recetapp.services.items.MedidaItem;
import dad.recetapp.services.items.TipoIngredienteItem;

public class TabMedidasController {

	//private MedidasService MS = new MedidasService();
	
	// lista que contiene los datos
	private List<MedidaItem> medidas = new ArrayList<MedidaItem>();

	// lista "observable" que envuelve a la lista "variables" 
	private ObservableList<MedidaItem> medidasList = FXCollections.observableList(medidas);

	@FXML
	private TableView<MedidaItem> medidasTable;

	@FXML
	private TableColumn<MedidaItem, String> nombreColumn;
	
	@FXML
	private TableColumn<MedidaItem, String> abreviaturaColumn;
	
	@FXML
	private TextField nombreText;

	@FXML
	private TextField abrevText;
	
	@FXML
	public void initialize() {	
		try {
			medidas = ServiceLocator.getIMedidasService().listarMedidas();
			for (MedidaItem m: medidas)
				medidasList.add(m);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		medidasTable.setItems(medidasList);

		nombreColumn.setCellValueFactory(new PropertyValueFactory<MedidaItem, String>("Nombre"));
		nombreColumn.setCellFactory(TextFieldTableCell.<MedidaItem>forTableColumn());
		nombreColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MedidaItem,String>>() {
			public void handle(CellEditEvent<MedidaItem, String> t) {
				((MedidaItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).setNombre(t.getNewValue());
				modificar();
			};
		});
		
		abreviaturaColumn.setCellValueFactory(new PropertyValueFactory<MedidaItem, String>("Abreviatura"));
		abreviaturaColumn.setCellFactory(TextFieldTableCell.<MedidaItem>forTableColumn());
		abreviaturaColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MedidaItem,String>>() {
			public void handle(CellEditEvent<MedidaItem, String> t) {
				((MedidaItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAbreviatura(t.getNewValue());
				modificar();
			};
		});
	}
	private void cargarDB() {
		medidas = new ArrayList<MedidaItem>();
		medidasList.clear();
		try {
			medidas = ServiceLocator.getIMedidasService().listarMedidas();
			for (MedidaItem c: medidas)
				medidasList.add(c);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	@FXML
	public void anadir() {
		if (!nombreText.getText().equals("") || !abrevText.getText().equals(""))
		{
			MedidaItem medida = new MedidaItem();
			medida.setNombre(nombreText.getText());
			medida.setAbreviatura(abrevText.getText());
			//medida.setId((long) 25);

			try {
				ServiceLocator.getIMedidasService().crearMedida(medida);
				cargarDB();
			} catch (ServiceException e) {
				e.printStackTrace();
			}

			nombreText.clear();
			abrevText.clear();
		}
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Atenci�n");
			alert.setHeaderText("Problemas al introducir medida");
			alert.setContentText("La descripci�n no puede estar en blanco");

			alert.showAndWait();
		}
	}

	@FXML
	public void eliminar() {
		MedidaItem item = medidasTable.getSelectionModel().getSelectedItem();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar");
		alert.setHeaderText("Eliminando " + item.getNombre());
		alert.setContentText("�Est� seguro que desea eliminarlo?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				ServiceLocator.getIMedidasService().eliminarMedida(item.getId());
				medidasList.remove(item);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
	}
	@FXML
	public void modificar(){
		//System.out.println("Modificando");
		MedidaItem item = medidasTable.getSelectionModel().getSelectedItem();

		try {
			ServiceLocator.getIMedidasService().modificarMedida(item);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}
