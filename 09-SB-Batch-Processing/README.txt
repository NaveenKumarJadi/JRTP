
GET: http://localhost:9088/customers

**********************
DB Queries
**********************
show databases;
use batchdemo;
show tables;

select * from customers_info;
select * from batch_job_seq;
select * from batch_job_execution_context;

truncate customers_info;

drop table customers_info;