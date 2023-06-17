package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
    //Attempt a payment of amountSent for reservationId using the given mode ("cASh", "card", or "upi")
    //If the amountSent is less than bill, throw "Insufficient Amount" exception, otherwise update payment attributes
    //If the mode contains a string other than "cash", "card", or "upi" (any character in uppercase or lowercase), throw "Payment mode not detected" exception.
    //Note that the reservationId always exists
        Reservation reservation = reservationRepository2.findById(reservationId).get();
        int time = reservation.getNumberOfHours();
        int charge = reservation.getSpot().getPricePerHour();
        int bill = time * charge;
        if(bill > amountSent) {
            throw new Exception("Insufficient Amount");
        }
        Payment payment = new Payment();
        if(mode.equalsIgnoreCase("cash")) {
            payment.setPaymentMode(PaymentMode.CASH);
        } else if (mode.equalsIgnoreCase("upi")) {
            payment.setPaymentMode(PaymentMode.UPI);
        } else if(mode.equalsIgnoreCase("card")) {
            payment.setPaymentMode(PaymentMode.CARD);
        } else {
            throw new Exception("Payment mode not detected");
        }

        payment.setPaymentCompleted(Boolean.TRUE);


    }
}
