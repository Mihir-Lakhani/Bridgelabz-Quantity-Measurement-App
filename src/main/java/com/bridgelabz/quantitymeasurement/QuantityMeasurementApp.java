package com.bridgelabz.quantitymeasurement;

import com.bridgelabz.quantitymeasurement.controller.QuantityMeasurementController;
import com.bridgelabz.quantitymeasurement.repository.CacheMeasurementRepository;
import com.bridgelabz.quantitymeasurement.service.QuantityMeasurementService;

public class QuantityMeasurementApp {
    public static void main(String[] args) {
        QuantityMeasurementController controller = new QuantityMeasurementController(
                new QuantityMeasurementService(new CacheMeasurementRepository()));
        Quantity<LengthUnit> foot = new Quantity<>(1, LengthUnit.FEET);
        Quantity<LengthUnit> inches = new Quantity<>(12, LengthUnit.INCHES);
        System.out.println("1 foot equals 12 inches: " + controller.compare(foot, inches));
        System.out.println("Sum: " + controller.add(foot, inches, LengthUnit.FEET).getValue() + " feet");
        System.out.println("Operations saved: " + controller.history().size());
    }
}
