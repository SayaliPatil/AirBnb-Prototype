package com.cmpe275.openhome.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmpe275.openhome.model.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>{

}
