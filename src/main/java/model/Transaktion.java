package model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Transaktion
{
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int id;

    @OneToOne(
            cascade = {CascadeType.MERGE}
    )
    @JoinColumn(
            name = "buyer_id"
    )
    private Buyer buyer;

    @OneToOne(
            cascade = {CascadeType.MERGE}
    )
    @JoinColumn(
            name = "ad_id"
    )
    private Advert ad;
    private int amount;
    private LocalDate date;
    private boolean bid;

    public Transaktion(Buyer buyer, Advert ad, int amount, boolean bid) {

        this.buyer = buyer;
        this.ad = ad;
        this.amount = amount;
        this.bid = bid;
        ///////////////
        this.date = LocalDate.now();
    }

    public Transaktion() {

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
