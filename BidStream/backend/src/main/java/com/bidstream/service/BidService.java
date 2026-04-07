package com.bidstream.service;

import com.bidstream.dto.request.BidRequest;
import com.bidstream.exception.InsufficientBalanceException;
import com.bidstream.exception.ResourceNotFoundException;
import com.bidstream.model.AuctionItem;
import com.bidstream.model.Bid;
import com.bidstream.model.User;
import com.bidstream.repository.BidRepository;
import com.bidstream.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class BidService {

    private static final Logger log = LoggerFactory.getLogger(BidService.class);

    private final BidRepository bidRepository;
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;

    public BidService(BidRepository bidRepository,
                      UserRepository userRepository,
                      MongoTemplate mongoTemplate) {
        this.bidRepository = bidRepository;
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Bid placeBid(BidRequest request, String bidderId) {

        User bidder = userRepository.findById(bidderId)
                .orElseThrow(() -> new ResourceNotFoundException("Bidder not found: " + bidderId));

        if (bidder.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException(
                    "Insufficient balance. Available: " + bidder.getBalance()
                    + ", Required: " + request.getAmount());
        }

        Query query = new Query(
                Criteria.where("_id").is(request.getAuctionItemId())
                        .and("status").is("ACTIVE")
                        .and("currentHighestBid").lt(request.getAmount())
                        .and("endsAt").gt(Instant.now())
        );

        Update update = new Update()
                .set("currentHighestBid", request.getAmount())
                .set("currentHighestBidderId", bidderId)
                .set("lastBidAt", Instant.now());

        FindAndModifyOptions options = FindAndModifyOptions.options().returnNew(true);
        AuctionItem updatedAuction = mongoTemplate.findAndModify(
                query, update, options, AuctionItem.class);

        if (updatedAuction == null) {
            throw new IllegalStateException(
                    "Bid rejected: amount is too low or auction has ended.");
        }

        Bid bid = new Bid();
        bid.setAuctionItemId(request.getAuctionItemId());
        bid.setBidderId(bidderId);
        bid.setAmount(request.getAmount());
        bid.setPlacedAt(Instant.now());
        bid.setStatus("ACCEPTED");

        log.info("Bid ACCEPTED — user={}, auction={}, amount={}",
                bidderId, request.getAuctionItemId(), request.getAmount());

        return bidRepository.save(bid);
    }

    public List<Bid> getBidsForAuction(String auctionItemId) {
        return bidRepository.findByAuctionItemIdOrderByAmountDesc(auctionItemId);
    }
}