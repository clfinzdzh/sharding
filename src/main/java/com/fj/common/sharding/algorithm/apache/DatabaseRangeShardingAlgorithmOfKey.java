package com.fj.common.sharding.algorithm.apache;

import com.google.common.collect.Range;
import io.shardingjdbc.core.api.algorithm.sharding.RangeShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.RangeShardingAlgorithm;

import java.util.Collection;

public class DatabaseRangeShardingAlgorithmOfKey implements RangeShardingAlgorithm {

    @Override
    public Collection<String> doSharding(Collection availableTargetNames, RangeShardingValue shardingValue) {
        Range valueRange = shardingValue.getValueRange();
        Comparable lowerEndpoint = valueRange.lowerEndpoint();
        Comparable upperEndpoint = valueRange.upperEndpoint();
        return availableTargetNames;
    }
}
