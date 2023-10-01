INSERT INTO tb_user (first_name, last_name, birthday, login, email, password, phone) VALUES ('Bob', 'Falcon', TIMESTAMP WITH TIME ZONE '2005-12-12T13:00:00Z', 'bob_king3242', 'bob@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', '+558123943232');
INSERT INTO tb_user (first_name, last_name, birthday, login, email, password, phone) VALUES ('Ana', 'Brás', TIMESTAMP WITH TIME ZONE '1998-02-22T22:19:00Z', 'ana_king2423', 'ana@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG',  '+5581228329332');

INSERT INTO tb_role (authority) VALUES ('ROLE_USER');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);