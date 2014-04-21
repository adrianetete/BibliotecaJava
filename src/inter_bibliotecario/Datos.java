package inter_bibliotecario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Datos {
	
	public Datos() {
		colocar();
		acciones();
		System.out.println("Ventana Datos abierta.");
	}
	
	/* * * * * * * * * * * Ventana * * * * * * * * * */
	private JFrame ventana = new JFrame("Bibliotecario - Datos Personales");
	
	/* * * * * * * * Panel Principal * * * * * * * * */
	private JPanel panel1 = new JPanel();
		private JLabel lblconectado = new JLabel("Conectado como:");
		private JLabel txtNombre_titulo = new JLabel(GUI_Principal.actual.getNombre() + " " + GUI_Principal.actual.getApellido());
	
	
	/* * * * * * * * * * * * Panel 2 * * * * * * * * * * * */
	private JPanel panel2 = new JPanel();
		private JButton volver = new JButton("<- Volver");
		
	/* * * * * * * * Panel 3 * * * * * * * * * * * * */
	private JPanel panel3 = new JPanel();	
		private JLabel lblDatosPersonales = new JLabel("Datos personales");
		private JLabel lblDNI = new JLabel("DNI:");
		private JLabel txtDNI = new JLabel(GUI_Principal.actual.getDNI());
		private JLabel lblNombre = new JLabel("Nombre:");
		private JLabel txtNombre = new JLabel(GUI_Principal.actual.getNombre());
		private JLabel lblApellidos = new JLabel("Apellidos:");
		private JLabel txtApellidos = new JLabel(GUI_Principal.actual.getApellido());
		private final JLabel lblTipo = new JLabel("Tipo:");
		private final JLabel txtTipo = new JLabel("Bibliotecario");
		
	/** Metodo que hace visible la ventana */
	public void Mostrar(){
		ventana.setVisible(true);
	}
	
	private void colocar() {
		
		/* * * * * * * * * * * * Ventana * * * * * * * * * */
		ventana.setBounds(460, 330, 393, 391);
		ventana.getContentPane().setLayout(null);
		ventana.setResizable(false);
				
		/* * * * * * * * * Panel Principal * * * * * * * * */
		panel1.setLayout(null);
		panel1.setBorder(UIManager.getBorder("PasswordField.border"));
		panel1.setBounds(10, 66, 368, 33);
				
			lblconectado.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
			lblconectado.setBounds(10, 8, 142, 17);
			panel1.add(lblconectado);
			txtNombre_titulo.setHorizontalAlignment(SwingConstants.LEFT);

			txtNombre_titulo.setFont(new Font("MS Reference Sans Serif", Font.ITALIC, 14));
			txtNombre_titulo.setBounds(162, 6, 196, 20);
			panel1.add(txtNombre_titulo);		
		ventana.getContentPane().add(panel1);
		
		/* * * * * * * * * * * * Panel 2 * * * * * * * * * * * */
		panel2.setBounds(10, 11, 368, 44);		
		panel2.setLayout(null);
		panel2.setBorder(UIManager.getBorder("PasswordField.border"));
			
			//boton volver
			volver.setBounds(10, 11, 89, 23);
			panel2.add(volver);
		ventana.getContentPane().add(panel2);
		
		JLabel lblEstosSonTus = new JLabel("Estos son tus datos:");
		lblEstosSonTus.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
		lblEstosSonTus.setBounds(142, 11, 194, 18);
		panel2.add(lblEstosSonTus);

		/* * * * * * * * * * * * Panel 3 * * * * * * * * * * * */
		panel3.setLayout(null);
		panel3.setBorder(UIManager.getBorder("PasswordField.border"));
		panel3.setBounds(10, 110, 368, 244);
			
			//Etiqueta titulo panel 3
			lblDatosPersonales.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblDatosPersonales.setBounds(10, 11, 348, 17);
			panel3.add(lblDatosPersonales);
			
			//Campo DNI
			lblDNI.setBounds(39, 73, 72, 14);
			panel3.add(lblDNI);
			txtDNI.setFont(new Font("Tahoma", Font.BOLD, 11));
			txtDNI.setBounds(184, 73, 174, 14);
			panel3.add(txtDNI);
			
			//Campo Nombre
			lblNombre.setBounds(39, 98, 72, 14);
			panel3.add(lblNombre);
			txtNombre.setFont(new Font("Tahoma", Font.BOLD, 11));
			txtNombre.setBounds(184, 98, 174, 14);
			panel3.add(txtNombre);
			
			//Campo Apellidos
			lblApellidos.setBounds(39, 123, 72, 14);
			panel3.add(lblApellidos);					
			txtApellidos.setFont(new Font("Tahoma", Font.BOLD, 11));
			txtApellidos.setBounds(184, 123, 174, 14);
			panel3.add(txtApellidos);
		ventana.getContentPane().add(panel3);
		
		JLabel lblTienesPermisosComo = new JLabel("Tienes permisos como administrador general sobre");
		lblTienesPermisosComo.setVerticalAlignment(SwingConstants.TOP);
		lblTienesPermisosComo.setHorizontalAlignment(SwingConstants.LEFT);
		lblTienesPermisosComo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTienesPermisosComo.setBounds(10, 164, 336, 17);
		panel3.add(lblTienesPermisosComo);
		
		JLabel lblSobreTodaLa = new JLabel("toda la base de datos de la Biblioteca.");
		lblSobreTodaLa.setVerticalAlignment(SwingConstants.TOP);
		lblSobreTodaLa.setHorizontalAlignment(SwingConstants.LEFT);
		lblSobreTodaLa.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSobreTodaLa.setBounds(10, 186, 299, 17);
		panel3.add(lblSobreTodaLa);
		lblTipo.setBounds(39, 48, 72, 14);
		
		panel3.add(lblTipo);
		txtTipo.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtTipo.setBounds(184, 48, 174, 14);
		
		panel3.add(txtTipo);
	}
	private void acciones() {
		
		volver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent volver) {
				/* cierra la ventana y la quita de memoria, por lo que al
				 * abrirla de nuevo, volvera a ejecutarse toda la ventana*/
				ventana.dispose();
			}			
		});
	}
}
