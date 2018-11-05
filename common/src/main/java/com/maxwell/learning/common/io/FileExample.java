package com.maxwell.learning.common.io;

import com.maxwell.learning.common.comparatorexample.Person;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class FileExample {


    @Test
    public void testFileWriter() {
        try (FileWriter fw = new FileWriter("demo.txt");) {
            fw.write("abcde");
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFileReader() {
        //要保证该文件是已经存在的，如果不存在，会发生异常FileNotFoundException
        try (FileReader fileReader = new FileReader("demo.txt");) {
            int ch = 0;
            while ((ch = fileReader.read()) != -1) {
                System.out.println("ch=" + (char) ch);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFileReader2() {
        //要保证该文件是已经存在的，如果不存在，会发生异常FileNotFoundException
        try (FileReader fileReader = new FileReader("demo.txt");) {
            char[] buf = new char[1024];
            int num = 0;
            while ((num = fileReader.read(buf)) != -1) {
                System.out.println(new String(buf, 0, num));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testFileStream() {
        try (OutputStream outputStream = new FileOutputStream("/Users/yue/Desktop/copy.png");
             InputStream inputStream = new FileInputStream("/Users/yue/Desktop/test.png");
        ) {
            byte[] buffer = new byte[1024 * 1024];
            while (inputStream.read(buffer) != -1) {
                outputStream.write(buffer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 1380
     */
    @Test
    public void testFileStream2() {
        long start = System.currentTimeMillis();
        try (OutputStream outputStream = new FileOutputStream("/Users/yue/Desktop/copy.ev4");
             InputStream inputStream = new FileInputStream("/Users/yue/Desktop/test.ev4");
        ) {
            byte[] buffer = new byte[1024 * 1024];
            while (inputStream.read(buffer) != -1) {
                outputStream.write(buffer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("不使用缓冲，耗时：" + (end - start));
    }

    /**
     * 文件842572164byte
     */
    @Test
    public void testBufferedStream() {
        long start = System.currentTimeMillis();
        try (
                InputStream inputStream = new FileInputStream("/Users/yue/Desktop/test.ev4");
                InputStream bis = new BufferedInputStream(inputStream, 1024 * 1024);
                OutputStream outputStream = new FileOutputStream("/Users/yue/Desktop/copy.ev4");
                OutputStream bos = new BufferedOutputStream(outputStream, 1024 * 1024);
        ) {
            byte[] buffer = new byte[1024 * 1024];
            while ((bis.read(buffer)) != -1) {
                bos.write(buffer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("使用缓冲，耗时：" + (end - start));
    }

    @Test
    public void testDataStream() {
        try (
                DataOutputStream dos = new DataOutputStream(new FileOutputStream("test.txt"));
                DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream("test.txt")));
        ) {
            dos.writeChars("abc");
            dos.writeBoolean(Boolean.TRUE);
            dos.writeInt(123);

            System.out.println(dis.readChar());
            System.out.println(dis.readChar());
            System.out.println(dis.readChar());
            System.out.println(dis.readBoolean());
            System.out.println(dis.readInt());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testObjectStream() {
        //写入
        try (
                ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("object.ar")));
        ) {
            Person person1 = new Person("xiaohong", 12);
            Person person2 = new Person("xiaoming", 12);

            oos.writeObject(person1);
            oos.writeObject(person2);
            oos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //读取
        try (
                ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("object.ar")));
        ) {
            Person xiaohong = (Person) ois.readObject();
            Person xiaoming = (Person) ois.readObject();

            System.out.println(xiaohong);
            System.out.println(xiaoming);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testFile() {
        Path path = Paths.get("C:\\mystuff.txt");
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("C:\\my.ini"), StandardCharsets.UTF_8);
            String str = null;
            while ((str = reader.readLine()) != null) {
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\my.ini"), Charset.forName("UTF-8")))
        ) {
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get("C:\\my2.ini"), StandardCharsets.UTF_8);
            writer.write("测试文件写操作");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Path dir = Paths.get("D:\\webworkspace");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path e : stream) {
                System.out.println(e.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Stream<Path> stream = Files.list(Paths.get("C:/"))) {
            Iterator<Path> ite = stream.iterator();
            while (ite.hasNext()) {
                Path pp = ite.next();
                System.out.println(pp.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Path startingDir = Paths.get("C:\\apache-tomcat-8.0.21");
        try {
            Files.walkFileTree(startingDir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                    if (path.toString().endsWith(".java")) {
                        System.out.println(path.getFileName());
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRandomAccessFile() throws IOException {
        RandomAccessFile raf = new RandomAccessFile("xxx.rr", "rw");
        //写入10个字符，占用20个字节
        raf.writeChars("aaaaabbbbb");
        raf = new RandomAccessFile("xxx.rr", "rw");
        //定位到第10个字节，读的时候会从第11个字节开始读
        raf.seek(10);
        for (int i = 0; i < 5; i++) {
            char c = raf.readChar();
            System.out.println(String.valueOf(c));
        }
        raf.close();
    }


    @Test
    public void testAppend() throws IOException {
        RandomAccessFile raf = new RandomAccessFile("xx.txt", "rw");
        raf.write("aaaaa".getBytes(Charset.defaultCharset()));
        long length = raf.length();
        raf.seek(length);
        raf.writeChars("bbbbb");
        raf.close();
    }

    @Test
    public void testFilePath() throws IOException {
        Path path = Paths.get("/Users", "yue", "Desktop", "test.ev4");

        RandomAccessFile file = new RandomAccessFile(path.toFile(), "rw");


        String path1 = "/Users/yue/Desktop/test.ev4";
    }

    public static byte[] readAllBytes(String fileName, long maxSize) throws IOException {
        Path path = Paths.get(fileName);
        long size = Files.size(path);
        if (size > maxSize) {
            throw new IOException("file: " + path + ", size:" + size + "> " + maxSize);
        }
        return Files.readAllBytes(path);
    }

    public static List<String> readAllLines(String fileName, Charset charset, long maxSize) throws IOException {
        Path path = Paths.get(fileName);
        long size = Files.size(path);
        if (size > maxSize) {
            throw new IOException("file: " + path + ", size:" + size + "> " + maxSize);
        }
        return Files.readAllLines(path, charset);
    }

    public static boolean isSubFile(File parent, File child) throws IOException {
        return child.getCanonicalPath().startsWith(parent.getCanonicalPath());
    }

    public static boolean isSubFile(String parent, String child) throws IOException {
        return isSubFile(new File(parent), new File(child));
    }

    @Test
    public void testFileParent() throws IOException {
        Path path = Paths.get("/Users", "yue", "Desktop");


        System.out.println(path.toFile().getAbsolutePath());
    }


}
