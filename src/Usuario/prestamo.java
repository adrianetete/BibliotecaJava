package Usuario;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import tablas.Documento;
import tablas.Prestamo;
import tablas.Usuario;

public class prestamo {

	
	private JFrame ventanaPrestamo;
	private JPanel panel_titulo = new JPanel();
	private JPanel panel1 = new JPanel();
	private JLabel lblPrestamo = new JLabel("PR\u00C9STAMO");
	private final JLabel lblConectadoComo = new JLabel("");
	private JLabel lblSeleccioneLaFila = new JLabel("Seleccione la fila del documento ha prestar:");
	private JLabel lblDni = new JLabel("DNI:");
	private JLabel lblFechaPrestamo = new JLabel("Fecha Préstamo:");
	private JLabel lblFechaDevolucion = new JLabel("Fecha Devolución:");
	private JLabel txtFechaPrestamo = new JLabel("");
	private JLabel txtFechaDevolucion = new JLabel("");
	private JLabel txtDni = new JLabel("");
	private JLabel lblOrdenarPor = new JLabel("Ordenar Por:");
	private JLabel lblBuscar = new JLabel("Introduce Un C\u00F3digo Para Buscar:");
	
	private JTextField txtBuscar = new JTextField();
	
    private JButton btnSalir = new JButton("Salir");
    private JButton btnAceptar = new JButton("Aceptar");
    
	
	/*
	 * Para Tabla
	 */
    private final int alto = 280;
	private final int ancho = 675;
	
	private Documento[] lista = new Documento[100];
	private Documento[] busqueda = null;
	
	private JPanel panel_tabla = new JPanel();
	
	private JScrollPane barra_lateral;
	private JTable tabla;
	private String[] titulos = {"Codigo", "Título", "Autor", "Tipo"};
	private String[][] datos;
	
	private String[] camposOrdenar={"-------------", "Código", "Título", "Autor", "Tipo"};
	private JComboBox<Object> elegirOrdenacion = new JComboBox<Object>(camposOrdenar);
	/*
	 * Fin Para Tabla
	 */
	
	static Usuario usu;
	static Connection con = ventanaUsuarios.con;
	
	public prestamo(Usuario us) {	
		prestamo.usu=us;
		datos = new String [30] [10];
		tabla = new JTable(datos, titulos);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		barra_lateral = new JScrollPane(tabla);
		barra_lateral.setBounds(27, 5, 696, 308);
		
		lblConectadoComo.setText("Conectado como:  "+ventanaUsuarios.usu.getNombre()+" "+ventanaUsuarios.usu.getApellido());
		colocar();
		documentosPor(con, "biblioteca", "CODIGO");
		colocaDocumentos(lista);
		System.out.println("PRESTAMOS (prestamo usu): "+usu.getNumprestamos());
		//System.out.println("PRESTAMOS (prestamo us): "+usu.getNumprestamos());
	}
	
	public void mostrar(){
		ventanaPrestamo.setVisible(true);
	}

