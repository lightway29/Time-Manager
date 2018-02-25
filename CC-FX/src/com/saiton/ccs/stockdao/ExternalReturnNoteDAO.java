/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.stockdao;

import com.saiton.ccs.database.Starter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

/**
 *
 * @author Miren
 */
public class ExternalReturnNoteDAO {

    public static Starter star;//db connection
    Codec ORACLE_CODEC = new OracleCodec();
    private Logger log = Logger.getLogger(this.getClass());

    public String generateID() {

        Integer id = null;
        String cid = null;
        String finalId = null;
        if (star.con == null) {
            log.error("Databse connection failiure.");
            return null;
        } else {
            try {

                Statement st = star.con.createStatement();
                Statement ste = star.con.createStatement();
                ResultSet rs = st.executeQuery(
                        "SELECT MAX(id) as ID FROM external_return_note");

                while (rs.next()) {
                    id = rs.getInt("id");
                }
                ResultSet rss = ste.executeQuery(
                        "SELECT external_return_note_id FROM external_return_note WHERE id= "
                        + id + "");

                while (rss.next()) {
                    cid = rss.getString("external_return_note_id");
                }

                if (id != 0) {
                    String original = cid.split("N")[1];
                    int i = Integer.parseInt(original) + 1;

                    if (i < 10) {
                        finalId = "ERN000" + i;
                    } else if (i >= 10 && i < 100) {
                        finalId = "ERN00" + i;
                    } else if (i >= 100 && i < 1000) {
                        finalId = "ERN0" + i;
                    } else if (i >= 1000) {
                        finalId = "ERN" + i;
                    }
                    return finalId;

                } else {
                    return "ERN0001";
                }
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException |
                    SQLException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {
                    log.error("Exception tag --> " + "Split character error");
                } else if (e instanceof NumberFormatException) {
                    log.error(
                            "Exception tag --> "
                            + "Invalid number found in current id");
                } else if (e instanceof SQLException) {
                    log.error(
                            "Exception tag --> " + "Invalid sql statement " + e.
                            getMessage());
                }
                return null;

            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return null;
            }
        }
    }

