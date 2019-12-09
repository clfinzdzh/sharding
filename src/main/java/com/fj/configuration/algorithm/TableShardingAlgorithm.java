//package com.fj.configuration.algorithm;
//
//import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
//import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
//import com.google.common.collect.Range;
//import org.springframework.stereotype.Component;
//
//import java.util.Collection;
//import java.util.HashSet;
//
//@Component
//public class TableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Integer> {
//
//    @Override
//    public String doEqualSharding(Collection<String> availableTargetNames, ShardingValue<Integer> shardingValue) {
//        Integer value = shardingValue.getValue();
//        for (String name : availableTargetNames) {
//            if (name.endsWith((value % 2) + "")) {
//                return name;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public Collection<String> doInSharding(Collection<String> availableTargetNames, ShardingValue<Integer> shardingValue) {
//        HashSet<String> availableName = new HashSet();
//        Collection<Integer> values = shardingValue.getValues();
//        for (Integer value : values) {
//            for (String name : availableTargetNames) {
//                if (name.endsWith(((value) % 2) + "")) {
//                    availableName.add(name);
//                }
//            }
//        }
//        return availableName;
//    }
//
//    @Override
//    public Collection<String> doBetweenSharding
//            (Collection<String> availableTargetNames, ShardingValue<Integer> shardingValue) {
//        HashSet<String> availableName = new HashSet();
//        Range<Integer> range = shardingValue.getValueRange();
//        for (Integer value = range.lowerEndpoint(); value <= range.upperEndpoint(); value++) {
//            for (String name : availableTargetNames) {
//                if (name.endsWith(((value) % 2) + "")) {
//                    availableName.add(name);
//                }
//            }
//        }
//        return availableName;
//    }
//
//}
