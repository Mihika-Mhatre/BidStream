package com.bidstream.repository;

import com.bidstream.model.Bid;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BidRepository extends MongoRepository<Bid, String> {
    // All bids for an auction, sorted by amount descending (highest first)
    List<Bid> findByAuctionItemIdOrderByAmountDesc(String auctionItemId);

    // All bids placed by a specific user
    List<Bid> findByBidderId(String bidderId);

    // The current winning bid — highest amount for an auction
    Optional<Bid> findTopByAuctionItemIdOrderByAmountDesc(String auctionItemId);
}