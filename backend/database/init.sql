create table Libri (
    id_libro int primary key,
    titolo varchar(500) not null,
    autore varchar(500) not null,
    anno int,
    genere varchar(500),
    editore varchar(500)
);

create table Utenti (
    id_utente INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome varchar(255) not null,
    cognome varchar(255) not null,
    email varchar(255) unique not null,
    password varchar(255) not null,
    cf varchar(16) unique not null
);

CREATE TABLE libreria (
    id_libreria INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_utente INT not null references Utenti(id_utente) on delete cascade on update cascade,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE libreria_libri (
    id_libreria INT NOT NULL  REFERENCES libreria(id_libreria) on delete cascade on update cascade,
    id_libro INT NOT NULL REFERENCES libri(id_libro) on delete cascade on update cascade,
    PRIMARY KEY (id_libreria, id_libro)
);

create table Libri_Consigliati (
    id_libro int references Libri(id_libro) on delete cascade on update cascade,
    id_utente int DEFAULT 1 references Utenti(id_utente) on delete set default on update cascade,
    id_libro_consigliato int references Libri(id_libro),
    primary key (id_libro, id_utente, id_libro_consigliato)
);

create table Valutazioni_Libri (
    id_libro int references Libri(id_libro) on delete cascade on update cascade,
    id_utente int DEFAULT 1 references Utenti(id_utente) on delete set default on update cascade,
    primary key (id_libro, id_utente),
    edizione varchar(255) not null,
    voto_edizione int check(voto_edizione >= 1 and voto_edizione <= 5) not null,
    stile varchar(255) not null,
    voto_stile int check(voto_stile >= 1 and voto_stile <= 5) not null,
    contenuto varchar(255) not null,
    voto_contenuto int check(voto_contenuto >= 1 and voto_contenuto <= 5) not null,
    gradevolezza varchar(255) not null,
    voto_gradevolezza int check(voto_gradevolezza >= 1 and voto_gradevolezza <= 5) not null,
    originalita varchar(255) not null,
    voto_originalita int check(voto_ordiginalita >= 1 and voto_ordiginalita <= 5) not null,
    voto_medio numeric(3,2)

);

CREATE TABLE libri_inviati (
    client_host VARCHAR(255) NOT NULL,
    id_libro INT NOT NULL,
    PRIMARY KEY (client_host, id_libro),
    FOREIGN KEY (id_libro) REFERENCES libri(id_libro)
);


-- le valutazioni e i commenti sono obbligatori
-- un utente può valutare un libro solo se presente nella sua libreria
-- un utente può consigliare un libro solo se presente nella sua libreria