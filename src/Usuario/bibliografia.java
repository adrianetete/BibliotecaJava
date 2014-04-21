package Usuario;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

import tablas.Documento;

public class bibliografia {
	
	Connection con = ventanaUsuarios.con;
	
	private final int alto = 270;
	private final int ancho = 764;
	
	private Documento[] lista = new Documento[100];	//recibe en un array de Objetos, todos los Documentos de la base de datos
	private Documento[] busqueda = null; //recibe solo un Objeto Documento con el codigo establecido

	private JFrame ventanaInformacion;	
	private JPanel panel_titulo = new JPanel();
	private JPanel panel_tabla = new JPanel();
	private JLabel lblTitulo = new JLabel("BIBLIOGRAFÍA");
	private JLabel lblOrdenarPor = new JLabel("Ordenar por:");
	private JLabel lblBuscar = new JLabel("Introduce un C\u00F3digo para Buscar:");
	private JLabel lblConectado = new JLabel("");
	
	private JButton btnSalir = new JButton("Salir");
	
	private JScrollPane barra_lateral;
	private JTable tabla;
	private String[] titulos = {"Codigo", "Título", "Autor", "Tipo","Prestado"};
	private String[][] datos;
	
	private String[] camposOrdenar={"-------------", "Código", "Título", "Autor", "Tipo","Disponibilidad"};
	private JComboBox<Object>elegirOrdenacion = new JComboBox<Object>(camposOrdenar);
	private JTextField txtBuscar;

	public bibliografia() {
		datos = new String [30] [10];
		tabla = new JTable(datos, titulos);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		barra_lateral = new JScrollPane(tabla);
		colocar();
		lblConectado.setText("Conectado como:  "+ventanaUsuarios.usu.getNombre()+" "+ventanaUsuarios.usu.getApellido());
		documentosPor(con, "biblioteca", "CODIGO");
		colocaDocumentos(lista);
	}
	
	public void mostrar(){
		ventanaInformacion.setVisible(true);		
	}


