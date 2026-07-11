DROP TABLE IF EXISTS players;
DROP TABLE IF EXISTS race;
DROP TABLE IF EXISTS profession;

CREATE TABLE race
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE profession
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE player
(
    id             SERIAL PRIMARY KEY,
    name           VARCHAR(12) NOT NULL UNIQUE,
    title          VARCHAR(30) NOT NULL,
    race_id        INT REFERENCES race (id),
    profession_id  INT REFERENCES profession (id),
    experience     INT         NOT NULL,
    level          INT         NOT NULL,
    untilNextLevel INT         NOT NULL,
    birthday       DATE        NOT NULL,
    banned         BOOLEAN     NOT NULL
);

INSERT INTO race(name)
values ('HUMAN'),
       ('DWARF'),
       ('ELF'),
       ('GIANT'),
       ('ORC'),
       ('TROLL'),
       ('HOBBIT');


INSERT INTO profession(name)
values ('WARRIOR'),
       ('ROGUE'),
       ('SORCERER'),
       ('CLERIC'),
       ('PALADIN'),
       ('NAZGUL'),
       ('WARLOCK'),
       ('DRUID');

INSERT INTO player (name, title, race_id, profession_id, experience, level, untilNextLevel, birthday, banned)
VALUES ('Thorin', 'Молот Горы', 2, 1, 120, 1, 180, '1985-03-15', true),
       ('Elara', 'Лунная Заклинательница', 3, 3, 350, 2, 250, '1990-07-22', false),
       ('Gimli', 'Хранитель Камня', 2, 8, 500, 2, 100, '1975-11-11', true),
       ('Liriel', 'Светозарная Целительница', 3, 4, 1000, 4, 500, '2000-05-30', false),
       ('Brok', 'Кровавый Топор', 5, 1, 2450, 6, 350, '1982-01-05', true),
       ('Saphira', 'Ночная Тень', 7, 2, 4000, 8, 500, '1998-09-17', false),
       ('Kael', 'Рыцарь Рассвета', 1, 5, 5500, 10, 1100, '1987-12-25', true),
       ('Fizz', 'Повелитель Бурь', 4, 7, 7200, 11, 600, '1995-04-10', false),
       ('Zarn', 'Тёмный Всадник', 6, 6, 9000, 12, 100, '1980-08-08', true),
       ('Mira', 'Архимаг Гильдии', 1, 3, 10000, 13, 500, '2005-02-14', false);

