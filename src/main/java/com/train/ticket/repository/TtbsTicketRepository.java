package com.train.ticket.repository;

import com.train.ticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TtbsTicketRepository extends JpaRepository<Ticket, Integer> {
    Ticket findTopOrderByStatus(String status);
    List<Ticket> findBySection(String section);
    Ticket findBySeatNumber(String seatNumber);
    List<Ticket> findByUserId(int userid);
}
