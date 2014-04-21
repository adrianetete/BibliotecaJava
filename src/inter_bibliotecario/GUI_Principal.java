package inter_bibliotecario;

import inicio_Sesion.Acceso;
import inicio_Sesion.datosRegistrarse;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;

import tablas.Bibliotecario;

public class GUI_Principal {
	
	//Objeto estático que se mantiene durante la sesión
	static Bibliotecario actual = new Bibliotecario(null, null, null, null);
	static Connection con;
	
	/* * * * * * * * Ventana General * * * * * * */
	private JFrame generalBibliotecario = new JFrame("Bibliotecario");
	
	/* * * * * * * Panel Principal * * * * * * * * */
	private JPanel panel = new JPanel();		
		
	/* * * * * * * Panel con los Botones * * * * * */
	private JPanel panel_1 = new JPanel();
	private JButton devolver = new JButton("Devolver Documento");
	private JButton prestamo = new JButton("Realizar un Pr\u00E9stamo");
	private JButton datos = new JButton("Mis Datos");
	private JButton bibliografia = new JButton("Bibliograf\u00EDa");
	
	private JLabel lblaccion = new JLabel("Elige una acci\u00F3n:");
	private JLabel lbltitulo = new JLabel("Conectado como:");
	private JLabel txtNombre_titulo = new JLabel();
	private JButton nuevousuario = new JButton("Nuevo Usuario");
	private final JButton salir = new JButton("Desconectarme");
	
	
	public GUI_Principal(Bibliotecario actual, Connection con) {
		GUI_Principal.con = con;
		GUI_Principal.actual = actual;
		txtNombre_titulo.setText(actual.getNombre() + " " + actual.getApellido());
		colocar();
		
	}
	public void Mostrar(){
		generalBibliotecario.setVisible(true);
		Acceso.ventana.setVisible(false);
	}

	private void colocar() {	
	
		
		/* * * * * * * Ventana General * * * * * * * */
		generalBibliotecario.setBounds(450, 340, 387, 321);
		generalBibliotecario.getContentPane().setLayout(null);//absolute layout
		generalBibliotecario.setResizable(false);
		
		/* * * * * * * Panel principal * * * * * * * */		
		panel.setBounds(10, 11, 362, 33);	
		panel.setLayout(null);
		panel.setBorder(UIManager.getBorder("PasswordField.border"));
			//eqtiqueta de "registrado como:"
			lbltitulo.setBounds(10, 8, 142, 17);
			lbltitulo.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
			panel.add(lbltitulo);
			
			//etiqueta del nombre
			txtNombre_titulo.setBounds(151, 7, 201, 19);
			txtNombre_titulo.setFont(new Font("MS Reference Sans Serif", Font.ITALIC, 14));
			panel.add(txtNombre_titulo);//añadir al panel			
		generalBibliotecario.getContentPane().add(panel);
		
		panel_1.setBounds(10, 55, 362, 227);
		panel_1.setLayout(null);
		panel_1.setBorder(UIManager.getBorder("PasswordField.border"));
			//boton devolver
			devolver.setBounds(10, 36, 156, 31);
			devolver.setToolTipText("Devuelve un documento prestado a un determinado Usuario.");
			panel_1.add(devolver);
			
			//boton prestamo
			prestamo.setBounds(10, 94, 156, 31);
			prestamo.setToolTipText("Realiza un préstamo de un documento a un Usuario");
			panel_1.add(prestamo);
			
			//boton de datos
			datos.setBounds(192, 36, 140, 31);
			datos.setToolTipText("Informa sobre los datos personales del Bibliotecario");
			panel_1.add(datos);
			
			//boton de bibliografia	
			bibliografia.setBounds(192, 94, 140, 73);
			bibliografia.setToolTipText("Gestión de los libros que hay en la biblioteca.");
			panel_1.add(bibliografia);
		generalBibliotecario.getContentPane().add(panel_1);
		lblaccion.setBounds(10, 11, 257, 14);
		panel_1.add(lblaccion);

		nuevousuario.setBounds(10, 136, 156, 31);
		panel_1.add(nuevousuario);
		salir.setBounds(211, 193, 121, 23);
		
		panel_1.add(salir);
		
			//Accion del botón devolver
		devolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Crea una nueva ventana para devolver un libro
				Devolver ventanaDevolver = new Devolver();
				ventanaDevolver.Mostrar();
			}
		});
		
		//Accion del boton Mis Datos
		datos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				//crea una nueva ventana con los datos del Bibliotecario
				Datos ventanaDatos = new Datos();
				ventanaDatos.Mostrar();
				
			}
		});
		//Acción del boton Bibliografía
		bibliografia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Crea una nueva ventana con una tabla con todos los documentos
				Bibliografia ventanaBibliografia = new Bibliografia();
				ventanaBibliografia.Mostrar();				
			}
		});
		//Acción del botón Prestamo
		prestamo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI_Prestamo ventanaPrestamo = new GUI_Prestamo();
				ventanaPrestamo.Mostrar();
				
			}
		});
		//Accion al cerrar la ventana
		generalBibliotecario.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				generalBibliotecario.dispose();
				Acceso.ventana.setVisible(true);
			}
		});
		//Añadir un nuevo Usuario
		nuevousuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				datosRegistrarse<Object> nuevo = new datosRegistrarse<Object>();
				nuevo.mostrarVentana();				
			}
		});
		//boton salir
		salir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generalBibliotecario.dispose();
				Acceso.ventana.setVisible(true);
			}
		});
	}
}
