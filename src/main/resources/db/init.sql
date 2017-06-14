
-- create simple tags table with two entries
CREATE TABLE tags (ID serial NOT NULL PRIMARY KEY, info jsonb NOT NULL);

INSERT INTO tags (info) VALUES ('[{"name": "one", "value": "ENA"},{"name":"two", "value": "DVA"}]');
INSERT INTO tags (info) VALUES ('[{"name": "one", "value": "ENA"},{"name":"three", "value": "TRI"}]');