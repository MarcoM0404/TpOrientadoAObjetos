package clases;

public class UsuarioBasico extends Usuario {
	public UsuarioBasico(String nombre, String apellido, String correo, String password, EstadoUsuario estado) {
		super(nombre, apellido, correo, password, estado, TipoUsuario.USUARIO);
	}
}
