/**
 * 
 */
package se.munhunger.repress.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import se.munhunger.repress.user.User;
import se.munhunger.repress.util.Version;

/**
 * @author munhunger
 * 		
 */
public class SplashScene extends GridPane
{
	public SplashScene()
	{
		super();
		BorderPane header = new BorderPane();
		header.setMinWidth(300);
		header.setMinHeight(75);
		header.setId("header");
		Label repressLabel = new Label("REPRESS");
		repressLabel.setId("title");
		header.setLeft(repressLabel);
		Label welcomeLabel = new Label("Welcome");
		welcomeLabel.setId("section");
		User user = User.userExists(System.getProperty("user.name")) ? User.getUser(System.getProperty("user.name"))
				: User.createUser(System.getProperty("user.name"));
		Label userLabel = new Label(user.getName());
		userLabel.setId("subsection");
		BorderPane footer = new BorderPane();
		Label versionLabel = new Label(
				"REsourcePRojEctSoftwareSolution : " + Version.getVersionLabel() + "\nAuthor:Marcus Münger");
		versionLabel.setId("version");
		footer.setRight(versionLabel);
		footer.setMinWidth(header.getMinWidth());
		footer.setMinHeight(25);
		footer.setId("footer");
		
		super.setId("body");
		super.setAlignment(Pos.CENTER);
		super.add(header, 0, 0, 2, 1);
		super.add(welcomeLabel, 0, 1, 2, 1);
		super.add(userLabel, 0, 2);
		super.add(footer, 0, 4);
	}
}
