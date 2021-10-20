CREATE TABLE IF NOT EXISTS school
(
    id SERIAL PRIMARY KEY,
    name CHARACTER VARYING(30) NOT NULL,
    address CHARACTER VARYING(50)
);

CREATE TABLE IF NOT EXISTS client
(
    id SERIAL PRIMARY KEY,
    name CHARACTER VARYING(30) NOT NULL,
    surname CHARACTER VARYING(30) NOT NULL,
    nickname CHARACTER VARYING(30) UNIQUE NOT NULL,
    sex CHARACTER VARYING(1),
    school_id INTEGER,
    FOREIGN KEY (school_id) REFERENCES school(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS friend
(
    first_client_id INTEGER,
    second_cLient_id INTEGER,
    PRIMARY KEY (first_client_id, second_cLient_id),
    FOREIGN KEY (first_client_id) REFERENCES client(id) ON DELETE CASCADE,
    FOREIGN KEY (second_cLient_id) REFERENCES client(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS post
(
    id SERIAL PRIMARY KEY,
    client_id INTEGER,
    date TIMESTAMP NOT NULL,
    header CHARACTER VARYING(50),
    text CHARACTER VARYING(300) NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dialog
(
    id SERIAL PRIMARY KEY,
    name CHARACTER VARYING(30)
);

CREATE TABLE IF NOT EXISTS client_dialog
(
    client_id INTEGER,
    dialog_id INTEGER,
    PRIMARY KEY (client_id, dialog_id),
    FOREIGN KEY (client_id) REFERENCES client(id) ON DELETE CASCADE,
    FOREIGN KEY (dialog_id) REFERENCES dialog(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS message
(
    id SERIAL PRIMARY KEY,
    client_id INTEGER,
    dialog_id INTEGER,
    Date TIMESTAMP NOT NULL,
    Text CHARACTER VARYING(300) NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client(id) ON DELETE SET NULL,
    FOREIGN KEY (dialog_id) REFERENCES dialog(Id) ON DELETE CASCADE
);