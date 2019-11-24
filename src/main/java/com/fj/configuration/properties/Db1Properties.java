package com.fj.configuration.properties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "db1.ds")
@Component
@Data
public class Db1Properties extends Db0Properties {


}
