-- http://cljson.com/
-- https://www.convertjson.com/json-to-sql.htm
CREATE TABLE movies(
    plot      VARCHAR(781) NOT NULL
    ,director  VARCHAR(120) NOT NULL
    ,genres    VARCHAR(117) NOT NULL
    ,title     VARCHAR(124) NOT NULL
    ,year      INTEGER  NOT NULL
    ,actors    VARCHAR(157) NOT NULL
    ,id        INTEGER  NOT NULL PRIMARY KEY
    ,runtime   INTEGER  NOT NULL
    ,posterUrl VARCHAR(781) NOT NULL
);