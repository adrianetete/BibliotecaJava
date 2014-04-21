package tablas;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Consulta {

	private int cod_documento;
	private String cod_usuario, fechaConsulta;
	//constructor
	public Consulta(int cod_d, String cod_u, String fechaCons){
		cod_documento = cod_d;
		cod_usuario = cod_u;
		fechaConsulta = fechaCons;
	}
	
	public void crearTabla(Connection con, String BDNombre){
		//query de creacion de la tabla Prestamos
		String query = "CREATE TABLE "+BDNombre+".consultas (" +
				"COD_DOCUMENTO INTEGER(15) NOT NULL," +
				"COD_USUARIO VARCHAR(15) NOT NULL," +
				"FECHA_CONSULTA VARCHAR(15) NOT NULL," +
				"PRIMARY KEY (COD_DOCUMENTO, FECHA_CONSULTA),"+
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
	
	public void insertarConsulta(Connection con, String BDNombre, Consulta cons){
		//Consulta de insercion
		String query = "Insert into "+BDNombre+".consultas (COD_DOCUMENTO, COD_USUARIO, FECHA_CONSULTA) VALUES(" +
				cons.getCodDocumento()+"," +
				"'"+cons.getCodUsuario()+"',"+
				"'"+cons.getFechaConsulta()+"'"+
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
	
	public void borrarConsulta(Connection con,String BDNombre,int cod){
		String query = "delete from "+BDNombre+".consultas where COD_DOCUMENTO = "+cod+";";
		
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
	
/**------------------------Setters---------------------------**/
	
	public void setCodDocumento(int c){
		cod_documento = c;
	}
	
	public void setCodUsuario(String c){
		cod_usuario = c;
	}

	public void setFechaConsulta(String fechaCons) {
		fechaConsulta = fechaCons;
	}
	
/**------------------------Getters--------------------------**/
	public int getCodDocumento(){
		return cod_documento;
	}
	
	public String getCodUsuario(){
		return cod_usuario;
	}

	public String getFechaConsulta() {
		return fechaConsulta;
	}
	
}
