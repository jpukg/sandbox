package org.example.utils;

import org.apache.commons.configuration.Configuration;

/**
 * The server configuration.
 * 
 */
public interface IServerConfiguration {
  /**
   * Returns the main configuration
   * 
   * @return the main configuration
   */
  Configuration getMainConfiguration();

  String encryptValue(String valueToEncrypt);

  String decryptValue(String valueToDecrypt);
}
