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
import application.datos.CategoriaDTO;



public class AddCategoriaController {

    @FXML
    private Button addButton,cancelarButton;

    @FXML
    private TextField categoriaTextField;
    
    @FXML
    private Label messageLabel;

    @FXML
    void addButtonOnAction(ActionEvent event) {
    	if (categoriaTextField.getText().isBlank()==true) {
    		messageLabel.setText("El campo no puede estar vacío");
			
		}else {
			addCategoria();
		}
    	
    	
    }
    
    public void addCategoria() {
    	CategoriaDTO categoria = new CategoriaDTO();
    	categoria.setNombre(categoriaTextField.getText());
    	
    	String addCategoria = "INSERT INTO categorias (nombre) VALUES (?)";
    	
    	 try {
         	DatabaseConnection conectNow = new DatabaseConnection();
             Connection conectDb = conectNow.getConnection();
             
             PreparedStatement st = conectDb.prepareStatement(addCategoria);
             st.setString(1, categoria.getNombre());
            
             int affectedRows = st.executeUpdate();
             //System.out.println(affectedRows);
             
             if (affectedRows > 0) {
             	messageLabel.setText("Categoría: " + categoria.getNombre() + " creada correctamente!");
             }else {
             	messageLabel.setText("No se ha podido añadir la nueva categoría");
             }
             
             
             
             
             
 	} catch (SQLException e) {
 			e.printStackTrace();
 			
 			if (e.getSQLState().startsWith("23")) {
 	            messageLabel.setText("Error: La categoría ya existe.");
 	        } else {
 	            messageLabel.setText("Error al añadir la categoría.");
 	        }
 		}
     	
     }
    
    public void cancelarButtonOnAction(ActionEvent e) {
		Stage stage = (Stage) cancelarButton.getScene().getWindow();
		stage.close();

	}
}
