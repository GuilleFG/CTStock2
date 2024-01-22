package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.io.IOException;
import java.sql.*;
import application.datos.*;

public class PrincipalController {

	@FXML
	private TableView<ProductoDTO> tablaProductos;

	@FXML
	private TableColumn<ProductoDTO, Double> precio;

	@FXML
	private TableColumn<ProductoDTO, String> nombre;

	@FXML
	private TableColumn<ProductoDTO, String> categoria;

	@FXML
	private TableColumn<ProductoDTO, String> proveedor;

	@FXML
	private TableColumn<ProductoDTO, String> accion;

	@FXML
	private Button productosButton, proveedoresButton,categoriasButton, proveedorAddButton, productoAddButton, cancelarButton;


	private ObservableList<ProductoDTO> list = FXCollections.observableArrayList();
	
	
	
	public void categoriasButtonOnAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/views/categoriaLista.fxml"));

			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(new Scene(root, 296, 411));
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
			e.getCause();
		}

	}
	public void proveedoresButtonOnAction(ActionEvent event) {
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("/views/proveedorLista.fxml"));

			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(new Scene(root, 607, 411));
			stage.show();
		

		} catch (IOException e) {
			e.printStackTrace();
			e.getCause();
		}

	}
	
		
	


	public void productoAddButtonOnAction(ActionEvent e) {

		addProduct();
	}


	
	// Accion del boton productos que lista todos los productos existentes

	public void productosButtonOnAction(ActionEvent e) {
		DatabaseConnection conectNow = new DatabaseConnection();

		Connection conectDb = conectNow.getConnection();

		try {

			// Borramos el contenido actual de la tabla para que no se dupliquen los datos
			// al volver a dar a productos
			tablaProductos.getItems().clear();

			nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
			precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
			categoria.setCellValueFactory(new PropertyValueFactory<>("categoriaNombre"));
			proveedor.setCellValueFactory(new PropertyValueFactory<>("proveedorNombre"));
			
			String listarProductos = "Select productos.nombre, precio , categorias.nombre, proveedores.nombre from productos,"
					+ " categorias, proveedores where productos.idCategoria	= categorias.idcategoria and productos.idProveedor= proveedores.idProveedor;";

			PreparedStatement statement = conectDb.prepareStatement(listarProductos);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {

				// Crear un nueva lista de ProductoDTO con los datos obtenidos
				list.add(new ProductoDTO(rs.getString("nombre"), rs.getDouble("precio"),
						rs.getString("proveedores.nombre"), rs.getString("categorias.nombre")));

				// agregar lista a la tabla productos ;
				tablaProductos.setItems(list);

			}

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		// Establecer las propiedades de las columnas de botones
		accion.setCellValueFactory(new PropertyValueFactory<>("botonesContainer"));
		accion.setCellFactory(param -> new TableCell<>() {
			@Override
			protected void updateItem(String item, boolean empty) {

				if (empty) {
					setGraphic(null);
				} else {
					ProductoDTO producto = getTableView().getItems().get(getIndex());

					// Creamos contenedor para ambos botones
					HBox botonesContainer = new HBox(producto.getEliminarButton(), producto.getEditarButton());
					botonesContainer.setSpacing(5);
					setGraphic(botonesContainer);

					// Creamos evento en boton eliminar
					producto.getEliminarButton().setOnMouseClicked((MouseEvent event) -> {
						try {
//							//Creamos una ventana de confirmacion 	
							Alert confirm = new Alert(AlertType.CONFIRMATION);
							confirm.setHeaderText(null);
							confirm.setContentText("Estás seguro de que quieres eliminar el producto: " + producto.getNombre() + "?");
							ButtonType cancelar = new ButtonType("Cancelar");
							ButtonType aceptar = new ButtonType("Aceptar");
							confirm.getButtonTypes().setAll(cancelar, aceptar);
							ButtonType result = confirm.showAndWait().orElse(ButtonType.CANCEL);

							if (result == aceptar) {

								// Elimina el producto de la base de datos
								String delete = "DELETE FROM productos WHERE nombre  = '" + producto.getNombre() + "'";
								PreparedStatement st = conectDb.prepareStatement(delete);
								st.execute();

								// Eliminamos el producto de la lista y actualizamos la tabla
								list.remove(producto);
								tablaProductos.setItems(list);
							}

						} catch (Exception e2) {
							e2.printStackTrace();
						}
					});

				}
			}
		});
	}

	// metodo que nos lleva a la ventana de agregar prductos
	public void addProduct() {

		try {
			Parent root = FXMLLoader.load(getClass().getResource("/views/addProduct.fxml"));

			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(new Scene(root, 679, 433));
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
			e.getCause();
		}

	}



}