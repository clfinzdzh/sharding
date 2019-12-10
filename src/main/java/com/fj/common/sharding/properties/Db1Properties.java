package com.fj.common.sharding.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;




@Data
@Component
@ConfigurationProperties(prefix = "master.equip1")
public class Db1Properties extends Db0Properties {

}
