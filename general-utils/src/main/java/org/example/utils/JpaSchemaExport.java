package org.example.utils;

import java.io.IOException;
import java.util.Properties;

import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.HSQLDialect;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.Target;

@SuppressWarnings("deprecation")
public class JpaSchemaExport {

  private static final String DIALECT_ORACLE = "ORACLE";
  private static final String SCHEMA_NAME = "EXAMPLE";

  public static void main(String[] args) throws IOException {
    execute(args[0], "example-unit", "src/test/resources/example-ddl-scripts.sql", false);
  }

  public static void execute(String dialectName, String persistenceUnitName, String destination, boolean format) {
    System.out.println("Starting schema export");

    Ejb3Configuration cfg = new Ejb3Configuration().configure(persistenceUnitName, new Properties());
    cfg.setProperty("hibernate.dialect", HSQLDialect.class.getName());
    if (DIALECT_ORACLE.equalsIgnoreCase(dialectName)) {
      cfg.setProperty("hibernate.dialect", Oracle10gDialect.class.getName());
    }
    cfg.setProperty("hibernate.default_schema", SCHEMA_NAME);
    Configuration hbmcfg = cfg.getHibernateConfiguration();
    SchemaExport schemaExport = new SchemaExport(hbmcfg);
    schemaExport.setOutputFile(destination);
    schemaExport.setDelimiter(";");
    schemaExport.setFormat(format);
    schemaExport.execute(Target.SCRIPT, SchemaExport.Type.BOTH);

    System.out.println("Schema exported to " + destination);
  }
}
