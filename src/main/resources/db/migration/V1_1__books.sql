create table  IF NOT EXISTS books(
    id INT NOT NULL AUTO_INCREMENT,
    title char,
    writter char,
    publisher char,
    price INT,
    PRIMARY KEY (id)
);