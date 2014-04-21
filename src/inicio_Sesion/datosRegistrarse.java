package inicio_Sesion;

import inicio_Sesion.ErrorDialog;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

import tablas.Bibliotecario;
import tablas.Usuario;


public class datosRegistrarse<ValueChangeEvent> {

	private JFrame ventanaRegistro = new JFrame();
	
	private JPanel panel = new JPanel();
	private JTextField txtNombre = new JTextField();
	private JTextField txtNacimiento = new JTextField();
	private JTextField txtTelefono = new JTextField();
	private JTextField txtMovil = new JTextField();
	private JTextField txtNomDepart = new JTextField();
	private JTextField txtAsignatura = new JTextField();
	private JTextField txtDireccion = new JTextField();
	private JTextField txtApellido = new JTextField();
	private JTextField txtDni = new JTextField();
	private JTextField txtExpediente = new JTextField();
	
	private JPasswordField passwordContrasenia = new JPasswordField();
	
	private JLabel lblRegistrarse = new JLabel("REGISTRARSE");
	private JLabel lblContrasenia = new JLabel("Contrase\u00F1a:");
	private JLabel lblDni = new JLabel("Dni :");
	private JLabel lblNacimiento = new JLabel("Fecha de Nacimiento:");
	private JLabel lblAsignatura = new JLabel("Asignatura:");
	private JLabel lblExpediente = new JLabel("N\u00BA de Expediente:");
	private JLabel lblNomDepart = new JLabel("Nombre Departamento");
	private JLabel lblNombre = new JLabel("Nombre:");
	private JLabel lblTelefono = new JLabel("N\u00BA Telefono:");
	private JLabel lblMovil = new JLabel("N\u00BA Movil:");
	private JLabel lblDireccion = new JLabel("Direcci\u00F3n:");
	private JLabel lblApellidos = new JLabel("Apellidos:");
	
	private JButton btnRegistrar = new JButton("Registrarse");
	private JButton btnSalir = new JButton("Salir");
	
	private JCheckBox familiarTrabajando = new JCheckBox("\u00BFFamiliar trabajando?");
	
	private JRadioButton rdbtnUsuario = new JRadioButton("USUARIO");
	private JRadioButton rdbtnBibliotecario = new JRadioButton("BIBLIOTECARIO");
	private JRadioButton rdbtnProfesores = new JRadioButton("Profesores");
	private JRadioButton rdbtnEstudiante = new JRadioButton("Estudiante");
	private JRadioButton rdbtnGeneral = new JRadioButton("General");
	
	private ButtonGroup tipoUsuario = new ButtonGroup();
	private ButtonGroup usuarioBibliotecario = new ButtonGroup();
	
	static Connection con = Acceso.con;

	public datosRegistrarse() {
		colocar();
	}
	
	public void mostrarVentana() {
		ventanaRegistro.setVisible(true);
	}

