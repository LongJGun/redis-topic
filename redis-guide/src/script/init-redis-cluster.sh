#!/usr/bin/env bash


echo $0
echo "开始对传入参数校验"
if [ ! -n "$1" ]
then
	echo "第一个参数指定集群根目录，不能为空"
	exit 101
fi
if [ ! -n "$2" ]
then
	echo "第二个参数指定redis根目录，不能为空"
	exit 102
fi
if [ ! -n "$3" ]
then
	echo "第三个参数指定redis实例起始端口，不能为空"
	exit 103
fi
echo "传入参数校验完毕"
# 集群目录
clusterRootPath=$1
# redis根目录
redisHomePath=$2
# redis集群初始化端口
port=$3
redisServer=${redisHomePath}/bin/redis-server
redisClient=${redisHomePath}/bin/redis-cli
echo ${clusterRootPath}
# 判断Redis集群目录是否存在
if [ -d "${clusterRootPath}" ]
then
	echo "Redis集群目录[${clusterRootPath}]已存在"
else
	echo "Redis集群目录[${clusterRootPath}]不存在"
	mkdir -p ${clusterRootPath}
	echo "Redis集群目录[${clusterRootPath}]创建成功"
fi
# 判断redis-server文件是否存在
if [ ! -f "${redisServer}" ]
then
	echo "可执行文件[${redisServer}]不存在"
	exit 110
else
	if [ -x "${redisServer}" ]
	then
		echo "可执行文件[${redisServer}]存在"
	else
		echo "文件[${redisServer}]没有执行权限"
		exit 111
	fi
fi
# 判断redis-cli文件是否存在
if [ ! -f "${redisClient}" ]
then
	echo "可执行文件[${redisClient}]不存在"
	exit 110
else
	if [ -x "${redisClient}" ]
	then
		echo "可执行文件[${redisClient}]存在"
	else
		echo "文件[${redisClient}]没有执行权限"
		exit 112
	fi
fi
# 创建redis集群持久化数据目录
redisClusterDb=${clusterRootPath}/redis-db
mkdir -p ${redisClusterDb}
# 创建redis集群关闭脚本
shutdownRedisCluster="${clusterRootPath}/shutdown-redis-cluster.sh"
touch ${shutdownRedisCluster}

# 创建集群子目录及生成配置文件
read `echo cluster{1..6}` <<< "`echo cluster{1..6}`"
clusters=`echo cluster{1..6}`
clusterArr=(${clusters// / })
redisClientCmd="${redisClient} --cluster create"
for cluTmp in ${clusterArr[@]}
do
	if [ -d "${clusterRootPath}/${cluTmp}" ]
	then
		echo "目录[${clusterRootPath}/${cluTmp}]存在"
		rm -rf ${clusterRootPath}/${cluTmp}
		echo "目录[${clusterRootPath}/${cluTmp}]删除成功"
	else
		echo "目录[${clusterRootPath}/${cluTmp}]不存在"
	fi
	mkdir -p ${clusterRootPath}/${cluTmp}
	echo "目录[${clusterRootPath}/${cluTmp}]创建成功"
	# 集群配置文件信息
	echo "
# 绑定端口
port ${port}
# 集群配置
cluster-enabled yes
cluster-config-file ${clusterRootPath}/${cluTmp}/nodes.conf
cluster-node-timeout 5000
appendonly yes
# 守护进程方式启动
daemonize yes
# 绑定IP
bind 0.0.0.0
save 900 1
save 300 10
save 60 10000
# 设置日志等级
loglevel notice
# 设置日志文件
logfile "${clusterRootPath}/${cluTmp}/redis-${port}.log"
# 进程号输出位置
pidfile "${clusterRootPath}/${cluTmp}/redis-${port}.pid"
# 指定数据持久化文件存放位置
dir ${redisClusterDb}
dbfilename "dump-${port}.rdb"
appendfilename "appendonly-${port}.aof"
# 初始化数据库个数
databases 16
# 设置密码
# requirepass 123456
	" >> ${clusterRootPath}/${cluTmp}/redis.conf
	# touch ${clusterRootPath}/${cluTmp}/dump-${port}.rdb
	# 启动当前redis实例
	startRedisServer="${redisServer} ${clusterRootPath}/${cluTmp}/redis.conf"
	echo ${startRedisServer}
	echo "开始启动redis实例..."
	${startRedisServer}
	redisClientCmd=${redisClientCmd}" 127.0.0.1:${port}"
	echo "redis实例启动成功"
	# 将关闭集群的脚本写入文件
	echo "${redisClient} -p ${port} shutdown nosave" >> ${shutdownRedisCluster}
	# 端口号递增
	port=`expr ${port} + 1`
done
# 创建集群
startRedisCluster="${redisClientCmd} --cluster-replicas 1"
echo ${startRedisCluster}
echo "开始创建redis集群..."
${startRedisCluster}
echo "创建集群成功"
