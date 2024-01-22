package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.PasswordField;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {

	@FXML
	private Button cancelarButton;

	@FXML
	private Button loginButton;

	@FXML
	private Label loginMessageLabel;

	@FXML
	private Label crearLabel;

	@FXML
	private TextField usuarioLoginTextField;
	@FXML
	private PasswordField contrasenaPasswordField;

	public void loginButtonOnAction(ActionEvent e) {
		if (usuarioLoginTextField.getText().isBlank() == false
				&& contrasenaPasswordField.getText().isBlank() == false) {
			validarLogin();

		} else {
			loginMessageLabel.setText("Introduce usuario y contraseña");

		}
	}

	public void validarLogin() {
		DatabaseConnection conectNow = new DatabaseConnection();
		Connection conectDb = conectNow.getConnection();

		// consulta SQL que comprueba si coincide el usuario y contraseña
		String verificarLogin = "SELECT count(1) FROM cuentasusuario WHERE usuario = '"
				+ usuarioLoginTextField.getText() + "' AND password = '" + contrasenaPasswordField.getText() + "'";

		try {

			Statement statement = conectDb.createStatement();
			ResultSet queryResult = statement.executeQuery(verificarLogin);

			while (queryResult.next()) {
				if (queryResult.getInt(1) == 1) {
					loginMessageLabel.setText("Bienvenido!");
					appAceess();

				} else {
					loginMessageLabel.setText("Usuario o contraseña incorrecta");

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Método que cierra la ventana con click en Cancelar
	public void cancelarButtonOnAction(ActionEvent e) {
		Stage stage = (Stage) cancelarButton.getScene().getWindow();
		stage.close();

	}

	@FXML
	public void initialize() {
		// Asignamos un manejador de eventos al Label para el clic
		crearLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				handleCrearLabelClick();
			}
		});
	}

	private void handleCrearLabelClick() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/createAccount.fxml"));
			Parent root = loader.load();
			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(new Scene(root, 580, 579));
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error al cargar la vista de crear cuenta: " + e.getMessage());
		}
	}

	public void appAceess() {

		try {
			Parent root = FXMLLoader.load(getClass().getResource("/views/principal.fxml"));

			Stage mainStage = new Stage();
			// mainStage.initStyle(StageStyle.UNDECORATED);
			mainStage.setScene(new Scene(root, 780, 479));
			mainStage.show();
			// Cerramos la ventana de login
			Stage stage = (Stage) loginButton.getScene().getWindow();
			stage.close();

		} catch (IOException e) {
			e.printStackTrace();
			e.getCause();
		}

	}
}
