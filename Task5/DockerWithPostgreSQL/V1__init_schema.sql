create table School
(
	Id serial primary key,
	Name character varying(30),
	Address character varying(30)
);

create table Subject
(
	Id serial primary key,
	Name character varying(30)
);

create table Teacher
(
	Id serial primary key,
	FIO character varying(30),
	Age int,
	Sex character varying(1),
	School_Id int references School(Id)
);

create table Student
(
	Id serial primary key,
	FIO character varying(30),
	Age int,
	Sex character varying(1),
	School_Id int references School(Id)
);

create table Teacher_Subject
(
	Teacher_Id serial references Teacher(Id),
	Subject_Id serial references Subject(Id),
	primary key (Teacher_Id, Subject_Id)
);

create table Student_Subject
(
	Student_Id serial references Student(Id),
	Subject_Id serial references Subject(Id),
	primary key (Student_Id, Subject_Id)
);