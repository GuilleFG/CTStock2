package application.datos;

import java.net.URL;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class CategoriaDTO {
	
	private int idCategoria;
	private String nombre;
	private Button editarButton, eliminarButton;
	private HBox botonesContainer;
	
	
	public CategoriaDTO() {
		
	}
	
	public CategoriaDTO(String nombre) {
		this.nombre = nombre;
		this.eliminarButton = new Button();
		this.editarButton = new Button();
		imagenBotones();
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public HBox getBotonesContainer() {
		return botonesContainer;
	}

	public void setBotonesContainer(HBox botonesContainer) {
		this.botonesContainer = botonesContainer;
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

	@Override
	public String toString() {
		return "CategoriaDTO [idCategoria=" + idCategoria + ", nombre=" + nombre + "]";
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
