package clases;


public class Prestamo {
	private String correoUsuario;
	private String icbnLibro;

	public Prestamo(String correoUsuario, String icbnLibro) {
		this.correoUsuario = correoUsuario;
		this.icbnLibro = icbnLibro;
	}

	public String getCorreoUsuario() {
		return correoUsuario;
	}

	public String getIcbnLibro() {
		return icbnLibro;
	}
}