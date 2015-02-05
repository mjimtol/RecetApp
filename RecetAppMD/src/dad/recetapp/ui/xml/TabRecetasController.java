package dad.recetapp.ui.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.RecetaItem;
import dad.recetapp.services.items.RecetaListItem;
import dad.recetapp.services.items.TipoAnotacionesItem;

public class TabRecetasController{
	private List<RecetaListItem> recetas = new ArrayList<RecetaListItem>();
	private ObservableList<RecetaListItem> recetasList = FXCollections.observableList(recetas);
	
	private List<TipoAnotacionesItem> categorias = new ArrayList<TipoAnotacionesItem>();
	private ObservableList<TipoAnotacionesItem> categoriasList = FXCollections.observableList(categorias);
		
	private RecetaListItem recetaSeleccionada;
	
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
	private ComboBox<Integer> minutosCombobox;
	@FXML
	private ComboBox<Integer> segundosCombobox;
	@FXML
	private ComboBox<String> categoriaCombobox;
	
	@FXML
	public void initialize() {	
		cargarCombos();
		cargarDB();
				
		recetasTableView.setItems(recetasList);
		recetasTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		nombreColumn.setCellValueFactory(new PropertyValueFactory<RecetaListItem, String>("nombre"));
		nombreColumn.setCellFactory(TextFieldTableCell.<RecetaListItem>forTableColumn());
		
		paraColumn.setCellValueFactory(new PropertyValueFactory<RecetaListItem, String>("para"));
		paraColumn.setCellFactory(TextFieldTableCell.<RecetaListItem>forTableColumn());
		
//		tiempototalColumn.setCellValueFactory(new PropertyValueFactory<RecetaListItem, String>("tiempoTotal"));
//		tiempototalColumn.setCellFactory(TextFieldTableCell.<RecetaListItem>forTableColumn());
		
//		fechacreacionColumn.setCellValueFactory(new PropertyValueFactory<RecetaListItem, Date>("fechaCreacion"));
//		fechacreacionColumn.setCellFactory(TextFieldTableCell.<RecetaListItem, Date> forTableColumn(new DateStringConverter("dd/MM/yyyy")));
		
		
		categoriaColumn.setCellValueFactory(new PropertyValueFactory<RecetaListItem, String>("categoria"));
		categoriaColumn.setCellFactory(TextFieldTableCell.<RecetaListItem>forTableColumn());
	}
	
	private void cargarCombos() {
		int i = 0;
		List <Integer> minutos = new ArrayList<Integer>();
		for (; i <= 60; i++)
			minutos.add(i);
		segundosCombobox.getItems().addAll(minutos);
		segundosCombobox.setValue(0);
		
		for (; i <= 120; i++)
			minutos.add(i);
		minutosCombobox.getItems().addAll(minutos);
		minutosCombobox.setValue(0);
		
		for (TipoAnotacionesItem c: categorias)
			categoriaCombobox.getItems().add(c.getDescripcion());	
	}

	@FXML
	private void nuevaReceta(){
	    try {
	    	FXMLLoader loader = new FXMLLoader(TabRecetasController.class.getResource("NuevaReceta.fxml"));	 

			AnchorPane rootPane = (AnchorPane) loader.load();	    	
			Scene scene = new Scene(rootPane);
			Stage stagePrincipal = new Stage();
			stagePrincipal.setTitle("Nueva Receta");
			stagePrincipal.setScene(scene);

			NuevaRecetaController controller = loader.getController();
			controller.setRecetasController(this);

			stagePrincipal.show();
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
	@FXML
	private void eliminarReceta(){
		List<RecetaListItem> seleccionados = recetasTableView.getSelectionModel().getSelectedItems();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar");
		alert.setHeaderText("Eliminando " + seleccionados.size() + " receta(s)");
		alert.setContentText("¿Está seguro que desea eliminarla(s)?");
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				for(RecetaListItem receta : seleccionados){
					System.out.println("eliminando " + receta.getNombre());
					ServiceLocator.getIRecetasService().eliminarReceta(receta.getId());					
				}			
				recetasList.removeAll(seleccionados);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}		
	}
	
	@FXML
	private void editarReceta(){
		try {
			RecetaListItem receta = recetasTableView.getSelectionModel().getSelectedItem();

			if (receta != null)
			{
				setRecetaSeleccionada(receta);

				FXMLLoader loader = new FXMLLoader(TabRecetasController.class.getResource("NuevaReceta.fxml"));	 

				AnchorPane rootPane = (AnchorPane) loader.load();	    	
				Scene scene = new Scene(rootPane);

				Stage stagePrincipal = new Stage();
				stagePrincipal.setTitle("Editar Receta");
				stagePrincipal.setScene(scene);

				NuevaRecetaController controller = loader.getController();
				controller.setRecetasController(this);

				stagePrincipal.show();
			}
			else
			{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Atención");
				alert.setHeaderText("Problemas al intentar editar");
				alert.setContentText("Debe escoger una receta primero");

				alert.showAndWait();
			}
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
	@FXML
	public void buscarReceta(){
		Long idCategoria = obtenerIdCategoria();
		recetas = new ArrayList<RecetaListItem>();
		recetasList.clear();
		try{
			String nombreReceta = nombreText.getText();
			if(nombreReceta.equals(""))
				nombreReceta = null;
			Integer tiempoReceta = (minutosCombobox.getValue()*60)+(segundosCombobox.getValue());
			if(tiempoReceta==0)
				tiempoReceta=null;
			if(idCategoria==0)
				idCategoria=null;
			recetas = ServiceLocator.getIRecetasService().buscarRecetas(nombreReceta, tiempoReceta,idCategoria );
			for (RecetaListItem r: recetas)
				recetasList.add(r);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private long obtenerIdCategoria(){
		for (TipoAnotacionesItem c : categoriasList)
			if (c.getDescripcion().equals(categoriaCombobox.getSelectionModel().getSelectedItem()))
				return c.getId();
		
		return 0;
	}
	@FXML
	public void cargarDB() {
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

	public RecetaListItem getRecetaSeleccionada() {
		return recetaSeleccionada;
	}

	public void setRecetaSeleccionada(RecetaListItem recetaSeleccionada) {
		this.recetaSeleccionada = recetaSeleccionada;
	}
	
}