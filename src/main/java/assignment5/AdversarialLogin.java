package assignment5;
import java.sql.*;
public class AdversarialLogin{
 static boolean unsafe(Connection c,String id,String pw)throws SQLException{String q="SELECT COUNT(*) FROM users WHERE id='"+id+"' AND pwd='"+pw+"'";try(Statement s=c.createStatement();ResultSet r=s.executeQuery(q)){r.next();return r.getInt(1)>0;}}
 static boolean safe(Connection c,String id,String pw)throws SQLException{try(PreparedStatement p=c.prepareStatement("SELECT COUNT(*) FROM users WHERE id=? AND pwd=?")){p.setString(1,id);p.setString(2,pw);try(ResultSet r=p.executeQuery()){r.next();return r.getInt(1)>0;}}}
 public static void main(String[]x)throws Exception{try(Connection c=Db.connect()){String attack="' OR '1'='1' -- ";System.out.println("Unsafe accepts crafted input: "+unsafe(c,attack,"x"));System.out.println("PreparedStatement accepts crafted input: "+safe(c,attack,"x"));}}
}
