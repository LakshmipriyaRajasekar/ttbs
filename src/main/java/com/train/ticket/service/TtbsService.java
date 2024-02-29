package com.train.ticket.service;

import com.train.ticket.constants.TtbsConstants;
import com.train.ticket.entity.Ticket;
import com.train.ticket.entity.UserInfo;
import com.train.ticket.repository.TtbsTicketRepository;
import com.train.ticket.repository.TtbsUserRepository;
import com.train.ticket.response.Receipt;
import org.apache.catalina.User;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class TtbsService {

    TtbsTicketRepository ttbsTicketRepository;
    TtbsUserRepository ttbsUserRepository;
    @Autowired
    public TtbsService(
            TtbsTicketRepository ttbsTicketRepository,
            TtbsUserRepository ttbsUserRepository
    ) {
        this.ttbsTicketRepository = ttbsTicketRepository;
        this.ttbsUserRepository = ttbsUserRepository;
    }

    public List<UserInfo> getUserDetails() {
        return new LinkedList<>(ttbsUserRepository.findAll());
    }

    public Receipt getReceiptForUser(UserInfo userInfoRequest) {
        Receipt receipt = new Receipt();
        Optional<UserInfo> savedUserInfo = Optional.ofNullable(ttbsUserRepository
                    .findByEmailAddress(userInfoRequest.getEmailAddress()));

        if(savedUserInfo.isEmpty())
                savedUserInfo = Optional.of(ttbsUserRepository.save(userInfoRequest));

        userInfoRequest.setUserId(savedUserInfo.get().getUserId());
        receipt.setUser(userInfoRequest);
        Ticket retrievedTicket = ttbsTicketRepository.findTopOrderByStatus(TtbsConstants.SEAT_AVAILABLE);
        retrievedTicket.setUserId(savedUserInfo.get().getUserId());
        retrievedTicket.setStatus(TtbsConstants.SEAT_BOOKED);
        ttbsTicketRepository.save(retrievedTicket);

        receipt.setTo(TtbsConstants.FROM);
        receipt.setFrom(TtbsConstants.TO);
        receipt.setPricePaid(TtbsConstants.PRICE);
    return receipt;
    }

    public HttpStatus setTicketTableWithInitData() {

        List<Ticket> ticketsList = new LinkedList<>();

        IntStream.range(1,41)
                .forEach(index -> {
                    Ticket ticketDetail = new Ticket();
                    ticketDetail.setTicketId(index);
                    ticketDetail.setSection((index<=20)?TtbsConstants.SECTION_A:TtbsConstants.SECTION_B);
                    ticketDetail.setSeatNumber(ticketDetail.getSection()+ index);
                    ticketDetail.setStatus(TtbsConstants.SEAT_AVAILABLE);
                    ticketDetail.setUserId(0);
                    ticketsList.add(ticketDetail);
                                    });

            ttbsTicketRepository.saveAll(ticketsList);
            return HttpStatus.ACCEPTED;
    }

    public List<Ticket> getBookingDetailsBySection(String section) {
        return ttbsTicketRepository.findBySection(section);
    }

    public HttpStatusCode cancelBookingByUserId(int userid) {
        try{
            List<Ticket> ticketsOwnedByUser = ttbsTicketRepository.findByUserId(userid);
            ticketsOwnedByUser.forEach(e -> {
                    e.setUserId(0);
                    e.setStatus(TtbsConstants.SEAT_AVAILABLE);
            });
            ttbsTicketRepository.saveAll(ticketsOwnedByUser);
        }catch (DataAccessException e){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.NO_CONTENT;
    }

    public Ticket updateBookingByUserIdAndSeat(int userId, String seatNumber) throws BadRequestException {
        Ticket retrievedTicketInfo = ttbsTicketRepository.findBySeatNumber(seatNumber);
        Ticket modifiedSeat;
        if(userId == retrievedTicketInfo.getUserId()){
            modifiedSeat = ttbsTicketRepository.findTopOrderByStatus(TtbsConstants.SEAT_AVAILABLE);
            modifiedSeat.setUserId(userId);
            modifiedSeat.setStatus(TtbsConstants.SEAT_BOOKED);
            ttbsTicketRepository.save(modifiedSeat);
        }else throw new BadRequestException("User not authorized");
        retrievedTicketInfo.setUserId(0);
        retrievedTicketInfo.setStatus(TtbsConstants.SEAT_AVAILABLE);
        ttbsTicketRepository.save(retrievedTicketInfo);
        return modifiedSeat;
    }
}