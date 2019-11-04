create database realworld;

alter database realworld set timezone to 'utc';

create table if not exists users
(
    id serial not null
        constraint users_pk
            primary key,
    email text not null,
    password text not null,
    token text,
    username text,
    bio text,
    image text
);

create table if not exists articles
(
    id serial not null
        constraint articles_pk
            primary key,
    slug text not null,
    title text not null,
    description text not null,
    body text not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone,
    author_id integer not null
        constraint articles_users_id_fk
            references users
);

create index if not exists articles_author_id_index
    on articles (author_id);

create unique index if not exists articles_slug_uindex
    on articles (slug);

create unique index if not exists users_email_uindex
    on users (email);

create unique index if not exists users_token_uindex
    on users (token);

create table if not exists comments
(
    id serial not null
        constraint comments_pk
            primary key,
    body text not null,
    created_at timestamp with time zone not null,
    updated_at timestamp with time zone,
    author_id integer not null
        constraint comments_users_id_fk
            references users,
    article_id integer not null
        constraint comments_articles_id_fk
            references articles
);

create index if not exists comments_article_id_index
    on comments (article_id);

create index if not exists comments_author_id_index
    on comments (author_id);

create table if not exists tags
(
    id serial not null
        constraint tags_pk
            primary key,
    name text not null
);

create table if not exists favorited_articles
(
    article_id integer not null
        constraint favorited_articles_pk
            primary key
        constraint favorited_articles_articles_id_fk
            references articles,
    user_id integer not null
        constraint favorited_articles_users_id_fk
            references users
);

create index if not exists favorited_articles_article_id_index
    on favorited_articles (article_id);

create index if not exists favorited_articles_user_id_index
    on favorited_articles (user_id);

create table if not exists article_to_tag
(
    article_id integer not null
        constraint article_to_tag_articles_id_fk
            references articles,
    tag_id integer not null
        constraint article_to_tag_pk
            primary key
        constraint article_to_tag_tags_id_fk
            references tags
);

create table if not exists follows
(
    from_user_id integer not null
        constraint follows_users_id_fk
            references users,
    to_user_id integer not null
        constraint follows_pk
            primary key
        constraint follows_users_id_fk_2
            references users
);
