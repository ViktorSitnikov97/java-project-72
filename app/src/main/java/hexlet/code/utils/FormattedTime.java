package hexlet.code.utils;

import java.util.Date;
import java.sql.Timestamp;

public class FormattedTime {
    public static Timestamp getCurrentTime() {
        Date currentDate = new Date();
        Timestamp timestamp = new Timestamp((currentDate.getTime()));
        return timestamp;
    }
}