CREATE TABLE IF NOT EXISTS School
(
    Id SERIAL PRIMARY KEY,
    Name CHAR(30),
    Address CHAR(50)
);

CREATE TABLE IF NOT EXISTS Client
(
    Id SERIAL PRIMARY KEY,
    Name CHAR(30),
    Nickname CHAR(20),
    Sex CHAR(1),
    SchoolId INTEGER,
    FOREIGN KEY (SchoolId) REFERENCES School(Id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS Friend
(
    FirstClient INTEGER,
    SecondCLient INTEGER,
    PRIMARY KEY (FirstClient, SecondCLient),
    FOREIGN KEY (FirstClient) REFERENCES Client(Id) ON DELETE CASCADE,
    FOREIGN KEY (SecondCLient) REFERENCES Client(Id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Post
(
    Id SERIAL PRIMARY KEY,
    ClientId INTEGER,
    Date TIMESTAMP,
    Text CHAR(1024),
    FOREIGN KEY (ClientId) REFERENCES Client(Id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS PrivateMessages
(
    Id SERIAL PRIMARY KEY,
    SenderId INTEGER,
    RecipientId INTEGER,
    Date TIMESTAMP,
    Text CHAR(1024),
    FOREIGN KEY (SenderId) REFERENCES Client(Id) ON DELETE SET NULL,
    FOREIGN KEY (RecipientId) REFERENCES Client(Id) ON DELETE SET NULL
)
