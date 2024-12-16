package models.account;

import models.user.Client;

import java.util.Scanner;
import java.util.InputMismatchException;

public class CreditCard {
    public final String cardNumber;
    private double limit=20000;
    private boolean isActive = true;
    //private Account account
    Scanner scan = new Scanner(System.in);

    public CreditCard(String cardNumber, boolean isActive) {
        this.cardNumber = cardNumber;
        this.isActive = isActive;
    }

    public double getLimit() {
        return limit;
    }

    public void makePayment() {

        //Validate Card Number
        if (!cardNumber.equals(this.cardNumber)) {
            System.out.println("Invalid card number");
            return;
        }

        System.out.println("Enter Amount:");


        try {
            double amount = scan.nextDouble();

            //Check if Card is active and amount is less than limit
            if (isActive && amount <= limit) {
                System.out.println("Payment Successful :)");
                limit=getLimit()-amount;
            } else if (!isActive) {
                System.out.println("Card disabled :(");
            } else if (amount > limit) {
                System.out.println("Amount exceeded limit :(");
            }
        }catch (InputMismatchException e) {
            System.out.println("Invalid input. Please try again.");
            scan.nextLine();
        }
    }

    //Resetting limit
    public void resetLimit() {
        limit=20000;
    }



    public boolean disableCard(String cardNumber){

        //Validate Card Number

        if(!this.cardNumber.equals(cardNumber)) {
            System.out.println("Invalid card number");
            return false;
        }
        System.out.println("Card disabled");
        isActive= false;
        return true;
    }

    public boolean activateCard(String cardNumber){

        //Validate Card Number
        if(!this.cardNumber.equals(cardNumber)) {
            System.out.println("Invalid card number");
            return false;
        }
        System.out.println("Card activated");
        isActive= true;
        return true;
    }
}