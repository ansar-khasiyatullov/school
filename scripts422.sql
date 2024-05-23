create table cars (
    id int,
    mark varchar,
    model varchar,
    price int
);

create table users (
    id int,
    name varchar,
    age int,
    canDrive boolean,
    car_id int references cars (id)
);