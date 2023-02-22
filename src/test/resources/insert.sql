insert into test_user (id, email, name, created)
values ('e592ad12-a48f-444e-9318-75f75e51600f', 'user@gmail.com', 'name', '2023-02-19 17:55:12.570653');

insert into test_shortener (id, user_id, short_url, url, created, disabled)
values ('e592ad12-a48f-444e-9318-75f75e51601f', 'e592ad12-a48f-444e-9318-75f75e51600f',
        'qwerty', 'https://www.google.com/', '2023-02-19 17:57:12.570653', false);