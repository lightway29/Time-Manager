/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.settingsdao;

import com.saiton.ccs.commondao.CommonSqlUtil;
import com.saiton.ccs.database.Starter;
import static com.saiton.ccs.reportdao.PrinterReportDAO.star;
import static com.saiton.ccs.stockdao.ItemDAO.star;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

/**
 *
 * @author Isu
 */
public class InvoiceSettingsDAO {

    public static Starter star;//db connection
    Codec ORACLE_CODEC = new OracleCodec();
    private final Logger log = Logger.getLogger(this.getClass());

    public Boolean checkingTaxAvailability(int tax, String invoiceId) {

        boolean available = false;
        boolean status = false;

        if (star.con == null) {

            log.error("Exception tag --> " + "Databse connection failiure. ");

        } else {
            try {

                String query = "SELECT * FROM invoice_id_reset i where i.is_tax_invoice= ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setInt(1, tax);
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    available = true;
                }

                if (available == false) {
                    status = insertRecord(invoiceId, tax);
                } else {
                    status = updateRecord(invoiceId, tax);
                }

            } catch (NullPointerException | SQLException e) {
                if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry passed");

                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement");
                }
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
            }
        }
        return status;
    }

    public Boolean insertRecord(String invoiceId, int tax) {

        String encodedInvId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, invoiceId);

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return false;
        } else {
            try {
                String sql = "INSERT INTO `invoice_id_reset` " + "(`reset_invoice_id`, "
                        + "`is_tax_invoice`) " + "VALUES " + "(?,?);";

                PreparedStatement stmt = Starter.con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, encodedInvId);
                stmt.setInt(2, tax);

                int val = stmt.executeUpdate();

                if (val == 1) {
                    return true;
                } else {
                    return false;
                }

            } catch (SQLException e) {

                log.error("Exception tag --> " + "Invalid sql statement " + e.getMessage());
                return false;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");
                return false;
            }
        }
    }

    public Boolean updateRecord(String invoiceId, int tax) {

        String encodedInvId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, invoiceId);

        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");
            return false;
        } else {
            try {
                String sql = "UPDATE invoice_id_reset SET `reset_invoice_id`=? WHERE `is_tax_invoice`=? ";

                PreparedStatement stmt = Starter.con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                stmt.setString(1, encodedInvId);
                stmt.setInt(2, tax);

                int val = stmt.executeUpdate();

                if (val == 1) {
                    return true;
                } else {
                    return false;
                }

            } catch (SQLException e) {

                log.error("Exception tag --> " + "Invalid sql statement " + e.getMessage());
                return false;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");
                return false;
            }
        }
    }

    public String loadId(String isTaxInvoice) {
        String encodedStatus = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, isTaxInvoice);

        String id = null;
        if (star.con == null) {

            log.info(" Exception tag --> " + "Databse connection failiure. ");

        } else {
            try {
                String sql = "select reset_invoice_id from invoice_id_reset "
                        + "where is_tax_invoice= ?";

                PreparedStatement stmt = Starter.con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                stmt.setString(1, encodedStatus);

                ResultSet r = stmt.executeQuery();
                while (r.next()) {
                    id = r.getString("reset_invoice_id");
                }
            } catch (SQLException e) {

                log.error("Exception tag --> " + "Invalid sql statement " + e.getMessage());

            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

            }

        }
        return id;
    }

}
