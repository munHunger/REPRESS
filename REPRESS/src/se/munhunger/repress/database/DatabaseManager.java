package se.munhunger.repress.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Consumer;

public class DatabaseManager
{
	private static Connection conn = null;
	
	static
	{
		try
		{
			conn = DriverManager.getConnection("jdbc:sqlite:repress.db");
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Closes the connection to the database
	 *
	 * @since May 14, 2016
	 * @author munhunger
	 */
	public static void closeConnection()
	{
		try
		{
			conn.close();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Executes a sql statement.<br />
	 * This should be a SELECT statement.
	 * 
	 * @see #updateSQL(String) for running SQL that shouldn't have a result
	 * @param sql
	 *            the statement to execute
	 * @return the result of executing the input sql statement. or null if sql
	 *         failed
	 * @since May 13, 2016
	 * @author munhunger
	 * @deprecated
	 */
	@Deprecated
	public static void executeSQL(String sql, Consumer<ResultSet> consumer)
	{
		try (Statement stmt = conn.createStatement())
		{
			consumer.accept(stmt.executeQuery(sql));
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets a statement to execute elsewhere<br />
	 * Note that this must be closed
	 *
	 * @return
	 * @since May 14, 2016
	 * @author munhunger
	 */
	public static Statement getStatement()
	{
		try
		{
			return conn.createStatement();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Executes a sql statement that won't have a result set.<br />
	 * i.e. statements such as INSERT
	 * 
	 * @see #executeSQL(String) for running SQL that have a result
	 * @param sql
	 * @since May 13, 2016
	 * @author munhunger
	 */
	public static int updateSQL(String sql)
	{
		try (Statement stmt = conn.createStatement())
		{
			return stmt.executeUpdate(sql);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
}
