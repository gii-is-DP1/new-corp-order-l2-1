INSERT INTO authorities(id, name)
VALUES (1, 'ADMIN'),
       (2, 'USER');

-- $2a$12$19.5GDidvCHN4go6jO9dde918jIYONlxe01RgKh5USM3Yd/lFzeI2: password
INSERT INTO users(authority, first_seen, id, last_seen, email, username, password, picture)
VALUES (1, '2020-01-01', 1, '2023-01-01', 'johndoe@example.com', 'JohnDoe',
        '$2a$12$19.5GDidvCHN4go6jO9dde918jIYONlxe01RgKh5USM3Yd/lFzeI2', null),
       (2, '2020-02-01', 2, '2023-02-01', 'jane@example.com', 'Jane',
        '$2a$12$19.5GDidvCHN4go6jO9dde918jIYONlxe01RgKh5USM3Yd/lFzeI2', null),
       (2, '2020-03-01', 3, '2023-03-01', 'samsmith@example.com', 'SamSmith',
        '$2a$12$19.5GDidvCHN4go6jO9dde918jIYONlxe01RgKh5USM3Yd/lFzeI2', null),
       (2, '2023-04-01', 4, '2023-12-31', 'octavio@example.com', 'Octavio',
        '$2a$12$19.5GDidvCHN4go6jO9dde918jIYONlxe01RgKh5USM3Yd/lFzeI2', null),
       (2, '2023-05-01', 5, '2023-12-31', 'alice@example.com', 'Alice',
        '$2a$12$19.5GDidvCHN4go6jO9dde918jIYONlxe01RgKh5USM3Yd/lFzeI2', null),
       (2, '2023-06-01', 6, '2023-12-31', 'bob@example.com', 'Bob',
        '$2a$12$19.5GDidvCHN4go6jO9dde918jIYONlxe01RgKh5USM3Yd/lFzeI2', null),
       (2, '2020-04-01', 7, '2023-12-31', 'emily@example.com', 'Emily',
        '$2a$12$19.5GDidvCHN4go6jO9dde918jIYONlxe01RgKh5USM3Yd/lFzeI2', null),
       (2, '2020-05-01', 8, '2023-12-31', 'peter@example.com', 'Peter',
        '$2a$12$19.5GDidvCHN4go6jO9dde918jIYONlxe01RgKh5USM3Yd/lFzeI2', null),
       (2, '2020-06-01', 9, '2023-12-31', 'susan@example.com', 'Susan',
        '$2a$12$19.5GDidvCHN4go6jO9dde918jIYONlxe01RgKh5USM3Yd/lFzeI2', null),
       (2, '2023-07-01', 10, '2023-12-31', 'michael@example.com', 'Michael',
        '$2a$12$19.5GDidvCHN4go6jO9dde918jIYONlxe01RgKh5USM3Yd/lFzeI2', null),
       (2, '2023-08-01', 11, '2023-12-31', 'olivia@example.com', 'Olivia',
        '$2a$12$19.5GDidvCHN4go6jO9dde918jIYONlxe01RgKh5USM3Yd/lFzeI2', null),
       (2, '2023-09-01', 12, '2023-12-31', 'william@example.com', 'William',
        '$2a$12$19.5GDidvCHN4go6jO9dde918jIYONlxe01RgKh5USM3Yd/lFzeI2', null);

INSERT INTO players(id, user_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6),
       (7, 7),
       (8, 8),
       (9, 9),
       (10, 10),
       (11, 11),
       (12, 12);

INSERT INTO notifications(id, user_id, state, sent_at)
VALUES (1, 1, 1, '2023-01-01 00:00:00'),
       (2, 4, 0, '2023-05-01 10:00:00');

INSERT INTO invite_notifications(id, sender_id, match_code)
VALUES (1, 2, 'ASDFGH'),
       (2, 1, 'OCTAVIO');

