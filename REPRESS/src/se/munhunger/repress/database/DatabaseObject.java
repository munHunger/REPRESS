/**
 * 
 */
package se.munhunger.repress.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author munhunger
 * 		
 */
public abstract class DatabaseObject
{
	protected int id = -1;
	
	/**
	 * @return the ID of the object
	 * @since May 15, 2016
	 * @author munhunger
	 */
	public int getID()
	{
		return id;
	}
	
	/**
	 * Get a SQL that creates a database table representing this object
	 *
	 * @return
	 * @since May 13, 2016
	 * @author munhunger
	 */
	public abstract String getCreateSQL();
	
	/**
	 * Gets the name of the database table object
	 *
	 * @return
	 * @since May 14, 2016
	 * @author munhunger
	 */
	public abstract String getTableName();
	
	/**
	 * Gets the string value of this object with the input label
	 *
	 * @param label
	 *            a label of a column in the database. This column should be a
	 *            string.
	 * @return the value of the column labeled <code>label</code> of this
	 *         object. Note that it is using {@link #getTableName()} to find the
	 *         correct table and {@link #getID()} to find the object
	 * @since May 15, 2016
	 * @author munhunger
	 */
	protected String getAttributeString(String label)
	{
		try (Statement stmt = DatabaseManager.getStatement())
		{
			ResultSet res = stmt
					.executeQuery(String.format("SELECT %s FROM %s WHERE id=" + getID(), label, getTableName()));
			if (res.next())
				return res.getString(label);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Gets the integer value of this object with the input label.
	 *
	 * @param label
	 *            a label of a column in the database. This column should be an
	 *            integer.
	 * @return the value of the column labeled <code>label</code> of this
	 *         object, or 0 if something went wrong. Note that it is using
	 *         {@link #getTableName()} to find the
	 *         correct table and {@link #getID()} to find the object
	 * @since May 15, 2016
	 * @author munhunger
	 */
	protected int getAttributeInt(String label)
	{
		try (Statement stmt = DatabaseManager.getStatement())
		{
			ResultSet res = stmt
					.executeQuery(String.format("SELECT %s FROM %s WHERE id=" + getID(), label, getTableName()));
			if (res.next())
				return res.getInt("name");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Creates the table for this object.
	 *
	 * @since May 13, 2016
	 * @author munhunger
	 */
	public void createTable()
	{
		DatabaseManager.updateSQL(getCreateSQL());
	}
}
