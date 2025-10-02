create table Libri (
    id_libro int primary key,
    titolo varchar(255) not null,
    autore varchar(255) not null,
    anno int,
    genere varchar(255),
    editore varchar(255)
);

create table Utenti (
    id_utente int primary key,
    nome varchar(255) not null,
    cognome varchar(255) not null,
    email varchar(255) unique not null,
    password varchar(255) not null,
    cf varchar(16) unique not null
);

create table Librerie (
    nome varchar(255),
    id_libro int references Libri(id_libro) on delete cascade on update cascade,
    id_utente int references Utenti(id_utente) on delete cascade on update cascade,
    primary key (nome, id_libro, id_utente)
);

create table Libri_Consigliati (
    id_libro int references Libri(id_libro) on delete cascade on update cascade,
    id_utente int DEFAULT 0 references Utenti(id_utente) on delete set default on update cascade,
    id_libro_consigliato int references Libri(id_libro),
    primary key (id_libro, id_utente, id_libro_consigliato)
);

create table Valutazioni_Libri (
    id_libro int references Libri(id_libro) on delete cascade on update cascade,
    id_utente int DEFAULT 0 references Utenti(id_utente) on delete set default on update cascade,
    primary key (id_libro, id_utente),
    edizione varchar(255) not null,
    voto_edizione int check(voto_edizione >= 1 and voto_edizione <= 5) not null,
    stile varchar(255) not null,
    voto_stile int check(voto_stile >= 1 and voto_stile <= 5) not null,
    contenuto varchar(255) not null,
    voto_contenuto int check(voto_contenuto >= 1 and voto_contenuto <= 5) not null,
    gradevolezza varchar(255) not null,
    voto_gradevolezza int check(voto_gradevolezza >= 1 and voto_gradevolezza <= 5) not null,
    ordiginalita varchar(255) not null,
    voto_ordiginalita int check(voto_ordiginalita >= 1 and voto_ordiginalita <= 5) not null

);

-- le valutazioni e i commenti sono obbligatori
-- un utente può valutare un libro solo se presente nella sua libreria
-- un utente può consigliare un libro solo se presente nella sua libreria