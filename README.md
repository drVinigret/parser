MySQL migration:

CREATE TABLE IF NOT EXISTS domain
(
    id          BIGINT(20)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
    domain_url  VARCHAR(255) NOT NULL,
    parseStatus VARCHAR(255) NOT NULL
);

create table domain_review
(
    id            bigint auto_increment primary key,
    domain_id     bigint       null,
    name          varchar(255) not null,
    review_counts bigint       not null,
    rating        double       not null
);
