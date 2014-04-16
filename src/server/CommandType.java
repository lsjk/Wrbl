package server;

/**
 * Created with IntelliJ IDEA.
 * User: myan
 * Date: 14-3-30
 * Time: 下午4:31
 * To change this template use File | Settings | File Templates.
 */
public final class CommandType {
    public static final int cmdInvalidMin = 0;
    public static final int cmdLogin = 1;
    public static final int cmdShortMessage = 2;
    public static final int cmdReplyMessage = 3;
    public static final int cmdRegister = 5;
    public static final int cmdInvalidMax = 6;

    public static boolean check(int cmd) {
        if (cmd <= CommandType.cmdInvalidMin || cmd >= CommandType.cmdInvalidMax)
            return false;
        else
            return true;
    }
}
