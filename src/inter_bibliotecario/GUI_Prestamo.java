package inter_bibliotecario;

import java.util.Calendar;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.*;

import tablas.*;

public class GUI_Prestamo {
	
	public GUI_Prestamo() {
		colocar();
		acciones();
		System.out.println("Ventana Prestamo abierta.");
	}
	
	private JFrame ventana = new JFrame("Bibliotecario - Pr\u00E9stamo");	
	private JPanel panel1 = new JPanel();	
		private JButton volver = new JButton("<- Volver");
		private JLabel lblTitulo = new JLabel("Completa el siguiente formulario.");
		
	private JPanel panel2 = new JPanel();	
		private JLabel lblDNI = new JLabel("DNI del Usuario:");
		private JTextField txtDNI = new JTextField();
		
		private JLabel lblCodigo = new JLabel("C\u00F3digo del libro:");
		private JTextField txtCodigo = new JTextField();
		
		private JLabel lblFecha_actual = new JLabel("Fecha actual:");
		private JLabel txtFecha_Actual = new JLabel("06/06/2012");
		
		private JLabel lblFecha_Entrega = new JLabel("Fecha de entrega:");
		private JLabel txtFecha_Entrega = new JLabel("06/07/2012");
		
		private JButton proceder = new JButton("Proceder");
				

	public void Mostrar(){
		ventana.setVisible(true);
	}
	
