


insert into game values (nextval('game_seq'), 'gioco bello', 'super mario', 2000);
insert into game values (nextval('game_seq'), 'gioco brutto', 'pokemon scarlatto', 2022);
insert into game values (nextval('game_seq'), 'gioco meraviglioso', 'zelda', 2018);
insert into platform (id,carousel_description,description,name,year_of_release) values (nextval('platform_seq'),'Console ibrida tra portatile e fissa di nintendo' ,'descrizione console', 'nintendo switch', 1800);





INSERT INTO developer VALUES (nextval('developer_seq'), 'descrizione', 'Nintendo', 1889);

INSERT INTO developer VALUES (nextval('developer_seq'), 'descrizione', 'Sony', 1946);

INSERT INTO developer VALUES (nextval('developer_seq'), 'descrizione', 'Microsoft', 1975);

INSERT INTO developer VALUES (nextval('developer_seq'), 'descrizione', 'Team Cherry', 2014);




INSERT INTO game VALUES (nextval('game_seq'), 'descrizione', 'Super Mario Odyssey', 2017);

INSERT INTO game VALUES (nextval('game_seq'), 'descrizione', 'Pokémon Colosseum', 2003);

INSERT INTO game VALUES (nextval('game_seq'), 'descrizione', 'Uncharted 4: Fine di un Ladro', 2016);

INSERT INTO game VALUES (nextval('game_seq'), 'descrizione', 'Halo: Combat Evolved', 2001);

INSERT INTO game VALUES (nextval('game_seq'), 'descrizione', 'Hollow Knight', 2017);




INSERT INTO genre (id, name) VALUES (nextval('genre_seq'), 'Azione')

INSERT INTO genre (id, name) VALUES (nextval('genre_seq'), 'Arcade')

INSERT INTO genre (id, name) VALUES (nextval('genre_seq'), 'Avventura')

INSERT INTO genre (id, name) VALUES (nextval('genre_seq'), 'Beat em all')

INSERT INTO genre (id, name) VALUES (nextval('genre_seq'), 'Cooperazione')

INSERT INTO genre (id, name) VALUES (nextval('genre_seq'), 'Corse')

INSERT INTO genre (id, name) VALUES (nextval('genre_seq'), 'FPS')

INSERT INTO genre (id, name) VALUES (nextval('genre_seq'), 'Gestionale')

INSERT INTO genre (id, name) VALUES (nextval('genre_seq'), 'Guerra')

INSERT INTO genre (id, name) VALUES (nextval('genre_seq'), 'Indie')

INSERT INTO genre (id, name) VALUES (nextval('genre_seq'), 'MMO')

INSERT INTO genre (id, name) VALUES (nextval('genre_seq'), 'Piattaforme')

INSERT INTO genre (id, name) VALUES (nextval('genre_seq'), 'Picchiaduro')

INSERT INTO genre (id, name) VALUES (nextval('genre_seq'), 'RPG')

INSERT INTO genre (id, name) VALUES (nextval('genre_seq'), 'Simulazione')

INSERT INTO genre (id, name) VALUES (nextval('genre_seq'), 'Single-player')

INSERT INTO genre (id, name) VALUES (nextval('genre_seq'), 'Sport')

INSERT INTO genre (id, name) VALUES (nextval('genre_seq'), 'Strategia')

INSERT INTO genre (id, name) VALUES (nextval('genre_seq'), 'VR')

INSERT INTO genre_games VALUES (1, 1)
INSERT INTO genre_games VALUES (51, 1)
INSERT INTO genre_games VALUES (1, 101)

INSERT INTO platform_games VALUES (1, 1)
