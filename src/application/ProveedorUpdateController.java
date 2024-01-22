package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import application.datos.ProveedorDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProveedorUpdateController {

	@FXML
	private Button aceptarButton;

	@FXML
	private Button cancelarButton;

	@FXML
	private TextField contactoTextField;

	@FXML
	private TextField emailTextField;

	@FXML
	private TextField nombreTextField;

	@FXML
	private Label proveedorLabel,mensajeLabel;

	@FXML
	private TextField telefonoTextField;

	private String nombre;
	private String email;
	private String telefono;
	private String contacto;
	private ProveedorDTO proveedor;

	@FXML
	void aceptarButtonOnAction(ActionEvent event) {
		if (nombreTextField.getText().isBlank()==true || telefonoTextField.getText().isBlank()== true) {
			mensajeLabel.setText("Los campos nombre y teléfono no pueden estar en blanco");
			
		} else {
			ProveedorDTO proveedorNuevo = new ProveedorDTO(nombreTextField.getText(), telefonoTextField.getText(),emailTextField.getText(), contactoTextField.getText());
			updateProveedor(proveedor, proveedorNuevo);
			mensajeLabel.setText("Proveedor modificado correcatmente!");
		}
		
	}

	@FXML
	public void cancelarButtonOnAction(ActionEvent e) {
		Stage stage = (Stage) cancelarButton.getScene().getWindow();
		stage.close();

	}
	// metodo que usamos desde el otro controlador para recoger los datos del proveedor a modificar
	public void actualizarProveedor(ProveedorDTO proveedor) {
		this.proveedor = proveedor;
		this.nombre = proveedor.getNombre();
		this.email = proveedor.getEmail();
		this.telefono = proveedor.getTelefono();
		this.contacto = proveedor.getContacto();
		proveedorLabel.setText("Editar proveedor: " + proveedor.getNombre());
		nombreTextField.setText(nombre);
		emailTextField.setText(email);
		telefonoTextField.setText(telefono);
		contactoTextField.setText(contacto);

	}
	public void updateProveedor(ProveedorDTO nuevo, ProveedorDTO anterior) {
		try {
			DatabaseConnection conectNow = new DatabaseConnection();
	        Connection conectDb = conectNow.getConnection();
	        
	        String updateProveedor = "UPDATE proveedores SET nombre = ?, telefono = ?, mail = ? , contacto = ? WHERE nombre = ?";
			PreparedStatement st = conectDb.prepareStatement(updateProveedor);
			
			st.setString(1, nombreTextField.getText());
			st.setString(2, telefonoTextField.getText());
			st.setString(3, emailTextField.getText());
			st.setString(4, contactoTextField.getText());
			st.setString(5, nombre);
			st.executeUpdate();
		
			
	        
	        
	        
		} catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("No se ha podido editar el proveedor");
        }
		
		
		
		
}
	


}
