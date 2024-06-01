package org.yaroslaavl.webappstarter.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.yaroslaavl.webappstarter.database.entity.Booking;
import org.yaroslaavl.webappstarter.database.entity.BookingStatus;
import org.yaroslaavl.webappstarter.database.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b " +
            "JOIN FETCH b.user " +
            "JOIN FETCH b.pet WHERE b.status = :status")
    List<Booking> findByStatus(BookingStatus status);

    @Query("SELECT b FROM Booking b " +
            "JOIN FETCH b.user " +
            "JOIN FETCH b.pet WHERE b.user = :user")
    List<Booking> findBookingByUser(@Param("user") User user);

    @Query("SELECT b FROM Booking b " +
            "JOIN FETCH b.user u " +
            "JOIN FETCH b.pet p " +
            "JOIN FETCH p.species s WHERE b.id = :id")
    Optional<Booking> findById(@Param("id") Long id);

    @Query("SELECT b FROM Booking b JOIN FETCH b.user u" +
            " JOIN FETCH b.pet p")
    List<Booking> findAll();

}
