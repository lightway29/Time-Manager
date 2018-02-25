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

public class CustomerRegistrationDAO {

    public static Starter star;//db connection
    Codec ORACLE_CODEC = new OracleCodec();
    private final Logger log = Logger.getLogger(this.getClass());

    public ArrayList<ArrayList<String>> searchItemDetails(String search) {

        String encodedSearch = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, search);

        String cus_id = null;
        String cus_name = null;
        String cus_address = null;
        String cus_nic = null;
        String cus_Vat = null;
        String cus_title = null;

        ArrayList<ArrayList<String>> Mainlist = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Database connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * FROM customer c LEFT JOIN customer_vehicle_no  vn ON "
                        + "c.cus_id = vn.cus_id "
                        + "WHERE (c.cus_name LIKE ? OR c.cus_address LIKE ? "
                        + "OR c.cus_id LIKE ? or "
                        + "vn.vehicle_no LIKE ? )";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedSearch + "%");
                pstmt.setString(2, encodedSearch + "%");
                pstmt.setString(3, encodedSearch + "%");
                pstmt.setString(4, encodedSearch + "%");

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    cus_id = r.getString("cus_id");
                    cus_title = r.getString("cus_title");
                    cus_name = r.getString("cus_name");
//                
                    cus_address = r.getString("cus_address");
                    cus_Vat = r.getString("cus_type");
                    
                    list.add(cus_id);
                    list.add(cus_title);
                    list.add(cus_name);

                    list.add(cus_address);
                    list.add(cus_Vat);
                    Mainlist.add(list);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException | NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error("Exception tag --> " + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement");

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

    public String generateId() {

        Integer id = null;

        if (star.con == null) {

            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;
        } else {

            String eid = null;
            String final_id = null;
            if (star.con == null) {
                log.error(" Exception tag --> " + "Databse connection failiure. ");
                return null;
            } else {
                try {

                    String query = "SELECT MAX(id) as ID FROM customer";

                    PreparedStatement pstmt = star.con.prepareStatement(query);

                    ResultSet r = pstmt.executeQuery();

                    while (r.next()) {
                        id = r.getInt("id");
                    }
                    String queryCurrentId = "SELECT cus_id FROM customer WHERE id=?";

                    PreparedStatement pstmtId = star.con.prepareStatement(queryCurrentId);
                    pstmtId.setInt(1, id);

                    ResultSet rss = pstmtId.executeQuery();
                    while (rss.next()) {
                        eid = rss.getString("cus_id");

                    }
                    if (id != 0) {
                        String original = eid.split("S")[1];
                        int i = Integer.parseInt(original) + 1;

                        if (i < 10) {
                            final_id = "CUS000" + i;
                        } else if (i >= 10 && i < 100) {
                            final_id = "CUS00" + i;
                        } else if (i >= 100 && i < 1000) {
                            final_id = "CUS0" + i;
                        } else if (i >= 1000 && i < 10000) {
                            final_id = "CUS" + i;
                            if (i >= 9999) {
                                log.error("Exception tag --> " + "id max reached");
                            }
                        }
                        return final_id;
                    } else {
                        return "CUS0001";
                    }
                } catch (ArrayIndexOutOfBoundsException | NumberFormatException | SQLException e) {

                    if (e instanceof ArrayIndexOutOfBoundsException) {

                        log.error("Exception tag --> " + "Split character error");

                    } else if (e instanceof NumberFormatException) {

                        log.error("Exception tag --> " + "Invalid number found in current id");

                    } else if (e instanceof SQLException) {

                        log.error("Exception tag --> " + "Invalid sql statement");

                    }

                    return null;
                } catch (Exception e) {

                    log.error("Exception tag --> " + "Error");

                    return null;
                }
            }
        }
    }

    public Boolean insertCustomerDetails(
            String cusId,
            String cusName,
            String cusAddress,
            String cusTitle,
            String VAT
    ) {

        String encodedCusId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cusId);
        String encodedCusName = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cusName);
        String encodedCusAddress = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cusAddress);
        String encodedCusTitle = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cusTitle);
        String encodedCusVat = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, VAT);

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");
            return null;
        } else {
            try {

                PreparedStatement ps = star.con.prepareStatement("insert into customer(cus_id,cus_name,cus_address,cus_title,cus_type) VALUES(?,?,?,?,?)");

                ps.setString(1, encodedCusId);
                ps.setString(2, encodedCusName);
                ps.setString(3, encodedCusAddress);
                ps.setString(4, encodedCusTitle);
                ps.setString(5, encodedCusVat);

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

                    log.error("Exception tag --> " + "Invalid Insert sql statement");

                }
                return false;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return false;
            }
        }
    }

    public boolean checkingCustomerAvailability(String cus_id) {

        String encodedCusId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cus_id);
        boolean available = false;

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");

        } else {
            try {

                String query = "SELECT * FROM customer where cus_id = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedCusId);

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

    public Boolean insertCustomerTelephoneDetails(
            String cus_id,
            String tel_no) {

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                PreparedStatement ps = star.con.prepareStatement("INSERT INTO customer_tel(cus_id,tel_no) VALUES(?,?)");
                ps.setString(1, cus_id);
                ps.setString(2, tel_no);

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

                    log.error("Exception tag --> " + "Invalid sql statement");

                }
                return false;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return false;
            }
        }
    }

    public Boolean insertCustomerMobileDetails(
            String cus_id,
            String mobile_no) {

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {

            try {

                PreparedStatement ps = star.con.prepareStatement("INSERT INTO customer_mobile(cus_id,mob_no) VALUES(?,?)");
                ps.setString(1, cus_id);
                ps.setString(2, mobile_no);

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

                    log.error("Exception tag --> " + "Invalid sql statement");

                }
                return false;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return false;
            }
        }
    }

    public Boolean insertCustomerEmailDetails(
            String cus_id,
            String email_no) {

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");
            return null;
        } else {
            try {
                PreparedStatement ps = star.con.prepareStatement("INSERT INTO customer_email(cus_id,email) VALUES(?,?)");
                ps.setString(1, cus_id);
                ps.setString(2, email_no);

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

                    log.error("Exception tag --> " + "Invalid sql statement");

                }
                return false;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return false;
            }
        }
    }

    public Boolean insertCustomerFaxDetails(
            String cus_id,
            String vehicle_no) {
        Integer id = null;

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");
            return null;
        } else {
            try {
                PreparedStatement ps = star.con.prepareStatement("INSERT INTO customer_vehicle_no(cus_id,vehicle_no) VALUES(?,?)");
                ps.setString(1, cus_id);
                ps.setString(2, vehicle_no);

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

                    log.error("Exception tag --> " + "Invalid sql statement");

                }
                return false;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return false;
            }
        }
    }
    
    public Boolean insertDriverDetails(
            String cus_id,
            String driver) {
        Integer id = null;

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");
            return null;
        } else {
            try {
                PreparedStatement ps = star.con.prepareStatement("INSERT INTO "
                        + " drivers ("
                        + " cus_id, "
                        + "driver "
                        + ") VALUES "
                        + " (?,?)");
                ps.setString(1, cus_id);
                ps.setString(2, driver);

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

                    log.error("Exception tag --> " + "Invalid sql statement");

                }
                return false;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return false;
            }
        }
    }

    public ArrayList customerTelephoneLoading(String cus_id) {

        String encodedCusId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cus_id);
        ArrayList list = new ArrayList();
        String tel_no = null;

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");
            return null;
        } else {
            try {

                String query = "SELECT * FROM customer_tel WHERE cus_id = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedCusId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    tel_no = r.getString("tel_no");

                    list.add(tel_no);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException | NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error("Exception tag --> " + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement");

                } else if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry for list");

                }
                return null;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return null;
            }

        }
        return list;
    }

    public ArrayList customerMobileLoading(String cus_id) {

        String encodedCusId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cus_id);
        String mob_no = null;
        ArrayList list = new ArrayList();

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");
            return null;
        } else {
            try {

                String query = "SELECT * FROM customer_mobile WHERE cus_id = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedCusId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    mob_no = r.getString("mob_no");

                    list.add(mob_no);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException | NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error("Exception tag --> " + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement");

                } else if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry for list");

                }
                return null;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return null;
            }

        }
        return list;
    }

    public ArrayList customerEmailLoading(String cus_id) {

        String encodedCusId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cus_id);
        String email = null;

        ArrayList list = new ArrayList();

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");
            return null;
        } else {
            try {
                String query = "SELECT * FROM customer_email WHERE cus_id = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedCusId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    email = r.getString("email");

                    list.add(email);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException | NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error("Exception tag --> " + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement :" + e.getMessage());

                } else if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry for list");

                }
                return null;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return null;
            }

        }
        return list;
    }

    public ArrayList customerFaxLoading(String cus_id) {

        String encodedCusId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cus_id);
        String vehicle_no = null;
        ArrayList list = new ArrayList();

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");
            return null;
        } else {
            try {
                String query = "SELECT * FROM customer_vehicle_no WHERE cus_id = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedCusId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    vehicle_no = r.getString("vehicle_no");

                    list.add(vehicle_no);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException | NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error("Exception tag --> " + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement");

                } else if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry for list");

                }
                return null;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return null;
            }

        }
        return list;
    }
    
    public ArrayList customerDriverLoading(String cus_id) {

        String encodedCusId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cus_id);
        String driver = null;
        ArrayList list = new ArrayList();

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");
            return null;
        } else {
            try {
                String query = "SELECT * FROM drivers WHERE cus_id = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedCusId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    driver = r.getString("driver");

                    list.add(driver);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException | NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error("Exception tag --> " + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement");

                } else if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry for list");

                }
                return null;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return null;
            }

        }
        return list;
    }

    public Boolean deleteCustomerInfoForUpdate(String cus_id) {
        String encodedCusId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cus_id);

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");
            return null;
        } else {
            try {

                String query = "DELETE FROM customer WHERE cus_id = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedCusId);

                int val = pstmt.executeUpdate();
                if (val == 1) {
                    return true;
                } else {
                    return false;
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
    }

    public Boolean deleteCustomerTelephoneForUpdate(String cus_id) {

        String encodedCusId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cus_id);

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");
            return null;
        } else {
            try {

                String query = "DELETE FROM customer_tel WHERE cus_id= ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedCusId);

                int val = pstmt.executeUpdate();
                if (val == 1) {
                    return true;
                } else {
                    return false;
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
    }

    public Boolean deleteCustomerMobileForUpdate(String cus_id) {

        String encodedCusId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cus_id);

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");
            return null;
        } else {
            try {

                String query = "DELETE FROM customer_mobile WHERE cus_id= ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedCusId);

                int val = pstmt.executeUpdate();

                if (val == 1) {
                    return true;
                } else {
                    return false;
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
    }

    public Boolean deleteCustomerEmailForUpdate(String cus_id) {

        String encodedCusId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cus_id);

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");
            return null;
        } else {
            try {

                String query = "DELETE FROM customer_email WHERE cus_id= ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedCusId);

                int val = pstmt.executeUpdate();

                if (val == 1) {
                    return true;
                } else {
                    return false;
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
    }

    public Boolean deleteCustomerFaxForUpdate(String cus_id) {

        String encodedCusId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cus_id);

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");
            return null;
        } else {
            try {

                String query = "DELETE FROM customer_vehicle_no WHERE cus_id= ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedCusId);

                int val = pstmt.executeUpdate();
                if (val == 1) {
                    return true;
                } else {
                    return false;
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
    }
    
    public Boolean deleteCustomerDriverForUpdate(String cus_id) {

        String encodedCusId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cus_id);

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");
            return null;
        } else {
            try {

                String query = "DELETE FROM drivers WHERE cus_id= ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedCusId);

                int val = pstmt.executeUpdate();
                if (val == 1) {
                    return true;
                } else {
                    return false;
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
    }

    public String customerVerificationType(String cus_id) {

        String encodedCusId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cus_id);
        String Verification_type = null;

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * FROM customer WHERE cus_id = ?";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedCusId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    Verification_type = r.getString("cus_verification_type");

                }

            } catch (SQLException | NullPointerException e) {

                if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement");

                } else if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry for list");

                }
                return null;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return null;
            }
        }

        return Verification_type;

    }

    public ArrayList<String> customerInformation(String cus_id) {

        String encodedCusId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cus_id);

        String cus_name = null;
        String cus_address = null;
  //      String cus_nic = null;
        String cus_title = null;
  //      String cus_nationality = null;
  //      String cus_id_type = null;
  //      String cus_dob = null;
  //      String cus_profession = null;
        String cus_type = null;
        
        ArrayList<String> list = new ArrayList<>();

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * FROM customer  WHERE cus_id = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedCusId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    cus_id = r.getString("cus_id");
                    cus_title = r.getString("cus_title");
                    cus_name = r.getString("cus_name");
      //              cus_id_type = r.getString("customer.cus_nic_passport_id_Type");
      //              cus_nic = r.getString("customer.cus_nic_passport_id");
                    cus_address = r.getString("cus_address");
       //             cus_nationality = r.getString("customer.cus_nationality");
       //             cus_dob = r.getString("customer.cus_dob");
        //            cus_profession = r.getString("profession.profession");
                    cus_type = r.getString("cus_type");

                    list.add(cus_id);
                    list.add(cus_title);
                    list.add(cus_name);
         //           list.add(cus_id_type);
        //            list.add(cus_nic);
                    list.add(cus_address);
         //           list.add(cus_nationality);
         //           list.add(cus_dob);
          //          list.add(cus_profession);
                    list.add(cus_type);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException | NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error("Exception tag --> " + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement");

                } else if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry for list");

                }
                return null;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return null;
            }
        }

        return list;
