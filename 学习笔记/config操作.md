

### 获取配置项

[参数说明]: https://redis.io/commands/config-get	"CONFIG GET"

```
> CONFIG GET *

> CONFIG GET save
```



### 设置配置项

[参数说明]: https://redis.io/commands/config-set	"CONFIG SET"

```
CONFIG SET parameter value

> CONFIG SET SAVE "900 1 300 10"
> CONFIG SET appendonly  "yes"
```



### 重置统计信息

[参数说明]: https://redis.io/commands/config-resetstat	"CONFIG RESETSTAT"

```
> CONFIG RESETSTAT
> ok

这些是重置的计数器：
Keyspace hits # 键空间点击
Keyspace misses # 键空间未命中
Number of commands processed # 处理的命令数
Number of connections received # 收到的连接数
Number of expired keys # 过期密钥数
Number of rejected connections # 拒绝的连接数
Latest fork(2) time # 最新fork（2）时间
The aof_delayed_fsync counter # 该aof_delayed_fsync计数器
```





























