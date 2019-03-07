package com.uraltranscom.calculaterate.dao;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.Timer;
import com.uraltranscom.calculaterate.configuration.AppConfig;
import com.uraltranscom.calculaterate.configuration.AppInit;
import com.uraltranscom.calculaterate.configuration.DBConfig;
import com.uraltranscom.calculaterate.util.connect.ConnectionDB;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.SQLException;


/**
 * @author Vladislav.Klochkov
 * @project CalculationRate
 * @date 06.03.2019
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        AppInit.class,
        DBConfig.class,
        AppConfig.class})
@WebAppConfiguration
public class TestConnectionToDB {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestConnectionToDB.class);
    private static final int MAX_ITERATIONS = 1000;
    private Slf4jReporter logReporter;
    private Timer timer;

    @Autowired
    ConnectionDB connectionDB;

    @Before
    public void init() {
        MetricRegistry metricRegistry = new MetricRegistry();
        this.logReporter = Slf4jReporter
                .forRegistry(metricRegistry)
                .outputTo(LOGGER)
                .build();
        timer = metricRegistry.timer("connection");
    }

    @Test
    public void testOpenCloseConnections() throws SQLException {
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            Timer.Context context = timer.time();
            connectionDB.getDataSource().getConnection().close();
            context.stop();
        }
        logReporter.report();
    }
}
