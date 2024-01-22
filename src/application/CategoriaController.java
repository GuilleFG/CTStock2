package application;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import application.datos.CategoriaDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CategoriaController implements Initializable {
	
	
	@FXML
	private TableView<CategoriaDTO> categoriaTableView;
	@FXML
	private TableColumn<CategoriaDTO, String> nombreColumn;
	@FXML
	private TableColumn<CategoriaDTO, String> accionColumn;
	@FXML
	private Button cancelarButton, aceptarButton, categoriaAddButton;

	private ObservableList<CategoriaDTO> list = FXCollections.observableArrayList();
	
	

	public void listarCategorias() {
		try {
			DatabaseConnection conectNow = new DatabaseConnection();

			Connection conectDb = conectNow.getConnection();

			//categoriaTableView.getItems().clear();

			nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));

			String listar = "SELECT nombre FROM categorias";

			PreparedStatement statement = conectDb.prepareStatement(listar);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {

				// Crear un nueva lista de CategoriaDTO con los datos obtenidos
				list.add(new CategoriaDTO(rs.getString("categorias.nombre")));

				// agregar lista a la tabla categoria ;
				categoriaTableView.setItems(list);

			}

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
//		// Establecer las propiedades de las columnas de botones
		accionColumn.setCellValueFactory(new PropertyValueFactory<>("botonesContainer"));
		accionColumn.setCellFactory(param -> new TableCell<>() {
			@Override
			protected void updateItem(String item, boolean empty) {

				if (empty) {
					setGraphic(null);
				} else {
					CategoriaDTO categoria = getTableView().getItems().get(getIndex());

					// Creamos contenedor para ambos botones
					HBox botonesContainer = new HBox(categoria.getEliminarButton(), categoria.getEditarButton());
					botonesContainer.setSpacing(5);
					setGraphic(botonesContainer);
					// Creamos evento en boton eliminar
					categoria.getEditarButton().setOnMouseClicked((MouseEvent event) -> {
					    try {
					    	cargarUpdateCategoria(categoria.getNombre());
					    	Stage stage = (Stage) cancelarButton.getScene().getWindow();
							stage.close();
							
						} catch (Exception e) {
							e.printStackTrace();
							
						}
				
					});
					categoria.getEliminarButton().setOnMouseClicked((MouseEvent event) -> {
						try {
							// Creamos una ventana de confirmacion para eliminar 
							Alert confirm = new Alert(AlertType.CONFIRMATION);
							confirm.setHeaderText(null);
							confirm.setContentText(
									"Estás seguro de que quieres eliminar el producto: " + categoria.getNombre() + "?");
							ButtonType cancelar = new ButtonType("Cancelar");
							ButtonType aceptar = new ButtonType("Aceptar");
							confirm.getButtonTypes().setAll(cancelar, aceptar);
							ButtonType result = confirm.showAndWait().orElse(ButtonType.CANCEL);

							if (result == aceptar) {

								// Elimina el producto de la base de datos
								String delete = "DELETE FROM categorias WHERE nombre  = '" + categoria.getNombre()
										+ "'";
								DatabaseConnection conectNow = new DatabaseConnection();

								Connection conectDb = conectNow.getConnection();
								PreparedStatement st = conectDb.prepareStatement(delete);
								st.execute();

								// Eliminamos el categoria de la lista y actualizamos la tabla
								list.remove(categoria);
								categoriaTableView.setItems(list);

							}
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					});
					
				}
			}
		});
	}
	
	public void categoriaAddButtonOnAction(ActionEvent e) {

		addCategoria();
		
	}
	
	public void addCategoria() {

		try {
			Parent root = FXMLLoader.load(getClass().getResource("/views/addCategoria.fxml"));

			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(new Scene(root, 469, 304));
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
			e.getCause();
		}

	}
	public void cargarUpdateCategoria(String nombreCategoria) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/updateCategoria.fxml"));
	        Parent root = loader.load();
	        
	      CategoriaUpdateController categoriaUpdateController = loader.getController();

	        // Llamamos al método en el controlador de la nueva vista para pasar el nombre de la categoría
	        categoriaUpdateController.actualizarNombreCategoria(nombreCategoria);
	        Stage stage = new Stage();
	        stage.initStyle(StageStyle.UNDECORATED);
	        stage.setScene(new Scene(root, 426, 373));
	        stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	public void cancelarButtonOnAction(ActionEvent e) {
		Stage stage = (Stage) cancelarButton.getScene().getWindow();
		stage.close();

	}

	
	
	

		
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listarCategorias();
	}

}
