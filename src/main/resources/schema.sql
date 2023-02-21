create table if not exists job_offer (
    name_                 varchar(255),
    contact_date_         varchar(64),
    company_              varchar(255),
    foreign_              varchar(64),
    company_type_         varchar(64),
    company_offer_        varchar(4000),
    job_offer_description_ varchar(10000)
    );

create table if not exists company_offer (
    company_name_          varchar(255),
    daily_rate_            varchar(255),
    short_description_      varchar(4000),
    contact_                 varchar(1000),
    address_                 varchar(400),
    location_               varchar(64),
    atmoskop_description_   varchar(4000),
    company_type_           varchar(64),
    popularity_             varchar(64)
    );