INSERT INTO achievements(id, threshold, name, description, image_url, stat)
VALUES (1, 1, 'Victory', 'Win 1 games', null, 'GAMES_WON'),
       (2, 1, 'Welcome', 'Did you like our game?', null, 'TIMES_PLAYED'),
       (3, 15, 'Champion', 'Achieve 15 victories', null, 'GAMES_WON'),
       (4, 30, 'Dedicated Player', 'Play 30 games', null, 'TIMES_PLAYED'),
        (5, 5, 'Lost', 'Like the series', null, 'GAMES_LOST'),
       (6, 10, 'Negotiator', 'Tie 10 games', null, 'GAMES_TIED'),
       (7, 25, 'Infiltrator', 'Infiltrate 25 times', null, 'TIMES_INFILTRATED'),
       (8, 40, 'Conqueror', 'Take over 40 times', null, 'TIMES_TAKEN_OVER'),
       (9, 50, 'The right time for a comeback', 'Use consultant stats 50 times', null, 'CONSULTANT_STATS_USED'),
       (10, 60, 'Skillful', 'Use abilities 60 times', null, 'ABILITIES_USED'),
       (11, 30, 'Legend', 'Like that famous game', 'https://upload.wikimedia.org/wikipedia/commons/2/29/Trifuerza.svg', 'FINAL_SCORE'),
       (12, 100, 'Victor', 'Did you think it was a real typo?', 'https://i.kym-cdn.com/photos/images/original/000/839/199/8a9.jpg', 'GAMES_WON'),
       (13, 1000, 'Time to go outside', 'Play too much', null, 'TIMES_PLAYED'),
       (14, 50, 'Plot a lot', 'Is funny because plot and lot ends with lot', null, 'TIMES_PLOTTED');

INSERT INTO match_stats(id, code, end_time, start_time, mode, max_players)
VALUES (1, 'BIKEHS', '2023-01-01 01:00:00', '2023-01-01 00:00:00', 'NORMAL', 4),
       (2, 'JAKDFO', '2023-01-02 01:00:00', '2023-01-02 00:00:00', 'QUICK', 3),
       (3, 'DJCVMA', '2023-04-01 02:00:00', '2023-04-01 00:00:00', 'NORMAL', 4),
       (4, 'OCTAVIO', '2023-05-01 03:00:00', '2023-05-01 00:00:00', 'QUICK', 2),
       (5, 'ASDFGW', '2023-04-01 02:00:00', '2023-04-01 00:00:00', 'NORMAL', 3),
       (6, 'QIWNSU', '2023-04-01 03:00:00', '2023-04-01 01:00:00', 'QUICK', 4),
       (7, 'QOBDSO', '2023-06-15 04:00:00', '2023-06-15 02:00:00', 'NORMAL', 3),
       (8, 'OIHDPS', '2023-07-20 05:00:00', '2023-07-20 03:00:00', 'QUICK', 4),
       (9, 'OIHAFS', '2023-08-10 06:00:00', '2023-08-10 04:00:00', 'NORMAL', 2),
       (10, 'POAJPD', '2023-09-05 07:00:00', '2023-09-05 05:00:00', 'QUICK', 3),
       (11, 'KAJSET', '2023-10-20 08:00:00', '2023-10-20 06:00:00', 'NORMAL', 4),
       (12, 'MSJYUD', '2023-11-15 09:00:00', '2023-11-15 07:00:00', 'QUICK', 2);

INSERT INTO player_match_stats(id, match_stats_id, player_id, times_infiltrated, times_plotted, times_taken_over,
                               total_victory_points, result)
