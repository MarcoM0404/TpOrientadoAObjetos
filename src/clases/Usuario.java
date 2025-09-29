package clases;

public class Usuario {
	
	public enum EstadoUsuario{ ACTIVO , INACTIVO}
	public enum RolUsuario {USUARIO, BIBLIOTECARIO}
	
	private String nombre;
	private String apellido;
	private String mail;
	private String password;
	private EstadoUsuario estado;
	private final RolUsuario rol;
	public Usuario(String nombre, String apellido, String mail, String password, EstadoUsuario estado,
			RolUsuario rol) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.mail = mail;
		this.password = password;
		this.estado = estado;
		this.rol = rol;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPassword() {
		return password;
	}
	public void setContraseña(String password) {
		this.password = password;
	}
	public EstadoUsuario getEstado() {
		return estado;
	}
	public void setEstado(EstadoUsuario estado) {
		this.estado = estado;
	}
	public RolUsuario getRol() {
		return rol;
	}
	
	
	

}
