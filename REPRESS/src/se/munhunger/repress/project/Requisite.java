/**
 * 
 */
package se.munhunger.repress.project;

import se.munhunger.repress.database.DatabaseManager;
import se.munhunger.repress.database.DatabaseObject;
import se.munhunger.repress.user.User;

/**
 * @author munhunger
 * 		
 */
public class Requisite extends DatabaseObject
{
	static
	{
		new Requisite(0).createTable();
	}
	
	private int id = -1;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see se.munhunger.repress.database.DatabaseObject#getCreateSQL()
	 */
	@Override
	public String getCreateSQL()
	{
		return "CREATE TABLE IF NOT EXISTS requisite (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, description TEXT NOT NULL, creator INTEGER, responsible_user INTEGER, project INTEGER, creation_date INTEGER NOT NULL)";
	}
	
	public String getTitle()
	{
		return super.getAttributeString("title");
	}
	
	public String getDescription()
	{
		return super.getAttributeString("description");
	}
	
	public User getCreator()
	{
		return new User(super.getAttributeInt("creator"));
	}
	
	public User getResponisbleUser()
	{
		return new User(super.getAttributeInt("responsible_user"));
	}
	
	public Project getParentProject()
	{
		return new Project(super.getAttributeInt("project"));
	}
	
	public int getCreationDate()
	{
		return super.getAttributeInt("creation_date");
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see se.munhunger.repress.database.DatabaseObject#getTableName()
	 */
	@Override
	public String getTableName()
	{
		return "requisite";
	}
	
	/**
	 * Creates a new requisite, with all the needed connections
	 *
	 * @param title
	 * @param description
	 * @param creator
	 * @param responsibleUser
	 * @param parentProject
	 * @since May 15, 2016
	 * @author munhunger
	 */
	public static void createRequisite(String title, String description, User creator, User responsibleUser,
			Project parentProject)
	{
		DatabaseManager.updateSQL(String.format(
				"INSERT INTO requisite (title, description, creator, responsible_user, project, creation_date) VALUES ('%s', '%s', %d, %d, %d, %d)",
				title, description, creator.getID(), responsibleUser.getID(), parentProject.getID(),
				System.currentTimeMillis()));
	}
	
	/**
	 * Gets the ID of this Requisite
	 *
	 * @return
	 * @since May 14, 2016
	 * @author munhunger
	 */
	@Override
	public int getID()
	{
		return this.id;
	}
	
	public Requisite(int id) throws IllegalArgumentException
	{
		if (id < 0)
			throw new IllegalArgumentException("only possitive ID's are allowed");
		this.id = id;
	}
}
