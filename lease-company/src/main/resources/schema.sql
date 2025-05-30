create table car(
    id int generated by default as identity,
    make varchar(255),
    model varchar(255),
    version varchar(255),
    number_of_doors int,
    gross_price numeric(9,2),
    nett_price numeric(9,2),
    emission int,
    primary key (id));

alter table car
    add constraint UQ_MAKE_MODEL_VERSION UNIQUE (make, model, version) ;

create table interest(
    id int generated by default as identity,
    interest_rate numeric(9,2),
    start_date date,
    primary key (id));
