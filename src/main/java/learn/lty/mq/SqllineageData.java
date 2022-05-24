package learn.lty.mq;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;


/**
 * @author liangty1
 */
public class SqllineageData {

    @Getter
    @Setter
    private String queryEngine;

    @Getter
    @Setter
    private Map<String, String> data;

    public SqllineageData(String queryEngine, Map<String, String> data) {
        this.queryEngine = queryEngine;
        this.data = data;
    }

}
