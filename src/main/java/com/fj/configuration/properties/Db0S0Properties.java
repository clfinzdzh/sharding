package com.fj.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "slave.equip0")
@Component
@Data
public class Db0S0Properties extends Db0Properties {

}
