# Zuul Enhancement
### 支持配置的http header加入到request中
通过routes.{service}.extraHeaders配置
sample
```yaml
zuul:
  routes:
    foo:
      path: /foo/**
      serviceId: foo
      extraHeaders:
        authorization: basic Zm9vOnNlY3JldHNlY3JldHh4
        x-custom-header: header-value
```
