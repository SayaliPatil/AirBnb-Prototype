package com.cmpe275.openhome.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmpe275.openhome.model.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>{
	Card findByEmail(String email);
	void save(Optional<Card> card);
}
