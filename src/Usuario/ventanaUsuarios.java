package Usuario;


import inicio_Sesion.Acceso;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;

import tablas.Consulta;
import tablas.Usuario;
import tablas.Documento;


public class ventanaUsuarios {

	static JFrame ventana;
	private JLabel lblConectadoComo = new JLabel("Conectado Como:");
	
	private Documento[] listaDoc = new Documento[100];
	
	static Usuario usu = new Usuario (null,null,null,0,0,null,null,null);
	public static Connection con;

	private String fecha = null;
	
	public ventanaUsuarios(Usuario us, Connection con) {
		ventanaUsuarios.usu = us;
		ventanaUsuarios.con = con;
		lblConectadoComo.setText("Conectado como: "+us.getNombre() + " " + us.getApellido());
		colocar();
		eliminarConsultasAyer();
	}

	public void Mostrar(){
		ventana.setVisible(true);
		Acceso.ventana.setVisible(false);
	}
	
	private void colocar(){	
		
		ventana = new JFrame();
		ventana.getContentPane().setForeground(Color.BLACK);
		ventana.setBounds(400, 350, 450, 252);
		ventana.setResizable(false);
		ventana.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setForeground(Color.BLACK);
		ventana.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JButton btnDatosPersonales = new JButton("Datos Personales");
		btnDatosPersonales.setFocusPainted(false);
		btnDatosPersonales.setToolTipText("Informaci\u00F3n sobre tus datos y tus pr\u00E9stamos");
		btnDatosPersonales.setBounds(43, 56, 149, 39);
		panel.add(btnDatosPersonales);
		
		JButton btnInformacion = new JButton("Bibliografía");
		btnInformacion.setFocusPainted(false);
		btnInformacion.setToolTipText("Libros disponibles en la biblioteca");
		btnInformacion.setBounds(270, 56, 113, 39);
		panel.add(btnInformacion);
		
		JButton btnConsulta = new JButton("Consulta");
		btnConsulta.setFocusPainted(false);
		btnConsulta.setToolTipText("Leer libros en la biblioteca");
		btnConsulta.setBounds(73, 117, 89, 39);
		panel.add(btnConsulta);
		
		JButton btnPrestamo = new JButton("Pr\u00E9stamo");
		btnPrestamo.setFocusPainted(false);
		btnPrestamo.setToolTipText("Realizar un pr\u00E9stamo");
		btnPrestamo.setBounds(270, 117, 113, 39);
		panel.add(btnPrestamo);
		
		JButton btnSalir = new JButton("Desconectarme");
		btnSalir.setMinimumSize(new Dimension(53, 28));
		btnSalir.setMaximumSize(new Dimension(53, 28));
		btnSalir.setBounds(292, 179, 131, 23);
		panel.add(btnSalir);
		
		
		lblConectadoComo.setBounds(63, 0, 320, 45);
		lblConectadoComo.setFont(new Font("MS Reference Sans Serif", Font.ITALIC, 18));
		panel.add(lblConectadoComo);
		
		btnDatosPersonales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				datosPersonales dT = new datosPersonales();
				dT.mostrar();
			}			
		});
		
		btnInformacion.addActionListener(new ActionListener() { 
	        public void actionPerformed(ActionEvent e) { 
	        	bibliografia inf = new bibliografia();
				inf.mostrar();
	        } 
	    });
		
		btnConsulta.addActionListener(new ActionListener() { 
	        public void actionPerformed(ActionEvent e) { 
	        	consulta con = new consulta();
				con.mostrar();
	        } 
	    });
		
		btnPrestamo.addActionListener(new ActionListener() { 
	        public void actionPerformed(ActionEvent e) { 
	        	prestamo pre = new prestamo(usu);
	        	pre.mostrar();
	        } 
	    });
		
		btnSalir.addActionListener(new ActionListener() { 
	        public void actionPerformed(ActionEvent e) { 
	        	ventana.dispose();
	        	usu.setNumprestamos(0);
	        	Acceso.ventana.setVisible(true);
	        } 
	    });
		//Accion al cerrar la ventana
		ventana.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				ventana.dispose();
				Acceso.ventana.setVisible(true);
			}
		});
	}
	
	/** Metodo que almacena la fecha actual o de préstamo*/
	private String getFechaActual(){
		//Calculo de la fecha actual
				Calendar calendario = Calendar.getInstance();
				SimpleDateFormat formatoFecha = new SimpleDateFormat("dd MMMM yyyy");
				return formatoFecha.format(calendario.getTime());		
	}
	
	public void eliminarConsultasAyer(){
		documentosPor(con,"biblioteca");
		diferenciaCodigo(listaDoc);
	}
	
	private void diferenciaCodigo(Documento [] arrayDoc){
		Consulta cons = new Consulta(0,null,null);
		Documento doc = new Documento ();
		for(int i = 0; i < arrayDoc.length && arrayDoc[i] != null; i++){
			int codigo = Integer.parseInt(String.valueOf(arrayDoc[i].getCodigo()));
			buscarFechaConsulta(con, "biblioteca", codigo);
			if(!fecha.equals(getFechaActual())){
				doc.modificarPrestado(con,"biblioteca",codigo,false);	
				cons.borrarConsulta(con, "biblioteca", codigo);
			}
		}
	}
	
	private Documento[] documentosPor(Connection con, String BDNombre){

		Documento a = null;
		//Consulta que selecciona los documentos ordenador por lo que reciba el String "ordenadosPor"
		String consulta = "select * from " +
		BDNombre + ".documentos " + "WHERE (TIPO = 'Periódico' or TIPO = 'Tésis') and PRESTADO = 1 order by CODIGO asc;";
		Statement stmt = null;
		
		try{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(consulta);			
			//"numero" es la posicion que va a ocupar cada objeto en el array de objetos
			int numero = 0;
			//bucle que sigue mientras exista una nueva fila en el ResultSet
			while(rs.next()){
				//Creacion de un nuevo Objeto Documento para cada fila del ResultSet
				
				a = new Documento(
						Integer.parseInt(rs.getString(1)),//(int) Codigo
						rs.getString(2),//(String) Titulo
						rs.getString(3),//(String) Autor
						rs.getString(4),//(String) Tipo
						rs.getBoolean(5) //(Boolean) Prestado true/false || 0/1
				);
			//vamos llenando el array con objetos de la clase
			listaDoc [numero] = a;
			//Aumenta número y con el la posición de cada objeto en el array
			numero++;
			}			
		}catch (SQLException e){
			e.printStackTrace();			
		}		
		//Devuelve un array "lista" que es mostrado por consola
		return listaDoc;
	}
	
	private String buscarFechaConsulta(Connection con, String BDNombre, int cod){
		String consulta = "select * from " +
		BDNombre + ".consultas " + "WHERE COD_DOCUMENTO = "+cod+";";
		Statement stmt = null;
		
		try{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(consulta);
			while(rs.next()){
				fecha = rs.getString(3);
			}			
		}catch (SQLException e){
			e.printStackTrace();	
		}
		return fecha;
	}
}
