package server;

/**
 * Created with IntelliJ IDEA.
 * User: myan
 * Date: 14-3-30
 * Time: 下午5:02
 * To change this template use File | Settings | File Templates.
 */


public class GenericMessage {
    public String cmd;
    public String data;

    public GenericMessage(String cmd, String data) {
        this.cmd = cmd;
        this.data = data;
    }
}


