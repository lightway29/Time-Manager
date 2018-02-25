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

public class InvoiceDAO {

    public static Starter star;//db connection
    Codec ORACLE_CODEC = new OracleCodec();
    private final Logger log = Logger.getLogger(this.getClass());

    public String generateId() {

        Integer id = null;
        String cid = null;
        String final_id = null;
        if (star.con == null) {
            log.error("Database connection failiure.");
            return null;
        } else {
            try {

                Statement st = star.con.createStatement();
                Statement ste = star.con.createStatement();
                ResultSet rs = st.executeQuery(
                        "SELECT MAX(id) as ID FROM invoice ");

                while (rs.next()) {
                    id = rs.getInt("id");
                }
                ResultSet rss = ste.executeQuery(
                        "SELECT inv_no FROM invoice WHERE id= " + id + "");

                while (rss.next()) {
                    cid = rss.getString("inv_no");
                }

                if (id != 0) {
                    String original = cid.split("T")[1];
                    int i = Integer.parseInt(original) + 1;

                    if (i < 10) {
                        final_id = "INT000" + i;
                    } else if (i >= 10 && i < 100) {
                        final_id = "INT00" + i;
                    } else if (i >= 100 && i < 1000) {
                        final_id = "INT0" + i;
                    } else if (i >= 1000 && i < 10000) {
                        final_id = "INT" + i;
                    }
                    return final_id;

                } else {
                    return "INT0001";
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

    public ArrayList loadCustomerType() {

        String customerType = null;
        ArrayList cutomerTypeList = new ArrayList();

        if (star.con == null) {
            log.error("Database connection failiure.");
        } else {
            try {
                Statement stt = star.con.createStatement();
                ResultSet r = stt.
                        executeQuery("SELECT * FROM customer_type");
                while (r.next()) {
                    customerType = r.getString("customer_type");

                    cutomerTypeList.add(customerType);
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
        return cutomerTypeList;
    }

    public ArrayList loadUnitType() {

        String unitType = null;
        ArrayList unitTypeList = new ArrayList();

        if (star.con == null) {
            log.error("Database connection failiure.");
        } else {
            try {
                Statement stt = star.con.createStatement();
                ResultSet r = stt.
                        executeQuery("SELECT * FROM item_unit");
                while (r.next()) {

                    unitType = r.getString("unit");

                    unitTypeList.add(unitType);
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
        return unitTypeList;
    }

    public ArrayList loadVehicleNo(String customerId) {

        String vehicleNo = null;
        ArrayList vehicleNoList = new ArrayList();
        String encodedSearch = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                customerId);

        if (star.con == null) {
            log.error("Database connection failiure.");
        } else {
            try {

                String query = "SELECT * FROM customer_vehicle_no WHERE"
                        + " cus_id = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedSearch);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    vehicleNo = r.getString("vehicle_no");

                    vehicleNoList.add(vehicleNo);

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
        return vehicleNoList;
    }

    public ArrayList loadDriver(String customerId) {

        String driver = null;
        ArrayList driverList = new ArrayList();
        String encodedSearch = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                customerId);

        if (star.con == null) {
            log.error("Database connection failiure.");
        } else {
            try {

                String query = "SELECT * FROM drivers WHERE"
                        + " cus_id = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedSearch);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    driver = r.getString("driver");

                    driverList.add(driver);

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
        return driverList;
    }

    public String getDriverId(String driver, String customerId) {

        String driverId = null;
        ArrayList driverList = new ArrayList();
        String encodedDriver = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                driver);

        String encodedCustomerId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                customerId);

        if (star.con == null) {
            log.error("Database connection failiure.");
        } else {
            try {

                String query = "SELECT * FROM drivers WHERE"
                        + " cus_id = ? AND driver = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedCustomerId);
                pstmt.setString(2, encodedDriver);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    driverId = r.getString("id");

                    return driverId;

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
        return null;
    }

    public Boolean insertInvoice(
            String invoiceNo,
            String date,
            String salesExecutive,
            String customerType,
            String cusID,
            String vehicleNo,
            Double totaldiscount,
            String paymentTerm,
            Double total,
            Double netAmt,
            String amountInWords,
            String userID,
            String remarks
    ) {

        String encodedInvoiceNo = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                invoiceNo);

        String encodedDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, date);

        String encodedCusID = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, cusID);

        String encodedCusType = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                customerType);
        String encodedVehicleNo = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                vehicleNo);
        String encodedSalesExecutive = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, salesExecutive);
        String encodedPaymentTerm = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                paymentTerm);

        String encodedAmountInWords = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                amountInWords);
        String encodedUserID = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, userID);

        if (star.con == null) {
            log.error("Database Connection failure");
            return false;
        } else {

            try {
                PreparedStatement ps = star.con.prepareStatement(
                        "INSERT INTO `invoice` ("
                        + "`inv_no`,"
                        + " `date`, "
                        + " `salse_executive`,"
                        + " `cus_type`,"
                        + " `cus_id`,"
                        + " `vehicle_no`,"
                        + "`total_discount`,"
                        + " `payment_term`,"
                        + " `total`,"
                        + "`net_amount`,"
                        + " `net_amount_word`,"
                        + " `user_id`,"
                        + " `remarks` "
                        + " )"
                        + " VALUES "
                        + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

                ps.setString(1, encodedInvoiceNo);
                ps.setString(2, encodedDate);
                ps.setString(3, encodedSalesExecutive);
                ps.setString(4, encodedCusType);
                ps.setString(5, encodedCusID);
                ps.setString(6, encodedVehicleNo);
                ps.setDouble(7, totaldiscount);
                ps.setString(8, encodedPaymentTerm);
                ps.setDouble(9, total);
                ps.setDouble(10, netAmt);
                ps.setString(11, amountInWords);
                ps.setString(12, userID);
                ps.setString(13, remarks);

                int val = ps.executeUpdate();
                if (val == 1) {
                    return true;
                } else {
                    return false;
                }

            } catch (SQLException ex) {
                if (ex instanceof SQLException) {
                    log.error("Exception Tag -->" + "Invalid Sql Statement");
                    ex.printStackTrace();
                }
                return false;
            } catch (Exception e) {
                log.error("Exception Tag -->" + "Error");
                return false;
            }
        }

    }

    public Boolean insertInvoiceMeterReading(
            String invoiceNo,
            String meterReading,
            String nextMeterReading
    ) {

        String encodedInvoiceNo = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                invoiceNo);

        String encodedServiceMeterReading = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, meterReading);

        String encodedNextMeterReading = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, nextMeterReading);

        if (star.con == null) {
            log.error("Database Connection failure");
            return false;
        } else {

            try {
                PreparedStatement ps = star.con.prepareStatement(
                        "INSERT INTO `invoice_meter` ("
                        + "`invoice_no`,"
                        + " `service_meter_reading`, "
                        + " `next_service_meter_reading`"
                        + " )"
                        + " VALUES "
                        + "(?, ?, ?)");

                ps.setString(1, encodedInvoiceNo);
                ps.setString(2, encodedServiceMeterReading);
                ps.setString(3, encodedNextMeterReading);

                int val = ps.executeUpdate();
                if (val == 1) {
                    return true;
                } else {
                    return false;
                }

            } catch (SQLException ex) {
                if (ex instanceof SQLException) {
                    log.error("Exception Tag -->" + "Invalid Sql Statement");
                    ex.printStackTrace();
                }
                return false;
            } catch (Exception e) {
                log.error("Exception Tag -->" + "Error");
                return false;
            }
        }

    }

    public Boolean insertInvoiceDriver(
            String invoiceNo,
            String driverId
    ) {

        String encodedInvoiceNo = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                invoiceNo);

        String encodedServiceMeterReading = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, driverId);

