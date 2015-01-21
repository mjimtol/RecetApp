package dad.recetapp.ui.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.impl.CategoriasService;
import dad.recetapp.services.impl.MedidasService;
import dad.recetapp.services.items.TipoAnotacionesItem;
import dad.recetapp.services.items.MedidaItem;

public class TabMedidasController {

	private MedidasService MS = new MedidasService();
	
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
			medidas = MS.listarMedidas();
			for (MedidaItem m: medidas)
				medidasList.add(m);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		medidasTable.setItems(medidasList);

		nombreColumn.setCellValueFactory(new PropertyValueFactory<MedidaItem, String>("Nombre"));
		nombreColumn.setCellFactory(TextFieldTableCell.<MedidaItem>forTableColumn());
		nombreColumn.setOnEditCommit(t -> ((MedidaItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).setNombre(t.getNewValue()));
		
		abreviaturaColumn.setCellValueFactory(new PropertyValueFactory<MedidaItem, String>("Abreviatura"));
		abreviaturaColumn.setCellFactory(TextFieldTableCell.<MedidaItem>forTableColumn());
		abreviaturaColumn.setOnEditCommit(t -> ((MedidaItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAbreviatura(t.getNewValue()));
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
				MS.crearMedida(medida);
				medidasList.add(medida);
			} catch (ServiceException e) {
				e.printStackTrace();
			}

			nombreText.clear();
			abrevText.clear();
		}
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Atención");
			alert.setHeaderText("Problemas al introducir medida");
			alert.setContentText("La descripción no puede estar en blanco");

			alert.showAndWait();
		}
	}

	@FXML
	public void eliminar() {
		MedidaItem item = medidasTable.getSelectionModel().getSelectedItem();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar");
		alert.setHeaderText("Eliminando " + item.getNombre());
		alert.setContentText("¿Está seguro que desea eliminarlo?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				MS.eliminarMedida(item.getId());
				medidasList.remove(item);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
	}
}
