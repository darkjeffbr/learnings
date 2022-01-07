CREATE TABLE language (
    code varchar(5) not null,
    primary key (code)
);

CREATE TABLE movie (
    id int auto_increment,
    release_date date not null comment 'Release date in UTC',
    views double not null default 0,
    primary key(id)
);

CREATE TABLE movie_translation (
    movie int not null,
    language varchar(5) not null,
    title varchar(500) not null,
    cost double not null default 0,
    cost_currency varchar(4) not null default 'USD',
    primary key(movie, language),
    foreign key(movie) references movie(id),
    foreign key(language) references language(code),
);

INSERT INTO language VALUES ('en'),('pt-BR'),('es');

INSERT INTO movie (release_date, views) VALUES ( '2012-04-11', 848228 );
INSERT INTO movie_translation (movie, language, title, cost, cost_currency) VALUES ( 1, 'en', 'The Avengers', 220000000, 'USD'), ( 1, 'pt-BR', 'The Avengers - Os Vingadores', 1238468000, 'BRL'), ( 1, 'es', 'Los Vengadores', 195049800, 'EUR');

INSERT INTO movie (release_date, views) VALUES ( '2001-12-13', 848228 );
INSERT INTO movie_translation (movie, language, title, cost, cost_currency) VALUES ( 2, 'en', 'A Beautiful Mind', 78000000, 'USD'), ( 2, 'pt-BR', 'Uma Mente Brilhante', 439054200, 'BRL'), ( 2, 'es', 'Una mente maravillosa', 69154020, 'EUR');