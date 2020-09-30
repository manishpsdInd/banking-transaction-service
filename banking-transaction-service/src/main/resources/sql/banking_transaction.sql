create if not exists banking_transaction;
grant all privileges on banking_transaction.* to 'root'@'localhost';
use banking_transaction;

create if not exists TRANSACTIONDETAILS (
    TRANSACTION_ID 	    INT             NOT NULL PRIMARY KEY,
    CARD_NUMBER 	    INT             NOT NULL,
    TRANSACTION_AMOUNT 	INT             NOT NULL,
    TRANSACTION_DATE 	TimeStamp       NOT NULL,
    TRANSACTION_TYPE 	VARCHAR(10)     NOT NULL,
    STATUS_CODE 	    VARCHAR(10)     NOT NULL,
    STATUS_MESSAGE 	    VARCHAR(100)
);

