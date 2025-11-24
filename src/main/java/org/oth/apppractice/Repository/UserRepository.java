package org.oth.apppractice.Repository;

import jakarta.transaction.Transactional;
import org.oth.apppractice.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    // @Query("SELECT u FROM User u WHERE u.email = ?1") Another way of doing things
    Optional<User> findUserByEmail(String email);
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.enabled = true WHERE u.email = ?1 ")
    int enableUser(String email);
}
