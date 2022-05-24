package learn.lty.config;

/**
 * @author liangty1
 */
public final class Configs {

    public static final String SQLLINEAGE_MQ_SERVERS = "sqllineage.mq.servers";

    public static final String SQLLINEAGE_MQ_CONFIGS = "sqllineage.mq.configs";

    public static final String SPARK_SQLLINEAGE_MQ_CONFIGS = "spark.sqllineage.mq.configs";

    public static final String MQ_CONF_FORMAT = "MqType:%s,topic:%s,tag:%s";

    public static final String MQ_TYPE = "MqType";

    public static final String TOPIC = "topic";

    public static final String TAG = "tag";

    public static final String METASTORE_URIS = "metastoreUris";

    public static final String METASTORE_URI_CONF_KEY = "spark.hive.metastore.uris";

    public static final String DEFAULT_TOPIC_NAME = "DG_DataChange_Topic";

    public static final String SQL = "sql";

    public static final String DB = "db";

    public static final String USER_NAME = "userName";

    public static final String DEFAULT_USER_NAME = "unknown_user";

    public static final String HIVE_DATASOURCE_ID = "hive.datasourceId";

    public static final String DATASOURCE_ID = "datasourceId";

    public static final String SPARK_DATASOURCE_ID_CONFIG_KEY = "spark.datasourceId";

    public static final String SPARK_SQLLINEAGE_MQ_SERVERS_CONFIG_KEY = "spark.sqllineage.mq.servers";

    public static final String DEFAULT_HIVE_RESOURCE_ID = "hive";

    public static final String DEFAULT_SPARK_RESOURCE_ID = "spark";

    public static final String QUERY_ENGINE = "queryEngine";

    public static final String DEFAULT_TAG = "6";

    public static final String DEFAULT_MQ_NAME = "RocketMQ";

    public static final String KAFKA = "kafka";

}
