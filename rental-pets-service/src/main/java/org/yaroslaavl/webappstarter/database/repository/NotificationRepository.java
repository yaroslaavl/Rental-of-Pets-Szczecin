package org.yaroslaavl.webappstarter.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.yaroslaavl.webappstarter.database.entity.Notification;
import org.yaroslaavl.webappstarter.database.entity.User;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    @Query("SELECT n FROM Notification n WHERE n.user = :user")
    List<Notification> findAllByUser(@Param("user") User user);

}
