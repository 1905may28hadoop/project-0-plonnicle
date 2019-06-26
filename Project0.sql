DROP TABLE ACCOUNTS CASCADE CONSTRAINTS;
CREATE TABLE ACCOUNTS (
    id NUMBER(6) PRIMARY KEY,
    accountnum VARCHAR2(10),
    username VARCHAR2(100),
    --is_checking CHAR(1) CHECK (is_checking IN ('T','F')), -- If it's not a checking account, it is automatically savings.
    pass_word VARCHAR2(200),
    balance NUMBER(20,2)
);

--CREATE TABLE CUSTOMERS (
--    id NUMBER(6) PRIMARY KEY,
--    username VARCHAR2(100)
--);

--CREATE TABLE CHECKING_ACCOUNTS (
--    id NUMBER(6) PRIMARY KEY,
--    account_num NUMBER(10),
--    username VARCHAR2(100),
--    is_checking CHAR(1) CHECK (is_checking IN ('T','F')), -- ONLY list if is_checking is set to 'T'
--    balance NUMBER(20)
--);

--CREATE TABLE SAVINGS_ACCOUNTS (
--    id NUMBER(6) PRIMARY KEY,
--    account_num NUMBER(10),
--    username VARCHAR2(100),
--    is_checking CHAR(1) CHECK (is_checking IN ('T','F')), -- ONLY list if is_checking is set to 'F'
--    balance NUMBER(20)
--);
INSERT INTO ACCOUNTS 
VALUES (1, '0000000001', 'King Kawng', 'banana12', 12.33);
SELECT * FROM ACCOUNTS;

INSERT INTO ACCOUNTS 
VALUES (2, '0000000002', 'Gojira', 'at0micbreath', 1223200.00);
UPDATE ACCOUNTS SET balance = 0 WHERE accountnum = '0000000002';

DELETE FROM ACCOUNTS WHERE username = 'Thien';
