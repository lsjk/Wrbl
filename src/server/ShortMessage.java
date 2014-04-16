package server;

import dbutils.DatabaseAccess;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: myan
 * Date: 14-3-30
 * Time: 下午2:28
 * To change this template use File | Settings | File Templates.
 */

public class ShortMessage extends Object {

    long id;
    String sender;
    String recver;
    String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRecver() {

        return recver;
    }

    public void setRecver(String recver) {
        this.recver = recver;
    }

    public String getSender() {

        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static ShortMessage getObjectFromDb() {
        ShortMessage msg = null;

        try {
            msg = (ShortMessage) DatabaseAccess.getInstance().
                    query("select * from dbtest.shortmsg",
                            new BeanHandler(ShortMessage.class), (Object)null);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return msg;
        }
    }

    public static void setMessageToDb(ShortMessage msg) {
        if (msg == null) {
            return;
        }

        String sql = "insert into dbtest.shortmsg " +
                "(id, sender, recver, data) values (?,?,?,?)";
        Object[] obj = new Object[] {
                msg.getId(),
                msg.getSender(),
                msg.getRecver(),
                msg.getData(),
        };

        try {
            DatabaseAccess.getInstance().update(sql, obj);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return String.valueOf(id) + ":" + sender + ":"  + recver + ":" + data;
    }
}
