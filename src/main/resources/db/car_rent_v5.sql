-- CREATE DATABASE IF NOT EXISTS 'car_rental';

DROP TABLE IF EXISTS company_transaction;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS images;
DROP TABLE IF EXISTS cars;
DROP TABLE IF EXISTS brands;
DROP TABLE IF EXISTS user_account;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS locations;
DROP TABLE IF EXISTS cities;
DROP TABLE IF EXISTS status;
DROP TABLE IF EXISTS operation_type;
DROP TABLE IF EXISTS company_account;
DROP TABLE IF EXISTS cars;

CREATE TABLE IF NOT EXISTS brands
(
    id    bigint CONSTRAINT brands_pk GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name  varchar(32) NOT null,
    model varchar(32) not null
);

CREATE TABLE IF NOT EXISTS cars
(
    id              bigint CONSTRAINT cars_pk GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    brand_id        bigint  not null,
    production_year integer not null,
    automatic       boolean not null,
    availability    boolean not null,
    price           bigint  not null,
    disabled        boolean not null,
    CONSTRAINT "FK_car_brand" FOREIGN KEY (brand_id) REFERENCES brands (id)
);

CREATE TABLE IF NOT EXISTS images
(
    id     bigint CONSTRAINT images_pk GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    car_id bigint not null,
    CONSTRAINT "FK_image_car"
        FOREIGN KEY (car_id) REFERENCES cars (id),
    link   varchar
);

CREATE TABLE If NOT EXISTS roles
(
    id   bigint CONSTRAINT roles_pk GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(32) not null
);

CREATE TABLE If NOT EXISTS users
(
    id        bigint CONSTRAINT users_pk GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    firstname varchar(64) not null,
    lastname  varchar(64) not null,
    email     varchar(64) not null,
    phone     varchar(16) not null,
    password  varchar(60) not null,
    role_id   bigint      not null,
    CONSTRAINT "FK_users_role"
        FOREIGN KEY (role_id) REFERENCES roles (id),
    disabled  boolean     not null,
    UNIQUE(email)
);

CREATE TABLE IF NOT EXISTS cities
(
    id   bigint CONSTRAINT cities_pk GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(64)
);

CREATE TABLE If NOT EXISTS locations
(
    id      bigint CONSTRAINT locations_pk GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    city_id bigint       not null,
    constraint "FK_location_city"
        FOREIGN KEY (city_id) REFERENCES cities (id),
    address varchar(255) not null
);

CREATE TABLE IF NOT EXISTS status
(
    id   bigint constraint status_pk GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(64) default 'New'
);

CREATE TABLE If NOT EXISTS orders
(
    id                  bigint constraint orders_pk GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    car_id              bigint not null,
    constraint "FK_orders_cars"
        FOREIGN KEY (car_id) references cars (id),
    user_id             bigint not null,
    constraint "FK_orders_users"
        FOREIGN KEY (user_id) references users (id),
    start_date          date   not null,
    end_date            date   not null,
    pick_up_location_id bigint not null,
    constraint "FK_orders_pick_up_location"
        FOREIGN KEY (pick_up_location_id) references locations (id),
    status_id           bigint not null,
    constraint "FK_order_status"
        FOREIGN KEY (status_id) references status (id),
    creation_date       date   not null,
    date_of_change      date   not null,
    total_amount        bigint not null
);

CREATE TABLE IF NOT EXISTS operation_type
(
    id   bigint constraint operation_type_pk GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(32) not null
);

CREATE TABLE IF NOT EXISTS user_account
(
    id      bigint constraint user_account_pk GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id bigint not null,
    CONSTRAINT user_account_user_id UNIQUE (user_id),
    CONSTRAINT "FK_user_account_user"
        FOREIGN KEY (user_id) references users (id),
    balance bigint
);

CREATE TABLE IF NOT EXISTS company_account
(
    id      bigint constraint company_balance_pk GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    balance bigint
);

CREATE TABLE IF NOT EXISTS company_transaction
(
    id                 bigint constraint company_transaction_pk GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    order_id           bigint not null,
    constraint "FK_order_company_transaction"
        FOREIGN KEY (order_id) references orders (id),
    operation_type_id  bigint not null,
    constraint "FK_operaion_type_company_transaction"
        FOREIGN KEY (operation_type_id) references operation_type (id),
    transaction_amount bigint,
    transaction_date   date,
    company_account_id bigint not null,
    constraint "FK_company_account_company_transaction"
        FOREIGN KEY (company_account_id) references company_account (id),
    user_account_id    bigint not null,
    constraint "FK_user_account_company_transaction"
        FOREIGN KEY (user_account_id) references user_account (id)
);

-- INSERT INTO public.brands (name, model)
-- VALUES('KIA', 'RIO'), 
-- ('Volkswagen', 'Passat'), 
-- ('Mercedes-Benz', 'C-Class'), 
-- ('BMW', '5 Series'),
-- ('Nissan', 'Sentra'),
-- ('Nissan', 'Almera')
-- ;

-- INSERT INTO public.cars (brand_id, production_year, automatic, availability, price, disabled)
-- VALUES(1, 2018, 'false', 'true', 1500, 'false'),
-- (2, 2020, 'true', 'true', 2500, 'false'),
-- (3, 2020, 'true', 'true', 2500, 'false')
-- ;

-- INSERT INTO public.roles (name)
-- values ('User'),
-- ('Manager'),
-- ('Admin');

-- INSERT INTO public.cities(name)
-- values ('Москва'),
-- ('Санкт-Петербург'),
-- ('Казань'),
-- ('Нижний Новгород');

-- INSERT INTO public.locations(city_id, address)
-- VALUES (1, 'ул. Одесская, д.2'),
-- (2, 'пр.Просвещения 80/3'),
-- (3, 'ул.Московская, 13А'),
-- (4, 'пл.Революции, 2а');

-- INSERT INTO public.operation_type(name)
-- VALUES ('Списание'),
-- ('Поступление');

-- INSERT INTO public.status(name)
-- VALUES ('Забронирован'),
-- ('Отменен'),
-- ('Завершен');
