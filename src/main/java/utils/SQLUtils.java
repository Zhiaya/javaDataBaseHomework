package utils;

public class SQLUtils {
    public static Object UserSQL;

    public  static class PersonSQL{

        // all constant must in uppercase
        public static final String getAllPersonSql= """
            select * from person;
            """;
        public static final String insertNewPerson= """
                INSERT INTO person ("fullname","gender","email","address") VALUES(?,?,?,?);
                """;

        public  static final String deletePersonById = """
                delete from person where id = ?
                """;

        public  static final String updatePerson= """
                update person set  fullname=?,gender=?,email=?,address=?
                where id = ?
                """;
    }
    public static class SystemUserSQL {
        public static final String loginUser = """
                SELECT * FROM system_user WHERE username = ? AND password = ?;
                """;
    }
}
