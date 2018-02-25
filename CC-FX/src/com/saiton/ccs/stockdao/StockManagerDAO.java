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

public class StockManagerDAO {

    public static Starter star;//db connection
    Codec ORACLE_CODEC = new OracleCodec();
    private Logger log = Logger.getLogger(this.getClass());

    public ArrayList<ArrayList<String>> getTableInfo(String Date,
            String Category) {

        String encodedCategory = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                Category);
        String encodedDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, Date);

        String date = null;
        String itemId = null;
        String itemName = null;
        String qty = null;
        String avalibleQty = null;

        ArrayList<ArrayList<String>> Mainlist
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return null;

        } else {
            try {

                String query
                        = "select s.item_id,i.item_name,sum(s.qty) "
                        + "from stock_handler s "
                        + "left join item i "
                        + "on s.item_id=i.item_id "
                        + "where s.date=? and s.category=? and s.status=0 "
                        + "group by s.item_id ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedDate);
                pstmt.setString(2, encodedCategory);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    itemId = r.getString("s.item_id");
                    itemName = r.getString("i.item_name");
                    qty = r.getString("sum(s.qty)");

                    String query1 = null;

                    if ("Ala Carte".equals(Category)) {

                        query1 = "select sum(qty) "
                                + "from alacarte_stock "
                                + "where item_id=? ";
                    } else if ("Reservation".equals(Category)) {

                        query1 = "select sum(qty) "
                                + "from banquet_stock "
                                + "where item_id=? ";
                    } else if ("Banquet".equals(Category)) {

                        query1 = "select sum(qty) "
                                + "from room_stock "
                                + "where item_id=? ";
                    }

                    PreparedStatement pstmt1 = star.con.prepareStatement(query1);
                    pstmt1.setString(1, itemId);

                    ResultSet r1 = pstmt1.executeQuery();

                    while (r1.next()) {
                        avalibleQty = r1.getString("sum(qty)");

                        list.add(itemId);
                        list.add(itemName);
                        list.add(qty);
                        list.add(avalibleQty);
                        list.add(qty);
                    }

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

    public boolean updateStock(String Date, String itemId, String qty,
            String Category) {

        String encodedDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                Date);
        String encodedItemId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, itemId);
        String encodedCategory = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, Category);

        Double quantity = Double.parseDouble(qty);

        if (star.con == null) {
            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return false;
        } else {
            try {
                String sql = "update stock_handler "
                        + "set status=1 "
                        + "where category=? and date=? ";

                PreparedStatement stmt = star.con.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, encodedCategory);
                stmt.setString(2, encodedDate);

                int val = stmt.executeUpdate();

                String query1 = null;

                if ("Ala Carte".equals(Category)) {

                    query1 = "select item_id,batch_no,qty "
                            + "from alacarte_stock "
                            + "where item_id=? ";
                } else if ("Reservation".equals(Category)) {

                    query1 = "select item_id,batch_no,qty "
                            + "from banquet_stock "
                            + "where item_id=? ";
                } else if ("Banquet".equals(Category)) {

                    query1 = "select item_id,batch_no,qty "
                            + "from room_stock "
                            + "where item_id=? ";
                }

                PreparedStatement pstmt1 = star.con.prepareStatement(query1);
                pstmt1.setString(1, itemId);

                ResultSet r1 = pstmt1.executeQuery();
                String sql1=null;
                while (r1.next() && quantity > 0.0) {
                    if (r1.getDouble("qty") > quantity) {

                        if ("Ala Carte".equals(Category)) {

                            sql1 = "update alacarte_stock "
                                + "set qty=qty - ? "
                                + "where item_id=? and batch_no=? ";
                            
                        } else if ("Reservation".equals(Category)) {
                            sql1 = "update banquet_stock "
                                + "set qty=qty - ? "
                                + "where item_id=? and batch_no=? ";
                            
                        } else if ("Banquet".equals(Category)) {
                            sql1 = "update room_stock "
                                + "set qty=qty - ? "
                                + "where item_id=? and batch_no=? ";
                            
                        }

                        

                        PreparedStatement stmt1 = star.con.prepareStatement(sql1,
                                Statement.RETURN_GENERATED_KEYS);
                        stmt1.setDouble(1, quantity);
                        stmt1.setString(2, r1.getString("item_id"));
                        stmt1.setString(3, r1.getString("batch_no"));

                        val = stmt1.executeUpdate();
                        
                        quantity=0.0;
                    } else {
                        if ("Ala Carte".equals(Category)) {

                            sql1 = "update alacarte_stock "
                                + "set qty=0 "
                                + "where item_id=? and batch_no=? ";
                            
                        } else if ("Reservation".equals(Category)) {
                            sql1 = "update banquet_stock "
                                + "set qty=0 "
                                + "where item_id=? and batch_no=? ";
                            
                        } else if ("Banquet".equals(Category)) {
                            sql1 = "update room_stock "
                                + "set qty=0 "
                                + "where item_id=? and batch_no=? ";
                            
                        }

                        

                        PreparedStatement stmt1 = star.con.prepareStatement(sql1,
                                Statement.RETURN_GENERATED_KEYS);

                        stmt1.setString(1, r1.getString("item_id"));
                        stmt1.setString(2, r1.getString("batch_no"));

                        val = stmt1.executeUpdate();
                        
                        quantity=quantity-r1.getDouble("qty");
                    }
                    
                    
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
