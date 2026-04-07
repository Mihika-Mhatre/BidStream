package com.bidstream.model;

import java.math.BigDecimal;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "auctionItems")
public class AuctionItem {

    @Id
    private String id;

    private String title;
    private String description;
    private BigDecimal startingPrice;
    private BigDecimal currentPrice;
    private String currentWinnerId;
    private Instant endsAt;
    private boolean live;

    public AuctionItem() {
        this.live = true;
    }

    public AuctionItem(String title, String description, BigDecimal startingPrice, Instant endsAt) {
        this.title = title;
        this.description = description;
        this.startingPrice = startingPrice;
        this.currentPrice = startingPrice;
        this.endsAt = endsAt;
        this.live = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(BigDecimal startingPrice) {
        this.startingPrice = startingPrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getCurrentWinnerId() {
        return currentWinnerId;
    }

    public void setCurrentWinnerId(String currentWinnerId) {
        this.currentWinnerId = currentWinnerId;
    }

    public Instant getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(Instant endsAt) {
        this.endsAt = endsAt;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public boolean isClosed() {
        return endsAt != null && Instant.now().isAfter(endsAt);
    }
}
