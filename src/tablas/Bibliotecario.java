package tablas;
import java.sql.*;

public class Bibliotecario extends Persona{
	
	private String contrasenia;
	
	public Bibliotecario(String d, String nom,String ape, String cont){
		super(d,nom,ape);
		contrasenia = cont;
	}
	
	/**-------------------------Base de datos----------------------*/
	public void crearTabla(Connection con,String BDNombre){
		//consulta de creación
		String query = "Create table "+BDNombre+".bibliotecarios (" +
				"DNI VARCHAR(12) NOT NULL," +
				"NOMBRE VARCHAR(15) NOT NULL," +
				"APELLIDO VARCHAR(15) NOT NULL," +
				"CONTRASENIA VARCHAR(15) NOT NULL," +
				"PRIMARY KEY (DNI)" +
				");";
		
		Statement stmt = null;

		try{
			//Ejecutar la consulta
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch(SQLException s){
			s.printStackTrace();
		}
	}//fin de crearTabla()

	public void insertarBibliotecario(Connection con,String BDNombre,Bibliotecario bi){
		//consulta de inserción
		String query = "INSERT INTO "+BDNombre+".bibliotecarios VALUES(" +
				"'"+bi.getDNI()+"'," +
				"'"+bi.getNombre()+"'," +
				"'"+bi.getApellido()+"'," +
				"'"+bi.getContrasenia()+"'" +
				");";
		
		Statement stmt = null;
		try{
			//ejecutar la consulta
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		}catch (SQLException s){
			s.printStackTrace();
		}//fin del try...catch
	}//fin de insertarBibliotecario()

	public void borrarBibliotecario(Connection con, String BDNombre, String DNI){
		//Query de borrado
		String query = "DELETE FROM "+BDNombre+".bibliotecario WHERE DNI = '"+DNI+"';";
		
		Statement stmt = null;
		try{
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException s){
			s.printStackTrace();
		}
	}

	/*public void modificarBibliotecario(Connection con,String BDNombre,String dni,Bibliotecario bib){
		borrarBibliotecario(con, "biblioteca", dni);
		insertarBibliotecario(con, "biblioteca", bib);
	}*/
	
	
	public void setContrasenia(String con){
		contrasenia = con;
	}
	
	public String getContrasenia(){
		return contrasenia;
	}
	
}//fin de la clase Bibliotecario
