package Usuario;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ErrorDialog extends JDialog{

	private static final long serialVersionUID = 8689805689403605279L;

	public ErrorDialog(Frame ventana, String texto) {
		super(ventana, texto, true);
		//Titulo para el Dialog
		setTitle("Error");
		
		/* * * * Panel 1 * * * * */
		JPanel panelError = new JPanel();
		panelError.setLayout(new FlowLayout());
			JLabel lblError = new JLabel(texto);
			//añade la etiqueta al panel 1
			panelError.add(lblError);
		//añade el panel a la ventana
		getContentPane().add(panelError);
		
		/* * * * Panel 2 * * * * */
		JPanel panelError2 = new JPanel();
		panelError2.setLayout(new FlowLayout());		
			JButton btnError = new JButton("Aceptar");
			//añade el boton al panel 2
			panelError2.add(btnError);			
		//añade el panel a la ventana
		getContentPane().add(panelError2);	
		
		setBounds(510, 450, 0, 0);
		setLayout(new GridLayout(2, 1));
		setResizable(false);//No deja redimensionarla		
		setMinimumSize(getSize());//Ajusta el tamaño
		
		/* * * * * Colores * * * * * */
		btnError.setForeground(new Color(255, 20, 20));
		btnError.setFocusPainted(false);
		lblError.setForeground(Color.red);
		
		//Accion al hacer click en el Boton
		btnError.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();//cierra la ventana y la borra de memoria	
			}
		});
		
		//Accion al presionar una tecla
		btnError.addKeyListener(new KeyAdapter() {
			//Método que se ejecuta al pulsar cualquier tecla
			public void keyPressed(KeyEvent ke) {
				//Si la tecla pulsada es ENTER
		          if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
		        	  dispose();//cierra la ventana y la borra de memoria	
		          }
		      }
		});
	}
		
}
