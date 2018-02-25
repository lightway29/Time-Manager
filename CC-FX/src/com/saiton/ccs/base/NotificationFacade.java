package com.saiton.ccs.base;

import com.saiton.ccs.basedao.NotificationDAO;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author Saitonya
 */
public class NotificationFacade {

    private static Timeline time = null;
    private final NotificationDAO notificationDAO = new NotificationDAO();
    private static NotificationFacade instance = null;

    private NotificationFacade() {
    }

    public static NotificationFacade getInstance() {
        synchronized (NotificationFacade.class) {
            if (instance == null) {
                instance = new NotificationFacade();
            }
        }
        return instance;
    }

    public List<Notification> selectUnresolvedNotifications() {

        List<String[]> data = notificationDAO.selectUnresolvedNotifications();

        return filterForUser(convert(data));
    }

    public List<Notification> searchNotifications(String search,
            String startDate,
            String endDate) {

        List<String[]> data = notificationDAO.searchNotifications(search,
                startDate, endDate);

        return filterForUser(convert(data));

    }

    private List<Notification> convert(List<String[]> data) {
        ArrayList<Notification> notf = new ArrayList<>();

        if (data == null) {
            return null;
        }
        for (String[] atom : data) {
            Notification n = new Notification();
            n.setFromStringArray(atom);
            notf.add(n);
        }
        return notf;
    }

    public Boolean insert(String type, String desc, String ui,
            String addedUser) {
        return notificationDAO.insert(type, desc, ui, addedUser);
    }

    public Boolean resolve(String nid, String userResolved) {
        return notificationDAO.resolve(nid, userResolved);
    }

    private List<Notification> filterForUser(List<Notification> original) {
        if (original == null) {
            return null;
        }
        ArrayList<Notification> n = new ArrayList<>();

        for (Notification atom : original) {
            UserNotification un = UserSession.getInstance().findNotification(
                    atom.getType());

            if (un != null && un.getShow()) {
                n.add(atom);
            }
        }

        return n;
    }

    public static final void showNotifications() {
        synchronized (NotificationFacade.class) {
            if (UserSession.getInstance().isLoggedIn()) {
                List<Notification> data = getInstance().
                        selectUnresolvedNotifications();
                int key = 1;
                Timeline tempo = new Timeline();
                tempo.setCycleCount(1);
                tempo.setAutoReverse(false);
                for (Notification atom : data) {
                    if (!UserSession.getInstance().isNotificationShown(atom)) {
                        Notifications notificationBuilder = Notifications.
                                create()
                                .title(atom.getType())
                                .text(atom.getDescription())
                                .hideAfter(Duration.seconds(3))
                                .position(Pos.TOP_RIGHT)
                                .hideCloseButton()
                                ;
                        
                        tempo.getKeyFrames().add(
                                new KeyFrame(Duration.seconds(key++),
                                        event -> {
                                    notificationBuilder.show();
                                }
                                ));
                    }
                }
                tempo.play();
            }
        }
    }

    public static void start() {
        synchronized (NotificationFacade.class) {
            if (time != null) {
                return;
            }
            time = new Timeline();
            time.setCycleCount(Timeline.INDEFINITE);
            time.setAutoReverse(false);
            time.getKeyFrames().add(new KeyFrame(Duration.seconds(60), event
                    -> {
                showNotifications();
            }));
            time.play();
        }
    }
}
