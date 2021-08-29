-- flyway.sqlMigrationPrefix=V
-- flyway.sqlMigrationSeparator=__

-- w resources.db.migration tworzymy plik z prefixem: V2__nazwaTegoCoJestDodawane.sql
-- Z każdym plikiem inkrementacja numerka np. V3

INSERT role(name) VALUES ('ROLE_USER');
INSERT role(name) VALUES ('ROLE_ADMIN');