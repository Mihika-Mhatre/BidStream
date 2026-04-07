package com.bidstream.controller;

import com.bidstream.dto.request.BidRequest;
import com.bidstream.model.AuctionItem;
import com.bidstream.model.Bid;
import com.bidstream.service.AuctionItemService;
import com.bidstream.service.BidService;
import com.bidstream.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {

    private final AuctionItemService auctionItemService;
    private final BidService bidService;
    private final UserService userService;

    public AuctionController(AuctionItemService auctionItemService,
                             BidService bidService,
                             UserService userService) {
        this.auctionItemService = auctionItemService;
        this.bidService = bidService;
        this.userService = userService;
    }

    // PUBLIC — no token required (configured in SecurityConfig)
    @GetMapping("/active")
    public ResponseEntity<List<AuctionItem>> getActiveAuctions() {
        return ResponseEntity.ok(auctionItemService.getActiveAuctions());
    }

    @GetMapping("/{auctionId}")
    public ResponseEntity<AuctionItem> getAuction(@PathVariable String auctionId) {
        return ResponseEntity.ok(auctionItemService.getAuctionById(auctionId));
    }

    // PROTECTED — requires valid JWT
    @PostMapping
    public ResponseEntity<AuctionItem> createAuction(
            @RequestBody AuctionItem item,
            @AuthenticationPrincipal UserDetails userDetails) {
        // Resolve email → userId
        String userId = userService.getUserByEmail(userDetails.getUsername()).getId();
        return ResponseEntity.ok(auctionItemService.createAuction(item, userId));
    }

    @PostMapping("/{auctionId}/bids")
    public ResponseEntity<Bid> placeBid(
            @PathVariable String auctionId,
            @Valid @RequestBody BidRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        request.setAuctionItemId(auctionId); // Bind path param to DTO
        String bidderId = userService.getUserByEmail(userDetails.getUsername()).getId();
        return ResponseEntity.ok(bidService.placeBid(request, bidderId));
    }

    @GetMapping("/{auctionId}/bids")
    public ResponseEntity<List<Bid>> getBids(@PathVariable String auctionId) {
        return ResponseEntity.ok(bidService.getBidsForAuction(auctionId));
    }
}