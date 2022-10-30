package model;

public class Transaction
{
    Buyer buyer;
    Seller seller;
    private int amount, date; // YYYYMMDD
    private boolean bid;

    public Transaction(Buyer buyer, Seller seller, int amount, int date, boolean bid) {
        this.buyer = buyer;
        this.seller = seller;
        this.amount = amount;
        this.date = date;
        this.bid = bid;
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
