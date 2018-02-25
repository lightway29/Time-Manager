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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

/**
 *
 * @author Miren
 */
public class ReturnNoteDAO {

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
                        "SELECT MAX(id) as ID FROM return_resolve");

                while (rs.next()) {
                    id = rs.getInt("id");
                }
                ResultSet rss = ste.executeQuery(
                        "SELECT resolve_id FROM return_resolve WHERE id= " + id + "");

                while (rss.next()) {
                    cid = rss.getString("resolve_id");
                }

                if (id != 0) {
                    String original = cid.split("N")[1];
                    int i = Integer.parseInt(original) + 1;

                    if (i < 10) {
                        final_id = "RTN000" + i;
                    } else if (i >= 10 && i < 100) {
                        final_id = "RTN00" + i;
                    } else if (i >= 100 && i < 1000) {
                        final_id = "RTN0" + i;
                    } else if (i >= 1000 && i < 10000) {
                        final_id = "RTN" + i;
                    }
                    return final_id;

                } else {
                    return "RTN0001";
                }
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException | SQLException e) {

                if (e instanceof ArrayIndexOutOfBoundsException) {
                    log.error("Exception tag --> " + "Split character error");
                } else if (e instanceof NumberFormatException) {
                    log.error(
                            "Exception tag --> " + "Invalid number found in current id");
                } else if (e instanceof SQLException) {
                    log.error(
                            "Exception tag --> " + "Invalid sql statement " + e.getMessage());
                }
                return null;

            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return null;
            }
        }
    }

    public ArrayList<ArrayList<String>> getTableInfo(boolean category,
            String searchKeyword, String fromDate, String toDate) {

        String encodedSearchKeyword = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                searchKeyword);

        String encodedFromDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                fromDate);
        String encodedToDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                toDate);
        String itemId = null;
        String ReturnNoteId = null;
        String BatchNo = null;
        String Date = null;
        String Name = null;
        String Type = null;
        String qty = null;

        ArrayList<ArrayList<String>> mainlist
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query = null;
                System.err.println("zxcfdsf" + category);
                if (category == false) {
                    query
                            = "select e.external_return_note_id as return_id,"
                            + "e.return_note_type,e.return_note_date,ei.item_id,"
                            + "ei.batch_no,ei.qty,i.item_name "
                            + "from external_return_note e "
                            + "join external_return_note_item ei "
                            + "on e.external_return_note_id=ei.external_return_note_id "
                            + "join item i "
                            + "on i.item_id=ei.item_id "
                            + "where (e.is_approved=1 and e.status=0 and "
                            + "e.return_note_date between ? and ? ) and "
                            + "e.external_return_note_id like ? ";
                } else {
                    query
                            = "select e.return_note_id as return_id,"
                            + "e.return_note_type,e.return_note_date,ei.item_id,"
                            + "ei.batch_no,ei.qty,i.item_name "
                            + "from return_note e "
                            + "join return_note_item ei "
                            + "on e.return_note_id=ei.return_note_id "
                            + "join item i "
                            + "on i.item_id=ei.item_id "
                            + "where (e.is_approved=1 and e.status=0 and "
                            + "e.return_note_date between ? and ?) and "
                            + "e.return_note_id like ? ";
                }

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedFromDate);
                pstmt.setString(2, encodedToDate);
                pstmt.setString(3, encodedSearchKeyword + "%");

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    itemId = r.getString("ei.item_id");
                    ReturnNoteId = r.getString("return_id");
                    BatchNo = r.getString("ei.batch_no");
                    Date = r.getString("e.return_note_date");
                    Name = r.getString("i.item_name");
                    Type = r.getString("e.return_note_type");
                    qty = r.getString("ei.qty");

                    list.add(itemId);
                    list.add(ReturnNoteId);
                    list.add(BatchNo);
                    list.add(Date);
                    list.add(Name);
                    list.add(Type);
                    list.add(qty);

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
        return mainlist;
    }

    public boolean updateData(String resolveId, String returnId,
            String returnDate, String itemId, String BatchNo, String resolveDate,
            String type, Double qty, String userId, boolean value) {

        String encodedResolveId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                resolveId);
        String encodedReturnId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, returnId);
        String encodedReturnDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                returnDate);
        String encodedItemId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, itemId);
        String encodedBatchNo = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                BatchNo);
        String encodedResolveDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                resolveDate);
        String encodedType = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, type);
        String encodedUserId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, userId);

        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return false;
        } else {
            try {

                boolean accept = false;
                if (returnId.contains("ERN")) {

                    accept = true;
                }

                String sql = null;
                if (accept == true) {
                    sql = "UPDATE external_return_note set status = 1 where external_return_note_id = ? ";
                } else {
                    sql = "UPDATE return_note set status = 1 where return_note_id = ? ";
                }
                PreparedStatement stmt = star.con.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, encodedReturnId);

                int val = stmt.executeUpdate();

                String sql1 = "INSERT INTO `return_resolve` (`resolve_id`, "
                        + "`return_note_id`, `return_date`, `item_id`, "
                        + "`batch_no`, `resolve_date`, `type`, `qty`, `user_id`) "
                        + "VALUES(?,?,?,?,?,?,?,?,?)";
                PreparedStatement stmt1 = star.con.prepareStatement(sql1,
                        Statement.RETURN_GENERATED_KEYS);
                stmt1.setString(1, encodedResolveId);
                stmt1.setString(2, encodedReturnId);
                stmt1.setString(3, encodedReturnDate);
                stmt1.setString(4, encodedItemId);
                stmt1.setString(5, encodedBatchNo);
                stmt1.setString(6, encodedResolveDate);
                stmt1.setString(7, encodedType);
                stmt1.setDouble(8, qty);
                stmt1.setString(9, encodedUserId);

                val = stmt1.executeUpdate();

                if (value == true) {
                    String sql2 = null;
                    if (type.contains("Banquet")) {
                        sql2 = "UPDATE banquet_stock set qty = qty + ? where item_id = ? and batch_no=? ";
                    } else if (type.contains("Ala Carte")) {
                        sql2 = "UPDATE alacarte_stock set qty = qty + ? where item_id = ? and batch_no= ? ";
                    } else if (type.contains("room_stock")) {
                        sql2 = "UPDATE external_return_note set qty = qty + ? where item_id = ? and batch_no= ? ";
                    }

                    PreparedStatement stmt2 = star.con.prepareStatement(sql2,
                            Statement.RETURN_GENERATED_KEYS);
                    stmt2.setDouble(1, qty);
                    stmt2.setString(2, encodedItemId);
                    stmt2.setString(3, encodedBatchNo);
                    val = stmt2.executeUpdate();
                }

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
