package model;

import java.time.LocalDate;

public class Transaction
{
    private int id;
    private Buyer buyer;
    private Advert ad;
    private int amount;
    private LocalDate date; // YYYYMMDD
    private boolean bid;

    public Transaction(Buyer buyer, Advert ad, int amount, boolean bid) {

        this.buyer = buyer;
        this.ad = ad;
        this.amount = amount;
        this.bid = bid;
        ///////////////
        this.date = LocalDate.now();
        this.id = -1;
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
