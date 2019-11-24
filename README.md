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
