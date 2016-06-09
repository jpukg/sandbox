package org.example.utils;

import java.nio.file.Paths;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.commons.configuration.Configuration;

/**
 * The server configuration implementation
 * 
 */
@Startup
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Local(value = IServerConfiguration.class)
public class ServerConfiguration implements IServerConfiguration {

  private Configuration mainConfiguration = null;

  public ServerConfiguration() throws ConfigurationException {
    String configRoot = System.getenv("appname.configuration.root");
    if ((configRoot == null) || (configRoot.trim().length() == 0)) {
      configRoot = System.getProperty("appname.configuration.root");
    }
    mainConfiguration = ConfigurationSetup.initConfiguration(Paths.get(configRoot));
  }

  @Override
  public String encryptValue(String valueToEncrypt) {
    if ((mainConfiguration != null) && (mainConfiguration instanceof SecuredConfiguration)) {
      return ((SecuredConfiguration) mainConfiguration).encrypt(valueToEncrypt);
    }
    return valueToEncrypt;
  }

  @Override
  public String decryptValue(String valueToDecrypt) {
    if ((mainConfiguration != null) && (mainConfiguration instanceof SecuredConfiguration)) {
      return ((SecuredConfiguration) mainConfiguration).decrypt(valueToDecrypt);
    }
    return valueToDecrypt;
  }

  @Override
  public Configuration getMainConfiguration() {
    return mainConfiguration;
  }

}
