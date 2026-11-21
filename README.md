# Bridgelabz Quantity Measurement App

A Java quantity measurement application built one use case at a time from the BridgeLabz UC documents.

The `main` branch contains this README. Development starts on `develop`; each use case is implemented in its own `feature/uc-N-*` branch and merged into `develop` with a non fast forward commit.

## Run the current application

Check out `develop` and run `mvn spring-boot:run` with Java 21 or newer. The REST API uses an in-memory H2 database for development.

Example: `POST /api/v1/quantities/add` with JSON:

```json
{
  "category": "LENGTH",
  "firstValue": 1,
  "firstUnit": "FEET",
  "secondValue": 12,
  "secondUnit": "INCHES"
}
```

Supported categories are `LENGTH`, `WEIGHT`, `VOLUME`, and `TEMPERATURE`. Available operations are `compare`, `convert`, `add`, `add-with-target-unit`, `subtract`, `subtract-with-target-unit`, and `divide`. Set `targetUnit` for conversion or a chosen arithmetic result unit. Temperature supports comparison and conversion only.

History endpoints: `GET /api/v1/quantities/history`, `/history/operation/{operation}`, `/history/type/{type}`, `/history/errored`, and `/count/{operation}`.
