# Petstore Store API — Rest Assured + TestNG (Java 21)

This is a minimal but production‑style test project for Swagger Petstore **/store** endpoints.

- **Tech:** Java 21 · Maven · TestNG · Rest-Assured · Allure · Logback
- **Endpoints covered:** `/store/inventory`, `/store/order` (POST, GET, DELETE)
- **Negatives:** `400 Invalid ID supplied`, `404 Order not found`

## Run

```bash
mvn -q -DbaseUrl=https://petstore.swagger.io/v2 -Dthreads=3 test
```

> Defaults: `baseUrl=https://petstore.swagger.io/v2`, `threads=1`

## Reports

After run, generate Allure report:

```bash
allure serve target/allure-results
```

## Notes

- `GET/DELETE /store/order/{orderId}` treat 1..10 as **valid**; other values → **400**.  
- To get **404**, the test creates an order, deletes it, then fetches it again.
