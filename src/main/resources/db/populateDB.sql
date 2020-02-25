DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (description, date_time, calories,user_id) VALUES
    ('Breakfast','2020-02-24 10:30',500,100001),
    ('Lunch','2020-02-24 15:45',565,100001),
    ('Dinner','2020-02-24 19:20',300,100000);
