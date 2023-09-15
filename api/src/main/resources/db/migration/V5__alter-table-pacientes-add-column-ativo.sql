alter table pacientes add ativo tinyint;
update medicos set ativo =1;
ALTER TABLE medicos
MODIFY ativo tinyint NOT NULL;