package personas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reader extends User {
    public Reader(int id, String name, String key, int isAdmin, double taxes) {
        super(id, name, key, isAdmin, taxes);
    }

    public Reader(String name, String key, int isAdmin, double taxes) {
        super(name, key, isAdmin, taxes);
    }
}
