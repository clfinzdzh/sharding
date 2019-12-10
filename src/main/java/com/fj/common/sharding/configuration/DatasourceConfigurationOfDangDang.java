//package com.fj.common.sharding;
//
//import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//import com.dangdang.ddframe.rdb.sharding.api.MasterSlaveDataSourceFactory;
//import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
//import com.dangdang.ddframe.rdb.sharding.api.rule.BindingTableRule;
//import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
//import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
//import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
//import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
//import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
//import com.dangdang.ddframe.rdb.sharding.api.strategy.slave.MasterSlaveLoadBalanceStrategyType;
//import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
//import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
//import com.dangdang.ddframe.rdb.sharding.configuration.ShardingPropertiesConstant;
//import com.fj.common.sharding.properties.*;
//import com.google.common.collect.Lists;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//import java.util.*;
//
//@Slf4j
//@MapperScan(basePackages = "com.fj.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
//@Configuration
//public class DatasourceConfigurationOfDangDang {
//
//    @Autowired
//    private DataSourceConstant dataSourceConstant;
//    @Autowired
//    private Db0Properties db0Properties;
//    @Autowired
//    private Db1Properties db1Properties;
//    @Autowired
//    private Db0S0Properties db0S0Properties;
//    @Autowired
//    private Db1S0Properties db1S0Properties;
//
//    @Autowired
//    private SingleKeyDatabaseShardingAlgorithm databaseShardingAlgorithm;
//
//    @Autowired
//    private SingleKeyTableShardingAlgorithm singleKeyTableShardingAlgorithm;
//
//    /**
//     * 分库分表读写分离数据源
//     */
//    @Bean
//    public DataSource datasource() throws SQLException {
//        DataSourceRule dataSourceRule = dataSourceRule();
//        List<TableRule> tableRules = tableRule(dataSourceRule);
//        ShardingRule shardingRule = shardingRule(dataSourceRule, tableRules);
//        //打印执行sql
//        Properties properties = new Properties();
//        properties.setProperty(ShardingPropertiesConstant.SQL_SHOW.getKey(), dataSourceConstant.getSqlShow());
//        //配置执行sql的线程数
//        properties.setProperty(ShardingPropertiesConstant.EXECUTOR_SIZE.getKey(), dataSourceConstant.getExecutorSize());
//        return ShardingDataSourceFactory.createDataSource(shardingRule, properties);
//    }
//
//    /**
//     * 读写分离数据源
//     */
//    @Bean
//    public DataSource masterslave0() throws SQLException {
//        Map<String, DataSource> slaveMap = new HashMap<>();
//        slaveMap.put(db0S0Properties.getDatabaseName(), db0S0Properties.createDataSource());
//        DataSource masterslave0 = MasterSlaveDataSourceFactory.createDataSource(
//                dataSourceConstant.getMs0name(),
//                db0Properties.getDatabaseName(),
//                db0Properties.createDataSource(),
//                slaveMap,
//                MasterSlaveLoadBalanceStrategyType.ROUND_ROBIN
//        );
//        return masterslave0;
//    }
//
//    /**
//     * 读写分离数据源
//     */
//    @Bean
//    public DataSource masterslave1() throws SQLException {
//        Map<String, DataSource> slaveMap = new HashMap<>();
//        slaveMap.put(db1S0Properties.getDatabaseName(), db1S0Properties.createDataSource());
//        DataSource masterslave1 = MasterSlaveDataSourceFactory.createDataSource(
//                dataSourceConstant.getMs1name(),
//                db1Properties.getDatabaseName(),
//                db1Properties.createDataSource(),
//                slaveMap,
//                MasterSlaveLoadBalanceStrategyType.ROUND_ROBIN
//        );
//        return masterslave1;
//    }
//
//    /**
//     * 自定义mybatis会话工厂，用以创建会话连接客户端
//     */
//    @Bean
//    public SqlSessionFactory sqlSessionFactory(@Qualifier("datasource")
//                                                       DataSource dataSource) throws Exception {
//        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/fj/**/*.xml"));
//        return sqlSessionFactoryBean.getObject();
//    }
//
//    /**
//     * 分库分表数据源的事务管理器
//     */
//    @Bean
//    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("datasource")
//                                                                             DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    /**
//     * 配置默认数据源
//     */
//    private DataSourceRule dataSourceRule() throws SQLException {
//        Map<String, DataSource> datasourceMap = new HashMap<>(2);
//        datasourceMap.put(dataSourceConstant.getMs0name(), masterslave0());
//        datasourceMap.put(dataSourceConstant.getMs1name(), masterslave1());
//        return new DataSourceRule(datasourceMap, dataSourceConstant.getMs0name());
//    }
//
//    /**
//     * 定义分表规则
//     */
//    private List<TableRule> tableRule(DataSourceRule dataSourceRule) {
//        TableRule equipTableRule = TableRule
//                .builder("t_equip")
//                .dataSourceRule(dataSourceRule)
//                .actualTables(Arrays.asList("t_equip_0", "t_equip_1"))
//                .build();
//        TableRule equipPartsTableRule = TableRule
//                //定义设备配件逻辑表
//                .builder("t_equip_parts")
//                .dataSourceRule(dataSourceRule)
//                .actualTables(Arrays.asList("t_equip_parts_0", "t_equip_parts_1"))
//                .build();
//        return Lists.newArrayList(equipTableRule, equipPartsTableRule);
//    }
//
//    /**
//     * 定义分片规则
//     */
//    private ShardingRule shardingRule(DataSourceRule dataSourceRule, List<TableRule> tableRules) {
//        return ShardingRule
//                .builder()
//                .dataSourceRule(dataSourceRule)
//                .tableRules(tableRules)
//                //绑定关联表的关系,同时避免笛卡尔乘积
//                .bindingTableRules(Collections.singletonList(new BindingTableRule(tableRules)))
//                //关联数据通过equip_id进行分库，确保关联的数据分布在同一个物理数据节点
//                .databaseShardingStrategy(new DatabaseShardingStrategy("equip_id", databaseShardingAlgorithm))
//                //关联数据通过设备类型进行分表，确保关联数据一致的路由策略:t_equip_0==>t_equip_parts_0|| t_equip_1==>t_equip_parts_1
//                .tableShardingStrategy(new TableShardingStrategy("type", singleKeyTableShardingAlgorithm))
//                .build();
//    }
//
//
//}
//
