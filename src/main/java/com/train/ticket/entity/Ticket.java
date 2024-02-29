package com.train.ticket.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "tickets")
@Table(name = "tickets")
public class Ticket {
    @Id
    @Column(name = "TicketId", nullable = false,unique = true)
    private int ticketId;
    @Column(name = "UserId")
    private int userId;
    @Column(name = "SectionId", columnDefinition = "nvarchar")
    private String section;
    @Column(name = "SeatNumber", columnDefinition = "nvarchar")
    private String seatNumber;
    @Column(name = "Status", columnDefinition = "nvarchar")
    private String status;
}
