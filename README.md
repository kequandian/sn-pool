## 生成流水编号池，编号可回收
> 建议独立运行，连接独立数据库 snpool

### application.yml 配置
```yml
PoolConfig:
    minlength: 5
    dateFormat: zh  #中文日期yyyyMMdd格式
    prefixes:
            - EAP
            - EBO
            - EDI
```

