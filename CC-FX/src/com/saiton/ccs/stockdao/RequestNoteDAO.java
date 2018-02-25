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
public class RequestNoteDAO {

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
                        "SELECT MAX(id) as ID FROM req_note");

                while (rs.next()) {
                    id = rs.getInt("id");
                }
                ResultSet rss = ste.executeQuery(
                        "SELECT req_note_id FROM req_note WHERE id= " + id + "");

                while (rss.next()) {
                    cid = rss.getString("req_note_id");
                }

                if (id != 0) {
                    String original = cid.split("V")[1];
                    int i = Integer.parseInt(original) + 1;

                    if (i < 10) {
                        finalId = "RQV000" + i;
                    } else if (i >= 10 && i < 100) {
                        finalId = "RQV00" + i;
                    } else if (i >= 100 && i < 1000) {
                        finalId = "RQV0" + i;
                    } else if (i >= 1000 ) {
                        finalId = "RQV" + i;
                    }
                    return finalId;

                } else {
                    return "RQV0001";
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

    public ArrayList<String> loadSelectedItemCategory(String itemId) {
        String encodedItemId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, itemId);
        String category = null;
        ArrayList list = new ArrayList();

        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * FROM item where item_id = ? ";
                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedItemId);
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    category = r.getString("item_category");
                    list.add(category);

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
        return list;
    }

    public ArrayList<String> loadCategory() {

        String category = null;
        ArrayList list = new ArrayList();

        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * FROM item_category ";
                PreparedStatement pstmt = star.con.prepareStatement(query);
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    category = r.getString("category");
                    list.add(category);

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
        return list;
    }

    public ArrayList<ArrayList<String>> searchItemDetails(String category,
            String item) {

        String encodedCategory = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                category);
        String encodedItem = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, item);

        String itemId = null;
        String itemName = null;
        String qty = null;
        String unit = null;
        String batchNo = null;

        ArrayList<ArrayList<String>> mainList
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * "
                        + "From item join item_sub on "
                        + "item.item_id = item_sub.item_id "
                        + "Where item.item_id LIKE ? or item.item_name LIKE ? "
                        + "having item.item_category = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedItem + "%");
                pstmt.setString(2, encodedItem + "%");
                pstmt.setString(3, encodedCategory);
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    itemId = r.getString("item_id");
                    itemName = r.getString("item_name");
                    qty = r.getString("qty");
                    unit = r.getString("unit");
                    batchNo = r.getString("batch_no");

                    list.add(itemId);
                    list.add(itemName);
                    list.add(qty);
                    list.add(unit);
                    list.add(batchNo);

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

    public Boolean insertRequestNote(
            String requestNoteId,
            String requestDate,
            String description,
            String type,
            String userId) {

        String encodedRequestNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                requestNoteId);
        String encodedRequestDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                requestDate);
        String encodedDescription = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                description);
        String encodedType = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, type);
        String encodedUserId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, userId);

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {
            try {

                PreparedStatement ps = star.con.prepareStatement(
                        "INSERT INTO req_note(`req_note_id`, `date`, `description`, `req_note_type`, `user_id`) VALUES(?,?,?,?,?)");

                ps.setString(1, encodedRequestNoteId);
                ps.setString(2, encodedRequestDate);
                ps.setString(3, encodedDescription);
                ps.setString(4, encodedType);
                ps.setString(5, encodedUserId);

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

    public Boolean insertRequestNoteItems(
            String requestNoteId,
            String itemid,
            String itemDescription,
            Double qty) {
        String encodedRequestNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                requestNoteId);
        String encodedItemId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, itemid);
        String encodedItemDescription = ESAPI.encoder().encodeForSQL(
                ORACLE_CODEC, itemDescription);

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {
            try {

                PreparedStatement ps = star.con.prepareStatement(
                        "INSERT INTO req_note_item(`req_note_id`, `item_id`, `description`, `qty`) VALUES(?,?,?,?)");

                ps.setString(1, encodedRequestNoteId);
                ps.setString(2, encodedItemId);
                ps.setString(3, encodedItemDescription);
                ps.setDouble(4, qty);

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

    public ArrayList<ArrayList<String>> searchRequestNoteDetails(
            String requestId) {

        String encodedRequestId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                requestId);

        String RequestId = null;
        String date = null;
        String type = null;

        ArrayList<ArrayList<String>> mainList
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * "
                        + "From req_note "
                        + "Where req_note_id LIKE ? or date LIKE ? "
                        + "having is_approved = 0";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedRequestId + "%");
                pstmt.setString(2, encodedRequestId + "%");
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    RequestId = r.getString("req_note_id");
                    type = r.getString("req_note_type");
                    date = r.getString("date");

                    list.add(RequestId);
                    list.add(date);
                    list.add(type);

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

    public ArrayList<String> loadRequsetNoteInfo(String requestNoteId) {
        String encodedRequestNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                requestNoteId);

        String date = null;
        String description = null;
        String reqNoteType = null;
        String username = null;

        ArrayList<String> list = new ArrayList<>();

        if (star.con == null) {
            log.error("Databse connection failiure.");

        } else {

            try {
                String query
                        = "select r.date,r.description,r.req_note_type,u.user_name "
                        + "from req_note r "
                        + "left join user u "
                        + "on u.eid=r.user_id "
                        + "where r.req_note_id=? ";
                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedRequestNoteId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    reqNoteType = r.getString("r.req_note_type");
                    description = r.getString("r.description");
                    date = r.getString("r.date");
                    username = r.getString("u.user_name");

                    list.add(reqNoteType);
                    list.add(description);
                    list.add(date);
                    list.add(username);

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
        String description = null;
        String unit = null;
        String pax = null;

        ArrayList<ArrayList<String>> mainList
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query
                        = "SELECT r.item_id, r.description, r.qty, i.item_name,i.unit "
                        + "FROM req_note_item r "
                        + "left join item i "
                        + "on i.item_id=r.item_id "
                        + "WHERE r.req_note_id= ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedRequestNoteId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    itemId = r.getString("r.item_id");
                    item = r.getString("i.item_name");
                    description = r.getString("r.description");
                    unit = r.getString("i.unit");
                    pax = r.getString("r.qty");

                    list.add(itemId);
                    list.add(item);
                    list.add(description);
                    list.add(pax);
                    list.add(unit);

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

    public boolean checkRequestNoteAvailability(String requestNoteId) {
        String encodedRequestNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                requestNoteId);

        boolean available = false;

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {

            try {
                String query = "SELECT * FROM  req_note where req_note_id= ? ";
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

    public boolean deleteRequestNote(String requestNoteId) {
        String encodedRequestNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                requestNoteId);
        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return false;
        } else {
            try {
                String sql = "DELETE FROM req_note WHERE req_note_id=? ";
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
            String requestNoteId, String fromDate, String toDate) {

        String encodedrequestNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                requestNoteId);
        String encodedFromDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                fromDate);
        String encodedToDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                toDate);

        String Id = null;
        String remark = null;
        String date = null;
        String status = null;
        String type = null;
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
                        + "state from req_note "
                        + "where is_approved=? and (date between ? and ?) and"
                        + "(req_note_id LIKE ? OR description LIKE ? ) ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setInt(1, isApprove);
                pstmt.setString(2, encodedFromDate);
                pstmt.setString(3, encodedToDate);
                pstmt.setString(4, encodedrequestNoteId + "%");
                pstmt.setString(5, encodedrequestNoteId + "%");

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    Id = r.getString("id");
                    requestNoteId = r.getString("req_note_id");
                    remark = r.getString("description");
                    date = r.getString("date");
                    status = r.getString("state");
                    type = r.getString("req_note_type");

                    list.add(Id);
                    list.add(requestNoteId);
                    list.add(remark);
                    list.add(date);
                    list.add(status);
                    list.add(type);

                    mainList.add(list);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error(
                            "Exception tag --> "
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

    public boolean ApproveRequestnote(String id, String requestNoteId,
            String userId, String date) {
        String encodedRequestNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                requestNoteId);
        String encodedUserId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, userId);
        String encodedDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, date);
        String encodedID = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, id);

        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return false;
        } else {
            try {
                String sql
                        = "UPDATE req_note set is_approved=1 , approve_user_id= ? , approve_date= ? where req_note_id=? and id=? ";
                PreparedStatement stmt = star.con.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, encodedUserId);
                stmt.setString(2, encodedDate);
                stmt.setString(3, encodedRequestNoteId);
                stmt.setString(4, encodedID);

                int val = stmt.executeUpdate();
                if (val == 1) {
                    return true;
                } else {
                    return false;
                }

            } catch (SQLException e) {
                log.error(
                        "Exception tag --> " + "Invalid sql statement " + e.
                        getMessage());
                return false;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return false;
            }
        }

    }

}
