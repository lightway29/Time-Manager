package com.saiton.ccs.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author Saitonya
 */
public final class ApplicationProperties {

    //FILENAME
    private static final String FILENAME = "ccs.properties";
    //PROPERTIES-KEYS
    private static final String PROP_DATA_SAVED = "datasaved";
    private static final String PROP_FINGER = "hasfinger";
    private static final String PROP_DB = "db";
    private static final String PROP_PORT = "port";
    private static final String PROP_HOST = "host";
    private static final String PROP_IP = "ip";
    private static final String PROP_USER = "user";
    private static final String PROP_PASS = "pass";
    //INSTANCE
    private static ApplicationProperties instance = null;
    private static final TextEncrypt encrypt = new TextEncrypt("propdata");

    //SINGLETON
    public static ApplicationProperties getInstance() {
        synchronized (ApplicationProperties.class) {
            if (instance == null) {
                instance = new ApplicationProperties();
            }
        }
        return instance;
    }
    //PROPERTIES
    private Boolean dataSaved;
    private Boolean fingerAvailable;
    private String database;
    private String port;
    private String hostname;
    private String ip;
    private String username;
    private String password;

    private ApplicationProperties() {
        load();
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        if (hostname.isEmpty()) {
            return ip;
        }
        return hostname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDataSaved() {
        return dataSaved;
    }

    public void setDataSaved(Boolean dataSaved) {
        this.dataSaved = dataSaved;
    }

    public boolean isFingerAvailable() {
        return fingerAvailable;
    }

    public void setFingerAvailable(Boolean hasFinger) {
        this.fingerAvailable = hasFinger;
    }

    private String encode(String data) {
        if (data == null || data.isEmpty()) {
            return "";
        }
        String ret = encrypt.encrypt(data);
        return (ret == null) ? "" : ret;
    }

    private String decode(String data) {
        if (data == null || data.isEmpty()) {
            return "";
        }
        String ret = encrypt.decrypt(data);
        return (ret == null) ? "" : ret;
    }

    private static File getPropertyFile() {
        //File d = new File(System.getProperty("user.home"));
        File f = new File(FILENAME);
        return f;
    }

    /**
     * load properties
     */
    public void load() {
        Properties props = new Properties();
        InputStream is;

        // First try loading from the current directory
        try {

            is = new FileInputStream(getPropertyFile());
        } catch (Exception e) {
            is = null;
        }

        try {
            if (is == null) {
                // Try loading from classpath
                is = getClass().getResourceAsStream(FILENAME);
            }

            // Try loading properties from the file (if found)
            props.load(is);
            is.close();
        } catch (Exception e) {
        }

        //READ
        dataSaved = Boolean.valueOf(props.getProperty(PROP_DATA_SAVED, "false"));
        fingerAvailable = Boolean.valueOf(
                props.getProperty(PROP_FINGER, "true"));
        database = decode(props.getProperty(PROP_DB, ""));
        port = decode(props.getProperty(PROP_PORT, ""));
        hostname = decode(props.getProperty(PROP_HOST, ""));
        ip = decode(props.getProperty(PROP_IP, ""));
        username = decode(props.getProperty(PROP_USER, ""));
        password = decode(props.getProperty(PROP_PASS, ""));

    }

    /**
     * save properties
     */
    public void save() {
        try {
            //WRITE
            Properties props = new Properties();
            props.setProperty(PROP_DATA_SAVED, dataSaved.toString());
            props.setProperty(PROP_FINGER, fingerAvailable.toString());
            props.setProperty(PROP_DB, encode(database));
            props.setProperty(PROP_PORT, encode(port));
            props.setProperty(PROP_HOST, encode(hostname));
            props.setProperty(PROP_IP, encode(ip));
            props.setProperty(PROP_USER, encode(username));
            props.setProperty(PROP_PASS, encode(password));
            OutputStream out = new FileOutputStream(getPropertyFile());
            props.store(out, "IHMS DATA");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * reset the properties
     */
    public void reset() {
        dataSaved = false;
        save();
    }

    public boolean isInvalid() {
        return (!dataSaved || database.isEmpty() || port.isEmpty()
                || (hostname.isEmpty() && ip.isEmpty()) || username.isEmpty()
                || password.isEmpty());
    }
}
