package tablas;
import java.sql.*;


public class Prestamo {
	private int cod_documento;
	private String fecha_prestamo,fecha_fin,cod_usuario;
	private boolean atiempo;
	
	//constructor
	public Prestamo(int cod_d, String cod_u, String fp,String ff){
		cod_documento = cod_d;
		cod_usuario = cod_u;
		fecha_prestamo = fp;
		fecha_fin = ff;
	}

	/**--------------------Métodos de la base de datos----------------------**/
	public void crearTabla(Connection con, String BDNombre){
		//query de creacion de la tabla Prestamos
		String query = "CREATE TABLE "+BDNombre+".prestamos (" +
				"COD_DOCUMENTO INTEGER(15) NOT NULL," +
				"COD_USUARIO VARCHAR(15) NOT NULL," +
				"FECHA_PRESTAMO VARCHAR(30) NOT NULL," +
				"FECHA_DEVOLUCION VARCHAR(30) NOT NULL," +
				"ATIEMPO BOOLEAN," +
				"PRIMARY KEY (COD_DOCUMENTO, FECHA_PRESTAMO),"+
				"FOREIGN KEY(COD_DOCUMENTO) REFERENCES biblioteca.documentos(CODIGO)," +
				"FOREIGN KEY(COD_USUARIO) REFERENCES biblioteca.usuarios(DNI)" +
				");";
		
		Statement stmt = null;
		try{
			stmt = con.createStatement();
			stmt.executeUpdate(query);
		}catch(SQLException s){
			s.printStackTrace();
		}finally{
			try{
				stmt.close();
			}catch (SQLException e){
				e.printStackTrace();
			}
		}
	}

	public void insertarPrestamo(Connection con, String BDNombre, Prestamo pre){
		//Consulta de insercion
		String query = "Insert into "+BDNombre+".prestamos (COD_DOCUMENTO, COD_USUARIO, FECHA_PRESTAMO, FECHA_DEVOLUCION) VALUES(" +
				pre.getCodDocumento()+"," +
				"'"+pre.getCodUsuario()+"',"+
				"'"+pre.getFechaPrestamo() +"',"+
				"'"+pre.getFechaFin() +"'"+
				");";
		
		Statement stmt = null;
		try{
			stmt = con.createStatement();
			stmt.execute(query);
		} catch (SQLException s){
			s.printStackTrace();
		} finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void borrarPrestamo(Connection con,String BDNombre,String cod){
		String query = "delete from "+BDNombre+".prestamos where COD_DOCUMENTO = "+cod+";";
		
		Statement stmt = null;
		try{
			stmt = con.createStatement();
			stmt.executeQuery(query);
		} catch (SQLException s){
			s.printStackTrace();
		} finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void modificarPrestamo(Connection con,String BDNombre,String dni,Prestamo pres){
		borrarPrestamo(con, "biblioteca", dni);
		insertarPrestamo(con, "biblioteca", pres);
	}
	
	
	/**------------------------Setters---------------------------**/
	
	public void setCodDocumento(int c){
		cod_documento = c;
	}
	
	public void setCodUsuario(String c){
		cod_usuario = c;
	}
	
	public void setFechaInicio(String f){
		fecha_prestamo = f;
	}
	
	public void setFechaFin(String f){
		fecha_fin = f;
	}
	
	public void setATiempo(boolean a){
		atiempo = a;
	}
	
/**------------------------Getters--------------------------**/
	public int getCodDocumento(){
		return cod_documento;
	}
	
	public String getCodUsuario(){
		return cod_usuario;
	}
	
	public String getFechaPrestamo(){
		return fecha_prestamo;
	}
	
	public String getFechaFin(){
		return fecha_fin;
	}
	
	public boolean getATiempo(){
		return atiempo;
	}
}//Fin de la clase Préstamo
