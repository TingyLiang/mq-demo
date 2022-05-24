package learn.lty.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import learn.lty.config.Configs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liangty1
 */
@Slf4j
public class MQUtils {
    private static final Logger logger = LoggerFactory.getLogger(MQUtils.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String msgToJson(Object kv) {
        try {
            return MAPPER.writer().writeValueAsString(kv);
        } catch (JsonProcessingException e) {
            logger.error("Error while converting message {} to json，reason {}", kv, e.getMessage());
        }
        return null;
    }

    public static Map<String, String> resolveMQConfigs(String confString) {
        Map<String, String> conf = new HashMap<>(4);
        if (null != confString) {
            String[] confs = confString.split(",");
            for (String confStr : confs) {
                String[] str = confStr.split(":");
                conf.put(str[0].trim(), str[1].trim());
            }
        }
        if (conf.isEmpty()) {
            log.debug("Config sqllineage.mq.configs is not set, default configs will be used.");
            conf.put(Configs.MQ_TYPE, Configs.DEFAULT_MQ_NAME);
            conf.put(Configs.TAG, Configs.DEFAULT_TAG);
            conf.put(Configs.TOPIC, Configs.DEFAULT_TOPIC_NAME);
        }
        return conf;
    }

    public static boolean commandValid(String command) {
        if (StringUtils.isEmpty(command)) {
            return false;
        }
        String lowerCaseCmd = command.toLowerCase().trim();
        if (lowerCaseCmd.startsWith("use")
                || lowerCaseCmd.startsWith("show")
                || lowerCaseCmd.startsWith("alter")
                || lowerCaseCmd.startsWith("truncate")
                || lowerCaseCmd.startsWith("drop")) {
            return false;
        }
        return true;
    }
}
