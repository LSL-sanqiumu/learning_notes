# 单表

## 重复数据查找

**问题：**编写一个SQL查询，查找学生表中所有重复的学生名。

```mysql
create table student(
	id int(4) zerofill not null auto_increment primary key,
	name varchar(10) default null
)engine=innodb default charset=utf8;
insert into mysqltest.student(name) values('林夏'),('梁石湫'),('梁宜'),('鲁迅'),('梁石湫'),('梁宜'),('鲁迅');
```

**解：**

分析：DQL语句无非distinct、where、order by、having、limit、union、group by、like、order by、from子句、where子句、select子句。

思路1：可先按名字分组，然后使用聚合函数统计出数量，数量大于1的就是重复的了。

```mysql
select name,count(name) from student group by name;
-- 方法1：使用from子句（将from子句的结果当成一张表）
select name from (select name,count(name) as c from student group by name) as t where t.c>1;
```

```mysql
-- 方法2：分组后过滤
select name from student group by name having count(name) > 1;
```

## 查找第n高的数据

**问题：**现在有“课程表”，记录了学生选修课程的名称以及成绩。现在需要找出语文成绩第二高的分数是多少。如果不存在第二高的分数，那么查询应返回 null。

```mysql
create table grades(
	id int(4) zerofill not null auto_increment,
    course varchar(255) default null,
    scores int(4) default 0,
    primary key(id)
)engine=innodb default charset=utf8;
insert into mysqltest.grades(course,scores) values('语文',90),('数学',65),('语文',90),('英语',80),('语文',88),('数学',75),('英语',83);
```

**解：**

分析：DQL语句无非distinct、where、order by、having、limit、union、group by、like、order by、from子句、where子句、select子句。

思路1：将成绩排序后，再取第二个数据，因为第一的成绩可以会有多个，所以要对成绩去重。

```mysql
-- 1.对成绩去重
select distinct scores from grades where course='语文';
-- 2.对去重的成绩进行分页，然后取第二个就好
select scores from (select distinct scores from grades where course='语文') as t limit 1,1;
```

因为当第二成绩不存在时，要返回null，所以使用`ifnull(a,b)函数`（如果a是空则返回b，不是则返回a）。

```mysql
-- 最终SQL
select ifnull(
    (select scores from (select distinct scores from grades where course='语文') as t limit 1,1)
    ,null) as '语文成绩第二的分数';
```



思路2：找到最大的分数，其他的比最大的分数小的最大的分数就是第二大了。

```mysql
select max(scores) from grades where course='语文'; -- 找到最大的数
-- 使用where子句
select max(scores) from grades where course='语文' and scores<(select max(scores) from grades where course='语文');
-- 最终判null后的SQL
select ifnull(
    (select max(scores) from grades where course='语文' and scores<(select max(scores) from grades where course='语文'))
    ,null) as '语文成绩第二的分数';
```

# 多表

## 联表查询基本操作

**问题：**现在有两个表，“学生表”记录了学生的基本信息，有“学号”、“姓名”。“成绩表”记录了学生选修的课程，以及对应课程的成绩。这两个表通过“学号”进行关联。现在要查**找出所有学生的学号、姓名、课程和成绩**。

```mysql
create table mysqltest.student(
	id int(3) zerofill not null auto_increment primary key comment '学号',
    name varchar(5)
)engine=innodb default charset=utf8;
create table mysqltest.grades(
	id int(4) default 0  comment '学号',
    course varchar(6),
    scores int(6)
)engine=innodb default charset=utf8;
insert into student(name) values('张叁'),('李思'),('王武'),('赵柳');
insert into grades values(1,'语文',90),(1,'数学',66),(2,'语文',78),(2,'数学',73),(3,'数学',80);
```

**解：**

多表查询，无非内连接查询、外连接查询（左外、右外、全外）。

```mysql
select s.id,s.name,g.course,g.scores from student as s left join grades as g on s.id=g.id;
```

## 查找两表中不存在映射关系的数据

**问题1：**“学生表”有学号和姓名；“近视学生表”为近视学生的名单，只包含序号和学号。请问不是近视眼的学生都有谁？（“学生表”表中的学号与“近视学生”表中的学生学号一一对应）

```mysql
create table mysqltest.student(
	id int(3) zerofill not null auto_increment primary key comment '学号',
    name varchar(255) default null
)engine=innodb default charset=utf8;
create table mysqltest.myopia(
	id int(3) not null auto_increment primary key comment '序号',
    sid int(3) zerofill default 0
)engine=innodb default charset=utf8;
insert into student(name) values('周周'),('陆陆'),('梁梁'),('李李'),('吼吼');
insert into myopia(sid) values(0001),(0002),(0004);
```

**解：**

```mysql
-- 使用内连接
select s.id,s.name from student s join myopia m on s.id!=m.sid; 
-- 发现并不行，联表查询会匹配m*n次，只要匹配到的符合条件就会进入结果集
```

```mysql
-- 使用外连接 (要知道指令执行的顺序，不要被select迷惑)
select s.id,s.name from student s left join myopia m on s.id=m.sid where m.id is null; 
```

**问题2：**查找“不在表里的数据”应用案例：某网站包含两个表，顾客姓名表（表名Customers）和 购买记录表（表名Orders）。找出所有从不订购任何东西的客户。（“顾客姓名表”中的ID与“购买记录”表中的学生学号CustomerId一一对应）

```mysql
create table mysqltest.customers(
    id int(10) not null auto_increment primary key,
    name varchar(255)
)engine=innodb default charset=utf8;
create table mysqltest.orders(
    id int(10) not null auto_increment primary key,
    customerid int(10)
)engine=innodb default charset=utf8;
insert into mysqltest.customers(name) values("Joe"),("Henry"),("Sam"),("Bob");
insert into mysqltest.orders(customerid) values(1),(3);
```

**解：**

从两表中进行查询，可以利用union，也可以使用外连接，以顾客表为主表。

```mysql
select c.name from customers c left join orders o on c.id=o.customerid where o.customerid is null;
```

## 从两表取数据并进行一定分析

**问题：**“雇员表“中记录了员工的信息，“薪水表“中记录了对应员工发放的薪水。两表通过“雇员编号”关联。

查找当前所有雇员入职以来的薪水涨幅，给出雇员编号、以及其对应的薪水涨幅，并按照薪水涨幅进行升序。（注：薪水表中结束日期为2004-01-01的才是当前员工，否则是已离职员工）

<img src="img/ex_1.png" style="zoom: 67%;" />

```mysql
-- 雇员表
create table mysqltest.employee(
	employeeid varchar(255), 
    birthday date,
    `name` varchar(255),
    sex varchar(255),
    hiredate date
)engine=innodb default charset=utf8;
-- 薪水表
create table mysqltest.salary(
	id varchar(255), 
    salary int,
    startdate date,
    enddate date
)engine=innodb default charset=utf8;
insert into mysqltest.employee values
('1002','1976-09-09','小明','男','2001-08-02'),
('1005','1973-08-07','小红','女','2001-09-09'),
('1006','1980-08-28','小蓝','女','2001-08-02');
insert into mysqltest.salary values
('1002',72527,'2001-08-02','2003-01-01'),
('1002',75432,'2003-01-01','2004-01-01'),
('1005',94692,'2001-09-09','2003-01-01'),
('1006',43311,'2001-08-02','2004-01-01');
```

**解：**















