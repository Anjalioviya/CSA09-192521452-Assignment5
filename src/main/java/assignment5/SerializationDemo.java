package assignment5;
import java.io.*;import java.util.*;
class Resident implements Serializable{private static final long serialVersionUID=1L;String name,regNo;transient String password;transient Object liveConnection;Resident(String n,String r,String p){name=n;regNo=r;password=p;liveConnection=new Object();}}
class Room implements Serializable{private static final long serialVersionUID=1L;List<Resident>residents=new ArrayList<>();}
class Hostel implements Serializable{private static final long serialVersionUID=1L;List<Room>rooms=new ArrayList<>();}
public class SerializationDemo{public static void main(String[]x)throws Exception{Hostel h=new Hostel();Room r=new Room();r.residents.add(new Resident("Divya","192311003","secret"));h.rooms.add(r);try(ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream("data/hostel.ser"))){o.writeObject(h);}try(ObjectInputStream i=new ObjectInputStream(new FileInputStream("data/hostel.ser"))){Resident z=((Hostel)i.readObject()).rooms.get(0).residents.get(0);System.out.println("resident="+z.name+", reg="+z.regNo+", password="+z.password+", liveConnection="+z.liveConnection);}}}
