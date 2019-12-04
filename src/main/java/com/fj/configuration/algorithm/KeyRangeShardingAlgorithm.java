package com.fj.configuration.algorithm;

import io.shardingjdbc.core.api.algorithm.sharding.RangeShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.RangeShardingAlgorithm;

import java.util.Collection;

public class KeyRangeShardingAlgorithm implements RangeShardingAlgorithm {
    @Override
    public Collection<String> doSharding(Collection availableTargetNames, RangeShardingValue shardingValue) {
        return null;
    }
}
