package com.fj.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "slave.equip1")
@Component
@Data
public class Db1S0Properties extends Db1Properties {

}
