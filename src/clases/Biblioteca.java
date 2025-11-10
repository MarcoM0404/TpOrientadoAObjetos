package clases;

import java.util.ArrayList;

public class Biblioteca {
	private ArrayList<Usuario> usuarios;
	private ArrayList<Libro> libros;
	private ArrayList<Prestamo> prestamos = new ArrayList<Prestamo>();

	private int librosAgregadosDuranteEjecucion = 0;
	private int MAX_LIBROS_ADICIONALES = 10;

	public Biblioteca() {
		precargarDatos();
	}

	public Usuario buscarUsuarioPorCorreo(String correo) {
		for (int i = 0; i < usuarios.size(); i++) {
			Usuario u = usuarios.get(i);
			if (u.getCorreo().equalsIgnoreCase(correo))
				return u;
		}
		return null;
	}

	public Libro buscarLibroPorIcbn(String icbn) {
		for (int i = 0; i < libros.size(); i++) {
			Libro l = libros.get(i);
			if (l.getIcbn().equals(icbn))
				return l;
		}
		return null;
	}


	
	public ArrayList<Libro> librosDisponibles() {
		ArrayList<Libro> disponibles = new ArrayList<Libro>();
		for (int i = 0; i < libros.size(); i++) {
			Libro l = libros.get(i);
			if (l.estaDisponible())
				disponibles.add(l);
		}
		return disponibles;
	}

	public Prestamo buscarPrestamoPorCorreo(String correo) {
		for (Prestamo p : prestamos) {
			if (p.getCorreoUsuario().equalsIgnoreCase(correo)) {
				return p;
			}
		}
		return null;
	}

	public boolean usuarioTienePrestamoVigente(Usuario usuario) {
		return buscarPrestamoPorCorreo(usuario.getCorreo()) != null;
	}

	public String getIcbnPrestadoPorUsuario(Usuario usuario) {
		Prestamo p = buscarPrestamoPorCorreo(usuario.getCorreo());
		if (p != null) {
			return p.getIcbnLibro();
		} else {
			return null;
		}
	}
	
	public String solicitarPrestamo(Usuario usuario, String icbn) {
		if (usuario.getTipo() != TipoUsuario.USUARIO)
			return "Solo los usuarios básicos pueden pedir préstamos";

		if (usuarioTienePrestamoVigente(usuario))
			return "El usuario ya tiene un préstamo vigente";

		Libro libro = buscarLibroPorIcbn(icbn);
		if (libro == null)
			return "Libro no encontrado";
		if (libro.getEstado() != EstadoLibro.DISPONIBLE)
			return "El libro no está disponible para préstamo";

		libro.setEstado(EstadoLibro.PRESTADO);
		prestamos.add(new Prestamo(usuario.getCorreo(), icbn));
		return "Préstamo realizado con éxito";
	}

	public String devolverLibro(Usuario usuario) {
		if (usuario.getTipo() != TipoUsuario.USUARIO)
			return "Solo un usuario básico puede devolver";

		if (!usuarioTienePrestamoVigente(usuario))
			return "No tiene préstamo vigente";

		String icbn = getIcbnPrestadoPorUsuario(usuario);
		Libro libro = buscarLibroPorIcbn(icbn);
		
		if (libro != null) {
			libro.setEstado(EstadoLibro.DISPONIBLE);
		}

		Prestamo p = buscarPrestamoPorCorreo(usuario.getCorreo());
		if (p != null) {
			prestamos.remove(p);
		}
		
		return "Libro devuelto con éxito";
	}

	public String cambiarEstadoLibro(Usuario usuario, String icbn, EstadoLibro nuevoEstado) {
	    if (usuario.getTipo() != TipoUsuario.BIBLIOTECARIO)
	        return "Operación solo para bibliotecarios";

	    Libro libro = buscarLibroPorIcbn(icbn);
	    if (libro == null)
	        return "Libro no encontrado";
	    if (libro.getEstado() == EstadoLibro.PRESTADO && nuevoEstado == EstadoLibro.NO_DISPONIBLE)
	        return "No se puede marcar NO_DISPONIBLE un libro prestado";

	    libro.setEstado(nuevoEstado);
	    return "Estado actualizado";
	}

	public String altaLibro(Usuario usuario, String icbn, String titulo, String autor, String edicion) {
	    if (usuario.getTipo() != TipoUsuario.BIBLIOTECARIO)
	        return "Operación solo para bibliotecarios";
	    if (buscarLibroPorIcbn(icbn) != null)
	        return "Ya existe un libro con ese ICBN";
	    if (librosAgregadosDuranteEjecucion >= MAX_LIBROS_ADICIONALES)
	        return "Límite de 10 libros adicionales alcanzado";
	    libros.add(new Libro(icbn, titulo, autor, edicion, EstadoLibro.DISPONIBLE));
	    librosAgregadosDuranteEjecucion++;
	    return "Libro agregado correctamente";
	}

	public String activarUsuario(Usuario usuario, String correo) {
	    if (usuario.getTipo() != TipoUsuario.BIBLIOTECARIO)
	        return "Operación solo para bibliotecarios";
	    Usuario u = buscarUsuarioPorCorreo(correo);
	    if (u == null)
	        return "Usuario no encontrado";
	    u.activar();
	    return "Usuario activado";
	}

	public String inactivarUsuario(Usuario usuario, String correo) {
	    if (usuario.getTipo() != TipoUsuario.BIBLIOTECARIO)
	        return "Operación solo para bibliotecarios";
	    Usuario u = buscarUsuarioPorCorreo(correo);
	    if (u == null)
	        return "Usuario no encontrado";
	    u.inactivar();
	    return "Usuario inactivado";
	}


	private void precargarDatos() {
		this.usuarios = PrecargaDatos.getUsuariosIniciales();
		this.libros = PrecargaDatos.getLibrosIniciales();
	}

	public ArrayList<Usuario> getUsuarios() {
		return usuarios;
	}

	public ArrayList<Libro> getLibros() {
		return libros;
	}
}