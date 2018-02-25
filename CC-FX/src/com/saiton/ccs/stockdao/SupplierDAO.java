package com.saiton.ccs.stockdao;

import com.saiton.ccs.commondao.CommonSqlUtil;
import com.saiton.ccs.database.Starter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class SupplierDAO {

    public static Starter star;//db connection
    Codec ORACLE_CODEC = new OracleCodec();
    private Logger log = Logger.getLogger(this.getClass());

    public String generateSupplierId() {
        return CommonSqlUtil.
                generateId(star.con, "id", "sid", "supplier", "SUP", log);
    }

    public List<String> selectTelephoneNumbers(String sid) {
        return CommonSqlUtil.genericSelectStrings(star.con, "supplier_tel",
                "tel", "sid", sid, log);
    }

    public Boolean setTelephoneNumbers(String sid, List<String> telephones) {
        final String queryAdd = "INSERT INTO `supplier_tel` "
                + "(`tel`, " + "`sid`) " + "VALUES " + "(?,?);";

        if (star.con == null) {
            log.error(" Exception tag --> "
                    + "Databse connection failiure. ");
            return null;
        }

        try {
            CommonSqlUtil.genericDelete(star.con, "supplier_tel", "sid",
                    sid, log);
            PreparedStatement add = star.con.prepareStatement(queryAdd);
            for (String tel : telephones) {
                add.setString(1, CommonSqlUtil.encode(tel));
                add.setString(2, CommonSqlUtil.encode(sid));
                add.executeUpdate();
            }
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public List<String[]> selectItems(String sid) {
        final String query
                = "select i.item_id as 'iid',i.item_name as 'name',i.qty "
                + "from supplier s,item i,supplier_item si where  "
                + "s.sid = si.sid and i.item_id=si.item_id and s.sid=?;";
        if (star.con == null) {
            log.error(" Exception tag --> " + "Database connection failiure. ");
            return null;
        }

        try {

            ArrayList<String[]> data = new ArrayList<>();
            PreparedStatement stt = star.con.prepareStatement(query);
            stt.setString(1, sid);
            ResultSet rss = stt.executeQuery();
            while (rss.next()) {
                String iid = rss.getString("iid");
                String name = rss.getString("name");
                String qty = rss.getString("qty");
                data.add(new String[]{
                    iid, name, qty
                });
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return null;
    }

    public List<String[]> searchItemsBySupplier(String sid, String keyword) {
        final String query
                = "select i.item_id as 'iid',i.item_name as 'name',i.qty "
                + "from supplier s,item i,supplier_item si where  "
                + "s.sid = si.sid and i.item_id=si.item_id and s.sid LIKE ? and"
                + " (i.item_id LIKE ? or i.item_name LIKE ?)";
        if (star.con == null) {
            log.error(" Exception tag --> " + "Database connection failiure. ");
            return null;
        }

        try {

            ArrayList<String[]> data = new ArrayList<>();
            PreparedStatement stt = star.con.prepareStatement(query);
            stt.setString(1, sid + "%");
            stt.setString(2, keyword + "%");
            stt.setString(3, keyword + "%");
            ResultSet rss = stt.executeQuery();
            while (rss.next()) {
                String iid = rss.getString("iid");
                String name = rss.getString("name");

                String qty = rss.getString("qty");
                data.add(new String[]{
                    iid, name, qty
                });
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return null;
    }

    public List<String[]> selectAllItems() {
        final String query
                = "select i.item_id as 'iid',i.item_name as 'name' from item i;";
        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;
        }
        System.out.println("Changed - 25/07/2015");
        try {

            ArrayList<String[]> data = new ArrayList<>();
            PreparedStatement stt = star.con.prepareStatement(query);
            ResultSet rss = stt.executeQuery();
            while (rss.next()) {
                String iid = rss.getString("iid");
                String name = rss.getString("name");
//                String cat = rss.getString("cat");
//                String unit = rss.getString("unit");

                data.add(new String[]{
                    iid, name
                });
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return null;
    }

    public List<String[]> selectAllSuppliers() {
        final String query
                = "SELECT `sid`,`name`,`reg_no`,`address` FROM `supplier`;";
        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;
        }

        try {

            List<String[]> data = new ArrayList<>();
            PreparedStatement stt = star.con.prepareStatement(query);
            ResultSet rss = stt.executeQuery();
            while (rss.next()) {
                String sid = rss.getString("sid");
                String name = rss.getString("name");
                String regNo = rss.getString("reg_no");
                String address = rss.getString("address");
                data.add(new String[]{
                    sid, name, regNo, address
                });
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return null;
    }

    public Boolean setItems(String sid, List<String> items) {
        final String queryAdd
                = "INSERT INTO `supplier_item` (`item_id`,`sid`) VALUES(?,?);";

        if (star.con == null) {
            log.error(" Exception tag --> "
                    + "Databse connection failiure. ");
            return null;
        }

        try {
            CommonSqlUtil.genericDelete(star.con, "supplier_item", "sid",
                    sid, log);
            PreparedStatement add = star.con.prepareStatement(queryAdd);
            for (String item : items) {
                add.setString(1, CommonSqlUtil.encode(item));
                add.setString(2, CommonSqlUtil.encode(sid));
                add.executeUpdate();
            }
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public String insertSupplier(String name, String regNo, String address) {
        String sid = generateSupplierId();
        String query
                = "INSERT INTO `supplier`"
                + "(`sid`,`name`,`reg_no`,`address`) " + "VALUES (?,?,?,?);";
        if (star.con == null) {
            log.error(" Exception tag --> "
                    + "Databse connection failiure. ");
            return null;
        }

        try {

            PreparedStatement st = star.con.prepareStatement(query);
            st.setString(1, CommonSqlUtil.encode(sid));
            st.setString(2, CommonSqlUtil.encode(name));
            st.setString(3, CommonSqlUtil.encode(regNo));
            st.setString(4, CommonSqlUtil.encode(address));
            return st.executeUpdate() == 1 ? sid : null; //one row

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean updateSupplier(String sid, String name, String regNo,
            String address) {
        String query
                = "UPDATE `supplier`" + "SET" + "`name` = ?," + "`reg_no` = ?,"
                + "`address` = ?" + "WHERE `sid` = ?;";
        if (star.con == null) {
            log.error(" Exception tag --> "
                    + "Databse connection failiure. ");
            return null;
        }

        try {

            PreparedStatement st = star.con.prepareStatement(query);

            st.setString(1, CommonSqlUtil.encode(name));
            st.setString(2, CommonSqlUtil.encode(regNo));
            st.setString(3, CommonSqlUtil.encode(address));
            st.setString(4, CommonSqlUtil.encode(sid));
            return st.executeUpdate() == 1; //one row

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean deleteSupplier(String sid) {
        return CommonSqlUtil.
                genericDelete(star.con, "supplier", "sid", sid, log);
    }

    public List<String[]> selectItemByName(String title) {
        final String query
                = "select item_id as 'iid', item_name as 'name', item_category as 'cat',"
                + " unit, qty "
                + "from item where  "
                + "item_name LIKE ?";
        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;
        }

        try {
            ArrayList<String[]> data = new ArrayList<>();
            PreparedStatement stt = star.con.prepareStatement(query);
            stt.setString(1, title + "%");
            ResultSet rss = stt.executeQuery();
            while (rss.next()) {
                String iid = rss.getString("iid");
                String name = rss.getString("name");
                String cat = rss.getString("cat");
                String unit = rss.getString("unit");
                String qty = rss.getString("qty");
                data.add(new String[]{
                    iid, name, cat, unit, qty
                });
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return null;
    }

    public List<String[]> searchAllSuppliers(String keyword) {
        final String query
                = "SELECT * "
                + "From supplier "
                + "Where sid LIKE ? or name LIKE ? or reg_no LIKE ?";
        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;
        }

        try {

            List<String[]> data = new ArrayList<>();
            PreparedStatement stt = star.con.prepareStatement(query);
            stt.setString(1, keyword + "%");
            stt.setString(2, keyword + "%");
            stt.setString(3, keyword + "%");
            ResultSet rss = stt.executeQuery();
            while (rss.next()) {
                String sid = rss.getString("sid");
                String name = rss.getString("name");
                String regNo = rss.getString("reg_no");
                String address = rss.getString("address");
                data.add(new String[]{
                    sid, name, regNo, address
                });
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return null;
    }

    public List<String[]> searchItems(String keyword) {
        final String query
                = "select item_id as 'iid', item_name as 'name', item_category as 'cat',"
                + " unit, qty "
                + "from item where  "
                + "item_name LIKE ? or item_id LIKE ? ";
        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;
        }

        try {

            List<String[]> data = new ArrayList<>();
            PreparedStatement stt = star.con.prepareStatement(query);
            stt.setString(1, keyword + "%");
            stt.setString(2, keyword + "%");

            ResultSet rss = stt.executeQuery();
            while (rss.next()) {
                String iid = rss.getString("iid");
                String name = rss.getString("name");
                String cat = rss.getString("cat");
                String unit = rss.getString("unit");
                String qty = rss.getString("qty");
                data.add(new String[]{
                    iid, name, cat, unit, qty
                });
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return null;
    }

    public ArrayList<ArrayList< String>> searchSuppliers(String search) {

        String encodedsearch = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, search);

        String supplierId = null;
        String name = null;
        String regNo = null;
        String address = null;

        ArrayList<ArrayList<String>> mainList
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * "
                        + "From supplier "
                        + "Where sid LIKE ? or name LIKE ? or reg_no LIKE ?";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedsearch + "%");
                pstmt.setString(2, encodedsearch + "%");
                pstmt.setString(3, encodedsearch + "%");
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    supplierId = r.getString("sid");
                    name = r.getString("name");
                    regNo = r.getString("reg_no");
                    address = r.getString("address");

                    list.add(supplierId);
                    list.add(name);
                    list.add(regNo);
                    list.add(address);

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
}
