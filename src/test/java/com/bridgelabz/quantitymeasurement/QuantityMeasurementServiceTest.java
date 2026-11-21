package com.bridgelabz.quantitymeasurement;

import com.bridgelabz.quantitymeasurement.repository.CacheMeasurementRepository;
import com.bridgelabz.quantitymeasurement.service.QuantityMeasurementService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementServiceTest {
    @Test
    void serviceStoresOperationHistory() {
        QuantityMeasurementService service = new QuantityMeasurementService(new CacheMeasurementRepository());
        service.compare(new Quantity<>(1, LengthUnit.FEET), new Quantity<>(12, LengthUnit.INCHES));
        service.add(new Quantity<>(1, LengthUnit.FEET), new Quantity<>(12, LengthUnit.INCHES), LengthUnit.FEET);
        assertEquals(2, service.history().size());
        assertEquals("ADD", service.history().get(1).operation());
    }
}
