package com.bidstream.model;

import java.math.BigDecimal;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bids")
public class Bid {

    @Id
    private String id;
    private String auctionItemId;
    private String bidderId;
    private BigDecimal amount;
    private Instant placedAt;

    public Bid() {
        this.placedAt = Instant.now();
    }

    public Bid(String auctionItemId, String bidderId, BigDecimal amount) {
        this.auctionItemId = auctionItemId;
        this.bidderId = bidderId;
        this.amount = amount;
        this.placedAt = Instant.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuctionItemId() {
        return auctionItemId;
    }

    public void setAuctionItemId(String auctionItemId) {
        this.auctionItemId = auctionItemId;
    }

    public String getBidderId() {
        return bidderId;
    }

    public void setBidderId(String bidderId) {
        this.bidderId = bidderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getPlacedAt() {
        return placedAt;
    }

    public void setPlacedAt(Instant placedAt) {
        this.placedAt = placedAt;
    }
}
