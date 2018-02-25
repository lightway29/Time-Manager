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
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

/**
 *
 * @author Miren
 */
public class ItemDAO {

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
                ResultSet rs = st.executeQuery("SELECT MAX(id) as ID FROM item");

                while (rs.next()) {
                    id = rs.getInt("id");
                }
                ResultSet rss = ste.executeQuery(
                        "SELECT item_id FROM item WHERE id= " + id + "");

                while (rss.next()) {
                    cid = rss.getString("item_id");
                }

                if (id != 0) {
                    String original = cid.split("M")[1];
                    int i = Integer.parseInt(original) + 1;

                    if (i < 10) {
                        final_id = "ITM000" + i;
                    } else if (i >= 10 && i < 100) {
                        final_id = "ITM00" + i;
                    } else if (i >= 100 && i < 1000) {
                        final_id = "ITM0" + i;
                    } else if (i >= 1000 && i < 10000) {
                        final_id = "ITM" + i;
                    }
                    return final_id;

                } else {
                    return "ITM0001";
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
                ResultSet rs = st.executeQuery("SELECT MAX(id) as ID FROM item");

                while (rs.next()) {
                    id = rs.getInt("id");
                }
                ResultSet rss = ste.executeQuery(
                        "SELECT item_id FROM item WHERE id= " + id + "");

                while (rss.next()) {
                    cid = rss.getString("item_id");
                }

                if (id != 0) {
                    String original = cid.split("M")[1];
                    int i = Integer.parseInt(original) + no;

                    if (i < 10) {
                        final_id = "ITM000" + i;
                    } else if (i >= 10 && i < 100) {
                        final_id = "ITM00" + i;
                    } else if (i >= 100 && i < 1000) {
                        final_id = "ITM0" + i;
                    } else if (i >= 1000 && i < 10000) {
                        final_id = "ITM" + i;
                    }
                    return final_id;

                } else {
                    int i = no;
                    if (i < 10) {
                        final_id = "ITM000" + i;
                    } else if (i >= 10 && i < 100) {
                        final_id = "ITM00" + i;
                    } else if (i >= 100 && i < 1000) {
                        final_id = "ITM0" + i;
                    } else if (i >= 1000 && i < 10000) {
                        final_id = "ITM" + i;
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

    public ArrayList<String> loadingCategory() {

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
        return list;
    }

    public boolean checkingCategoryAvailability(String desc) {

        String encodedDesc = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, desc);
        boolean available = false;

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");

        } else {
            try {

                String query
                        = "SELECT * FROM item_category i where i.category= ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedDesc);
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    available = true;
                }
                if (available == false) {
                    insertCategory(desc);
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

    public boolean insertCategory(String desc) {
        String encodedDesc = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, desc);
        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return false;
        } else {
            try {
                String sql
                        = "INSERT INTO item_category (`category`) VALUES (?);";

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

    public boolean deleteCategory(String Desc) {
        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return false;
        } else {
            String encodedDesc = ESAPI.encoder().
                    encodeForSQL(ORACLE_CODEC, Desc);
            try {
                String sql = "DELETE FROM item_category where `category`= ? ";
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

    public boolean checkingItemAvailability(String itemId) {

        String encodedItemId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, itemId);
        boolean available = false;

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");

        } else {
            try {

                String query = "SELECT * FROM item where item_id= ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedItemId);
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

    public boolean checkingItemNameAvailability(String itemName) {

//        String encodedItemName = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, itemName);
        String encodedItemName = itemName;
        boolean available = false;

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");

        } else {
            try {

                String query = "SELECT * FROM item  where item_name= ?";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedItemName);

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
                        + "from item i "
                        + "left join user u "
                        + "on u.eid=i.user_id "
                        + "where i.item_id=? ";

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

    public ArrayList<ArrayList<String>> searchItemDetails(String item) {

        String encodedItem = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, item);

        String itemId = null;
        String itemName = null;
        String itemDesc = null;
        String qty = null;

        String price = null;

        ArrayList<ArrayList<String>> Mainlist
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * "
                        + "From item "
                        + "Where item_id LIKE ? or item_name LIKE ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedItem + "%");
                pstmt.setString(2, encodedItem + "%");
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    itemId = r.getString("item_id");
                    itemName = r.getString("item_name");
                    itemDesc = r.getString("item_description");

                    
                    list.add(itemId);
                    list.add(itemName);
                    list.add(itemDesc);

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

    public ArrayList<ArrayList<String>> searchItemBatchDetails(String item) {

        String encodedItem = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, item);

        String batchNo = null;
        String buyingPrice = null;
        String sellingPrice = null;

        ArrayList<ArrayList<String>> Mainlist
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * "
                        + "From item_sub "
                        + "Where item_id LIKE ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedItem + "%");
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    batchNo = r.getString("batch_no");
                    buyingPrice = r.getString("buying_price");
                    sellingPrice = r.getString("selling_price");
                    

                    list.add(batchNo);
                    list.add(buyingPrice);
                    list.add(sellingPrice);

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

    public ArrayList getPrice(String item, String batchNo) {

        String encodedItem = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, item);
        String encodedBatchNo = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                batchNo);

        String buyingPrice = null;
        String sellingPrice = null;
        String unitValue = null;
        String qty = null;
        
        ArrayList batchList = new ArrayList();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * "
                        + " From item_sub s JOIN item_unit_value u ON "
                        + " s.unit = u.id "
                        + "Where s.item_id = ? and s.batch_no = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedItem);
                pstmt.setString(2, encodedBatchNo);
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    buyingPrice = r.getString("s.buying_price");
                    sellingPrice = r.getString("s.selling_price");
                    unitValue = r.getString("s.unit");
                    qty = r.getString("s.qty");
                    
                    batchList.add(buyingPrice);
                    batchList.add(sellingPrice);
                    batchList.add(unitValue);
                    batchList.add(qty);
                    
                    
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
        return batchList;
    }

    public boolean deleteItem(String item) {

        String encodedItem = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, item);
        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return false;
        } else {
            try {
                String sql = "DELETE FROM item WHERE item_id='" + encodedItem
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

    public boolean additem(
            String itemId,
            String itemName,
            double qty,
            String userId,
            String itemDesc,
            String partNo,
            String itemMainCategory,
            String itemSubCategory,
            String batchNo,
            double buyingPrice,
            double reorderLevel,
            double sellingPrice,
            String unit) {

        String encodedItemId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, itemId);
//      String encodedItemName = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, itemName);
        String encodedItemName = itemName;

        String encodeduserId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, userId);

        String encodedBatchNo = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                batchNo);
        boolean available = false;
        boolean available1 = false;
        boolean value = false;

        if (star.con == null) {

            log.error("Exception tag --> " + "Database connection failiure. ");

        } else {
            try {

                String query = "SELECT * FROM item where item_id= ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedItemId);
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    value = updateItems(itemId,
                            itemName,
                            qty,
                            userId,
                            itemDesc,
                            partNo,
                            itemMainCategory,
                            itemSubCategory);

                    available = true;
                }
                if (available == false) {
                    value = insertItems(
                            itemId,
                            itemName,
                            qty,
                            userId,
                            itemDesc,
                            partNo,
                            itemMainCategory,
                            itemSubCategory);
                }

                String query1
                        = "SELECT * FROM item_sub where item_id=? and batch_no=? ";

                PreparedStatement pstmt1 = star.con.prepareStatement(query1);
                pstmt1.setString(1, encodedItemId);
                pstmt1.setString(2, encodedBatchNo);
                ResultSet r1 = pstmt1.executeQuery();

                while (r1.next()) {
                    available1 = true;
                    value = updateItemsSub(
                            itemId,
                            batchNo,
                            qty,
                            buyingPrice,
                            reorderLevel,
                            sellingPrice,
                            unit);
                }
                if (available1 == false) {
                    value = insertItemsSub(itemId,
                            batchNo,
                            qty,
                            buyingPrice,
                            reorderLevel,
                            sellingPrice,
                            unit);
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

    public Boolean insertItems(
            String itemId,
            String itemName,
            double qty,
            String userId,
            String itemDesc,
            String partNo,
            String itemMainCategory,
            String itemSubCategory
    ) {
        String encodedItemId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, itemId);
        String encodedItemName = itemName;
        double encodedQty = qty;
        String encodeduserId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, userId);
        String encodedItemDesc = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, itemDesc);
        String encodedPartNo = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, partNo);
        String encodedItemMainCategory = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, itemMainCategory);
        String encodedItemSubCategory = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, itemSubCategory);

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {
            try {

                PreparedStatement ps = star.con.prepareStatement(
                        "INSERT INTO item("
                        + "`item_id`,"
                        + " `item_name`,"
                        + " `qty`,"
                        + " `user_id`,"
                        + " `item_description`,"
                        + " `part_no`,"
                        + " `item_main_category`,"
                        + " `item_sub_category`) "
                        + "VALUES(?,?,?,?,?,?,?,?)");

                ps.setString(1, encodedItemId);
                ps.setString(2, encodedItemName);
                ps.setDouble(3, encodedQty);
                ps.setString(4, encodeduserId);
                ps.setString(5, encodedItemDesc);
                ps.setString(6, encodedPartNo);
                ps.setString(7, encodedItemMainCategory);
                ps.setString(8, encodedItemSubCategory);

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

    public Boolean updateItems(
            String itemId,
            String itemName,
            double qty,
            String userId,
            String itemDesc,
            String partNo,
            String itemMainCategory,
            String itemSubCategory
    ) {
        String encodedItemId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, itemId);

        String encodedItemName = itemName;
        String encodeduserId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, userId);
        String encodedItemMainCategory = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, itemMainCategory);
        String encodedItemSubCategory = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, itemSubCategory);

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {
            try {

                PreparedStatement ps = star.con.prepareStatement(
                        "UPDATE item SET "
                        + "`item_name`=? , "
                        + "`qty`=? ,"
                        + "`user_id`=? , "
                        + "`item_description`=? , "
                        + "`part_no`=? , "
                        + "`item_main_category`=? , "
                        + "`item_sub_category`=?  "
                        + " WHERE `item_id`=? ");

                ps.setString(1, encodedItemName);
                ps.setDouble(2, qty);
                ps.setString(3, encodeduserId);
                ps.setString(4, itemDesc);
                ps.setString(5, partNo);
                ps.setString(6, encodedItemMainCategory);
                ps.setString(7, encodedItemSubCategory);
                ps.setString(8, encodedItemId);

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

    public Boolean insertItemsSub(
            String itemId,
            String batchNo,
            double qty,
            double buyingPrice,
            double reorderLevel,
            double sellingPrice,
            String unit) {
        String encodedItemId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, itemId);
        String encodedBatchNo = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                batchNo);
        String encodedUnit = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                unit);

        if (star.con == null) {
            log.error("Database connection failiure.");
            return false;
        } else {
            try {

                PreparedStatement ps = star.con.prepareStatement(
                        "INSERT INTO item_sub("
                        + "`item_id`,"
                        + " `batch_no`,"
                        + " `qty`,"
                        + " `buying_price`,"
                        + " `reorder_level`,"
                        + " `selling_price`,"
                        + " `unit`) "
                        + "VALUES(?,?,?,?,?,?,?)");

                ps.setString(1, encodedItemId);
                ps.setString(2, encodedBatchNo);
                ps.setDouble(3, qty);
                ps.setDouble(4, buyingPrice);
                ps.setDouble(5, reorderLevel);
                ps.setDouble(6, sellingPrice);
                ps.setString(7, unit);

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

    public Boolean updateItemsSub(
            String itemId,
            String batchNo,
            double qty,
            double buyingPrice,
            double reorderLevel,
            double sellingPrice,
            String unit) {
        String encodedItemId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, itemId);
        String encodedBatchNo = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                batchNo);

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {
            try {

                PreparedStatement ps = star.con.prepareStatement(
                        "UPDATE item_sub SET "
                        + "`qty`=? ,"
                        + "`buying_price`=? ,"
                        + "`reorder_level`=? ,"
                        + "`selling_price`=? ,"
                        + "`unit`=? ,"
                        + "WHERE `item_id`=? and `batch_no`=? ");

                ps.setDouble(1, qty);
                ps.setDouble(2, buyingPrice);
                ps.setDouble(3, reorderLevel);
                ps.setDouble(4, sellingPrice);
                ps.setString(5, encodedItemId);
                ps.setString(6, encodedBatchNo);

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

    public String generateBatchID(String ItemNo) {
        String encodedItemNo = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, ItemNo);
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
                        "SELECT MAX(id) as ID FROM item_sub "
                        + "where item_id= '" + encodedItemNo + "' ");

                while (rs.next()) {
                    id = rs.getInt("id");
                }
                ResultSet rss = ste.executeQuery(
                        "SELECT batch_no FROM item_sub WHERE id= " + id + "");

                while (rss.next()) {
                    cid = rss.getString("batch_no");
                }

                if (id != 0) {
                    String original = cid.split("T")[1];
                    int i = Integer.parseInt(original) + 1;

                    if (i < 10) {
                        final_id = "BAT000" + i;
                    } else if (i >= 10 && i < 100) {
                        final_id = "BAT00" + i;
                    } else if (i >= 100 && i < 1000) {
                        final_id = "BAT0" + i;
                    } else if (i >= 1000 && i < 10000) {
                        final_id = "BAT" + i;
                    }
                    return final_id;

                } else {
                    return "BAT0001";
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

    public Boolean insertMainCategory(
            String mainCategory) {

        if (star.con == null) {

            log.error("Exception tag --> " + "Database connection failiure. ");
            return null;

        } else {
            try {

                PreparedStatement ps = star.con.prepareStatement("INSERT INTO "
                        + "item_main_category (title) VALUES(?)");
                ps.setString(1, mainCategory);

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

    public ArrayList loadMainCategory() {

        String mainCategory = null;
        ArrayList mainCategoryList = new ArrayList();

        if (star.con == null) {
            log.error("Database connection failiure.");
        } else {
            try {
                Statement stt = star.con.createStatement();
                ResultSet r = stt.
                        executeQuery("SELECT * FROM item_main_category");
                while (r.next()) {
                    mainCategory = r.getString("title");

                    mainCategoryList.add(mainCategory);
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
        return mainCategoryList;
    }

    public boolean deleteMainCategory(String mainCategory) {
        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return false;
        } else {
            String encodedTitle = ESAPI.encoder().
                    encodeForSQL(ORACLE_CODEC, mainCategory);
            try {
                String sql = "DELETE FROM item_main_category where `title`= ? ";
                PreparedStatement stmt = Starter.con.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, encodedTitle);
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

    public Boolean insertSubCategory(
            String subCategory,int mainId) {
        
        String encodedSubCategory = ESAPI.encoder().
                    encodeForSQL(ORACLE_CODEC, subCategory);

        if (star.con == null) {

            log.error("Exception tag --> " + "Database connection failiure. ");
            return null;

        } else {
            try {

                PreparedStatement ps = star.con.prepareStatement("INSERT INTO "
                        + "item_sub_category (item_Sub_Category,item_main_category)"
                        + " VALUES(?,?)");
                ps.setString(1, encodedSubCategory);
                ps.setInt(2, mainId);

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

    //Need some modification
    public ArrayList loadSubCategory(int mainId) {

        String subCategory = null;
        ArrayList subCategoryList = new ArrayList();

        if (star.con == null) {
            log.error("Database connection failiure.");
        } else {
            try {
                Statement stt = star.con.createStatement();
               
                
                String query = " SELECT * FROM item_sub_category "
                                + " Where item_main_category = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setInt(1, mainId);
                
                ResultSet r = pstmt.executeQuery();
                
                while (r.next()) {
                    
                    subCategory = r.getString("item_sub_category");
                    subCategoryList.add(subCategory);
                    
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
        return subCategoryList;
    }

    public boolean deleteSubCategory(String subCategory,int mainId) {
        
        String encodedSubCategory = ESAPI.encoder().
                    encodeForSQL(ORACLE_CODEC, subCategory);
        
        
        if (star.con == null) {
            
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return false;
        
        } else {
           
            try {
                String sql = "DELETE FROM item_sub_category where "
                        + "`item_sub_category`= ? AND item_main_category = ? ";
                PreparedStatement stmt = Starter.con.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                
                stmt.setString(1, encodedSubCategory);
                stmt.setInt(2, mainId);
                
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
    
    public String getMainCategoryId(String mainCategory) {

        String encodedMaincategory = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                mainCategory);
       

        String id = null;

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * "
                        + "From item_main_category "
                        + "Where title = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedMaincategory);
                
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    id = r.getString("id");
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
        return id;
    }
    
    public String getSubCategoryId(String subCategory,int mainCategoryId) {

        String encodedSubCategory = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                subCategory);
       

        String id = null;

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * "
                        + "From item_sub_category "
                        + "Where item_sub_category = ? AND item_main_category = ?";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedSubCategory);
                pstmt.setInt(2, mainCategoryId);
                
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    id = r.getString("id");
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
        return id;
    }

    public ArrayList loadUnit() {

        String unit = null;
        ArrayList unitList = new ArrayList();

        if (star.con == null) {
            log.error("Database connection failiure.");
        } else {
            try {
                Statement stt = star.con.createStatement();
                ResultSet r = stt.
                        executeQuery("SELECT * FROM item_unit");
                while (r.next()) {
                    unit = r.getString("unit");

                    unitList.add(unit);
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
        return unitList;
    }
    
     public Boolean insertUnit(
            String unit) {

        if (star.con == null) {

            log.error("Exception tag --> " + "Database connection failiure. ");
            return null;

        } else {
            try {

                PreparedStatement ps = star.con.prepareStatement("INSERT INTO "
                        + "item_unit (unit) VALUES(?)");
                ps.setString(1, unit);

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
     
      public Boolean insertUnitQty(
            String unitQty,int unitId) {
        
        String encodedUnitQty = ESAPI.encoder().
                    encodeForSQL(ORACLE_CODEC, unitQty);

        if (star.con == null) {

            log.error("Exception tag --> " + "Database connection failiure. ");
            return null;

        } else {
            try {

                PreparedStatement ps = star.con.prepareStatement("INSERT INTO "
                        + "item_unit_value (unit_qty,item_unit)"
                        + " VALUES(?,?)");
                ps.setString(1, encodedUnitQty);
                ps.setInt(2, unitId);

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
     
     public boolean deleteUnit(String unit) {
        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return false;
        } else {
            String encodedUnit = ESAPI.encoder().
                    encodeForSQL(ORACLE_CODEC, unit);
            try {
                String sql = "DELETE FROM item_unit where `unit`= ? ";
                PreparedStatement stmt = Starter.con.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                
                stmt.setString(1, encodedUnit);
                
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
     
     public ArrayList loadUnitQty(int unitId) {

        String unitQty = null;
        ArrayList unitQtyList = new ArrayList();

        if (star.con == null) {
            log.error("Database connection failiure.");
        } else {
            try {
                Statement stt = star.con.createStatement();
               
                
                String query = " SELECT * FROM item_unit_value "
                                + " Where item_unit = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setInt(1, unitId);
                
                ResultSet r = pstmt.executeQuery();
                
                while (r.next()) {
                    
                    unitQty = r.getString("unit_qty");
                    unitQtyList.add(unitQty);
                    
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
        return unitQtyList;
    }
     
     public String getUnitId(String unit) {

        String encodedUnit = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                unit);
       

        String id = null;

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * "
                        + "From item_unit "
                        + "Where unit = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedUnit);
                
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    id = r.getString("id");
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
        return id;
    }
     
     public boolean deleteUnitQty(String unitQty,int unitId) {
        
        String encodedUnitQty = ESAPI.encoder().
                    encodeForSQL(ORACLE_CODEC, unitQty);
        
        
        if (star.con == null) {
            
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return false;
        
        } else {
           
            try {
                String sql = "DELETE FROM item_unit_value where "
                        + "`unit_qty`= ? AND item_unit = ? ";
                PreparedStatement stmt = Starter.con.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                
                stmt.setString(1, encodedUnitQty);
                stmt.setInt(2, unitId);
                
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
     
     public ArrayList<String> loadItemData(String itemId) {
         
          String encodedItemId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                itemId);
       

        String itemDesc = null;
        String itemPartNo = null;
        String itemMainCat = null;
        String itemSubCat = null;
        ArrayList list = new ArrayList();

        if (star.con == null) {
            log.error(" Exception tag --> " + "Database connection failiure. ");
            return null;

        } else {
            try {

               String query = "SELECT * "
                        + "From item "
                        + "Where item_id = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedItemId);
                
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    
                    itemDesc = r.getString("item_description");
                    itemPartNo = r.getString("part_no");
                    itemMainCat = r.getString("item_main_category");
                    itemSubCat = r.getString("item_sub_category");
                    
                    list.add(itemDesc);
                    list.add(itemPartNo);
                    list.add(itemMainCat);
                    list.add(itemSubCat);

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
        return list;
    }
     
     public String getMainCategory(String mainId) {

        String encodedMainId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                mainId);
       

        String title = null;

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * "
                        + "From item_main_category "
                        + "Where id = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedMainId);
                
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    title = r.getString("title");
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
        return title;
    }
     
     public String getSubCategory(String mainId ,String subId) {

        String encodedMainId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                mainId);
        String encodedSubId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                subId);
       

        String title = null;

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * "
                        + "From item_sub_category "
                        + "Where id = ? AND item_main_category = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedSubId);
                pstmt.setString(2, encodedMainId);
                
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    title = r.getString("item_sub_category");
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
        return title;
    }
     
     public ArrayList<String> loadUnitValue(String unitValueId) {
         
          String encodedUnitValueId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                unitValueId);
       

        String unit = null;
        String unitValue = null;
     
        ArrayList list = new ArrayList();

        if (star.con == null) {
            log.error(" Exception tag --> " + "Database connection failiure. ");
            return null;

        } else {
            try {

               String query = "SELECT * "
                        + " From item_unit u JOIN item_unit_value v ON "
                       + " u.id = v.item_unit "
                        + "Where v.id = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedUnitValueId);
                
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    
                    unit = r.getString("u.unit");
                    unitValue = r.getString("v.unit_qty");
                  
                    
                    list.add(unit);
                    list.add(unitValue);
              

                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {
                    log.error("Exception tag --> "
                            + "Invalid entry location for list");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement "+e);
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
     
     public String getUnitQtyId(String unitQty,String unit) {

        String encodedUnitQty = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                unitQty);
        
        String encodedUnit = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                unit);
       

        String id = null;

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * "
                        + "From item_unit u JOIN item_unit_value v "
                        + " ON u.id = v.item_unit "
                        + " Where u.unit = ? AND v.unit_qty = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                
                pstmt.setString(1, encodedUnit);
                pstmt.setString(2, encodedUnitQty);
                
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    id = r.getString("v.id");
                }

            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {

                    log.error("Exception tag --> "
                            + "Invalid entry location for list");

                } else if (e instanceof SQLException) {

                    log.
                            error("Exception tag --> " + "Invalid sql statement"
                                    );

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
    }
     
     

}
