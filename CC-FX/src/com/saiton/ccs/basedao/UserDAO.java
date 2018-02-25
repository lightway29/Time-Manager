package com.saiton.ccs.basedao;

import com.saiton.ccs.commondao.ColumnsAndTables;
import com.saiton.ccs.commondao.CommonSqlUtil;
import com.saiton.ccs.database.Starter;
import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 *
 * @author Saitonya
 */
public class UserDAO {

    private Starter star;
    private PasswordEncrypted passEncrypt = null;

    private Logger log = Logger.getLogger(this.getClass());

    public UserDAO() {
        this.passEncrypt = new PasswordEncrypted("propl");
    }

    public Boolean hasUsername(String username) {

        return CommonSqlUtil.
                genericCheckExistence(star.con,
                        ColumnsAndTables.TABLE_USER,
                        ColumnsAndTables.COLUMN_USERNAME,
                        username, log);
    }

    public String generateEmpID() {
        return CommonSqlUtil.generateId(star.con, "id", "EID",
                ColumnsAndTables.TABLE_USER, "EM", log);
    }

    public String getEid(String username) {
        return CommonSqlUtil.genericSelectString(star.con, "user", "eid",
                "user_name", username, log);
    }

    public Boolean checkUserPass(String user, String password) {
        String pass = null;

        if (star.con == null) {
            log.error(
                    "Databse connection failiure. \n Please contact the administrator.");
            return null;
        } else {

            try {

                PreparedStatement stt = star.con.prepareStatement(
                        "SELECT password FROM user WHERE user_name=? AND is_canceled=0;");
                stt.setString(1, user);
                ResultSet rss = stt.executeQuery();
                if (rss.first()) {
                    pass = rss.getString("password");
                }
                if (pass != null) {
                    String decryptpass = passEncrypt.decrypt(pass);
                    return decryptpass.equals(password);
                } else {
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public String[] selectUser(String username) {

        String user = null;
        String flag = null;
        String title = null;
        String name = null;
        String hasFinger = null;
        String cat = null;
        String eid = null;
        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;
        }

        try {
            PreparedStatement stt = star.con.prepareStatement(
                    "SELECT * FROM user WHERE user_name=? AND is_canceled=0");
            stt.setString(1, CommonSqlUtil.encode(username));

            ResultSet rss = stt.executeQuery();
            if (rss.first()) {
                eid = rss.getString("eid");
                title = rss.getString("title");
                name = rss.getString("name");
                user = rss.getString("user_name");
                flag = rss.getString("flag");
                cat = rss.getString("category_type");
            }

            if (user != null) {
                return new String[]{
                    eid, title, name, user, flag, hasFinger, cat
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return null;

    }

    public List<String[]> selectAllUsers() {

        String user;
        String flag;
        String title;
        String name;
        //String hasFinger;
        String cat;
        String eid;
        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;
        }

        try {

            ArrayList<String[]> data = new ArrayList<>();
            PreparedStatement stt = star.con.prepareStatement(
                    "SELECT * FROM user WHERE is_canceled=0");
            ResultSet rss = stt.executeQuery();
            while (rss.next()) {
                eid = rss.getString("eid");
                title = rss.getString("title");
                name = rss.getString("name");
                user = rss.getString("user_name");
                flag = rss.getString("flag");
//                hasFinger = rss.getString("finger_print");
                cat = rss.getString("category_type");

                data.add(new String[]{
                    eid, title, name, user, flag,  cat
                });
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return null;

    }

    public byte[] selectFingerPrint(String eid) {
        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;
        }

        try {
            byte[] templateBuffer = null;

            PreparedStatement stt = star.con.prepareStatement(
                    "SELECT finger FROM user WHERE eid=?");
            stt.setString(1, CommonSqlUtil.encode(eid));

            ResultSet rss = stt.executeQuery();
            while (rss.next()) {
                templateBuffer = rss.getBytes("finger");
            }

            return templateBuffer;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }

    }

    public List<String[]> selectFingers() {
        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;
        }
        
        try {
            ArrayList<String[]> data = new ArrayList<>();
            byte[] templateBuffer;
            String title;
            String name;
            PreparedStatement stt = star.con.prepareStatement(
                    "SELECT title,name,finger FROM user WHERE is_canceled=0;");

            ResultSet rss = stt.executeQuery();
            while (rss.next()) {
                templateBuffer = rss.getBytes("finger");
                title = rss.getString("title");
                name = rss.getString("name");
                data.add(new String[]{base64(templateBuffer), title, name});
            }

            return data;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }

    }

    private String base64(byte[] finger) {

        String dat = new Base64().encodeToString(finger);

        return dat;
    }

    public Boolean isValid(String username) {
        String q
                = "SELECT IF(EXISTS(SELECT 1 FROM user u WHERE u.EID=? "
                + "AND u.is_canceled=0),1,0) as 'has';";

        try {
            PreparedStatement st = star.con.prepareStatement(q);
            st.setString(1, CommonSqlUtil.encode(username));
            ResultSet rs = st.executeQuery();
            if (rs.first()) {
                int i = rs.getInt("has");
                return (i != 0);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

//    public Boolean updateUser(String eid, String title, String name,
//            String username, String password, String flag) {
//
//        if (star.con == null) {
//            log.error(" Exception tag --> " + "Databse connection failiure. ");
//            return null;
//        }
//        try {
//            PreparedStatement ps = star.con.prepareStatement(
//                    "UPDATE user SET TITLE=?,NAME=?,USERNAME=?,PASSWORD=?,FLAG=? WHERE eid='"
//                    + eid + "'");
//
//            ps.setString(1, title);
//            ps.setString(2, name);
//            ps.setString(3, username);
//            ps.setString(4, password);
//            ps.setString(5, flag);
//
//            int val = ps.executeUpdate();
//            return (val == 1);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    public Boolean deleteUser(String eid) {

        if (star.con == null) {
            log.error(" Exception tag --> " + "Databse connection failiure. ");
            return null;
        }

        try {

            PreparedStatement pre = star.con.prepareStatement(
                    "UPDATE user SET is_canceled=1 WHERE eid=?;");
            pre.setString(1, CommonSqlUtil.encode(eid));
            int val = pre.executeUpdate();
            return (val == 1);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<String> selectUsernames() {

        if (star.con == null) {
            log.error(" Exception tag --> "
                    + "Databse connection failiure. ");
            return null;
        }

        try {
            List<String> usernames = new ArrayList<>();

            ResultSet result = star.con.createStatement().executeQuery(
                    "SELECT username FROM user WHERE is_canceled=0");

            while (result.next()) {
                String username = result.getString("username");
                usernames.add(username);
            }
            return usernames;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean insertUserType(String userType) {

        if (star.con == null) {
            log.error(" Exception tag --> "
                    + "Databse connection failiure. ");
            return null;
        }
        try {

            PreparedStatement st = star.con.prepareStatement(
                    "INSERT INTO user_type (type) VALUES (?)");
            st.setString(1, CommonSqlUtil.encode(userType));
            int success = st.executeUpdate();
            return (success == 1);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String[]> selectPermissions(String eid) {

        final String queryWithUser = "SELECT t.type, "
                + "IF(p.allow_insert IS NOT NULL,p.allow_insert,0) as 'allow_insert', "
                + "IF(p.allow_update IS NOT NULL,p.allow_update,0) as 'allow_update' , "
                + "IF(p.allow_delete IS NOT NULL,p.allow_delete,0) as 'allow_delete' , "
                + "IF(p.allow_view IS NOT NULL,p.allow_view,0) as 'allow_view'  "
                + "FROM user_permissions p RIGHT OUTER JOIN  "
                + "user_permission_type t ON p.type=t.type AND (p.EID=? OR p.EID IS NULL);";

        final String query = "SELECT t.type, "
                + "IF(p.allow_insert IS NOT NULL,p.allow_insert,0) as 'allow_insert', "
                + "IF(p.allow_update IS NOT NULL,p.allow_update,0) as 'allow_update' , "
                + "IF(p.allow_delete IS NOT NULL,p.allow_delete,0) as 'allow_delete' , "
                + "IF(p.allow_view IS NOT NULL,p.allow_view,0) as 'allow_view'   "
                + "FROM user_permissions p RIGHT OUTER JOIN  "
                + "user_permission_type t ON p.type=t.type AND (p.EID IS NULL);";

        if (star.con == null) {
            log.error(" Exception tag --> "
                    + "Databse connection failiure. ");
            return null;
        }

        try {
            List<String[]> permissions = new ArrayList<>();
            PreparedStatement st;
            if (eid == null || eid.isEmpty()) {
                st = star.con.prepareStatement(query);
            } else {
                st = star.con.prepareStatement(queryWithUser);
                st.setString(1, CommonSqlUtil.encode(eid));
            }

            ResultSet result = st.executeQuery();
            while (result.next()) {
                String a1 = result.getString(1);
                String a2 = result.getString(2);
                String a3 = result.getString(3);
                String a4 = result.getString(4);
                String a5 = result.getString(5);
                permissions.add(new String[]{a1, a2, a3, a4, a5});
            }
            return permissions;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String[]> selectNotifications(String eid) {

        final String queryWithUser = "SELECT t.type, "
                + "IF(p.show IS NOT NULL,p.show,0) as 'show' "
                + "FROM user_notifications p RIGHT OUTER JOIN  "
                + "user_notification_type t ON p.type=t.type AND (p.EID=? OR p.EID IS NULL);";

        final String query = "SELECT t.type, "
                + "IF(p.show IS NOT NULL,p.show,0) as 'show' "
                + "FROM user_notifications p RIGHT OUTER JOIN  "
                + "user_notification_type t ON p.type=t.type AND (p.EID IS NULL);";

        if (star.con == null) {
            log.error(" Exception tag --> "
                    + "Databse connection failiure. ");
            return null;
        }

        try {
            List<String[]> notifications = new ArrayList<>();
            PreparedStatement st;

            if (eid == null || eid.isEmpty()) {
                st = star.con.prepareStatement(query);
            } else {
                st = star.con.prepareStatement(queryWithUser);
                st.setString(1, CommonSqlUtil.encode(eid));
            }

            ResultSet result = st.executeQuery();
            while (result.next()) {
                String a1 = result.getString(1);
                String a2 = result.getString(2);
                notifications.add(new String[]{a1, a2});
            }
            return notifications;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean hasUserType(String userType) {
        return CommonSqlUtil.
                genericCheckExistence(star.con, "user_type", "type", userType,
                        log);
    }

    public Boolean deleteUserType(String userType) {
        return CommonSqlUtil.genericDelete(star.con, "user_type", "type",
                userType, log);
    }

    public List<String> getUserTypes() {
        return CommonSqlUtil.genericSelectStrings(star.con, "user_type",
                "type", log);
    }

    public List<String> getUserSubTypes() {
        return CommonSqlUtil.genericSelectStrings(star.con, "user_sub_type",
                "type", log);
    }

    public Boolean insertUser(String eid, String title, String name,
            String username, String password, String type, String subType) {
        String query
                = "INSERT INTO `user` (`EID`, `title`, `name`, `user_name`, `password`, "
                + " `flag`, `category_type`)  "
                + "VALUES (?, ?, ?, ?, ?, ?, ?);";
        String queryNoFinger
                = "INSERT INTO `user` (`EID`, `title`, `name`, `user_name`, `password`, "
                + " `flag`,  `category_type`)  "
                + "VALUES (?, ?, ?, ?, ?, ? ,?);";
        if (star.con == null) {
            log.error(" Exception tag --> "
                    + "Databse connection failiure. ");
            return null;
        }

        try {
            
           
           
                PreparedStatement st = star.con.prepareStatement(queryNoFinger);
                st.setString(1, CommonSqlUtil.encode(eid));
                st.setString(2, CommonSqlUtil.encode(title));
                st.setString(3, CommonSqlUtil.encode(name));
                st.setString(4, CommonSqlUtil.encode(username));
                st.setString(5, passEncrypt.encrypt(password));
                 st.setString(6, CommonSqlUtil.encode(type));
                st.setString(7, CommonSqlUtil.encode(subType));
                return st.executeUpdate() == 1; //one row
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean updateUser(String eid, String title, String name,
            String username, String password, String type, String subType) {
        String query
                = "UPDATE `user` " + "SET " + "`title` = ?, "
                + "`name` = ?, " + "`user_name` = ?, " + "`password` = ?, "
                + "`flag` = ?, "
                + "`category_type` = ? " + "WHERE `EID` = ?; ";

        String queryNoFinger = "UPDATE `user` " + "SET " + "`title` = ?, "
                + "`name` = ?, " + "`user_name` = ?, " + "`password` = ?, "
                + "`flag` = ?, " + "`category_type` = ? " + "WHERE `EID` = ?;";
        if (star.con == null) {
            log.error(" Exception tag --> "
                    + "Databse connection failiure. ");
            return null;
        }

        try {
         
                PreparedStatement st = star.con.prepareStatement(queryNoFinger);
                st.setString(1, CommonSqlUtil.encode(title));
                st.setString(2, CommonSqlUtil.encode(name));
                st.setString(3, CommonSqlUtil.encode(username));
                st.setString(4, passEncrypt.encrypt(password));
                st.setString(5, CommonSqlUtil.encode(type));
             
                st.setString(6, CommonSqlUtil.encode(subType));
                st.setString(7, CommonSqlUtil.encode(eid));
                return st.executeUpdate() == 1; //one row
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean insertUpdateUser(Boolean isUpdate, String eid, String title,
            String name,
            String username, String password, String type, String subType) {
        if (isUpdate) {
            return updateUser(eid, title, name, username, password, type, subType);
        } else {
            return insertUser(eid, title, name, username, password, type, subType);
        }
    }

    public Boolean setUserNotifications(String eid, List<String[]> data) {

        final String queryAdd = "INSERT INTO `user_notifications` "
                + "(`EID`,`type`,`show`) VALUES(?,?,?);";

        if (star.con == null) {
            log.error(" Exception tag --> "
                    + "Databse connection failiure. ");
            return null;
        }

        try {
            CommonSqlUtil.genericDelete(star.con, "user_notifications", "EID",
                    eid, log);
            PreparedStatement add = star.con.prepareStatement(queryAdd);
            for (String[] notify : data) {
                add.setString(1, CommonSqlUtil.encode(eid));
                add.setString(2, CommonSqlUtil.encode(notify[0]));
                add.setString(3, CommonSqlUtil.encode(notify[1]));
                add.executeUpdate();
            }
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public Boolean setUserPermissions(String eid, List<String[]> data) {

        final String queryAdd = "INSERT INTO `user_permissions` "
                + "(`EID`,`type`,`allow_insert`,`allow_update`,`allow_delete`,`allow_view`) "
                + "VALUES(?,?,?,?,?,?);";

        if (star.con == null) {
            log.error(" Exception tag --> "
                    + "Databse connection failiure. ");
            return null;
        }

        try {
            CommonSqlUtil.genericDelete(star.con, "user_permissions", "EID",
                    eid, log);
            PreparedStatement add = star.con.prepareStatement(queryAdd);
            for (String[] notify : data) {
                add.setString(1, CommonSqlUtil.encode(eid));
                add.setString(2, CommonSqlUtil.encode(notify[0]));
                add.setString(3, CommonSqlUtil.encode(notify[1]));
                add.setString(4, CommonSqlUtil.encode(notify[2]));
                add.setString(5, CommonSqlUtil.encode(notify[3]));
                add.setString(6, CommonSqlUtil.encode(notify[4]));
                add.executeUpdate();
            }
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
