/**
 * 
 */
package se.munhunger.repress.project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import se.munhunger.repress.database.DatabaseManager;
import se.munhunger.repress.database.DatabaseObject;
import se.munhunger.repress.user.User;

/**
 * @author munhunger
 * 		
 */
public class Project extends DatabaseObject
{
	static
	{
		new Project(0).createTable();
	}
	
	/**
	 * Fetches the name of this project
	 *
	 * @return the name of this project or null if something went wrong
	 * @since May 15, 2016
	 * @author munhunger
	 */
	public String getName()
	{
		return super.getAttributeString("name");
	}
	
	/**
	 * Gets all the requisites related to this project
	 *
	 * @return a list of requisites related to this project
	 * @since May 15, 2016
	 * @author munhunger
	 */
	public ArrayList<Requisite> getRequisites()
	{
		ArrayList<Requisite> result = new ArrayList<>();
		try (Statement stmt = DatabaseManager.getStatement())
		{
			ResultSet res = stmt.executeQuery("SELECT id FROM requisite WHERE project=" + getID());
			while (res.next())
				result.add(new Requisite(res.getInt("id")));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see se.munhunger.repress.database.DatabaseObject#getCreateSQL()
	 */
	@Override
	public String getCreateSQL()
	{
		return "CREATE TABLE IF NOT EXISTS project (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE NOT NULL, owner INTEGER)";
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see se.munhunger.repress.database.DatabaseObject#getTableName()
	 */
	@Override
	public String getTableName()
	{
		return "project";
	}
	
	/**
	 * Checks if a project exists in the database
	 *
	 * @param name
	 * @return true iff there is a user in the database with the given name
	 * @since May 14, 2016
	 * @author munhunger
	 */
	public static boolean projectExists(String name)
	{
		try (Statement stmt = DatabaseManager.getStatement())
		{
			ResultSet res = stmt.executeQuery(String.format("SELECT id FROM project WHERE name='%s'", name));
			return res.next();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Creates a new project in the database and returns the result.
	 *
	 * @param name
	 * @param owner
	 * @return
	 * @since May 14, 2016
	 * @author munhunger
	 */
	public static Project createProject(String name, User owner)
	{
		DatabaseManager
				.updateSQL(String.format("INSERT INTO project (name, owner) VALUES ('%s', '%d')", name, owner.getID()));
		return getProject(name);
	}
	
	/**
	 * Fetches the project with the given name
	 *
	 * @param name
	 * @return
	 * @since May 14, 2016
	 * @author munhunger
	 */
	public static Project getProject(String name)
	{
		try (Statement stmt = DatabaseManager.getStatement())
		{
			ResultSet res = stmt.executeQuery(String.format("SELECT id FROM project WHERE name='%s'", name));
			if (res.next())
				return new Project(res.getInt("id"));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<Project> getAllProjects()
	{
		ArrayList<Project> projects = new ArrayList<>();
		try (Statement stmt = DatabaseManager.getStatement())
		{
			ResultSet res = stmt.executeQuery("SELECT id FROM project");
			while (res.next())
				projects.add(new Project(res.getInt("id")));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return projects;
	}
	
	public Project(int id)
	{
		this.id = id;
	}
}
