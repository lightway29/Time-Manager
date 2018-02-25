package com.saiton.ccs.stockdao;


import com.saiton.ccs.commondao.CommonSqlUtil;
import com.saiton.ccs.database.Starter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

/**
 *
 * @author Saitonya
 */
public class PurchaseOrderDAO {

    private Starter star;
    Codec ORACLE_CODEC = new OracleCodec();
    private final Logger log = Logger.getLogger(this.getClass());

    public String generateId() {
        return CommonSqlUtil.generateId(star.con, "id", "purchase_order_id",
                "purchase_order", "PHS", log);
    }

    private Boolean updateReorderLevel(String itemId, String batchNo,
            String reorder) {
        String query
                = "UPDATE `item_sub` " + "SET" + " `reorder_level` = ? "
                + "WHERE `item_id` = ? AND `batch_no`=?;";
        if (star.con == null) {
            log.error(" Exception tag --> "
                    + "Databse connection failiure. ");
            return null;
        }

        try {

            PreparedStatement st = star.con.prepareStatement(query);

            st.setString(1, CommonSqlUtil.encode(reorder));
            st.setString(2, CommonSqlUtil.encode(itemId));
            st.setString(3, CommonSqlUtil.encode(batchNo));
            boolean ret = (st.executeUpdate() == 1); //one row
            st.close();
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Boolean setPurchaseItems(String purchaseId,
            List<String[]> purchaseItems) {

        final String queryAdd = "INSERT INTO `purchase_order_item` "
                + "(`purchase_order_id`, `item_id`, `batch_no`, `qty`, `remarks`) "
                + "VALUES (?, ?, ?, ?, ?);";

        if (star.con == null) {
            log.error(" Exception tag --> "
                    + "Databse connection failiure. ");
            return null;
        }

        try {
            CommonSqlUtil.genericDelete(star.con, "purchase_order_item",
                    "purchase_order_id", purchaseId, log);

            PreparedStatement add = star.con.prepareStatement(queryAdd);
            for (String[] tel : purchaseItems) {
                add.setString(1, CommonSqlUtil.encode(purchaseId));
                add.setString(2, CommonSqlUtil.encode(tel[0]));
                add.setString(3, CommonSqlUtil.encode(tel[1]));
                add.setString(4, CommonSqlUtil.encode(tel[2]));
                add.setString(5, CommonSqlUtil.encode(tel[4]));
                add.executeUpdate();
                updateReorderLevel(tel[0], tel[1], tel[3]);
            }
            add.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public Boolean deletePurchaseOrder(String purchaseId) {

        return CommonSqlUtil.genericDelete(star.con, "purchase_order",
                "purchase_order_id",
                purchaseId, log);
    }

    public Boolean insertPurchaseOrder(String supplierId, String userId,
            List<String[]> purchaseItems) {
        String purchaseId = generateId();
        String query
                = "INSERT INTO `purchase_order` " + "(`purchase_order_id`, "
                + "`supplier_id`, " + "`date`, " + "`user_id`) " + "VALUES "
                + "(?,?,curdate(),?);";

        if (star.con == null) {
            log.error(" Exception tag --> "
                    + "Databse connection failiure. ");
            return null;
        }

        try {

            PreparedStatement st = star.con.prepareStatement(query);
            st.setString(1, CommonSqlUtil.encode(purchaseId));
            st.setString(2, CommonSqlUtil.encode(supplierId));
            st.setString(3, CommonSqlUtil.encode(userId));
            boolean ret = st.executeUpdate() == 1 & setPurchaseItems(purchaseId,
                    purchaseItems) == true; //one row
            st.close();
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean updatePurchaseOrder(String purchaseId, String supplierId,
            String userId, List<String[]> purchaseItems) {
        String query
                = "UPDATE `purchase_order` " + "SET " + "`supplier_id` = ?, "
                + "`date` = curdate(), " + "`user_id` = ? "
                + "WHERE `purchase_order_id` = ?;";

        if (star.con == null) {
            log.error(" Exception tag --> "
                    + "Databse connection failiure. ");
            return null;
        }

        try {

            PreparedStatement st = star.con.prepareStatement(query);

            st.setString(1, CommonSqlUtil.encode(supplierId));
            st.setString(2, CommonSqlUtil.encode(userId));
            st.setString(3, CommonSqlUtil.encode(purchaseId));
            boolean ret = st.executeUpdate() == 1 & setPurchaseItems(purchaseId,
                    purchaseItems) == true; //one row
            st.close();
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<String[]> selectAllBatch(String itemId) {
        final String query = "SELECT `batch_no`, " + "    `qty`, "
                + "    `price`, " + "    `reorder_level` "
                + "FROM `item_sub` " + "WHERE `item_id` = ?;";

        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;
        }

        try {

            List<String[]> data = new ArrayList<>();
            PreparedStatement stt = star.con.prepareStatement(query);
            stt.setString(1, CommonSqlUtil.encode(itemId));
            ResultSet rss = stt.executeQuery();
            while (rss.next()) {
                String batch = rss.getString("batch_no");
                String qty = rss.getString("qty");
                String price = rss.getString("price");
                String reorder = rss.getString("reorder_level");
                data.add(new String[]{
                    batch, qty, price, reorder
                });
            }
            rss.close();
            stt.close();
            return data;
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return null;
    }

    public List<String[]> selectAllPurchases() {

        final String query
                = "select * from purchase_order p join supplier s "
                + "on s.sid = p.supplier_id where p.is_approved= 0";

        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;
        }

        try {

            List<String[]> data = new ArrayList<>();
            PreparedStatement stt = star.con.prepareStatement(query);
            ResultSet rss = stt.executeQuery();
            while (rss.next()) {
                String pid = rss.getString("p.purchase_order_id");
                String sid = rss.getString("s.sid");
                String name = rss.getString("s.name");
                String date = rss.getString("p.date");
                String user = rss.getString("p.user_id");
                data.add(new String[]{
                    pid, sid, name, date, user
                });
            }
            rss.close();
            stt.close();
            return data;
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return null;
    }

    public List<String[]> searchPurchases(String keyword) {

        final String query
                = "select * from purchase_order p join supplier s on s.sid = "
                + "p.supplier_id where p.purchase_order_id LIKE ?"
                + " having p.is_approved= 0 ";

        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;
        }

        try {

            List<String[]> data = new ArrayList<>();
            PreparedStatement stt = star.con.prepareStatement(query);
            stt.setString(1, keyword + "%");
            ResultSet rss = stt.executeQuery();
            while (rss.next()) {
                String pid = rss.getString("p.purchase_order_id");
                String sid = rss.getString("s.sid");
                String name = rss.getString("s.name");
                String date = rss.getString("p.date");
                String user = rss.getString("p.user_id");
                data.add(new String[]{
                    pid, sid, name, date, user
                });
            }
            rss.close();
            stt.close();
            return data;
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return null;
    }

    public List<String[]> selectPurchaseItems(String purchaseId) {
        final String query
                = "SELECT o.item_id,o.batch_no,o.qty,i.reorder_level,o.remarks "
                + "FROM purchase_order_item o,item_sub i  "
                + "WHERE o.item_id=i.item_id AND o.batch_no=i.batch_no  "
                + "AND o.purchase_order_id=?;";

        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;
        }

        try {

            List<String[]> data = new ArrayList<>();
            PreparedStatement stt = star.con.prepareStatement(query);
            stt.setString(1, CommonSqlUtil.encode(purchaseId));
            ResultSet rss = stt.executeQuery();
            while (rss.next()) {
                String itemId = rss.getString("item_id");
                String batchNo = rss.getString("batch_no");
                String qty = rss.getString("qty");
                String reorder = rss.getString("reorder_level");
                String remarks = rss.getString("remarks");
                data.add(new String[]{
                    itemId, batchNo, qty, reorder, remarks
                });
            }
            rss.close();
            stt.close();
            return data;
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return null;
    }

    public String[] selectItemDetails(String itemId) {
        final String query
                = "SELECT item_name,item_category,unit,qty "
                + "FROM item WHERE item_id=?";

        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;
        }

        try {

            String[] data = null;
            PreparedStatement stt = star.con.prepareStatement(query);
            stt.setString(1, CommonSqlUtil.encode(itemId));
            ResultSet rss = stt.executeQuery();
            if (rss.first()) {
                String itemName = rss.getString("item_name");
                String category = rss.getString("item_category");
                String unit = rss.getString("unit");
                String qty = rss.getString("qty");
                data = new String[]{itemId, itemName, category, unit, qty};
            }
            rss.close();
            stt.close();
            return data;
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return null;
    }

    public String selectQty(String ItemId) {

        String encodedItemId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, ItemId);
        String date = null;

        if (star.con == null) {
            log.error("Databse connection failiure.");

        } else {

            try {

                String query = "select * "
                        + "from item p "
                        + "where p.item_id=? ";
                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedItemId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    date = r.getString("p.qty");

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

    public ArrayList<ArrayList<String>> getTableApproveInfo(boolean approve,
            String searchKeyword, String fromDate, String toDate) {

        String encodedSearchKeyword = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                searchKeyword);

        String encodedFromDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                fromDate);
        String encodedToDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                toDate);
        String id = null;
        String supplierId = null;
        String purchaseId = null;
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
                        = "select p.*,if(is_approved = 0 ,'Pending','Approved') as state from purchase_order p "
                        + "left join supplier s on p.supplier_id = s.sid where"
                        + " (p.date between ? and ?) and p.purchase_order_id LIKE ? OR p.date LIKE ?"
                        + " OR s.sid LIKE ? having  p.is_approved=?";

                PreparedStatement pstmt = star.con.prepareStatement(query);

                pstmt.setString(1, encodedFromDate);
                pstmt.setString(2, encodedToDate);
                pstmt.setString(3, encodedSearchKeyword + "%");
                pstmt.setString(4, encodedSearchKeyword + "%");
                pstmt.setString(5, encodedSearchKeyword + "%");
                pstmt.setInt(6, isApprove);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    id = r.getString("p.id");
                    purchaseId = r.getString("p.purchase_order_id");
                    supplierId = r.getString("p.Supplier_id");
                    date = r.getString("p.date");
                    status = r.getString("state");

                    list.add(id);
                    list.add(supplierId);
                    list.add(purchaseId);
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

    public boolean approvePurchaseOrder(String id, String purchaseId,
            String userId, String date) {

        String encodedPurchaseId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                purchaseId);
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
                        = "UPDATE purchase_order set is_approved = ? , "
                        + "approve_user_id= ? , approve_date= ? "
                        + "where purchase_order_id = ? and id = ? ";
                PreparedStatement stmt = star.con.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, 1);
                stmt.setString(2, encodedUserId);
                stmt.setString(3, encodedDate);
                stmt.setString(4, encodedPurchaseId);
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

    public ArrayList<String> searchPurchaseDetails(String purchaseOrderId) {

        String encodedPurOrderId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                purchaseOrderId);


        String supplierId = null;
        String date = null;
        String userId = null;
        ArrayList<String> list = new ArrayList<String>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query
                        ="select * from purchase_order p left join "
                        + "(supplier s, user u) on s.sid = p.supplier_id "
                        + "and u.eid = p.user_id where p.purchase_order_id"
                        + " = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedPurOrderId);
//                pstmt.setString(2, encodedSupplierId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    supplierId = r.getString("s.sid");
                    date = r.getString("p.date");
                    userId = r.getString("u.name");

                    list.add(supplierId);
                    list.add(date);
                    list.add(userId);

                   

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

}
