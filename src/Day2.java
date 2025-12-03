import java.util.*;
import java.io.*;
import java.util.regex.*;

public class Day2 {
    public static void main(String[] args) throws IOException{
        String file = "src/input.txt";
        withRegex(file);
        noRegex(file);
    }

    static void withRegex(String file) throws IOException {
        Scanner scan = new Scanner(new File(file));
        scan.useDelimiter(",");
        long p1=0; long p2=0;

        String[] ids = scan.nextLine().split(",");
        for (String item : ids) {
            String[] range = item.split("-");
            for (long i=Long.parseLong(range[0]); i<=Long.parseLong(range[1]); i++) {
                String id = Long.toString(i);
                if (id.matches("(\\d+)\\1")) {
                    p1 += i;
                }
                if (id.matches("(\\d+)\\1+")) {
                    p2 += i;
                }
            }
        }

        System.out.println(p1+" "+p2);
    }

    static void noRegex(String file) throws IOException {
        Scanner scan = new Scanner(new File(file));
        scan.useDelimiter(",");
        long p1=0; long p2=0;
        String[] ids = scan.nextLine().split(",");

        for (String item : ids) {
            String[] range = item.split("-");
            for (long i=Long.parseLong(range[0]); i<=Long.parseLong(range[1]); i++) {
                String id = Long.toString(i);

                if (id.substring(0,id.length()/2).equals(id.substring(id.length()/2))) {
                    p1 += i;
                }

                for (int len=1; len<id.length(); len++) {
                    if (id.length()%len != 0) continue;
                    String ss = id.substring(0,len);
                    String test = ss.repeat(id.length()/len);
                    if (test.equals(id)) {
                        p2 += i;
                        break;
                    }
                }
            }
        }

        System.out.println(p1+" "+p2);
    }
}
