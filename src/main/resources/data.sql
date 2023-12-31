INSERT INTO authorities(id, name)
VALUES (1, 'ADMIN'),
       (2, 'USER');

INSERT INTO users(authority, first_seen, id, last_seen, email, username, password, picture)
VALUES (1, '2020-01-01', 1, '2023-01-01', 'johndoe@example.com', 'JohnDoe', 'password', null),
       (2, '2020-02-01', 2, '2023-02-01', 'jane@example.com', 'Jane', 'password', null),
       (2, '2020-03-01', 3, '2023-03-01', 'samsmith@example.com', 'SamSmith', 'password', null),
       (2, '2023-04-01', 4, '2023-12-31', 'octavio@example.com', 'Octavio', 'password', null),
       (2, '2023-05-01', 5, '2023-12-31', 'alice@example.com', 'Alice', 'password', null),
       (2, '2023-06-01', 6, '2023-12-31', 'bob@example.com', 'Bob', 'password', null);

INSERT INTO notification(id, state, sent_at, title, image_url, message)
VALUES (1, 1, '2023-01-01 00:00:00', 'Welcome!', null, 'Thanks for joining us!'),
       (2, 0, '2023-01-02 00:00:00', 'Update!', null, 'New features available.'),
       (3, 1, '2023-04-01 12:00:00', 'Important Update!', null, 'Check out the latest changes.'),
       (4, 0, '2023-05-01 10:00:00', 'Event Reminder', null, 'Dont forget about our upcoming event!');

INSERT INTO users_notifications(notifications_id, user_id)
VALUES (1, 1),
       (2, 1),
       (3, 2),
       (4, 3);

INSERT INTO achievements(id, threshold, name, description, image_url, stat)
VALUES (1, 10, 'Victor', 'Win 10 games', null, 'GAMES_WON'),
       (2, 20, 'Strategist', 'Play 20 hours', null, 'TIME_PLAYED'),
       (3, 15, 'Champion', 'Achieve 15 victories', null, 'GAMES_WON'),
       (4, 30, 'Dedicated Player', 'Play for 30 hours', null, 'TIME_PLAYED');

INSERT INTO match_stats(id, ended_at, started_at, mode)
VALUES (1, '2023-01-01 01:00:00', '2023-01-01 00:00:00', 'NORMAL'),
       (2, '2023-01-02 01:00:00', '2023-01-02 00:00:00', 'QUICK'),
       (3, '2023-04-01 02:00:00', '2023-04-01 00:00:00', 'NORMAL'),
       (4, '2023-05-01 03:00:00', '2023-05-01 00:00:00', 'QUICK');

INSERT INTO player_match_stats(id, match_stats_id, times_infiltrated, times_plotted, times_taken_over, totalvp, result)
VALUES (1, 1, 3, 2, 1, 15, 'WON'),
       (2, 2, 1, 1, 0, 5, 'LOST'),
       (3, 3, 2, 3, 1, 20, 'WON'),
       (4, 4, 0, 2, 1, 10, 'LOST');

INSERT INTO company_player_match_stats(ability_used, id, player_match_stats_id, company_type)
VALUES (5, 1, 1, 'BROADCAST_NETWORK'),
       (3, 2, 2, 'ONLINE_MARKETING'),
       (4, 3, 3, 'ONLINE_MARKETING'),
       (6, 4, 4, 'SOCIAL_MEDIA');

INSERT INTO conglomerate_player_match_stats(agents, id, player_match_stats_id, shares, conglomerate)
VALUES (10, 1, 1, 500, 'OMNICORP'),
       (8, 2, 2, 350, 'GENERIC_INC'),
       (12, 3, 3, 600, 'OMNICORP'),
       (7, 4, 4, 250, 'GENERIC_INC');

INSERT INTO consultant_player_match_stats(id, player_match_stats_id, used, consultant_type)
VALUES (1, 1, 2, 'MEDIA_ADVISOR'),
       (2, 2, 1, 'CORPORATE_LAWYER'),
       (3, 3, 3, 'MEDIA_ADVISOR'),
       (4, 4, 1, 'CORPORATE_LAWYER');

INSERT INTO friendship(friend_id, id, user_id, since)
VALUES (2, 1, 1, '2023-01-01 00:00:01'),
       (1, 2, 2, '2023-01-02 00:00:02'),
       (3, 3, 1, '2023-04-01 00:00:01'),
       (4, 4, 2, '2023-05-01 00:00:02');

INSERT INTO frienship_request(id, receiver_id, sender_id, sent_at)
VALUES (1, 2, 1, '2023-01-01 00:00:01'),
       (2, 1, 2, '2023-01-02 00:00:02'),
       (3, 4, 1, '2023-04-01 00:00:01'),
       (4, 3, 2, '2023-05-01 00:00:02');

INSERT INTO players(id, users)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4);
       