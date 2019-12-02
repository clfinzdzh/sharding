Centos7\mysql 5.8 主从复制环境的搭建
注：从机和主机配置完成后都要重启mysql服务
一、添加master 相关配置
1、开启二进制日志：log-bin=mysql-bin
2、配置server-id：server-id=1
3、指定要同步的数据库名称：binlog-do-db=my_test
4、指定二进制日志的记录方式：binlog-format=mixed
5、二进制日志文件的大小：max-binlog-size=1G
二、添加从库复制账号，首先登录mysql服务
1、创建从库账号：create user 'slave'@'%' identified by '123456';
2、给从库账号授权复制权限：grant replication slave on *.* to 'slave'@'%' with grant option;
3、刷新权限：flush privileges;
4、查看master状态：show master status;
三、添加slave相关配置
1、添加server-i：server-id=2
2、指定复制的库：replicate-do-db=my_test
四、配置主机相关连接参数，首先登陆从mysql
1、停止从机：stop slave
2、配置从机连接主机的参数
change master to master_host='host',master_port=3306,master_user='slave',master_password='123456',
master_log_file='mysql-bin.000003',master_log_pos=100;
3、启动从机：start slave;
4、查看从机状态：show slave status\G;
Slave_IO_Running: Yes;Slave_SQL_Running: Yes 说明配置成功
然后主机上的数据就会同步到从机上了。

#持久层采用mybatis-plus,支持查询条件的对象化，接口直接查询

#主键key：采用mybatis-plus 的唯一主键生成策略 19位long型（雪花算法）

#两个分片节点 equip_db_0 equip_db_1

#每个节点上4张表 t_equip_0 t_equip_1 t_equip_parts_0 t_equip_parts_1
#t_equip_0 && t_equip_1 || t_equip_parts_0 t_equip_parts_1 水平分表

#绑定关联表的关系，支持表的关联查询,
#关联的数据根据业务条件总是路由到同一数据节点，避免跨节点关联查询和跨节点跨数据库分布式事务
#t_equip_0 && t_equip_parts_0 || t_equip_1&&t_equip_parts_1 通过equip_id关联

#水平分库策略：（自定义）主键求模、主键一致性hash 范围分片、二进制取模等分片算法，可参考mycat的分片算法
#水平分表策略：（自定义）根据相关字段，定义路由策略

