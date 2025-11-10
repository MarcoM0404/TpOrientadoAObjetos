package clases;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Dimension; 
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

	// panel alumno
	private JTextArea areaAlumno;
	private JButton btnPrestar, btnDevolver, btnCerrarSesionA, btnSalirA;

	// Panel bibliotecario
	private JTextArea areaBib;
	private JButton btnCambiarEstado, btnCargarLibro, btnActivarUser, btnInactivarUser,
			btnCerrarSesionB, btnSalirB;
	
	// La lógica vuelve a estar aquí
	private Biblioteca biblioteca;
	private Usuario usuarioLogueado;
	private String emailmatch = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

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

		panelLogin = crearPanelLogin();
		panelAlumno = crearPanelAlumno();
		panelBib = crearPanelBib();

		frame.add(panelLogin, "LOGIN");
		frame.add(panelAlumno, "ALUMNO");
		frame.add(panelBib, "BIB");

		asignarListeners();
	}

	// creacion de paneles (sin cambios)
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
					mostrarPanel("ALUMNO");
				} else {
					refrescarBib("Sesión iniciada (Bibliotecario).");
					mostrarPanel("BIB");
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
			if (!asegurarSesion()) return;
			
			ArrayList<Libro> disponibles = biblioteca.librosDisponibles();
			if (disponibles.isEmpty()) {
				mostrarMensaje("No hay libros disponibles para prestar.", "Préstamo", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			String icbn = mostrarSelectorLibros(disponibles);
			if (icbn == null) return; 
			
			String r = biblioteca.solicitarPrestamo(usuarioLogueado, icbn);
			mostrarMensaje(r, "Nuevo Préstamo", msgType(r));
			refrescarAlumno("Acción: Prestar (" + icbn + ")\nResultado: " + r);
			actualizarBotonesAlumno();
		});

		btnDevolver.addActionListener(e -> {
			if (!asegurarSesion()) return;
			String r = biblioteca.devolverLibro(usuarioLogueado);
			mostrarMensaje(r, "Devolver", msgType(r));
			refrescarAlumno("Acción: Devolver\nResultado: " + r);
			actualizarBotonesAlumno();
		});

		btnCerrarSesionA.addActionListener(e -> cerrarSesion());
		btnSalirA.addActionListener(salirListener);

		// bibliotecario
		btnCambiarEstado.addActionListener(e -> {
			if (!asegurarSesion()) return;
			
			ArrayList<Libro> todosLosLibros = biblioteca.getLibros();
			if (todosLosLibros.isEmpty()) {
				mostrarMensaje("No hay libros cargados en el sistema.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String icbn = mostrarSelectorTodosLibros(todosLosLibros);
			if (icbn == null) return;

			EstadoLibro nuevoEstado = mostrarSelectorEstadoLibro();
			if (nuevoEstado == null) return;
			
			String r = biblioteca.cambiarEstadoLibro(usuarioLogueado, icbn, nuevoEstado);
			mostrarMensaje(r, "Cambiar estado", msgType(r));
			refrescarBib("Acción: Cambiar estado (" + icbn + " → " + nuevoEstado + ")\nResultado: " + r);
		});

		btnCargarLibro.addActionListener(e -> {
			if (!asegurarSesion()) return;
			String icbn = pedirDato("ICBN:", "Cargar libro");
			if (icbn == null) return;
			String titulo = pedirDato("Título:", "Cargar libro");
			if (titulo == null) return;
			String autor = pedirDato("Autor:", "Cargar libro");
			if (autor == null) return;
			String edicion = pedirDato("Edición:", "Cargar libro");
			if (edicion == null) return;
			String r = biblioteca.altaLibro(usuarioLogueado, icbn, titulo, autor, edicion);
			mostrarMensaje(r, "Cargar libro", msgType(r));
			refrescarBib("Acción: Cargar libro (" + icbn + ")\nResultado: " + r);
		});

		btnActivarUser.addActionListener(e -> {
			if (!asegurarSesion()) return;
			
			ArrayList<Usuario> usuariosBasicos = new ArrayList<>();
			for (Usuario u : biblioteca.getUsuarios()) {
				if (u.getTipo() == TipoUsuario.USUARIO) {
					usuariosBasicos.add(u);
				}
			}
			
			String correo = mostrarSelectorUsuarios(usuariosBasicos, "Seleccione usuario a ACTIVAR");
			if (correo == null) return;
			
			String r = biblioteca.activarUsuario(usuarioLogueado, correo);
			mostrarMensaje(r, "Activar usuario", msgType(r));
			refrescarBib("Acción: Activar usuario (" + correo + ")\nResultado: " + r);
		});

		btnInactivarUser.addActionListener(e -> {
			if (!asegurarSesion()) return;

			ArrayList<Usuario> usuariosBasicos = new ArrayList<>();
			for (Usuario u : biblioteca.getUsuarios()) {
				if (u.getTipo() == TipoUsuario.USUARIO) {
					usuariosBasicos.add(u);
				}
			}
			
			String correo = mostrarSelectorUsuarios(usuariosBasicos, "Seleccione usuario a INACTIVAR");
			if (correo == null) return;

			String r = biblioteca.inactivarUsuario(usuarioLogueado, correo);
			mostrarMensaje(r, "Inactivar usuario", msgType(r));
			refrescarBib("Acción: Inactivar usuario (" + correo + ")\nResultado: " + r);
		});

		btnCerrarSesionB.addActionListener(e -> cerrarSesion());
		btnSalirB.addActionListener(salirListener);
	}



	private boolean validarLogin() {
		String email = txtCorreo.getText().trim();
		String pass = new String(txtPassword.getPassword()).trim();

		if (!email.matches(emailmatch)) {
			mostrarMensaje("Correo inválido", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (pass.isEmpty()) {
			mostrarMensaje("Contraseña vacía", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		Usuario u = biblioteca.buscarUsuarioPorCorreo(email);
		
		if (u == null) {
			mostrarMensaje("Credenciales inválidas.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if (!u.getPassword().equals(pass)) {
			mostrarMensaje("Credenciales inválidas.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!u.esActivo()) {
			mostrarMensaje("Este usuario se encuentra INACTIVO.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		usuarioLogueado = u;
		return true;
	}

	private boolean asegurarSesion() {
		if (usuarioLogueado == null) {
			mostrarMensaje("Inicie sesión primero", "Error", JOptionPane.ERROR_MESSAGE);
			mostrarPanel("LOGIN");
			return false;
		}
		return true;
	}
	
	private void cerrarSesion() {
		usuarioLogueado = null;
		txtPassword.setText("");
		mostrarPanel("LOGIN");
	}

	
	private void mostrarPanel(String nombre) {
		CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
		cl.show(frame.getContentPane(), nombre);
	}

	private void mostrarMensaje(String msg, String titulo, int msgType) {
		JOptionPane.showMessageDialog(frame, msg, titulo, msgType);
	}

	private String pedirDato(String msg, String titulo) {
		String s = JOptionPane.showInputDialog(frame, msg, titulo, JOptionPane.QUESTION_MESSAGE);
		if (s == null)
			return null;
		s = s.trim();
		if (s.isEmpty()) {
			mostrarMensaje("Campo vacío", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return s;
	}

	private int msgType(String resp) {
		String r = resp.toLowerCase();
		return (r.contains("éxito") || r.contains("realiz")) ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE;
	}

	// metodos para refrescar

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
		boolean puedeDevolver = false;
		boolean puedePrestar = false;
		if (usuarioLogueado != null && usuarioLogueado.getTipo() == TipoUsuario.USUARIO) {
			boolean tienePrestamo = biblioteca.usuarioTienePrestamoVigente(usuarioLogueado);
			puedeDevolver = tienePrestamo;
			puedePrestar = !tienePrestamo;
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
	
	// metodos para seleccionar 
	
	private String mostrarSelectorLibros(ArrayList<Libro> libros) {
		JPanel panel = new JPanel(new GridLayout(0, 1)); 
		ButtonGroup group = new ButtonGroup();

		for (Libro libro : libros) {
			String texto = libro.getTitulo() + " (de " + libro.getAutor() + ")";
			JRadioButton radio = new JRadioButton(texto);
			radio.setActionCommand(libro.getIcbn()); 
			panel.add(radio);
			group.add(radio);
		}

		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setPreferredSize(new Dimension(400, 300)); 

		int result = JOptionPane.showConfirmDialog(frame, scrollPane, "Seleccionar Libro para Préstamo",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			if (group.getSelection() != null) {
				return group.getSelection().getActionCommand(); // Devuelve el ICBN
			}
		}
		return null; 
	}
	
	private String mostrarSelectorTodosLibros(ArrayList<Libro> libros) {
		JPanel panel = new JPanel(new GridLayout(0, 1)); 
		ButtonGroup group = new ButtonGroup();

		for (Libro libro : libros) {
			String texto = libro.getTitulo() + " (" + libro.getIcbn() + ") [" + libro.getEstado() + "]";
			JRadioButton radio = new JRadioButton(texto);
			radio.setActionCommand(libro.getIcbn()); // Guardamos el ICBN
			panel.add(radio);
			group.add(radio);
		}

		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setPreferredSize(new Dimension(450, 300)); 

		int result = JOptionPane.showConfirmDialog(frame, scrollPane, "Seleccionar Libro",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			if (group.getSelection() != null) {
				return group.getSelection().getActionCommand(); // devuelve el ICBN
			}
		}
		return null;
	}

	private String mostrarSelectorUsuarios(ArrayList<Usuario> usuarios, String titulo) {
		JPanel panel = new JPanel(new GridLayout(0, 1));
		ButtonGroup group = new ButtonGroup();

		for (Usuario user : usuarios) {
			String texto = user.getNombre() + " " + user.getApellido() + " (" + user.getCorreo() + ") [" + user.getEstado() + "]";
			JRadioButton radio = new JRadioButton(texto);
			radio.setActionCommand(user.getCorreo());
			panel.add(radio);
			group.add(radio);
		}

		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setPreferredSize(new Dimension(450, 300));

		int result = JOptionPane.showConfirmDialog(frame, scrollPane, titulo,
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			if (group.getSelection() != null) {
				return group.getSelection().getActionCommand();
			}
		}
		return null;
	}
	
	private EstadoLibro mostrarSelectorEstadoLibro() {
		JPanel panel = new JPanel(new GridLayout(0, 1));
		ButtonGroup group = new ButtonGroup();
		
		for (EstadoLibro estado : EstadoLibro.values()) {
			JRadioButton radio = new JRadioButton(estado.toString());
			radio.setActionCommand(estado.name()); 
			panel.add(radio);
			group.add(radio);
		}

		int result = JOptionPane.showConfirmDialog(frame, panel, "Seleccionar Nuevo Estado",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			if (group.getSelection() != null) {
				return EstadoLibro.valueOf(group.getSelection().getActionCommand());
			}
		}
		return null; 
	}
}