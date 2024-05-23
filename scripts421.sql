alter table student add constraint age_check check (age >= 16);

insert into student (age, name, faculty_id) values (16, 'name1', null);

alter table student add constraint name_unique unique (name);

alter table student alter column name set not null;

alter table faculty add constraint faculty_name_color_uniq unique (name, color);

alter table student alter column age set default 20;