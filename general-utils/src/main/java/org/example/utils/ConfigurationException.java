package org.example.utils;

/**
 * Exception for configuration issues.
 *
 */
public class ConfigurationException extends Exception
{


	/**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
	 * Constructor.
	 */
	public ConfigurationException()
	{
		// empty
	}

	/**
	 * Creates an exception with the given message
	 *
	 * @param message
	 *           the message
	 */
	public ConfigurationException(String message)
	{
		super(message);
	}

	/**
	 * Creates an exception with the given throwable
	 *
	 * @param cause
	 *           the cause
	 */
	public ConfigurationException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * reates an exception with the given throwable and message
	 *
	 * @param message
	 *           the message
	 * @param cause
	 *           the cause
	 */
	public ConfigurationException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
