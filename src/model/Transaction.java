package model;

public class Transaction
{
    private int id;
    private Buyer buyer;
    private Seller seller;
    private Advert ad;
    private int amount, date; // YYYYMMDD
    private boolean bid;

    public Transaction(int id, Buyer buyer, Seller seller, Advert ad, int amount, int date, boolean bid) {
        this.id = id;
        this.buyer = buyer;
        this.seller = seller;
        this.ad = ad;
        this.amount = amount;
        this.date = date;
        this.bid = bid;
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

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public boolean isBid() {
        return bid;
    }

    public void setBid(boolean bid) {
        this.bid = bid;
    }
}
