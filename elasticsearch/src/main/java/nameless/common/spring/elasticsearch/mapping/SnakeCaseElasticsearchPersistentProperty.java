package nameless.common.spring.elasticsearch.mapping;

import com.google.common.base.CaseFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.MultiField;
import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentProperty;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchPersistentProperty;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.model.Property;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.util.StringUtils;

public class SnakeCaseElasticsearchPersistentProperty extends SimpleElasticsearchPersistentProperty {

    private final String fieldName;

    public SnakeCaseElasticsearchPersistentProperty(Property property, PersistentEntity<?, ElasticsearchPersistentProperty> owner, SimpleTypeHolder simpleTypeHolder) {
        super(property, owner, simpleTypeHolder);
        String fieldName = getAnnotatedFieldName();
        if (fieldName == null) {
            fieldName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, property.getName());
        }
        this.fieldName = fieldName;
    }

    @Override
    public String getFieldName() {
        // 这样写是因为在SimpleElasticsearchPersistentProperty的构造方法中会调用getFieldName
        return fieldName == null ? super.getFieldName() : fieldName;
    }

    public String getAnnotatedFieldName() {
        String name = null;
        if (isAnnotationPresent(Field.class)) {
            name = findAnnotation(Field.class).name();
        } else if (isAnnotationPresent(MultiField.class)) {
            name = findAnnotation(MultiField.class).mainField().name();
        }
        return StringUtils.hasText(name) ? name : null;
    }
}
