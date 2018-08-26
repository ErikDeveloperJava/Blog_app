create database myBlog character set utf8 collate utf8_general_ci;

use myBlog;

create table user(
  id int not null auto_increment primary key ,
  name varchar(255) not null ,
  surname varchar(255) not null ,
  username varchar(255) not null ,
  password varchar(255) not null ,
  img_url varchar(255),
  role varchar(255) not null
)engine InnoDB character set utf8 collate utf8_general_ci;

create table category(
  id int not null auto_increment primary key ,
  name varchar(255) not null
)engine InnoDB character set utf8 collate utf8_general_ci;

create table post(
  id int not null auto_increment primary key ,
  title varchar(255) not null ,
  description text not null ,
  created_date datetime not null ,
  img_url varchar(255) ,
  category_id int not null ,
  foreign key (category_id) references category(id) on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table comment(
  id int not null auto_increment primary key ,
  comment text not null ,
  send_date datetime not null,
  user_id int not null ,
  post_id int not null ,
  parent_id int ,
  foreign key (user_id) references user(id) on delete cascade ,
  foreign key (post_id) references post(id) on delete cascade ,
  foreign key (parent_id) references comment(id) on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table gallery(
  id int not null auto_increment primary key ,
  img_url varchar(255) not null
)engine InnoDB character set utf8 collate utf8_general_ci;

insert into user(name, surname, username, password, img_url, role) VALUES
  ('Admin','Admin','admin','$2a$04$FYzp7mWfmppH4ekCvRx9d.dWbKzZ/Q.38gcvCOTZPAHIX/iuCq2VW','admin/admin.png','ADMIN');
