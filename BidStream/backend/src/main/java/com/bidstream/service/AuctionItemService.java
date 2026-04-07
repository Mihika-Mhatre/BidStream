package com.bidstream.service;

import com.bidstream.exception.ResourceNotFoundException;
import com.bidstream.model.AuctionItem;
import com.bidstream.repository.AuctionItemRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class AuctionItemService {

    private final AuctionItemRepository auctionItemRepository;

    public AuctionItemService(AuctionItemRepository auctionItemRepository) {
        this.auctionItemRepository = auctionItemRepository;
    }

    public AuctionItem createAuction(AuctionItem item, String creatorUserId) {
        item.setCreatedByUserId(creatorUserId);
        item.setStatus("ACTIVE");
        item.setCurrentHighestBid(item.getStartingPrice());
        item.setVersion(0L); // Initialize optimistic lock version
        return auctionItemRepository.save(item);
    }

    public List<AuctionItem> getActiveAuctions() {
        return auctionItemRepository.findByStatusAndEndsAtAfter("ACTIVE", Instant.now());
    }

    public AuctionItem getAuctionById(String auctionId) {
        return auctionItemRepository.findById(auctionId)
                .orElseThrow(() -> new ResourceNotFoundException("Auction not found: " + auctionId));
    }
}