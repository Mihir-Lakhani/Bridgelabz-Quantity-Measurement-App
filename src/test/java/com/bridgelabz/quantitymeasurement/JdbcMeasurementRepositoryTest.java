package com.bridgelabz.quantitymeasurement;

import com.bridgelabz.quantitymeasurement.repository.JdbcMeasurementRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JdbcMeasurementRepositoryTest {
    @Test
    void storesAndReadsMeasurements() {
        JdbcMeasurementRepository repository = new JdbcMeasurementRepository("jdbc:h2:mem:measurements;DB_CLOSE_DELAY=-1");
        repository.save("ADD", 2, "FEET");
        assertEquals(1, repository.findAll().size());
        assertEquals("ADD", repository.findAll().get(0).operation());
    }
}
