package tablas;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ModificarUsuario {
	
	//método que modifica el nombre del usuario
	public void modificarNombre(Connection con,String BDNombre,String dni,String nom){
		String query = "UPDATE "+BDNombre+".usuarios SET NOMBRE ='"+nom+"' WHERE DNI = '"+dni+"';";
		
		Statement stmt = null;
		try{
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException s){
			s.printStackTrace();
		}finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	//método que modifica el apellido del usuario
	public void modificarApellido(Connection con,String BDNombre,String dni,String ape){
		String query = "UPDATE "+BDNombre+".usuarios SET APELLIDO ='"+ape+"' WHERE DNI = '"+dni+"';";
		
		Statement stmt = null;
		try{
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException s){
			s.printStackTrace();
		}finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}			
	}
		
	//metodo que modifica la columna
	public void modificarPrestados(Connection con,String BDNombre,int prestamos,String dni){
		String query = "UPDATE "+BDNombre+".usuarios SET NUMPRESTAMOS = "+prestamos+" WHERE DNI = '"+dni+"';";
		
		Statement stmt = null;
		try{
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException s){
			s.printStackTrace();
		} finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}//fin de modificarPrestados()
		
	//metodo que modifica la columna telefono
	public void modificarTelefono(Connection con,String BDNombre,int telf,String dni){
		String query = "UPDATE "+BDNombre+".usuarios SET TELEFONO = "+telf+" WHERE DNI = '"+dni+"';";
		
		Statement stmt = null;
		try{
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException s){
			s.printStackTrace();
		} finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}//fin de modificarTelefono()
	
	//metodo que modifica la columna movil
	public void modificarMovil(Connection con,String BDNombre,int mov,String dni){
		String query = "UPDATE "+BDNombre+".usuarios SET MOVIL = "+mov+" WHERE DNI = '"+dni+"';";
		
		Statement stmt = null;
		try{
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException s){
			s.printStackTrace();
		} finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}//fin de modificarMovil()
	
	//método que modifica la fecha de nacimiento del usuario
	public void modificarFechaNacimiento(Connection con,String BDNombre,String dni,String fech){
		String query = "UPDATE "+BDNombre+".usuarios SET FECHA_NACIMIENTO ='"+fech+"' WHERE DNI = '"+dni+"';";
		
		Statement stmt = null;
		try{
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException s){
			s.printStackTrace();
		}finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}			
	}//fin de modificarFechaNacimiento()
	
	//método que modifica el expediente del usuario
	public void modificarExpediente(Connection con,String BDNombre,String dni,String ex){
		String query = "UPDATE "+BDNombre+".usuarios SET EXPEDIENDE ='"+ex+"' WHERE DNI = '"+dni+"';";
		
		Statement stmt = null;
		try{
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException s){
			s.printStackTrace();
		}finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}//fin de modificarExpediente()
	
	//método que modifica el nombre del departamento del usuario
	public void modificarNomDepart(Connection con,String BDNombre,String dni,String nom){
		String query = "UPDATE "+BDNombre+".usuarios SET NOM_DEPART ='"+nom+"' WHERE DNI = '"+dni+"';";
		
		Statement stmt = null;
		try{
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException s){
			s.printStackTrace();
		}finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}			
	}//fin de modificarNombreDepart()
	
	//método que modifica la asignatura impartida por el usuario
	public void modificarAsignatura(Connection con,String BDNombre,String dni,String asig){
		String query = "UPDATE "+BDNombre+".usuarios SET ASIGNATURA ='"+asig+"' WHERE DNI = '"+dni+"';";
		
		Statement stmt = null;
		try{
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException s){
			s.printStackTrace();
		}finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}			
	}//fin de modificarAsignatura()
	
	public void modificarDireccion(Connection con,String BDNombre,String dni,String dir){
		String query = "UPDATE "+BDNombre+".usuarios SET DIRECCION ='"+dir+"' WHERE DNI = '"+dni+"';";
		
		Statement stmt = null;
		try{
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException s){
			s.printStackTrace();
		}finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}			
	}//fin de modificarDireccion()
	
	public void modificarPuedePrestar(Connection con,String BDNombre,String dni,boolean p){
		String query = "UPDATE "+BDNombre+".usuarios SET PUEDE_PRESTAR ='"+p +"' WHERE DNI = '"+dni+"';";
		
		Statement stmt = null;
		try{
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException s){
			s.printStackTrace();
		}finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}			
	}//fin de modificarPuedePrestar()
	
	public void modificarFamiliar(Connection con,String BDNombre,String dni,boolean fami){
		String query = "UPDATE "+BDNombre+".usuarios SET FAMILIAR ="+fami+" WHERE DNI = '"+dni+"';";
		
		Statement stmt = null;
		try{
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException s){
			s.printStackTrace();
		}finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}			
	}//fin de modificarContrasenia()
	
	//método que modifica la contraseña del usuario
	public void modificarContrasenia(Connection con,String BDNombre,String dni,String cont){
		String query = "UPDATE "+BDNombre+".usuarios SET CONTRASENIA ='"+cont+"' WHERE DNI = '"+dni+"';";
		
		Statement stmt = null;
		try{
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException s){
			s.printStackTrace();
		}finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}			
	}//fin de modificarContrasenia()
	
}//Fin de la clase ModificarUsuarui
