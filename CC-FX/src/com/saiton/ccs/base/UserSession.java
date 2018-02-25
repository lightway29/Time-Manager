package com.saiton.ccs.base;

import com.saiton.ccs.basedao.UserDAO;
import com.saiton.ccs.finger.FingerPrintDetect;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * User Session : singleton
 *
 * @author Saitonya
 */
public final class UserSession {

    //----------------------------------------------
    private static UserSession instance = null;

    /**
     * Get Instance
     *
     * @return UserSession
     */
    public static UserSession getInstance() {
        synchronized (UserSession.class) {
            if (instance == null) {
                instance = new UserSession();
            }
        }
        return instance;
    }

    //---------------------------------------------
    private UserInfo userInfo;
    private List<UserNotification> notifications;
    private List<UserPermission> permissions;
    private HashSet<Notification> shownNotifications;
    private boolean loggedIn;
    //---------------------------------------------
    private final UserDAO userDao;

    private UserSession() {
        userDao = new UserDAO();
        
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public List<UserNotification> getNotifications() {
        return notifications;
    }

    public List<UserPermission> getPermissions() {
        return permissions;
    }

    public void logout() {
        loggedIn = false;
        userInfo = null;
        notifications = null;
        permissions = null;
        shownNotifications = null;
    }

    public boolean isNotificationShown(Notification n){
        if(!shownNotifications.contains(n)){
            shownNotifications.add(n);
            return false;
        }
        return true;
    }
    
    /**
     * login a given user
     *
     * @param username username
     * @param password password
     * @param fingerPrintDetect finger print detect
     * @return true if successful
     */
    public boolean login(String username, String password
            ) {
        
        

        if (loggedIn) {
            logout();
        }

        try {
            
            if (!userDao.checkUserPass(username, password)) {
                
                return false;
                
            }

            //load user information
            UserInfo userInformation = new UserInfo();
            String[] userdata = userDao.selectUser(username);
            
           
            if (userdata == null) {
               
                return false;
            }
            userInformation.setFromStringArray(userdata);

            //get user eid
            String eid = userInformation.getEid();
            if (eid == null) {
                 
                return false;
            }

 

            List<UserPermission> permissionsData = new ArrayList<>();
            List<UserNotification> notificationsData = new ArrayList<>();
            
            //get permission list
            List<String[]> permissionList = userDao.selectPermissions(eid);
            if (permissionList == null || permissionList.isEmpty()) {
                
                return false;
                
            }
            for (String[] permissionData : permissionList) {
                UserPermission userPermission = new UserPermission();
                userPermission.setFromStringArray(permissionData);
                permissionsData.add(userPermission);
            }
            //get notification list
            List<String[]> notificationList = userDao.selectNotifications(eid);
            if (notificationList == null || notificationList.isEmpty()) {
                
                return false;
            }
            for (String[] notificationData : notificationList) {
                UserNotification userNotification = new UserNotification();
                userNotification.setFromStringArray(notificationData);
                notificationsData.add(userNotification);
            }
            
            //finally set data
            this.permissions = permissionsData;
            this.notifications = notificationsData;
            this.userInfo = userInformation;
            this.shownNotifications = new HashSet<>();
            loggedIn = true;//successful login
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
       return false;//
    }

    /**
     * find a permission
     *
     * @param type type of permission
     * @return null if not found else UserPermission
     */
    public UserPermission findPermission(String type) {
        if (!loggedIn) {
            return null;
        }
        for (UserPermission userPermission : permissions) {
            if (userPermission.getType().equals(type)) {
                return userPermission;
            }
        }
        return null;
    }

    /**
     * find notification
     *
     * @param type type of notification
     * @return null if not found else UserNotification object
     */
    public UserNotification findNotification(String type) {
        if (!loggedIn) {
            return null;
        }
        for (UserNotification userNotification : notifications) {
            if (userNotification.getType().equals(type)) {
                return userNotification;
            }
        }
        return null;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

}
