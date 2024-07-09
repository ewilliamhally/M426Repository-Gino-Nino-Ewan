INSERT INTO User (firstname, lastname, email,password,role) VALUES ('root', 'root', 'root.trip@example.com', '$2a$10$ffMwMgDYdIQo9svf56rAEOZRgIV8Ouvm2XtQzRGME7qrtk9d0eny2', 'ADMIN'),
                                                                    ('Nino', 'Siegrist', 'nino.siegrist@gmail.com', '$2a$10$EXO8KC7hFr0aaM/jm9DjhOLc/Poduw7Rf3LLlj5.GaEY.Tmu0umH6', 'ADMIN');

INSERT INTO trips (location, image, name, description, user_id) VALUES
                                                                    ('Zurich, Switzerland', 0x89504E470D0A1A0A0000000D49484452000000010000000108020000009077250E0000000B49444154789C63606060200000028A0105A9F5740000000049454E44AE426082, 'Zurich Trip', 'Test description', 1),
                                                                    ('Bern, Switzerland', 0x89504E470D0A1A0A0000000D49484452000000010000000108020000009077250E0000000B49444154789C63606060200000028A0105A9F5740000000049454E44AE426082, 'Bern Adventure', 'Test description', 2),
                                                                    ('Geneva, Switzerland', 0x89504E470D0A1A0A0000000D49484452000000010000000108020000009077250E0000000B49444154789C63606060200000028A0105A9F5740000000049454E44AE426082, 'Geneva Excursion', 'Test description', 1),
                                                                    ('Basel, Switzerland', 0x89504E470D0A1A0A0000000D49484452000000010000000108020000009077250E0000000B49444154789C63606060200000028A0105A9F5740000000049454E44AE426082, 'Basel Tour', 'Test description', 2),
                                                                    ('Lucerne, Switzerland', 0x89504E470D0A1A0A0000000D49484452000000010000000108020000009077250E0000000B49444154789C63606060200000028A0105A9F5740000000049454E44AE426082, 'Lucerne Journey', 'Test description', 1);

INSERT INTO user_trips (user_id, trip_id) VALUES (1, 1), (1, 2), (2, 1);