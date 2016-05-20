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
	private static int minor = 0;
	private static int hotfix = 0;
	
	public static String getVersionLabel()
	{
		return major + "." + minor + (hotfix > 0 ? "." + hotfix : "");
	}
}
