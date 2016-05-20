package se.munhunger.repress.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import se.munhunger.repress.database.DatabaseManager;
import se.munhunger.repress.database.DatabaseObject;

public class User extends DatabaseObject
{
	static
	{
		new User(0).createTable();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see se.munhunger.repress.database.DatabaseObject#getCreateSQL()
	 */
	@Override
	public String getCreateSQL()
	{
		return "CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE NOT NULL)";
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see se.munhunger.repress.database.DatabaseObject#getTableName()
	 */
	@Override
	public String getTableName()
	{
		return "user";
	}
	
	/**
	 *
	 * @return The name of this user
	 * @since May 17, 2016
	 * @author munhunger
	 */
	public String getName()
	{
		return super.getAttributeString("name");
	}
	
	/**
	 * Checks if a users exists in the database
	 *
	 * @param name
	 * @return true iff there is a user in the database with the given name
	 * @since May 14, 2016
	 * @author munhunger
	 */
	public static boolean userExists(String name)
	{
		try (Statement stmt = DatabaseManager.getStatement())
		{
			ResultSet res = stmt.executeQuery(String.format("SELECT id FROM user WHERE name='%s'", name));
			return res.next();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Creates a new user in the database and returns the result.
	 *
	 * @param name
	 * @return
	 * @since May 14, 2016
	 * @author munhunger
	 */
	public static User createUser(String name)
	{
		DatabaseManager.updateSQL(String.format("INSERT INTO user (name) VALUES ('%s')", name));
		return getUser(name);
	}
	
	/**
	 * Fetches the user with the given name
	 *
	 * @param name
	 * @return
	 * @since May 14, 2016
	 * @author munhunger
	 */
	public static User getUser(String name)
	{
		try (Statement stmt = DatabaseManager.getStatement())
		{
			ResultSet res = stmt.executeQuery(String.format("SELECT id FROM user WHERE name='%s'", name));
			if (res.next())
				return new User(res.getInt("id"));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public User(int id) throws IllegalArgumentException
	{
		// TODO throw exception if user id is out of range
		if (id < 0)
			throw new IllegalArgumentException("only possitive ID's are allowed");
		this.id = id;
	}
}
