package com.saiton.ccs.reportdao;

import com.saiton.ccs.commondao.CommonSqlUtil;
import com.saiton.ccs.database.Starter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Saitonya
 */
public class PrinterReportDAO {

    public static Starter star;//db connection
    private final Logger log = Logger.getLogger(this.getClass());

    //--------------------------------------------------------
    //PRINTER
    public List<String> selectAllPrinterTypes() {
        return CommonSqlUtil.genericSelectStrings(star.con, "printer_type",
                "type", log);
    }

    public String generatePrinterId() {
        return CommonSqlUtil.generateId(star.con, "id", "pid", "printer", "PRN",
                log);
    }

    public Boolean insertPrinter(String name, String type, String description) {
        String query
                = "INSERT INTO `printer` " + "(`pid`, " + "`name`, "
                + "`type`, " + "`description`) " + "VALUES " + "(?,?,?,?);";
        if (star.con == null) {
            log.error(" Exception tag --> "
                    + "Databse connection failiure. ");
            return null;
        }

        try {
            PreparedStatement st = star.con.prepareStatement(query);
            st.setString(1, CommonSqlUtil.encode(
                    generatePrinterId()));
            st.setString(2, CommonSqlUtil.encode(name));
            st.setString(3, CommonSqlUtil.encode(type));
            st.setString(4, CommonSqlUtil.encode(description));
            return st.executeUpdate() == 1; //one row

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean updatePrinter(String pid, String name, String type,
            String description) {
        String query
                = "UPDATE `printer` " + "SET " + "`name` = ?, "
                + "`type` = ?, " + "`description` = ? " + "WHERE `pid` = ?;";

        if (star.con == null) {
            log.error(" Exception tag --> "
                    + "Databse connection failiure. ");
            return null;
        }

        try {
            PreparedStatement st = star.con.prepareStatement(query);
            st.setString(1, CommonSqlUtil.encode(name));
            st.setString(2, CommonSqlUtil.encode(type));
            st.setString(3, CommonSqlUtil.encode(description));
            st.setString(4, CommonSqlUtil.encode(pid));
            return st.executeUpdate() == 1; //one row

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean deletePrinter(String pid) {
        return CommonSqlUtil.genericDelete(star.con, "printer", "pid", pid, log);
    }

    public List<String[]> selectAllPrinters() {

        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;
        }

        try {

            ArrayList<String[]> data = new ArrayList<>();
            PreparedStatement stt = star.con.prepareStatement(
                    "SELECT * FROM printer;");
            ResultSet rss = stt.executeQuery();
            while (rss.next()) {
                String pid = rss.getString("pid");
                String pname = rss.getString("name");
                String ptype = rss.getString("type");
                String pdesc = rss.getString("description");

                data.add(new String[]{
                    pid, pname, ptype, pdesc
                });
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return null;
    }

    //----------------------------------------------------
    //REPORT
    public List<String> selectAllReportTypes() {
        return CommonSqlUtil.genericSelectStrings(star.con, "report_type",
                "type", log);
    }

    public String generateReportId() {
        return CommonSqlUtil.generateId(star.con, "id", "rid", "report", "RPT",
                log);
    }

    public Boolean insertReport(String name, String type) {
        String query
                = "INSERT INTO `report` " + "(`rid`, " + "`name`, "
                + "`type`) " + "VALUES " + "(?,?,?);";
        if (star.con == null) {
            log.error(" Exception tag --> "
                    + "Databse connection failiure. ");
            return null;
        }

        try {
            PreparedStatement st = star.con.prepareStatement(query);
            st.setString(1, CommonSqlUtil.encode(
                    generateReportId()));
            st.setString(2, CommonSqlUtil.encode(name));
            st.setString(3, CommonSqlUtil.encode(type));
            return st.executeUpdate() == 1; //one row

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean updateReport(String rid, String name, String type) {
        String query
                = "UPDATE `report` " + "SET " + "`name` = ?, "
                + "`type` = ? " + "WHERE `rid` = ?;";

        if (star.con == null) {
            log.error(" Exception tag --> "
                    + "Databse connection failiure. ");
            return null;
        }

        try {
            PreparedStatement st = star.con.prepareStatement(query);
            st.setString(1, CommonSqlUtil.encode(name));
            st.setString(2, CommonSqlUtil.encode(type));
            st.setString(3, CommonSqlUtil.encode(rid));
            return st.executeUpdate() == 1; //one row

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean deleteReport(String rid) {
        return CommonSqlUtil.genericDelete(star.con, "report", "rid", rid, log);
    }

    public List<String[]> selectAllReports() {

        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;
        }

        try {

            ArrayList<String[]> data = new ArrayList<>();
            PreparedStatement stt = star.con.prepareStatement(
                    "SELECT * FROM report;");
            ResultSet rss = stt.executeQuery();
            while (rss.next()) {
                String rid = rss.getString("rid");
                String rname = rss.getString("name");
                String rtype = rss.getString("type");

                data.add(new String[]{
                    rid, rname, rtype
                });
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return null;
    }

    //------------------------------------------------------
    //PRINTER REPORT
    public List<String[]> selectAllPrinterReports() {
        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;
        }

        try {

            ArrayList<String[]> data = new ArrayList<>();
            PreparedStatement stt = star.con.prepareStatement(
                    "SELECT p.pid,p.name as 'pname',p.type as 'ptype', "
                    + "p.description  as 'pdescription' , "
                    + "r.rid,r.name as 'rname',r.type as 'rtype' "
                    + "from printer p,report r, printer_report pr  "
                    + "WHERE p.pid = pr.pid AND r.rid = pr.rid;");
            ResultSet rss = stt.executeQuery();
            while (rss.next()) {
                String pid = rss.getString("pid");
                String pname = rss.getString("pname");
                String ptype = rss.getString("ptype");
                String pdesc = rss.getString("pdescription");
                String rid = rss.getString("rid");
                String rname = rss.getString("rname");
                String rtype = rss.getString("rtype");
                data.add(new String[]{
                    pid, pname, ptype, pdesc, rid, rname, rtype
                });
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return null;
    }

    public Boolean setAllPrinterReports(List<String[]> data) {

        final String queryAdd = "INSERT INTO `printer_report` " + "(`pid`, "
                + "`rid`) " + "VALUES " + "(?,?);";

        if (star.con == null) {
            log.error(" Exception tag --> "
                    + "Databse connection failiure. ");
            return null;
        }

        try {
            CommonSqlUtil.genericDelete(star.con, "printer_report", log);
            PreparedStatement add = star.con.prepareStatement(queryAdd);
            for (String[] pr : data) {
                add.setString(1, CommonSqlUtil.encode(pr[0]));
                add.setString(2, CommonSqlUtil.encode(pr[1]));
                add.executeUpdate();
            }
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
