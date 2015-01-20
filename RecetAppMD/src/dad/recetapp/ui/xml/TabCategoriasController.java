package dad.recetapp.ui.xml;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.impl.CategoriasService;
import dad.recetapp.services.items.CategoriaItem;

public class TabCategoriasController {

	private CategoriasService CS = new CategoriasService();
	
	// lista que contiene los datos
	private List<CategoriaItem> categorias = new ArrayList<CategoriaItem>();

	// lista "observable" que envuelve a la lista "variables" 
	private ObservableList<CategoriaItem> categoriasList = FXCollections.observableList(categorias);

	@FXML
	private TableView<CategoriaItem> categoriasTable;

	@FXML
	private TableColumn<CategoriaItem, String> descripcionColumn;

	@FXML
	private TextField cat_descText;

	@FXML
	public void initialize() {	
		try {
			categorias = CS.listarCategorias();
			for (CategoriaItem c: categorias)
				categoriasList.add(c);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		categoriasTable.setItems(categoriasList);

		descripcionColumn.setCellValueFactory(new PropertyValueFactory<CategoriaItem, String>("Descripcion"));
		descripcionColumn.setCellFactory(TextFieldTableCell.<CategoriaItem>forTableColumn());
		descripcionColumn.setOnEditCommit(t -> ((CategoriaItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDescripcion(t.getNewValue()));
	}

	@FXML
	public void anadir() {
		CategoriaItem categoria = new CategoriaItem();
		categoria.setDescripcion(cat_descText.getText());
		
		categoriasList.add(categoria);
			
		cat_descText.clear();
	}

	@FXML
	public void eliminar() {
		CategoriaItem item = categoriasTable.getSelectionModel().getSelectedItem();
		categoriasList.remove(item);
	}	
}
