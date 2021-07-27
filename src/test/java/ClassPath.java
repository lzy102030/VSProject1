import java.io.UnsupportedEncodingException;

public class ClassPath {
    public static void main(String[] args) {
        String realPath = ClassPath.class.getClassLoader().getResource("")
                .getFile();
        java.io.File file = new java.io.File(realPath);
        realPath = file.getParentFile().getAbsolutePath();

        System.out.println(realPath);
    }
}
