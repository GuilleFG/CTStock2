package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CategoriaUpdateController {
	
	@FXML
	private TextField nuevoTextField;
	
	@FXML
	private Label categoriaLabel;
	@FXML
	private Label mensajeLabel;
	
	@FXML
	private Button aceptarButton, cancelarButton;
	
	String nombre;
	
	
	@FXML
	public void aceptarButtonOnAction(ActionEvent event) {
		if (nuevoTextField.getText().isBlank()==true) {
			mensajeLabel.setText("El nombre no puede estar en blanco");
			
		} else {
			updateCategoria(nuevoTextField.getText(), nombre);
			mensajeLabel.setText("Categoría modificada correctamente!");
			

		}
	}
	
	public void actualizarNombreCategoria(String nombreCategoria) {
        	this.nombre = nombreCategoria;
			categoriaLabel.setText("Editar Categoría: "+nombreCategoria);
			nuevoTextField.setText(nombreCategoria);
    }
	
	public void updateCategoria(String nombreNuevo, String nombreAnterior) {
		try {
			
		DatabaseConnection conectNow = new DatabaseConnection();
        Connection conectDb = conectNow.getConnection();
        
		String updateCategoria =  "UPDATE categorias SET nombre = ? WHERE nombre = ?";
		
		PreparedStatement st = conectDb.prepareStatement(updateCategoria);
		st.setString(1, nombreNuevo);
		st.setString(2, nombreAnterior);
		st.executeUpdate();
		
		
		
		
		} catch (SQLException throwables) {
            throwables.printStackTrace();
		}
	}
	
	
	
		public void cancelarButtonOnAction(ActionEvent e) {
			Stage stage = (Stage) cancelarButton.getScene().getWindow();
			stage.close();

		}

}
		
	
	

