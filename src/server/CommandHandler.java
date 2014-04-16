package server;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: myan
 * Date: 14-3-30
 * Time: 下午5:04
 * To change this template use File | Settings | File Templates.
 */

public class CommandHandler {
    private static CommandHandler instance = null;
    private Map<Integer, CommandCallback> map = new HashMap<Integer, CommandCallback>();

    private CommandHandler() {

    }

    static public CommandHandler sharedHandler() {
        if (instance == null)
            instance = new CommandHandler();
        return instance;
    }

    public void register(int cmd, CommandCallback cb) {
        if (CommandType.check(cmd)) {
            System.out.format("register command:%d,with handler:%s\n", cmd, cb.getClass());
            map.put(cmd, cb);
        }
    }

    public ReplyMessage call(int cmd, String data) {
        CommandCallback cb = map.get(cmd);
        if (cb != null) {
            return cb.action(data);
        }
        return null;
    }
}