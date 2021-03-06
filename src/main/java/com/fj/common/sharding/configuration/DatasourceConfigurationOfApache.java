package com.fj.common.sharding.configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.fj.common.sharding.algorithm.apache.DatabaseShardingAlgorithmOfKey;
import com.fj.common.sharding.algorithm.apache.TableRangeShardingAlgorithmOfKey;
import com.fj.common.sharding.algorithm.apache.TableShardingAlgorithmOfKey;
import com.fj.common.sharding.algorithm.apache.DatabaseRangeShardingAlgorithmOfKey;
import com.fj.common.sharding.properties.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.shardingjdbc.core.api.MasterSlaveDataSourceFactory;
import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.algorithm.masterslave.MasterSlaveLoadBalanceAlgorithmType;
import io.shardingjdbc.core.api.config.MasterSlaveRuleConfiguration;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.StandardShardingStrategyConfiguration;
import io.shardingjdbc.core.constant.ShardingPropertiesConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
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
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@MapperScan(basePackages = "com.fj.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
@Configuration
public class DatasourceConfigurationOfApache {

    @Autowired
    private Db0Properties db0Properties;
    @Autowired
    private Db0S0Properties db0S0Properties;
    @Autowired
    private Db1Properties db1Properties;
    @Autowired
    private Db1S0Properties db1S0Properties;
    @Autowired
    private DataSourceConstant dataSourceConstant;
    @Autowired
    private PaginationInterceptor paginationInterceptor;
    @Autowired
    private PerformanceInterceptor performanceInterceptor;


    @Bean
    DataSource dataSource() throws SQLException {
        Map<String, DataSource> dataSourceMap = Maps.newHashMap();
        dataSourceMap.put(dataSourceConstant.getMs0name(), masterslave0());
        dataSourceMap.put(dataSourceConstant.getMs1name(), masterslave1());
        Properties properties = new Properties();
        properties.setProperty(ShardingPropertiesConstant.SQL_SHOW.getKey(), dataSourceConstant.getSqlShow());
        //配置执行sql的线程数
        properties.setProperty(ShardingPropertiesConstant.EXECUTOR_SIZE.getKey(), dataSourceConstant.getExecutorSize());
        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfiguration(),
                Maps.newConcurrentMap(), properties);

    }

    @Bean
    DataSource masterslave0() throws SQLException {
        Map<String, DataSource> dataSourceMap = Maps.newHashMap();
        dataSourceMap.put(db0Properties.getDatabaseName(), db0Properties.createDataSource());
        dataSourceMap.put(db0S0Properties.getDatabaseName(), db0S0Properties.createDataSource());
        MasterSlaveRuleConfiguration masterSlaveRuleConfiguration = new MasterSlaveRuleConfiguration();
        masterSlaveRuleConfiguration.setName(dataSourceConstant.getMs0name());
        masterSlaveRuleConfiguration.setMasterDataSourceName(db0Properties.getDatabaseName());
        masterSlaveRuleConfiguration.setSlaveDataSourceNames(Lists.newArrayList(db0S0Properties.getDatabaseName()));
        masterSlaveRuleConfiguration.setLoadBalanceAlgorithmType(MasterSlaveLoadBalanceAlgorithmType.ROUND_ROBIN);
        return MasterSlaveDataSourceFactory.createDataSource(dataSourceMap, masterSlaveRuleConfiguration, new ConcurrentHashMap<>());
    }

    @Bean
    DataSource masterslave1() throws SQLException {
        Map<String, DataSource> dataSourceMap = Maps.newHashMap();
        dataSourceMap.put(db1Properties.getDatabaseName(), db1Properties.createDataSource());
        dataSourceMap.put(db1S0Properties.getDatabaseName(), db1S0Properties.createDataSource());
        MasterSlaveRuleConfiguration masterSlaveRuleConfiguration = new MasterSlaveRuleConfiguration();
        masterSlaveRuleConfiguration.setName(dataSourceConstant.getMs1name());
        masterSlaveRuleConfiguration.setMasterDataSourceName(db1Properties.getDatabaseName());
        masterSlaveRuleConfiguration.setSlaveDataSourceNames(Lists.newArrayList(db1S0Properties.getDatabaseName()));
        masterSlaveRuleConfiguration.setLoadBalanceAlgorithmType(MasterSlaveLoadBalanceAlgorithmType.ROUND_ROBIN);
        return MasterSlaveDataSourceFactory.createDataSource(dataSourceMap, masterSlaveRuleConfiguration, new ConcurrentHashMap<>());
    }

    /**
     * 表规则配置
     */
    private List<TableRuleConfiguration> tableRuleConfiguration() {
        List<TableRuleConfiguration> tableRuleConfigurations = Lists.newArrayList();
        TableRuleConfiguration equipTableRuleConfiguration = new TableRuleConfiguration();
        tableRuleConfigurations.add(equipTableRuleConfiguration);
        equipTableRuleConfiguration.setLogicTable("t_equip");
        equipTableRuleConfiguration.setActualDataNodes("masterslave${0..1}.t_equip_${0..1}");
        TableRuleConfiguration equipPartsTableRuleConfiguration = new TableRuleConfiguration();
        tableRuleConfigurations.add(equipPartsTableRuleConfiguration);
        equipPartsTableRuleConfiguration.setLogicTable("t_equip_parts");
        equipPartsTableRuleConfiguration.setActualDataNodes("masterslave${0..1}.t_equip_parts_${0..1}");
        return tableRuleConfigurations;
    }

    /**
     * 分片规则配置
     */
    private ShardingRuleConfiguration shardingRuleConfiguration() {
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        shardingRuleConfiguration.getTableRuleConfigs().addAll(tableRuleConfiguration());
        shardingRuleConfiguration.getBindingTableGroups().add("t_equip,t_equip_parts");
        shardingRuleConfiguration.setDefaultDataSourceName(dataSourceConstant.getMs0name());
        StandardShardingStrategyConfiguration tableStrategy = new StandardShardingStrategyConfiguration("type", TableShardingAlgorithmOfKey.class.getName(), TableRangeShardingAlgorithmOfKey.class.getName());
        StandardShardingStrategyConfiguration databaseStrategy = new StandardShardingStrategyConfiguration("equip_id", DatabaseShardingAlgorithmOfKey.class.getName(), DatabaseRangeShardingAlgorithmOfKey.class.getName());
        shardingRuleConfiguration.setDefaultTableShardingStrategyConfig(tableStrategy);
        shardingRuleConfiguration.setDefaultDatabaseShardingStrategyConfig(databaseStrategy);
        //ComplexShardingStrategyConfiguration complex = new ComplexShardingStrategyConfiguration("equip_id,type", DatabaseShardingAlgorithmOfComplexKeys.class.getName());
        //shardingRuleConfiguration.setDefaultDatabaseShardingStrategyConfig(complex);
        //shardingRuleConfiguration.setDefaultTableShardingStrategyConfig(complex);
        return shardingRuleConfiguration;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        Interceptor[] interceptors = {paginationInterceptor, performanceInterceptor};
        sqlSessionFactoryBean.setPlugins(interceptors);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/fj/**/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 分库分表数据源的事务管理器
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("dataSource")
                                                                             DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
