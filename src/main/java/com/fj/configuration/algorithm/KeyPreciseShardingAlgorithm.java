package com.fj.configuration.algorithm;

import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

public class KeyPreciseShardingAlgorithm implements PreciseShardingAlgorithm {

    @Override
    public String doSharding(Collection availableTargetNames, PreciseShardingValue shardingValue) {
        return null;
    }
}
