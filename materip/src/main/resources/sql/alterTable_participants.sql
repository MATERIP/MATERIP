USE `materip`;

alter table `participants`
add unique (board_id, user_id);
alter table `participants`
add column `id` int auto_increment primary key;


alter table `board`
add column `max_count` int default 0;

alter table `board`
add column `current_count` int default 0;

