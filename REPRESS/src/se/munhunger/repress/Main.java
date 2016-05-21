package se.munhunger.repress;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import se.munhunger.repress.gui.RequisiteTable;
import se.munhunger.repress.gui.SplashScene;
import se.munhunger.repress.project.Project;

public class Main extends Application
{
	
	public static void main(String[] args)
	{
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
				primaryStage.setScene(new Scene(new RequisiteTable(Project.getAllProjects().get(0))));
				primaryStage.setResizable(true);
			});
		}).start();
	}
}
