package clases;

public class Bibliotecario extends Usuario {
	public Bibliotecario(String nombre, String apellido, String correo, String password, EstadoUsuario estado) {
		super(nombre, apellido, correo, password, estado, TipoUsuario.BIBLIOTECARIO);
	}
}