package com.abitesh.geospatial;

import com.abitesh.geospatial.models.AgentEntity;
import com.abitesh.geospatial.repositories.AgentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GeospatialApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeospatialApplication.class, args);
    } 

    @Bean
    CommandLineRunner testDatabase(AgentRepository agentRepository) {
        return args -> {
            AgentEntity agent = new AgentEntity("Dummy Agent");
            agentRepository.save(agent);

            System.out.println("Saved agent with id: " + agent.getId());
            System.out.println("Total agents in DB: " + agentRepository.findAll().size());
        };
    }
}