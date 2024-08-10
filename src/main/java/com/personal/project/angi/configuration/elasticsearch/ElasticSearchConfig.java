package com.personal.project.angi.configuration.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {
    @Value("${spring.data.elasticsearch.host}")
    private String ELK_HOST;
    @Value("${spring.data.elasticsearch.port}")
    private  int ELK_PORT;
    @Value("${jwt.signerKey}")
    private String temp;

    //connect to elk
    @Bean
    public RestClient getRestClient() {
        RestClient restClient = RestClient.builder(
                new HttpHost(ELK_HOST, ELK_PORT)).build();
        return restClient;
    }

    //map Model Class to JSON
    @Bean
    public ElasticsearchTransport getElasticsearchTransport() {
        return new RestClientTransport(
                getRestClient(), new JacksonJsonpMapper());
    }

    //using to preform all query operation
    @Bean
    public ElasticsearchClient getElasticsearchClient(){
        ElasticsearchClient client = new ElasticsearchClient(getElasticsearchTransport());
        return client;
    }
}
