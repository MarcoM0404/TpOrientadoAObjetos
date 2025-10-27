package clases;

import java.util.ArrayList;


public class PrecargaDatos {

	public static ArrayList<Usuario> getUsuariosIniciales() {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.add(new UsuarioBasico("Ana", "Pérez", "ana@demo.com", "1234", EstadoUsuario.ACTIVO));
		usuarios.add(new UsuarioBasico("Luis", "Gómez", "luis@demo.com", "1234", EstadoUsuario.ACTIVO));
		usuarios.add(new Bibliotecario("María", "López", "bibliotecaria@demo.com", "admin", EstadoUsuario.ACTIVO));
		return usuarios;
	}

	public static ArrayList<Libro> getLibrosIniciales() {
		ArrayList<Libro> libros = new ArrayList<Libro>();
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
		return libros;
	}
}