package org.yaroslaavl.webappstarter.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.yaroslaavl.webappstarter.database.entity.Species;

import java.util.List;

@Repository
public interface SpeciesRepository extends JpaRepository<Species, Long> {

     @Query("SELECT s FROM Species s")
     List<Species> findAll();
}
