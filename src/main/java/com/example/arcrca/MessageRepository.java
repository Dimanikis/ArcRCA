package com.example.arcrca;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<CustomMessage, Long> {

    @Query("SELECT u FROM CustomMessage u where u.user.id = :login")
    List<CustomMessage> findAllByLogin(@Param("login") Long id);
}
