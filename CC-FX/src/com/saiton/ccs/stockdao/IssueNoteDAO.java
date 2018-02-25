/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.stockdao;


import com.saiton.ccs.validations.FormatAndValidate;
import com.saiton.ccs.database.Starter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import org.apache.log4j.Logger;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

/**
 *
 * @author Miren
 */
public class IssueNoteDAO {

    public static Starter star;//db connection
    Codec ORACLE_CODEC = new OracleCodec();
    private Logger log = Logger.getLogger(this.getClass());

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
                ResultSet rs = st.executeQuery(
                        "SELECT MAX(id) as ID FROM issue_note");

                while (rs.next()) {
                    id = rs.getInt("id");
                }
                ResultSet rss = ste.executeQuery(
                        "SELECT issue_note_id FROM issue_note WHERE id= " + id
                        + "");

                while (rss.next()) {
                    cid = rss.getString("issue_note_id");
                }

                if (id != 0) {
                    String original = cid.split("U")[1];
                    int i = Integer.parseInt(original) + 1;

                    if (i < 10) {
                        final_id = "ISU000" + i;
                    } else if (i >= 10 && i < 100) {
                        final_id = "ISU00" + i;
                    } else if (i >= 100 && i < 1000) {
                        final_id = "ISU0" + i;
                    } else if (i >= 1000 && i < 10000) {
                        final_id = "ISU" + i;
                    }
                    return final_id;

                } else {
                    return "ISU0001";
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

    public Boolean insertIssueNote(
            String issueNoteId,
            String requestNoteId,
            String issueDate,
            String Description,
            String userId) {

        String encodedIssueNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                issueNoteId);
        String encodedRequestNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                requestNoteId);
        String encodedIssueDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                issueDate);
        String encodedDescription = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                Description);
        String encodedUserId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, userId);

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {
            try {

                PreparedStatement ps = star.con.prepareStatement(
                        "INSERT INTO issue_note(`issue_note_id`, `request_note_id`, `issue_note_date`, `description`, `user_id`) VALUES(?,?,?,?,?)");

                ps.setString(1, encodedIssueNoteId);
                ps.setString(2, encodedRequestNoteId);
                ps.setString(3, encodedIssueDate);
                ps.setString(4, encodedDescription);
                ps.setString(5, encodedUserId);

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
    
    public Boolean updateRequestNoteItems(
            String requestNoteId,
            String itemid,
            Double qty) {
        String encodedRequestNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                requestNoteId);
        String encodedItemId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, itemid);

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {
            try {
                PreparedStatement ps1 = star.con.prepareStatement(
                        "UPDATE req_note_item SET issue_qty = issue_qty - ? WHERE req_note_id=? and item_id=?");

                ps1.setDouble(1, qty);
                ps1.setString(2, encodedRequestNoteId);
                ps1.setString(3, encodedItemId);

                int val1 = ps1.executeUpdate();

                if (val1 == 1) {
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
    

    public Boolean insertIssueNoteItems(
            String issueNoteId,
            String requestNoteId,
            String itemid,
            String itemDescription,
            Double qty,
            String BatchNo) {
        String encodedIssueNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                issueNoteId);
        String encodedRequestNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                requestNoteId);
        String encodedItemId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, itemid);
        String encodedItemDescription = ESAPI.encoder().encodeForSQL(
                ORACLE_CODEC, itemDescription);

        String encodedItemBatchNo = ESAPI.encoder().encodeForSQL(
                ORACLE_CODEC, BatchNo);
        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {
            try {

                PreparedStatement ps = star.con.prepareStatement(
                        "INSERT INTO issue_note_item(`issue_note_id`, `item_id`, `description`, `qty`,`batch_no`) VALUES(?,?,?,?,?)");

                ps.setString(1, encodedIssueNoteId);
                ps.setString(2, encodedItemId);
                ps.setString(3, encodedItemDescription);
                ps.setDouble(4, qty);
                ps.setString(5, encodedItemBatchNo);
                int val = ps.executeUpdate();

                PreparedStatement ps1 = star.con.prepareStatement(
                        "UPDATE req_note_item SET issue_qty = issue_qty + ? WHERE req_note_id=? and item_id=?");

                ps1.setDouble(1, qty);
                ps1.setString(2, encodedRequestNoteId);
                ps1.setString(3, encodedItemId);

                int val1 = ps1.executeUpdate();

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

    public ArrayList<ArrayList<String>> searchIssueDetails(String IssueNoteId) {

        String encodedIssueNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                IssueNoteId);

        String itemId = null;
        String item = null;
        String description = null;
        String unit = null;
        String pax = null;
        String batchNo = null;

        ArrayList<ArrayList<String>> Mainlist
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query
                        = "SELECT * "
                        + "FROM issue_note_item r "
                        + " join item i "
                        + "on i.item_id=r.item_id "
                        + "WHERE r.issue_note_id= ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, IssueNoteId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    itemId = r.getString("r.item_id");
                    item = r.getString("i.item_name");
                    description = r.getString("r.description");
                    unit = r.getString("i.unit");
                    pax = r.getString("r.qty");
                    batchNo = r.getString("r.batch_no");

                    list.add(itemId);
                    list.add(item);
                    list.add(description);
                    list.add(pax);
                    list.add(unit);
                    list.add(batchNo);

                    Mainlist.add(list);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error("Exception tag --> "
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
        return Mainlist;
    }

    public ArrayList<ArrayList<String>> searchIssueNoteDetails(
            String issueNoteId) {

        String encodedIssueNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                issueNoteId);

        String issueId = null;
        String RequestId = null;
        String date = null;
        String type = null;

        ArrayList<ArrayList<String>> Mainlist
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * "
                        + "From issue_note "
                        + "Where issue_note_id LIKE ? or issue_note_date LIKE ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedIssueNoteId + "%");
                pstmt.setString(2, encodedIssueNoteId + "%");
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    issueId = r.getString("issue_note_id");
                    RequestId = r.getString("request_note_id");
                    date = r.getString("issue_note_date");

                    list.add(issueId);
                    list.add(RequestId);
                    list.add(date);

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

    public ArrayList<String> loadingIssueNoteInfo(String issueNoteId) {
        String encodedIssueNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                issueNoteId);

        String date = null;
        String description = null;
        String username = null;

        ArrayList<String> list = new ArrayList<>();

        if (star.con == null) {
            log.error("Databse connection failiure.");

        } else {

            try {
                String query
                        = "select r.issue_note_date,r.description,u.user_name "
                        + "from issue_note r "
                        + "left join user u "
                        + "on u.eid=r.user_id "
                        + "where r.issue_note_id=? ";
                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedIssueNoteId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    description = r.getString("r.description");
                    date = r.getString("r.issue_note_date");
                    username = r.getString("u.user_name");

                    list.add(description);
                    list.add(date);
                    list.add(username);

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

    public boolean checkingIssueNoteAvailability(String IssueNoteId) {
        String encodedIssueNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                IssueNoteId);

        boolean available = false;

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {

            try {
                String query
                        = "SELECT * FROM  issue_note where issue_note_id= ? ";
                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedIssueNoteId);

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

    public boolean deleteIssueNote(String IssueNoteId, String RequestNoteId) {
        String encodedIssueNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                IssueNoteId);
        String encodedRequestNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                RequestNoteId);
        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return false;
        } else {
            try {
                PreparedStatement ps1 = star.con.prepareStatement(
                        "UPDATE req_note SET status = 0 WHERE req_note_id=?");
                ps1.setString(1, encodedRequestNoteId);

                int val = ps1.executeUpdate();

                String sql = "DELETE FROM issue_note WHERE issue_note_id=? ";
                PreparedStatement stmt = star.con.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, encodedIssueNoteId);
                val=stmt.executeUpdate();
                if(val == 1){
                    return true;
                }else{
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

    public ArrayList<ArrayList<String>> searchRequestNoteDetails(
            String requestId) {

        String encodedRequestId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                requestId);

        String RequestId = null;
        String date = null;
        String type = null;

        ArrayList<ArrayList<String>> Mainlist
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * "
                        + "From req_note "
                        + "Where is_approved=1 and status=0 and (req_note_id LIKE ? or date LIKE ? ) ";

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

                    Mainlist.add(list);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error("Exception tag --> "
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
        return Mainlist;
    }

    public ArrayList<ArrayList<String>> searchDetails(String RequestNoteId) {

        String encodedRequestNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                RequestNoteId);

        String itemId = null;
        String item = null;
        String description = null;
        String unit = null;
        String pax = null;

        ArrayList<ArrayList<String>> Mainlist
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query
                        = "SELECT r.item_id, r.description, r.qty-r.issue_qty as avalable_qty, i.item_name,i.unit "
                        + "FROM req_note_item r "
                        + "left join item i "
                        + "on i.item_id=r.item_id "
                        + "WHERE r.req_note_id= ? "
                        + "having avalable_qty > 0 ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedRequestNoteId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    itemId = r.getString("r.item_id");
                    item = r.getString("i.item_name");
                    description = r.getString("r.description");
                    unit = r.getString("i.unit");
                    pax = r.getString("avalable_qty");

                    list.add(itemId);
                    list.add(item);
                    list.add(description);
                    list.add(pax);
                    list.add(unit);

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

    public void updateRequestNoteStatus(String RequestNoteId) {

        String encodedRequestNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                RequestNoteId);

        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");

        } else {
            try {
                boolean avalable = false;
                String query
                        = "SELECT r.item_id, r.description, r.qty-r.issue_qty as avalable_qty, i.item_name,i.unit "
                        + "FROM req_note_item r "
                        + "left join item i "
                        + "on i.item_id=r.item_id "
                        + "WHERE r.req_note_id= ? "
                        + "having avalable_qty > 0 ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedRequestNoteId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    avalable = true;

                }
                if (avalable == false) {

                    PreparedStatement ps1 = star.con.prepareStatement(
                            "UPDATE req_note SET status = 1 WHERE req_note_id=?");
                    ps1.setString(1, encodedRequestNoteId);

                    int val1 = ps1.executeUpdate();

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

            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");
            }
        }
    }

    public ArrayList<ArrayList<String>> getTableApproveInfo(boolean approve,
            String IssueNoteId, String fromDate, String toDate) {

        String encodedIssueNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                IssueNoteId);
        String encodedFromDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                fromDate);
        String encodedToDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                toDate);

        String Id = null;
        String issueNoteId = null;
        String requestNoteId = null;
        String remark = null;
        String date = null;
        String status = null;
        int isApprove = 0;

        if (approve == true) {
            isApprove = 1;
        }

        ArrayList<ArrayList<String>> Mainlist
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query
                        = "select *,if(is_approved = 0 ,'Pending','Approved') as "
                        + "state from issue_note "
                        + "where is_approved=? and (issue_note_date between ? and ?) and "
                        + "(issue_note_id LIKE ? OR request_note_id LIKE ? OR "
                        + "description LIKE ? ) ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setInt(1, isApprove);
                pstmt.setString(2, encodedFromDate);
                pstmt.setString(3, encodedToDate);
                pstmt.setString(4, encodedIssueNoteId + "%");
                pstmt.setString(5, encodedIssueNoteId + "%");
                pstmt.setString(6, encodedIssueNoteId + "%");

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    Id = r.getString("id");
                    issueNoteId = r.getString("issue_note_id");
                    requestNoteId = r.getString("request_note_id");
                    remark = r.getString("description");
                    date = r.getString("issue_note_date");
                    status = r.getString("state");

                    list.add(Id);
                    list.add(issueNoteId);
                    list.add(requestNoteId);
                    list.add(remark);
                    list.add(date);
                    list.add(status);

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

    public boolean ApproveRequestnote(String id, String issueNoteId,
            String userId, String Date) {
        String encodedIssueNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                issueNoteId);
        String encodedUserId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, userId);
        String encodedDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, Date);
        String encodedID = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, id);

        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return false;
        } else {
            try {
                String sql
                        = "UPDATE issue_note set is_approved=1 , approve_user_id= ? , approve_date= ? where issue_note_id=? and id=? ";
                PreparedStatement stmt = Starter.con.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, encodedUserId);
                stmt.setString(2, encodedDate);
                stmt.setString(3, encodedIssueNoteId);
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
    
    public ArrayList<String> loadingBatchNo(String itemId) {

        String batchNo = null;
        String encodedItemId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, itemId);
        ArrayList list = new ArrayList();

        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * FROM item_sub WHERE item_id = ? ";
                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedItemId);
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    batchNo = r.getString("batch_no");
                    list.add(batchNo);

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
     
     public boolean isItemOutOfStock(String itemId, String batchNo,Double qty) {
        String encodedItemId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                itemId);

        String encodedBatchNo = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                batchNo);
        boolean isOutOfStock = true;
        double availableQty = 0;
        
        if (star.con == null) {
            log.error("Databse connection failiure.");

        } else {

            try {
                String query = "select * "
                        + "from item_sub "
                        + "where item_id=? AND batch_no = ? ";
                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedItemId);
                pstmt.setString(2, encodedBatchNo);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    availableQty = r.getDouble("qty");
                     if ((availableQty-qty)>=0) {
                        isOutOfStock = false;
                    }
                    

                }
            } catch (NullPointerException | SQLException e) {

                if (e instanceof NullPointerException) {

                    log.error("Exception tag --> " + "Empty entry passed");

                } else if (e instanceof SQLException) {

                    log.error("Exception tag --> " + "Invalid sql statement "
                            + e.getMessage());

                }
                return true;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return true;
            }
        }
        return isOutOfStock;
    }
    

}
