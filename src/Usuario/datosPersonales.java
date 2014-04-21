package Usuario;

import inicio_Sesion.Acceso;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

import java.sql.Connection;

import tablas.ModificarUsuario;
import tablas.Usuario;
import tablas.Prestamo;
import Usuario.ventanaUsuarios;


public class datosPersonales {
	
	private JFrame ventanaDatosPersonales;
	
	private JPanel panel_titulo = new JPanel();
	private JPanel panel1 = new JPanel();
	private JPanel panel_tabla = new JPanel();
	private JPanel panel_boton = new JPanel();
	private JPanel panelTipos = new JPanel();
	
	private JLabel lblUsuarioDatos = new JLabel("DATOS PERSONALES");
	private JLabel lblDni = new JLabel("DNI:");
	private JLabel lblNombre = new JLabel("Nombre:");
	private JLabel lblFechaNacimiento = new JLabel("Fecha Nacimiento:");
	private JLabel lblNTelefono = new JLabel("Nº Telefono:");
	private JLabel lblNMovil = new JLabel("Nº Movil:");
	private JLabel lblIdentificacinDeSus = new JLabel("Datos de sus Pr\u00E9stamos:");
	private JLabel lblApellidos = new JLabel("Apellidos:");
	private JLabel lblConectado = new JLabel("");
	private JLabel lblExpediente = new JLabel("Expediente:");
	private JLabel lblDepartamento = new JLabel("Departamento:");
	private JLabel lblAsignatura = new JLabel("Asignatura:");
	private JLabel lblDireccion = new JLabel("Direcci\u00F3n:");
	
	private JLabel txtDni = new JLabel();
	private JTextField txtNombre = new JTextField(20);
	private JTextField txtNacimiento = new JTextField(20);
	private JTextField txtTelefono = new JTextField(20);
	private JTextField txtMovil = new JTextField(20);
	private JTextField txtApellidos = new JTextField();
	private JTextField txtExpediente = new JTextField();
	private JTextField txtDepartamento = new JTextField();
	private JTextField txtAsignatura = new JTextField();
	private JTextField txtDireccion = new JTextField();
	
	private JButton btnSalir = new JButton("Salir");
	private JButton btnModificar = new JButton("Modificar");
	
	private JRadioButton rdbtnEstudiante = new JRadioButton("Estudiante:");
	private JRadioButton rdbtnProfesor = new JRadioButton("Profesor:");
	private JRadioButton rdbtnGeneral = new JRadioButton("General:");
	private JCheckBox familiarTrabajando = new JCheckBox("\u00BFFamiliar Trabajando?");
	
	private JScrollPane barra_lateral;
	private JTable tabla;
	private String[] titulos = {"Codigo Documento", "Fecha Préstamo", "Fecha Devolución"};
	private String[][] datos;
	private final int alto = 48;
	private final int ancho = 480;
	
	static Usuario us = new Usuario (null,null,null,0,0,null,null,null);
	static Connection con;
	
	private Prestamo[] lista = new Prestamo[100];
	private int indice = 0;
	

	public datosPersonales() {
		us = ventanaUsuarios.usu;
		con = ventanaUsuarios.con;
		datos = new String [3] [3];
		tabla = new JTable(datos, titulos);
		barra_lateral = new JScrollPane(tabla);
		barra_lateral.setLocation(3, 5);
		barra_lateral.setSize(521, 178);
		
		lblConectado.setText("Conectado como:  "+us.getNombre()+" "+us.getApellido());
		colocar();
		System.out.println("Datos Personales Abierto");
		mostrarDatos();
		especificoUsuario();
		prestamosPorCodigo(con, "biblioteca",txtDni.getText());
		colocaPrestamos(lista);
	}
	
	public void mostrar(){
		ventanaDatosPersonales.setVisible(true);
	}

