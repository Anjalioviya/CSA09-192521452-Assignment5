package assignment5;
import java.io.*;import java.nio.file.*;
public class BufferBenchmark{
 static long countRaw(Path p)throws IOException{long n=0;try(InputStream in=Files.newInputStream(p)){int b;while((b=in.read())!=-1)if(b==10)n++;}return n;}
 static long countBuffered(Path p)throws IOException{long n=0;try(BufferedInputStream in=new BufferedInputStream(Files.newInputStream(p))){byte[]b=new byte[65536];int k;while((k=in.read(b))!=-1)for(int i=0;i<k;i++)if(b[i]==10)n++;}return n;}
 public static void main(String[]x)throws Exception{for(int mb:new int[]{1,10,50}){Path p=Path.of("data","log-"+mb+"MB.txt");Files.createDirectories(p.getParent());if(!Files.exists(p)||Files.size(p)!=(long)mb*1048576){byte[]row="attendance,student,present\n".getBytes();try(OutputStream o=Files.newOutputStream(p)){long left=(long)mb*1048576;while(left>0){int n=(int)Math.min(left,row.length);o.write(row,0,n);left-=n;}}}
 long t=System.nanoTime(),a=countRaw(p),ta=System.nanoTime()-t;t=System.nanoTime();long b=countBuffered(p),tb=System.nanoTime()-t;
 System.out.printf("%d MB lines=%d/%d unbuffered_ms=%.3f buffered_ms=%.3f%n",mb,a,b,ta/1e6,tb/1e6);}}
}
