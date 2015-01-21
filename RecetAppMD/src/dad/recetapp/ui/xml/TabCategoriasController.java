package dad.recetapp.ui.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.impl.CategoriasService;
import dad.recetapp.services.items.TipoAnotacionesItem;

public class TabCategoriasController {

	private CategoriasService CS = new CategoriasService();
	
	// lista que contiene los datos
	private List<TipoAnotacionesItem> categorias = new ArrayList<TipoAnotacionesItem>();

	// lista "observable" que envuelve a la lista "variables" 
	private ObservableList<TipoAnotacionesItem> categoriasList = FXCollections.observableList(categorias);

	@FXML
	private TableView<TipoAnotacionesItem> categoriasTable;

	@FXML
	private TableColumn<TipoAnotacionesItem, String> descripcionColumn;

	@FXML
	private TextField cat_descText;

	@FXML
	public void initialize() {	
		try {
			categorias = CS.listarCategorias();
			for (TipoAnotacionesItem c: categorias)
				categoriasList.add(c);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		categoriasTable.setItems(categoriasList);

		descripcionColumn.setCellValueFactory(new PropertyValueFactory<TipoAnotacionesItem, String>("Descripcion"));
		descripcionColumn.setCellFactory(TextFieldTableCell.<TipoAnotacionesItem>forTableColumn());
		descripcionColumn.setOnEditCommit(t -> ((TipoAnotacionesItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDescripcion(t.getNewValue()));
	}

	@FXML
	public void anadir() {
		if (!cat_descText.getText().equals(""))
		{
			TipoAnotacionesItem categoria = new TipoAnotacionesItem();
			categoria.setDescripcion(cat_descText.getText());

			try {
				CS.crearCategoria(categoria);
				categoriasList.add(categoria);
			} catch (ServiceException e) {
				e.printStackTrace();
			}

			cat_descText.clear();
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
		TipoAnotacionesItem item = categoriasTable.getSelectionModel().getSelectedItem();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar");
		alert.setHeaderText("Eliminando " + item.getDescripcion());
		alert.setContentText("¿Está seguro que desea eliminarlo?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				CS.eliminarCategoria(item.getId());
				categoriasList.remove(item);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		} 
	}	
	
	@FXML
	public void modificar(){
		System.out.println("Modificando");
		TipoAnotacionesItem item = categoriasTable.getSelectionModel().getSelectedItem();
		try {
			CS.modificarCategoria(item);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}
