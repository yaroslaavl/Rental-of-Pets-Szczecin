package org.yaroslaavl.webappstarter.database.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yaroslaavl.webappstarter.database.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    User findByEmailVerificationToken(String activationToken);
}
