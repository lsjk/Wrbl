package server;

import com.sun.javaws.exceptions.InvalidArgumentException;

import javax.print.attribute.standard.DateTimeAtCompleted;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: myan
 * Date: 14-3-30
 * Time: 下午4:58
 * To change this template use File | Settings | File Templates.
 */

import dbutils.DatabaseAccess;
import org.apache.commons.dbutils.handlers.BeanHandler;


public class LoginMessage implements CommandCallback {
    private String username;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private boolean check() {

        boolean ret = false;

        if (this.username == null || this.password == null)
            return ret;

        String sql = "select * from dbtest.player where username = " + this.username;

        try {
            RegisterMessage msg = DatabaseAccess.getInstance().
                    query(sql, new BeanHandler<RegisterMessage>(RegisterMessage.class), null);

            if (msg.password.equals(this.password))
                ret = true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return ret;
        }
    }

    @Override
    public ReplyMessage action(String data) {

        LoginMessage msg = GlobalGson.sharedGson().fromJson(data, LoginMessage.class);
        if (msg.check() == true) {
            return new ReplyMessage(true, "");
        }
        return new ReplyMessage(false, "用户名不存在或密码错误");
    }
}



