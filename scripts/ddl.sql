CREATE database tracking_cms_reborn;
CREATE USER 'tracking_cms_reborn'@'localhost' IDENTIFIED BY '123123a@';
CREATE USER 'tracking_cms_reborn'@'%' IDENTIFIED BY '123123a@';
GRANT ALL PRIVILEGES ON tracking_cms_reborn.* TO 'tracking_cms_reborn'@'localhost';
GRANT ALL PRIVILEGES ON tracking_cms_reborn.* TO 'tracking_cms_reborn'@'%';
flush privileges;

create table `authorization_matrix` (
    `username` varchar(50),
    `roles` text,
    `universes` text,
    `created_at` datetime,
    `updated_at` datetime
);

create table `role_api_authorization_matrix` (
    `role` varchar(50),
    `method` varchar(50),
    `url` varchar(512),
    `created_at` datetime default current_timestamp,
    `updated_at` datetime,
    primary key (role, method, url)
);

create table `universe` (
    `code` varchar(50) primary key,
    `description` text,
    `created_at` datetime,
    `updated_at` datetime
);

create table `action` (
    `name` varchar(50) primary key,
    `created_at` datetime,
    `updated_at` datetime
);

create table `event_src` (
    `name` varchar(50) primary key,
    `created_at` datetime,
    `updated_at` datetime
);

create table `object_type` (
    `name` varchar(50) primary key,
    `created_at` datetime,
    `updated_at` datetime
);

create table `event_src_obj_type_action_map` (
    `id` varchar(50) primary key,
    `event_src` varchar(50),
    `object_type` varchar(50),
    `action` varchar(50),
    `created_at` datetime,
    `updated_at` datetime,
    constraint event_src_fk foreign key(`event_src`) references `event_src`(`name`),
    constraint object_type_fk foreign key(`object_type`) references `object_type`(`name`),
    constraint action_fk foreign key(`action`) references `action`(`name`)
);

create table `tracking_event` (
    `id` varchar(50) primary key,
    `event_src` varchar(50),
    `object_id` varchar(50),
    `action` varchar(50),
    `status` varchar(50),
    `in_transaction` varchar(50),
    `created_at` datetime,
    `updated_at` datetime
);

create table `tracking_object` (
    `id` varchar(50) primary key,
    `universe` varchar(50),
    `parent_id` varchar(50),
    `object_type` varchar(50),
    `name` varchar(100),
    `description` text,
    `image` varchar(250),
    `status` varchar(50),
    `in_transaction` varchar(50),
    `created_at` datetime,
    `updated_at` datetime
);

create table `staged_info` (
    `type` varchar(50),
    `value` varchar(255),
    `created_at` datetime,
    `updated_at` datetime,
    primary key (`type`, `value`)
);

create table `transaction` (
    `id` varchar(50) primary key,
    `elements` text,
    `universe` varchar(50),
    `created_by` varchar(50),
    `approved_by` varchar(50),
    `status` varchar(50),
    `created_at` datetime,
    `updated_at` datetime
);