        if (star.con == null) {
            log.error("Database Connection failure");
            return false;
        } else {

            try {
                PreparedStatement ps = star.con.prepareStatement(
                        "INSERT INTO `invoice_driver` ("
                        + "`invoice_id`,"
                        + " `driver_id` "
                        + " )"
                        + " VALUES "
                        + "(?, ?)");

                ps.setString(1, encodedInvoiceNo);
                ps.setString(2, encodedServiceMeterReading);

                int val = ps.executeUpdate();
                if (val == 1) {
                    return true;
                } else {
                    return false;
                }

            } catch (SQLException ex) {
                if (ex instanceof SQLException) {
                    log.error("Exception Tag -->" + "Invalid Sql Statement");
                    ex.printStackTrace();
                }
                return false;
            } catch (Exception e) {
                log.error("Exception Tag -->" + "Error");
                return false;
            }
        }

    }

    public ArrayList<ArrayList<String>> searchItemDetails(String search) {

        String encodedSearch = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, search);

        String itemId = null;
        String itemDescription = null;
        String itemName = null;
        String partNo = null;
//        String cus_title = null;

        ArrayList<ArrayList<String>> Mainlist
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * FROM item i"
                        + " join item_main_category m  ON"
                        + " i.item_main_category = m.id join"
                        + " item_sub_category s ON"
                        + " i.item_sub_category = s.id"
                        + " WHERE (i.item_id LIKE ?"
                        + " OR i.item_name LIKE ? "
                        + " OR i.item_description LIKE ? "
                        + " OR i.part_no LIKE ? "
                        + " OR m.title LIKE ?"
                        + " OR s.item_sub_category LIKE ? )";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedSearch + "%");
                pstmt.setString(2, encodedSearch + "%");
                pstmt.setString(3, encodedSearch + "%");
                pstmt.setString(4, encodedSearch + "%");
                pstmt.setString(5, encodedSearch + "%");
                pstmt.setString(6, encodedSearch + "%");
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    itemId = r.getString("i.item_id");
                    itemDescription = r.getString("i.item_description");
                    itemName = r.getString("i.item_name");
                    partNo = r.getString("i.part_no");

                    list.add(itemId);
                    list.add(itemDescription);
                    list.add(itemName);
                    list.add(partNo);

                    Mainlist.add(list);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error("Exception tag --> "
                            + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement "
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

//    public Boolean insertInvoice(
//            String invoiceNo,
//            String isTaxInvoice,
//            String date,
//            String poNo,
//            String poDate,
//            String cusId,
//            String creditAllowed,
//            String salesExecutive,
//            String paymentTerm,
//            String warrantyNo,
//            double total,
//            double nbt,
//            double vat,
//            double netAmount,
//            String amountInWords,
//            String userId) {
//
//       
//
//        String encodedInvoiceNo = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
//                invoiceNo);
//        String encodedIsTaxInvoice = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
//                isTaxInvoice);
//        String encodedDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
//                date);
//        String encodedPONO = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
//                poNo);
//        String encodedUserId = ESAPI.encoder().
//                encodeForSQL(ORACLE_CODEC, userId);
//
//        String encodedPODate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
//                poDate);
//        String encodedCusId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
//                cusId);
//        String encodedCreditAllowed = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
//                creditAllowed);
//        String encodedSalesExecutive = ESAPI.encoder().
//                encodeForSQL(ORACLE_CODEC,
//                        salesExecutive);
//
//        String encodedWarrantyNo = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
//                warrantyNo);
//
//       
//        String encodedAmountInWords = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
//                amountInWords);
//        String encodedPaymentTerm = ESAPI.encoder().
//                encodeForSQL(ORACLE_CODEC, paymentTerm);
//
//        if (star.con == null) {
//            log.error("Databse connection failiure.");
//            return false;
//        } else {
//            try {
//
//                PreparedStatement ps = star.con.prepareStatement(
//                        "INSERT INTO `invoice` ("
//                        + " `inv_no`, "
//                        + "`is_tax_inv`, "
//                        + "`date`,"
//                        + " `po_no`,"
//                        + " `po_date`, "
//                        + "`cus_id`, "
//                        + "`credit_allow`, "
//                        + "`salse_executive`, "
//                        + "`payment_term`, "
//                        + "`warrenty_no`,"
//                        + " `total`,"
//                        + " `nbt`, "
//                        + "`vat`, "
//                        + "`net_amount`,"
//                        + " `net_amount_word`,"
//                                + " `user_id`"
//                        + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
//                ;
//
//                ps.setString(1, encodedInvoiceNo);
//                ps.setString(2, encodedIsTaxInvoice);
//                ps.setString(3, encodedDate);
//                ps.setString(4, encodedPONO);
//
//                ps.setString(5, encodedPODate);
//                ps.setString(6, encodedCusId);
//                ps.setString(7, encodedCreditAllowed);
//                ps.setString(8, encodedSalesExecutive);
//                ps.setString(9, encodedPaymentTerm);
//
//                ps.setString(10, encodedWarrantyNo);
//                 ps.setDouble(11, total);
//                ps.setDouble(12, nbt);
//                ps.setDouble(13, vat);
//                ps.setDouble(14, netAmount);
//                ps.setString(15, encodedAmountInWords);
//
//                ps.setString(16, encodedUserId);
//
//                int val = ps.executeUpdate();
//                if (val == 1) {
//                    return true;
//                } else {
//                    return false;
//                }
//
//            } catch (SQLException e) {
//
//                if (e instanceof SQLException) {
//                    log.error("Exception tag --> " + "Invalid sql statement "
//                            + e.getMessage());
//                }
//                return false;
//
//            } catch (Exception e) {
//                log.error("Exception tag --> " + "Error");
//                return false;
//            }
//        }
//    }
    public Boolean insertInvoiceItems(
            String invoiceNo,
            String itemCode,
            String description,
            String batchNo,
            double qty,
            double discountRate,
            double discount,
            double price,
            double netPrice
    ) {
        String encodedInvoiceNo = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                invoiceNo);
        String encodedItemCode = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                itemCode);
