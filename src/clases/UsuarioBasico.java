package clases;

public class UsuarioBasico extends Usuario {
	private String icbnPrestado; // si no tiene ningun prestamo, es null

	public UsuarioBasico(String nombre, String apellido, String correo, String password, EstadoUsuario estado) {
		super(nombre, apellido, correo, password, estado, TipoUsuario.USUARIO);
		this.icbnPrestado = null;
	}

	public boolean tienePrestamoVigente() {
		return icbnPrestado != null;
	}

	public String getIcbnPrestado() {
		return icbnPrestado;
	}

	public void asignarPrestamo(String icbn) {
		this.icbnPrestado = icbn;
	}

	public void limpiarPrestamo() {
		this.icbnPrestado = null;
	}
}
