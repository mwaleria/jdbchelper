DROP table if exists PERSONS;
create table if not exists PERSONS (
  ID int identity primary key,
  FIRST_NAME varchar,
  LAST_NAME varchar
)
