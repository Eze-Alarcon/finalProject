create table public.categories
(
    category_id   smallint    not null
        constraint pk_categories
            primary key,
    category_name varchar(15) not null,
    description   text,
    picture       bytea
);

alter table public.categories
    owner to ezequiel;

create table public.customers
(
    customer_id   varchar(5)  not null
        constraint pk_customers
            primary key,
    company_name  varchar(40) not null,
    contact_name  varchar(30),
    contact_title varchar(30),
    address       varchar(60),
    city          varchar(15),
    region        varchar(15),
    postal_code   varchar(10),
    country       varchar(15),
    phone         varchar(24),
    fax           varchar(24)
);

alter table public.customers
    owner to ezequiel;

create table public.employees
(
    employee_id       smallint    not null
        constraint pk_employees
            primary key,
    last_name         varchar(20) not null,
    first_name        varchar(10) not null,
    title             varchar(30),
    title_of_courtesy varchar(25),
    birth_date        date,
    hire_date         date,
    address           varchar(60),
    city              varchar(15),
    region            varchar(15),
    postal_code       varchar(10),
    country           varchar(15),
    home_phone        varchar(24),
    extension         varchar(4),
    photo             bytea,
    notes             text,
    reports_to        smallint
        constraint fk_employees_employees
            references public.employees,
    photo_path        varchar(255)
);

alter table public.employees
    owner to ezequiel;

create table public.region
(
    region_id          smallint    not null
        constraint pk_region
            primary key,
    region_description varchar(60) not null
);

alter table public.region
    owner to ezequiel;

create table public.shippers
(
    shipper_id   smallint    not null
        constraint pk_shippers
            primary key,
    company_name varchar(40) not null,
    phone        varchar(24)
);

alter table public.shippers
    owner to ezequiel;

create table public.orders
(
    order_id         smallint not null
        constraint pk_orders
            primary key,
    customer_id      varchar(5)
        constraint fk_orders_customers
            references public.customers,
    employee_id      smallint
        constraint fk_orders_employees
            references public.employees,
    order_date       date,
    required_date    date,
    shipped_date     date,
    ship_via         smallint
        constraint fk_orders_shippers
            references public.shippers,
    freight          real,
    ship_name        varchar(40),
    ship_address     varchar(60),
    ship_city        varchar(15),
    ship_region      varchar(15),
    ship_postal_code varchar(10),
    ship_country     varchar(15)
);

alter table public.orders
    owner to ezequiel;

create table public.suppliers
(
    supplier_id   smallint    not null
        constraint pk_suppliers
            primary key,
    company_name  varchar(40) not null,
    contact_name  varchar(30),
    contact_title varchar(30),
    address       varchar(60),
    city          varchar(15),
    region        varchar(15),
    postal_code   varchar(10),
    country       varchar(15),
    phone         varchar(24),
    fax           varchar(24),
    homepage      text
);

alter table public.suppliers
    owner to ezequiel;

create table public.products
(
    product_id        smallint    not null
        constraint pk_products
            primary key,
    product_name      varchar(40) not null,
    supplier_id       smallint
        constraint fk_products_suppliers
            references public.suppliers,
    category_id       smallint
        constraint fk_products_categories
            references public.categories,
    quantity_per_unit varchar(20),
    unit_price        real,
    units_in_stock    smallint,
    units_on_order    smallint,
    reorder_level     smallint,
    discontinued      integer     not null
);

alter table public.products
    owner to ezequiel;

create table public.order_details
(
    order_id   smallint not null
        constraint fk_order_details_orders
            references public.orders,
    product_id smallint not null
        constraint fk_order_details_products
            references public.products,
    unit_price real     not null,
    quantity   smallint not null,
    discount   real     not null,
    constraint pk_order_details
        primary key (order_id, product_id)
);

alter table public.order_details
    owner to ezequiel;

create table public.territories
(
    territory_id          varchar(20) not null
        constraint pk_territories
            primary key,
    territory_description varchar(60) not null,
    region_id             smallint    not null
        constraint fk_territories_region
            references public.region
);

alter table public.territories
    owner to ezequiel;

create table public.employee_territories
(
    employee_id  smallint    not null
        constraint fk_employee_territories_employees
            references public.employees,
    territory_id varchar(20) not null
        constraint fk_employee_territories_territories
            references public.territories,
    constraint pk_employee_territories
        primary key (employee_id, territory_id)
);

alter table public.employee_territories
    owner to ezequiel;

create table public.us_states
(
    state_id     smallint not null
        constraint pk_usstates
            primary key,
    state_name   varchar(100),
    state_abbr   varchar(2),
    state_region varchar(50)
);

alter table public.us_states
    owner to ezequiel;

