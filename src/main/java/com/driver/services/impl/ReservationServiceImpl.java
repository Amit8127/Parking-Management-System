package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
    //Reserve a spot in the given parkingLot such that the total price is minimum. Note that the price per hour for each spot is different
    //Note that the vehicle can only be parked in a spot having a type equal to or larger than given vehicle
    //If parkingLot is not found, user is not found, or no spot is available, throw "Cannot make reservation" exception.
        Optional<ParkingLot> parkingLotOpt = parkingLotRepository3.findById(parkingLotId);
        Optional<User> userOpt = userRepository3.findById(userId);
        if(!userOpt.isPresent() || !parkingLotOpt.isPresent()) {
            throw new Exception("Cannot make reservation");
        }
        ParkingLot parkingLot = parkingLotOpt.get();
        User user = userOpt.get();

        Spot spot1 = null;
        int minCost = Integer.MAX_VALUE;
        Reservation reservation = new Reservation();
        boolean booked = false;
        for(Spot spot : parkingLot.getSpotList()) {
            int maxWheels = 0;
            if(spot.getSpotType().equals(SpotType.TWO_WHEELER)) {
                maxWheels = 2;
            } else if (spot.getSpotType().equals(SpotType.FOUR_WHEELER)) {
                maxWheels = 4;
            } else {
                maxWheels = numberOfWheels;
            }
            if(spot.getPricePerHour() * timeInHours < minCost && numberOfWheels <= maxWheels){
                booked = true;
                minCost = spot.getPricePerHour() * timeInHours;
                spot1 = spot;
            }
        }
        reservation.setSpot(spot1);
        reservation.setNumberOfHours(timeInHours);
        spot1.getReservationList().add(reservation);
        user.getReservationList().add(reservation);

        // Code is Not complete yet
        return new Reservation();
    }
}
