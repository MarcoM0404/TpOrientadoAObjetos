package clases;

public class Libro {
	private String icbn;
	private String titulo;
	private String autor;
	private String edicion;
	private EstadoLibro estado;

	public Libro(String icbn, String titulo, String autor, String edicion, EstadoLibro estado) {
		this.icbn = icbn;
		this.titulo = titulo;
		this.autor = autor;
		this.edicion = edicion;
		this.estado = estado;
	}

	public String getIcbn() {
		return icbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getAutor() {
		return autor;
	}

	public String getEdicion() {
		return edicion;
	}

	public EstadoLibro getEstado() {
		return estado;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public void setEdicion(String edicion) {
		this.edicion = edicion;
	}

	public void setEstado(EstadoLibro estado) {
		this.estado = estado;
	}

	public boolean estaDisponible() {
		return estado == EstadoLibro.DISPONIBLE;
	}
}
