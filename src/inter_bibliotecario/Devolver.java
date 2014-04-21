package inter_bibliotecario;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

import tablas.*;

public class Devolver {
	
	public Devolver() {
		colocar();
		acciones();
		System.out.println("Ventana Devolver abierta.");
	}

	private JFrame ventana= new JFrame("Bibliotecario - Devoluci\u00F3n");
	private JTextField txtusuario = new JTextField();
	private JTextField txtcodigo = new JTextField();
	
	private JPanel panel1 = new JPanel();
		private JButton volver = new JButton("<- Volver");
		private JLabel lbltitulo = new JLabel("Introduce los datos del documento:");
	
	private JPanel panel2 = new JPanel();
		private JLabel lblusuario = new JLabel("Usuario al que ha sido prestado:");
		private JLabel lblcodigo = new JLabel("C\u00F3digo del documento:");
		private JLabel lblentregado = new JLabel("\u00BFEntregado dentro de la fecha?");
		private ButtonGroup grupo = new ButtonGroup();
		private JRadioButton si = new JRadioButton("Si");		
		private JRadioButton no = new JRadioButton("No");
		private JButton prodecer = new JButton("Proceder");
		
	/** Metodo que hace visible la ventana */
	public void Mostrar(){
		ventana.setVisible(true);
	}
	
	private void proceder(){
		if (txtcodigo.getText().equals("") || txtcodigo.getText().equals("") 
			  /*	|| (!(si.isSelected()) && !(no.isSelected()))*/){						
			ErrorDialog error = new ErrorDialog( ventana, "¡Rellena todos los campos!");
			error.pack();
			error.setVisible(true);
		}else{
			
			devolverDocumento();			
			//al FINAL limpiar todas las casillas
			txtcodigo.setText("");
			txtusuario.setText("");
			grupo.clearSelection();
		}
	}
	
	private void colocar() {
		
		/* * * * * * * * * * * * Ventana * * * * * * * * * */
		ventana.setBounds(435, 330, 441, 301);
		ventana.getContentPane().setLayout(null);
		ventana.setResizable(false);
		
		/* * * * * * * * * * * Panel 1 * * * * * * * * * * * */
		panel1.setBounds(10, 11, 414, 44);
		panel1.setLayout(null);
		panel1.setBorder(UIManager.getBorder("PasswordField.border"));
			//Boton devolver
			volver.setBounds(10, 11, 89, 23);
			panel1.add(volver);
		ventana.getContentPane().add(panel1);
			
		/* * * * * * * * * * * Panel 2 * * * * * * * * * * * */
		panel2.setBounds(10, 66, 414, 198);
		panel2.setBorder(UIManager.getBorder("PasswordField.border"));
		panel2.setLayout(null);
		
			//titulo
			lbltitulo.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lbltitulo.setBounds(10, 9, 366, 14);
			panel2.add(lbltitulo);
		
			//Para insertar el usuario
			lblusuario.setBounds(10, 51, 182, 14);
			panel2.add(lblusuario);
			txtusuario.setBounds(218, 48, 169, 17);
			panel2.add(txtusuario);
			txtusuario.setColumns(10);
					
			//Para insertar el Codigo
			lblcodigo.setBounds(10, 85, 169, 14);
			panel2.add(lblcodigo);
			txtcodigo.setBounds(218, 88, 169, 17);
			panel2.add(txtcodigo);
			txtcodigo.setColumns(10);
				
			//Indicar si esta retrasada o no la entrega
			lblentregado.setBounds(10, 121, 194, 14);
			panel2.add(lblentregado);	
			si.setBounds(245, 117, 62, 23);
			grupo.add(si);
			panel2.add(si);
			
			no.setBounds(309, 117, 62, 23);
			grupo.add(no);
			panel2.add(no);
			
			//boton proceder
			prodecer.setBounds(163, 164, 89, 23);
			panel2.add(prodecer);
		ventana.getContentPane().add(panel2);
	}
		
	private void acciones(){		
		
		//Accion del boton volver
		volver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent volver) {
				/* cierra la ventana y la quita de memoria, por lo que al
				 * abrirla de nuevo, volvera a ejecutarse toda la ventana*/
				ventana.dispose();				
			}			
		});
		
		//Accion del boton proceder
		prodecer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent proceder) {				
				proceder();
			}
		});

	}
	
	private void devolverDocumento(){
		//Crea un nuevo objeto Usuario para actualizar su número de prestamos			
		Usuario prestado = comprobarUsuario(GUI_Principal.con, "biblioteca", txtusuario.getText())[0];
		//Crea un objeto Documento con el codigo introducido
		Documento docPrestado = comprobarCodigo(GUI_Principal.con, "biblioteca", txtcodigo.getText())[0];
		
		/*Primero realiza comprobaciones sobre si Existen los usuarios, documentos o si el usuario ha llegado al límite de
		 * documentos prestados o si el documento que se quiere coger ya está prestado */		
		if(prestado == null){
			//Comprueba que el usuario se halla en la Tabla de Usuarios
			ErrorDialog noUsuDocu = new ErrorDialog(ventana, "Usuario con DNI = " + txtusuario.getText() + " no encontrado.");
			noUsuDocu.pack();
			noUsuDocu.setVisible(true);
		}		
		else if(docPrestado == null){
			//Comprueba que el Documento esta en la Tabla Documentos
			ErrorDialog noUsuDocu = new ErrorDialog(ventana, "Documento con Codigo = " + txtcodigo.getText() + " no encontrado.");
			noUsuDocu.pack();
			noUsuDocu.setVisible(true);
		}		
		else if(docPrestado.getPrestado() == false){
			//El documento no puede estar sin prestar
			ErrorDialog noPrest = new ErrorDialog(ventana, "El documento no se prestó a ningun usuario.");
			noPrest.pack();
			noPrest.setVisible(true);
		}
		
		//Si lo demas es correcto
		else{			
			try{
				int numPrestamos = prestado.getNumprestamos();
				System.out.println("Prestamos!!!! ---> " + numPrestamos);
				//suma uno a los préstamos actuales del usuario
				prestado.modificarPrestados(GUI_Principal.con, "biblioteca", numPrestamos-1, txtusuario.getText());				
				//Pone el documento como disponible (no prestado)
				docPrestado.modificarPrestado(GUI_Principal.con, "biblioteca", Integer.parseInt(txtcodigo.getText()), false);
				
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