	private void colocar() {
		ventanaInformacion = new JFrame();
		ventanaInformacion.setVisible(true);
		ventanaInformacion.setBounds(150, 200, 850, 476);
		ventanaInformacion.getContentPane().setLayout(null);
		ventanaInformacion.setResizable(false);
		panel_titulo.setBounds(85, 8, 680, 35);
		
		ventanaInformacion.getContentPane().add(panel_titulo);
		ventanaInformacion.getContentPane().add(panel_tabla);
		panel_titulo.setLayout(null);
		lblTitulo.setBounds(10, 6, 250, 25);
		
		panel_titulo.add(lblTitulo);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20)); 
		
		lblConectado.setFont(new Font("Tahoma", Font.ITALIC, 18));
		lblConectado.setBounds(230, 4, 361, 31);
		panel_titulo.add(lblConectado);
		
		panel_tabla.add(barra_lateral);
		tabla.setPreferredScrollableViewportSize(new Dimension(ancho-2, alto));	
		panel_tabla.setBounds(23, 79, 791, 308);	
		btnSalir.setBounds(750, 398, 64, 22);
		ventanaInformacion.getContentPane().add(btnSalir);
		btnSalir.setFocusPainted(false);
		
		JPanel panel = new JPanel();
		panel.setBounds(23, 55, 791, 24);
		ventanaInformacion.getContentPane().add(panel);
		panel.setLayout(null);
		
		lblOrdenarPor.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblOrdenarPor.setBounds(8, 5, 88, 14);
		panel.add(lblOrdenarPor);
		
		elegirOrdenacion.setBounds(101, 2, 101, 20);
		panel.add(elegirOrdenacion);
		
		lblBuscar.setBounds(291, 4, 204, 16);
		panel.add(lblBuscar);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(532, -1, 122, 26);
		panel.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		txtBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				buscarPor(con, "biblioteca", txtBuscar.getText());
				colocaDocumentos(busqueda);				
			}
		});
		
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventanaInformacion.dispose();				
			}			
		});
		
		elegirOrdenacion.setSelectedIndex(0);
		elegirOrdenacion.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				//Si se selecciona la primera opcion
				if (elegirOrdenacion.getSelectedIndex() == 1){
					//Ordenar por CÓDIGO
					documentosPor(con, "biblioteca", "CODIGO");
					colocaDocumentos(lista);				
				}
				//Si se selecciona la segunda opcion
				if (elegirOrdenacion.getSelectedIndex() == 2){
					//Ordenar por TITULO
					documentosPor(con, "biblioteca", "TITULO");
					colocaDocumentos(lista);				
				}
				//Si se selecciona la tercera opcion
				if (elegirOrdenacion.getSelectedIndex() == 3){
					//Ordenar por AUTOR
					documentosPor(con, "biblioteca", "AUTOR");
					colocaDocumentos(lista);;					
				}
				//Si se selecciona la cuarta opcion
				if (elegirOrdenacion.getSelectedIndex() == 4){
					//Ordenar por TIPO
					documentosPor(con, "biblioteca", "TIPO");
					colocaDocumentos(lista);				
				}
				if (elegirOrdenacion.getSelectedIndex() == 5){
					//Ordenar por TIPO
					System.out.println("Documentos ordenados por TIPO:");
					documentosPor(con, "biblioteca", "PRESTADO");
					colocaDocumentos(lista);				
				}
			}
		});
	}
		
		private void colocaDocumentos(Documento[] array){
			
			if (array [0] == null){			
				//en el caso de que el array no haya recibido ningun documento (primero elemento == null)
				limpiarTabla();//pone los datos de la tabla en blanco
				//abre un JDialog indicando que no hay documentos para esa búsqueda
				ErrorDialog error = new ErrorDialog(ventanaInformacion, "No se ha encontrado ningun documento con codigo " + txtBuscar.getText());
				error.pack();
				//muestra el JDialog
				error.setVisible(true);
			}
			else{
				//pone los datos de la tabla en blanco
				limpiarTabla();
				
				/*Bucle que va llenando las filas/columnas de la tabla con los datos de los documentos.
				 * va desde 0 hasta el final del array o hasta que el siguiente elemento es nulo (no hay documento) */			
				for(int i = 0; i < array.length && array[i] != null; i++){
					//para cada fila
					for(int j = 0; j < 5; j++){
						//datos para cada columna
						switch (j){
							case 0: datos [i] [j] = String.valueOf(array[i].getCodigo());break;//columna 0 = Codigo
							case 1: datos [i] [j] = array[i].getTitulo();break;//columna 1 = Titulo
							case 2: datos [i] [j] = array[i].getAutor();break;//columna 2 = Autor
							case 3: datos [i] [j] = array[i].getTipo();break;//columna 3 = Tipo
							case 4: String a = String.valueOf(array[i].getPrestado());//columna 4 = prestado Si/No
								if (a.equals("true")){datos [i] [j] = "Si";}//si recibe true, pone Si en la tabla
								if (a.equals("false")){datos [i] [j] = "No";}//si recibe no, pone No en la tabla		
								break;
							default: break;
						}
						
					}
										
				}
			}	
			ventanaInformacion.paintAll(ventanaInformacion.getGraphics());//refresca la ventana		
		}
		
		private Documento[] documentosPor(Connection con, String BDNombre, String ordenadosPor){

			Documento a = null;
			//Consulta que selecciona los documentos ordenador por lo que reciba el String "ordenadosPor"
			String consulta = "select CODIGO, TITULO, AUTOR, TIPO, PRESTADO from " +
			BDNombre + ".documentos " + "order by "+ ordenadosPor +" asc, PRESTADO desc;";
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
		
		private Documento[] buscarPor(Connection con, String BDNombre, String porCodigo){
			//Se crea un nuevo array de una sola posicion que va a recibir un Objeto o ninguno
			busqueda =  new Documento[1];
			Documento a = null;
			
			//Consulta que selecciona un documento de la tabla con el codigo = "porCodigo"
			String consulta = "select CODIGO, TITULO, AUTOR, TIPO, PRESTADO from " +
			BDNombre + ".documentos " + "where CODIGO = '"+ porCodigo +"';";
			Statement stmt = null;
			
			try{
				stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(consulta);
				
				//"numero" es la posicion que va a ocupar cada objeto en el array de objetos
				int numero = 0;			
				while(rs.next()){					
					a = new Documento(
							Integer.parseInt(rs.getString(1)),//(int) Codigo
							rs.getString(2),//(String) Titulo
							rs.getString(3),//(String) Autor
							rs.getString(4),//(String) Tipo
							rs.getBoolean(5) //(Boolean) Prestado true/false || 0/1
					);
				//vamos llenando el array con objetos de la clase
				busqueda [numero] = a;
				//Aumenta número y con el la posición de cada objeto en el array
				numero++;
				}

			}catch (SQLException e){
				
				e.printStackTrace();
			}
			//Devuelve un array "busqueda" que es mostrado por consola
			return busqueda;
		}
		
		private void limpiarTabla(){
			
			for(int i = 0; i < 30; i++){
				//para cada fila
				for(int j = 0; j < 5; j++){
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



