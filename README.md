# Promo Codes App

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Elements](#elements)
- [REST API Endpoints](#rest-api-endpoints)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
  - [Installation](#installation)
  - [Usage](#usage)
    - [Example Requests](#example-requests)


## Introduction

This application is designed to manage discount codes (promo codes) for sales or promotions. It provides a REST API for creating, updating, and using promo codes, as well as managing products and simulating purchases. The backend is developed using Java and the Spring framework, with an in-memory H2 database for data storage

## Features

- Create, edit, and list products
- Create, list, and get details of promo codes
- Calculate discount price for a product with a promo code
- Simulate a purchase and store purchase information with or without a promo code

## Elements

- Product: Represents a product that can be purchased. 
    - Attributes: name, description, regular price, currency
- Promo Code: Represents a discount code that can be applied to a product.
  - Attributes: code (Unique text identifier with 3-24 alphanumeric case-sensitive characters.), expiration date, discount amount, currency, max usages, type (fixed or percentage)
- Purchase: Represents a purchase of a product.
    - Attributes: date, product, regular price, discount amount

- Discount Calculation:
    - Fixed: discount price = regular price - promo code discount
    - Percentage: discount price = regular price - (regular price * promo code discount)
    - Validates promo code existence, expiration date, currency match, and usage limit.

## REST API Endpoints

1. **Products**
    - `POST /product/add`: Create a new product
    - `GET /product/all`: Get all products
    - `PUT /product/edit/{id}`: Update product data

2. **Promo Codes**
    - `POST /code/add`: Create a new promo code
    - `GET /code/all`: Get all promo codes
    - `GET /code/details/{code}`: Get promo code details by code

3. **Discount Price**
    - `GET /purchase/discount/{productId}`: Get the discounted price by providing a product and a promo code

4. **Purchase**
    - `POST /purchase/buy/{productId}`: Simulate a purchase

5. **Sales Report**
    - `GET /report`: Get a sales report with total purchases and discounts by currency

## Technologies

- Java
- Spring Boot
- Spring Data JPA
- H2 Database
- Maven

# Getting Started

## Installation

1. Clone the repository
   ```bash
   git clone https://github.com/AleksandraGorska02/promo-codes-app.git
    ```
2. Change directory
    ```bash
    cd promo-codes-app
    ```
3. Build the project using Maven:
    ```bash
    mvn clean install
    ```
4. Run the application:
    ```bash
    java -jar target/promo-codes-app-0.0.1-SNAPSHOT.jar
    ```
    or
    ```bash
    mvn spring-boot:run
    ```

5. The application will start on `http://localhost:8080`

## Usage

1. Use the provided REST API endpoints to interact with the application.

2. Create products and promo codes, calculate discount prices, and simulate purchases.

3. View the sales report to see the total purchases and discounts by currency.

### Example Requests

Recommended to use Jetbrains HTTP Client or Postman to send requests.

1. Create a product:

```
POST http://localhost:8080/product/add
Content-Type: application/json

{
    "name": "Product 1",
    "description": "Product 1 description",
    "price": 100.0,
    "currency": "USD"
}
```
response:
```

Response code: 200; Response message: Product added successfully
```
2. Get all products:

```
GET http://localhost:8080/product/all
```
response:
```JSON
[
  {
    "id": 1,
    "name": "Product 1",
    "description": "Description 1",
    "price": 10.99,
    "currency": "USD"
  },
  {
    "id": 2,
    "name": "Product 2",
    "description": null,
    "price": 20.0,
    "currency": "USD"
  },
  {
    "id": 3,
    "name": "Product 3",
    "description": "Description 3",
    "price": 30.0,
    "currency": "EUR"
  }
  ]
```

3. Edit a product:

```
PUT http://localhost:8080/product/edit/1
Content-Type: application/json

{
    "name": "Product 1 edited",
    "price": 100.0,
    "currency": "PLN"
}
```
response:
```
Response code: 200; Response message: Product edited successfully

```
4. Create a promo code:

```
POST http://localhost:8080/code/add
Content-Type: application/json
{
    "code": "newCode",
    "discount": 10,
    "expirationDate": "2024-12-31",
    "maxUses": 100,
    "currency": "USD, EUR, GBP"
}
```
or
```
POST http://localhost:8080/code/add
Content-Type: application/json
{
    "code": "newPromoCode",
    "discount": 10,
    "expirationDate": "2024-12-31",
    "maxUses": 100,
    "currency": "USD, EUR, GBP",
    "percentage": "true"
}
```
response:
```
Response code: 200; Response message: code added successfully
```
5. Get all promo codes:

```
GET http://localhost:8080/code/all
```
response:
```JSON
[
  {
    "id": 1,
    "code": "newCode",
    "expirationDate": "2024-12-31",
    "discount": 10,
    "currency": "USD, EUR, GBP",
    "maxUses": 100,
    "currentUses": 0,
    "percentage": false
  },
  {
    "id": 2,
    "code": "newPromoCode",
    "expirationDate": "2024-12-31",
    "discount": 10,
    "currency": "USD, EUR, GBP",
    "maxUses": 100,
    "currentUses": 0,
    "percentage": true
  }
]
```

6. Get promo code details:

```
GET http://localhost:8080/code/details/Code1
```
response:
```JSON
{
  "id": 1,
  "code": "Code1",
  "expirationDate": "2024-12-31",
  "discount": 10.0,
  "currency": "USD",
  "maxUses": 100,
  "currentUses": 0,
  "percentage": true
}
```

7. Get discount price:

```
GET http://localhost:8080/purchase/discount/2?code=Code1
```

response:
(if promo code is valid, price is after discount, amountOfDiscount is the discount amount)

```JSON
{
  "price": 18.0,
  "amountOfDiscount": 2.0,
  "valid": true,
  "response": "Discount applied"
}
```
if promo code is invalid:

- Promo code does not exist

```
Response code: 404; Response message: Code not found
```


- Currency does not match product currency

```JSON
{
  "price": 100.0,
  "amountOfDiscount": 0.0,
  "valid": false,
  "response": "Code currency does not match product currency"
}
```
- Promo code expired

```JSON
{
  "price": 20.0,
  "amountOfDiscount": 0.0,
  "valid": false,
  "response": "Code is expired"
}
```
- Max uses reached

```JSON
{
  "price": 20.0,
  "amountOfDiscount": 0.0,
  "valid": false,
  "response": "maximal number of usages was achieved"
}
```

8. Simulate purchase:

```
POST http://localhost:8080/purchase/buy/1?code=Code1
```

response:
```JSON
{
  "id": 1,
  "purchaseDate": "2024-05-16",
  "regularPrice": 20.0,
  "amountOfDiscount": 20.0,
  "productName": "Product 2",
  "currency": "USD"
}
```

purchase without promo code:

```
POST http://localhost:8080/purchase/buy/1
```

response:
```JSON
{
  "id": 2,
  "purchaseDate": "2024-05-16",
  "regularPrice": 100.0,
  "amountOfDiscount": 0.0,
  "productName": "Product 1 edited",
  "currency": "PLN"
}
```

If promo code is invalid, the purchase will not be completed. A response will be returned with the reason why like in case of getting discount price.

9. Get sales report:

```
GET http://localhost:8080/report
```

response:
```JSON
[
  {
    "totalAmount": 120.0,
    "totalDiscount": 40.0,
    "noOfPurchases": 4,
    "currency": "EUR"
  },
  {
    "totalAmount": 500.0,
    "totalDiscount": 0.0,
    "noOfPurchases": 5,
    "currency": "PLN"
  },
  {
    "totalAmount": 60.0,
    "totalDiscount": 60.0,
    "noOfPurchases": 3,
    "currency": "USD"
  }
]
```

## Author
Aleksandra Górska - [AleksandraGorska02](https://github.com/AleksandraGorska02)


