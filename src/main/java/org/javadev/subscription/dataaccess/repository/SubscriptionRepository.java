package org.javadev.subscription.dataaccess.repository;

import org.javadev.subscription.dataaccess.entity.ServiceName;
import org.javadev.subscription.dataaccess.entity.Subscription;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByUserIdAndServiceName(Long userId, ServiceName serviceName);

    @Query("SELECT s FROM Subscription s WHERE s.user.id = :userId")
    List<Subscription> findByUserId(@Param("userId") Long userId);

    // Получить TOP3 подписки по количеству пользователей, подписанных на каждую
    @Query("SELECT s FROM Subscription s " +
        "JOIN s.user u " +
        "GROUP BY s.id, s.serviceName " +
        "ORDER BY COUNT(u.id) DESC")
    List<Subscription> findTop3ByUserCount(Pageable pageable);

}