package com.train.ticket.controller;

import com.train.ticket.entity.Ticket;
import com.train.ticket.entity.UserInfo;
import com.train.ticket.response.Receipt;
import com.train.ticket.service.TtbsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ttbs")
@Validated
public class TtbsController {
    private TtbsService ttbsService;

    @Autowired
    public TtbsController(
            TtbsService ttbsService
    ){
        this.ttbsService = ttbsService;
    }
    @Operation(summary = "Get the list of Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of Users",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserInfo.class)) }),
            @ApiResponse(responseCode = "204", description = "users not found",
                    content = @Content) })
    @GetMapping(path = "/users/get")
    public ResponseEntity<List<UserInfo>> getUserInfo(
    ){
        List<UserInfo> userInfos = ttbsService.getUserDetails();
        if(userInfos.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(userInfos, HttpStatus.OK);
    }


    @Operation(summary = "Book seat in Train")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receipt for the purchase",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Receipt.class)) }),
            @ApiResponse(responseCode = "500", description = "Error occured while processing this request",
                    content = @Content) })
    @PutMapping(path = "/train/seat/book")
    public ResponseEntity<Receipt> getReceiptForPurchasedTicket(
           @Valid @RequestBody UserInfo userInfo) {
        return ResponseEntity.ok(ttbsService.getReceiptForUser(userInfo));
    }
    @Operation(summary = "Initialize Train with section and seat information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket table intialized with values",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HttpStatus.class)) }),
            @ApiResponse(responseCode = "500", description = "Error occured while processing this request",
                    content = @Content) })
    @PostMapping(path = "/train/seat/initialize")
    public ResponseEntity<HttpStatus> setTicketTableWithDefaultData() {
        return new ResponseEntity<>(ttbsService.setTicketTableWithInitData());
    }

    @Operation(summary = "Get booking details by section id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of booking for the section",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class)) }),
            @ApiResponse(responseCode = "500", description = "Error occured while processing this request",
                    content = @Content) })
    @GetMapping(path = "/train/booking/section/get/sectionid/{sectionid}")
    public ResponseEntity<List<Ticket>> getBookingDetailsBySectionId(@PathVariable(name="sectionid") String section) {
        return ResponseEntity.ok(ttbsService.getBookingDetailsBySection(section));
    }

    @Operation(summary = "Remove user from the train")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking for the user cancelled",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HttpStatus.class)) }),
            @ApiResponse(responseCode = "500", description = "Error occured while processing this request",
                    content = @Content) })
    @DeleteMapping(path = "/train/seat/cancel/userid/{userid}")
    public ResponseEntity<HttpStatus> cancelBookingByUserId(@PathVariable(name="userid") int userId){
        return new ResponseEntity<>(ttbsService.cancelBookingByUserId(userId));
    }

    @Operation(summary = "Modify the seat of the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seat changed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class)) }),
            @ApiResponse(responseCode = "500", description = "Error occured while processing this request",
                    content = @Content) })
    @PutMapping(path = "/train/seat/update/userid/{userid}/seatnumber/{seatnumber}")
    public ResponseEntity<Ticket> updateBookingByUserIdAndSeat(@PathVariable(name="userid") int userId,
                                                               @PathVariable(name="seatnumber") String seatNumber)
            throws BadRequestException {
        return ResponseEntity.ok(ttbsService.updateBookingByUserIdAndSeat(userId,seatNumber));
    }
}

