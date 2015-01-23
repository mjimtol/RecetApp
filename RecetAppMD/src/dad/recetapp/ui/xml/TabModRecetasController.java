package dad.recetapp.ui.xml;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import dad.recetapp.services.items.MedidaItem;
import dad.recetapp.services.items.TipoIngredienteItem;

public class TabModRecetasController {

	@FXML
	private TextField seccionText;
	
	@FXML
	private TableView<MedidaItem> ingredientesTable;
	@FXML
	private TableColumn<TipoIngredienteItem, String> ingCantidadColumn;
	@FXML
	private TableColumn<TipoIngredienteItem, String> ingMedidaColumn;
	@FXML
	private TableColumn<TipoIngredienteItem, String> ingTipoColumn;
	
	@FXML
	private TableView<MedidaItem> instruccionsTable;
	@FXML
	private TableColumn<TipoIngredienteItem, String> insOrdenColumn;
	@FXML
	private TableColumn<TipoIngredienteItem, String> insDescripcionColumn;
	
	
	
	@FXML
	private void AddIngrediente(){
		
	}

	@FXML
	private void EditIngrediente(){
		
	}
	
	@FXML
	private void suprIngrediente(){
		
	}
	
	
	@FXML
	private void AddInstruccion(){
		
	}

	@FXML
	private void EditInstruccion(){
		
	}
	
	@FXML
	private void suprInstruccion(){
		
	}	
	
	
	@FXML
	private void DeleteTab(){
		
	}

}


