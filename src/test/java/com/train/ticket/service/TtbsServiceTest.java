package com.train.ticket.service;

import com.train.ticket.repository.TtbsTicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TtbsServiceTest {

    @Mock
    private TtbsTicketRepository ttbsTicketRepository;

    @InjectMocks
    private TtbsService ttbsService;

    @Test
    void getAllPerson()
    {
    }
}

