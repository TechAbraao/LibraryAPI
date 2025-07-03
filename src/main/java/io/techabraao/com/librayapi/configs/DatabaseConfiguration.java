package io.techabraao.com.librayapi.configs;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    // Aqui eu to pegando do .yml as variáveis de ambiente (environment variables)
    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;

    @Value("${spring.datasource.driver-class-name}")
    String driver;

    // Não é recomendado pra utilizar em produção
    // Utilizado apenas pra teste. em produção é o HikariDataSource, onde o Hikari permite criar o Pool de Conexões.
    // @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driver);

        return ds;
    }

    // Todos são do tipo DataSource
    @Bean
    public DataSource hikariDataSource() {
        HikariConfig configs = new HikariConfig();
        configs.setJdbcUrl(url);
        configs.setUsername(username);
        configs.setPassword(password);
        configs.setDriverClassName(driver);

        // Configurando o Pool de Conexões.
        configs.setMaximumPoolSize(10); // Libera até 10 conexões (o máximo).
        configs.setMinimumIdle(1); // Tamanho inicial do Pool, ou seja, quanto de conexão é aberto quando esta corretamente funcionando.
        configs.setPoolName("library-db-pool");

        Integer MAX_LIFE_TIME_POOL = 600000; // Configurado em Milisegundos
        configs.setMaxLifetime(MAX_LIFE_TIME_POOL);

        Integer CONNECTION_TIME_OUT = 100000;
        configs.setConnectionTimeout(CONNECTION_TIME_OUT); // Vai buscar conexão até esse limite, caso não consiga, lançará um erro

        configs.setConnectionTestQuery("select 1"); // Query de Teste

        return new HikariDataSource(configs);
    }
}