//        String encodedDescription = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
//                description);
        String encodedDescription = description;

        String encodedBatchNo = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                batchNo);

        
        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {
            try {

                PreparedStatement ps = star.con.prepareStatement(
                        "INSERT INTO `invoice_item`("
                        + " `inv_no`,"
                        + " `item_id`,"
                        + " `description`,"
                        + " `batch_no`,"
                        + " `qty`,"
                        + " `discount_rate`,"
                        + " `discount`,"
                        + " `price`,"
                        + " `net_price`, "
                       // + " `part_no`,"
                       // + " `unit`,"
                        + " `unit_qty`"
                        + " )"
                        + " VALUES "
                        + "(?,?,?,?,?,?,?,?,?,?,?,?)");

                ps.setString(1, encodedInvoiceNo);
                ps.setString(2, encodedItemCode);
                ps.setString(3, encodedDescription);
                ps.setString(4, batchNo);
                ps.setDouble(5, qty);
                ps.setDouble(6, discountRate);
                ps.setDouble(7, discount);
                ps.setDouble(8, price);
                ps.setDouble(9, netPrice);
               // ps.setString(10, "partNo");
               // ps.setString(11, "unit");
                ps.setInt(10, 00);

                int val = ps.executeUpdate();
                if (val == 1) {
                    return true;
                } else {
                    return false;
                }

            } catch (SQLException e) {

                if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement "
                            + e);
                }
                return false;

            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return false;
            }
        }
    }

    /* public ArrayList<String> loadCustomerInfo(String CusID) {
     String EncodedCusID = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, CusID);

     ArrayList newList = new ArrayList();
     String cusId = null;
     String cusIdType = null;

     if (star.con == null) {
     log.error("Database connection failed");
     } else {

     try {
     PreparedStatement ps = star.con.prepareStatement("select * from customer where cus_id = ?");
     ps.setString(1, EncodedCusID);
     ResultSet r = ps.executeQuery();

     while (r.next()) {
     cusIdType = r.getString("cus_nic_passport_id_Type");
     newList.add(cusIdType);
     }

     } catch (SQLException ex) {
     java.util.logging.Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex);
     }

     }
     return newList;
     }*/
    public ArrayList<String> loadInvoiceInfo(String InvoiceID) {
        String EncodedInvoiceID = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                InvoiceID);

        ArrayList newList = new ArrayList();
        String InvoiceNo = null;

        String date = null;

        String total = null;
        String netAmount = null;
        String discount = null;
        String amountInWords = null;
        String remarks = null;
        String cus_type = null;
        String vehicleNo = null;
        String driver = null;
        String customerId = null;
        String meterReading = null;
        String nextMeterReading = null;

        if (star.con == null) {
            log.error("Database Connection Failure");
        } else {
            PreparedStatement ps;
            try {
                ps = star.con.prepareStatement(
                        "SELECT * FROM invoice i join invoice_driver d ON "
                        + " i.inv_no = d.invoice_id JOIN "
                        + " drivers dv ON "
                        + " d.driver_id = dv.id JOIN "
                        + " invoice_meter m ON "
                        + " i.inv_no = m.invoice_no "
                        + " WHERE i.inv_no LIKE ?");
                ps.setString(1, EncodedInvoiceID);
                ResultSet r = ps.executeQuery();

                while (r.next()) {

                    date = r.getString("i.date");
                    total = r.getString("i.total");
                    netAmount = r.getString("i.net_amount");
                    discount = r.getString("i.total_discount");
                    amountInWords = r.getString("i.net_amount_word");
                    remarks = r.getString("i.remarks");
                    cus_type = r.getString("i.cus_type");
                    vehicleNo = r.getString("i.vehicle_no");
                    driver = r.getString("dv.driver");
                    customerId = r.getString("i.cus_id");
                    meterReading = r.getString("m.service_meter_reading");
                    nextMeterReading = r.getString(
                            "m.next_service_meter_reading");

                    newList.add(date);
                    newList.add(total);
                    newList.add(netAmount);
                    newList.add(discount);
                    newList.add(amountInWords);
                    newList.add(remarks);
                    newList.add(cus_type);
                    newList.add(vehicleNo);
                    newList.add(driver);
                    newList.add(customerId);
                    newList.add(meterReading);
                    newList.add(nextMeterReading);

                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
        return newList;
    }

    public ArrayList<String> loadInvoiceCusInfo(String InvoiceID) {
        String EncodedInvoiceID = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                InvoiceID);

        ArrayList newList = new ArrayList();
        String cusName = null;
        String cusAddress = null;
        String salesExecutive = null;
        String warrentyPeriod = null;
        String warrenty_month_year = null;
        String paymentTerms = null;

        if (star.con == null) {
            log.error("Database Connection Failure");
        } else {
            PreparedStatement ps;
            try {
                ps = star.con.prepareStatement(
                        "SELECT * FROM invoice join customer on invoice.cus_id=customer.cus_id WHERE inv_no LIKE ?");
                ps.setString(1, EncodedInvoiceID);
                ResultSet r = ps.executeQuery();

                while (r.next()) {

                    cusName = r.getString("cus_name");
                    salesExecutive = r.getString("salse_executive");
                    paymentTerms = r.getString("payment_term");

                    newList.add(cusName);
                    newList.add(salesExecutive);
                    newList.add(paymentTerms);
                }

            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(InvoiceDAO.class.getName()).
                        log(Level.SEVERE, null, ex);
            }

        }
        return newList;
    }

    public ArrayList<ArrayList<String>> searchInvoiceDetails(String search) {

        String encodedSearch = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, search);

        String invoiceNo = null;

        String date = null;
        String salesExecutive = null;
        String cutomerType = null;
        String vehicleNo = null;

        ArrayList<ArrayList<String>> Mainlist
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * FROM invoice WHERE inv_no LIKE ? AND status = 0 ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedSearch + "%");

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    invoiceNo = r.getString("inv_no");
                    salesExecutive = r.getString("salse_executive");
                    date = r.getString("date");
                    cutomerType = r.getString("cus_type");
                    vehicleNo = r.getString("vehicle_no");

                    list.add(invoiceNo);
                    list.add(salesExecutive);
                    list.add(date);
                    list.add(cutomerType);
                    list.add(vehicleNo);

                    Mainlist.add(list);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error("Exception tag --> "
                            + "Invalid entry location for list" + e);

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

    public ArrayList<ArrayList<String>> searchInvoiceItems(String search) {
        String encodedSearch = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, search);

        String itemCode = null;
        String description = null;
        String batchNo = null;
        String quantity = null;
        String discount = null;
        String discountAmt = null;
        String unitPrice = null;
        String value = null;

        ArrayList<ArrayList<String>> MainList
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {
            log.error("Database connection failure");
            return null;
        } else {
            try {

                String query = "select * from invoice_item where inv_no = ?";

                PreparedStatement ps = star.con.prepareStatement(query);

                ps.setString(1, encodedSearch);

                ResultSet r = ps.executeQuery();

                while (r.next()) {
                    ArrayList<String> list = new ArrayList<String>();

                    itemCode = r.getString("item_id");
                    description = r.getString("description");
                    batchNo = r.getString("batch_no");
                    quantity = r.getString("qty");
                    discount = r.getString("discount_rate");
                    discountAmt = r.getString("discount");
                    unitPrice = r.getString("price");
                    value = r.getString("net_price");

                    list.add(itemCode);
                    list.add(description);
                    list.add(batchNo);
                    list.add(quantity);
                    list.add(discount);
                    list.add(discountAmt);
                    list.add(unitPrice);
                    list.add(value);

                    MainList.add(list);

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

        return MainList;
    }

    public ArrayList<String> loadingCustomerInfo(String customerId) {
        String encodedCusId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                customerId);
        ArrayList list = new ArrayList();
        String cusId = null;
        String cusName = null;
        String cusAddress = null;
        String cusTitle = null;

        if (star.con == null) {
            log.error("Databse connection failiure.");
        } else {

            try {
                Statement stt = star.con.createStatement();
                ResultSet r = stt.executeQuery(
                        "SELECT * FROM customer WHERE cus_id ='" + encodedCusId
                        + "'");
                while (r.next()) {

                    cusAddress = r.getString("cus_address");
                    cusTitle = r.getString("cus_title");

                    list.add(cusAddress);
                    list.add(cusTitle);

                }
            } catch (NullPointerException | SQLException e) {
                if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry passed");
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
        return list;
    }

    public ArrayList<String> loadingItemInfo(String ItemID) {

        String encodedSearch = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, ItemID);
        ArrayList list = new ArrayList();
        String itemId = null;
        String batchNo = null;
        String itemName = null;
        String sellingPrice = null;
        String unit = null;
        String unitValue = null;
        //String description = null;

        if (star.con == null) {
            log.error("Database Connection Failure");
        } else {
            try {

                String query = "SELECT * FROM item i "
                        + " JOIN item_sub s on "
                        + " i.item_id = s.item_id "
                        + " JOIN item_unit_value uv "
                        + " ON s.unit = uv.id "
                        + " JOIN item_unit u "
                        + " ON u.id = uv.item_unit "
                        + " WHERE "
                        + " i.item_id = ? ";

                PreparedStatement ps = star.con.prepareStatement(query);

                ps.setString(1, encodedSearch);

                ResultSet r = ps.executeQuery();

                while (r.next()) {

                    itemName = r.getString("i.item_name");
                    sellingPrice = r.getString("s.selling_price");
                    batchNo = r.getString("s.batch_no");
                    unit = r.getString("u.unit");
                    unitValue = r.getString("uv.unit_qty");

                    list.add(itemName);
                    list.add(sellingPrice);
                    list.add(batchNo);
                    list.add(unit);
                    list.add(unitValue);

                }

            } catch (NullPointerException | SQLException e) {
                if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry passed");
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
        return list;
    }

    public boolean updateItemTableQty(String itemCode,
            String batchNo, double qtyy) {
        ArrayList<String> itemList = new ArrayList<>();

        String encodedItemCode = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                itemCode);
        String encodedBatchNo = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                batchNo);

        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return false;
        } else {

            try {
                String sql = "update item i inner join item_sub it on "
                        + "(i.item_id = it.item_id)"
                        + "set i.qty = i.qty - ?,it.qty = it.qty - ?"
                        + "where i.item_id = ? AND "
                        + "it.batch_no = ?";
                PreparedStatement ps = star.con.prepareStatement(sql);

                ps.setDouble(1, qtyy);
                ps.setDouble(2, qtyy);
                ps.setString(3, encodedItemCode);
                ps.setString(4, encodedBatchNo);

                int val = ps.executeUpdate();

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

    public boolean cancelInvoice(String invoiceNo, int status) {
        String encodedInvoiceNo = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                invoiceNo);

        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return false;
        } else {
            try {
                String sql = "UPDATE `invoice` SET `status`=? WHERE "
                        + "`inv_no`=?";

                PreparedStatement ps = star.con.prepareStatement(sql);
                ps.setInt(1, status);
                ps.setString(2, encodedInvoiceNo);

                ps.executeUpdate();

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
        return true;
    }

    public boolean updateItem_InvoiceDeleted(String itemCode,
            String batchNo, double qtyy) {
        ArrayList<String> itemList = new ArrayList<>();
        String encodedItemCode = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                itemCode);
        String encodedBatchNo = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                batchNo);

        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return false;
        } else {

            try {
                String sql = "update item i inner join item_sub it on "
                        + "(i.item_id = it.item_id)"
                        + "set i.qty = i.qty + ?,it.qty = it.qty + ?"
                        + "where i.item_id = ? AND "
                        + "it.batch_no = ?";
                PreparedStatement ps = star.con.prepareStatement(sql);

                ps.setDouble(1, qtyy);
                ps.setDouble(2, qtyy);
                ps.setString(3, encodedItemCode);
                ps.setString(4, encodedBatchNo);

                int val = ps.executeUpdate();

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

    public boolean checkInvoiceAvailability(String invId) {
        String encodedInvId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                invId);

        boolean available = false;

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {

            try {
                String query
                        = "SELECT * FROM  invoice where inv_no= ? ";
                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedInvId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    available = true;
                }

            } catch (NullPointerException | SQLException e) {

                if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry passed");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement "
                            + e.getMessage());
                }
                return false;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return false;
            }
        }
        return available;
    }
    
    public Boolean insertVehicle(String customerId,
            String vehicleNo) {
        
        String encodedCustomerId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                customerId);
        

        if (star.con == null) {

            log.error("Exception tag --> " + "Database connection failiure. ");
            return null;

        } else {
            try {

                PreparedStatement ps = star.con.prepareStatement("INSERT INTO "
                        + " customer_vehicle_no ("
                        + " cus_id, "
                        + " vehicle_no "
                        + ") VALUES(?,? )");
                ps.setString(1, customerId);
                ps.setString(2, vehicleNo);

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
     
    
}
