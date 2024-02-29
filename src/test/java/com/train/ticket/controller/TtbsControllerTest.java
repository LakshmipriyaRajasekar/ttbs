package com.train.ticket.controller;

import com.train.ticket.entity.UserInfo;
import com.train.ticket.service.TtbsService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)

public class TtbsControllerTest {

    @InjectMocks
    private TtbsController ttbsController;

    @Mock
    private TtbsService ttbsService;

   @Before("")
   public  void setUp() {

   }

    @Test
    public void getAllUserDetailsTestOK() {
        List<UserInfo> userDetails = new LinkedList<>();

        UserInfo userInfo1 =  UserInfo
                .builder()
                .userId(1)
                .emailAddress("lp@gmail.com")
                .firstName("priya")
                .lastName("r")
                .build();
        UserInfo userInfo2 =  UserInfo
                .builder()
                .userId(2)
                .emailAddress("ap@gmail.com")
                .firstName("abhi")
                .lastName("mithu")
                .build();
        userDetails.add(userInfo1);
        userDetails.add(userInfo2);

        Mockito.when(ttbsService.getUserDetails())
                .thenReturn(userDetails);


        ResponseEntity<List<UserInfo>> result = ttbsController.getUserInfo();
        assertThat(result.getStatusCode().is2xxSuccessful());
        assertThat(result.getBody()).isEqualTo(userDetails);

    }
}
