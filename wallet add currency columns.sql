use mu_db;



ALTER TABLE wallet
drop column currency_symbol;



ALTER TABLE wallet
drop column currency_type;



ALTER TABLE wallet
    ADD currency_symbol varchar(10) default "CA$";



ALTER TABLfredE wallet
    ADD currency_type varchar(64) default "CAD";