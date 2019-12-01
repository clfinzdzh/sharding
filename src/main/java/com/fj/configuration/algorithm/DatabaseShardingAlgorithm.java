package com.fj.configuration.algorithm;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.google.common.collect.Range;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

@Component
public class DatabaseShardingAlgorithm implements SingleKeyDatabaseShardingAlgorithm<Integer> {


    @Override
    public String doEqualSharding(Collection<String> availableTargetNames, ShardingValue<Integer> shardingValue) {
        Integer value = shardingValue.getValue();
        if (value <= 10) {
            return "masterslave0";
        } else {
            return "masterslave1";
        }
    }

    @Override
    public Collection<String> doInSharding(Collection<String> availableTargetNames, ShardingValue<Integer> shardingValue) {
        HashSet<String> availableName = new HashSet();
        Collection<Integer> values = shardingValue.getValues();
        for (Integer value : values) {
            if (value <= 10) {
                availableName.add("masterslave0");
            } else {
                availableName.add("masterslave1");
            }
        }
        return availableName;
    }

    @Override
    public Collection<String> doBetweenSharding(Collection<String> availableTargetNames, ShardingValue<Integer> shardingValue) {
        HashSet<String> availableName = new HashSet();
        Range<Integer> range = shardingValue.getValueRange();
        for (Integer value = range.lowerEndpoint(); value <= range.upperEndpoint(); value++) {
            if (value <= 10) {
                availableName.add("masterslave0");
            } else {
                availableName.add("masterslave1");
            }
        }
        return availableName;
    }
}
