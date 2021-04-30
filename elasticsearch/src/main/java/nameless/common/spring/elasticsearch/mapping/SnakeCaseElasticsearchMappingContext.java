package nameless.common.spring.elasticsearch.mapping;

import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentProperty;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchPersistentEntity;
import org.springframework.data.mapping.model.Property;
import org.springframework.data.mapping.model.SimpleTypeHolder;

/**
 *
 * 将elasticsearch中snake_case的property和java的camelCase实现自动转换，即：存储到elasticsearch中的时候全部以snake_case格式，
 * 导入到java中全部以camelCase
 *
 * Usage
 * <pre> {@code
 *     @Bean
 *     @Primary
 *     public SnakeCaseElasticsearchMappingContext elasticsearchMappingContext() {
 *         SnakeCaseElasticsearchMappingContext mappingContext = new SnakeCaseElasticsearchMappingContext();
 *         mappingContext.setInitialEntitySet(getInitialEntitySet());
 *         mappingContext.setSimpleTypeHolder(elasticsearchCustomConversions().getSimpleTypeHolder());
 *         return mappingContext;
 *     }
 * }</pre>
 */
public class SnakeCaseElasticsearchMappingContext extends SimpleElasticsearchMappingContext {
    @Override
    protected ElasticsearchPersistentProperty createPersistentProperty(Property property,
                                                                       SimpleElasticsearchPersistentEntity<?> owner,
                                                                       SimpleTypeHolder simpleTypeHolder) {
        return new SnakeCaseElasticsearchPersistentProperty(property, owner, simpleTypeHolder);
    }
}
