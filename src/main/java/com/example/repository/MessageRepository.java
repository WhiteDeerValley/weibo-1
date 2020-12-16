package com.example.repository;

import com.example.entity.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findAllById(Long id);

    @Transactional
    @Modifying
    @CacheEvict(cacheNames="agress",allEntries = true)
    @Query("UPDATE Message m SET m.aggreeNum = :newagreeNum WHERE m.id = :messageId")
    int updateAgreeNum(@Param("newagreeNum") int newagreeNum, @Param("messageId") Long messageId);

}
