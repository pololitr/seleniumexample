package cz.churchcrm.testframework;
import java.io.*;
import java.util.Properties;

public class ConfigFileReader {


        private Properties properties;
        private final String propertyFilePath= "configuration\\Configuration.properties";



        public ConfigFileReader(){
            BufferedReader reader;


            reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/" + propertyFilePath)));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String getDefaultDriver(){
            String defaultDriver = properties.getProperty("defaultDriver");
            if(defaultDriver!= null) return defaultDriver;
            else throw new RuntimeException("driverPath not specified in the Configuration.properties file.");
        }

        public long getHeadlessMod() {
            String headlessMod = properties.getProperty("headlessMod");
            if(headlessMod != null) return Long.parseLong(headlessMod);
            else throw new RuntimeException("implicitlyWait not specified in the Configuration.properties file.");
        }

        public String getApplicationUrl() {
            String applicationUrl = properties.getProperty("applicationUrl");
            if(applicationUrl != null) return applicationUrl;
            else throw new RuntimeException("url not specified in the Configuration.properties file.");
        }

    }

