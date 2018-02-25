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

public class ExternalGrnDAO {

    public static Starter star;//db connection
    Codec ORACLE_CODEC = new OracleCodec();
    private Logger log = Logger.getLogger(this.getClass());

    public String generateId() {

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
                        "SELECT MAX(id) as ID FROM external_grn");

                while (rs.next()) {
                    id = rs.getInt("id");
                }
                ResultSet rss = ste.executeQuery(
                        "SELECT external_grn_id FROM external_grn WHERE id= "
                        + id + "");

                while (rss.next()) {
                    cid = rss.getString("external_grn_id");
                }

                if (id != 0) {
                    String original = cid.split("N")[1];
                    int i = Integer.parseInt(original) + 1;

                    if (i < 10) {
                        final_id = "EGN000" + i;
                    } else if (i >= 10 && i < 100) {
                        final_id = "EGN00" + i;
                    } else if (i >= 100 && i < 1000) {
                        final_id = "EGN0" + i;
                    } else if (i >= 1000 && i < 10000) {
                        final_id = "EGN" + i;
                    }
                    return final_id;

                } else {
                    return "EGN0001";
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

    public ArrayList<ArrayList<String>> searchGrnDetails(String keyword) {

        String encodedKeyword = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                keyword);

        String grnId = null;
        String date = null;
        String description = null;

        ArrayList<ArrayList<String>> Mainlist
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * "
                        + "From external_grn "
                        + "Where external_grn_id LIKE ? or description LIKE ? "
                        + "or date LIKE ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedKeyword + "%");
                pstmt.setString(2, encodedKeyword + "%");
                pstmt.setString(3, encodedKeyword + "%");
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    grnId = r.getString("external_grn_id");
                    description = r.getString("description");
                    date = r.getString("date");

                    list.add(grnId);
                    list.add(date);
                    list.add(description);

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

    public Boolean insertExternalGrn(
            String grnId,
            String purchaseOrderId,
            String grnDate,
            String Description,
            String userId) {

        String encodedGrnId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                grnId);
        String encodedPurchaseOrderId = ESAPI.encoder().encodeForSQL(
                ORACLE_CODEC,
                purchaseOrderId);
        String encodedGrnDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                grnDate);
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
                        "INSERT INTO external_grn(`external_grn_id`, "
                        + "`purchase_order_id`, `date`, `description`, "
                        + "`user_id`) VALUES(?,?,?,?,?)");

                ps.setString(1, encodedGrnId);
                ps.setString(2, encodedPurchaseOrderId);
                ps.setString(3, encodedGrnDate);
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

    public Boolean insertGrnItems(
            String grnId,
            String itemid,
            String itemDescription,
            Double qty,
            String batchNo) {
        String encodedGrnId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                grnId);
        String encodedItemId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, itemid);
        String encodedDescription = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, itemDescription);
        String encodedBatchNo = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, batchNo);

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {
            try {

                PreparedStatement ps = star.con.prepareStatement(
                        "INSERT INTO external_grn_item"
                        + "(`external_grn_id`, `item_id`, `description`,  `qty`,`batch_no`) "
                        + "VALUES(?,?,?,?,?)");

                ps.setString(1, encodedGrnId);
                ps.setString(2, encodedItemId);
                ps.setString(3, encodedDescription);
                ps.setDouble(4, qty);
                ps.setString(5, encodedBatchNo);

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

    public ArrayList<String> loadingGrnInfo(String grnId) {
        String encodedGrnId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                grnId);

        String date = null;
        String description = null;
        String issueNoteId = null;
        String userId = null;

        ArrayList<String> list = new ArrayList<>();

        if (star.con == null) {
            log.error("Databse connection failiure.");

        } else {

            try {
                String query = "select * "
                        + "from external_grn "
                        + "where external_grn_id=? ";
                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedGrnId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    description = r.getString("description");
                    date = r.getString("date");
                    issueNoteId = r.getString("purchase_order_id");
                    userId = r.getString("user_Id");

                    list.add(description);
                    list.add(date);
                    list.add(issueNoteId);
                    list.add(userId);

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

    public ArrayList<ArrayList<String>> searchGrnItemDetails(String grnId) {

        String encodedgrnId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, grnId);

        String itemId = null;
        String item = null;
        String description = null;
        String unit = null;
        String qty = null;
        String batchNo = null;

        ArrayList<ArrayList<String>> Mainlist
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * "
                        + "FROM external_grn_item r "
                        + " join item i "
                        + "on i.item_id=r.item_id "
                        + "WHERE r.external_grn_id= ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedgrnId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    itemId = r.getString("r.item_id");
                    item = r.getString("i.item_name");
                    description = r.getString("r.description");
                    qty = r.getString("r.qty");
                    batchNo = r.getString("r.batch_no");

                    list.add(itemId);
                    list.add(item);
                    list.add(description);
                    list.add(qty);
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

    public boolean deleteGrnNote(String grnId) {
        String encodedIssueNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                grnId);
        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return false;
        } else {
            try {
                String sql = "DELETE FROM external_grn WHERE external_grn_id=? ";
                PreparedStatement stmt = star.con.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, encodedIssueNoteId);
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

    public ArrayList<ArrayList<String>> getTableApproveInfo(boolean approve,
            String searchKeyword, String fromDate, String toDate) {

        String encodedSearchKeyword = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                searchKeyword);

        String encodedFromDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                fromDate);
        String encodedToDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                toDate);
        String id = null;
        String issueNoteId = null;
        String grnId = null;
        String remark = null;
        String date = null;
        String status = null;
        int isApprove = 0;

        if (approve == true) {
            isApprove = 1;
        }

        ArrayList<ArrayList<String>> mainlist
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query
                        = "select *,if(is_approved = 0 ,'Pending','Approved') as"
                        + " state from external_grn "
                        + "where is_approved=? and (date between ? and ?) and"
                        + " (purchase_order_id LIKE ? OR external_grn_id LIKE ? OR "
                        + "date LIKE ? OR description LIKE ? ) ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setInt(1, isApprove);
                pstmt.setString(2, encodedFromDate);
                pstmt.setString(3, encodedToDate);
                pstmt.setString(4, encodedSearchKeyword + "%");
                pstmt.setString(5, encodedSearchKeyword + "%");
                pstmt.setString(6, encodedSearchKeyword + "%");
                pstmt.setString(7, encodedSearchKeyword + "%");

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    id = r.getString("id");
                    issueNoteId = r.getString("purchase_order_id");
                    grnId = r.getString("external_grn_id");
                    remark = r.getString("description");
                    date = r.getString("date");
                    status = r.getString("state");

                    list.add(id);
                    list.add(issueNoteId);
                    list.add(grnId);
                    list.add(remark);
                    list.add(date);
                    list.add(status);

                    mainlist.add(list);

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error("Exception tag --> "
                            + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.
                            error("Exception tag --> " + "Invalid sql statement"
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
        return mainlist;
    }

    public boolean approveGrn(String id, String grnId,
            String userId, String date) {

        String encodedGrnId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                grnId);
        String encodedUserId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, userId);
        String encodedDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, date);
        String encodedId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, id);

        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return false;
        } else {
            try {
                String sql
                        = "UPDATE external_grn set is_approved = ? , "
                        + "approve_user_id= ? , approve_date= ? "
                        + "where external_grn_id = ? and id = ? ";
                PreparedStatement stmt = star.con.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, 1);
                stmt.setString(2, encodedUserId);
                stmt.setString(3, encodedDate);
                stmt.setString(4, encodedGrnId);
                stmt.setString(5, encodedId);

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

    public ArrayList<ArrayList<String>> loadingGrn(String grnId) {

        String encodedCusId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, grnId);

        String itemId = null;
        String batchNo = null;
        String qty = null;

        ArrayList<ArrayList<String>> Mainlist
                = new ArrayList<ArrayList<String>>();
        if (star.con == null) {
            log.error("Databse connection failiure.");
            return null;
        } else {

            try {
                String query = "Select * from external_grn_item g "
                        + "join (item i,item_sub s) "
                        + "on g.item_id = i.item_id "
                        + "AND g.item_id = s.item_id "
                        + "AND g.batch_no = s.batch_no"
                        + " where g.external_grn_id =? ";
                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedCusId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    itemId = r.getString("g.item_id");
                    batchNo = r.getString("g.batch_no");
                    qty = r.getString("g.qty");

                    list.add(itemId);
                    list.add(batchNo);
                    list.add(qty);

                    Mainlist.add(list);

                }

            } catch (NullPointerException | NumberFormatException | SQLException e) {

                if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry passed");
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
        return Mainlist;
    }

    public boolean updateGRNStatus(
            String grnId,
            int status
    ) {

        String encodedIssueNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                grnId);

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {
            try {
                String query = "UPDATE external_grn set "
                        + "status=?"
                        + " WHERE external_grn_id =?  ";
                PreparedStatement ps = star.con.prepareStatement(query);

                ps.setInt(1, status);
                ps.setString(2, grnId);

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

    public boolean updateMainStockItems(
            String itemId,
            String batchNo,
            Double qty
    ) {

        String encodedItemId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                itemId);
        String encodedBatchNo = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                batchNo);

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {
            try {
                String query = "UPDATE item_sub set "
                        + "qty=qty+?"
                        + " WHERE item_id=? AND batch_no=? ";
                PreparedStatement ps = star.con.prepareStatement(query);

                ps.setDouble(1, qty);
                ps.setString(2, encodedItemId);
                ps.setString(3, encodedBatchNo);

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

    public boolean updateItemTotal(
            String itemId, double qty
    ) {

        String encodedItemId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                itemId);

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {
            try {

                String query = "UPDATE item set "
                        + "qty=qty+?"
                        + " WHERE item_id=? ";
                PreparedStatement ps = star.con.prepareStatement(query);

                ps.setDouble(1, qty);
                ps.setString(2, itemId);

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

    public ArrayList<ArrayList<String>> searchPurchaseOrderDetails(
            String purchaseOrderId) {

        String encodedIssueNoteId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                purchaseOrderId);

        String purchaseId = null;
        String supplierId = null;
        String date = null;
        String name = null;

        ArrayList<ArrayList<String>> Mainlist
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * "
                        + "From purchase_order p join"
                        + " (supplier s) on p.supplier_id = s.sid "
                        //                        + "and p.purchase_order_id= e.purchase_order_id "
                        + "Where (p.purchase_order_id LIKE ? or "
                        + "s.sid LIKE ? ) "
                        + "AND p.is_approved= 1";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedIssueNoteId + "%");
                pstmt.setString(2, encodedIssueNoteId + "%");
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    purchaseId = r.getString("p.purchase_order_id");
                    supplierId = r.getString("p.supplier_id");
                    date = r.getString("p.date");
                    name = r.getString("s.name");

                    list.add(purchaseId);
                    list.add(supplierId);
                    list.add(date);
                    list.add(name);

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

    public ArrayList<ArrayList<String>> searchPurchaseOrderItems(
            String purchaseOrderId) {

        String encodedPurchaseOrderId = ESAPI.encoder().encodeForSQL(
                ORACLE_CODEC,
                purchaseOrderId);

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
                        + "FROM purchase_order_item r "
                        + " join item i "
                        + "on i.item_id=r.item_id "
                        + "WHERE r.purchase_order_id= ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, purchaseOrderId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    itemId = r.getString("r.item_id");
                    item = r.getString("i.item_name");
                    description = r.getString("r.remarks");
                    //unit = r.getString("i.unit");
                    pax = r.getString("r.qty");
                    batchNo = r.getString("r.batch_no");

                    list.add(itemId);
                    list.add(item);
                    list.add(description);
                    list.add(pax);
                    //list.add(unit);
                    list.add(batchNo);

                    Mainlist.add(list);

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
        return Mainlist;
    }

    public String loadingPurchaseOrderDate(String purchaseOrderId) {
        String encodedPurchaseOrderId = ESAPI.encoder().encodeForSQL(
                ORACLE_CODEC,
                purchaseOrderId);

        String date = null;

        if (star.con == null) {
            log.error("Databse connection failiure.");

        } else {

            try {
                String query = "select * "
                        + "from purchase_order p "
                        + "where p.purchase_order_id=? ";
                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedPurchaseOrderId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    date = r.getString("p.date");

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
        return date;
    }

    public ArrayList<String> loadGrnInfo(String grnId) {
        String encodedGrnId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                grnId);

        String date = null;
        String description = null;
        String issueNoteId = null;
        String userName = null;
        String purchaseOrderdate = null;

        ArrayList<String> list = new ArrayList<>();

        if (star.con == null) {
            log.error("Databse connection failiure.");

        } else {

            try {
                String query = "select e.*,u.name,p.date "
                        + "from external_grn e "
                        + "left join user u "
                        + "on u.eid=e.user_id "
                        + "left join purchase_order p "
                        + "on p.purchase_order_id=e.purchase_order_id "
                        + "where e.external_grn_id=? ";
                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedGrnId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    description = r.getString("e.description");
                    date = r.getString("e.date");
                    issueNoteId = r.getString("e.purchase_order_id");
                    purchaseOrderdate = r.getString("p.date");
                    userName = r.getString("u.name");

                    list.add(description);
                    list.add(date);
                    list.add(issueNoteId);
                    list.add(purchaseOrderdate);
                    list.add(userName);

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

}
