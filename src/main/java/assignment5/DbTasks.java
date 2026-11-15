package assignment5;
import java.sql.*;import java.util.*;
public class DbTasks{
 static void bind(PreparedStatement p,String[]a)throws SQLException{p.setString(1,a[0]);p.setString(2,a[1]);p.setString(3,a[2]);p.setInt(4,Integer.parseInt(a[3]));p.setDouble(5,Double.parseDouble(a[4]));}
 static List<String[]> data(int n){List<String[]>d=new ArrayList<>(n);for(int i=1;i<=n;i++)d.add(new String[]{"P"+i,"R"+i,"Student"+i,""+(i%20),""+(1000+i)});return d;}
 static void clear(Connection c)throws SQLException{try(Statement s=c.createStatement()){s.executeUpdate("DELETE FROM bus_pass");}}
 static long prepared(Connection c,List<String[]>d)throws SQLException{clear(c);long t=System.nanoTime();try(PreparedStatement p=c.prepareStatement("INSERT INTO bus_pass VALUES(?,?,?,?,?)")){for(String[]a:d){bind(p,a);p.executeUpdate();}}return System.nanoTime()-t;}
 static long statement(Connection c,List<String[]>d)throws SQLException{clear(c);long t=System.nanoTime();try(Statement s=c.createStatement()){for(String[]a:d)s.executeUpdate("INSERT INTO bus_pass VALUES('"+a[0]+"','"+a[1]+"','"+a[2]+"',"+a[3]+","+a[4]+")");}return System.nanoTime()-t;}
 static long batch(Connection c,List<String[]>d,boolean inject)throws SQLException{clear(c);boolean old=c.getAutoCommit();c.setAutoCommit(false);long t=System.nanoTime();try(PreparedStatement p=c.prepareStatement("INSERT INTO bus_pass VALUES(?,?,?,?,?)")){int i=0;for(String[]a:d){if(inject&&i==d.size()/2)a=new String[]{a[0],a[1],a[2],"BAD",a[4]};bind(p,a);p.addBatch();i++;if(i%1000==0)p.executeBatch();}p.executeBatch();c.commit();}catch(Exception e){c.rollback();if(e instanceof SQLException q)throw q;throw new SQLException(e);}finally{c.setAutoCommit(old);}return System.nanoTime()-t;}
 public static void main(String[]x)throws Exception{try(Connection c=Db.connect()){for(int n:new int[]{10,1000,10000,100000}){List<String[]>d=data(n);System.out.printf("n=%d Statement_ms=%.3f Prepared_ms=%.3f Batch_tx_ms=%.3f%n",n,statement(c,d)/1e6,prepared(c,d)/1e6,batch(c,d,false)/1e6);}
 try{batch(c,data(100),true);}catch(SQLException e){System.out.println("Injected batch failure: "+e.getMessage());}
 try(Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT COUNT(*) FROM bus_pass")){r.next();System.out.println("Rows after rollback="+r.getInt(1));}
 DatabaseMetaData m=c.getMetaData();System.out.println("DB="+m.getDatabaseProductName()+" "+m.getDatabaseProductVersion());System.out.println("Driver="+m.getDriverName()+" "+m.getDriverVersion());try(ResultSet r=m.getTables(c.getCatalog(),null,"%",new String[]{"TABLE"})){while(r.next())System.out.println("Table="+r.getString("TABLE_NAME"));}}}
}
