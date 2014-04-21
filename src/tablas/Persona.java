package tablas;

public class Persona {
	private String dni,nombre,apellido;
	
	/**-----------------------------Constructores-----------------------**/
	public Persona(String d,String nom,String ape){
		dni = d;
		nombre = nom;
		apellido = ape;
	}//fin del constructor con parámetros
	
	public Persona(){
		dni = null;
		nombre = null;
		apellido = null;
	}//fin del constructor por defecto
	
	/**----------------------------Setters---------------------------**/
	
	public void setDNI(String d){
		dni = d;
	}
	
	public void setNombre(String n){
		nombre = n;
	}
	
	public void setApellido(String a){
		apellido = a;
	}
	
	
	/**----------------------------Getters---------------------------**/
	
	public String getDNI(){
		return dni;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public String getApellido(){
		return apellido;
	}
	
}//fin de la clase Presona
