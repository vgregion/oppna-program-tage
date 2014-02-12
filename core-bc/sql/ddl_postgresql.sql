
    alter table Category 
        drop constraint FK6DD211E2C2EBFBA;

    alter table Request 
        drop constraint FKA4878A6F79C2D1DB;

    alter table Request 
        drop constraint FKA4878A6FC70BAE6;

    alter table Request 
        drop constraint FKA4878A6FFBD5FB26;

    alter table advertisement 
        drop constraint FKF85DD20579C2D1DB;

    alter table advertisement 
        drop constraint FKF85DD205EA7030E5;

    alter table advertisement 
        drop constraint FKF85DD205C70BAE6;

    alter table advertisement 
        drop constraint FKF85DD205FBD5FB26;

    alter table photoset 
        drop constraint FKB40C78F096F7792E;

    alter table photoset 
        drop constraint FKB40C78F05D8FA15F;

    drop table if exists Category cascade;

    drop table if exists Person cascade;

    drop table if exists Photo cascade;

    drop table if exists Request cascade;

    drop table if exists Unit cascade;

    drop table if exists Visit cascade;

    drop table if exists advertisement cascade;

    drop table if exists photoset cascade;

    drop sequence hibernate_sequence;

    create table Category (
        id int4 not null,
        title varchar(255) not null,
        parent_id int4,
        primary key (id)
    );

    create table Person (
        id int4 not null,
        email varchar(255) not null,
        name varchar(255) not null,
        phone varchar(255) not null,
        userId varchar(255),
        primary key (id)
    );

    create table Photo (
        id int4 not null,
        creatorUid varchar(255) not null,
        height int4 not null,
        image oid not null,
        mimeType varchar(255),
        thumbnail oid not null,
        title varchar(255),
        width int4 not null,
        primary key (id)
    );

    create table Request (
        id int4 not null,
        created timestamp not null,
        creatorUid varchar(255) not null,
        description varchar(255) not null,
        status int4 not null,
        title varchar(255) not null,
        category_id int4 not null,
        contact_id int4 not null,
        unit_id int4 not null,
        primary key (id),
        unique (contact_id)
    );

    create table Unit (
        id int4 not null,
        name varchar(255),
        primary key (id)
    );

    create table Visit (
        id int4 not null,
        lastVisit timestamp not null,
        userId varchar(255) not null,
        visitCount int4 not null,
        primary key (id)
    );

    create table advertisement (
        id int4 not null,
        created timestamp not null,
        creatorUid varchar(255) not null,
        description varchar(255) not null,
        displayOption int4 not null,
        pickupAddress varchar(255) not null,
        pickupConditions varchar(255),
        status int4 not null,
        title varchar(255) not null,
        booker_id int4,
        category_id int4 not null,
        contact_id int4 not null,
        unit_id int4 not null,
        primary key (id)
    );

    create table photoset (
        advertisement_id int4 not null,
        photos_id int4 not null,
        unique (photos_id)
    );

    alter table Category 
        add constraint FK6DD211E2C2EBFBA 
        foreign key (parent_id) 
        references Category;

    alter table Request 
        add constraint FKA4878A6F79C2D1DB 
        foreign key (contact_id) 
        references Person;

    alter table Request 
        add constraint FKA4878A6FC70BAE6 
        foreign key (category_id) 
        references Category;

    alter table Request 
        add constraint FKA4878A6FFBD5FB26 
        foreign key (unit_id) 
        references Unit;

    alter table advertisement 
        add constraint FKF85DD20579C2D1DB 
        foreign key (contact_id) 
        references Person;

    alter table advertisement 
        add constraint FKF85DD205EA7030E5 
        foreign key (booker_id) 
        references Person;

    alter table advertisement 
        add constraint FKF85DD205C70BAE6 
        foreign key (category_id) 
        references Category;

    alter table advertisement 
        add constraint FKF85DD205FBD5FB26 
        foreign key (unit_id) 
        references Unit;

    alter table photoset 
        add constraint FKB40C78F096F7792E 
        foreign key (advertisement_id) 
        references advertisement;

    alter table photoset 
        add constraint FKB40C78F05D8FA15F 
        foreign key (photos_id) 
        references Photo;

    create sequence hibernate_sequence;
