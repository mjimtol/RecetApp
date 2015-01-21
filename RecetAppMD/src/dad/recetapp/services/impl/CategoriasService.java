package dad.recetapp.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dad.recetapp.db.DataBase;
import dad.recetapp.services.ICategoriasService;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.items.TipoAnotacionesItem;

public class CategoriasService implements ICategoriasService {

	public CategoriasService() {
	}

	@Override
	public void crearCategoria(TipoAnotacionesItem categoria) throws ServiceException {
		try {
			if (categoria == null || categoria.getDescripcion() == null) {
				throw new ServiceException("Error al crear la categoría: debe especificar la categoría");
			}			
			Connection conn = DataBase.getConnection();
			PreparedStatement statement = conn.prepareStatement("insert into categorias (descripcion) values (?)");
			statement.setString(1, categoria.getDescripcion());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al crear la categoría '" + categoria.getDescripcion() + "'", e);
		} catch (NullPointerException e) {
			throw new ServiceException("Error al crear la categoría: la categoría no puede ser nula", e);
		}
	}

	@Override
	public void modificarCategoria(TipoAnotacionesItem categoria) throws ServiceException {
		try{
			if (categoria.getId() == null) {
				throw new ServiceException("Error al recuperar la categoría: Debe especificar el identificador");
			}
			Connection conn = DataBase.getConnection();
			PreparedStatement statement = conn.prepareStatement("update categorias set descripcion=? where id = ?");
			statement.setString(1, categoria.getDescripcion());
			statement.setLong(2, categoria.getId());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al modificar la categoría con ID '" + categoria.getId()+ "'", e);
		}
	}

	@Override
	public void eliminarCategoria(Long id) throws ServiceException {
		try{
			if (id == null) {
				throw new ServiceException("Error al recuperar la categoría: Debe especificar el identificador");
			}
			Connection conn = DataBase.getConnection();
			PreparedStatement statement = conn.prepareStatement("delete from categorias where id = ?");
			statement.setLong(1, id);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al eliminar la categoría con ID '" + id + "'", e);
		}
	}

	@Override
	public List<TipoAnotacionesItem> listarCategorias() throws ServiceException {
		List<TipoAnotacionesItem> listcategoria = new ArrayList<TipoAnotacionesItem>();
		TipoAnotacionesItem categoria = null;
		try {
			Connection conn = DataBase.getConnection();
			PreparedStatement statement = conn.prepareStatement("select id,descripcion from categorias");
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				categoria = new TipoAnotacionesItem();
				categoria.setId(rs.getLong("id"));
				categoria.setDescripcion(rs.getString("descripcion"));
				listcategoria.add(categoria);
			}
			statement.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al recuperar las categorías", e);
		}
		return listcategoria;
	}

	@Override
	public TipoAnotacionesItem obtenerCategoria(Long id) throws ServiceException {
		TipoAnotacionesItem categoria = null;
		try {
			if (id == null) {
				throw new ServiceException("Error al recuperar la categoría: Debe especificar el identificador");
			}
			Connection conn = DataBase.getConnection();
			PreparedStatement statement = conn.prepareStatement("select id,descripcion from categorias where id = ?"); 
			statement.setLong(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				categoria = new TipoAnotacionesItem();
				categoria.setId(rs.getLong("id"));
				categoria.setDescripcion(rs.getString("descripcion"));
			}
			statement.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al recuperar la categoría con ID '" + id + "'", e);
		}
		return categoria;
	}

}