	private void colocar() {
		ventanaRegistro = new JFrame();
		ventanaRegistro.setBounds(350, 200, 634, 476);		
		ventanaRegistro.getContentPane().add(panel, BorderLayout.CENTER);
		ventanaRegistro.setResizable(false);
		panel.setLayout(null);
		
		
		lblRegistrarse.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblRegistrarse.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistrarse.setBounds(224, 12, 155, 41);
		panel.add(lblRegistrarse);
		
		
		lblContrasenia.setBounds(25, 141, 107, 14);
		panel.add(lblContrasenia);	
		lblContrasenia.setEnabled(false);
		passwordContrasenia = new JPasswordField();
		passwordContrasenia.setBounds(160, 135, 107, 26);
		panel.add(passwordContrasenia);
		passwordContrasenia.setEnabled(false);
		
		
		lblDni.setToolTipText("Si us plau Introduzca su Dni");
		lblDni.setBounds(25, 112, 56, 14);
		panel.add(lblDni);
		lblDni.setEnabled(false);
		txtDni.setBounds(160, 106, 107, 26);
		txtDni.setColumns(10);
		panel.add(txtDni);
		txtDni.setEnabled(false);
		
		
		lblNombre.setBounds(25, 167, 55, 16);
		panel.add(lblNombre);
		lblNombre.setEnabled(false);
		txtNombre.setBounds(160, 162, 107, 26);
		txtNombre.setColumns(10);
		panel.add(txtNombre);
		txtNombre.setEnabled(false);
		
		lblApellidos.setBounds(25, 196, 55, 16);
		panel.add(lblApellidos);
		lblApellidos.setEnabled(false);
		txtApellido.setBounds(160, 191, 107, 26);
		txtApellido.setColumns(10);
		panel.add(txtApellido);
		txtApellido.setEnabled(false);
				
		lblNacimiento.setBounds(302, 109, 137, 20);
		panel.add(lblNacimiento);
		lblNacimiento.setEnabled(false);
		txtNacimiento.setBounds(437, 106, 107, 26);
		txtNacimiento.setColumns(10);
		panel.add(txtNacimiento);
        txtNacimiento.setEnabled(false);
		
		
		
		lblTelefono.setBounds(302, 141, 117, 14);
		panel.add(lblTelefono);
		lblTelefono.setEnabled(false);
		txtTelefono.setBounds(437, 135, 107, 26);
		txtTelefono.setColumns(10);
		panel.add(txtTelefono);
        txtTelefono.setEnabled(false);
			
		lblMovil.setBounds(302, 168, 46, 14);
		panel.add(lblMovil);
		lblMovil.setEnabled(false);
		txtMovil.setBounds(437, 162, 107, 26);
		txtMovil.setColumns(10);
		panel.add(txtMovil);
        txtMovil.setEnabled(false);
				
		lblExpediente.setBounds(59, 255, 100, 14);
		panel.add(lblExpediente);
        lblExpediente.setEnabled(false);
		txtExpediente.setBounds(160, 249, 107, 26);
		txtExpediente.setColumns(10);
		panel.add(txtExpediente);
        txtExpediente.setEnabled(false);
			
		lblNomDepart.setBounds(339, 255, 130, 14);
		panel.add(lblNomDepart);
		lblNomDepart.setEnabled(false);    
		txtNomDepart.setBounds(482, 249, 107, 26);
		txtNomDepart.setColumns(10);
		panel.add(txtNomDepart);
		txtNomDepart.setEnabled(false);
		
		
		lblAsignatura.setBounds(339, 283, 103, 14);
		panel.add(lblAsignatura);
		lblAsignatura.setEnabled(false);
		txtAsignatura.setBounds(482, 277, 107, 26);
		txtAsignatura.setColumns(10);
		panel.add(txtAsignatura);
        txtAsignatura.setEnabled(false);
		
		
		lblDireccion.setBounds(59, 362, 83, 14);
		panel.add(lblDireccion);
		lblDireccion.setEnabled(false);     
		txtDireccion.setBounds(141, 356, 107, 26);
		txtDireccion.setColumns(10);
		panel.add(txtDireccion);		
		txtDireccion.setEnabled(false);
		
		familiarTrabajando.setFocusPainted(false);
		familiarTrabajando.setBounds(59, 325, 164, 29);
		panel.add(familiarTrabajando);	
		familiarTrabajando.setEnabled(false);
		
		btnRegistrar.setToolTipText("");
		btnRegistrar.setBounds(224, 393, 123, 34);
		panel.add(btnRegistrar);
		btnRegistrar.setFocusPainted(false);
		
		rdbtnEstudiante.setBounds(25, 224, 109, 23);
		rdbtnEstudiante.setFocusPainted(false);
		panel.add(rdbtnEstudiante);
		rdbtnEstudiante.setEnabled(false);
		
		rdbtnProfesores.setBounds(302, 224, 109, 23);
		rdbtnProfesores.setFocusPainted(false);
		panel.add(rdbtnProfesores);
		rdbtnProfesores.setEnabled(false);
		
		rdbtnGeneral.setBounds(25, 306, 107, 20);
		rdbtnGeneral.setFocusPainted(false);
		panel.add(rdbtnGeneral);
		rdbtnGeneral.setEnabled(false);
		
		tipoUsuario.add(rdbtnEstudiante);
		tipoUsuario.add(rdbtnProfesores);
		tipoUsuario.add(rdbtnGeneral);
		  
		rdbtnUsuario.setFocusPainted(false);
		rdbtnUsuario.setFont(new Font("SansSerif", Font.ITALIC, 14));
		rdbtnUsuario.setBounds(141, 60, 126, 29);
		panel.add(rdbtnUsuario);
		rdbtnBibliotecario.setFocusPainted(false);
		rdbtnBibliotecario.setFont(new Font("SansSerif", Font.ITALIC, 14));
		rdbtnBibliotecario.setBounds(313, 60, 156, 29);
		panel.add(rdbtnBibliotecario);
		
		usuarioBibliotecario.add(rdbtnUsuario);
		usuarioBibliotecario.add(rdbtnBibliotecario);
		btnSalir.setFocusPainted(false);
		
		btnSalir.setBounds(517, 401, 90, 26);
		panel.add(btnSalir);
		
		
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ventanaRegistro.dispose();			
			}			
		});
		
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				realizarRegistro();			
			}			
		});
		
		rdbtnBibliotecario.addActionListener(new java.awt.event.ActionListener() {
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	        	limpiarCampos();
	        	lblDni.setEnabled(true);
	        	txtDni.setEnabled(true);
	        	lblContrasenia.setEnabled(true);
	        	passwordContrasenia.setEnabled(true);
	        	lblNombre.setEnabled(true);
	        	txtNombre.setEnabled(true);
	        	lblApellidos.setEnabled(true);
	        	txtApellido.setEnabled(true);
	        	lblNacimiento.setEnabled(false);
	        	txtNacimiento.setEnabled(false);
	        	txtNacimiento.setText("");
	        	lblTelefono.setEnabled(false);
	        	txtTelefono.setEnabled(false);
	        	txtTelefono.setText("");
	        	lblMovil.setEnabled(false);
	        	txtMovil.setEnabled(false);
	        	txtMovil.setText("");
	        	lblExpediente.setEnabled(false);
	        	txtExpediente.setEnabled(false);
	        	lblNomDepart.setEnabled(false);
	        	txtNomDepart.setEnabled(false);
	        	lblAsignatura.setEnabled(false);
	        	txtAsignatura.setEnabled(false);
	        	lblDireccion.setEnabled(false);
	        	txtDireccion.setEnabled(false);
	        	familiarTrabajando.setEnabled(false);
	        	rdbtnEstudiante.setEnabled(false);
	        	rdbtnProfesores.setEnabled(false);
	        	rdbtnGeneral.setEnabled(false);
	        }
	    });
		
		rdbtnUsuario.addActionListener(new java.awt.event.ActionListener() {
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	        	lblDni.setEnabled(true);
	        	txtDni.setEnabled(true);
	        	lblContrasenia.setEnabled(true);
	        	passwordContrasenia.setEnabled(true);
	        	lblNombre.setEnabled(true);
	        	txtNombre.setEnabled(true);
	        	lblApellidos.setEnabled(true);
	        	txtApellido.setEnabled(true);
	        	lblNacimiento.setEnabled(true);
	        	txtNacimiento.setEnabled(true);
	        	lblTelefono.setEnabled(true);
	        	txtTelefono.setEnabled(true);
	        	lblMovil.setEnabled(true);
	        	txtMovil.setEnabled(true);
	        	rdbtnEstudiante.setEnabled(true);
	        	rdbtnProfesores.setEnabled(true);
	        	rdbtnGeneral.setEnabled(true);
	        }
		});
		
		rdbtnEstudiante.addActionListener(new java.awt.event.ActionListener() {
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	        	limpiarCampos();
	        	lblExpediente.setEnabled(true);
	        	txtExpediente.setEnabled(true);
	        	lblNomDepart.setEnabled(false);
	        	txtNomDepart.setEnabled(false);
	        	lblAsignatura.setEnabled(false);
	        	txtAsignatura.setEnabled(false);
	        	lblDireccion.setEnabled(false);
	        	txtDireccion.setEnabled(false);
	        	familiarTrabajando.setEnabled(false);
	        }
		});
		
		rdbtnProfesores.addActionListener(new java.awt.event.ActionListener() {
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	        	limpiarCampos();
	        	lblExpediente.setEnabled(false);
	        	txtExpediente.setEnabled(false);
	        	lblNomDepart.setEnabled(true);
	        	txtNomDepart.setEnabled(true);
	        	lblAsignatura.setEnabled(true);
	        	txtAsignatura.setEnabled(true);
	        	lblDireccion.setEnabled(false);
	        	txtDireccion.setEnabled(false);
	        	familiarTrabajando.setEnabled(false);
	        }
		});
		
		rdbtnGeneral.addActionListener(new java.awt.event.ActionListener() {
	        public void actionPerformed(java.awt.event.ActionEvent evt) {
	        	limpiarCampos();
	        	lblExpediente.setEnabled(false);
	        	txtExpediente.setEnabled(false);
	        	lblNomDepart.setEnabled(false);
	        	txtNomDepart.setEnabled(false);
	        	lblAsignatura.setEnabled(false);
	        	txtAsignatura.setEnabled(false);
	        	lblDireccion.setEnabled(true);
	        	txtDireccion.setEnabled(true);
	        	familiarTrabajando.setEnabled(true);
	        }
		});
	}
	
	public void realizarRegistro(){
		if(rdbtnBibliotecario.isSelected()){
			if(txtDni.getText().equals("") || txtNombre.getText().equals("") || txtApellido.getText().equals("") || 
					passwordContrasenia.getPassword().equals("")){
				ErrorDialog error = new ErrorDialog(ventanaRegistro, "¡Rellena todos los campos!");
				error.pack();
				error.setVisible(true);
			}
			else{
				Bibliotecario bib = new Bibliotecario (txtDni.getText(),txtNombre.getText(),
						txtApellido.getText(), String.valueOf(passwordContrasenia.getPassword()));
				bib.insertarBibliotecario(con,"biblioteca",bib);
				ErrorDialog noUsuDocu = new ErrorDialog(ventanaRegistro, "Registro Realizado");
				noUsuDocu.pack();
				noUsuDocu.setVisible(true);
				ventanaRegistro.setVisible(false);
			}
		}
		
		if(rdbtnUsuario.isSelected() && rdbtnEstudiante.isSelected()){
			if(txtDni.getText().equals("") || txtNombre.getText().equals("") || txtApellido.getText().equals("") || txtTelefono.getText().equals("") ||
					txtMovil.getText().equals("") || txtNacimiento.getText().equals("") || txtExpediente.getText().equals("") ||
					passwordContrasenia.getPassword().equals("")){
				ErrorDialog error = new ErrorDialog(ventanaRegistro, "¡Rellena todos los campos!");
				error.pack();
				error.setVisible(true);
			}
			else{
				Usuario usu = new Usuario (txtDni.getText(),txtNombre.getText(),txtApellido.getText(),Integer.parseInt(txtTelefono.getText()),
						Integer.parseInt(txtMovil.getText()),txtNacimiento.getText(),txtExpediente.getText(),String.valueOf(passwordContrasenia.getPassword()));
				usu.insertarUsuario(con,"biblioteca",usu);
				ErrorDialog noUsuDocu = new ErrorDialog(ventanaRegistro, "Registro Realizado");
				noUsuDocu.pack();
				noUsuDocu.setVisible(true);
				ventanaRegistro.setVisible(false);
			}
		}
		
		if(rdbtnUsuario.isSelected() && rdbtnProfesores.isSelected()){
			if(txtDni.getText().equals("") || txtNombre.getText().equals("") || txtApellido.getText().equals("") || txtTelefono.getText().equals("") ||
					txtMovil.getText().equals("") || txtNacimiento.getText().equals("") || txtNomDepart.getText().equals("") ||
					txtAsignatura.getText().equals("") || passwordContrasenia.getPassword().equals("")){
				ErrorDialog error = new ErrorDialog(ventanaRegistro, "¡Rellena todos los campos!");
				error.pack();
				error.setVisible(true);
			}
			else{
				Usuario usu = new Usuario (txtDni.getText(),txtNombre.getText(),txtApellido.getText(),Integer.parseInt(txtTelefono.getText()),
						Integer.parseInt(txtMovil.getText()),txtNacimiento.getText(),txtNomDepart.getText(),txtAsignatura.getText(),
						String.valueOf(passwordContrasenia.getPassword()));
				usu.insertarUsuario(con,"biblioteca",usu);
	
				ErrorDialog noUsuDocu = new ErrorDialog(ventanaRegistro, "Registro Realizado");
				noUsuDocu.pack();
				noUsuDocu.setVisible(true);
				ventanaRegistro.setVisible(false);
			}
		}
		
		if(rdbtnUsuario.isSelected() && rdbtnGeneral.isSelected()){
			if(txtDni.getText().equals("") || txtNombre.getText().equals("") || txtApellido.getText().equals("") || txtTelefono.getText().equals("") ||
					txtMovil.getText().equals("") || txtNacimiento.getText().equals("") || txtDireccion.getText().equals("") ||
					passwordContrasenia.getPassword().equals("")){
				ErrorDialog error = new ErrorDialog(ventanaRegistro, "¡Rellena todos los campos!");
				error.pack();
				error.setVisible(true);
			}
			else{
				if(familiarTrabajando.isSelected()){
					Usuario usu = new Usuario (txtDni.getText(),txtNombre.getText(),txtApellido.getText(),Integer.parseInt(txtTelefono.getText()),
							Integer.parseInt(txtMovil.getText()),txtNacimiento.getText(),true,txtDireccion.getText(),String.valueOf(passwordContrasenia.getPassword()));
					usu.insertarUsuario(con,"biblioteca",usu);
				}
				else{
					Usuario usu = new Usuario (txtDni.getText(),txtNombre.getText(),txtApellido.getText(),Integer.parseInt(txtTelefono.getText()),
							Integer.parseInt(txtMovil.getText()),txtNacimiento.getText(),false,txtDireccion.getText(),String.valueOf(passwordContrasenia.getPassword()));
					usu.insertarUsuario(con,"biblioteca",usu);
				}
				ErrorDialog noUsuDocu = new ErrorDialog(ventanaRegistro, "Registro Realizado");
				noUsuDocu.pack();
				noUsuDocu.setVisible(true);
				ventanaRegistro.setVisible(false);
			}
		}
	}
	
	public void limpiarCampos(){
		txtExpediente.setText("");
		txtNomDepart.setText("");
		txtAsignatura.setText("");
		txtDireccion.setText("");
		familiarTrabajando.setSelected(false);
	}
}