	private void colocar() {
		ventanaPrestamo = new JFrame();
		ventanaPrestamo.setBounds(260, 200, 780, 589);
		ventanaPrestamo.getContentPane().setLayout(null);
		ventanaPrestamo.setResizable(false);
		
		panel_titulo.setBounds(121, 11, 565, 35);
		ventanaPrestamo.getContentPane().add(panel_titulo);
		panel1.setBounds(67, 57, 651, 50);
		ventanaPrestamo.getContentPane().add(panel1);
		panel_titulo.setLayout(null);
		panel1.setLayout(null);
		panel_titulo.setLayout(null);

		
		
		lblPrestamo.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblPrestamo.setBounds(12, 6, 112, 25);
		panel_titulo.add(lblPrestamo);
		lblConectadoComo.setFont(new Font("Tahoma", Font.ITALIC, 18));
		lblConectadoComo.setBounds(164, 4, 373, 31);
		
		panel_titulo.add(lblConectadoComo);
		panel1.setLayout(null);
		
		
		lblDni.setBounds(0, 1, 127, 20);
		panel1.add(lblDni);
		txtDni.setBounds(119, 1, 150, 20);
		txtDni.setText(usu.getDNI());
		panel1.add(txtDni);
		
		
		lblFechaPrestamo.setBounds(0, 22, 103, 20);
		panel1.add(lblFechaPrestamo);
		
		
		String fecha = getFechaPrestamo();
		txtFechaPrestamo.setBounds(119, 22, 136, 20);
		txtFechaPrestamo.setText(fecha);
		panel1.add(txtFechaPrestamo);
		
		
		lblFechaDevolucion.setBounds(298, 22, 119, 20);
		panel1.add(lblFechaDevolucion);
		
		
		String devolucion = getFechaDevolucion();
		txtFechaDevolucion.setBounds(429, 22, 136, 20);
		txtFechaDevolucion.setText(devolucion);
		panel1.add(txtFechaDevolucion);
		
		ventanaPrestamo.getContentPane().add(panel_tabla);
		panel_tabla.setLayout(null);
		panel_tabla.add(barra_lateral);
		tabla.setPreferredScrollableViewportSize(new Dimension(ancho-2, alto));	
		panel_tabla.setBounds(12, 171, 752, 317);	
	    
	    btnAceptar.setFocusPainted(false);
	    btnAceptar.setBounds(36, 499, 107, 35);
	    ventanaPrestamo.getContentPane().add(btnAceptar);

	    btnSalir.setFocusPainted(false);
	    btnSalir.setBounds(646, 505, 89, 23);
	    ventanaPrestamo.getContentPane().add(btnSalir);
	    
	    lblSeleccioneLaFila.setFont(new Font("SansSerif", Font.ITALIC, 14));
	    lblSeleccioneLaFila.setBounds(23, 113, 302, 16);
	    ventanaPrestamo.getContentPane().add(lblSeleccioneLaFila);
	
	    elegirOrdenacion.setBounds(139, 139, 101, 20);
	    ventanaPrestamo.getContentPane().add(elegirOrdenacion);
		txtBuscar.setBounds(520, 136, 122, 26);
		txtBuscar.setColumns(10);
		ventanaPrestamo.getContentPane().add(txtBuscar);
		lblOrdenarPor.setBounds(36, 141, 89, 16);
		
		ventanaPrestamo.getContentPane().add(lblOrdenarPor);
		lblBuscar.setBounds(313, 141, 189, 16);
		
		ventanaPrestamo.getContentPane().add(lblBuscar);
		
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
					//llamada al método que coloca los datos en la tabla
					colocaDocumentos(lista);
				}
			}
		});
	    
		txtBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				buscarPor(con, "biblioteca", txtBuscar.getText());
				colocaDocumentos(busqueda);				
			}
		});
		
	    btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(usu.getNumprestamos()>=3){
					ErrorDialog error = new ErrorDialog(ventanaPrestamo, "Has alcanzado el límite de préstamos");
					error.pack();
					error.setVisible(true);
					ventanaPrestamo.setVisible(false);
				}
				else{
					realizarPrestamo();
				}
			}			
		});
	    btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventanaPrestamo.dispose();	
			}			
		});
	    
	}
	
	/** Metodo que almacena la fecha actual o de préstamo*/
	private String getFechaPrestamo(){
		//Calculo de la fecha actual
				Calendar calendario = Calendar.getInstance();
				SimpleDateFormat formatoFecha = new SimpleDateFormat("EEEE dd MMMM yyyy");
				return formatoFecha.format(calendario.getTime());		
	}
	
	/**Metodo que almacena la fecha de devolucion*/
	private String getFechaDevolucion(){
		//Calculo de la fecha dentro de 5 dias
				Calendar calendarioEntrega = Calendar.getInstance();
				calendarioEntrega.add(Calendar.DATE, 5);
				SimpleDateFormat formatoFecha = new SimpleDateFormat("EEEE dd MMMM yyyy");
				return formatoFecha.format(calendarioEntrega.getTime());
	}

	/** Método que realiza todas las acciones para realizar el prestamo */
	private void realizarPrestamo(){
		//Extraer el valor del codigo de la fila seleccionada
		String cod_string = (String) tabla.getModel().getValueAt(tabla.getSelectedRow(),0);
				
		//if(comprobarCodigo(ventanaUsuarios.con, "biblioteca", codigo)[0] == null){
		if(cod_string == null || cod_string == ""){
			//Comprueba que el Documento esta en la Tabla Documentos
			ErrorDialog error = new ErrorDialog(ventanaPrestamo, "Documento con codigo no válido.");
			error.pack();
			error.setVisible(true);
		}	
		//Si lo demas es correcto, pasa a crear un nuevo prestamo
		else{	
			int codigo = Integer.parseInt(cod_string);
			Prestamo prestamo = new Prestamo(codigo,//Int
						usu.getDNI(), //String
						getFechaPrestamo(), //String
						getFechaDevolucion());	  //String
			try{
				//Añade un Nuevo préstamo a la Base de Datos
				prestamo.insertarPrestamo(con, "biblioteca", prestamo);
				
				//Crea un nuevo objeto Usuario para actualizar su número de prestamos (añadiendole 1)
				usu.setNumprestamos((usu.getNumprestamos() + 1));
				System.out.println("Antes " + usu.getNumprestamos());
				usu.modificarPrestados(con, "biblioteca", (usu.getNumprestamos()), usu.getDNI());
				System.out.println("Despues " + usu.getNumprestamos());
				
				//Modificamos el libro a prestado
				Documento doc = new Documento();
				doc.modificarPrestado(con, "biblioteca", codigo, true);
				
				ErrorDialog realizado = new ErrorDialog(ventanaPrestamo, "Prestamo Realizado");
				realizado.setTitle("Información");
				realizado.pack();
				realizado.setVisible(true);
				
				ventanaPrestamo.setVisible(false);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private Documento[] documentosPor(Connection con, String BDNombre, String ordenadosPor){

		Documento a = null;
		//Consulta que selecciona los documentos ordenador por lo que reciba el String "ordenadosPor"
		String consulta = "select CODIGO, TITULO, AUTOR, TIPO, PRESTADO from " +
		BDNombre + ".documentos WHERE PRESTADO = 0 and TIPO!='Periódico' and TIPO!='Tésis' " + "order by "+ ordenadosPor +" asc, PRESTADO desc;";
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
	
	private void colocaDocumentos(Documento[] array){	
		if (array [0] == null){			
			//en el caso de que el array no haya recibido ningun documento (primero elemento == null)
			limpiarTabla();//pone los datos de la tabla en blanco
			System.out.println("No se han encontrado documentos");
			//abre un JDialog indicando que no hay documentos para esa búsqueda
			ErrorDialog errorBusqueda = new ErrorDialog(ventanaPrestamo, "No se ha encontrado ningun documento con codigo " + txtBuscar.getText());
			errorBusqueda.pack();
			//muestra el JDialog
			errorBusqueda.setVisible(true);
			//Desactiva el boton modificar porque no hay documentos
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
					}//Fin del switch
					
				}//Fin del 2º for (fin de cada fila)	
							
			}//Fin 1º for (fin de todas las filas de la tabla)
		}//Fin del else	
		ventanaPrestamo.paintAll(ventanaPrestamo.getGraphics());//refresca la ventana		
	}
	
	private Documento[] buscarPor(Connection con, String BDNombre, String porCodigo){
		//Se crea un nuevo array de una sola posicion que va a recibir un Objeto o ninguno
		busqueda =  new Documento[1];
		Documento a = null;
		
		//Consulta que selecciona un documento de la tabla con el codigo = "porCodigo"
		String consulta = "select CODIGO, TITULO, AUTOR, TIPO, PRESTADO from " +
		BDNombre + ".documentos " + "where TIPO != 'Revista' and TIPO != 'Tesis' and PRESTADO = 0 and CODIGO = '"+ porCodigo +"';";
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
