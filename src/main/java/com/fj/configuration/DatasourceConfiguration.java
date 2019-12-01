package com.fj.configuration;


import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.dangdang.ddframe.rdb.sharding.api.MasterSlaveDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.BindingTableRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.dangdang.ddframe.rdb.sharding.api.strategy.slave.MasterSlaveLoadBalanceStrategyType;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.fj.configuration.properties.Db0Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@MapperScan(basePackages = "com.fj.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
@Configuration
public class DatasourceConfiguration {

    @Autowired
    private Db0Properties db0Properties,db0S0Properties,db1Properties,db1S0Properties;

    @Autowired
    private SingleKeyDatabaseShardingAlgorithm databaseShardingAlgorithm;

    @Autowired
    private SingleKeyTableShardingAlgorithm singleKeyTableShardingAlgorithm;

    @Bean("datasource")
    public DataSource datasource() throws SQLException {
        DataSource dataSource = buildDataSource();
        return dataSource;
    }

    @Bean("masterslave0")
    public DataSource masterslave0() throws SQLException {
        Map<String, DataSource> slaveMap = new HashMap<>();
        slaveMap.put(db0S0Properties.getDatabaseName(), db0S0Properties.createDataSource());
        DataSource masterslave0 = MasterSlaveDataSourceFactory.createDataSource(
                "masterslave0",
                db0Properties.getDatabaseName(),
                db0Properties.createDataSource(),
                slaveMap,
                MasterSlaveLoadBalanceStrategyType.getDefaultStrategyType()
        );
        return masterslave0;
    }

    @Bean("masterslave1")
    public DataSource masterslave1() throws SQLException {
        Map<String, DataSource> slaveMap = new HashMap<>();
        slaveMap.put(db1S0Properties.getDatabaseName(), db1S0Properties.createDataSource());
        DataSource masterslave1 = MasterSlaveDataSourceFactory.createDataSource(
                "masterslave1",
                db1Properties.getDatabaseName(),
                db1Properties.createDataSource(),
                slaveMap,
                MasterSlaveLoadBalanceStrategyType.getDefaultStrategyType()
        );
        return masterslave1;
    }

    private DataSource buildDataSource() throws SQLException {
        Map<String, DataSource> datasourceMap = new HashMap<>(2);
        datasourceMap.put("masterslave0", masterslave0());
        datasourceMap.put("masterslave1", masterslave1());
        DataSourceRule dataSourceRule = new DataSourceRule(datasourceMap, "masterslave0");
        TableRule equipTableRule = TableRule
                //定义设备逻辑表
                .builder("t_equip")
                .dataSourceRule(dataSourceRule)
                .actualTables(Arrays.asList("t_equip_0", "t_equip_1"))
                .build();
        TableRule equipPartsTableRule = TableRule
                //定义设备配件逻辑表
                .builder("t_equip_parts")
                .dataSourceRule(dataSourceRule)
                .actualTables(Arrays.asList("t_equip_parts_0", "t_equip_parts_1"))
                .build();
        ShardingRule shardingRule = ShardingRule
                .builder()
                .dataSourceRule(dataSourceRule)
                .tableRules(Arrays.asList(equipTableRule, equipPartsTableRule))
                //绑定关联表的关系,同时避免笛卡尔乘积
                .bindingTableRules(Collections.singletonList(new BindingTableRule(Arrays.asList(equipTableRule, equipPartsTableRule))))
                //关联数据通过equip_id进行分库，确保关联的数据分布在同一个物理数据节点
                .databaseShardingStrategy(new DatabaseShardingStrategy("equip_id", databaseShardingAlgorithm))
                //关联数据通过设备类型进行分表，确保关联数据一致的路由策略:t_equip_0==>t_equip_parts_0|| t_equip_1==>t_equip_parts_1
                .tableShardingStrategy(new TableShardingStrategy("type", singleKeyTableShardingAlgorithm))
                .build();
        return ShardingDataSourceFactory.createDataSource(shardingRule);
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("datasource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/fj/**/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean("dataSourceTransaction")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("datasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
