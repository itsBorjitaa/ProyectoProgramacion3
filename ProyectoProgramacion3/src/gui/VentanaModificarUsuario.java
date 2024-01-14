package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import main.BaseDatos;

public class VentanaModificarUsuario extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField nombre, contrasenya;
	private JFrame vActual;
	private Logger logger = Logger.getLogger(VentanaPrincipal.class.getName());
	public static Connection con;
	
	private static final String RUTA_DB = "resources/db/BaseDatos.db";
	
	public VentanaModificarUsuario(String usuario) {
		String usuarioActual=usuario;
		con = BaseDatos.initBD(RUTA_DB);
		vActual = this;

        // Crear componentes
        JLabel nombreLabel = new JLabel("Nuevo Nombre:");
        JLabel contrasenyaLabel = new JLabel("Nueva Contraseña:");

        nombre = new JTextField(20);
        contrasenya = new JTextField(20);

        JButton modificarNombre = new JButton("Modificar Nombre");
        JButton modificarContrasenya = new JButton("Modificar Contraseña");
        JButton botonEliminarUsuario = new JButton("Eliminar Usuario");
        JButton volverButton = new JButton("Volver a la Ventana Principal");

        // Configurar el diseño de la ventana
        setLayout(new GridLayout(5, 2, 10, 10));

        // Agregar componentes a la ventana
        add(nombreLabel);
        add(nombre);
        add(contrasenyaLabel);
        add(contrasenya);
        add(modificarNombre);
        add(modificarContrasenya);
        add(botonEliminarUsuario);
        add(volverButton);

        modificarNombre.addActionListener((e)->{
        	String nuevoNombre = nombre.getText();
        	if (!nuevoNombre.isEmpty()) {
        		BaseDatos.modificarNombreUsuarioBD(con, usuarioActual, nuevoNombre);
        		JOptionPane.showMessageDialog(vActual, "Nombre modificado correctamente.");
        	} else {
        		JOptionPane.showMessageDialog(vActual, "Ingresa un nuevo nombre.");
        	}
        });

        modificarContrasenya.addActionListener((e)->{
        	String nuevaContrasenya = contrasenya.getText();
        	if (!nuevaContrasenya.isEmpty()) {
        		BaseDatos.modificarContrasenyaUsuarioBD(con, usuarioActual, nuevaContrasenya);
        		JOptionPane.showMessageDialog(vActual, "Contraseña modificada correctamente.");
        	} else {
        		JOptionPane.showMessageDialog(vActual, "Ingresa una nueva contraseña.");
        	}
        });

        botonEliminarUsuario.addActionListener((e)->{
			int opcion = JOptionPane.showConfirmDialog(vActual, "¿Estás seguro de querer eliminar tu usuario?", "ELIMINAR USUARIO", JOptionPane.YES_NO_OPTION);
            
            if (opcion == JOptionPane.YES_OPTION) {
            	logger.info("Usuario eliminado correctamente");
            	//Llamada al método de eliminar usuario
            	BaseDatos.eliminarUsuarioBD(con, usuarioActual);
            	//Volver a VentanaLogin
            	new VentanaInicioSesion();
            	vActual.dispose();
            } else {
            	logger.info("Operación cancelada");
            }
		});

        volverButton.addActionListener((e)->{
            new VentanaPrincipal(usuario);
            vActual.dispose();
            
        });
		
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(450, 300, 600, 400);
		setTitle("DeustoFinanzas");
		setVisible(true);

    }
}