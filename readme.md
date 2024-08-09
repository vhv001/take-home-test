<h2>Introduction</h2>

This project is a ledger like system which handles payment related operations. The system supports functionalities like creation of accounts, performing a transaction and managing transaction types which the system can handle.

<h2>API spec </h2>

### Create Account - API to create a new user account for a given document and product name
```http
POST /v1/accounts

request
{
    "document_id"  : "AWGEQ9Q12",
    "product_name" : "cards"
}

response
{
    "account_id"                : "IN912AGEA127",
    "account_opening_timestamp" : "2024-08-08T23:00:00",
    "account_status"            : "ACTIVE"
}
```


| Parameter | Type | Description |
| :--- | :--- | :--- |
| `document_id` | `string` | [**Required**] Identity proof |
| `product_name`| `string` | [**Required**] Type of account|

| Status Code | Description |
| :--- | :--- |
| 201 | `Account crated successfully` |
| 400 | `Request body has empty fields` |
| 500 | `Internal server error` |
| 503 | `Happens when account number generation fails` |

<br></br>
### Get Account - API to retrieve the information for a given account Id
```http

GET /v1/accounts/{account_id}

response
{
    "account_id"                : "IN912AGEA127",
    "product_name"              : "cards",
    "document_id"               : "AWGEQ9Q12",
    "account_opening_timestamp" : "2024-08-08T23:00:00",
    "account_status"            : "ACTIVE"
}

```
| Parameter | Type | Description |
| :--- | :--- | :--- |
| `account_id` | `string` | [**Required**] Account Id to fetch |


| Status Code | Description |
| :--- | :--- |
| 200 | `Account info fetched successfully` |
| 204 | `Provided account id doesn't exist in system` |
| 400 | `Account id is empty in params` |
| 500 | `Internal server error` |

<br></br>

### Create transaction - API to handle payment requests of user's

```http
POST /v1/transaction

request
{
    "account_id"        : "IN912AGEA127",
    "operation_type_id" : 1,
    "amount"            : "100",
    "currency"          : "INR",
}

response
{
    "transaction_id"    : "4d035e97-a2ea-4316-94cb-fba0ce2102d7",
    "status"            : "SUCCESS",
    "transaction_time"  : "2024-08-09T23:00:00"
}

```
| Parameter | Type | Description |
| :--- | :--- | :--- |
| `account_id` | `string` | [**Required**] Account Id to fetch |
| `operation_type_id` | `integer` | [**Required**] Type of transaction |
| `amount` | `string` | [**Required**] Transaction amount |
| `currency` | `string` | [**Required**] Currency |


| Status Code | Description |
| :--- | :--- |
| 201 | `Account info fetched successfully` |
| 400 | `Provided account id doesn't exist in system` |
| 401 | `Account id is empty in params` |
| 500 | `Internal server error` |
| 501 | `When provided operation type id is not supported by system` |

<br></br>

### Create new operation type - API to create new operation/functionality type which is supported by our system
```http
POST /v1/operationtype

request
{
    "operation_type"    : 1,
    "description"       : "cards authorisation",
    "debit_or_credit"   : "DEBIT"
}

response
{
}

```
| Parameter | Type | Description |
| :--- | :--- | :--- |
| `operation_type` | `integer` | [**Required**] Unique representation of operation/payment type|
| `description` | `integer` | [**Required**] Type of operation |
| `debit_or_credit` | `string` | [**Required**] Determines whether it is a debit or credit txn from user's perspective |


| Status Code | Description |
| :--- | :--- |
| 201 | `Created new operation type successfully` |
| 400 | `Invalid request`|
| 500 | `Internal server error` |


<h3> Database schema </h3>

<h4>Accounts table</h4>

```
+----------------------+-------------+------+-----+-------------------+-----------------------------------------------+
| Field                | Type        | Null | Key | Default           | Extra                                         |
+----------------------+-------------+------+-----+-------------------+-----------------------------------------------+
| id                   | int         | NO   | PRI | NULL              | auto_increment                                |
| account_id           | varchar(15) | NO   | MUL | NULL              |                                               |
| document_id          | varchar(20) | NO   |     | NULL              |                                               |
| product_name         | varchar(15) | NO   |     | NULL              |                                               |
| account_opening_time | timestamp   | NO   |     | NULL              |                                               |
| account_status       | varchar(10) | NO   |     | NULL              |                                               |
| updated_at           | timestamp   | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED on update CURRENT_TIMESTAMP |
| created_at           | timestamp   | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED                             |
+----------------------+-------------+------+-----+-------------------+-----------------------------------------------+
```

<h4>Operation types table</h4>

```
+-----------------+-------------+------+-----+-------------------+-----------------------------------------------+
| Field           | Type        | Null | Key | Default           | Extra                                         |
+-----------------+-------------+------+-----+-------------------+-----------------------------------------------+
| operation_id    | int         | NO   | PRI | NULL              |                                               |
| description     | varchar(40) | NO   |     | NULL              |                                               |
| debit_or_credit | varchar(10) | NO   |     | NULL              |                                               |
| created_at      | timestamp   | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED                             |
| updated_at      | timestamp   | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED on update CURRENT_TIMESTAMP |
+-----------------+-------------+------+-----+-------------------+-----------------------------------------------+
```

<h4>Transactions table</h4>

```
+------------------+-------------+------+-----+-------------------+-----------------------------------------------+
| Field            | Type        | Null | Key | Default           | Extra                                         |
+------------------+-------------+------+-----+-------------------+-----------------------------------------------+
| id               | int         | NO   | PRI | NULL              | auto_increment                                |
| transaction_id   | varchar(40) | NO   | MUL | NULL              |                                               |
| account_id       | varchar(15) | NO   |     | NULL              |                                               |
| operation_type   | int         | NO   |     | NULL              |                                               |
| amount           | varchar(10) | NO   |     | NULL              |                                               |
| currency         | varchar(5)  | NO   |     | NULL              |                                               |
| status           | varchar(10) | NO   |     | NULL              |                                               |
| transaction_time | timestamp   | NO   |     | NULL              |                                               |
| created_at       | timestamp   | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED                             |
| updated_at       | timestamp   | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED on update CURRENT_TIMESTAMP |
+------------------+-------------+------+-----+-------------------+-----------------------------------------------+
```