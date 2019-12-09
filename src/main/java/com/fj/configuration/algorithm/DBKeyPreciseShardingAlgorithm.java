package com.fj.configuration.algorithm;

import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

public class DBKeyPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {


    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        return "masterslave" + shardingValue.getValue() % 2;
    }
}
