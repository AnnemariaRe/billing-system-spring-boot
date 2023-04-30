create table if not exists public.tariffs
(
    id                  varchar(255) not null
        primary key,
    first_minute_limit  integer,
    first_minute_price  numeric(19, 2),
    fix_price           numeric(19, 2),
    free_minute_limit   integer,
    is_for_clients_free boolean      not null,
    is_incoming_free    boolean      not null,
    name                varchar(255) not null
        constraint uk_lt51ssbtjwb2x833uph2ngfag
            unique,
    next_minute_price   numeric(19, 2)
);

alter table public.tariffs
    owner to test;

create table if not exists public.clients
(
    id           bigserial
        primary key,
    balance      numeric(19, 2) not null,
    phone_number varchar(255)   not null
        constraint uk_bt1ji0od8t2mhp0thot6pod8u
            unique,
    tariff_id    varchar(255)   not null
        constraint fkm1kg1pe0ij97a1c4r9hk0biw1
            references public.tariffs
);

alter table public.clients
    owner to test;

create table if not exists public.reports
(
    id            bigserial
        primary key,
    monetary_unit varchar(255) not null,
    total_cost    numeric(19, 2),
    client_id     bigint       not null
        constraint fkmiqk34gfam6emk63vq844fem2
            references public.clients
);

alter table  public.reports
    owner to test;

create table if not exists public.client_calls
(
    id         bigserial
        primary key,
    call_type  varchar(255) not null,
    cost       numeric(19, 2),
    duration   varchar(255) not null,
    end_time   timestamp    not null,
    start_time timestamp    not null,
    report_id  bigint       not null
        constraint fk32bldqpjd6205vrqan0k9s5ns
            references public.reports
);

alter table public.client_calls
    owner to test;

create table if not exists public.users
(
    id           bigserial
        primary key,
    password     varchar(255) not null,
    phone_number varchar(255) not null
        constraint uk_9q63snka3mdh91as4io72espi
            unique,
    role         varchar(255) not null,
    username     varchar(255) not null
        constraint uk_r43af9ap4edm43mmtq01oddj6
            unique
);

alter table public.users
    owner to test;


INSERT INTO public.tariffs(
    id, first_minute_limit, first_minute_price, fix_price, free_minute_limit, is_for_clients_free, is_incoming_free, name, next_minute_price)
VALUES ('03', null, 1.5, null, null, false, false, 'Perminute', null);

INSERT INTO public.tariffs(
    id, first_minute_limit, first_minute_price, fix_price, free_minute_limit, is_for_clients_free, is_incoming_free, name, next_minute_price)
VALUES ('06', null, null, 100, 300, false, false, 'Unlimited300', 1);

INSERT INTO public.tariffs(
    id, first_minute_limit, first_minute_price, fix_price, free_minute_limit, is_for_clients_free, is_incoming_free, name, next_minute_price)
VALUES ('11', 100, 0.5, null, null, false, true, 'Regular', 1.5);

INSERT INTO public.clients (id, balance, phone_number, tariff_id)
VALUES (-1, 500.0, 79999999999, '11')
ON CONFLICT (phone_number) DO NOTHING;

INSERT INTO public.users(
    id, password, phone_number, role, username)
VALUES (1, '$2a$10$aQFb2GpxLwFx2zhzp4u8auMzcx.AF8SLoTcZE7kMUzi4ATTJMWpPK', 0, 'ROLE_MANAGER', 'manager');

INSERT INTO public.users(
    id, password, phone_number, role, username)
VALUES (2, '$2a$10$gVlo6kKC4CiiUI9Vl54Eg.mCvHmUiVBgxS0XHVcgmUvnklJJavqgm', 79999999999, 'ROLE_ABONENT', 'abonent');