package application.datos;

import java.net.URL;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;



public class ProductoDTO {
	
	 
	private int idProducto;
	private String nombre;
	private int cantidadStock;
	private int cantidadAnterior;
	private int idCategoria;
	private int idProveedor;
	private double precio;
	private String proveedorNombre;
	private String categoriaNombre;
	private Button editarButton, eliminarButton;
	private HBox botonesContainer;
	
	

	

	public ProductoDTO() {

	}

	public ProductoDTO(String nombre, double precio) {
		this.nombre = nombre;
		this.precio = precio;
	}

	public ProductoDTO(String nombre, double precio, String proveedorNombre, String categoriaNombre) {
		super();
		this.nombre = nombre;
		this.precio = precio;
		this.proveedorNombre = proveedorNombre;
		this.categoriaNombre = categoriaNombre;
		this.eliminarButton = new Button();
		this.editarButton = new Button();
		imagenBotones();
		
		
	}
	
	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public Button getEditarButton() {
		return editarButton;
	}

	public void setEditarButton(Button editarButton) {
		this.editarButton = editarButton;
	}

	public Button getEliminarButton() {
		return eliminarButton;
	}

	public void setEliminarButton(Button eliminarButton) {
		this.eliminarButton = eliminarButton;
	}

	public String getProveedorNombre() {
		return proveedorNombre;
	}

	public void setProveedorNombre(String proveedorNombre) {
		this.proveedorNombre = proveedorNombre;
	}

	public String getCategoriaNombre() {
		return categoriaNombre;
	}

	
	public void setCategoriaNombre(String categoriaNombre) {
		this.categoriaNombre = categoriaNombre;
	}
	

	public HBox getBotonesContainer() {
		return botonesContainer;
	}

	public void setBotonesContainer(HBox botonesContainer) {
		this.botonesContainer = botonesContainer;
	}


	@Override
	public String toString() {
		return "productoDTO [idProducto=" + idProducto + ", nombre=" + nombre + ", cantidadStock=" + cantidadStock
				+ ", cantidadAnterior=" + cantidadAnterior + ", idCategoria=" + idCategoria + ", idProveedor="
				+ idProveedor + ", precio=" + precio + "]";
	}
	
	public void imagenBotones() {
		URL linkPapelera = getClass().getResource("/img/papelera.png");
		URL linkEditar = getClass().getResource("/img/editar.png");
		Image imagenPapelera = new Image (linkPapelera.toString(),24,24,false, true);
		Image imagenEditar = new Image (linkEditar.toString(),24,24,false, true);
		eliminarButton.setGraphic((new ImageView(imagenPapelera)));
		editarButton.setGraphic((new ImageView(imagenEditar)));
	}

	
	

}
