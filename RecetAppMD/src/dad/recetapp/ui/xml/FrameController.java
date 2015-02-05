package dad.recetapp.ui.xml;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.RecetaListItem;

public class FrameController {
	@FXML Label cantidadRecetasLabel;
	
	@FXML
	public void onTabRecetasEnter(){
		try {
			List<RecetaListItem> recetas = ServiceLocator.getIRecetasService().listarRecetas();
			cantidadRecetasLabel.setText( "Recetas: " + recetas.size() );
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public void setCantidadRecetasLabel(String cantidadRecetas) {
		this.cantidadRecetasLabel.setText("Recetas: " + cantidadRecetas);
	}
	
		
	
}
