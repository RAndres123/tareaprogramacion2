package formularios;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import conexionsql.Conexionsql;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class sistema extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private JPanel contentPane;
	private JTextField txtcedula;
	private JTextField txtnombre;
	private JTextField txtapellido;
	private JTextField txtsueldo;

	/**
	 * Launch the application.
	 */
	Connection conexion = null;
	PreparedStatement  ps = null;
	ResultSet rs = null;
	private JTextField txtid;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					sistema frame = new sistema();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public sistema() {
		
		this.setLocationRelativeTo(null);
		this.getContentPane().setBackground(getBackground());
		conexion = Conexionsql.conectar();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 305, 323);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(60, 196, 175));
		panel.setBounds(0, 0, 290, 284);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Cedula");
		lblNewLabel.setBounds(29, 107, 46, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre");
		lblNewLabel_1.setBounds(29, 132, 46, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Apellido");
		lblNewLabel_1_1.setBounds(29, 157, 46, 14);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Sueldo");
		lblNewLabel_1_1_1.setBounds(29, 182, 46, 14);
		panel.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Empleados");
		lblNewLabel_1_1_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_1_1_2.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 23));
		lblNewLabel_1_1_2.setBounds(84, 11, 122, 24);
		panel.add(lblNewLabel_1_1_2);
		
		txtcedula = new JTextField();
		txtcedula.setBounds(85, 104, 109, 20);
		panel.add(txtcedula);
		txtcedula.setColumns(10);
		
		txtnombre = new JTextField();
		txtnombre.setColumns(10);
		txtnombre.setBounds(85, 129, 109, 20);
		panel.add(txtnombre);
		
		txtapellido = new JTextField();
		txtapellido.setColumns(10);
		txtapellido.setBounds(85, 154, 109, 20);
		panel.add(txtapellido);
		
		txtsueldo = new JTextField();
		txtsueldo.setColumns(10);
		txtsueldo.setBounds(85, 179, 109, 20);
		panel.add(txtsueldo);
		
		JButton btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarDatos();
			}
		});
		btnNuevo.setBounds(10, 216, 99, 23);
		panel.add(btnNuevo);
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarDatos();
			}
		});
		btnActualizar.setBounds(10, 250, 99, 23);
		panel.add(btnActualizar);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertarDatos();
				limpiarDatos();
			}
		});
		btnGuardar.setBounds(165, 216, 99, 23);
		panel.add(btnGuardar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarDatos();
			}
		});
		btnEliminar.setBounds(165, 250, 99, 23);
		panel.add(btnEliminar);
		
		txtid = new JTextField();
		txtid.setBounds(94, 49, 86, 20);
		panel.add(txtid);
		txtid.setColumns(10);
		
		JLabel ID = new JLabel("ID");
		ID.setBounds(29, 52, 46, 14);
		panel.add(ID);
		
		JButton btnNewButton = new JButton("Buscar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarDatos();
			}
		});
		btnNewButton.setBounds(191, 46, 89, 23);
		panel.add(btnNewButton);
	}
	
	public void limpiarDatos() {
		txtcedula.setText("");
		txtnombre.setText("");
		txtapellido.setText("");
		txtsueldo.setText("");
		txtid.setText("");
	}
	
	
	public void insertarDatos() {
		try {
			ps = conexion.prepareStatement("Insert into empleado (Cedula,Nombre,Apellido,Sueldo) values (?,?,?,?)");
			
			ps.setString(1, txtcedula.getText());
			ps.setString(2, txtnombre.getText());
			ps.setString(3, txtapellido.getText());
			ps.setDouble(4, Double.parseDouble(txtsueldo.getText()));
			
			ps.execute();
			
			JOptionPane.showMessageDialog(null, "Registro Exitoso");
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Registro Fallido "+e.getMessage());
		}
	}
	
	
	public void actualizarDatos() {
		try {
			
			ps = conexion.prepareStatement("UPDATE empleado SET cedula=?,nombre=?,apellido=?,sueldo=? where idempleados=?");
			
			
			ps.setString(1, txtcedula.getText());
			ps.setString(2, txtnombre.getText());
			ps.setString(3, txtapellido.getText());
			ps.setDouble(4, Double.parseDouble(txtsueldo.getText()));
			ps.setString(5, txtid.getText());
			
			ps.execute();
			
			JOptionPane.showMessageDialog(null, "Modificacion Exitosa");
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Modificacion Fallida "+e.getMessage());
		}
	}
	
	public void borrarDatos() {
		try {
			
			ps = conexion.prepareStatement("Delete from empleado where idempleados=?");
			
			ps.setString(1, txtid.getText());
			
			ps.execute();
			
			JOptionPane.showMessageDialog(null, "Registro eliminado correctamente");
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No se pudo eliminar el registro "+e.getMessage());
		}
	}
	
	
	public void buscarDatos() {
		try {
			
			ps = conexion.prepareStatement("Select cedula,nombre,apellido,sueldo from empleado where idempleados=?");
			
			ps.setString(1, txtid.getText());
			
			rs= ps.executeQuery();
			
			if(rs.next()) {
				txtcedula.setText(rs.getString("cedula"));
				txtnombre.setText(rs.getString("nombre"));
				txtapellido.setText(rs.getString("apellido"));
				txtsueldo.setText(rs.getString("sueldo"));
			}else {
			
			JOptionPane.showMessageDialog(null, "Registro no encontrado");
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ocurrio un error al acceder a la base de datos "+e.getMessage());
		}
	}
}
