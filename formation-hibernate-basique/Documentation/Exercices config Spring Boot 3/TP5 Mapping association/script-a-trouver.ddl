create sequence test.declaration_seq start with 1 increment by 50;
create sequence test.hib_seq start with 1 increment by 1;
create sequence test.indice_seq start with 1 increment by 50;
create sequence test.secteur_seq start with 1 increment by 50;

    create table test.declaration (
       id integer not null,
        date timestamp(6),
        montant float(53),
        entreprise_id integer,
        primary key (id)
    );

    create table test.entreprise (
       id integer not null,
        rue varchar(255),
        numero varchar(255),
        pays varchar(255),
        type_voie varchar(255),
        ville varchar(255),
        date_creation date,
        denomination varchar(255),
        forme_juridique varchar(255),
        siren varchar(9) not null,
        telephone varchar(10),
        secteur_id integer,
        primary key (id)
    );

    create table test.indice (
       id integer not null,
        derniere_maj timestamp(6) with time zone,
        valeur float(53),
        secteur_id integer,
        primary key (id)
    );

    create table test.indice_annuel (
       year timestamp(6),
        id integer not null,
        primary key (id)
    );

    create table test.indice_mensuel (
       month timestamp(6),
        id integer not null,
        primary key (id)
    );

    create table test.secteur (
       id integer not null,
        code_naf varchar(255),
        libelle_nomenclature varchar(255),
        primary key (id)
    );

    alter table if exists test.entreprise 
       add constraint UK_tq34gg6ld568rgypt9dgkes3t unique (siren);

    alter table if exists test.declaration 
       add constraint FKci8f3vilkcfbmw19i7x3pb6ue 
       foreign key (entreprise_id) 
       references test.entreprise;

    alter table if exists test.entreprise 
       add constraint FKlp02w8bopb6racr9s38rl3uno 
       foreign key (secteur_id) 
       references test.secteur;

    alter table if exists test.indice 
       add constraint FKlyyg9jn51r8b14vurdx7mc65q 
       foreign key (secteur_id) 
       references test.secteur;

    alter table if exists test.indice_annuel 
       add constraint FKbwmkyste1xpdlshjqytpxk8hb 
       foreign key (id) 
       references test.indice;

    alter table if exists test.indice_mensuel 
       add constraint FKcm47lkvga8a03gm67fgyxwb39 
       foreign key (id) 
       references test.indice;
