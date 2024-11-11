package solovev.java.javacore.syntax;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ExceptionInMain {
    public static void main(String[] args) throws FileNotFoundException {
        File nonExist = new File("/nonExist");
        try {
            FileInputStream stream = new FileInputStream(nonExist);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            FileInputStream stream = new FileInputStream(nonExist);
        }
    }

}
