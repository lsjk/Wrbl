package server;

/**
 * Created with IntelliJ IDEA.
 * User: myan
 * Date: 14-3-30
 * Time: 下午4:59
 * To change this template use File | Settings | File Templates.
 */

public class ReplyMessage {
    public boolean result;
    public String reason;

    ReplyMessage(boolean r, String re) {
        this.result = r;
        this.reason = re;
    }
}
