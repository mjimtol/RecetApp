package dad.recetapp.services;

import java.util.List;

import dad.recetapp.services.items.TipoAnotacionesItem;

public interface ICategoriasService {
	
	public void crearCategoria(TipoAnotacionesItem categoria) throws ServiceException;
	public void modificarCategoria(TipoAnotacionesItem categoria) throws ServiceException;
	public void eliminarCategoria(Long id) throws ServiceException;
	public List<TipoAnotacionesItem> listarCategorias() throws ServiceException;
	public TipoAnotacionesItem obtenerCategoria(Long id) throws ServiceException;

}
