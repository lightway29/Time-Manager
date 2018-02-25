package com.saiton.ccs.database;

import com.saiton.ccs.base.ApplicationProperties;
import java.sql.Connection;

//Starter Class
public class Starter {

    public static Connection con = null;

    public static boolean start() {
        ApplicationProperties app = ApplicationProperties.getInstance();
        con = DB.connect(app.getUsername(), app.getPassword(), app.getHost(),
                app.getDatabase(), app.getPort());
        return (con != null);
    }
}