    public ArrayList<ArrayList<String>> searchItemDetails(String item,
            String supplierId) {

        String encodedItem = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, item);
        String encodedSupplierId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                supplierId);

        String itemId = null;
        String batchNo = null;
        String itemName = null;
        String qty = null;

        ArrayList<ArrayList<String>> mainList
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query
                        = "select i.item_id,ie.batch_no,i.item_name,i.qty from "
                        + "supplier s join (supplier_item si,item i,item_sub ie)"
                        + " on s.sid = si.sid and si.item_id = i.item_id and "
                        + "i.item_id = ie.item_id where s.sid = ? and "
                        + "(i.item_id LIKE ? or i.item_name LIKE ? )";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedSupplierId);
                pstmt.setString(2, encodedItem + "%");
                pstmt.setString(3, encodedItem + "%");
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    itemId = r.getString("i.item_id");
                    batchNo = r.getString("ie.batch_no");
                    itemName = r.getString("i.item_name");
                    qty = r.getString("i.qty");

                    list.add(itemId);
                    list.add(batchNo);
                    list.add(itemName);
                    list.add(qty);

                    mainList.add(list);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error(
                            "Exception tag --> "
                            + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement"
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
        return mainList;
    }

    public Boolean insertReturnNote(
            String returnNoteId,
            String returnDate,
            String description,
            String userId,
            String supplierId) {

        String encodedReturnNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                returnNoteId);
        String encodedReturnDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                returnDate);
        String encodedDescription = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                description);
        String encodedSupplierId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                supplierId);
        String encodedUserId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, userId);

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {
            try {

                PreparedStatement ps = star.con.prepareStatement(
                        "INSERT INTO external_return_note(`external_return_note_id`, "
                        + "`return_note_date`, `description`, `user_id`,`supplier_id`) "
                        + "VALUES(?,?,?,?,?)");

                ps.setString(1, encodedReturnNoteId);
                ps.setString(2, encodedReturnDate);
                ps.setString(3, encodedDescription);
                ps.setString(4, encodedUserId);
                ps.setString(5, encodedSupplierId);

                int val = ps.executeUpdate();
                if (val == 1) {
                    return true;
                } else {
                    return false;
                }

            } catch (SQLException e) {

                if (e instanceof SQLException) {
                    log.error(
                            "Exception tag --> " + "Invalid sql statement " + e.
                            getMessage());
                    e.printStackTrace();
                }
                return false;

            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return false;
            }
        }
    }

    public Boolean insertReturnNoteItems(
            String returnNoteId,
            String itemid,
            String batchNo,
            String itemDescription,
            Double qty) {
        String encodedReturnNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                returnNoteId);
        String encodedItemId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, itemid);
        String encodedBatchNo = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                batchNo);
        String encodedItemDescription = ESAPI.encoder().encodeForSQL(
                ORACLE_CODEC, itemDescription);

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {
            try {

                PreparedStatement ps = star.con.prepareStatement(
                        "INSERT INTO external_return_note_item(`external_return_note_id`, "
                        + "`item_id`,`batch_no`, `description`, `qty`) "
                        + "VALUES(?,?,?,?,?)");

                ps.setString(1, encodedReturnNoteId);
                ps.setString(2, encodedItemId);
                ps.setString(3, encodedBatchNo);
                ps.setString(4, encodedItemDescription);
                ps.setDouble(5, qty);

                int val = ps.executeUpdate();
                if (val == 1) {
                    return true;
                } else {
                    return false;
                }

            } catch (SQLException e) {

                if (e instanceof SQLException) {
                    log.error(
                            "Exception tag --> " + "Invalid sql statement " + e.
                            getMessage());
                }
                return false;

            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return false;
            }
        }
    }

    public ArrayList<String> loadingReturnNoteInfo(String requestNoteId) {
        String encodedRequestNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                requestNoteId);

        String date = null;
        String description = null;
        String username = null;
        String supplier = null;

        ArrayList<String> list = new ArrayList<>();

        if (star.con == null) {
            log.error("Databse connection failiure.");

        } else {

            try {
                String query
                        = "select r.return_note_date,r.description ,u.user_name,r.supplier_id "
                        + "from external_return_note r "
                        + "left join user u "
                        + "on u.eid=r.user_id "
                        + "where r.external_return_note_id=? ";
                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedRequestNoteId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    description = r.getString("r.description");
                    date = r.getString("r.return_note_date");
                    username = r.getString("u.user_name");
                    supplier = r.getString("r.supplier_id");

                    list.add(description);
                    list.add(date);
                    list.add(username);
                    list.add(supplier);

                }
            } catch (NullPointerException | SQLException e) {

                if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry passed");

                } else if (e instanceof SQLException) {

                    log.error(
                            "Exception tag --> " + "Invalid sql statement " + e.
                            getMessage());

                }
                return null;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return null;
            }
        }
        return list;
    }

    public ArrayList<ArrayList<String>> searchDetails(String requestNoteId) {

        String encodedRequestNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                requestNoteId);

        String itemId = null;
        String item = null;
        String batchNo = null;
        String description = null;
        String pax = null;

        ArrayList<ArrayList<String>> mainList
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query
                        = "SELECT r.item_id, r.batch_no, r.description, r.qty, i.item_name "
                        + "FROM external_return_note_item r "
                        + "left join item i "
                        + "on i.item_id=r.item_id "
                        + "WHERE r.external_return_note_id= ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedRequestNoteId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    itemId = r.getString("r.item_id");
                    batchNo = r.getString("r.batch_no");
                    item = r.getString("i.item_name");
                    description = r.getString("r.description");
                    pax = r.getString("r.qty");

                    list.add(itemId);
                    list.add(batchNo);
                    list.add(item);
                    list.add(description);
                    list.add(pax);

                    mainList.add(list);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error(
                            "Exception tag --> "
                            + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement");
                    e.printStackTrace();

                } else if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry for list");

                }
                return null;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return null;
            }
        }
        return mainList;
    }

    public ArrayList<ArrayList<String>> searchReturnNoteDetails(
            String requestId) {

        String encodedRequestId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                requestId);
        String date = null;

        ArrayList<ArrayList<String>> mainList
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * "
                        + "From external_return_note "
                        + "Where external_return_note_id LIKE ? or return_note_date LIKE ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedRequestId + "%");
                pstmt.setString(2, encodedRequestId + "%");
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    requestId = r.getString("external_return_note_id");
                    date = r.getString("return_note_date");

                    list.add(requestId);
                    list.add(date);

                    mainList.add(list);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error(
                            "Exception tag --> "
                            + "Invalid entry location for list");

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
        return mainList;
    }

    public boolean checkingReturnNoteAvailability(String requestNoteId) {
        String encodedRequestNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                requestNoteId);

        boolean available = false;

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {

            try {
                String query = "SELECT * FROM  external_return_note where "
                        + "external_return_note_id= ? ";
                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedRequestNoteId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    available = true;
                }

            } catch (NullPointerException | SQLException e) {

                if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry passed");
                } else if (e instanceof SQLException) {
                    log.error(
                            "Exception tag --> " + "Invalid sql statement " + e.
                            getMessage());
                }
                return false;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return false;
            }
        }
        return available;
    }

    public boolean deleteReturnNote(String requestNoteId) {
        String encodedRequestNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                requestNoteId);
        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return false;
        } else {
            try {
                String sql = "DELETE FROM external_return_note WHERE "
                        + "external_return_note_id=? ";
                PreparedStatement stmt = star.con.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, encodedRequestNoteId);
                stmt.executeUpdate();
            } catch (SQLException e) {

                if (e instanceof SQLException) {
                    log.error(
                            "Exception tag --> " + "Invalid sql statement " + e.
                            getMessage());
                }
                return false;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return false;
            }
            return true;
        }
    }

    public ArrayList<ArrayList<String>> getTableApproveInfo(boolean approve,
            String returnNoteId, String fromDate, String toDate) {

        String encodedReturnNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                returnNoteId);
        String encodedFromDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                fromDate);
        String encodedToDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                toDate);

        String Id = null;
        String remark = null;
        String date = null;
        String status = null;
        int isApprove = 0;

        if (approve == true) {
            isApprove = 1;
        }

        ArrayList<ArrayList<String>> mainList
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query
                        = "select *,if(is_approved = 0 ,'Pending','Approved') as "
                        + "state from external_return_note "
                        + "where is_approved=? and (return_note_date between ? and ?) and "
                        + "(external_return_note_id LIKE ? OR description LIKE ? ) ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setInt(1, isApprove);
                pstmt.setString(2, encodedFromDate);
                pstmt.setString(3, encodedToDate);
                pstmt.setString(4, encodedReturnNoteId + "%");
                pstmt.setString(5, encodedReturnNoteId + "%");

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    Id = r.getString("id");
                    returnNoteId = r.getString("external_return_note_id");
                    remark = r.getString("description");
                    date = r.getString("return_note_date");
                    status = r.getString("state");

                    list.add(Id);
                    list.add(returnNoteId);
                    list.add(remark);
                    list.add(date);
                    list.add(status);

                    mainList.add(list);

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
        return mainList;
    }

    public boolean ApproveRequestnote(String id, String returnNoteId,
            String userId, String Date) {
        String encodedReturnNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                returnNoteId);
        String encodedUserId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, userId);
        String encodedDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, Date);
        String encodedID = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, id);

        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return false;
        } else {
            try {
                String sql = "UPDATE external_return_note set is_approved=1 , "
                        + "approve_user_id= ? , approve_date= ? "
                        + "where external_return_note_id=? and id=? ";
                PreparedStatement stmt = Starter.con.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, encodedUserId);
                stmt.setString(2, encodedDate);
                stmt.setString(3, encodedReturnNoteId);
                stmt.setString(4, encodedID);

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
