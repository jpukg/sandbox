package org.example.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.configuration.tree.NodeCombiner;
import org.apache.commons.configuration.tree.OverrideCombiner;

/**
 * Utility class to set up configuration.
 * 
 */
public final class ConfigurationSetup {
  public static final String DEFAULT_CONFIG_PASSFILE = "secured.pass";

  public static final String[] DEFAULT_CONFIG_LEVELS = new String[] {"server-configuration.xml", "environment-configuration.xml", "default-configuration.xml"};

  private static final long MINUTE = 60000L;

  private ConfigurationSetup() {/* hide */
  }

  /**
   * Creates a configuration based on the provided parameters.
   * 
   * @param configRoot the path to the root folder containing the configuration files
   * @param parts the list of files in the order of precedence. The first entry has highest priority
   *        (e.g. server-configuration.xml, environment-configuration.xml,
   *        default-configuration.xml).
   * @param passFileName the name of the password file containing the en/decription key. File can be
   *        relative to the <code>configRoot</code> or an absolute path. If the password file cannot
   *        be resolved, an standard configuration is returned, if it can be resolved a secure
   *        configuration is returned. Default name is <code>secured.pass</code>.
   * @return the (in)secure configuration
   * @throws ConfigurationException
   */
  public static Configuration initConfiguration(Path configRoot, String[] parts, String passFileName) throws ConfigurationException {
    CombinedConfiguration mainConfiguration = null;
    try {
      // check if pass file is defined and exists
      Path passFile = null;
      if ((passFileName != null) && (passFileName.trim().length() > 0)) {
        passFile = Paths.get(passFileName);
      }
      // fallback to pass file in configRoot
      if ((passFileName != null) && ((passFile == null) || Files.notExists(passFile))) {
        passFile = configRoot.resolve(passFileName);
      }
      // fallback to default pass file
      if ((passFile == null) || Files.notExists(passFile)) {
        passFile = configRoot.resolve(DEFAULT_CONFIG_PASSFILE);
      }
      NodeCombiner combiner = new OverrideCombiner();

      // fallback to insecure configuration
      if (Files.exists(passFile)) {
        mainConfiguration = new SecuredConfiguration(passFile, combiner);
      } else {
        mainConfiguration = new CombinedConfiguration(combiner);
      }

      // load individual configuration files
      for (String part : parts) {
        XMLConfiguration xmlConfiguration = new XMLConfiguration(configRoot.resolve(part).toFile());
        FileChangedReloadingStrategy strategy = new FileChangedReloadingStrategy();
        strategy.setRefreshDelay(1 * MINUTE);
        xmlConfiguration.setReloadingStrategy(strategy);
        // xmlConfiguration.addConfigurationListener(this);
        mainConfiguration.addConfiguration(xmlConfiguration);
      }
      mainConfiguration.setForceReloadCheck(true);
      return mainConfiguration;
    } catch (org.apache.commons.configuration.ConfigurationException e) {
      throw new ConfigurationException(e);
    }
  }

  /**
   * Creates a configuration based on the standard naming conventions and the provided configuration
   * path.
   * 
   * @param configRoot the path to the root folder containing the configuration files The default
   *        setup is used: 3 configuration levels with filenames: <code>server-configuration.xml,
   *           environment-configuration.xml and default-configuration.xml</code>. The standard name
   *        for the password file is used (<code>secured.pass</code>) and is supposed to be present
   *        in the given configuration path.
   * @return the (in)secure configuration
   * @throws ConfigurationException
   */
  public static Configuration initConfiguration(Path configRoot) throws ConfigurationException {
    return initConfiguration(configRoot, DEFAULT_CONFIG_LEVELS, DEFAULT_CONFIG_PASSFILE);
  }
}
