package inter_bibliotecario;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

import tablas.Documento;

public class Bibliografia {
	
	Connection con = GUI_Principal.con;//Conexión pasada desde la clase GUI_Principal
	private final int alto = 280;
	private final int ancho = 764;
	private Documento[] lista = new Documento[150];	//recibe en un array de Objetos, todos los Documentos de la base de datos
	private Documento[] busqueda = null; //recibe solo un Objeto Documento con el codigo establecido
	private int pagina = 0; //Numero de pagina
		
	/* * * * * * * * * * * * * * * * * Constructor de la clase * * * * * * * * * * * * * * * */
	public Bibliografia(){		
		datos = new String [15] [5];
		tabla = new JTable(datos, Titulos);
		barra_lateral = new JScrollPane(tabla);
		colocar();
		acciones();
		lista = documentosPor(con, "biblioteca", "CODIGO");
		System.out.println("Ventana Bibliografía abierta.");
	}

	/* * * * * * Ventana * * * * * * */
	private JFrame ventana = new JFrame("Bibliograf\u00EDa");	
	
	/* * * * * * * Panel 1 * * * * * */
	private JPanel panel1 = new JPanel();
		private JButton volver = new JButton("<- Volver");
		private JLabel lblNewLabel = new JLabel("Ordenar documentos por:");
		private  String [] ordenar = {
				"Elige...",
				"Código",
				"Título",
				"Autor",
				"Tipo"};
		private JComboBox<Object> opciones = new JComboBox<Object>(ordenar);	
		private JLabel lblBusqueda = new JLabel("Buscar documento por c\u00F3digo.           Introduce el c\u00F3digo:");
		private JTextField buscar = new JTextField();
		
	/* * * * * * * Panel 2 * * * * * * */
	private JPanel panel_tabla = new JPanel();
	private JScrollPane barra_lateral;
		private JTable tabla;
			private String[] Titulos = {"Codigo", "Título", "Autor", "Tipo", "Prestado"};
			private String[][] datos;	
	private JButton Modificar = new JButton("Modificar");
	
	/* * * * * * * Panel 3 * * * * * * */
	private JPanel panel3 = new JPanel();
	private JButton izquierda = new JButton(new ImageIcon("izquierda.gif"));
	private JButton derecha = new JButton(new ImageIcon("derecha.gif"));
	private JLabel lblpagina = new JLabel(String.valueOf(pagina+1));
	private final JButton borrar = new JButton("Borrar");
	private final JButton anadir = new JButton("A\u00F1adir Documentos");
	private final JButton limpiar = new JButton("Limpiar");
	
	/* * * * * * * * * * * * * * MÉTODOS * * * * * * * * * * * * * */
	
	/** Metodo que hace visible la ventana */
	public void Mostrar(){
		ventana.setVisible(true);
	}

		
	/** Coloca los elementos */
	private void colocar() {
		
		/* * * * * * Ventana * * * * * * */
		ventana.setTitle("Bibliograf\u00EDa");
		ventana.setBounds(250, 300, 789, 462);
		ventana.getContentPane().setLayout(null);
		ventana.setResizable(false);
		
		/* * * * * * * Panel 1 * * * * * */
		panel1.setBorder(UIManager.getBorder("PasswordField.border"));
		panel1.setBounds(10, 11, 764, 74);		
		panel1.setLayout(null);
		
		volver.setBounds(21, 23, 89, 25);
		panel1.add(volver);
		
		lblNewLabel.setBounds(186, 15, 192, 14);
		panel1.add(lblNewLabel);
		
		opciones.setBounds(448, 12, 207, 20);
		panel1.add(opciones);
		
		lblBusqueda.setBounds(186, 47, 329, 16);
		panel1.add(lblBusqueda);
		
		buscar.setBounds(525, 46, 130, 18);
		panel1.add(buscar);
		buscar.setColumns(10);
		ventana.getContentPane().add(panel1);
		
		/* * * * * * * Panel 2 * * * * * * */
		panel_tabla.add(barra_lateral);
		tabla.setPreferredScrollableViewportSize(new Dimension(ancho-2, alto));	
		panel_tabla.setBounds(10, 105, ancho, alto-14);
		ventana.getContentPane().add(panel_tabla);
		
		/* * * * * * * Panel 3 * * * * * * */
		panel3.setBounds(10, 382, 764, 41);
		panel3.setLayout(null);
		
		Modificar.setBounds(12, 5, 108, 32);
		panel3.add(Modificar);
		Modificar.setEnabled(false);

		izquierda.setBounds(319, 8, 23, 25);
		derecha.setBounds(368, 8, 23, 25);
		panel3.add(izquierda);izquierda.setEnabled(false);
		panel3.add(derecha);derecha.setEnabled(false);
		
		lblpagina.setHorizontalAlignment(SwingConstants.CENTER);
		lblpagina.setBounds(341, 8, 27, 26);		
		panel3.add(lblpagina);
		
		ventana.getContentPane().add(panel3);
		borrar.setEnabled(false);
		borrar.setBounds(138, 5, 108, 32);
		
		panel3.add(borrar);
		anadir.setEnabled(false);
		anadir.setBounds(569, 5, 185, 32);
		
		panel3.add(anadir);
		limpiar.setBounds(444, 5, 108, 32);
		
		panel3.add(limpiar);
					
	}
	
