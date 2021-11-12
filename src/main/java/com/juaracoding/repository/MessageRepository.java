package com.juaracoding.repository;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juaracoding.model.MessageModel;

@Repository
public interface MessageRepository extends JpaRepository<MessageModel, Integer> {

}
