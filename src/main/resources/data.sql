--Insert two authorities: admin and user
INSERT INTO authorities(id, name)
VALUES (1, 'ADMIN');
INSERT INTO authorities(id, name)
VALUES (2, 'USER');

-- One admin user, named admin1 with password 4dm1n and authority admin
-- one user named user with password user and authority user
-- One user named user1 with password 0wn3r and authority user
INSERT INTO users(id, email, username, password, authority, first_seen, last_seen, picture)
VALUES (1, 'a@example.com', 'admin1', '$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS', 1, null, null,
        null),
       (2, 'b@example.com', 'user', '$2a$10$dq4k1SV8hd8mzkD2aC/e3uu4rPFHHLN8Lw7WGxVqViTFmUlDbxQX2', 2, null, null,
        null),
       (3, 'c@example.com', 'user1', '$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e', 2, null, null,
        null);
