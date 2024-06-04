package org.yaroslaavl.webappstarter.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.yaroslaavl.webappstarter.database.entity.Notification;
import org.yaroslaavl.webappstarter.database.entity.User;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    List<Notification> findAllByUser(User user);

}