	private void colocar() {
		
		ventana.setBounds(435, 330, 440, 332);
		ventana.getContentPane().setLayout(null);
		ventana.setResizable(false);
		
		panel1.setBorder(UIManager.getBorder("PasswordField.border"));
		panel1.setBounds(10, 11, 414, 43);		
		panel1.setLayout(null);
		ventana.getContentPane().add(panel1);
		
		lblTitulo.setBounds(137, 11, 267, 21);
		lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel1.add(lblTitulo);
		
		//Boton devolver
		volver.setBounds(10, 10, 89, 23);
		panel1.add(volver);

		panel2.setBorder(UIManager.getBorder("PasswordField.border"));
		panel2.setBounds(10, 65, 414, 229);		
		panel2.setLayout(null);
		ventana.getContentPane().add(panel2);
		
		lblDNI.setBounds(31, 21, 134, 17);
		panel2.add(lblDNI);
		
		txtDNI.setBounds(175, 19, 179, 19);
		panel2.add(txtDNI);
		txtDNI.setColumns(10);
		
		lblCodigo.setBounds(31, 49, 134, 17);
		panel2.add(lblCodigo);
		
		txtCodigo.setBounds(175, 47, 179, 19);
		panel2.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		lblFecha_actual.setBounds(31, 92, 134, 17);
		
		panel2.add(lblFecha_actual);
		
		txtFecha_Actual.setBounds(175, 92, 134, 17);		
		txtFecha_Actual.setText(getFechaActual());
		panel2.add(txtFecha_Actual);
		
		lblFecha_Entrega.setBounds(31, 120, 134, 17);
		panel2.add(lblFecha_Entrega);
		
		txtFecha_Entrega.setBounds(175, 121, 134, 17);
		
		txtFecha_Entrega.setText(getFechaFin());
		panel2.add(txtFecha_Entrega);
		
		proceder.setBounds(152, 185, 89, 23);
		panel2.add(proceder);
		
	}	
	
	
	private void acciones(){
		
		//Accion del boton volver
		volver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent volver) {
				/* cierra la ventana y la quita de memoria, por lo que al
				 * abrirla de nuevo, volvera a abirse todo lo de la ventana*/
				ventana.dispose();
			}			
		});
		
		//Accion del boton proceder
		proceder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent proceder) {
				
				if (txtDNI.getText().equals("") || txtCodigo.getText().equals("")){							
					ErrorDialog error = new ErrorDialog( ventana, "¡Rellena todos los campos!");
					error.pack();
					error.setVisible(true);
				}else{
					realizarPrestamo();					
					//al FINAL limpiar todas las casillas
					txtDNI.setText("");
					txtCodigo.setText("");
				}
			}
		});
	}
	
	private String getFechaActual(){
		//Calculo de la fecha actual
				Calendar calendario = Calendar.getInstance();
				SimpleDateFormat formatoFecha = new SimpleDateFormat("EEEEEEEE dd MMMM YYYY");
				return formatoFecha.format(calendario.getTime());		
	}
	
	private String getFechaFin(){
		//Calculo de la fecha dentro de 20 dias
				Calendar calendarioEntrega = Calendar.getInstance();
				calendarioEntrega.add(Calendar.DATE, 20);
				SimpleDateFormat formatoFecha = new SimpleDateFormat("EEEEEEEE dd MMMM YYYY");
				return formatoFecha.format(calendarioEntrega.getTime());
	}

	
	/** Método que realiza todas las acciones para realizar el prestamo */
	private void realizarPrestamo(){
		//Crea un nuevo objeto Usuario para actualizar su número de prestamos			
		Usuario prestado = comprobarUsuario(GUI_Principal.con, "biblioteca", txtDNI.getText())[0];
		//Crea un objeto Documento con el codigo introducido
		Documento docPrestado = comprobarCodigo(GUI_Principal.con, "biblioteca", txtCodigo.getText())[0];
		/*Primero realiza comprobaciones sobre si Existen los usuarios, documentos o si el usuario ha llegado al límite de
		 * documentos prestados o si el documento que se quiere coger ya está prestado */		
		if(prestado == null){
			//Comprueba que el usuario se halla en la Tabla de Usuarios
			ErrorDialog noUsuDocu = new ErrorDialog(ventana, "Usuario con DNI = " + txtDNI.getText() + " no encontrado.");
			noUsuDocu.pack();
			noUsuDocu.setVisible(true);
		}		
		else if(docPrestado == null){
			//Comprueba que el Documento esta en la Tabla Documentos
			ErrorDialog noUsuDocu = new ErrorDialog(ventana, "Documento con Codigo = " + txtCodigo.getText() + " no encontrado.");
			noUsuDocu.pack();
			noUsuDocu.setVisible(true);
		}		
		else if(prestado.getNumprestamos() >= 3){
			//el número de préstamos no debe ser mayor de 3
			ErrorDialog numPrest = new ErrorDialog(ventana, "El usuario con DNI: " + prestado.getDNI() + 
					" tiene demasiados préstamos (" + prestado.getNumprestamos() + ").");
			numPrest.pack();
			numPrest.setVisible(true);			
		}
		else if(docPrestado.getPrestado() == true){
			//El documento no puede estar prestado
			ErrorDialog isPrest = new ErrorDialog(ventana, "El documento ya está prestado.");
			isPrest.pack();
			isPrest.setVisible(true);
		}
		
		//Si lo demas es correcto, pasa a crear un nuevo prestamo
		else{			
			Prestamo prestamo = new Prestamo(Integer.parseInt(txtCodigo.getText()),//Int
						txtDNI.getText(), //String
						getFechaActual(), //String
						getFechaFin());	  //String
			try{
				
				prestado.setNumprestamos((prestado.getNumprestamos() + 1));
				System.out.println("Antes " + prestado.getNumprestamos());
				prestado.modificarPrestados(GUI_Principal.con, "biblioteca", (prestado.getNumprestamos()), prestado.getDNI());
				System.out.println("Despues " + prestado.getNumprestamos());
				
				/*
				int numPrestamos = prestado.getNumprestamos();
				System.out.println("Prestamos!!!! ---> " + numPrestamos);
				//suma uno a los préstamos actuales del usuario
				prestado.modificarPrestados(GUI_Principal.con, "biblioteca", numPrestamos+1, txtDNI.getText());	*/	
				//Pone el documento como prestado
				docPrestado.modificarPrestado(GUI_Principal.con, "biblioteca", Integer.parseInt(txtCodigo.getText()), true);
				//Añade el Nuevo préstamo a la Base de Datos
				prestamo.insertarPrestamo(GUI_Principal.con, "biblioteca", prestamo);	
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/** Devuelve un array de objetos Documentos con el codigo seleccionado */
	private Documento[] comprobarCodigo(Connection con, String BDNombre, String porCodigo){
		Documento[] busquedaDocumentos =  new Documento[1];
		Documento a = null;
		//Consulta
		String consulta = "select CODIGO, TITULO, AUTOR, TIPO, PRESTADO from " +
		BDNombre + ".documentos " + "where CODIGO = '"+ porCodigo +"';";
		Statement stmt = null;
		
		try{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(consulta);
			int numero = 0;
			
				while(rs.next()){					
					a = new Documento(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4),
							rs.getBoolean(5)
					);
				//vamos llenando el array con objetos de la clase
				busquedaDocumentos [numero] = a;
				numero++;
				}			
		}catch (SQLException e){
			e.printStackTrace();			
		}
		
		return busquedaDocumentos;
	}
	
	/** Devuelve un array de objetos Usuario con el DNI seleccionado */
	private Usuario[] comprobarUsuario(Connection con, String BDNombre, String DNI){
		Usuario[] busquedaUsuarios =  new Usuario[1];	
		Usuario a = null;
		//Consulta
		String consulta = "select DNI, NOMBRE, APELLIDO, NUMPRESTAMOS, TELEFONO, MOVIL, FECHA_NACIMIENTO, EXPEDIENDE, NOM_DEPART, ASIGNATURA, DIRECCION from " +
		BDNombre + ".usuarios " + "where DNI = '"+ DNI +"';";
		Statement stmt = null;
		
		try{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(consulta);
			int numero = 0;
			
				while(rs.next()){					
					a = new Usuario(
							rs.getString(1),
							rs.getString(2),
							rs.getString(3),
							rs.getInt(4),
							rs.getInt(5),
							rs.getString(6),
							rs.getBoolean(7),
							rs.getString(8),
							rs.getString(9)
					);
					a.setNumprestamos(rs.getInt(4));
				//vamos llenando el array con objetos de la clase
				busquedaUsuarios [numero] = a;
				numero++;
				}			
		}catch (SQLException e){
			e.printStackTrace();			
		}
		
		return busquedaUsuarios;
	}
}
