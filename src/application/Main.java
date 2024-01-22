package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.Parent;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
//			Parent root = FXMLLoader.load(getClass().getResource("/views/principal.fxml"));
//			
//			Stage mainStage = new Stage();
//			//mainStage.initStyle(StageStyle.UNDECORATED);
//			mainStage.setScene(new Scene(root,780, 479));
//			mainStage.show();
			Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
			//quitamos el marco de la ventana
			primaryStage.initStyle(StageStyle.UNDECORATED);
			Scene scene = new Scene(root,520,400);
			primaryStage.setScene(scene);
			//primaryStage.setTitle("CtStock PFC");
			primaryStage.show();
			System.out.println("prueba");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
