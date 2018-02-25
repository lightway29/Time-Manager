package com.saiton.ccs.salesdao;

import com.saiton.ccs.database.Starter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

public class ServiceDAO {

    public static Starter star;//db connection
    Codec ORACLE_CODEC = new OracleCodec();
    private final Logger log = Logger.getLogger(this.getClass());

    public String generateID() {

        Integer id = null;
        String cid = null;
        String final_id = null;
        if (star.con == null) {
            log.error("Databse connection failiure.");
            return null;
        } else {
            try {

                Statement st = star.con.createStatement();
                Statement ste = star.con.createStatement();
                ResultSet rs = st.executeQuery("SELECT MAX(id) as ID FROM services");

                while (rs.next()) {
                    id = rs.getInt("id");
                }
                ResultSet rss = ste.executeQuery(
                        "SELECT service_id FROM services WHERE id= " + id + "");

                while (rss.next()) {
                    cid = rss.getString("service_id");
                }

                if (id != 0) {
                    String original = cid.split("C")[1];
                    int i = Integer.parseInt(original) + 1;

                    if (i < 10) {
                        final_id = "SVC000" + i;
                    } else if (i >= 10 && i < 100) {
                        final_id = "SVC00" + i;
                    } else if (i >= 100 && i < 1000) {
                        final_id = "SVC0" + i;
                    } else if (i >= 1000 && i < 10000) {
                        final_id = "SVC" + i;
                    }
                    return final_id;

                } else {
                    return "SVC0001";
                }
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException |
                    SQLException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {
                    log.error("Exception tag --> " + "Split character error");
                } else if (e instanceof NumberFormatException) {
                    log.error("Exception tag --> "
                            + "Invalid number found in current id");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement "
                            + e.getMessage());
                }
                return null;

            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return null;
            }
        }
    }

    public String generateIDOOnDemand(int no) {

        Integer id = null;
        String cid = null;
        String final_id = null;
        if (star.con == null) {
            log.error("Databse connection failiure.");
            return null;
        } else {
            try {

                Statement st = star.con.createStatement();
                Statement ste = star.con.createStatement();
                ResultSet rs = st.executeQuery("SELECT MAX(id) as ID FROM services");

                while (rs.next()) {
                    id = rs.getInt("id");
                }
                ResultSet rss = ste.executeQuery(
                        "SELECT service_id FROM services WHERE id= " + id + "");

                while (rss.next()) {
                    cid = rss.getString("service_id");
                }

                if (id != 0) {
                    String original = cid.split("C")[1];
                    int i = Integer.parseInt(original) + no;

                    if (i < 10) {
                        final_id = "SVC000" + i;
                    } else if (i >= 10 && i < 100) {
                        final_id = "SVC00" + i;
                    } else if (i >= 100 && i < 1000) {
                        final_id = "SVC0" + i;
                    } else if (i >= 1000 && i < 10000) {
                        final_id = "SVC" + i;
                    }
                    return final_id;

                } else {
                    int i = no;
                    if (i < 10) {
                        final_id = "SVC000" + i;
                    } else if (i >= 10 && i < 100) {
                        final_id = "SVC00" + i;
                    } else if (i >= 100 && i < 1000) {
                        final_id = "SVC0" + i;
                    } else if (i >= 1000 && i < 10000) {
                        final_id = "SVC" + i;
                    }
                    return final_id;
                }
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException |
                    SQLException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {
                    log.error("Exception tag --> " + "Split character error");
                } else if (e instanceof NumberFormatException) {
                    log.error("Exception tag --> "
                            + "Invalid number found in current id");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement "
                            + e.getMessage());
                }
                return null;

            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return null;
            }
        }
    }

    
    public boolean checkingItemNameAvailability(String serviceName) {

//        String encodedItemName = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, itemName);
        String encodedServiceName = serviceName;
        boolean available = false;

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");

        } else {
            try {

                String query = "SELECT * FROM services  where service = ?";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedServiceName);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    available = true;
                }

            } catch (NullPointerException | SQLException e) {
                if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry passed");

                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement");
                }
                return false;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return false;
            }
        }
        return available;
    }

    public Boolean insertService(
            String serviceId,
            String serviceName,
            String serviceDesc,
            Double price,
            String userId) {

        String encodedServiceId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, serviceId);

        String encodedServiceName =  ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, serviceName);
        
         String encodedServiceDesc =  ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, serviceDesc);
 
        String encodeduserId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, userId);
        
        
        
        if (star.con == null) {

            log.error("Exception tag --> " + "Database connection failiure. ");
            return null;

        } else {
            try {

                PreparedStatement ps = star.con.prepareStatement("INSERT INTO "
                        + "services "
                        + "(service_id,service,service_description,price,user_id)"
                        + " VALUES(?,?,?,?,?)");
                
                ps.setString(1, encodedServiceId);
                ps.setString(2, encodedServiceName);
                ps.setString(3, encodedServiceDesc);
                ps.setDouble(4, price);
                ps.setString(5, encodeduserId);

                int val = ps.executeUpdate();
                if (val == 1) {
                    return true;
                } else {
                    return false;
                }

            } catch (NullPointerException | SQLException e) {

                if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry passed");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement "+e);

                }
                return false;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return false;
            }
        }
    }
    
    
      public boolean addServiceItem(
             String serviceId,
            String serviceName,
            String serviceDesc,
            Double price,
            String userId) {

        String encodedServiceId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, serviceId);

        String encodedServiceName =  ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, serviceName);

        String encodeduserId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, userId);
 
        boolean available = false;
        boolean available1 = false;
        boolean value = false;

        if (star.con == null) {

            log.error("Exception tag --> " + "Database connection failiure. ");

        } else {
            try {

                String query = "SELECT * FROM services where service_id = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedServiceId);
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    value = updateItems(
                            serviceId,
                            serviceName,
                            serviceDesc,
                            price,
                            userId
                           );

                    available = true;
                }
                if (available == false) {
                    value = insertService(
                             serviceId,
                             serviceName,
                             serviceDesc,
                             price,
                             userId
                            
                            );
                }

              
            } catch (NullPointerException | SQLException e) {
                if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry passed");

                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement");
                }
                return false;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return false;
            }
        }
        return value;
    }
      
      public Boolean updateItems(
             String serviceId,
            String serviceName,
            String serviceDesc,
            Double price,
            String userId
    ) {
       String encodedServiceId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, serviceId);

        String encodedServiceName =  ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, serviceName);

        String encodeduserId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, userId);
 

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {
            try {

                PreparedStatement ps = star.con.prepareStatement(
                        "UPDATE services SET "
                        + "`service`=? , "
                        + "`service_description`=? ,"
                        + "`price`=? , "
                        + "`user_id`=?  "
                        + " WHERE `service_id`=? ");

                ps.setString(1, serviceName);
                ps.setString(2, serviceDesc);
                ps.setDouble(3, price);
                ps.setString(4, userId);
                ps.setString(5, serviceId);
            
                int val = ps.executeUpdate();

                if (val == 1) {
                    return true;
                } else {
                    return false;
                }

            } catch (SQLException e) {

                if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement "
                            + e.getMessage());
                }
                return false;

            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return false;
            }
        }
    }
      
    public ArrayList<ArrayList<String>> searchItemDetails(String item) {

        String encodedItem = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, item);

        String itemId = null;
        String itemName = null;
        String itemDesc = null;
        String itemPrice = null;

        

        ArrayList<ArrayList<String>> Mainlist
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * "
                        + "From services "
                        + "Where service_id LIKE ? or service LIKE ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedItem + "%");
                pstmt.setString(2, encodedItem + "%");
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    itemId = r.getString("service_id");
                    itemName = r.getString("service");
                    itemDesc = r.getString("service_description");
                    itemPrice = r.getString("price");

                    
                    list.add(itemId);
                    list.add(itemName);
                    list.add(itemDesc);
                    list.add(itemPrice);

                    Mainlist.add(list);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error("Exception tag --> "
                            + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.
                            error("Exception tag --> " + "Invalid sql statement"
                                    + e);

                } else if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry for list");

                }
                return null;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return null;
            }
        }
        return Mainlist;
    }
      
    public String getUserName(String ItemId) {

        String encodedItemId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, ItemId);
        String userName = null;
        if (star.con == null) {
            log.error("Databse connection failiure.");
            return null;
        } else {
            try {

                String query = "select u.user_name "
                        + "from services i "
                        + "left join user u "
                        + "on u.eid = i.user_id "
                        + "where i.service_id = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedItemId);
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    userName = r.getString("u.user_name");
                }

                return userName;
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException |
                    SQLException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {
                    log.error("Exception tag --> " + "Split character error");
                } else if (e instanceof NumberFormatException) {
                    log.error("Exception tag --> "
                            + "Invalid number found in current id");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement "
                            + e.getMessage());
                }
                return null;

            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return null;
            }
        }
    }  
    
    public boolean checkingItemAvailability(String serviceId) {

        String encodedServiceId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, serviceId);
        boolean available = false;

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");

        } else {
            try {

                String query = "SELECT * FROM services where service_id= ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedServiceId);
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    available = true;
                }

            } catch (NullPointerException | SQLException e) {
                if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry passed");

                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement");
                }
                return false;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return false;
            }
        }
        return available;
    }
    public boolean deleteItem(String service) {

        String encodedService = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, service);
        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return false;
        } else {
            try {
                String sql = "DELETE FROM services WHERE service_id='" + encodedService
                        + "' ";
                PreparedStatement stmt = star.con.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
//                stmt.setString(1, encodedItem);
                stmt.executeUpdate();
            } catch (SQLException e) {

                if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement "
                            + e.getMessage());
                }
                return false;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return false;
            }
            return true;
        }
    }
      
   }
