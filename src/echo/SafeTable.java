package echo;

import java.util.HashMap;

public class SafeTable extends HashMap<String,String> {



    // want to override get and put methods from HashMap class and add synchronized modifer
    // synchronized makes these methods thread safe

    @Override
    public synchronized String put(String key, String value) {
        return super.put(key, value);
    }

    @Override
    public synchronized String get(Object key) {
        return super.get(key);
    }
}
