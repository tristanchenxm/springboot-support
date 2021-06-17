* 自动将elasticsearch中的snake_case字段转为java习惯的camelCase. 代码示例：
    ```java
    import nameless.common.spring.elasticsearch.mapping.SnakeCaseElasticsearchMappingContext;
    import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
  
    public class ElasticSearchConfiguration extends AbstractElasticsearchConfiguration {
  
      @Bean
      @Primary
      public SnakeCaseElasticsearchMappingContext elasticsearchMappingContext() {
          SnakeCaseElasticsearchMappingContext mappingContext = new SnakeCaseElasticsearchMappingContext();
          mappingContext.setInitialEntitySet(getInitialEntitySet());
          mappingContext.setSimpleTypeHolder(elasticsearchCustomConversions().getSimpleTypeHolder());
          return mappingContext;
      }
  
    }
    ```
