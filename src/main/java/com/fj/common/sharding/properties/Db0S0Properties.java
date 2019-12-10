package com.fj.common.sharding.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;



@Data
@Component
@ConfigurationProperties(prefix = "slave.equip0")
public class Db0S0Properties extends Db0Properties {

}
