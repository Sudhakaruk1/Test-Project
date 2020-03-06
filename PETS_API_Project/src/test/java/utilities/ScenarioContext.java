package utilities;

import java.util.HashMap;
import java.util.Map;


public class ScenarioContext {

    private Map<String, Object> contextKey;

    public ScenarioContext() {
        contextKey = new HashMap<>();
    }

    public void setContext(Context key, Object value) {
        contextKey.put(key.toString(), value);
    }

    public Object getContext(Context key) {
        return contextKey.get(key.toString());
    }

    public Boolean isContains(Context key) {
        return contextKey.containsKey(key.toString());
    }
}

