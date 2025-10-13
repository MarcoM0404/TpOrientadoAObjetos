package clases;

public class Main {
    public static void main(String[] args) {
        Biblioteca b = new Biblioteca();

        // Login usuario básico
        Usuario ana = b.login("ana@demo.com", "1234");
        if (ana == null) {
            System.out.println("No se pudo iniciar sesión con Ana");
            return;
        }
        System.out.println("Login OK: " + ana.getNombre());

        // Listar libros disponibles
        System.out.println("\nLibros disponibles:");
        for (int i = 0; i < b.librosDisponibles().size(); i++) {
            Libro l = b.librosDisponibles().get(i);
            System.out.println(l.getIcbn() + " - " + l.getTitulo() + " [" + l.getEstado() + "]");
        }

        // Pedir préstamo
        System.out.println("\nSolicitando préstamo de ICBN-001...");
        String res1 = b.solicitarPrestamo(ana, "ICBN-001");
        System.out.println(res1);

        // Intentar segundo préstamo (debe fallar)
        System.out.println("Intentando otro préstamo de ICBN-002...");
        String res2 = b.solicitarPrestamo(ana, "ICBN-002");
        System.out.println(res2);

        // Devolver libro
        System.out.println("\nDevolviendo libro...");
        String res3 = b.devolverLibro(ana);
        System.out.println(res3);

        // Login bibliotecario y alta de libro
        Usuario bib = b.login("bibliotecaria@demo.com", "admin");
        if (bib != null) {
            System.out.println("\nBibliotecario logueado: " + bib.getNombre());
            String res4 = b.altaLibro(bib, "ICBN-006", "1984", "Orwell", "Ed. 2");
            System.out.println(res4);
        }

        // Mostrar catálogo final
        System.out.println("\nCatálogo final:");
        for (int i = 0; i < b.getLibros().size(); i++) {
            Libro l = b.getLibros().get(i);
            System.out.println(l.getIcbn() + " - " + l.getTitulo() + " [" + l.getEstado() + "]");
        }
    }
}
