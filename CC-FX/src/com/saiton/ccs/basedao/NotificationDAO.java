package com.saiton.ccs.basedao;

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
public class NotificationDAO {

    private Starter star;

    private Logger log = Logger.getLogger(this.getClass());

    public String generateID() {
        return CommonSqlUtil.generateId(star.con, "id", "nid",
                "notifications", "NT", log);
    }

    public List<String[]> selectUnresolvedNotifications() {

        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;
        }

        try {

            ArrayList<String[]> data = new ArrayList<>();
            PreparedStatement stt = star.con.prepareStatement(
                    "SELECT * FROM notifications WHERE is_resolved=0");
            ResultSet rss = stt.executeQuery();
            while (rss.next()) {
                String nid = rss.getString("nid");
                String type = rss.getString("type");
                String desc = rss.getString("description");
                String ui = rss.getString("ui");
                String addedUser = rss.getString("added_user");
                String dateAdded = rss.getString("date_added");

                data.add(new String[]{
                    nid, type, desc, ui, addedUser, dateAdded, null, "0", null
                });
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return null;

    }

    public List<String[]> searchNotifications(String search, String startDate,
            String endDate) {

        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;
        }

        try {

            ArrayList<String[]> data = new ArrayList<>();
            String searchStr = "%" + search + "%";
            PreparedStatement stt = star.con.prepareStatement(
                    "SELECT * FROM notifications WHERE  "
                    + "(date_added BETWEEN ? AND ?) AND  "
                    + "(`type` LIKE ? OR `description` LIKE ? OR `ui` LIKE ?)");
            stt.setString(1, CommonSqlUtil.encode(startDate));
            stt.setString(2, CommonSqlUtil.encode(endDate));
            stt.setString(3, CommonSqlUtil.encode(searchStr));
            stt.setString(4, CommonSqlUtil.encode(searchStr));
            stt.setString(5, CommonSqlUtil.encode(searchStr));
            ResultSet rss = stt.executeQuery();
            while (rss.next()) {
                String nid = rss.getString("nid");
                String type = rss.getString("type");
                String desc = rss.getString("description");
                String ui = rss.getString("ui");
                String addedUser = rss.getString("added_user");
                String dateAdded = rss.getString("date_added");
                String dateResol = rss.getString("date_resolved");
                String isResol = rss.getString("is_resolved");
                String resolUser = rss.getString("resolved_user");

                data.add(new String[]{
                    nid, type, desc, ui, addedUser, dateAdded, dateResol,
                    isResol, resolUser
                });
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return null;

    }

    public Boolean insert(String type, String desc, String ui,
            String addedUser) {
        String query
                = "INSERT INTO `notifications` " + "(`nid`, "
                + "`type`, " + "`description`, " + "`ui`, "
                + "`added_user`, " + "`date_added` " + ") " + "VALUES "
                + "(?,?,?,?,?,CURDATE());";

        if (star.con == null) {
            log.error(" Exception tag --> "
                    + "Databse connection failiure. ");
            return null;
        }

        try {

            PreparedStatement st = star.con.prepareStatement(query);
            st.setString(1, CommonSqlUtil.encode(generateID()));
            st.setString(2, CommonSqlUtil.encode(type));
            st.setString(3, CommonSqlUtil.encode(desc));
            st.setString(4, CommonSqlUtil.encode(ui));
            st.setString(5, CommonSqlUtil.encode(addedUser));
            return st.executeUpdate() == 1; //one row

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean resolve(String nid, String userResolved) {
        
        String query
                = "UPDATE `notifications` " + "SET "
                + "`date_resolved` = CURDATE(), " + "`is_resolved` = 1, "
                + "`resolved_user` = ? " + "WHERE `nid` = ?;";

        if (star.con == null) {
            log.error(" Exception tag --> "
                    + "Databse connection failiure. ");
            return null;
        }

        try {
            PreparedStatement st = star.con.prepareStatement(query);
            st.setString(1, CommonSqlUtil.encode(userResolved));
            st.setString(2, CommonSqlUtil.encode(nid));
            return st.executeUpdate() == 1; //one row
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
