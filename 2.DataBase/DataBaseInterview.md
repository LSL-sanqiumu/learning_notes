# MySQL

免费开源的关系型数据库。

## 存储引擎

### 常用命令

```mysql
show engines; -- 查看MySQL提供的所有存储引擎
show variables like '%storage_engine%'; -- 查看默认的存储引擎
show table status like 'table_name'; -- 查看表的存储引擎
show table status like "info"; -- 查看某个数据库中的某个表的使用的存储引擎（双引号包的是表名）

```

### MyISAM和Innodb的区别？

MySQL5.5版本后的默认存储引擎 Innodb。

1. Innodb支持事务、外键和行级锁、MVCC，而MyISAM不支持。
2. MyISAM支持全文索引，而Innodb不支持。
3. Innodb下的表可以存储较多的数据。
4. MyISAM较小，节约空间，速度较快。
5. Innodb安全性高，支持高并发。





## 字符集





## 索引

MySQL官方对索引的定义为：**索引（Index）是帮助MySQL高效获取数据的数据结构。**

MySQL支持多种索引（BTree索引、哈希索引、全文索引），平时使用最多的就是BTree索引（自平衡二叉树）。

索引实现原理：缩小扫描的范围，避免全表扫描。







## 缓存查询







## 事务







## 锁





## 大表优化







## 数据库连接池







## 分库分表







## SQL指令执行







## 高性能MySQL







