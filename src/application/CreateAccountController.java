package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateAccountController {

	@FXML
	private TextField apellidoTextField;

	@FXML
	private Button cancelarButton;

	@FXML
	private Label messageLabel;

    @FXML
    private Label messagePassword;

	@FXML
	private TextField nombreTextField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Button registrarseButton;

	@FXML
	private PasswordField repetirPasswordField;

	@FXML
	private TextField usuarioTextField;

	@FXML
	void registrarseButtonOnAction(ActionEvent event) {
		if (passwordField.getText().equals(repetirPasswordField.getText())) {
			messagePassword.setText("");
			registrarUsuario();

		} else {
			messagePassword.setText("Las contraseñas no coinciden");

		}

	}

	public void registrarUsuario() {
		DatabaseConnection connectNow = new DatabaseConnection();
		Connection connectDb = connectNow.getConnection();
		
		String nombre = nombreTextField.getText();
		String apellido = apellidoTextField.getText();
		String usuario = usuarioTextField.getText();
		String password = passwordField.getText();
		
		String insertarCampos = "INSERT INTO cuentasusuario (nombre, apellido, usuario, password) VALUES ('";
		String insertarValores = nombre +"','"+ apellido+"','"+usuario +"','"+ password+"')";
		String insertarRegistro = insertarCampos + insertarValores;
		
		try {
			
			Statement st = connectDb.createStatement();
			st.executeUpdate(insertarRegistro);
			messageLabel.setText("Registro de usuario realizado con éxito");
			
		} catch (SQLException e) {
			e.printStackTrace();
			e.getCause();
			if (e.getSQLState().startsWith("23")) {
 	            messageLabel.setText("Usuario ya existe.");
 	        } else {
 	            messageLabel.setText("Error al crear nuevo usuario.");
 	        }
		}
		
	}

	@FXML
	void cancelarButtonOnAction(ActionEvent event) {
		Stage stage = (Stage) cancelarButton.getScene().getWindow();
		stage.close();

	}
}