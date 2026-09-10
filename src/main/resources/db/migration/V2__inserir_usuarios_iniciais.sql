INSERT INTO T_CLYVO_PESSOA (nome, cpf, data_nascimento, email_pessoal)
VALUES ('Administrador Clyvo', '529.982.247-25', DATE '1995-05-05', 'admin@clyvo.com.br');

INSERT INTO T_CLYVO_PESSOA (nome, cpf, data_nascimento, email_pessoal)
VALUES ('Usuario Clyvo', '111.222.333-44', DATE '1998-08-10', 'usuario@clyvo.com.br');

INSERT INTO T_CLYVO_USUARIO (fk_pessoa, username, senha, permissao, data_criacao)
VALUES (
    (SELECT id FROM T_CLYVO_PESSOA WHERE email_pessoal = 'admin@clyvo.com.br'),
    'admin',
    '$2a$10$0BhZzH8hG2FEW6qhs9UpV.XlaRPf.ODWDDevV67y3TzXeDzUUaRTK',
    'ADMIN',
    DATE '2026-01-01'
);

INSERT INTO T_CLYVO_USUARIO (fk_pessoa, username, senha, permissao, data_criacao)
VALUES (
    (SELECT id FROM T_CLYVO_PESSOA WHERE email_pessoal = 'usuario@clyvo.com.br'),
    'usuario',
    '$2a$10$0BhZzH8hG2FEW6qhs9UpV.XlaRPf.ODWDDevV67y3TzXeDzUUaRTK',
    'USER',
    DATE '2026-01-01'
);