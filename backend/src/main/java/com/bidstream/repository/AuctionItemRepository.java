package com.bidstream.repository;

import com.bidstream.model.AuctionItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.util.List;

@Repository
public interface AuctionItemRepository extends MongoRepository<AuctionItem, String> {
    // Fetch all active auctions whose end time hasn't passed
    List<AuctionItem> findByStatusAndEndsAtAfter(String status, Instant now);

    // Fetch all auctions created by a specific user
    List<AuctionItem> findByCreatedByUserId(String userId);
}