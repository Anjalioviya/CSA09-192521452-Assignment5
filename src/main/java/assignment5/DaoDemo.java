package assignment5;
import java.util.*;
interface Dao<T,K>{int insert(T x);T findById(K id);List<T> findAll();int update(T x);int delete(K id);}
record Student(int id,String name,String email){}
class StudentDao implements Dao<Student,Integer>{Map<Integer,Student>m=new LinkedHashMap<>();public int insert(Student s){m.put(s.id(),s);return 1;}public Student findById(Integer k){return m.get(k);}public List<Student>findAll(){return new ArrayList<>(m.values());}public int update(Student s){if(!m.containsKey(s.id()))return 0;m.put(s.id(),s);return 1;}public int delete(Integer k){return m.remove(k)==null?0:1;}}
public class DaoDemo{public static void main(String[]x){StudentDao d=new StudentDao();d.insert(new Student(1,"Arun","arun@example.test"));System.out.println(d.findById(1));System.out.println("rows="+d.findAll().size());}}
