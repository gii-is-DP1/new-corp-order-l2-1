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
        '$2a$12$19.5GDidvCHN4go6jO9dde918jIYONlxe01RgKh5USM3Yd/lFzeI2', null);

INSERT INTO players(id, users)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4);

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
       (4, 'OCTAVIO', '2023-05-01 03:00:00', '2023-05-01 00:00:00', 'QUICK', 2);

INSERT INTO player_match_stats(id, match_stats_id, player_id, times_infiltrated, times_plotted, times_taken_over,
                               total_victory_points, result)
VALUES (1, 1, 4, 3, 2, 1, 15, 'WON'),
       (2, 2, 4, 1, 1, 0, 5, 'LOST'),
       (3, 2, 1, 5, 3, 4, 1, 'TIED'),
       (4, 2, 2, 1, 1, 2, 4, 'TIED'),
       (5, 3, 1, 2, 3, 1, 20, 'WON'),
       (6, 4, 3, 3, 1, 2, 15, 'WON'),
       (7, 4, 2, 0, 2, 1, 10, 'LOST');

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
       (6, 4, 2, '2023-05-01 00:00:02');

INSERT INTO friendship_requests(id, sender_id, receiver_id, sent_at)
VALUES (1, 2, 3, '2023-01-01 00:00:01'),
       (2, 1, 4, '2023-01-01 00:00:01');
