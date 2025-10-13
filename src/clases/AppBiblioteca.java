package clases;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class AppBiblioteca {
    private JFrame frame;
    private JPanel panelLogin, panelAlumno, panelBib;

    // Login
    private JTextField txtCorreo;
    private JPasswordField txtPassword;
    private JButton btnIniciar, btnLimpiar, btnSalirLogin;

    // Panel Alumno (Usuario Básico)
    private JTextArea areaAlumno;
    private JButton btnPrestar, btnDevolver, btnCerrarSesionA, btnSalirA;

    // Panel Bibliotecario
    private JTextArea areaBib;
    private JButton btnCambiarEstado, btnCargarLibro, btnAltaUsuario, btnActivarUser, btnInactivarUser, btnCerrarSesionB, btnSalirB;

    private String emailmatch = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    private Biblioteca biblioteca;
    private Usuario usuarioLogueado;

    public static void main(String[] args) {
        AppBiblioteca w = new AppBiblioteca();
        w.frame.setVisible(true);
    }

    public AppBiblioteca() {
        biblioteca = new Biblioteca();
        inicializar();
    }

    private void inicializar() {
        frame = new JFrame("Biblioteca – UCEL");
        frame.setBounds(100, 100, 820, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new CardLayout());

        Font fBtn = new Font("SansSerif", Font.PLAIN, 15);

        // ====== LOGIN ======
        panelLogin = new JPanel(new GridLayout(5, 2, 6, 6));
        panelLogin.add(new JLabel("Correo:"));
        txtCorreo = new JTextField();
        panelLogin.add(txtCorreo);

        panelLogin.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        txtPassword.setEchoChar('*');
        panelLogin.add(txtPassword);

        btnIniciar = new JButton("Iniciar sesión");
        btnLimpiar = new JButton("Limpiar");
        btnSalirLogin = new JButton("Salir");
        btnIniciar.setFont(fBtn); btnLimpiar.setFont(fBtn); btnSalirLogin.setFont(fBtn);

        panelLogin.add(btnIniciar);
        panelLogin.add(btnLimpiar);
        panelLogin.add(btnSalirLogin);
        panelLogin.add(new JLabel(""));

        // ====== PANEL ALUMNO (Usuario Básico) ======
        panelAlumno = new JPanel(new GridLayout(2, 1, 6, 6));
        areaAlumno = new JTextArea(18, 60);
        areaAlumno.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaAlumno.setEditable(false);
        panelAlumno.add(new JScrollPane(areaAlumno));

        JPanel botonesAlumno = new JPanel(new GridLayout(1, 4, 6, 6));
        btnPrestar = new JButton("Nuevo Préstamo");
        btnDevolver = new JButton("Devolver");
        btnCerrarSesionA = new JButton("Cerrar sesión");
        btnSalirA = new JButton("Salir");
        btnPrestar.setFont(fBtn); btnDevolver.setFont(fBtn); btnCerrarSesionA.setFont(fBtn); btnSalirA.setFont(fBtn);
        botonesAlumno.add(btnPrestar); botonesAlumno.add(btnDevolver); botonesAlumno.add(btnCerrarSesionA); botonesAlumno.add(btnSalirA);
        panelAlumno.add(botonesAlumno);

        // ====== PANEL BIBLIOTECARIO ======
        panelBib = new JPanel(new GridLayout(2, 1, 6, 6));
        areaBib = new JTextArea(18, 60);
        areaBib.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaBib.setEditable(false);
        panelBib.add(new JScrollPane(areaBib));

        JPanel botonesBib = new JPanel(new GridLayout(2, 3, 6, 6));
        btnCambiarEstado = new JButton("Cambiar estado libro");
        btnCargarLibro   = new JButton("Cargar nuevo libro");
        btnAltaUsuario   = new JButton("Alta usuario básico");
        btnActivarUser   = new JButton("Activar usuario");
        btnInactivarUser = new JButton("Inactivar usuario");
        btnCerrarSesionB = new JButton("Cerrar sesión");
        btnSalirB        = new JButton("Salir");

        btnCambiarEstado.setFont(fBtn);
        btnCargarLibro.setFont(fBtn);
        btnAltaUsuario.setFont(fBtn);
        btnActivarUser.setFont(fBtn);
        btnInactivarUser.setFont(fBtn);
        btnCerrarSesionB.setFont(fBtn);
        btnSalirB.setFont(fBtn);

        botonesBib.add(btnCambiarEstado);
        botonesBib.add(btnCargarLibro);
        botonesBib.add(btnAltaUsuario);
        botonesBib.add(btnActivarUser);
        botonesBib.add(btnInactivarUser);
        botonesBib.add(btnCerrarSesionB);
        JPanel filaSalir = new JPanel(new GridLayout(1,1));
        filaSalir.add(btnSalirB);
        panelBib.add(botonesBib);
        panelBib.add(filaSalir);

        frame.add(panelLogin, "LOGIN");
        frame.add(panelAlumno, "ALUMNO");
        frame.add(panelBib, "BIB");

        btnIniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!validarLogin()) return;
                if (usuarioLogueado instanceof UsuarioBasico) {
                    refrescarAlumno("Sesión iniciada (Usuario Básico).");
                    actualizarBotonesAlumno();
                    mostrar("ALUMNO");
                } else { 
                    refrescarBib("Sesión iniciada (Bibliotecario).");
                    mostrar("BIB");
                }
            }
        });

        btnLimpiar.addActionListener(e -> { txtCorreo.setText(""); txtPassword.setText(""); });
        btnSalirLogin.addActionListener(e -> System.exit(0));

        btnPrestar.addActionListener(e -> {
            if (!asegurarSesion()) return;
            String icbn = pedir("Ingrese ICBN a prestar:", "Préstamo");
            if (icbn == null) return;
            String r = biblioteca.solicitarPrestamo(usuarioLogueado, icbn);
            JOptionPane.showMessageDialog(frame, r, "Nuevo Préstamo", msgType(r));
            refrescarAlumno("Acción: Prestar (" + icbn + ")\nResultado: " + r);
            actualizarBotonesAlumno();
        });

        btnDevolver.addActionListener(e -> {
            if (!asegurarSesion()) return;
            String r = biblioteca.devolverLibro(usuarioLogueado);
            JOptionPane.showMessageDialog(frame, r, "Devolver", msgType(r));
            refrescarAlumno("Acción: Devolver\nResultado: " + r);
            actualizarBotonesAlumno();
        });

        btnCerrarSesionA.addActionListener(e -> cerrarSesion());
        btnSalirA.addActionListener(e -> System.exit(0));

        btnCambiarEstado.addActionListener(e -> {
            if (!asegurarSesion()) return;
            String icbn = pedir("ICBN del libro:", "Cambiar estado");
            if (icbn == null) return;
            String estado = pedir("Nuevo estado (DISPONIBLE / PRESTADO / RESERVADO / NO_DISPONIBLE):", "Cambiar estado");
            if (estado == null) return;

            estado = estado.trim().toUpperCase().replace(' ', '_');
            EstadoLibro nuevo;
            try {
                nuevo = EstadoLibro.valueOf(estado);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Estado inválido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String r = biblioteca.cambiarEstadoLibro(usuarioLogueado, icbn, nuevo);
            JOptionPane.showMessageDialog(frame, r, "Cambiar estado", msgType(r));
            refrescarBib("Acción: Cambiar estado (" + icbn + " → " + nuevo + ")\nResultado: " + r);
        });

        btnCargarLibro.addActionListener(e -> {
            if (!asegurarSesion()) return;
            String icbn = pedir("ICBN:", "Cargar libro"); if (icbn == null) return;
            String titulo = pedir("Título:", "Cargar libro"); if (titulo == null) return;
            String autor = pedir("Autor:", "Cargar libro"); if (autor == null) return;
            String edicion = pedir("Edición:", "Cargar libro"); if (edicion == null) return;

            String r = biblioteca.altaLibro(usuarioLogueado, icbn, titulo, autor, edicion);
            JOptionPane.showMessageDialog(frame, r, "Cargar libro", msgType(r));
            refrescarBib("Acción: Cargar libro (" + icbn + ")\nResultado: " + r);
        });

        btnAltaUsuario.addActionListener(e -> {
            if (!asegurarSesion()) return;
            String nombre = pedir("Nombre:", "Alta usuario básico"); if (nombre == null) return;
            String apellido = pedir("Apellido:", "Alta usuario básico"); if (apellido == null) return;
            String correo = pedir("Correo:", "Alta usuario básico"); if (correo == null) return;
            if (!correo.matches(emailmatch)) { JOptionPane.showMessageDialog(frame, "Correo inválido", "Error", JOptionPane.ERROR_MESSAGE); return; }
            String pass = pedir("Contraseña:", "Alta usuario básico"); if (pass == null) return;

            String r = biblioteca.altaUsuarioBasico(usuarioLogueado, nombre, apellido, correo, pass);
            JOptionPane.showMessageDialog(frame, r, "Alta usuario", msgType(r));
            refrescarBib("Acción: Alta usuario básico (" + correo + ")\nResultado: " + r);
        });

        btnActivarUser.addActionListener(e -> {
            if (!asegurarSesion()) return;
            String correo = pedir("Correo a ACTIVAR:", "Activar usuario");
            if (correo == null) return;
            String r = biblioteca.activarUsuario(usuarioLogueado, correo);
            JOptionPane.showMessageDialog(frame, r, "Activar usuario", msgType(r));
            refrescarBib("Acción: Activar usuario (" + correo + ")\nResultado: " + r);
        });

        btnInactivarUser.addActionListener(e -> {
            if (!asegurarSesion()) return;
            String correo = pedir("Correo a INACTIVAR:", "Inactivar usuario");
            if (correo == null) return;
            String r = biblioteca.inactivarUsuario(usuarioLogueado, correo);
            JOptionPane.showMessageDialog(frame, r, "Inactivar usuario", msgType(r));
            refrescarBib("Acción: Inactivar usuario (" + correo + ")\nResultado: " + r);
        });

        btnCerrarSesionB.addActionListener(e -> cerrarSesion());
        btnSalirB.addActionListener(e -> System.exit(0));
    }

    // ===== Helpers comunes =====
    private boolean validarLogin() {
        String email = txtCorreo.getText().trim();
        if (!email.matches(emailmatch)) {
            JOptionPane.showMessageDialog(frame, "Correo inválido", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        String pass = new String(txtPassword.getPassword()).trim();
        if (pass.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Contraseña vacía", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        usuarioLogueado = biblioteca.login(email, pass);
        if (usuarioLogueado == null) {
            JOptionPane.showMessageDialog(frame, "Credenciales inválidas o usuario inactivo", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean asegurarSesion() {
        if (usuarioLogueado == null) {
            JOptionPane.showMessageDialog(frame, "Inicie sesión primero", "Error", JOptionPane.ERROR_MESSAGE);
            mostrar("LOGIN");
            return false;
        }
        return true;
    }

    private void cerrarSesion() {
        usuarioLogueado = null;
        txtPassword.setText("");
        mostrar("LOGIN");
    }

    private void mostrar(String nombre) {
        CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
        cl.show(frame.getContentPane(), nombre);
    }

    private int msgType(String resp) {
        String r = resp.toLowerCase();
        return (r.contains("éxito") || r.contains("realiz")) ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE;
    }

    private String pedir(String msg, String titulo) {
        String s = JOptionPane.showInputDialog(frame, msg, titulo, JOptionPane.QUESTION_MESSAGE);
        if (s == null) return null;
        s = s.trim();
        if (s.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Campo vacío", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return s;
    }

    private void refrescarAlumno(String encabezado) {
        String datos = (encabezado == null ? "" : encabezado + "\n\n");
        UsuarioBasico ub = (UsuarioBasico) usuarioLogueado;
        datos += "Usuario: " + ub.getNombre() + " " + ub.getApellido() + "\n";
        datos += "Tipo: USUARIO\n";
        datos += "Préstamo vigente: " + (ub.tienePrestamoVigente() ? ub.getIcbnPrestado() : "(ninguno)") + "\n";
        datos += "\nLibros disponibles:\n";
        for (int i = 0; i < biblioteca.librosDisponibles().size(); i++) {
            Libro l = biblioteca.librosDisponibles().get(i);
            datos += l.getIcbn() + " - " + l.getTitulo() + " [" + l.getEstado() + "]\n";
        }
        areaAlumno.setText(datos);
    }

    private void actualizarBotonesAlumno() {
        boolean puedeDevolver = false, puedePrestar = false;
        if (usuarioLogueado instanceof UsuarioBasico) {
            UsuarioBasico ub = (UsuarioBasico) usuarioLogueado;
            puedeDevolver = ub.tienePrestamoVigente();
            puedePrestar = !ub.tienePrestamoVigente(); // máx 1 a la vez
        }
        btnPrestar.setEnabled(puedePrestar);
        btnDevolver.setEnabled(puedeDevolver);
    }

    private void refrescarBib(String encabezado) {
        String datos = (encabezado == null ? "" : encabezado + "\n\n");
        datos += "Bibliotecario: " + usuarioLogueado.getNombre() + " " + usuarioLogueado.getApellido() + "\n";
        datos += "\nLibros (todos):\n";
        for (int i = 0; i < biblioteca.getLibros().size(); i++) {
            Libro l = biblioteca.getLibros().get(i);
            datos += l.getIcbn() + " - " + l.getTitulo() + " [" + l.getEstado() + "]\n";
        }
        datos += "\nUsuarios:\n";
        for (int i = 0; i < biblioteca.getUsuarios().size(); i++) {
            Usuario u = biblioteca.getUsuarios().get(i);
            datos += u.getNombre() + " " + u.getApellido() + " - " + u.getCorreo() + " (" + u.getEstado() + ", " + u.getTipo() + ")\n";
        }
        areaBib.setText(datos);
    }
}
