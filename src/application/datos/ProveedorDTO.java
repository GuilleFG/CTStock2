package application.datos;

import java.net.URL;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ProveedorDTO {
	
	private int idProveedor;
	private String nombre;
	private String telefono;
	private String email;
	private String contacto;
	private Button editarButton, eliminarButton;
	private HBox botonesContainer;
	
	
	public ProveedorDTO() {
		
	}
	
	public ProveedorDTO(String nombre) {
		this.nombre = nombre;
	}
	

	public ProveedorDTO(String nombre, String telefono, String email, String contacto) {
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
		this.contacto = contacto;
		this.eliminarButton = new Button();
		this.editarButton = new Button();
		imagenBotones();
	}

	public int getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
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

	public HBox getBotonesContainer() {
		return botonesContainer;
	}

	public void setBotonesContainer(HBox botonesContainer) {
		this.botonesContainer = botonesContainer;
	}
	
	@Override
	public String toString() {
		return "ProveedorDTO [idProveedor=" + idProveedor + ", nombre=" + nombre + ", telefono=" + telefono + ", email="
				+ email + ", contacto=" + contacto + "]";
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
