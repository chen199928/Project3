
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;
import java.io.File; 
import java.io.IOException;  

public class Runner {
    static final int NumRecs = 1024; // Each record holds 8 bytes. Each block has 8192 bytes
    
    /** Initialize the random variable */
    static private Random value = new Random(); // Hold the Random class object

        
    static int randInt() {
        return value.nextInt(Integer.MAX_VALUE );
    }

    static float randFloat() {
        return value.nextFloat()*Float.MAX_VALUE;
    }


    public static void main(String[] args) throws IOException {

        
       DataInputStream input = new DataInputStream(new BufferedInputStream(new FileInputStream("Sampledata_16blocks.bin")));
       RandomAccessFile rfile = new RandomAccessFile("Sampledata_16blocks.bin", "r"); 
       FileWriter myWriter = new FileWriter("filename.txt");
      

       //rfile.readLine();
       int pointer = 0;
//       byte[] intarray = new byte[4];
//       byte[] floatarray = new byte[4];
//       rfile.seek(0);
//       rfile.read(intarray);asdasda
//       System.out.print(convertByteArrayToInteger(intarray) + "  rfile.length() - 1  ");
       while (pointer <= rfile.length() - 1) {
           byte[] intarray = new byte[4];
           byte[] floatarray = new byte[4];
           rfile.seek(pointer);
           rfile.read(intarray);
           //System.out.print();
           //break;
           myWriter.write(convertByteArrayToInteger(intarray) + "    ");
           pointer = pointer + 4;
           rfile.seek(pointer);
           rfile.read(floatarray);
           myWriter.write(String.valueOf(convertByteArrayToFloat(floatarray)) + "\n");
           pointer = pointer + 4;
       }
    
       myWriter.close();
 
    }
    public static int convertByteArrayToInteger(byte[] intBytes)
    {
      ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.BYTES);
      byteBuffer.put(intBytes);
      byteBuffer.flip();
      return byteBuffer.getInt();
    }
    
    public static float convertByteArrayToFloat(byte[] floatBytes)
    {
      ByteBuffer byteBuffer = ByteBuffer.allocate(Float.BYTES);
      byteBuffer.put(floatBytes);
      byteBuffer.flip();
      return byteBuffer.getFloat();
    }
}