//        return customer_info;

    }

    public ArrayList<String> loadingProfession() {

        String profession = null;

        ArrayList list = new ArrayList();

        if (star.con == null) {

            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * FROM profession ";

                PreparedStatement pstmt = star.con.prepareStatement(query);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    profession = r.getString("profession");

                    list.add(profession);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException | NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error("Exception tag --> " + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement");

                } else if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry for list");

                }
                return null;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return null;
            }
        }
        return list;
    }

  /*  public String searchProfessionId(String profession) {

        String encodedProfession = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, profession);
        String id = null;

        ArrayList list = new ArrayList();

        if (star.con == null) {

            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * FROM profession where profession= ?";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedProfession);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    id = r.getString("id");

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException | NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error("Exception tag --> " + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement");

                } else if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry for list");

                }
                return null;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return null;
            }
        }
        return id;
    } */

    public Boolean insertProfession(
            String profession
    ) {

        String encodedProfession = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, profession);

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");
            return null;
        } else {
            try {
                PreparedStatement ps = star.con.prepareStatement("INSERT INTO profession(profession) VALUES(?)");
                ps.setString(1, encodedProfession);

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

                    log.error("Exception tag --> " + "Invalid sql statement");

                }
                return false;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return false;
            }
        }
    }

    public Boolean updateCustomerInfo(
            String cusId,
            String cusName,
            String cusAddress,
            //String cusNic,
            String cusTitle,
            String cusVat
    //String cusDob,
    //String cusNationality,
    //String cusVerification,
    //int cusProfession
    ) {

        String encodedCusId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cusId);
        String encodedCusName = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cusName);
        String encodedCusAddress = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cusAddress);
        //String encodedCusNic = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cusNic);
        String encodedCusTitle = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cusTitle);
        String encodedCusVat = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cusVat);
        //String encodedCusDob = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cusDob);
        //String encodedCusNationality = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cusNationality);
        //String encodedCusVerification = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cusVerification);
        if (star.con == null) {
            log.error("Databse connection failiure.");
            return null;
        } else {
            try {

                String query = "UPDATE customer set cus_name = ? ,cus_address = ?,"
                        + "cus_title = ?,cus_type = ? WHERE cus_id=?";
                PreparedStatement ps = star.con.prepareStatement(query);

                ps.setString(1, encodedCusName);
                ps.setString(2, encodedCusAddress);
                //ps.setString(3, encodedCusNic);
                ps.setString(3, encodedCusTitle);
                ps.setString(4, encodedCusVat);
                //ps.setString(5, encodedCusDob);
                //ps.setString(6, encodedCusNationality);
                //ps.setString(7, encodedCusVerification);
                //ps.setInt(8, cusProfession);
                ps.setString(5, encodedCusId);

                int val = ps.executeUpdate();

                if (val == 1) {
                    return true;
                } else {
                    return false;
                }

            } catch (ArrayIndexOutOfBoundsException | NullPointerException | NumberFormatException | SQLException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {
                    log.error("Exception tag --> " + "Invalid entry location for list");
                } else if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry passed");
                } else if (e instanceof NumberFormatException) {
                    log.error("Exception tag --> " + "Invalid number found in current id");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement " + e.getMessage());
                }
                return null;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return null;
            }
        }
    }

    public boolean checkingProfessionAvailability(String profession) {

        String encodedCusId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, profession);
        boolean available = false;

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");

        } else {
            try {

                String query = "SELECT * FROM profession where profession = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedCusId);

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
    
        public Boolean insertCustomerType(
            String customerType) {

        if (star.con == null) {

            log.error("Exception tag --> " + "Database connection failiure. ");
            return null;

        } else {
            try {

                PreparedStatement ps = star.con.prepareStatement("INSERT INTO "
                        + "customer_type(customer_type) VALUES(?)");
                ps.setString(1, customerType);

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

                    log.error("Exception tag --> " + "Invalid sql statement");

                }
                return false;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return false;
            }
        }
    }
    

        public ArrayList loadCustomerType() {

        String customerType = null;
        ArrayList customerTypeList = new ArrayList();

        if (star.con == null) {
            log.error("Databse connection failiure.");
        } else {
            try {
                Statement stt = star.con.createStatement();
                ResultSet r = stt.
                        executeQuery("SELECT * FROM customer_Type");
                while (r.next()) {
                    customerType = r.getString("customer_type");

                    customerTypeList.add(customerType);
                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {
                if (e instanceof ArrayIndexOutOfBoundsException) {
                    log.error("Exception tag --> "
                            + "Invalid entry location for list");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement "
                            + e.getMessage());
                } else if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry for list");
                }
                return null;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return null;
            }
        }
        return customerTypeList;
    }
        
        public boolean deleteCustomerType(String customerType) {
        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return false;
        } else {
            String encodedDesc = ESAPI.encoder().
                    encodeForSQL(ORACLE_CODEC, customerType);
            try {
                String sql = "DELETE FROM customer_type where `customer_type`= ? ";
                PreparedStatement stmt = Starter.con.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, encodedDesc);
                int val = stmt.executeUpdate();
                if (val == 1) {
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                log.error("Exception tag --> " + "Invalid sql statement " + e.
                        getMessage());
                return false;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return false;
            }
        }
    }
}
