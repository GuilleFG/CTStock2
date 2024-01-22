package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import application.datos.ProveedorDTO;
public class AddProveedorController {

    @FXML
    private TextField contactoTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField nombreTextField;

    @FXML
    private TextField telefonoTextField;
    
    @FXML
    private Label messageLabel;
    
    @FXML
    private Button addButton, cancelarButton;
    
    
    public void addButtonOnAction() {
    	addProveedor();
    }

    
    public void addProveedor() {
    	ProveedorDTO proveedor = new ProveedorDTO();
    	proveedor.setNombre(nombreTextField.getText());
    	proveedor.setTelefono(telefonoTextField.getText());
    	proveedor.setEmail(emailTextField.getText());
    	proveedor.setContacto(contactoTextField.getText());
    	
    	String addProveedor = "INSERT INTO proveedores (nombre, telefono, mail, contacto) VALUES (?, ?, ?, ?)";
    	
    	 
        try {
        	DatabaseConnection conectNow = new DatabaseConnection();
            Connection conectDb = conectNow.getConnection();
            
            PreparedStatement st = conectDb.prepareStatement(addProveedor);
            st.setString(1, proveedor.getNombre());
            st.setString(2, proveedor.getTelefono());
            st.setString(3, proveedor.getEmail());
            st.setString(4, proveedor.getContacto());
            
            int affectedRows = st.executeUpdate();
            System.out.println(affectedRows);
            
            if (affectedRows > 0) {
            	messageLabel.setText("Proveedor: " + proveedor.getNombre() + " creado correctamente!");
            }else {
            	messageLabel.setText("No se ha podido añadir el nuevo proveedor");
            }
            
            
            
            
            
	} catch (SQLException e) {
			e.printStackTrace();
			if (e.getSQLState().startsWith("23")) {
	            messageLabel.setText("Error: El proveedor ya existe.");
	        } else {
	            messageLabel.setText("Error al añadir el proveedor.");
	        }
		}

    	
    }
    public void cancelarButtonOnAction(ActionEvent e) {
		Stage stage = (Stage) cancelarButton.getScene().getWindow();
		stage.close();

	}
    
}



	
	
	
	
	

