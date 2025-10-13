package clases;

import java.util.ArrayList;

public class Biblioteca {
	private ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
	private ArrayList<Libro> libros = new ArrayList<Libro>();
	private int librosAgregadosDuranteEjecucion = 0;
	private int MAX_LIBROS_ADICIONALES = 10;

	public Biblioteca() {
		precargarDatos();
	}

	// buscar usuarios y libros 
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

	// login
	public Usuario login(String correo, String password) {
		Usuario u = buscarUsuarioPorCorreo(correo);
		if (u == null)
			return null;
		if (!u.esActivo())
			return null;
		if (!u.getPassword().equals(password))
			return null;
		return u;
	}

	// consultar libros disponibles
	public ArrayList<Libro> librosDisponibles() {
		ArrayList<Libro> disponibles = new ArrayList<Libro>();
		for (int i = 0; i < libros.size(); i++) {
			Libro l = libros.get(i);
			if (l.estaDisponible())
				disponibles.add(l);
		}
		return disponibles;
	}

	// metodos de usuario BÁSICO
	public String solicitarPrestamo(Usuario usuario, String icbn) {
	    if (usuario.getTipo() != TipoUsuario.USUARIO)
	        return "Solo los usuarios básicos pueden pedir préstamos";

	    if (usuario.tienePrestamoVigente())
	        return "El usuario ya tiene un préstamo vigente";

	    Libro libro = buscarLibroPorIcbn(icbn);
	    if (libro == null)
	        return "Libro no encontrado";
	    if (libro.getEstado() != EstadoLibro.DISPONIBLE)
	        return "El libro no está disponible para préstamo";

	    libro.setEstado(EstadoLibro.PRESTADO);
	    usuario.asignarPrestamo(icbn);
	    return "Préstamo realizado con éxito";
	}


	public String devolverLibro(Usuario usuario) {
	    if (usuario.getTipo() != TipoUsuario.USUARIO)
	        return "Solo un usuario básico puede devolver";

	    if (!usuario.tienePrestamoVigente())
	        return "No tiene préstamo vigente";

	    String icbn = usuario.getIcbnPrestado();
	    Libro libro = buscarLibroPorIcbn(icbn);
	    if (libro == null)
	        return "Libro no encontrado";

	    libro.setEstado(EstadoLibro.DISPONIBLE);
	    usuario.limpiarPrestamo();
	    return "Libro devuelto con éxito";
	}


	// métodos de Bibliotecario
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

	public String altaUsuarioBasico(Usuario usuario, String nombre, String apellido, String correo, String password) {
	    if (usuario.getTipo() != TipoUsuario.BIBLIOTECARIO)
	        return "Operación solo para bibliotecarios";
	    if (buscarUsuarioPorCorreo(correo) != null)
	        return "Ya existe un usuario con ese correo";
	    usuarios.add(new UsuarioBasico(nombre, apellido, correo, password, EstadoUsuario.ACTIVO));
	    return "Usuario básico dado de alta";
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


	// precarga de 2 usuarios basicos y 1 bibliotecario. Además de 10 libros
	private void precargarDatos() {
		usuarios.add(new UsuarioBasico("Ana", "Pérez", "ana@demo.com", "1234", EstadoUsuario.ACTIVO));
		usuarios.add(new UsuarioBasico("Luis", "Gómez", "luis@demo.com", "1234", EstadoUsuario.ACTIVO));
		usuarios.add(new Bibliotecario("María", "López", "bibliotecaria@demo.com", "admin", EstadoUsuario.ACTIVO));

		libros.add(new Libro("ICBN-001", "El Quijote", "Cervantes", "Ed. 1", EstadoLibro.DISPONIBLE));
		libros.add(new Libro("ICBN-002", "Cien años de soledad", "García Márquez", "Ed. 3", EstadoLibro.DISPONIBLE));
		libros.add(new Libro("ICBN-003", "Rayuela", "Cortázar", "Ed. 2", EstadoLibro.DISPONIBLE));
		libros.add(new Libro("ICBN-004", "Ficciones", "Borges", "Ed. 1", EstadoLibro.DISPONIBLE));
		libros.add(new Libro("ICBN-005", "La metamorfosis", "Kafka", "Ed. 4", EstadoLibro.DISPONIBLE));
		libros.add(new Libro("ICBN-006", "1984", "Orwell", "Ed. 2", EstadoLibro.DISPONIBLE));
		libros.add(new Libro("ICBN-007", "Crónica de una muerte anunciada", "García Márquez", "Ed. 1",
				EstadoLibro.DISPONIBLE));
		libros.add(new Libro("ICBN-008", "El Aleph", "Borges", "Ed. 5", EstadoLibro.DISPONIBLE));
		libros.add(new Libro("ICBN-009", "Pedro Páramo", "Rulfo", "Ed. 2", EstadoLibro.DISPONIBLE));
		libros.add(new Libro("ICBN-010", "Fahrenheit 451", "Bradbury", "Ed. 6", EstadoLibro.DISPONIBLE));
	}

	public ArrayList<Usuario> getUsuarios() {
		return usuarios;
	}

	public ArrayList<Libro> getLibros() {
		return libros;
	}
}
