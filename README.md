# Quantity Measurement App

A Java and Spring Boot learning project for comparing, converting, and calculating quantities. The project grows through 17 use cases. The current application is on [develop](https://github.com/Mihir-Lakhani/Bridgelabz-Quantity-Measurement-App/tree/develop); `main` contains documentation only.

## Supported measurements

| Category | Units |
| --- | --- |
| Length | `FEET`, `INCHES`, `YARDS`, `CENTIMETERS` |
| Weight | `KILOGRAM`, `GRAM`, `POUND` |
| Volume | `LITRE`, `MILLILITRE`, `GALLON` |
| Temperature | `CELSIUS`, `FAHRENHEIT` |

Lengths, weights, and volumes support comparison, conversion, addition, subtraction, and division. Temperatures support comparison and conversion; arithmetic on absolute temperatures is rejected.

## Use cases

Each UC has its own `feature/uc-*` branch, merged into `develop` with a non-fast-forward merge.

1. **UC01 — Feet equality:** compare two measurements in feet.
2. **UC02 — Inches equality:** compare inch measurements and distinguish units.
3. **UC03 — Generic length:** use one quantity class for length comparisons.
4. **UC04 — More length units:** support yards and centimeters.
5. **UC05 — Conversion:** convert between length units.
6. **UC06 — Addition:** add two lengths.
7. **UC07 — Target unit:** choose the unit of an addition result.
8. **UC08 — Standalone unit enum:** move conversion factors into the unit enum.
9. **UC09 — Weight:** add weight units and operations.
10. **UC10 — Generic quantity:** use a unit interface and generic `Quantity<U>`.
11. **UC11 — Volume:** add volume units and operations.
12. **UC12 — Subtraction and division:** add both arithmetic operations.
13. **UC13 — Shared arithmetic:** centralize arithmetic logic.
14. **UC14 — Temperature:** add Celsius and Fahrenheit conversion.
15. **UC15 — Layers:** introduce controller, service, and persistence layers.
16. **UC16 — JDBC persistence:** save operation history with JDBC.
17. **UC17 — Spring REST and JPA:** expose HTTP endpoints and persist history with Spring Data JPA.

The current `develop` branch uses JPA directly. The UC15 and UC16 branches retain their earlier learning-stage implementations.

## Run and test

Requirements: Java 21 or newer and Maven.

```bash
git switch develop
mvn spring-boot:run
```

Run `mvn test` separately. The server uses port `8080` by default. Development data is stored in an in-memory H2 database and disappears when the application stops.

## REST API

Base path: `/api/v1/quantities`. Send JSON to `POST /{operation}`. Operations are `compare`, `convert`, `add`, `add-with-target-unit`, `subtract`, `subtract-with-target-unit`, and `divide`.

Example: `POST http://localhost:8080/api/v1/quantities/add`

```json
{
  "category": "LENGTH",
  "firstValue": 1,
  "firstUnit": "FEET",
  "secondValue": 12,
  "secondUnit": "INCHES"
}
```

Response:

```json
{"result": 2.0, "unit": "FEET"}
```

For `convert`, provide `firstValue`, `firstUnit`, and `targetUnit`; no second quantity is needed. Other binary operations need `secondValue` and `secondUnit`. `targetUnit` is optional for addition and subtraction; without it, the result uses `firstUnit`. Category and unit names are case-insensitive.

History endpoints:

- `GET /history` — all saved operations.
- `GET /history/operation/{operation}` — one operation type.
- `GET /history/type/{type}` — one measurement category.
- `GET /history/errored` — rejected calculation attempts recorded as errors.
- `GET /count/{operation}` — count saved operations of one type.

Invalid values, unknown units, unsupported temperature arithmetic, and division by zero return HTTP 400 with an `error` message.

## Code structure

`Quantity<U>` and the unit enums contain measurement rules. `QuantityRestController` receives HTTP requests; `QuantityMeasurementService` performs operations and saves results; `JpaMeasurementRepository` stores history. Request and history records live in `model`. Tests cover calculations and representative REST requests.
