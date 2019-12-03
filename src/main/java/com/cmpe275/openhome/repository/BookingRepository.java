package com.cmpe275.openhome.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmpe275.openhome.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>{

}
