USE `materip`;

alter table `participants`
add unique (board_id, user_id);
alter table `participants`
add column `id` int auto_increment primary key;
