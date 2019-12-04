package com.fj.configuration;

import java.util.Properties;

import com.fj.configuration.algorithm.KeyPreciseShardingAlgorithm;
import com.fj.configuration.algorithm.KeyRangeShardingAlgorithm;
import com.fj.configuration.algorithm.KeysComplexKeysShardingAlgorithm;
import com.fj.configuration.properties.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.shardingjdbc.core.api.MasterSlaveDataSourceFactory;
import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.algorithm.masterslave.MasterSlaveLoadBalanceAlgorithmType;
import io.shardingjdbc.core.api.config.MasterSlaveRuleConfiguration;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.ComplexShardingStrategyConfiguration;
import io.shardingjdbc.core.api.config.strategy.StandardShardingStrategyConfiguration;
import io.shardingjdbc.core.constant.ShardingPropertiesConstant;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@MapperScan(basePackages = "com.fj.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
@Configuration
public class DatasourceConfiguraionOfApache {

    @Autowired
    private DataSourceConstant dataSourceConstant;
    @Autowired
    private Db0Properties db0Properties;
    @Autowired
    private Db1Properties db1Properties;
    @Autowired
    private Db0S0Properties db0S0Properties;
    @Autowired
    private Db1S0Properties db1S0Properties;

    @Bean
    DataSource dataSource() throws SQLException {
        Map<String, DataSource> dataSourceMap = Maps.newTreeMap();
        dataSourceMap.put(dataSourceConstant.getMs0name(), masterslave0());
        dataSourceMap.put(dataSourceConstant.getMs1name(), masterslave1());
        Properties properties = new Properties();
        properties.setProperty(ShardingPropertiesConstant.SQL_SHOW.getKey(), dataSourceConstant.getSqlShow());
        //配置执行sql的线程数
        properties.setProperty(ShardingPropertiesConstant.EXECUTOR_SIZE.getKey(), dataSourceConstant.getExecutorSize());
        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfiguration(),
                Maps.newConcurrentMap(), properties);

    }

    private DataSource masterslave0() throws SQLException {
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

    private DataSource masterslave1() throws SQLException {
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
     *
     * @return
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
    private ShardingRuleConfiguration shardingRuleConfiguration() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        shardingRuleConfiguration.getTableRuleConfigs().addAll(tableRuleConfiguration());
        shardingRuleConfiguration.getBindingTableGroups().add("t_equip,t_equip_parts");
        shardingRuleConfiguration.setDefaultDataSourceName(dataSourceConstant.getMs0name());
        StandardShardingStrategyConfiguration standard = new StandardShardingStrategyConfiguration("equip_id", KeyPreciseShardingAlgorithm.class.getName(), KeyRangeShardingAlgorithm.class.getName());
        shardingRuleConfiguration.setDefaultDatabaseShardingStrategyConfig(standard);
        shardingRuleConfiguration.setDefaultTableShardingStrategyConfig(standard);
        //ComplexShardingStrategyConfiguration complex = new ComplexShardingStrategyConfiguration("equip_id,type", KeysComplexKeysShardingAlgorithm.class.getName());
        //shardingRuleConfiguration.setDefaultDatabaseShardingStrategyConfig(complex);
        //shardingRuleConfiguration.setDefaultTableShardingStrategyConfig(complex);
        return shardingRuleConfiguration;
    }

}
