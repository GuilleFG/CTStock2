package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import application.datos.ProductoDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddProductController implements Initializable{

    @FXML
    private Button addButton, cancelarButton;
    
    @FXML
    private Label messageLabel;

    @FXML
    private ComboBox<String> categoriaComboBox;

    @FXML
    private TextField precioTextField;

    @FXML
    private TextField productoTextField;

    @FXML
    private ComboBox<String> proveedorComboBox;
    
    
    public void addButtonOnAction() {
    	addProducto();
    	
    }
    
    
    public void addProducto() {
    	
    	 ProductoDTO producto = new ProductoDTO();
    	 producto.setNombre(productoTextField.getText());
    	 producto.setCategoriaNombre(categoriaComboBox.getValue());
    	 producto.setProveedorNombre(proveedorComboBox.getValue());
    	 producto.setPrecio(Double.parseDouble(precioTextField.getText()));
    	 
    	
    	 String addProducto = "INSERT INTO productos(nombre, idcategoria, idproveedor, precio) "
    		        + "VALUES (?, (SELECT idcategoria FROM categorias WHERE nombre = ?),"
    		        + " (SELECT idproveedor FROM proveedores WHERE nombre = ?), ?)";
       
        try {
        	DatabaseConnection conectNow = new DatabaseConnection();
            Connection conectDb = conectNow.getConnection();
            
            PreparedStatement st = conectDb.prepareStatement(addProducto);
            st.setString(1, producto.getNombre());
            st.setString(2, producto.getCategoriaNombre());
            st.setString(3, producto.getProveedorNombre());
            st.setDouble(4, producto.getPrecio());
            
            int affectedRows = st.executeUpdate();
            System.out.println(affectedRows);
            
            if (affectedRows > 0) {
            	messageLabel.setText("Producto creado correctamente!");
            	productoTextField.setText("");
            }else {
            	messageLabel.setText("No se ha podido añadir el producto");
            }
            
            
            
            
            
	} catch (SQLException e) {
			e.printStackTrace();
			
			if (e.getSQLState().startsWith("23")) {
	            messageLabel.setText("Error: EL producto ya existe.");
	        } else {
	            messageLabel.setText("Error al añadir el producto.");
	        }
		}

    	
    }
    
   

    
    public void categoriaComboBox() {
    	DatabaseConnection conectNow = new DatabaseConnection();
        Connection conectDb = conectNow.getConnection();

        String categorias ="SELECT nombre from categorias";

        try {
            PreparedStatement statement = conectDb.prepareStatement(categorias);
            ResultSet rs = statement.executeQuery();

            ObservableList<String>  list = FXCollections.observableArrayList(); 
            while (rs.next()) {
            	String item = rs.getString("nombre");
                list.add(item);
                
            }
            //System.out.println(list);
            categoriaComboBox.setItems(list);
			
		} catch (Exception e) {
			
		}}
    
    public void proveedorComboBox() {
    	DatabaseConnection conectNow = new DatabaseConnection();
        Connection conectDb = conectNow.getConnection();

        String categorias ="SELECT nombre from proveedores";

        try {
            PreparedStatement statement = conectDb.prepareStatement(categorias);
            ResultSet rs = statement.executeQuery();

            ObservableList<String>  list = FXCollections.observableArrayList(); 

            while (rs.next()) {
            	String item = rs.getString("nombre");
                list.add(item);
                
            }
            //System.out.println(list);
            proveedorComboBox.setItems(list);
			
		} catch (Exception e) {

		}}
    
    public void cancelarButtonOnAction(ActionEvent e) {
		Stage stage = (Stage) cancelarButton.getScene().getWindow();
		stage.close();

	}
    
        @Override
    	public void initialize(URL arg0, ResourceBundle arg1) {
        	categoriaComboBox();
        	proveedorComboBox();
    		
    	
    }

	

		
	}