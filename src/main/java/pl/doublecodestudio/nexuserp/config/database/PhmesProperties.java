package pl.doublecodestudio.nexuserp.config.database;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@EqualsAndHashCode(callSuper = true)
@Component
@ConfigurationProperties(prefix = "phmes")
public class PhmesProperties extends BaseProperties {
}
