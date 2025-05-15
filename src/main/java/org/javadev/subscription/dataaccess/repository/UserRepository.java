package org.javadev.subscription.dataaccess.repository;

import org.javadev.subscription.dataaccess.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}