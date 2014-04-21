package inicio_Sesion;

import inter_bibliotecario.ErrorDialog;
import inter_bibliotecario.GUI_Principal;

import javax.swing.*;

import Usuario.ventanaUsuarios;

import tablas.Bibliotecario;
import tablas.Usuario;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Acceso {
	
	public static JFrame ventana = new JFrame();
	private JPanel panel = new JPanel();
	private JTextField txtDNI = new JTextField("");
	private JPasswordField txtpass = new JPasswordField("");
	private JButton btnIniciar = new JButton("Iniciar Sesi\u00F3n");
	private JRadioButton bibliotecario = new JRadioButton("Bibliotecario");
	private JRadioButton usuario = new JRadioButton("Usuario");
	private JButton registrarse = new JButton("Registrarse");	
	private JLabel lblNombre = new JLabel("DNI:");
	private JLabel lblPass = new JLabel("Contrase\u00F1a:");

	private ButtonGroup grupo = new ButtonGroup();
	
	static Connection con;
	
	private void colocar() {
		ventana.setBounds(450, 350, 365, 212);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setVisible(true);
		ventana.setResizable(false);

		ventana.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		btnIniciar.setBounds(196, 129, 108, 31);
		panel.add(btnIniciar);		
		
		bibliotecario.setBounds(168, 72, 109, 23);
		panel.add(bibliotecario);		
		
		registrarse.setFont(new Font("Tahoma", Font.PLAIN, 12));
		registrarse.setBounds(43, 134, 109, 23);
		panel.add(registrarse);		
		
		usuario.setBounds(95, 72, 109, 23);
		panel.add(usuario);
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 11));

		lblNombre.setBounds(90, 55, 47, 14);
		panel.add(lblNombre);
		
		txtDNI.setBounds(149, 52, 144, 20);
		panel.add(txtDNI);
		txtDNI.setColumns(10);
		lblPass.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		lblPass.setBounds(51, 101, 76, 14);
		panel.add(lblPass);
		txtpass.setFont(new Font("Tahoma", Font.PLAIN, 8));
		
		txtpass.setBounds(149, 98, 144, 20);
		panel.add(txtpass);
		
		grupo.add(usuario);
		grupo.add(bibliotecario);
		
		JLabel princi = new JLabel("Inicia sesi\u00F3n en la biblioteca:");
		princi.setHorizontalAlignment(SwingConstants.CENTER);
		princi.setFont(new Font("Tahoma", Font.PLAIN, 18));
		princi.setBounds(10, 11, 329, 19);
		panel.add(princi);
		
	}
	
	private void acciones(){
		registrarse.addActionListener(new ActionListener() { 
	        public void actionPerformed(ActionEvent e) { 	                      	        		   
				datosRegistrarse<Object> nuevo = new datosRegistrarse<Object>();
				nuevo.mostrarVentana();
	        } 
	    }); 
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//los campos de contraseña y usuario no deben estar vacios
				if(txtDNI.getText().equals("") || String.valueOf(txtpass.getPassword()).equals("")){			
					ErrorDialog err = new ErrorDialog(ventana, "Rellena tus datos");
					err.pack();
					err.setVisible(true);
				}else{
					inicioDeSesion();
				}
			}
		});
		txtpass.addKeyListener(new KeyAdapter() {
			//Método que se ejecuta al pulsar cualquier tecla
			public void keyPressed(KeyEvent ke) {
				//Si la tecla pulsada es ENTER
		          if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
		        	  //los campos de contraseña y usuario no deben estar vacios
		        	  if(txtDNI.getText().equals("") || String.valueOf(txtpass.getPassword()).equals("")){			
							ErrorDialog err = new ErrorDialog(ventana, "Rellena tus datos");
							err.pack();
							err.setVisible(true);
						}else{
							inicioDeSesion();
						}
		          }
		      }
		});
		
	}//Fin acciones
	
	private void inicioDeSesion(){
		//En el caso de que se seleccione el RadioButton de bibliotecario
		if(bibliotecario.isSelected()){
			sesionBibliotecario();
			}
		//En el caso de que se seleccione el RadioButton de usuario
		else if(usuario.isSelected()){
			sesionUsuario();
			}
		else{
			ErrorDialog err = new ErrorDialog(ventana, "Selecciona si eres usuario o bibliotecario");
			err.pack();
			err.setVisible(true);
		}		
	}
	//metodo que inicia sesion como Bibliotecario
	private void sesionBibliotecario(){
		Bibliotecario[] intentoBibliotecario = comprobarBibliotecario(con, "biblioteca");
		
		if(intentoBibliotecario[0] == null){
			//Comprueba que el bibliotecario se halla en la Tabla de Bibliotecario
			ErrorDialog noUsuDocu = new ErrorDialog(ventana, "Datos incorrectos para Bibliotecario con DNI = " + txtDNI.getText());
			noUsuDocu.pack();
			noUsuDocu.setVisible(true);
		}else{
			GUI_Principal interfazBibliotecario = new GUI_Principal(intentoBibliotecario[0], con);
			interfazBibliotecario.Mostrar();
			//Limpia los datos introducidos
			ventana.setVisible(false);
			txtDNI.setText("");
			txtpass.setText("");
			grupo.clearSelection();
		}		
	}
	
	//Metodo que inicia la sesion como usuario
	private void sesionUsuario(){
		Usuario[] intentoUsuario = comprobarUsuario(con, "biblioteca");
		
		if(intentoUsuario[0] == null){
			//Comprueba que el usuario se halla en la Tabla de Usuarios
			ErrorDialog noUsuDocu = new ErrorDialog(ventana, "Datos incorrectos para Usuario con DNI = " + txtDNI.getText());
			noUsuDocu.pack();
			noUsuDocu.setVisible(true);
		}else{
			ventanaUsuarios interfazUsuario= new ventanaUsuarios(intentoUsuario[0], con);
			interfazUsuario.Mostrar();
			//Limpia los datos introducidos
			ventana.setVisible(false);
			txtDNI.setText("");
			txtpass.setText("");
			grupo.clearSelection();
		}
	}
		
	/** Devuelve un array de objetos Usuario con el DNI seleccionado */
	private Usuario[] comprobarUsuario(Connection con, String BDNombre){
		Usuario[] busquedaUsuarios =  new Usuario[1];	
		Usuario a = null;
		//Consulta
		String consulta = "select DNI, NOMBRE, APELLIDO, NUMPRESTAMOS, TELEFONO, MOVIL, FECHA_NACIMIENTO, EXPEDIENDE, " +
				"FAMILIAR, " +
				"DIRECCION, CONTRASENIA, NOM_DEPART, ASIGNATURA from " +
		BDNombre + ".usuarios " + "where DNI = '"+ txtDNI.getText() +"' AND CONTRASENIA = '" + String.valueOf(txtpass.getPassword()) + "';";
		Statement stmt = null;
		
		try{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(consulta);
			int numero = 0;
			
				while(rs.next()){					
					a = new Usuario(
							rs.getString(1), //DNI
							rs.getString(2), //Nombre
							rs.getString(3), //Apellido
							rs.getInt(4),	 //NumPrest
							rs.getInt(5),	//telefono
							rs.getInt(6), 	//movil
							rs.getString(7), //fecha_nacimiento
							rs.getString(8), //Expediente
							rs.getBoolean(9), //familiar
							rs.getString(10), //direccion
							rs.getString(11), //Contraseña
							rs.getString(12), //Nombre de Departamento
							rs.getString(13) //Asignatura
					);
					a.setNumprestamos(rs.getInt(4));
				//vamos llenando el array con objetos de la clase
				System.out.println("PRESTAMOS: "+rs.getInt(4));
				busquedaUsuarios [numero] = a;
				numero++;
				}	
		}catch (SQLException e){
			e.printStackTrace();			
		}		
		return busquedaUsuarios;
	}
	
	/** Devuelve un array de objetos Usuario con el DNI seleccionado */
	private Bibliotecario[] comprobarBibliotecario(Connection con, String BDNombre){
		Bibliotecario[] busquedaBibliotecarios =  new Bibliotecario[1];	
		Bibliotecario a = null;
		//Consulta
		String consulta = "select DNI, NOMBRE, APELLIDO, CONTRASENIA from " +
		BDNombre + ".bibliotecarios " + "where DNI = '"+ txtDNI.getText() +"' AND CONTRASENIA = '" + String.valueOf(txtpass.getPassword()) +"';";
		Statement stmt = null;
		
		try{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(consulta);
			int numero = 0;
			
				while(rs.next()){					
					a = new Bibliotecario(
							rs.getString(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4)
					);
				//vamos llenando el array con objetos de la clase
					busquedaBibliotecarios [numero] = a;
				numero++;
				}			
		}catch (SQLException e){
			e.printStackTrace();			
		}
		
		return busquedaBibliotecarios;
	}
	
	
	public static void main (String [] Args){
		/* Para cambiar la apariencia de la ventana	 */
		try {
			UIManager.setLookAndFeel("com.nilo.plaf.nimrod.NimRODLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	
		/* Conexion con la Base de datos */
		try {
			
			con = DriverManager.getConnection("" +
					"jdbc:mysql://localhost:3306/biblioteca", "root", "");
			
			Acceso programa = new Acceso();		
			programa.colocar();
			programa.acciones();	
		}catch (SQLException e) {	
			
			ErrorDialog falloConexion = new ErrorDialog(ventana, "Error al intentar acceder a la base de datos.");
			falloConexion.pack();
			falloConexion.setVisible(true);			
			System.out.println("Error al acceder a la Base de Datos");
			
		
		
		
		}
	}
}
