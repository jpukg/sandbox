package org.example.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.tree.NodeCombiner;
import org.jasypt.contrib.org.apache.commons.codec_1_3.binary.Base64;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * Secured composite configuration adds encryption to the standard composite configuration. Values
 * between <code>ENC(.)</code> are are decrypted at runtime. The decryption password is Base64
 * encoded and located in the provided password File.
 * 
 */
public class SecuredConfiguration extends CombinedConfiguration {
  private static final int ENCRYPTION_PREFIX_LENGTH = 4;

  /**
   * 
   */
  private static final long serialVersionUID = 6212669796894904389L;

  private final StandardPBEStringEncryptor encryptor;

  /**
   * Creates a composite configuration that supports encrypted values. Deprecated, use
   * {@link SecuredConfiguration (Path, NodeCombiner)} instead.
   * 
   * @param passFile the file containing the Base64 encoded decryption password
   * @param combiner
   * @throws ConfigurationException
   */
  @Deprecated
  public SecuredConfiguration(File passFile, NodeCombiner combiner) throws ConfigurationException {
    this(Paths.get(passFile.toURI()), combiner);
  }

  /**
   * Creates a composite configuration that supports encrypted values.
   * 
   * @param passFile the file containing the Base64 encoded decryption password
   * @param combiner
   * @throws ConfigurationException
   */
  public SecuredConfiguration(Path passFile, NodeCombiner combiner) throws ConfigurationException {
    super(combiner);
    encryptor = new StandardPBEStringEncryptor();
    loadSecureProperties(passFile);
  }

  private void loadSecureProperties(Path passFile) throws ConfigurationException {

    try (BufferedReader reader = Files.newBufferedReader(passFile, Charset.forName("utf-8"))) {
      String pass = reader.readLine();
      if (pass == null) {
        throw new ConfigurationException("Could not read password for secure storage");
      }
      pass = new String(Base64.decodeBase64(pass.getBytes()));

      reader.close();
      encryptor.setPassword(pass);
    } catch (IOException e) {
      throw new ConfigurationException("Error while configuring secured configuration", e);
    }
  }

  /**
   * Decrypts the value if it's surrounded by <code>ENC(...)</code>
   */
  @Override
  public Object getProperty(String key) {
    Object val = super.getProperty(key);
    if (val instanceof String) {
      String value = (String) val;
      if (!value.startsWith("ENC(")) {
        return val;
      }
      return encryptor.decrypt(value.substring(ENCRYPTION_PREFIX_LENGTH, value.length() - 1));
    }
    return val;
  }

  @Override
  public List<Object> getList(String key) {

    List<Object> res = super.getList(key);
    List<Object> retval = new ArrayList<Object>();
    for (Object val : res) {
      if (val instanceof String) {
        String value = (String) val;
        if (!value.startsWith("ENC(")) {
          retval.add(val);
        } else {
          retval.add(encryptor.decrypt(value.substring(ENCRYPTION_PREFIX_LENGTH, value.length() - 1)));
        }
      } else {
        retval.add(val);
      }
    }
    return retval;
  }

  /**
   * Encrypts a string
   * 
   * @param string the string to encrypt
   * @return the encrypted string
   */
  public String encrypt(String string) {
    return encryptor.encrypt(string);
  }

  /**
   * Decrypts a string
   * 
   * @param string the string to decrypt
   * @return the decrypted string
   */
  public String decrypt(String string) {
    return encryptor.decrypt(string);
  }
}
