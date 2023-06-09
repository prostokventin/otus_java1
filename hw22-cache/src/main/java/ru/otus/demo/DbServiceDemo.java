package ru.otus.demo;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.HwListener;
import ru.otus.cachehw.MyCache;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DbServiceClientImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DbServiceDemo {

    private static final Logger log = LoggerFactory.getLogger(DbServiceDemo.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
///
        var clientTemplate = new DataTemplateHibernate<>(Client.class);

        HwCache<String, Client> cache = new MyCache<>();
        HwListener<String, Client> listener = new HwListener<String, Client>() {
            @Override
            public void notify(String key, Client value, String action) {
                log.info("!!! action:{}, key:{}, value:{}",action, key, value);
            }
        };
//        cache.addListener(listener);

        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate, cache);

        List<Long> idList = new ArrayList<>();

        LocalDateTime startDttm = LocalDateTime.now();

        for (int i = 1; i <= 1000; i++) {
            var client = dbServiceClient.saveClient(
                    new Client(
                            null,
                            "name" + i,
                            new Address(null, "street" + i),
                            List.of(new Phone(null, "phone" + i))
                    )
            );
            idList.add(client.getId());
        }

        for (var id : idList) {
            dbServiceClient.getClient(id);
        }

        LocalDateTime endDttm = LocalDateTime.now();

        log.info("start dttm:{}", startDttm);
        log.info("end dttm:{}", endDttm);

//        cache.removeListener(listener);
    }
}
