package com.infinities_x.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@ApplicationScoped
public class JPAConfig {
    private static final Logger LOGGER = Logger.getLogger(JPAConfig.class.getName());

    @Produces
    @ApplicationScoped
    public EntityManagerFactory createEntityManagerFactory() {
        try {
            InitialContext ctx = new InitialContext();
            DataSource dataSource = lookupDataSource(ctx);
            Map<String, Object> properties = new HashMap<>();
            properties.put("javax.persistence.jtaDataSource", dataSource);
           
            return Persistence.createEntityManagerFactory("TaskManagerPU", properties);
        } catch (Exception e) {
            LOGGER.severe("Error creating EntityManagerFactory: " + e.getMessage());
            throw new RuntimeException("Error creating EntityManagerFactory", e);
        }
    }

    @Produces
    public EntityManager createEntityManager(EntityManagerFactory emf) {
        return emf.createEntityManager();
    }

    public void closeEntityManager(@Disposes EntityManager em) {
        if (em.isOpen()) {
            em.close();
        }
    }

    private DataSource lookupDataSource(InitialContext ctx) {
        try {
            return (DataSource) ctx.lookup("jdbc/TaskManagerDS");
        } catch (Exception e) {
            LOGGER.warning("First DataSource lookup failed: " + e.getMessage());
            try {
                return (DataSource) ctx.lookup("java:/TaskManagerDS");
            } catch (Exception ex) {
                LOGGER.severe("Could not find DataSource: " + ex.getMessage());
                throw new RuntimeException("Could not find DataSource", ex);
            }
        }
    }
}
