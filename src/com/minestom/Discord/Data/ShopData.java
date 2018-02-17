package com.minestom.Discord.Data;


import net.dv8tion.jda.core.entities.Message;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ShopData {

    private boolean shopping;
    private boolean buyingExpBooster;
    private boolean buyingCoinsBooster;
    private boolean confirming;
    private boolean choosingPercent;
    private boolean choosingTime;
    private double coinsBoosterPercent;
    private double expBoosterPercent;
    private double coinsBoosterTime;
    private double expBoosterTime;
    private double totalPrice;
    private double userCoins;
    private Set<String> cart;
    private Message message;

    public ShopData() {
        this.shopping = true;
        this.buyingExpBooster = false;
        this.buyingCoinsBooster = false;
        this.confirming = false;
        this.choosingPercent = false;
        this.choosingTime = false;
        this.cart = new HashSet<>();
        this.coinsBoosterPercent = 0.0;
        this.expBoosterPercent = 0.0;
        this.coinsBoosterTime = 0.0;
        this.expBoosterTime = 0.0;
        this.totalPrice = 0.0;
        this.userCoins = 0.0;
    }

    public boolean isShopping() {
        return shopping;
    }

    public void setShopping(boolean shopping) {
        this.shopping = shopping;
    }

    public boolean isBuyingExpBooster() {
        return buyingExpBooster;
    }

    public void setBuyingExpBooster(boolean buyingExpBooster) {
        this.buyingExpBooster = buyingExpBooster;
    }

    public boolean isBuyingCoinsBooster() {
        return buyingCoinsBooster;
    }

    public void setBuyingCoinsBooster(boolean buyingCoinsBooster) {
        this.buyingCoinsBooster = buyingCoinsBooster;
    }

    public double getCoinsBoosterPercent() {
        return coinsBoosterPercent;
    }

    public void setCoinsBoosterPercent(double coinsBoosterPercent) {
        this.coinsBoosterPercent = coinsBoosterPercent;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public boolean isConfirming() {
        return confirming;
    }

    public void setConfirming(boolean confirming) {
        this.confirming = confirming;
    }

    public double getExpBoosterPercent() {
        return expBoosterPercent;
    }

    public void setExpBoosterPercent(double expBoosterPercent) {
        this.expBoosterPercent = expBoosterPercent;
    }

    public double getCoinsBoosterTime() {
        return coinsBoosterTime;
    }

    public void setCoinsBoosterTime(double coinsBoosterTime) {
        this.coinsBoosterTime = coinsBoosterTime;
    }

    public double getExpBoosterTime() {
        return expBoosterTime;
    }

    public void setExpBoosterTime(double expBoosterTime) {
        this.expBoosterTime = expBoosterTime;
    }

    public boolean isChoosingPercent() {
        return choosingPercent;
    }

    public void setChoosingPercent(boolean choosingPercent) {
        this.choosingPercent = choosingPercent;
    }

    public boolean isChoosingTime() {
        return choosingTime;
    }

    public void setChoosingTime(boolean choosingTime) {
        this.choosingTime = choosingTime;
    }

    public String getCart() {
        return cart.stream().collect(Collectors.joining(", ")).trim();
    }

    public void addToCart(String item) {
        this.cart.add(item);
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getUserCoins() {
        return userCoins;
    }

    public void setUserCoins(double userCoins) {
        this.userCoins = userCoins;
    }
}
