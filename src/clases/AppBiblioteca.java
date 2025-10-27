package clases;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints; 
import java.awt.GridBagLayout; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder; 

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
	private JButton btnCambiarEstado, btnCargarLibro, btnActivarUser, btnInactivarUser,
			btnCerrarSesionB, btnSalirB;

	private String emailmatch = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

	private Biblioteca biblioteca;
	private Usuario usuarioLogueado;

	private ActionListener salirListener = e -> System.exit(0);

	private Font fBtn = new Font("SansSerif", Font.PLAIN, 15);
	private Font fArea = new Font("Monospaced", Font.PLAIN, 13);

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

		// crear paneles
		panelLogin = crearPanelLogin();
		panelAlumno = crearPanelAlumno();
		panelBib = crearPanelBib();

		// agregar paneles al frame
		frame.add(panelLogin, "LOGIN");
		frame.add(panelAlumno, "ALUMNO");
		frame.add(panelBib, "BIB");

		//listeners
		asignarListeners();
	}

	// creacion de paneles
	

	private JPanel crearPanelLogin() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel loginBox = new JPanel(new BorderLayout(10, 10));

		JPanel panelCampos = new JPanel(new GridLayout(2, 2, 6, 6));
		panelCampos.add(new JLabel("Correo:"));
		txtCorreo = new JTextField(20);
		panelCampos.add(txtCorreo);

		panelCampos.add(new JLabel("Contraseña:"));
		txtPassword = new JPasswordField();
		panelCampos.add(txtPassword);

		JPanel panelBotones = new JPanel(new GridLayout(1, 3, 6, 6));
		btnIniciar = new JButton("Iniciar sesión");
		btnLimpiar = new JButton("Limpiar");
		btnSalirLogin = new JButton("Salir");
		btnIniciar.setFont(fBtn);
		btnLimpiar.setFont(fBtn);
		btnSalirLogin.setFont(fBtn);

		panelBotones.add(btnIniciar);
		panelBotones.add(btnLimpiar);
		panelBotones.add(btnSalirLogin);

		loginBox.add(panelCampos, BorderLayout.NORTH);
		loginBox.add(panelBotones, BorderLayout.SOUTH);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;

		panel.add(loginBox, gbc);

		return panel;
	}

	private JPanel crearPanelAlumno() {
		JPanel panel = new JPanel(new BorderLayout(6, 6));

		areaAlumno = new JTextArea(18, 60);
		areaAlumno.setFont(fArea);
		areaAlumno.setEditable(false);
		panel.add(new JScrollPane(areaAlumno), BorderLayout.CENTER);

		JPanel botonesAlumno = new JPanel(new GridLayout(1, 4, 6, 6));
		btnPrestar = new JButton("Nuevo Préstamo");
		btnDevolver = new JButton("Devolver");
		btnCerrarSesionA = new JButton("Cerrar sesión");
		btnSalirA = new JButton("Salir");

		btnPrestar.setFont(fBtn);
		btnDevolver.setFont(fBtn);
		btnCerrarSesionA.setFont(fBtn);
		btnSalirA.setFont(fBtn);

		botonesAlumno.add(btnPrestar);
		botonesAlumno.add(btnDevolver);
		botonesAlumno.add(btnCerrarSesionA);
		botonesAlumno.add(btnSalirA);

		panel.add(botonesAlumno, BorderLayout.SOUTH);
		return panel;
	}

	private JPanel crearPanelBib() {
		JPanel panel = new JPanel(new BorderLayout(6, 6));

		areaBib = new JTextArea(18, 60);
		areaBib.setFont(fArea);
		areaBib.setEditable(false);
		panel.add(new JScrollPane(areaBib), BorderLayout.CENTER);

		JPanel botonesBib = new JPanel(new GridLayout(2, 3, 6, 6));
		btnCambiarEstado = new JButton("Cambiar estado libro");
		btnCargarLibro = new JButton("Cargar nuevo libro");
		btnActivarUser = new JButton("Activar usuario");
		btnInactivarUser = new JButton("Inactivar usuario");
		btnCerrarSesionB = new JButton("Cerrar sesión");
		btnSalirB = new JButton("Salir");

		btnCambiarEstado.setFont(fBtn);
		btnCargarLibro.setFont(fBtn);
		btnActivarUser.setFont(fBtn);
		btnInactivarUser.setFont(fBtn);
		btnCerrarSesionB.setFont(fBtn);
		btnSalirB.setFont(fBtn);

		botonesBib.add(btnCambiarEstado);
		botonesBib.add(btnCargarLibro);
		botonesBib.add(btnActivarUser);
		botonesBib.add(btnInactivarUser);
		botonesBib.add(btnCerrarSesionB);
		botonesBib.add(btnSalirB);

		panel.add(botonesBib, BorderLayout.SOUTH);
		return panel;
	}

	private void asignarListeners() {

		// login
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!validarLogin())
					return;

				if (usuarioLogueado.getTipo() == TipoUsuario.USUARIO) {
					refrescarAlumno("Sesión iniciada (Usuario Básico).");
					actualizarBotonesAlumno();
					mostrar("ALUMNO");
				} else {
					refrescarBib("Sesión iniciada (Bibliotecario).");
					mostrar("BIB");
				}
			}
		});

		btnLimpiar.addActionListener(e -> {
			txtCorreo.setText("");
			txtPassword.setText("");
		});
		btnSalirLogin.addActionListener(salirListener);

		// usuario
		btnPrestar.addActionListener(e -> {
			if (!asegurarSesion())
				return;
			String icbn = pedir("Ingrese ICBN a prestar:", "Préstamo");
			if (icbn == null)
				return;
			String r = biblioteca.solicitarPrestamo(usuarioLogueado, icbn);
			JOptionPane.showMessageDialog(frame, r, "Nuevo Préstamo", msgType(r));
			refrescarAlumno("Acción: Prestar (" + icbn + ")\nResultado: " + r);
			actualizarBotonesAlumno();
		});

		btnDevolver.addActionListener(e -> {
			if (!asegurarSesion())
				return;
			String r = biblioteca.devolverLibro(usuarioLogueado);
			JOptionPane.showMessageDialog(frame, r, "Devolver", msgType(r));
			refrescarAlumno("Acción: Devolver\nResultado: " + r);
			actualizarBotonesAlumno();
		});

		btnCerrarSesionA.addActionListener(e -> cerrarSesion());
		btnSalirA.addActionListener(salirListener);

		// bibliotecario
		btnCambiarEstado.addActionListener(e -> {
			if (!asegurarSesion())
				return;
			String icbn = pedir("ICBN del libro:", "Cambiar estado");
			if (icbn == null)
				return;
			String estado = pedir("Nuevo estado (DISPONIBLE / PRESTADO / RESERVADO / NO_DISPONIBLE):", "Cambiar estado");
			if (estado == null)
				return;

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
			if (!asegurarSesion())
				return;
			String icbn = pedir("ICBN:", "Cargar libro");
			if (icbn == null)
				return;
			String titulo = pedir("Título:", "Cargar libro");
			if (titulo == null)
				return;
			String autor = pedir("Autor:", "Cargar libro");
			if (autor == null)
				return;
			String edicion = pedir("Edición:", "Cargar libro");
			if (edicion == null)
				return;

			String r = biblioteca.altaLibro(usuarioLogueado, icbn, titulo, autor, edicion);
			JOptionPane.showMessageDialog(frame, r, "Cargar libro", msgType(r));
			refrescarBib("Acción: Cargar libro (" + icbn + ")\nResultado: " + r);
		});


		btnActivarUser.addActionListener(e -> {
			if (!asegurarSesion())
				return;
			String correo = pedir("Correo a ACTIVAR:", "Activar usuario");
			if (correo == null)
				return;
			String r = biblioteca.activarUsuario(usuarioLogueado, correo);
			JOptionPane.showMessageDialog(frame, r, "Activar usuario", msgType(r));
			refrescarBib("Acción: Activar usuario (" + correo + ")\nResultado: " + r);
		});

		btnInactivarUser.addActionListener(e -> {
			if (!asegurarSesion())
				return;
			String correo = pedir("Correo a INACTIVAR:", "Inactivar usuario");
			if (correo == null)
				return;
			String r = biblioteca.inactivarUsuario(usuarioLogueado, correo);
			JOptionPane.showMessageDialog(frame, r, "Inactivar usuario", msgType(r));
			refrescarBib("Acción: Inactivar usuario (" + correo + ")\nResultado: " + r);
		});

		btnCerrarSesionB.addActionListener(e -> cerrarSesion());
		btnSalirB.addActionListener(salirListener);
	}


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
			JOptionPane.showMessageDialog(frame, "Credenciales inválidas o usuario inactivo", "Error",
					JOptionPane.ERROR_MESSAGE);
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
		if (s == null)
			return null;
		s = s.trim();
		if (s.isEmpty()) {
			JOptionPane.showMessageDialog(frame, "Campo vacío", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return s;
	}



	private void refrescarAlumno(String encabezado) {
		String datos = (encabezado == null ? "" : encabezado + "\n\n");

		datos += "Usuario: " + usuarioLogueado.getNombre() + " " + usuarioLogueado.getApellido() + "\n";
		datos += "Tipo: USUARIO\n";

		String icbnPrestado = biblioteca.getIcbnPrestadoPorUsuario(usuarioLogueado);
		datos += "Préstamo vigente: " + (icbnPrestado != null ? icbnPrestado : "(ninguno)") + "\n";

		datos += "\nLibros disponibles:\n";
		ArrayList<Libro> disponibles = biblioteca.librosDisponibles();
		for (int i = 0; i < disponibles.size(); i++) {
			Libro l = disponibles.get(i);
			datos += l.getIcbn() + " - " + l.getTitulo() + " [" + l.getEstado() + "]\n";
		}
		areaAlumno.setText(datos);
	}

	private void actualizarBotonesAlumno() {
		boolean puedeDevolver = false, puedePrestar = false;

		if (usuarioLogueado != null && usuarioLogueado.getTipo() == TipoUsuario.USUARIO) {
			boolean tienePrestamo = biblioteca.usuarioTienePrestamoVigente(usuarioLogueado);
			puedeDevolver = tienePrestamo;
			puedePrestar = !tienePrestamo; // max 1 a la vez
		}

		btnPrestar.setEnabled(puedePrestar);
		btnDevolver.setEnabled(puedeDevolver);
	}

	private void refrescarBib(String encabezado) {
		String datos = (encabezado == null ? "" : encabezado + "\n\n");
		datos += "Bibliotecario: " + usuarioLogueado.getNombre() + " " + usuarioLogueado.getApellido() + "\n";

		datos += "\nLibros (todos):\n";
		ArrayList<Libro> libros = biblioteca.getLibros();
		for (int i = 0; i < libros.size(); i++) {
			Libro l = libros.get(i);
			datos += l.getIcbn() + " - " + l.getTitulo() + " [" + l.getEstado() + "]\n";
		}

		datos += "\nUsuarios:\n";
		ArrayList<Usuario> usuarios = biblioteca.getUsuarios();
		for (int i = 0; i < usuarios.size(); i++) {
			Usuario u = usuarios.get(i);
			datos += u.getNombre() + " " + u.getApellido() + " - " + u.getCorreo() + " (" + u.getEstado() + ", "
					+ u.getTipo() + ")\n";
		}
		areaBib.setText(datos);
	}
}