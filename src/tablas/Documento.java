package tablas;
import java.sql.*;

public class Documento {
	private int codigo;
	private String titulo,autor,tipo;
	private boolean prestado;
	
	//Constructor vacio
	public Documento(){}
	
	//constructor 
	public Documento(int cod,String tit,String aut,String tip,boolean pres){
		codigo = cod;
		titulo = tit;
		autor = aut;
		tipo = tip;
		prestado = pres;
	}
	
	//método que crea la tabla Documentos
	public void crearTabla(Connection con, String BDNombre){
		//Consulta de creación
		String query = "CREATE TABLE "+BDNombre+".documentos (" +
				"CODIGO INTEGER(15) NOT NULL," +
				"TITULO VARCHAR(30) NOT NULL," +
				"AUTOR VARCHAR(30) NOT NULL," +
				"TIPO VARCHAR(15) NOT NULL," +
				"PRESTADO BOOLEAN NOT NULL," +
				"PRIMARY KEY(CODIGO)" +
				");";
		
		Statement stmt = null;
		try{
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		}catch (SQLException s){
			s.printStackTrace();
		}finally{
			//try...catch del método close()
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}//fin del try...catch de la sentencia finally
		}
	}//fin del método crearTabla()
	
	//método que inserta documentos
	public void insertarDocumento(Connection con,String BDnombre,Documento doc){
		
		if(!comprobarCodigo(con, BDnombre, doc.getCodigo())){
			System.err.println("El código del libro debe ser único");
		} else{
			//consulta de inserción
			String query = "INSERT INTO "+BDnombre+".documentos VALUES(" +
					doc.getCodigo()+"," +
					"'"+doc.getTitulo()+"',"+
					"'"+doc.getAutor()+"',"+
					"'"+doc.getTipo()+"',"+
					doc.getPrestado()+
					");";
			
			Statement stmt = null;
			//ejecutar la consulta
			try{
				stmt = con.createStatement();
				stmt.executeUpdate(query);
			}catch(SQLException s){
				s.printStackTrace();
			}finally{
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//método que borra documentos
	public void borrarDocumento(Connection con,String BDnombre,int cod){
		//query de borrado
		String query = "DELETE FROM "+BDnombre+".documentos WHERE CODIGO = "+cod+";";
		
		Statement stmt = null;
		try{
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		}catch (SQLException s){
			s.printStackTrace();
		}finally{
			try{
				stmt.close();
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	//método que modifica el campo titulo
	public void modificarTitulo(Connection con,String BDNombre,int cod,String tit){
		String query = "UPDATE "+BDNombre+".documentos SET TITULO = '"+tit+"' WHERE CODIGO = "+cod+";";
		
		Statement stmt = null;
		try{
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		}catch (SQLException s){
			s.printStackTrace();
		}finally{
			try{
				stmt.close();
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	//método que modifica el campo autor
	public void modificarAutor(Connection con,String BDNombre,int cod,String aut){
		String query = "UPDATE "+BDNombre+".documentos SET AUTOR = '"+aut+"' WHERE CODIGO = "+cod+";";
		
		Statement stmt = null;
		try{
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		}catch (SQLException s){
			s.printStackTrace();
		}finally{
			try{
				stmt.close();
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	//método que modifica el campo tipo
	public void modificarTipo(Connection con,String BDNombre,int cod,String tip){
		String query = "UPDATE "+BDNombre+".documentos SET TIPO = '"+tip+"' WHERE CODIGO = "+cod+";";
		
		Statement stmt = null;
		try{
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		}catch (SQLException s){
			s.printStackTrace();
		}finally{
			try{
				stmt.close();
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	//método que modifica el campo Prestado
		public void modificarPrestado(Connection con,String BDNombre,int cod,boolean pre){
			String query = "UPDATE "+BDNombre+".documentos SET PRESTADO = "+pre+" WHERE CODIGO = "+cod+";";
			
			Statement stmt = null;
			try{
				stmt = con.createStatement();
				stmt.executeUpdate(query);
			}catch (SQLException s){
				s.printStackTrace();
			}finally{
				try{
					stmt.close();
				}catch (SQLException e){
					e.printStackTrace();
				}
			}
		}
	public boolean comprobarCodigo(Connection con, String BDNombre,int codigo){
		boolean correcto = true;
		String query = "select CODIGO from "+BDNombre+".documentos";
		
		Statement stmt = null;
		
		try{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				if (rs.getInt(1) == codigo){
					correcto = false;
				}
			}
		} catch (SQLException s){
			s.printStackTrace();
		} finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return correcto;
	}
	
	/**------------------------------Setters--------------------------**/
	//método que borra documentos
	public void setCodigo(int cod){
		codigo = cod;
	}
	
	public void setTitulo(String tit){
		titulo = tit;
	}
	
	public void setAutor(String aut){
		autor = aut;
	}
	
	public void setTipo(String tip){
		tipo = tip;
	}
	
	public void setPrestado(boolean pr){
		prestado = pr;
	}
	
	/**------------------------------Getters--------------------------**/
	//método que borra documentos
	public int getCodigo(){
		return codigo;
	}
	
	public String getTitulo(){
		return titulo;
	}
	
	public String getAutor(){
		return autor;
	}
	
	public String getTipo(){
		return tipo;
	}
	
	public boolean getPrestado(){
		return prestado;
	}	
	
}//Fin de la clase Documento