	/** Acciones de los elementos */
	public void acciones(){
		
		//Accion del boton volver
		volver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent volver) {
				/* cierra la ventana y la quita de memoria, por lo que al
				 * abrirla de nuevo, volvera a ejecutarse toda la ventana*/
				ventana.dispose();
			}			
		});
				
		//Se pone en 0 (Elige...) la primera vez
		opciones.setSelectedIndex(0);
		//Acciones de la lista desplegable
		opciones.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				//Si se selecciona la primera opcion
				if (opciones.getSelectedIndex() == 1){
					//Ordenar por CÓDIGO
					System.out.println("Documentos ordenados por CODIGO:");
					mostrarDocumentos(documentosPor(con, "biblioteca", "CODIGO"));
					colocaDocumentos(lista);
					Modificar.setEnabled(false);
					borrar.setEnabled(false);
					anadir.setEnabled(false);
					derecha.setEnabled(true);
				}
				//Si se selecciona la segunda opcion
				if (opciones.getSelectedIndex() == 2){
					//Ordenar por TITULO
					System.out.println("Documentos ordenados por TITULO:");
					mostrarDocumentos(documentosPor(con, "biblioteca", "TITULO"));
					colocaDocumentos(lista);
					Modificar.setEnabled(false);
					borrar.setEnabled(false);
					anadir.setEnabled(false);
					derecha.setEnabled(true);
				}
				//Si se selecciona la tercera opcion
				if (opciones.getSelectedIndex() == 3){
					//Ordenar por AUTOR
					System.out.println("Documentos ordenados por AUTOR:");
					mostrarDocumentos(documentosPor(con, "biblioteca", "AUTOR"));
					colocaDocumentos(lista);
					Modificar.setEnabled(false);
					borrar.setEnabled(false);
					anadir.setEnabled(false);
					derecha.setEnabled(true);
				}
				//Si se selecciona la cuarta opcion
				if (opciones.getSelectedIndex() == 4){
					//Ordenar por TIPO
					System.out.println("Documentos ordenados por TIPO:");
					//muestra los documentos por consola y los ordena por tipo
					mostrarDocumentos(documentosPor(con, "biblioteca", "TIPO"));
					//llamada al método que coloca los datos en la tabla
					colocaDocumentos(lista);
					/* Se descativa el botón Modificar aunque en la llamada al método
					 * colocaDocumentos() se haya activado */					
					Modificar.setEnabled(false);
					borrar.setEnabled(false);
					anadir.setEnabled(false);
					derecha.setEnabled(true);
				}							
			}
		});
		//Accion del botón Buscar
		buscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pagina = 0;
				mostrarDocumentos(buscarPor(con, "biblioteca", buscar.getText()));
				colocaDocumentos(busqueda);
				anadir.setEnabled(false);
				derecha.setEnabled(false);
				izquierda.setEnabled(false);
				//Se activa el boton modificar porque existe un documento el cual modificar. Solo se activa cuando se hace la búsqueda
				if (busqueda[0] != null){Modificar.setEnabled(true); borrar.setEnabled(true);}
				lblpagina.setText(String.valueOf(pagina+1));
				buscar.setText("");
			}
		});
		//Accion del botón Modificar
		Modificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modificaDatosDocumento();
			}
		});
		//Accion del boton borrar
		borrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarDocumento();
			}
		});
		//Accion de siguiente pagina
		derecha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pagina++;				
				izquierda.setEnabled(true);
				System.out.println(pagina);
				colocaDocumentos(lista);
				if (pagina == 9){derecha.setEnabled(false);}
				lblpagina.setText(String.valueOf(pagina+1));
			}
		});
		
		izquierda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pagina--;				
				derecha.setEnabled(true);
				System.out.println(pagina);
				colocaDocumentos(lista);
				if (pagina == 0){izquierda.setEnabled(false);}				
				lblpagina.setText(String.valueOf(pagina+1));				
			}
		});
		limpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCasillas();
				anadir.setEnabled(true);
				Modificar.setEnabled(false);
				borrar.setEnabled(false);
				pagina = 0;
				derecha.setEnabled(false);
				izquierda.setEnabled(false);
				lblpagina.setText(String.valueOf(pagina+1));
			}
		});
		anadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aniadirDocumentos();
			}
		});
	}
	
	/** Muestra el array de objetos Documentos por consola */
	private void mostrarDocumentos(Documento[] mostrar){
		/*Para cada posicion del array, recibe un objeto y muestra sus datos por consola.
		 * El bucle para cuando a recorrido todo el array o cuando el siguiente elemento es nulo */
		for(int i = 0; i<mostrar.length && mostrar[i] != null; i++){
			System.out.println("Título: " + mostrar[i].getTitulo() +
					", Autor: " + mostrar[i].getAutor() +
					", Tipo: " + mostrar[i].getTipo() + 
					". Codigo: " +mostrar[i].getCodigo());
		}//fin del for
		
	}
	
	/** Ordena los datos de los documentos en la tabla */
	private void colocaDocumentos(Documento[] array){
		
		limpiarCasillas();//pone los datos de la tabla en blanco
		//Para el array de búsqueda (que tiene 1 solo elementos)
		if (array.equals(busqueda)){			
			if (array [0] == null){			
				//en el caso de que el array no haya recibido ningun documento (primero elemento == null)
				limpiarCasillas();
				System.out.println("No se han encontrado documentos");
				//abre un JDialog indicando que no hay documentos para esa búsqueda
				ErrorDialog errorBusqueda = new ErrorDialog(ventana, "No se ha encontrado ningun documento con codigo " + buscar.getText());
				errorBusqueda.pack();
				//muestra el JDialog
				errorBusqueda.setVisible(true);
				//Desactiva el boton modificar porque no hay documentos
				Modificar.setEnabled(false);
				anadir.setEnabled(false);
				borrar.setEnabled(false);
			}else{
			
				for(int j = 0; j < 5; j++){
					//datos para cada columna
					switch (j){
						case 0: datos [0] [j] = String.valueOf(array[0].getCodigo());break;//columna 0 = Codigo
						case 1: datos [0] [j] = array[0].getTitulo();break;//columna 1 = Titulo
						case 2: datos [0] [j] = array[0].getAutor();break;//columna 2 = Autor
						case 3: datos [0] [j] = array[0].getTipo();break;//columna 3 = Tipo
						case 4: String a = String.valueOf(array[0].getPrestado());//columna 4 = prestado Si/No
								if (a.equals("true")){datos [0] [j] = "Si";}//si recibe true, pone Si en la tabla
								if (a.equals("false")){datos [0] [j] = "No";}//si recibe no, pone No en la tabla
								break;
						default: break;
					}//Fin del switch
					
				}//Fin del 2º for (fin de cada fila)	
							
			}//Fin de else
			
		}else{
			/*Bucle que va llenando las filas/columnas de la tabla con los datos de los documentos.
			 * va desde 0 hasta el final del array o hasta que el siguiente elemento es nulo (no hay documento) */			
			for(int i = 0; i < 15; i++){
				//Para cada fila
				if (array[i + (pagina * 15)] == null){
					//Si el elemento es nulo, coloca la fila en blanco
					for(int j = 0; j < 5; j++){
						//datos para cada columna
						switch (j){
							case 0: //columna 0 = Codigo
							case 1: //columna 1 = Titulo
							case 2: //columna 2 = Autor
							case 3: //columna 3 = Tipo
							case 4: datos [i] [j] = ""; break;//columna 4 = prestado Si/No
							
						}//Fin del switch
					}//Fin del 2º for
				}else{
					for(int j = 0; j < 5; j++){
						//datos para cada columna
						switch (j){
							case 0: datos [i] [j] = String.valueOf(array[i + (pagina * 15)].getCodigo());break;//columna 0 = Codigo
							case 1: datos [i] [j] = array[i + (pagina * 15)].getTitulo();break;//columna 1 = Titulo
							case 2: datos [i] [j] = array[i + (pagina * 15)].getAutor();break;//columna 2 = Autor
							case 3: datos [i] [j] = array[i + (pagina * 15)].getTipo();break;//columna 3 = Tipo
							case 4: String a = String.valueOf(array[i + (pagina * 15)].getPrestado());//columna 4 = prestado Si/No
									if (a.equals("true")){datos [i] [j] = "Si";}//si recibe true, pone Si en la tabla
									if (a.equals("false")){datos [i] [j] = "No";}//si recibe no, pone No en la tabla
									break;
							default: break;
						}//Fin del switch
						
					}//Fin del 2º for (fin de cada fila)	
				}
							
			}//Fin 1º for (fin de todas las filas de la tabla)
		}//Fin del else	
		ventana.paintAll(ventana.getGraphics());//refresca la ventana para que se vean los datos introducidos a la tabla
		
	}
	
	/** Pone en blanco cada casilla de la tabla */
	private void limpiarCasillas(){
		
		for(int i = 0; i < 15; i++){
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
		ventana.paintAll(ventana.getGraphics()); //Refresca la ventana
	}
	
	/** Devuelve un array de objetos Documentos ordenados por alguno de sus campos */
	private Documento[] documentosPor(Connection con, String BDNombre, String ordenadosPor){
		
		Documento a = null;
		//Consulta que selecciona los documentos ordenador por lo que reciba el String "ordenadosPor"
		String consulta = "select CODIGO, TITULO, AUTOR, TIPO, PRESTADO from " +
		BDNombre + ".documentos " + "order by "+ ordenadosPor +" asc;";
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
	
	/** Devuelve un array de objetos Documentos con el codigo seleccionado */
	private Documento[] buscarPor(Connection con, String BDNombre, String porCodigo){
		//Se crea un nuevo array de una sola posicion que va a recibir un Objeto o ninguno
		busqueda =  new Documento[1];
		System.out.println("Buscando documentos con Código " + porCodigo);
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
	
	//Modifica un documento previamente buscado
	private void modificaDatosDocumento(){
		System.out.print("Modificar el siguiente documento --->  ");
		System.out.println("Titulo: " + datos [0][1] + ", Autor: " + datos [0][2] + ", Tipo: " + datos [0][3] + ", ¿Prestado? " + datos [0][4]);
		//Variable que traduce el valor del campo "prestado"
		boolean prestado = false;		
		if (datos[0][4].equals("Si") || datos[0][4].equals("si") || datos[0][4].equals("SI")){prestado = true;}
		if (datos[0][4].equals("No") || datos[0][4].equals("no") || datos[0][4].equals("NO")){prestado = false;}
		//creacion del documentos a modificar
		Documento modificar = new Documento(Integer.parseInt(datos [0][0]), datos [0][1], datos [0][2], datos [0][3], prestado);
		//modifica cada dato del documento por los que hay en cada campo de la primera fila
		modificar.modificarTitulo(GUI_Principal.con, "biblioteca", Integer.parseInt(datos [0][0]), datos [0][1]);
		modificar.modificarAutor(GUI_Principal.con, "biblioteca", Integer.parseInt(datos [0][0]), datos [0][2]);
		modificar.modificarTipo(GUI_Principal.con, "biblioteca", Integer.parseInt(datos [0][0]), datos [0][3]);
		modificar.modificarPrestado(GUI_Principal.con, "biblioteca", Integer.parseInt(datos [0][0]), prestado);
	}
	
	//Borra un docuemtno previamente Buscado
	private void borrarDocumento(){
		boolean prestado = false;		
		if (datos[0][4].equals("Si") || datos[0][4].equals("si") || datos[0][4].equals("SI")){prestado = true;}
		if (datos[0][4].equals("No") || datos[0][4].equals("no") || datos[0][4].equals("NO")){prestado = false;}
		
		Documento borrar = new Documento(Integer.parseInt(datos [0] [0]), datos[0][1], datos[0][2], datos[0][3], prestado);
		borrar.borrarDocumento(con, "biblioteca", Integer.parseInt(datos [0] [0]));
		
		System.out.print("Borrado el siguiente documento --->  ");
		System.out.println("Titulo: " + datos [0][1] + ", Autor: " + datos [0][2] + ", Tipo: " + datos [0][3] + ", ¿Prestado? " + datos [0][4]);
		limpiarCasillas();
	}
	
	//Añade documentos que se escriban directamente en la tabla
	private void aniadirDocumentos(){
		//Objeto documento vacio		
		Documento aniadir = new Documento(0, null, null, null, true);
		//Variable prestado
		boolean prestado = false;		
		if (datos[0][4].equals("Si") || datos[0][4].equals("si") || datos[0][4].equals("SI")){prestado = true;}
		if (datos[0][4].equals("No") || datos[0][4].equals("no") || datos[0][4].equals("NO")){prestado = false;}
		for (int i = 0; i < 15; i++){
			
			//Comprueba que el código no este en el base de datos
			if (comprobarCodigo(con, "biblioteca", datos[i][0])){			
				//Cada fila
				if (datos[i][0] != "" && datos  [i][1] != "" && datos  [i][2] != "" && datos  [i][3] != "" && datos  [i][4] != ""){
					aniadir.insertarDocumento(con, "biblioteca", new Documento(Integer.parseInt(datos[i][0]), datos[i][1], datos[i][2], datos[i][3], prestado));
					System.out.println("Documento Insertado: " + datos[i][0] + " - " + datos[i][1] + " - " + datos[i][2] + " - " + datos[i][3] + " - " + prestado);
				}
				//El codigo existiria
			}else{
				System.out.println("El codigo ya existe.");
			}
		}
	}
	/** Devuelve true si el documento NO esta en la base de datos */
	private boolean comprobarCodigo(Connection con, String BDNombre, String porCodigo){
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
		
		if (busquedaDocumentos[0] == null) {
			return true;
		}else{
			return false;
		}
	}
	
}