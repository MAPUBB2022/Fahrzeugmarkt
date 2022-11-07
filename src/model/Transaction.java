package model;

import java.time.LocalDate;

public class Transaction
{
    private int id;
    private Buyer buyer;
    private Seller seller;
    private Advert ad;
    private int amount;
    private LocalDate date; // YYYYMMDD
    private boolean bid;

    public Transaction(int id, Buyer buyer, Seller seller, Advert ad, int amount, boolean bid) {
        this.id = id;
        this.buyer = buyer;
        this.seller = seller;
        this.ad = ad;
        this.amount = amount;
        this.bid = bid;
        ///////////////
        this.date = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public Advert getAd() {
        return ad;
    }

    public void setAd(Advert ad) {
        this.ad = ad;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isBid() {
        return bid;
    }

    public void setBid(boolean bid) {
        this.bid = bid;
    }
}