VALUES (1, 1, 4, 3, 2, 1, 15, 'WON'),
       (2, 2, 4, 1, 1, 0, 2, 'LOST'),
       (3, 2, 1, 5, 3, 4, 4, 'TIED'),
       (4, 2, 2, 1, 1, 2, 4, 'TIED'),
       (5, 3, 1, 2, 3, 1, 20, 'WON'),
       (6, 4, 3, 3, 1, 2, 15, 'WON'),
       (7, 4, 2, 0, 2, 1, 10, 'LOST'),
       (8, 5, 1, 2, 1, 1, 5, 'LOST'),
       (9, 5, 4, 1, 2, 1, 15, 'WON'),
       (10, 6, 1, 0, 2, 1, 2, 'LOST'),
       (11, 6, 2, 1, 1, 1, 7, 'TIED'),
       (12, 6, 3, 3, 2, 1, 3, 'LOST'),
       (13, 6, 4, 2, 1, 1, 7, 'TIED'),
       (14, 7, 1, 3, 1, 1, 10, 'LOST'),
       (15, 7, 2, 2, 2, 0, 8, 'LOST'),
       (16, 7, 3, 1, 0, 3, 12, 'WON'),
       (17, 8, 4, 2, 1, 2, 18, 'WON'),
       (18, 8, 1, 1, 2, 1, 5, 'LOST'),
       (19, 8, 2, 3, 1, 1, 13, 'LOST'),
       (20, 8, 3, 0, 3, 1, 3, 'LOST'),
       (21, 9, 1, 2, 1, 1, 2, 'LOST'),
       (22, 9, 2, 1, 2, 0, 6, 'WON'),
       (24, 10, 4, 3, 1, 2, 14, 'TIED'),
       (25, 10, 1, 0, 2, 1, 3, 'LOST'),
       (26, 10, 2, 2, 1, 1, 14, 'TIED'),
       (28, 11, 1, 1, 1, 1, 5, 'LOST'),
       (29, 11, 2, 2, 0, 0, 8, 'LOST'),
       (30, 11, 3, 0, 2, 2, 10, 'LOST'),
       (31, 11, 4, 3, 1, 1, 12, 'WON'),
       (32, 12, 1, 2, 0, 1, 4, 'LOST'),
       (33, 12, 4, 1, 1, 0, 6, 'WON');

INSERT INTO ability_stats(times_used, id, player_match_stats_id, type)
VALUES (5, 1, 1, 'BROADCAST_NETWORK'),
       (3, 2, 2, 'ONLINE_MARKETING'),
       (4, 3, 3, 'ONLINE_MARKETING'),
       (6, 4, 4, 'SOCIAL_MEDIA');

INSERT INTO conglomerate_stats(agents_used, id, player_match_stats_id, shares_used, conglomerate)
VALUES (10, 1, 1, 500, 'OMNICORP'),
       (8, 2, 2, 350, 'GENERIC_INC'),
       (12, 3, 3, 600, 'OMNICORP'),
       (7, 4, 4, 250, 'GENERIC_INC');

INSERT INTO consultant_stats(id, player_match_stats_id, times_used, type)
VALUES (1, 1, 2, 'MEDIA_ADVISOR'),
       (2, 2, 3, 'CORPORATE_LAWYER'),
       (3, 2, 2, 'DEAL_MAKER'),
       (4, 2, 1, 'MEDIA_ADVISOR'),
       (5, 3, 3, 'MEDIA_ADVISOR'),
       (6, 4, 1, 'CORPORATE_LAWYER');

INSERT INTO friendship(id, friend_id, user_id, since)
VALUES (1, 1, 2, '2023-01-02 00:00:02'),
       (2, 2, 1, '2023-01-01 00:00:01'),
       (3, 1, 3, '2023-01-01 00:00:01'),
       (4, 3, 1, '2023-04-01 00:00:01'),
       (5, 2, 4, '2023-04-01 00:00:01'),
       (6, 4, 2, '2023-05-01 00:00:02'),
       (7, 5, 4, '2023-05-01 00:00:01'),
       (8, 4, 5, '2023-05-01 00:00:01'),
       (9, 6, 4, '2023-05-01 00:00:01'),
       (10, 4, 6, '2023-05-01 00:00:01'),
       (11, 7, 4, '2023-06-01 00:00:01'),
       (12, 4, 7, '2023-06-01 00:00:01'),
       (13, 8, 4, '2023-06-01 00:00:01'),
       (14, 4, 8, '2023-06-01 00:00:01'),
       (15, 9, 4, '2023-07-01 00:00:01'),
       (16, 4, 9, '2023-07-01 00:00:01'),
       (17, 10, 1, '2023-07-01 00:00:01'),
       (18, 1, 10, '2023-07-01 00:00:01');

INSERT INTO friendship_requests(id, sender_id, receiver_id, sent_at)
VALUES (1, 2, 3, '2023-01-01 00:00:01'),
       (2, 1, 4, '2023-01-01 00:00:01');
