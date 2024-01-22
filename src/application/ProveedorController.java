package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import application.datos.ProveedorDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProveedorController implements Initializable {

    @FXML
    private TableColumn<ProveedorDTO, String> accionColumn;

    @FXML
    private TableColumn<ProveedorDTO, String> contactoColumn;

    @FXML
    private TableColumn<ProveedorDTO, String> emailColumn;

    @FXML
    private TableColumn<ProveedorDTO, String> nombreColumn;

    @FXML
    private TableView<ProveedorDTO> proveedorTableView;

    @FXML
    private TableColumn<ProveedorDTO, String> telefonoColumn;
    
    @FXML
    private Button cancelarButton, addProveedorButton;
    
	private ObservableList<ProveedorDTO> list = FXCollections.observableArrayList();




	
	public void listarProveedores() {
		try {
			DatabaseConnection conectNow = new DatabaseConnection();
			Connection conectDb = conectNow.getConnection();
			
			proveedorTableView.getItems().clear();
			
			nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
			telefonoColumn.setCellValueFactory(new PropertyValueFactory<>("telefono"));
			emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
			contactoColumn.setCellValueFactory(new PropertyValueFactory<>("contacto"));
			
			String listar = "SELECT nombre, mail, telefono, contacto FROM proveedores ";
			
			PreparedStatement statement = conectDb.prepareStatement(listar);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				ProveedorDTO proveedor = new ProveedorDTO(rs.getString("nombre"),rs.getString("mail"), rs.getString("telefono"), rs.getString("contacto"));
				list.add(proveedor);
				
				proveedorTableView.setItems(list);
				
			}
			
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		accionColumn.setCellValueFactory(new PropertyValueFactory<>("botonesContainer"));
		accionColumn.setCellFactory(param -> new TableCell<>() {
			@Override
			protected void updateItem(String item, boolean empty) {

				if (empty) {
					setGraphic(null);
				} else {
					ProveedorDTO proveedor = getTableView().getItems().get(getIndex());

					// Creamos contenedor para ambos botones
					HBox botonesContainer = new HBox(proveedor.getEliminarButton(), proveedor.getEditarButton());
					botonesContainer.setSpacing(5);
					setGraphic(botonesContainer);
					// Creamos evento en boton eliminar
					proveedor.getEditarButton().setOnMouseClicked((MouseEvent event) -> {
					    try {
					    	cargarUpdateProveedor(proveedor);
					    	Stage stage = (Stage) cancelarButton.getScene().getWindow();
							stage.close();
							
						} catch (Exception e) {
							e.printStackTrace();
							
						}
				
					});
					proveedor.getEliminarButton().setOnMouseClicked((MouseEvent event) -> {
						try {
							// Creamos una ventana de confirmacion para eliminar 
							Alert confirm = new Alert(AlertType.CONFIRMATION);
							confirm.setHeaderText(null);
							confirm.setContentText(
									"Estás seguro de que quieres eliminar el proveedor: " + proveedor.getNombre() + "?");
							ButtonType cancelar = new ButtonType("Cancelar");
							ButtonType aceptar = new ButtonType("Aceptar");
							confirm.getButtonTypes().setAll(cancelar, aceptar);
							ButtonType result = confirm.showAndWait().orElse(ButtonType.CANCEL);

							if (result == aceptar) {

								// Elimina el producto de la base de datos
								String delete = "DELETE FROM proveedores WHERE nombre  = '" + proveedor.getNombre()
										+ "'";
								DatabaseConnection conectNow = new DatabaseConnection();

								Connection conectDb = conectNow.getConnection();
								PreparedStatement st = conectDb.prepareStatement(delete);
								st.execute();

								// Eliminamos el proveedor de la lista y actualizamos la tabla
								list.remove(proveedor);
								proveedorTableView.setItems(list);

							}
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					});
					
				}
			}
		});
	}
	
	public void proveedorAddButtonOnAction(ActionEvent e) {

		addProveedor();
	}
	public void addProveedor() {

		try {
			Parent root = FXMLLoader.load(getClass().getResource("/views/addProveedor.fxml"));

			Stage stage = new Stage();
			//stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(new Scene(root, 679, 433));
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
			e.getCause();
		}

	}
	public void cancelarButtonOnAction(ActionEvent e) {
		Stage stage = (Stage) cancelarButton.getScene().getWindow();
		stage.close();

	}
	public void cargarUpdateProveedor(ProveedorDTO proveedor) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/updateProveedor.fxml"));
	        Parent root = loader.load();
	        
	      ProveedorUpdateController proveedorUpdateController = loader.getController();

	        // Llamamos al método en el controlador de la nueva vista para pasar el nombre de la categoría
	        proveedorUpdateController.actualizarProveedor(proveedor);
	        Stage stage = new Stage();
	        stage.initStyle(StageStyle.UNDECORATED);
	        stage.setScene(new Scene(root, 670, 472));
	        stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
	
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listarProveedores();
		
	}

}
