INSERT INTO public.brands (name, model)
VALUES ('KIA', 'RIO'),
       ('Volkswagen', 'Passat'),
       ('Mercedes-Benz', 'C-Class'),
       ('BMW', '5 Series'),
       ('Nissan', 'Sentra'),
       ('Nissan', 'Almera')
;

INSERT INTO public.cars (brand_id, production_year, automatic, availability, price, disabled)
VALUES (1, 2018, 'false', 'true', 1500, 'false'),
       (2, 2020, 'true', 'true', 2500, 'false'),
       (3, 2020, 'true', 'true', 2500, 'false')
;

INSERT INTO public.roles (name)
values ('User'),
       ('Manager'),
       ('Admin');

INSERT INTO public.cities(name)
values ('Москва'),
       ('Санкт-Петербург'),
       ('Казань'),
       ('Нижний Новгород');

INSERT INTO public.locations(city_id, address)
VALUES (1, 'ул. Одесская, д.2'),
       (2, 'пр.Просвещения 80/3'),
       (3, 'ул.Московская, 13А'),
       (4, 'пл.Революции, 2а');

INSERT INTO public.operation_type(name)
VALUES ('Списание'),
       ('Поступление');

INSERT INTO public.status(name)
VALUES ('Забронирован'),
       ('Отменен'),
       ('Завершен');
