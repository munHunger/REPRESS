package se.munhunger.repress;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import se.munhunger.repress.gui.SplashScene;

public class Main extends Application
{
	
	public static void main(String[] args)
	{
		// User user = User.userExists("munhunger") ? User.getUser("munhunger")
		// : User.createUser("munhunger");
		// Project proj = Project.projectExists("DCTM D2 ÄoD") ?
		// Project.getProject("DCTM D2 ÄoD")
		// : Project.createProject("DCTM D2 ÄoD", user);
		// Requisite.createRequisite("Krav #2", "Måste kunna logga ut eller
		// nått", user, user, proj);
		// for (Requisite req : proj.getRequisites())
		// System.out.println(req.getTitle() + "\t" + req.getDescription());
		// DatabaseManager.closeConnection();
		// System.exit(0);
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		primaryStage.setTitle("REsourcePRojEctSoftwareSolution");
		Scene splash = new Scene(new SplashScene());
		splash.getStylesheets().add("file:res/css/splash/splash.css");
		primaryStage.setScene(splash);
		primaryStage.getIcons().add(new Image("file:res/icon/32.png"));
		primaryStage.show();
		primaryStage.setResizable(false);
		new Thread((Runnable) () ->
		{
			try
			{
				Thread.sleep(2500);
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Platform.runLater(() ->
			{
				primaryStage.setScene(new Scene(new Label("UPDATED")));
			});
		}).start();
	}
}
