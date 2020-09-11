package com.udacity.pricing.service;

import com.udacity.pricing.domain.price.Price;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Implements the pricing service to get prices for each vehicle.
 */
public class PricingService {

    /**
     * Holds {ID: Price} pairings (current implementation allows for 20 vehicles)
     */
    private static final Map<Long, Price> PRICES = LongStream
            .range(1, 20)
            .mapToObj(i -> new Price("USD", randomPrice(), i))
            .collect(Collectors.toMap(Price::getVehicleId, p -> p));

    /**
     * If a valid vehicle ID, gets the price of the vehicle from the stored array.
     * @param vehicleId ID number of the vehicle the price is requested for.
     * @return price of the requested vehicle
     * @throws PriceException vehicleID was not found
     */
    public static Price getPrice(Long vehicleId) throws PriceException {

        if (!PRICES.containsKey(vehicleId)) {
            throw new PriceException("Cannot find price for Vehicle " + vehicleId);
        }

        return PRICES.get(vehicleId);
    }

    /**
     * When a ID is Deleted this method assignes new Price to The ID.
     * @param vehicleId ID number of the vehicle the price needs to be refreshed.
     * @return price of the vehicle After Update
     * @throws PriceException vehicleID was not found
     */
    public static Price refreshPrice(Long vehicleId) throws PriceException {

        if (!PRICES.containsKey(vehicleId)) {
            throw new PriceException("No Price Assigned For Vehicle : " + vehicleId);
        }

        Price _temp = PRICES.get(vehicleId);
        BigDecimal currentPrice = _temp.getPrice();
        BigDecimal randomPrice = randomPrice();
        while(randomPrice.equals(currentPrice)) {
            randomPrice = randomPrice();
        }
        _temp.setPrice(randomPrice);
        PRICES.put(vehicleId, _temp);
        return PRICES.get(vehicleId);
    }

    /**
     * Gets a random price to fill in for a given vehicle ID.
     * @return random price for a vehicle
     */
    private static BigDecimal randomPrice() {
        return new BigDecimal(ThreadLocalRandom.current().nextDouble(1, 5))
                .multiply(new BigDecimal(5000d)).setScale(2, RoundingMode.HALF_UP);
    }

}
