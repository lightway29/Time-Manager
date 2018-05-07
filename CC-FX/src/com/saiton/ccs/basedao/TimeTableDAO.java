/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saiton.ccs.basedao;

import com.saiton.ccs.database.Starter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

public class TimeTableDAO {

    public static Starter star;//db connection
    Codec ORACLE_CODEC = new OracleCodec();
    private final Logger log = Logger.getLogger(this.getClass());


    public Boolean insertClassGroup(
            String classId,
            String subGroupId,
            String gender) {

        if (star.con == null) {

            log.error("Exception tag --> " + "Database connection failiure. ");
            return null;

        } else {
            try {

                PreparedStatement ps = star.con.prepareStatement("INSERT INTO "
                        + "class_group (class_id,sub_group_id,gender) VALUES(?,?,?)");
                ps.setString(1, classId);
                ps.setString(2, subGroupId);
                ps.setString(3, gender);

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

                    log.error("Exception tag --> " + "Invalid sql statement "
                            + e);

                }
                return false;
            } catch (Exception e) {

                log.error("Exception tag --> " + "Error");

                return false;
            }
        }
    }



    public ArrayList getSubjectList() {

        String subject = null;
        ArrayList subjectList = new ArrayList();

        if (star.con == null) {
            log.error("Database connection failiure.");
        } else {
            try {
                Statement stt = star.con.createStatement();
                ResultSet r = stt.
                        executeQuery("SELECT * FROM subject");
                while (r.next()) {
                    subject = r.getString("subject");

                    subjectList.add(subject);
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
        return subjectList;
    }

    public ArrayList getClassGroupList() {

        String classGroup = null;
        ArrayList classGroupList = new ArrayList();

        if (star.con == null) {
            log.error("Database connection failiure.");
        } else {
            try {
                Statement stt = star.con.createStatement();
                ResultSet r = stt.
                        executeQuery("SELECT * FROM class_group");
                while (r.next()) {
                    classGroup = r.getString("id");

                    classGroupList.add(classGroup);
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
        return classGroupList;
    }

    
     public String getClassId(String classTitle) {

        String encodedCusId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, classTitle);
        String classId = null;

        if (star.con == null) {

            log.error("Exception tag --> " + "Database connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * FROM class WHERE title = ?";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedCusId);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    classId = r.getString("id");

                }

            } catch (SQLException | NullPointerException e) {

                if (e instanceof SQLException) {

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

        return classId;

    }
     
     public boolean isSlotAvailable(String timetableId,String day,String subject,String slot) {
        String encodedTimetableId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                timetableId);


        boolean available = true;

        if (star.con == null) {
            log.error("Databse connection failiure.");
            return false;
        } else {

            try {
                String query
                        = "SELECT * FROM class_time_table_reg where class_time_table_id = ? AND "+day+" = ? AND slot = ?";
                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedTimetableId);
                pstmt.setString(2, subject);
                pstmt.setString(3, slot);

                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    System.out.println("Reached the status changer");
                    available = false;
                }

            } catch (NullPointerException | SQLException e) {

                if (e instanceof NullPointerException) {
                    log.error("Exception tag --> " + "Empty entry passed");
                } else if (e instanceof SQLException) {
                    log.error("Exception tag --> " + "Invalid sql statement "
                            + e.getMessage());
                }
                return false;
            } catch (Exception e) {
                log.error("Exception tag --> " + "Error");
                return false;
            }
        }
        return available;
    }
     
        public ArrayList loadSubject() {

        String subject = null;
        ArrayList subjectList = new ArrayList();

        if (star.con == null) {
            log.error("Database connection failiure.");
        } else {
            try {
                Statement stt = star.con.createStatement();
                ResultSet r = stt.
                        executeQuery("SELECT * FROM subject");
                while (r.next()) {
                    subject = r.getString("subject");

                    subjectList.add(subject);
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
        return subjectList;
    }

          public ArrayList loadTeacher() {

        String subject = null;
        ArrayList subjectList = new ArrayList();

        if (star.con == null) {
            log.error("Database connection failiure.");
        } else {
            try {
                Statement stt = star.con.createStatement();
                ResultSet r = stt.
                        executeQuery("SELECT * FROM Teacher");
                while (r.next()) {
                    subject = r.getString("teacher_name");

                    subjectList.add(subject);
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
        return subjectList;
    }

      public ArrayList<ArrayList<String>> searchTimetableDetails(
            String searchTerm) {

        String encodedSearchTerm = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                searchTerm);

        String id = null;
        String timetableTitle = null;

        ArrayList<ArrayList<String>> mainList
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Database connection failiure. ");
            return null;

        } else {
            try {

                String query = "SELECT * "
                        + "From class_time_table "
                        + "Where time_table_title LIKE ? or id LIKE ? ";

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedSearchTerm + "%");
                pstmt.setString(2, encodedSearchTerm + "%");
                ResultSet r = pstmt.executeQuery();

                while (r.next()) {

                    ArrayList<String> list = new ArrayList<String>();

                    id = r.getString("id");
                    timetableTitle = r.getString("time_table_title");

                    list.add(id);
                    list.add(timetableTitle);

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
      
      
      public ArrayList<ArrayList<String>> getTableInfo(String fromDate) {


        String encodedFromDate = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                fromDate);

        String itemId = null;
        String ReturnNoteId = null;
        String BatchNo = null;
        String Date = null;

        ArrayList<ArrayList<String>> mainlist
                = new ArrayList<ArrayList<String>>();

        if (star.con == null) {

            log.info(" Exception tag --> " + "Database connection failiure. ");
            return null;

        } else {
            try {

                String query = null;
                
                    query = "select * from class_time_table_reg "
                            + "where class_time_table_id = ? ";
                

                PreparedStatement pstmt = star.con.prepareStatement(query);
                pstmt.setString(1, encodedFromDate);


                ResultSet r = pstmt.executeQuery();

                while (r.next()) {
                    
                    ArrayList<String> list = new ArrayList<String>();

                    itemId = r.getString("mon");
                    ReturnNoteId = r.getString("tue");
                    BatchNo = r.getString("wed");
                    Date = r.getString("thu");



                    list.add(itemId);
                    list.add(ReturnNoteId);
                    list.add(BatchNo);
                    list.add(Date);
 

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


}
