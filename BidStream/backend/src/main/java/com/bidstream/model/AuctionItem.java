package com.bidstream.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document(collection = "auction_items")
public class AuctionItem {

    @Id
    private String id;

    private String title;
    private String description;
    private String imageUrl;

    private BigDecimal startingPrice;
    private BigDecimal currentHighestBid;

    private String currentHighestBidderId;
    private String status;

    private String createdByUserId;

    private Instant createdAt = Instant.now();
    private Instant endsAt;
    private Instant lastBidAt;

    @Version
    private Long version;

    public AuctionItem() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public BigDecimal getStartingPrice() { return startingPrice; }
    public void setStartingPrice(BigDecimal startingPrice) { this.startingPrice = startingPrice; }

    public BigDecimal getCurrentHighestBid() { return currentHighestBid; }
    public void setCurrentHighestBid(BigDecimal currentHighestBid) { this.currentHighestBid = currentHighestBid; }

    public String getCurrentHighestBidderId() { return currentHighestBidderId; }
    public void setCurrentHighestBidderId(String currentHighestBidderId) { this.currentHighestBidderId = currentHighestBidderId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCreatedByUserId() { return createdByUserId; }
    public void setCreatedByUserId(String createdByUserId) { this.createdByUserId = createdByUserId; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getEndsAt() { return endsAt; }
    public void setEndsAt(Instant endsAt) { this.endsAt = endsAt; }

    public Instant getLastBidAt() { return lastBidAt; }
    public void setLastBidAt(Instant lastBidAt) { this.lastBidAt = lastBidAt; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
}