-- 1) Usuários iniciais (senha "password" criptografada com BCrypt)
INSERT INTO users (
  username, password, full_name, email
) VALUES
  ('joao23', '$2a$10$DowJonesBcryptExampleHash', 'João Vitor', 'joao@gmail.com'),
  ('mariaass',   '$2a$10$DowJonesBcryptExampleHash', 'Maria Clara',   'maria@gmail.com'),
  ('carlos123', '$2a$10$DowJonesBcryptExampleHash', 'Carlos Gabriel', 'carlos@gmail.com'),
  ('julias', '$2a$10$DowJonesBcryptExampleHash', 'Julia Santos', 'julia@gmail.com'),
  ('henrique12', '$2a$10$DowJonesBcryptExampleHash', 'Henrique Silva', 'henrique@gmail.com'),
  ('heitordib', '$2a$10$DowJonesBcryptExampleHash', 'Heitor Dib', 'heitor@gmail.com');

-- 2) Papéis (roles)
INSERT INTO user_roles (user_id, role) VALUES
  (1, 'ROLE_ADVISOR'),
  (2, 'ROLE_ADVISOR'),
  (3, 'ROLE_ADVISOR'),
  (4, 'ROLE_INVESTOR'),
  (5, 'ROLE_ADVISOR'),
  (6, 'ROLE_INVESTOR');

-- 3) Perfil de investidor
INSERT INTO tb_investors (
  name, capital_available, risk_appetite, user_id
) VALUES
  ('Julia Santos',      30000.0, 'LOW',    4),
  ('Heitor Dib',        75000.0, 'HIGH',   6);

-- 4) Perfil de assessor
INSERT INTO advisors (
  full_name, bio, hourly_rate, user_id
) VALUES
  ('João Vitor',              'Especialista em renda variável',     180.0, 1),
  ('Maria Clara',          	  'Assessora com 10 anos de experiência', 200.0, 2),
  ('Carlos Gabriel',          'Consultoria financeira personalizada',220.0, 3),
  ('Henrique Silva',          'Planejamento patrimonial',           160.0, 5);

-- 5) Detalhes do assessor
INSERT INTO advisor_certifications (advisor_id, certification) VALUES
  (1, 'CPA-10'),
  (1, 'CGP'),
  (2, 'CPA-10'),
  (2, 'CPA-20'),
  (2, 'CGP'),
  (3, 'CGP'),
  (4, 'CFA'),
  (4, 'CGP');

INSERT INTO advisor_specialties (advisor_id, specialty) VALUES
  (1, 'Ações'),
  (2, 'Fundos Imobiliários'),
  (2, 'Renda Fixa'),
  (3, 'Renda Fixa'),
  (4, 'Planejamento Financeiro');
