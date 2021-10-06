CREATE TABLE IF NOT EXISTS school
(
    id SERIAL PRIMARY KEY,
    name CHAR(30),
    address CHAR(50)
);

CREATE TABLE IF NOT EXISTS client
(
    id SERIAL PRIMARY KEY,
    name CHAR(30),
    surname CHAR(30),
    nickname CHAR(30),
    sex CHAR(1),
    school_id INTEGER,
    FOREIGN KEY (school_id) REFERENCES school(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS friend
(
    first_client INTEGER,
    second_cLient INTEGER,
    PRIMARY KEY (first_client, second_cLient),
    FOREIGN KEY (first_client) REFERENCES client(id) ON DELETE CASCADE,
    FOREIGN KEY (second_cLient) REFERENCES client(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS post
(
    id SERIAL PRIMARY KEY,
    client_id INTEGER,
    date TIMESTAMP,
    header CHAR(50),
    text CHAR(300),
    FOREIGN KEY (client_id) REFERENCES client(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dialog
(
    id SERIAL PRIMARY KEY,
    name CHAR(30)
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
    Date TIMESTAMP,
    Text CHAR(300),
    FOREIGN KEY (client_id) REFERENCES client(id) ON DELETE SET NULL,
    FOREIGN KEY (dialog_id) REFERENCES dialog(Id) ON DELETE CASCADE
);
