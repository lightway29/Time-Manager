/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.printerservicedao;

import com.saiton.ccs.database.Starter;
import com.saiton.ccs.printerservice.ReportType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

/**
 *
 * @author Polypackaging-A1
 */
public class PrinterServiceDAO {

    public static Starter star;//db connection
    Codec ORACLE_CODEC = new OracleCodec();
    private Logger log = Logger.getLogger(this.getClass());

    public ArrayList<String> getReportInfo(ReportType reportType) {

        String reportName = reportType.toString();

        ArrayList<String> list = new ArrayList<>();

        String reportId = null;
        String printerId = null;

        if (star.con == null) {
            log.error("Databse connection failiure.");

        } else {


            try {

                String query
                        = "select * from report r join printer_report pr on r.rid = pr.rid "
                        + "where r.name = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1,reportName);

                ResultSet resultSet = pstmt.executeQuery();
                while (resultSet.next()) {
                    
                    reportId = resultSet.getString("r.rid");
                    printerId = resultSet.getString("pr.pid");

                    list.add(reportId);
                    list.add(printerId);

                }
            } catch (ArrayIndexOutOfBoundsException | SQLException |
                    NullPointerException e) {
                if (e instanceof ArrayIndexOutOfBoundsException) {
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

        return list;

    }

    public ArrayList<String> getPrintJobInfo(String printJobId) {

        String encodedPrintJobId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                printJobId);

        ArrayList<String> list = new ArrayList<>();

        String reportId = null;
        String printerId = null;

        if (star.con == null) {
            log.error("Databse connection failiure.");

        } else {

            try {

                String query = "select * from report_reg where id = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedPrintJobId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    reportId = r.getString("report_id");
                    printerId = r.getString("printer_id");

                    list.add(reportId);
                    list.add(printerId);

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

        return list;

    }

    public String getPrintName(String printerId) {

        String encodedPrintId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                printerId);

        ArrayList<String> list = new ArrayList<>();

        String printerName = null;

        if (star.con == null) {
            log.error("Databse connection failiure.");

        } else {

            try {

                String query = "select * from printer where pid = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedPrintId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    printerName = r.getString("name");

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

        return printerName;

    }

    public String getReportURL(String reportId) {

        String encodedReportId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                reportId);

        ArrayList<String> list = new ArrayList<>();

        String url = null;

        if (star.con == null) {
            log.error("Databse connection failiure.");

        } else {

            try {

                String query = "select * from report where rid = ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedReportId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    url = r.getString("url");

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

        return url;

    }

    public String insertPrintData(String reportId, String printerId,
            String userId) {

        String encodedReportId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                reportId);
        String encodedPrinterId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                printerId);
        String encodedUserId = ESAPI.encoder().
                encodeForSQL(ORACLE_CODEC, userId);

        String id = null;

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return null;
        } else {
            try {

                PreparedStatement ps = star.con.prepareStatement(
                        "INSERT INTO report_reg(report_id,printer_id,user_id) VALUES(?,?,?)");

                ps.setString(1, encodedReportId);
                ps.setString(2, encodedPrinterId);
                ps.setString(3, encodedUserId);

                int val = ps.executeUpdate();
                if (val == 1) {

                    String query
                            = "select MAX(id) from report_reg";

                    PreparedStatement pstmt = star.con.prepareStatement(query);

                    ResultSet r = pstmt.executeQuery();

                    while (r.next()) {

                        id = r.getString(1);

                    }

                    return id;
                } else {
                    return null;
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
    }

}
