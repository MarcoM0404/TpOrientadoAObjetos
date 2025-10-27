package clases;

public class Usuario {
	private String nombre;
	private String apellido;
	private String correo;
	private String password;
	private EstadoUsuario estado;
	private TipoUsuario tipo;

	public Usuario(String nombre, String apellido, String correo, String password, EstadoUsuario estado,
			TipoUsuario tipo) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.password = password;
		this.estado = estado;
		this.tipo = tipo;
	}
	
	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getCorreo() {
		return correo;
	}

	public String getPassword() {
		return password;
	}

	public EstadoUsuario getEstado() {
		return estado;
	}

	public TipoUsuario getTipo() {
		return tipo;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void activar() {
		this.estado = EstadoUsuario.ACTIVO;
	}

	public void inactivar() {
		this.estado = EstadoUsuario.INACTIVO;
	}

	public boolean esActivo() {
		return this.estado == EstadoUsuario.ACTIVO;
	}
}