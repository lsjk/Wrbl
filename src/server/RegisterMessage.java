package server;

import dbutils.DatabaseAccess;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: myan
 * Date: 14-3-30
 * Time: 下午6:32
 * To change this template use File | Settings | File Templates.
 */
public class RegisterMessage implements CommandCallback {
    String name;
    String email;
    String password;

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    @Override
    public ReplyMessage action(String data) {
        boolean result = true;

        RegisterMessage msg = GlobalGson.sharedGson().fromJson(data, RegisterMessage.class);
        if (msg != null) {
            Object[] obj = new Object[] {
                msg.getName(),
                msg.getEmail(),
                msg.getPassword(),
                new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date())
            };
            try {
                DatabaseAccess.getInstance().
                        update("insert into dbtest.player (name, email, password, date) values (?,?,?,?)", obj);
            } catch (SQLException e) {
                //if (e.getErrorCode() == 1092
                e.printStackTrace();
                result = false;
            } finally {
                return new ReplyMessage(result, "");
            }
        }
        return null;
    }
}

