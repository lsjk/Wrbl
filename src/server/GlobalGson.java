package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: myan
 * Date: 14-3-30
 * Time: 下午5:01
 * To change this template use File | Settings | File Templates.
 */
public class GlobalGson {
    static private Gson instance = null;

    static public Gson sharedGson() {
        if (instance == null)
            instance = new GsonBuilder().disableHtmlEscaping().create();
        return instance;
    }
}
