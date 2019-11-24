package com.fj.configuration.algorithm;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.fj.configuration.properties.Db0Properties;
import com.fj.configuration.properties.Db1Properties;
import com.google.common.collect.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

@Component
public class DatabaseShardingAlgorithm implements SingleKeyDatabaseShardingAlgorithm<Integer> {

    @Autowired
    private Db0Properties db0Properties;

    @Autowired
    private Db1Properties db1Properties;

    @Override
    public String doEqualSharding(Collection<String> availableTargetNames, ShardingValue<Integer> shardingValue) {
        Integer value = shardingValue.getValue();
        if (value <= 1000000) {
            return db0Properties.getDatabaseName();
        } else {
            return db1Properties.getDatabaseName();
        }
    }

    @Override
    public Collection<String> doInSharding(Collection<String> availableTargetNames, ShardingValue<Integer> shardingValue) {
        HashSet<String> availableName = new HashSet();
        Collection<Integer> values = shardingValue.getValues();
        for (Integer value : values) {
            if (value <= 1000000) {
                availableName.add(db0Properties.getDatabaseName());
            } else {
                availableName.add(db1Properties.getDatabaseName());
            }
        }
        return availableName;
    }

    @Override
    public Collection<String> doBetweenSharding(Collection<String> availableTargetNames, ShardingValue<Integer> shardingValue) {
        HashSet<String> availableName = new HashSet();
        Range<Integer> range = shardingValue.getValueRange();
        for (Integer value = range.lowerEndpoint(); value <= range.upperEndpoint(); value++) {
            if (value <= 1000000) {
                availableName.add(db0Properties.getDatabaseName());
            } else {
                availableName.add(db1Properties.getDatabaseName());
            }
        }
        return availableName;
    }
}
