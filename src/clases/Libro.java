package clases;

public class Libro {

    public enum EstadoLibro { DISPONIBLE, PRESTADO, RESERVADO, NO_DISPONIBLE }

    private String icbn;
    private String titulo;
    private String autor;
    private String edicion;
    private EstadoLibro estado;

    // Constructor
    public Libro(String icbn, String titulo, String autor, String edicion, EstadoLibro estado) {
        this.icbn = icbn;
        this.titulo = titulo;
        this.autor = autor;
        this.edicion = edicion;
        this.estado = estado;
    }

    // Getters y Setters
    public String getIcbn() { return icbn; }
    public void setIcbn(String icbn) { this.icbn = icbn; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getEdicion() { return edicion; }
    public void setEdicion(String edicion) { this.edicion = edicion; }

    public EstadoLibro getEstado() { return estado; }
    public void setEstado(EstadoLibro estado) { this.estado = estado; }

    // Método de conveniencia
    @Override
    public String toString() {
        return "Libro{" +
                "ICBN='" + icbn + '\'' +
                ", Título='" + titulo + '\'' +
                ", Autor='" + autor + '\'' +
                ", Edición='" + edicion + '\'' +
                ", Estado=" + estado +
                '}';
    }
}
