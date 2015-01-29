package dad.recetapp.ui.xml;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.RecetaItem;
import dad.recetapp.services.items.TipoAnotacionesItem;

public class NuevaRecetaController {
		
	@FXML
	private TabPane tabs;
	
	@FXML
	private TextField nombreText;
	@FXML
	private TextField paraText;
	@FXML
	private ComboBox minutosTotalCombo;
	@FXML
	private ComboBox segundosTotalCombo;
	@FXML
	private ComboBox minutosThermomixCombo;
	@FXML
	private ComboBox segundosThermomixCombo;
	@FXML
	private ComboBox paraCombo;
	@FXML
	private ComboBox categoriaCombo;
	
	
	@FXML
	public void initialize() {	
		int i = 1;
		List <Integer> minutos = new ArrayList<Integer>();
		for (; i <= 60; i++)
			minutos.add(i);
		segundosTotalCombo.getItems().addAll(minutos);
		segundosThermomixCombo.getItems().addAll(minutos);
		
		for (; i <= 120; i++)
			minutos.add(i);
		minutosTotalCombo.getItems().addAll(minutos);
		minutosThermomixCombo.getItems().addAll(minutos);
		
//		for (TipoAnotacionesItem c: categorias)
//			categoriaCombobox.getItems().add(c.getDescripcion());		
	}
	
	
	@FXML
	private void handleCrearButton() {
		System.out.println("CREANDO");
		
		RecetaItem receta = new RecetaItem();
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
		categoria.setDescripcion("Perras");
		long bitches = 2;
		categoria.setId(bitches);
		
		receta.setCategoria(categoria);
		
		try {
			ServiceLocator.getIRecetasService().crearReceta(receta);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}	
	
	@FXML
	private void addTab(){
		
	}
	
	
	
}
