CREATE TABLE OAUTH2_AUTHORIZATION (
                                      id varchar(100) NOT NULL,
                                      registered_client_id varchar(100) NOT NULL,
                                      principal_name varchar(200) NOT NULL,
                                      authorization_grant_type varchar(100) NOT NULL,
                                      authorized_scopes varchar(1000) DEFAULT NULL,
                                      attributes blob DEFAULT NULL,
                                      state varchar(500) DEFAULT NULL,
                                      authorization_code_value blob DEFAULT NULL,
                                      authorization_code_issued_at timestamp DEFAULT NULL,
                                      authorization_code_expires_at timestamp DEFAULT NULL,
                                      authorization_code_metadata blob DEFAULT NULL,
                                      access_token_value blob DEFAULT NULL,
                                      access_token_issued_at timestamp DEFAULT NULL,
                                      access_token_expires_at timestamp DEFAULT NULL,
                                      access_token_metadata blob DEFAULT NULL,
                                      access_token_type varchar(100) DEFAULT NULL,
                                      access_token_scopes varchar(1000) DEFAULT NULL,
                                      oidc_id_token_value blob DEFAULT NULL,
                                      oidc_id_token_issued_at timestamp DEFAULT NULL,
                                      oidc_id_token_expires_at timestamp DEFAULT NULL,
                                      oidc_id_token_metadata blob DEFAULT NULL,
                                      refresh_token_value blob DEFAULT NULL,
                                      refresh_token_issued_at timestamp DEFAULT NULL,
                                      refresh_token_expires_at timestamp DEFAULT NULL,
                                      refresh_token_metadata blob DEFAULT NULL,
                                      user_code_value blob DEFAULT NULL,
                                      user_code_issued_at timestamp DEFAULT NULL,
                                      user_code_expires_at timestamp DEFAULT NULL,
                                      user_code_metadata blob DEFAULT NULL,
                                      device_code_value blob DEFAULT NULL,
                                      device_code_issued_at timestamp DEFAULT NULL,
                                      device_code_expires_at timestamp DEFAULT NULL,
                                      device_code_metadata blob DEFAULT NULL,
                                      PRIMARY KEY (id)
);


CREATE TABLE OAUTH2_REGISTERED_CLIENT (
                                          id varchar(255) NOT NULL,
                                          client_id varchar(100) NOT NULL,
                                          client_id_issued_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                          client_secret varchar(200) DEFAULT NULL,
                                          client_secret_expires_at TIMESTAMP NULL DEFAULT NULL,
                                          client_name varchar(200) NOT NULL,
                                          client_authentication_methods varchar(1000) NOT NULL,
                                          authorization_grant_types varchar(1000) NOT NULL,
                                          redirect_uris varchar(1000) DEFAULT NULL,
                                          scopes varchar(1000) NOT NULL,
                                          client_settings varchar(2000) NOT NULL,
                                          token_settings varchar(2000) NOT NULL,
                                          PRIMARY KEY (id)
);


create table USER
(
    ID        bigint(11) auto_increment,
    EMAIL varchar(50) not null,
    NICK_NAME varchar(50) not null,
    PASSWORD  varchar(50) not null,
    constraint USER_pk
        primary key (ID)
);

create table ROLE
(
    ID   bigint(11) auto_increment,
    NAME varchar(50) not null,
    TYPE varchar(50) not null,
    constraint ROLE_pk
        primary key (ID)
);

create table USER_ROLES
(
    USER_ID bigint(11) null,
    ROLE_ID bigint(11) null,
    constraint USER_ROLES_ROLE_ID_fk
        foreign key (ROLE_ID) references ROLE (ID),
    constraint USER_ROLES_USER_ID_fk
        foreign key (USER_ID) references USER (ID)
);