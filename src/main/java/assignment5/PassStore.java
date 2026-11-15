package assignment5;
import java.io.*;import java.nio.file.*;import java.util.*;
public class PassStore {
 private final Path file; public PassStore(Path f){file=f;}
 public void add(String id,String reg,String name,int route,double amount)throws IOException{
  Files.createDirectories(file.toAbsolutePath().getParent());
  try(BufferedWriter w=Files.newBufferedWriter(file,StandardOpenOption.CREATE,StandardOpenOption.APPEND)){w.write(String.join(",",id,reg,name,""+route,""+amount));w.newLine();}
 }
 public String[] find(String id)throws IOException{
  if(!Files.exists(file))return null;
  try(BufferedReader r=Files.newBufferedReader(file)){String line;while((line=r.readLine())!=null){
   String[] f=line.split(",",-1);if(f.length!=5)continue;
   try{Integer.parseInt(f[3]);Double.parseDouble(f[4]);}catch(NumberFormatException e){continue;}
   if(f[0].equals(id))return f;
  }}return null;
 }
 public List<String[]> listByRoute(int route)throws IOException{
  List<String[]> out=new ArrayList<>();if(!Files.exists(file))return out;
  try(BufferedReader r=Files.newBufferedReader(file)){String line;while((line=r.readLine())!=null){String[]f=line.split(",",-1);if(f.length!=5)continue;try{if(Integer.parseInt(f[3])==route)out.add(f);}catch(NumberFormatException ignored){}}}return out;
 }
 public static void main(String[]x)throws Exception{
  Path p=Path.of("data","bus_passes.csv");Files.deleteIfExists(p);PassStore s=new PassStore(p);
  s.add("P1001","192311001","Arun",12,4500);Files.writeString(p,"P1002,DAMAGED LINE\nPbad,192311009,Wrong,7,not-a-number\n\n",StandardOpenOption.APPEND);s.add("P1003","192311003","Divya",7,3800);
  System.out.println("find P1003 = "+Arrays.toString(s.find("P1003")));System.out.println("find P1002 = "+Arrays.toString(s.find("P1002")));
  System.out.println("route 7 count = "+s.listByRoute(7).size());System.out.println("Damaged rows skipped; search continues.");
 }
}
