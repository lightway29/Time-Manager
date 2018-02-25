/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.basedao;


import com.saiton.ccs.database.Starter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

public class SetCurrencyRateDAO {

    public static Starter star;//db connection
    Codec ORACLE_CODEC = new OracleCodec();
    private final Logger log = Logger.getLogger(this.getClass());

    
     public String getValue(String cell) {
        String Cur="null";
        
         if (star.con == null) {
            log.error("Databse connection failiure.");
            return null;
        } else {
            try {

                Statement st = star.con.createStatement();                
                ResultSet rs = st.executeQuery("SELECT "+cell+" FROM currency");

                while (rs.next()) {
                    Cur = rs.getString(cell);
                }
                return Cur;
            }catch(SQLException e){
                log.error(e);
            }
         }
        return Cur;
    }
    
    public void setValue(String lkr,String usd) {
               
         if (star.con == null) {
            log.error("Databse connection failiure.");
            
        } else {
            try {
                Statement st = star.con.createStatement();                
                st.executeUpdate("UPDATE currency SET lkr_rate="+lkr+",usd_rate="+usd+" WHERE id=1"); 
                
            }catch(SQLException e){
                log.error(e);
            }
         }
        
    }
}
