package com.train.ticket.response;

import com.train.ticket.entity.UserInfo;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class Receipt {

    private String from;
    private String to;
    private UserInfo user;
    private String pricePaid;

}
