
    alter table vgr_tage_advertisement 
        drop constraint FK2CDEF1EF79C2D1DB;

    alter table vgr_tage_advertisement 
        drop constraint FK2CDEF1EFEA7030E5;

    alter table vgr_tage_advertisement 
        drop constraint FK2CDEF1EFC70BAE6;

    alter table vgr_tage_advertisement 
        drop constraint FK2CDEF1EFFBD5FB26;

    alter table vgr_tage_category 
        drop constraint FK1413D2D42C2EBFBA;

    alter table vgr_tage_photoset 
        drop constraint FKC51D8EC695585904;

    alter table vgr_tage_photoset 
        drop constraint FKC51D8EC65D8FA15F;

    alter table vgr_tage_request 
        drop constraint FKB578C9F979C2D1DB;

    alter table vgr_tage_request 
        drop constraint FKB578C9F9C70BAE6;

    alter table vgr_tage_request 
        drop constraint FKB578C9F9FBD5FB26;

    drop table if exists vgr_tage_advertisement cascade;

    drop table if exists vgr_tage_category cascade;

    drop table if exists vgr_tage_person cascade;

    drop table if exists vgr_tage_photo cascade;

    drop table if exists vgr_tage_photoset cascade;

    drop table if exists vgr_tage_request cascade;

    drop table if exists vgr_tage_unit cascade;

    drop table if exists vgr_tage_visit cascade;

    drop sequence vgr_tage_hibernate_sequence;

    create table vgr_tage_advertisement (
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

    create table vgr_tage_category (
        id int4 not null,
        title varchar(255) not null,
        parent_id int4,
        primary key (id)
    );

    create table vgr_tage_person (
        id int4 not null,
        email varchar(255) not null,
        name varchar(255) not null,
        phone varchar(255) not null,
        userId varchar(255),
        primary key (id)
    );

    create table vgr_tage_photo (
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

    create table vgr_tage_photoset (
        vgr_tage_advertisement_id int4 not null,
        photos_id int4 not null,
        unique (photos_id),
        primary key (vgr_tage_advertisement_id, photos_id)
    );

    create table vgr_tage_request (
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

    create table vgr_tage_unit (
        id int4 not null,
        name varchar(255),
        primary key (id)
    );

    create table vgr_tage_visit (
        id int4 not null,
        lastVisit timestamp not null,
        userId varchar(255) not null,
        visitCount int4 not null,
        primary key (id)
    );

    alter table vgr_tage_advertisement 
        add constraint FK2CDEF1EF79C2D1DB 
        foreign key (contact_id) 
        references vgr_tage_person;

    alter table vgr_tage_advertisement 
        add constraint FK2CDEF1EFEA7030E5 
        foreign key (booker_id) 
        references vgr_tage_person;

    alter table vgr_tage_advertisement 
        add constraint FK2CDEF1EFC70BAE6 
        foreign key (category_id) 
        references vgr_tage_category;

    alter table vgr_tage_advertisement 
        add constraint FK2CDEF1EFFBD5FB26 
        foreign key (unit_id) 
        references vgr_tage_unit;

    alter table vgr_tage_category 
        add constraint FK1413D2D42C2EBFBA 
        foreign key (parent_id) 
        references vgr_tage_category;

    alter table vgr_tage_photoset 
        add constraint FKC51D8EC695585904 
        foreign key (vgr_tage_advertisement_id) 
        references vgr_tage_advertisement;

    alter table vgr_tage_photoset 
        add constraint FKC51D8EC65D8FA15F 
        foreign key (photos_id) 
        references vgr_tage_photo;

    alter table vgr_tage_request 
        add constraint FKB578C9F979C2D1DB 
        foreign key (contact_id) 
        references vgr_tage_person;

    alter table vgr_tage_request 
        add constraint FKB578C9F9C70BAE6 
        foreign key (category_id) 
        references vgr_tage_category;

    alter table vgr_tage_request 
        add constraint FKB578C9F9FBD5FB26 
        foreign key (unit_id) 
        references vgr_tage_unit;

    create sequence vgr_tage_hibernate_sequence;
