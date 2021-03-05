## 功能
- 生成实体对象模拟/测试数据

## 使用场景
- 单元测试时，构建一些比较复杂的实体对象
- 使用java生成/导入一些测试/模拟数据

##三种构建模拟数据模式
- Normal 模式
> 普通模式(生成的值只跟类型有关),生成的数据不具备实际意义
- Localization 模式
> 本土化模式(生成的值除了跟类型有关外还跟fieldName意义有关,详情见field/DefaultFieldValueType.java,如果匹配不到相关的FieldValueType,则降为普通模式)
- Custom 模式
> 自定义模式(生成的值由自定义的规则决定,如果匹配不到规则,则降为本土化模式)

## 如何使用?
### 主要包含三个步骤：
- 初始化构建模式(除了自定义模式外的其他两种模式，配置这一行就够了)
```java
 ModelBuilder.initCommonConfig(CommonConfig.CUSTOM_BUILD_MODE);
```
- 自定义模式，添加配置规则(其他两种模式这里不需要配置)
```java
StringFieldValueRule<String> rule1=new StringFieldValueRule.Builder<String>(){}.fieldName("password").minLength(4).maxLength(5).build();
FieldValueRuleManager.addRule(rule1);
```
- 生成模拟实体对象数据
```java
 BlogAuthor blogAuthor = ModelBuilder.generator(BlogAuthor.class);
```
- 具体使用代码实例可以到单元测试目录/test/*下查看

> 补充一下：上面提到的自定义的规则主要包含7种类型规则，分别是(包:rules/*)：
- BooleanFieldValueRule(布尔类型的字段使用)

- DateLocalDateFieldValueRule(日期类型的字段使用( LocalDateTime, LocalDate, LocalTime, Date))

- FloatDoubleFieldValueRule(浮点数类型的字段使用)

- ListSetFieldValueRule(list和set类型的字段使用)

- MapFieldValueRule(map类型的字段使用)

- StringFieldValueRule(字符串类型的字段使用(包括String,Character))

- WholeNumberFieldValueRule(整形类型的字段使用(包括Byte, Short, Integer, Long))

## 局限
- 目前仅支持基础类型+常见的日期类型+(List/Set/Map)+自定义实体对象类型
- 目前仅实现功能,但代码质量,注释与风格还有待提高,望包含。另外支持的类型和规则的扩展也有待增加
