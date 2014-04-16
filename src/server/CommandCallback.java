package server;

/**
 * Created with IntelliJ IDEA.
 * User: myan
 * Date: 14-3-30
 * Time: 下午5:00
 * To change this template use File | Settings | File Templates.
 */

public interface CommandCallback {
    ReplyMessage action(String data);
}
