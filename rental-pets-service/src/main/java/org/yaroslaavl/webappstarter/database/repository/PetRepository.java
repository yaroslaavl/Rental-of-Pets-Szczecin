package org.yaroslaavl.webappstarter.database.repository;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.yaroslaavl.webappstarter.database.entity.Pet;

import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet,Long>, QuerydslPredicateExecutor<Pet> {

    @Query("SELECT p FROM Pet p " +
            "JOIN FETCH p.species WHERE p.id = :id")
    Optional<Pet> findById(@Param("id") Long id);

    Page<Pet> findAll(Predicate predicate, Pageable pageable);


}