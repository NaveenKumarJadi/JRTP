
End Points::
GET: http://localhost:9098/case/1

POST : http://localhost:9098/planSelection
{
    "caseNum" : 1,
    "planId" : 3
}

POST: http://localhost:9098/income
{
    "caseNum" : 1,
    "empIncome" : 100,
    "propertyIncome" : 250
}

POST : http://localhost:9098/education
{
    "caseNum" : 1,
    "graduationYear" : 2019,
    "highestQualification" : "Bachelors"
}

POST: http://localhost:9098/childrens
{
    "caseNum": 1,
    "childs": [
        {
            "childAge": 20,
            "childName": "John",
            "childSsn": 686868
        },
        {
            "childAge": 18,
            "childName": "Smith",
            "childSsn": 586868
        }
    ]
}


****************************
MYSQL::
****************************
show databases;
use jrtp;
show tables;
select * from user_master;
drop table user_master;

select * from citizen_apps;
select * from plan_master;
select * from dc_cases;
select * from dc_income;
select * from dc_education;
select * from dc_children;

drop table dc_cases;
drop table dc_income;
drop table dc_education;
drop table dc_children;
