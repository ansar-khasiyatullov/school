select s.name, s.age, f.name from student s
    join faculty f on f.id = s.faculty_id;

select * from student s
    join avatar a on s.id = a.student_id
        where a.student_id is not null;