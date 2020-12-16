package com.example.repository;

import com.example.entity.Agree;
import com.example.entity.Message;
import com.example.entity.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.List;

@Repository
public interface AgreeRepository extends JpaRepository<Agree, Integer> {

    List<Agree> findAgreesByUserAndMessage(User user, Message message);


    @Transactional
    @Modifying
    @CacheEvict(cacheNames="agress",allEntries = true)
    void deleteByUserAndMessage(User user,Message message);


}
