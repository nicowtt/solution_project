
CREATE SEQUENCE complain.complainuser_id_seq;

CREATE TABLE complain.complainUser (
                                       id INTEGER NOT NULL DEFAULT nextval('complain.complainuser_id_seq'),
                                       name VARCHAR(50) NOT NULL,
                                       firstName VARCHAR(50) NOT NULL,
                                       email VARCHAR(100) NOT NULL,
                                       password VARCHAR NOT NULL,
                                       creationDate TIMESTAMP NOT NULL,
                                       popularity INTEGER NOT NULL,
                                       CONSTRAINT complainuser_pk PRIMARY KEY (id)
);


ALTER SEQUENCE complain.complainuser_id_seq OWNED BY complain.complainUser.id;

CREATE SEQUENCE complain.theme_id_seq;

CREATE TABLE complain.theme (
                                id INTEGER NOT NULL DEFAULT nextval('complain.theme_id_seq'),
                                complainUser_id INTEGER NOT NULL,
                                name VARCHAR NOT NULL,
                                creationDate TIMESTAMP NOT NULL,
                                popularity INTEGER NOT NULL,
                                nbrRequest INTEGER NOT NULL,
                                CONSTRAINT theme_pk PRIMARY KEY (id)
);


ALTER SEQUENCE complain.theme_id_seq OWNED BY complain.theme.id;

CREATE SEQUENCE complain.complainrequest_id_seq;

CREATE TABLE complain.complainRequest (
                                          id INTEGER NOT NULL DEFAULT nextval('complain.complainrequest_id_seq'),
                                          request VARCHAR(500) NOT NULL,
                                          complainUser_id INTEGER NOT NULL,
                                          creationDate TIMESTAMP NOT NULL,
                                          popularity INTEGER NOT NULL,
                                          nbrResponse INTEGER NOT NULL,
                                          theme_id INTEGER NOT NULL,
                                          CONSTRAINT complainrequest_pk PRIMARY KEY (id)
);


ALTER SEQUENCE complain.complainrequest_id_seq OWNED BY complain.complainRequest.id;

CREATE SEQUENCE complain.complainresponse_id_seq;

CREATE TABLE complain.complainResponse (
                                           id INTEGER NOT NULL DEFAULT nextval('complain.complainresponse_id_seq'),
                                           response VARCHAR(500) NOT NULL,
                                           creationDate TIMESTAMP NOT NULL,
                                           popularity INTEGER NOT NULL,
                                           complainUser_id INTEGER NOT NULL,
                                           complainRequest_id INTEGER NOT NULL,
                                           CONSTRAINT complainresponse_pk PRIMARY KEY (id)
);


ALTER SEQUENCE complain.complainresponse_id_seq OWNED BY complain.complainResponse.id;

ALTER TABLE complain.theme ADD CONSTRAINT complainuser_theme_fk
    FOREIGN KEY (complainUser_id)
        REFERENCES complain.complainUser (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE complain.complainRequest ADD CONSTRAINT complainuser_complainrequest_fk
    FOREIGN KEY (complainUser_id)
        REFERENCES complain.complainUser (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE complain.complainResponse ADD CONSTRAINT complainuser_complainresponse_fk
    FOREIGN KEY (complainUser_id)
        REFERENCES complain.complainUser (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE complain.complainRequest ADD CONSTRAINT theme_complainrequest_fk
    FOREIGN KEY (theme_id)
        REFERENCES complain.theme (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
        NOT DEFERRABLE;

ALTER TABLE complain.complainResponse ADD CONSTRAINT complainrequest_complainresponse_fk
    FOREIGN KEY (complainRequest_id)
        REFERENCES complain.complainRequest (id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
        NOT DEFERRABLE;