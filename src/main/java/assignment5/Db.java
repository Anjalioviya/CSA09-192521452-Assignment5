package assignment5;
import java.sql.*;
public final class Db {
 private Db(){}
 public static Connection connect() throws SQLException {
  String u=System.getenv().getOrDefault("DB_URL","jdbc:mysql://localhost:3306/assignment5?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
  return DriverManager.getConnection(u,System.getenv().getOrDefault("DB_USER","root"),System.getenv().getOrDefault("DB_PASSWORD",""));
 }
}
