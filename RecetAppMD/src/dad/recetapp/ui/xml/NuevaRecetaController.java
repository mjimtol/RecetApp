package dad.recetapp.ui.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.RecetaItem;
import dad.recetapp.services.items.RecetaListItem;
import dad.recetapp.services.items.TipoAnotacionesItem;

public class NuevaRecetaController {
	 	
	@FXML	private TabPane tabs;
	
	@FXML	private TextField nombreText;
	@FXML	private TextField paraText;
	
	@FXML	private ComboBox<Integer> minutosTotalCombo;
	@FXML	private ComboBox<Integer> segundosTotalCombo;
	@FXML	private ComboBox<Integer> minutosThermomixCombo;
	@FXML	private ComboBox<Integer> segundosThermomixCombo;
	@FXML	private ComboBox<String> paraCombo;
	@FXML	private ComboBox<String> categoriaCombo;
	
	@FXML	private Button addButton;
	
	private TabRecetasController recetasController;
	
	@FXML
	public void initialize() {
		rellenarCombos();
		cargarDB();	
	}
		
	private void rellenarCombos() {
		int i = 0;
		List <Integer> minutos = new ArrayList<Integer>();
		for (; i <= 60; i++)
			minutos.add(i);
		segundosTotalCombo.getItems().addAll(minutos);
		segundosThermomixCombo.getItems().addAll(minutos);
		segundosTotalCombo.setValue(0);
		segundosThermomixCombo.setValue(0);
		
		for (; i <= 120; i++)
			minutos.add(i);
		minutosTotalCombo.getItems().addAll(minutos);
		minutosThermomixCombo.getItems().addAll(minutos);
		minutosTotalCombo.setValue(0);
		minutosThermomixCombo.setValue(0);
	}


	private void cargarDB() {
		try {
			List<TipoAnotacionesItem> categorias = ServiceLocator.getICategoriasService().listarCategorias();
			for (TipoAnotacionesItem c: categorias)
				categoriaCombo.getItems().add(c.getDescripcion());
			paraCombo.getItems().add("Personas");
			paraCombo.getItems().add("Unidades");
			paraCombo.getItems().add("Raciones");
			paraCombo.getItems().add("Cabritos");
			paraCombo.setValue("Personas");
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void crearButton() {
		RecetaItem receta;
			receta = new RecetaItem();
			receta.setNombre(nombreText.getText());
			receta.setFechaCreacion(new Date());
			receta.setCantidad(Integer.parseInt(paraText.getText()));
			receta.setPara(paraCombo.getValue().toString());		

			int totalMin = Integer.parseInt(minutosTotalCombo.getValue().toString());
			int totalSeg = Integer.parseInt(segundosTotalCombo.getValue().toString());
			int tiempoTotal = totalMin * 60 + totalSeg;		
			receta.setTiempoTotal(tiempoTotal);

			int thermomixMin = Integer.parseInt(minutosThermomixCombo.getValue().toString());
			int thermomixSeg = Integer.parseInt(segundosThermomixCombo.getValue().toString());
			int tiempoThermomix = thermomixMin * 60 + thermomixSeg;	
			receta.setTiempoThermomix(tiempoThermomix);		

			TipoAnotacionesItem categoria = new TipoAnotacionesItem();
			categoria.setDescripcion(categoriaCombo.getSelectionModel().getSelectedItem());

			List<TipoAnotacionesItem> categorias;
			try {
				categorias = ServiceLocator.getICategoriasService().listarCategorias();
				for (TipoAnotacionesItem c: categorias)
				{
					if ( c.getDescripcion().equals(categoriaCombo.getSelectionModel().getSelectedItem()) )
						categoria.setId(c.getId());
				}
			} catch (ServiceException e1) {
				e1.printStackTrace();
			}			

			receta.setCategoria(categoria);
			
			try {
				if (addButton.getText().equals("Crear"))
				{	ServiceLocator.getIRecetasService().crearReceta(receta);	}
				else
				{
					receta.setId(recetasController.getRecetaSeleccionada().getId());
					ServiceLocator.getIRecetasService().modificarReceta(receta);
				}
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			recetasController.cargarDB();
			cerrarVentana();
	}	
		
	@FXML
	private void cerrarVentana()
	{
		Stage stage = (Stage) nombreText.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	private void addTab() throws IOException{
		Tab tab = new Tab();
		tab.setText("Pene");
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("TabModRecetas.fxml"));	 
		
		BorderPane rootPane = (BorderPane) loader.load();	    
		
		TabModRecetasController controller = loader.getController();
		controller.setRecetaController(this);
		controller.setParentTab(tab);
		
		tabs.getTabs().add(tab);
		tab.setContent((Node)rootPane);
	}

	private void rellenarDatos() {
		RecetaListItem receta = recetasController.getRecetaSeleccionada();

		if (receta != null)
		{
			nombreText.setText(receta.getNombre());
			paraText.setText(receta.getCantidad().toString());
			paraCombo.setValue(receta.getPara());
			categoriaCombo.setValue(receta.getCategoria());
			minutosTotalCombo.setValue(receta.getTiempoTotal() / 60);
			segundosTotalCombo.setValue(receta.getTiempoTotal() % 60);
			minutosThermomixCombo.setValue(receta.getTiempoThermomix() / 60);
			segundosThermomixCombo.setValue(receta.getTiempoThermomix() % 60);
			addButton.setText("Guardar cambios");
		}
	}

	public void setRecetasController(TabRecetasController recetasController) {
		this.recetasController = recetasController;				
		rellenarDatos();
	}
}
