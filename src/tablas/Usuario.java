package tablas;
import java.sql.*;


public class Usuario extends Persona{
	//declaración de variables
	private int numprestamos,telefono,movil;
	private String fecha_nacimiento,expediente,nom_depart,asignatura,direccion,fecha_multa,contrasenia;
	private boolean familiar, puedeprestar;
	
	/**-------------------------------Constructores-----------------------**/
	
	public Usuario(){}			//constructor sin parámetros
	
	public Usuario(String d,String nom,String ape,int tel,int mov,String fecha,String exp,String con){
		super(d,nom,ape);		//invocar al constructor de la clase base
		
		telefono = tel;
		movil = mov;
		fecha_nacimiento = fecha;
		expediente = exp;
		nom_depart = null;
		asignatura = null;
		direccion = null;
		fecha_multa = null;
		contrasenia = con;
	}//fin del constructor para usuario estudiante
	
	public Usuario(String d,String nom,String ape,int tel,int mov,String fecha,String nom_a,String asig,String con){
		super(d,nom,ape);		//invocar al constructor de la clase base
		
		telefono = tel;
		movil = mov;
		fecha_nacimiento = fecha;
		nom_depart = nom_a;
		asignatura = asig;
		expediente = null;
		direccion = null;
		fecha_multa = null;
		contrasenia = con;
	}//fin del constructor para usuario profesor
	
	public Usuario(String d,String nom,String ape,int tel,int mov,String fecha,boolean fami,String dir,String con){
		super(d,nom,ape);		//invocar al constructor de la clase base
		
		telefono = tel;
		movil = mov;
		fecha_nacimiento = fecha;
		direccion = dir;
		expediente = null;
		nom_depart = null;
		asignatura = null;
		fecha_multa = null;
		familiar = fami;
		contrasenia = con;
	}//fin del constructor para usuario general
	
	//Construcor general	
	public Usuario(String d,String nom,String ape,int numprest,int tel,int mov,String fecha,String expe,
			boolean fami,String dir,String con, String NomDep, String asign){
		super(d,nom,ape);		//invocar al constructor de la clase base
		
		telefono = tel;
		movil = mov;
		fecha_nacimiento = fecha;
		direccion = dir;
		expediente = expe;
		nom_depart = NomDep;
		asignatura = asign;
		fecha_multa = null;
		familiar = fami;
		contrasenia = con;
		
	}
	
	//método que crea la tabla Usuario
	public void crearTabla(Connection con,String BDNombre){
		//Introducir la consulta que crea la tabla
		String query = "CREATE TABLE "+BDNombre+".usuarios("+
						"DNI VARCHAR(12) NOT NULL," +
						"NOMBRE VARCHAR(15) NOT NULL," +
						"APELLIDO VARCHAR(15) NOT NULL," +
						"NUMPRESTAMOS INTEGER(2)," +
						"TELEFONO INTEGER(9)," +
						"MOVIL INTEGER(9)," +
						"FECHA_NACIMIENTO VARCHAR(20)," +
						"EXPEDIENDE VARCHAR(20)," +
						"NOM_DEPART VARCHAR(20)," +
						"ASIGNATURA VARCHAR(12)," +
						"FAMILIAR BOOLEAN," +
						"DIRECCION VARCHAR(25)," +
						"PUEDE_PRESTAR BOOLEAN," +
						"FECHA_MULTA VARCHAR(20)," +
						"CONTRASENIA VARCHAR(20)," +
						"PRIMARY KEY(DNI)" +
						");";
		
		java.sql.Statement stmt = null;
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
	}// fin de crearTabla()
	
	//método que inserta usuarios 
	public void insertarUsuario(Connection con,String BDNombre,Usuario usu){
		//pasamos el DNI a mayúsculas
		usu.setDNI(usu.getDNI().toUpperCase());
		
		//comprobamos que el código de usuario sea único
		if(! comprobarCodigo(con, BDNombre, usu.getDNI())){
			System.err.println("El numero de usuario debe ser único");
		} else {
			String query = "INSERT INTO "+BDNombre+".usuarios VALUES(" +
						"'"+usu.getDNI()+"'," +
						"'"+usu.getNombre()+"'," +
						"'"+usu.getApellido()+"'," +
						usu.getNumprestamos()+"," +
						usu.getTelefono()+"," +
						usu.getMovil()+"," +
						"'"+usu.getFechanaciemto()+"'," +
						"'"+usu.getExpediente()+"'," +
						"'"+usu.getNomdepart()+"'," +
						"'"+usu.getAsignatura()+"'," +
						usu.getFamiliar()+"," +
						"'"+usu.getDireccion()+"'," +
						usu.getPuedePrestar()+"," +
						"'"+usu.getFechaMulta()+"'," +
						"'"+usu.getContrasenia()+"'" +
					");";
			java.sql.Statement stmt = null;
			try {
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
		}
	}
	
	//método que borra usuarios
	public void borrarUsuario(Connection con,String BDNombre,String d){
		String query = "delete from "+BDNombre+".usuarios where DNI = "+d;
		
		java.sql.Statement stmt = null;
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
	}
		
	//metodo que modifica la columna prestados
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
	
	}
	
	public boolean comprobarCodigo(Connection con, String BDNombre,String dni){
		boolean correcto = true;
		String query = "select DNI from "+BDNombre+".usuarios";
		
		Statement stmt = null;
		
		try{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()){
				if (rs.getString(1).equals(dni)){
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
	
	/**------------------------------Setters-----------------------------**/
	
	public void setNumprestamos(int pres){
		numprestamos = pres;
	}
	
	public void setTelefono(int tel){
		telefono = tel;
	}
	
	public void setMovil(int mo){
		movil = mo;
	}
	
	public void setFechanacimiento (String fe){
		fecha_nacimiento = fe;
	}
	
	public void setExpediente (String ex){
		expediente = ex;
	}
	
	public void setNomdepart(String no){
		nom_depart = no;
	}
	
	public void setAsignatura (String as){
		asignatura = as;
	}
	
	public void setDireccion (String dir){
		direccion = dir;
	}
	
	public void setPuedePrestar (boolean p){
		puedeprestar = p;
	}
	
	public void setFechaMulta(String f){
		fecha_multa = f;
	}
	
	public void setContrasenia(String c){
		contrasenia = c;
	}

	public void setFamiliar(boolean fam) {
		familiar = fam;
	}
	
	/**------------------------------Getters-----------------------------**/
	
	public int getNumprestamos(){
		return numprestamos;
	}
	
	public int getTelefono(){
		return telefono;
	}
	
	public int getMovil(){
		return movil;
	}
	
	public String getFechanaciemto(){
		return fecha_nacimiento;
	}
	
	public String getExpediente(){
		return expediente;
	}
	
	public String getNomdepart(){
		return nom_depart;
	}
	
	public String getAsignatura(){
		return asignatura;
	}
	
	public String getDireccion(){
		return direccion;
	}
	
	public boolean getPuedePrestar(){
		return puedeprestar;
	}
	
	public String getFechaMulta(){
		return fecha_multa;
	}
	
	public String getContrasenia(){
		return contrasenia;
	}
	public boolean getFamiliar(){
		return familiar;
	}

}//fin de la clase Usuario
