package com.train.ticket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "users")
@Table(name = "users")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId",nullable = false,unique = true)
    private int userId;

    @Column(name = "FirstName", columnDefinition = "nvarchar")
    private String firstName;

    @Column(name = "LastName", columnDefinition = "nvarchar")
    private String lastName;

    @Column(name = "EmailAddress", columnDefinition = "nvarchar", unique = true)
    private String emailAddress;

}
