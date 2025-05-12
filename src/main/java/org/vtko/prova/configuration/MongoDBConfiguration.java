package org.vtko.prova.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.vtko.prova.model.Gate;

import jakarta.validation.Validator;

@Configuration
public class MongoDBConfiguration {

    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener(Validator validator) {
        return new ValidatingMongoEventListener(validator);
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://localhost:27017/airport");
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient, MongoMappingContext mongoMappingContext) {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, "airport");
        createIndexes(mongoTemplate);
        return mongoTemplate;
    }

    private void createIndexes(MongoTemplate mongoTemplate) {
        IndexOperations indexOps = mongoTemplate.indexOps(Gate.class);
        indexOps.ensureIndex(new Index().on("code", Sort.Direction.ASC).unique());
    }

    @Bean
    @Primary
    public MongoProperties mongoProperties() {
        MongoProperties props = new MongoProperties();
        props.setUri("mongodb://localhost:27017/airport");
        return props;
    }
}
