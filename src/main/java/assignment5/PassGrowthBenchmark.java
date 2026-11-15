package assignment5;
import java.nio.file.*;
public class PassGrowthBenchmark{public static void main(String[]x)throws Exception{Path p=Path.of("data","growth.csv");Files.deleteIfExists(p);PassStore s=new PassStore(p);for(int n=1;n<=100000;n++){s.add("P"+n,"R"+n,"Student"+n,n%20,1000+n);if(n==1000||n==10000||n==50000||n==100000){long t=System.nanoTime();boolean ok=s.find("P"+n)!=null;System.out.printf("records=%d found=%s elapsed_ms=%.3f%n",n,ok,(System.nanoTime()-t)/1e6);}}}}
