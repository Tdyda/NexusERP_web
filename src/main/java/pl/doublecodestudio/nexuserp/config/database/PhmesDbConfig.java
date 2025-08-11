package pl.doublecodestudio.nexuserp.config.database;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "pl.doublecodestudio.nexuserp.infrastructure.phmes",
        entityManagerFactoryRef = "phmesEntityManagerFactory",
        transactionManagerRef = "phmesTransactionManager"
)
@RequiredArgsConstructor
public class PhmesDbConfig {
    private final PhmesProperties props;

    @Bean
    public DataSource phmesDataSource() {
        return DataSourceBuilder.create()
                .url(props.getUrl())
                .username(props.getUsername())
                .password(props.getPassword())
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean phmesEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", props.getDdlAuto());
        properties.put("hibernate.physical_naming_strategy", props.getPhysicalNamingStrategy());
        return builder
                .dataSource(phmesDataSource())
                .packages("pl.doublecodestudio.nexuserp.infrastructure.phmes")
                .persistenceUnit("phmesDb")
                .properties(properties)
                .build();
    }

    @Bean
    public PlatformTransactionManager phmesTransactionManager(
            @Qualifier("phmesEntityManagerFactory") EntityManagerFactory phmesEntityManagerFactory) {

        return new JpaTransactionManager(phmesEntityManagerFactory);
    }
}
