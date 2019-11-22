package com.cmpe275.openhome.repository;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import com.cmpe275.openhome.model.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long>{
	
}