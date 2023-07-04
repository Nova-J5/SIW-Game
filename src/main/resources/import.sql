insert into game values (nextval('game_seq'), 'gioco bello', 'super mario', 2000);
insert into game values (nextval('game_seq'), 'gioco brutto', 'pokemon scarlatto', 2022);
insert into game values (nextval('game_seq'), 'gioco meraviglioso', 'zelda', 2018);
insert into genre values (nextval('genre_seq'), 'descrizione genere', 'azione');
insert into platform values (nextval('platform_seq'), 'descrizione console', 'nintendo', 1800)
insert into platform_games values (1, 1)
insert into genre_games values (1, 1)

