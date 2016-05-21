/**
 * 
 */
package se.munhunger.repress.util;

/**
 * @author munhunger
 * 		
 */
public class Version
{
	private static int major = 0;
	private static int minor = 1;
	private static int hotfix = 0;
	
	/**
	 * Gets a string representation of the app version.<br />
	 * This will be on the form major.minor.hotfix
	 *
	 * @return
	 * @since May 20, 2016
	 * @author munhunger
	 */
	public static String getVersionLabel()
	{
		return major + "." + minor + (hotfix > 0 ? "." + hotfix : "");
	}
}