	private void colocar() {
		ventanaDatosPersonales = new JFrame();
		ventanaDatosPersonales.setBounds(350, 200, 600, 542);
		ventanaDatosPersonales.getContentPane().setLayout(null);
		panel_titulo.setBounds(185, 4, 226, 35);
		
		ventanaDatosPersonales.getContentPane().add(panel_titulo);
		panel1.setBounds(25, 74, 254, 166);
		ventanaDatosPersonales.getContentPane().add(panel1);
		panel_tabla.setBounds(25, 356, 527, 91);
		ventanaDatosPersonales.getContentPane().add(panel_tabla);
		panel_boton.setBounds(479, 459, 73, 33);
		ventanaDatosPersonales.getContentPane().add(panel_boton);
		panel_titulo.setLayout(null);
		
		
		lblUsuarioDatos.setBounds(5, 5, 220, 25);
		lblUsuarioDatos.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		panel_titulo.add(lblUsuarioDatos);
		panel1.setLayout(null);
		
		
		lblDni.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblDni.setBounds(0, 0, 133, 28);
		panel1.add(lblDni);
		txtDni.setBounds(127, 2, 125, 26);
		panel1.add(txtDni);
		
		
		lblNombre.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblNombre.setBounds(0, 26, 133, 28);
		panel1.add(lblNombre);
		txtNombre.setBounds(127, 28, 125, 26);
		panel1.add(txtNombre);
		
		lblFechaNacimiento.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblFechaNacimiento.setBounds(0, 80, 133, 28);
		panel1.add(lblFechaNacimiento);
		txtNacimiento.setBounds(127, 82, 125, 26);
		panel1.add(txtNacimiento);
		
		
		lblNTelefono.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblNTelefono.setBounds(0, 109, 133, 28);
		panel1.add(lblNTelefono);
		txtTelefono.setBounds(127, 111, 125, 26);
		panel1.add(txtTelefono);
		
		
		lblNMovil.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblNMovil.setBounds(0, 138, 133, 28);
		panel1.add(lblNMovil);
		txtMovil.setBounds(127, 140, 125, 26);
		panel1.add(txtMovil);
		
		lblApellidos.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblApellidos.setBounds(0, 58, 133, 16);
		
		panel1.add(txtApellidos);
		txtApellidos.setBounds(127, 54, 125, 26);
		txtApellidos.setColumns(10);
		
		panel1.add(lblApellidos);
		
		panel_tabla.add(barra_lateral);
		tabla.setPreferredScrollableViewportSize(new Dimension(ancho-2, alto));
		
		panel_boton.setLayout(null);
		btnSalir.setBounds(10, 5, 53, 26);
		btnSalir.setFocusPainted(false);
		panel_boton.add(btnSalir);
		
		lblIdentificacinDeSus.setFont(new Font("SansSerif", Font.ITALIC, 16));
		lblIdentificacinDeSus.setBounds(25, 328, 203, 16);
		ventanaDatosPersonales.getContentPane().add(lblIdentificacinDeSus);
		
		btnModificar.setFocusPainted(false);
		btnModificar.setBounds(162, 270, 101, 35);
		ventanaDatosPersonales.getContentPane().add(btnModificar);
		
		lblConectado.setFont(new Font("SansSerif", Font.ITALIC, 18));
		lblConectado.setBounds(138, 39, 414, 23);
		ventanaDatosPersonales.getContentPane().add(lblConectado);
		
		panelTipos.setBounds(291, 74, 261, 231);
		ventanaDatosPersonales.getContentPane().add(panelTipos);
		panelTipos.setLayout(null);
		
		rdbtnEstudiante.setFocusPainted(false);
		rdbtnEstudiante.setBounds(0, 0, 126, 23);
		panelTipos.add(rdbtnEstudiante);

		
		rdbtnProfesor.setFocusPainted(false);
		rdbtnProfesor.setBounds(0, 54, 126, 23);
		panelTipos.add(rdbtnProfesor);

		
		rdbtnGeneral.setFocusPainted(false);
		rdbtnGeneral.setBounds(0, 133, 126, 23);
		panelTipos.add(rdbtnGeneral);
		
		familiarTrabajando.setFocusPainted(false);
		familiarTrabajando.setBounds(40, 154, 188, 29);
		panelTipos.add(familiarTrabajando);
		
		txtExpediente.setBounds(134, 21, 125, 26);
		txtExpediente.setColumns(10);
		lblExpediente.setBounds(40, 26, 86, 16);
		panelTipos.add(lblExpediente);
		
		
		lblDepartamento.setBounds(40, 79, 86, 16);
		panelTipos.add(lblDepartamento);
		txtDepartamento.setBounds(134, 74, 125, 26);
		panelTipos.add(txtDepartamento);
		txtDepartamento.setColumns(10);
		
		
		lblAsignatura.setBounds(40, 106, 86, 16);
		panelTipos.add(lblAsignatura);
		txtAsignatura.setBounds(134, 101, 125, 26);
		panelTipos.add(txtAsignatura);
		txtAsignatura.setColumns(10);
		
		
		lblDireccion.setBounds(40, 188, 75, 16);
		panelTipos.add(lblDireccion);	
		txtDireccion.setBounds(134, 183, 125, 26);
		panelTipos.add(txtDireccion);
		txtDireccion.setColumns(10);
		
		panelTipos.add(txtExpediente);
	    
	    btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventanaDatosPersonales.dispose();
			}			
		});
	    
	    btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				modificarDatos();
				ErrorDialog modif = new ErrorDialog(ventanaDatosPersonales, "¡Datos Modificados! Conectese de Nuevo");
				modif.setTitle("¡Atencion!");
				modif.pack();
				modif.setVisible(true);
				ventanaDatosPersonales.setVisible(false);
				ventanaUsuarios.ventana.setVisible(false);
				Acceso.ventana.setVisible(true);
			}			
		});
	}
	
	public void modificarDatos(){
		ModificarUsuario mod = new ModificarUsuario();
		
		/*
		 * Modificar estudiante
		 */
		if(!ventanaUsuarios.usu.getExpediente().equals("null")){		
			mod.modificarNombre(con, "biblioteca", txtDni.getText(),txtNombre.getText());
			mod.modificarApellido(con, "biblioteca", txtDni.getText(),txtApellidos.getText());
			mod.modificarTelefono(con, "biblioteca",Integer.parseInt(txtTelefono.getText()), txtDni.getText());
			mod.modificarMovil(con, "biblioteca",Integer.parseInt(txtMovil.getText()), txtDni.getText());
			mod.modificarFechaNacimiento(con, "biblioteca", txtDni.getText(),txtNacimiento.getText());
			mod.modificarExpediente(con, "biblioteca", txtDni.getText(), txtExpediente.getText());
		}
		/*
		 *Modificar profesor
		 */
		if(!ventanaUsuarios.usu.getNomdepart().equals("null") && !ventanaUsuarios.usu.getAsignatura().equals("null")){	
			mod.modificarNombre(con, "biblioteca", txtDni.getText(),txtNombre.getText());
			mod.modificarApellido(con, "biblioteca", txtDni.getText(),txtApellidos.getText());
			mod.modificarTelefono(con, "biblioteca",Integer.parseInt(txtTelefono.getText()), txtDni.getText());
			mod.modificarMovil(con, "biblioteca",Integer.parseInt(txtMovil.getText()), txtDni.getText());
			mod.modificarFechaNacimiento(con, "biblioteca", txtDni.getText(),txtNacimiento.getText());
			mod.modificarNomDepart(con, "biblioteca", txtDni.getText(), txtDepartamento.getText());
			mod.modificarAsignatura(con, "biblioteca", txtDni.getText(), txtAsignatura.getText());
		}
		/*
		 * Modificar general
		 */
		if(!ventanaUsuarios.usu.getDireccion().equals("null")){	
			mod.modificarDireccion(con, "biblioteca", txtDni.getText(), txtDireccion.getText());
			if(familiarTrabajando.isSelected()){
				mod.modificarFamiliar(con, "biblioteca", txtDni.getText(), true);
			}
			else{
				mod.modificarFamiliar(con, "biblioteca", txtDni.getText(), false);
			}
		 }
	}
	
	public void mostrarDatos(){
		txtDni.setText(us.getDNI());
		txtNombre.setText(us.getNombre());
		txtApellidos.setText(us.getApellido());
		txtNacimiento.setText(us.getFechanaciemto());
		txtTelefono.setText(String.valueOf(us.getTelefono()));
		txtMovil.setText(String.valueOf(us.getMovil()));
	}
	
	private Prestamo[] prestamosPorCodigo(Connection con, String BDNombre, String dni){
		Prestamo a = null;
		//Consulta que selecciona los documentos ordenador por lo que reciba el String "ordenadosPor"
		String consulta = "select COD_DOCUMENTO, COD_USUARIO, FECHA_PRESTAMO, FECHA_DEVOLUCION from " +
		BDNombre + ".prestamos " + " WHERE COD_USUARIO='"+dni+"' order by FECHA_DEVOLUCION asc;";
		Statement stmt = null;
		
		try{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(consulta);
			
			//"numero" es la posicion que va a ocupar cada objeto en el array de objetos
			int numero = 0;
			//bucle que sigue mientras exista una nueva fila en el ResultSet
			while(rs.next()){
				//Creacion de un nuevo Objeto Documento para cada fila del ResultSet
				a = new Prestamo(
						Integer.parseInt(rs.getString(1)),//(int) COD_DOCUMENTO
						rs.getString(2),//(String) COD_USUARIO
						rs.getString(3),//(String) FECHA_PRESTAMO
						rs.getString(4)//(String) FECHA_DEVOLUCION
				);
			//vamos llenando el array con objetos de la clase
			lista [numero] = a;
			//Aumenta número y con el la posición de cada objeto en el array
			numero++;
			}			
		}catch (SQLException e){
			e.printStackTrace();			
		}		
		//Devuelve un array "lista" que es mostrado por consola
		return lista;
	}
	
	private void colocaPrestamos(Prestamo[] array){
			//se restablece el indice a 0 (indicador de cual es la última fila de la tabla)
			indice = 0;
			//pone los datos de la tabla en blanco
			limpiarTabla();
			/*Bucle que va llenando las filas/columnas de la tabla con los datos de los documentos.
			 * va desde 0 hasta el final del array o hasta que el siguiente elemento es nulo (no hay documento) */			
			for(int i = 0; i < array.length && array[i] != null; i++){
				//para cada fila
				for(int j = 0; j < 5; j++){
					//datos para cada columna
					switch (j){
						case 0: datos [i] [j] = String.valueOf(array[i].getCodDocumento());break;//columna 0 = COD_DOCUMENTO
						case 1: datos [i] [j] = array[i].getFechaPrestamo();break;//columna 1 = FECHA_PRESTAMO
						case 2: datos [i] [j] = array[i].getFechaFin();break;//columna 2 = FECHA_DEVOLUCION
						//System.out.println("MMAJSJSJS  - > " +a);
								/*if (a.eq){datos [i] [j] = "Si";}
								if (a == 0){datos [i] [j] = "No";}	*/					
								//break;
						default: break;
					}//Fin del switch
					
				}//Fin del 2º for (fin de cada fila)	
				
				indice++;//Aumenta en una fila		
				
			}//Fin 1º for (fin de todas las filas de la tabla)
		ventanaDatosPersonales.paintAll(ventanaDatosPersonales.getGraphics());//refresca la ventana		
		System.out.println("La ultima fila es la "+ indice); //muestra cual la posicion de la útimal fila
	}
	
	public void especificoUsuario(){
		if(!ventanaUsuarios.usu.getExpediente().equals("null")){
			rdbtnGeneral.setEnabled(false);
			familiarTrabajando.setEnabled(false);
				lblDireccion.setEnabled(false);
				txtDireccion.setEnabled(false);
			rdbtnProfesor.setEnabled(false);
				lblDepartamento.setEnabled(false);
				txtDepartamento.setEnabled(false);
				lblAsignatura.setEnabled(false);
				txtAsignatura.setEnabled(false);
			rdbtnEstudiante.setSelected(true);
			txtExpediente.setText(us.getExpediente());
			
		}
		if(!ventanaUsuarios.usu.getNomdepart().equals("null") && !ventanaUsuarios.usu.getAsignatura().equals("null")){
			rdbtnGeneral.setEnabled(false);
				familiarTrabajando.setEnabled(false);
				lblDireccion.setEnabled(false);
				txtDireccion.setEnabled(false);
			rdbtnEstudiante.setEnabled(false);
				lblExpediente.setEnabled(false);
				txtExpediente.setEnabled(false);
			rdbtnProfesor.setSelected(true);
			txtDepartamento.setText(us.getNomdepart());
			txtAsignatura.setText(us.getAsignatura());
		}
		if(!ventanaUsuarios.usu.getDireccion().equals("null")){
			rdbtnEstudiante.setEnabled(false);
				rdbtnEstudiante.setSelected(false);
				lblExpediente.setEnabled(false);
				txtExpediente.setEnabled(false);
			rdbtnProfesor.setEnabled(false);
				rdbtnProfesor.setSelected(false);
				lblDepartamento.setEnabled(false);
				txtDepartamento.setEnabled(false);
				lblAsignatura.setEnabled(false);
				txtAsignatura.setEnabled(false);
			rdbtnGeneral.setSelected(true);
				familiarTrabajando.setEnabled(true);
				if(us.getFamiliar()==true){
					familiarTrabajando.setSelected(true);
				}
				else{
					familiarTrabajando.setSelected(false);
				}
				lblDireccion.setEnabled(true);
				txtDireccion.setEnabled(true);
				txtDireccion.setText(us.getDireccion());
		}
	}
	
	private void limpiarTabla(){
		
		for(int i = 0; i < 3; i++){
			//para cada fila
			for(int j = 0; j < 3; j++){
				//datos para cada columna
				switch (j){
					case 0:
					case 1:
					case 2:
					case 3:
					case 4: datos [i] [j] = ""; break;// columnas 0, 1, 2, 3 y 4 se ponen en blanco
					default: break;
				}//Fin del switch
			}//Fin 2º for
		}//fin 1º for
	}
}
