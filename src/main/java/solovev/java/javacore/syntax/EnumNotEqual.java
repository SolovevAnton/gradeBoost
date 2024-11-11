package solovev.java.javacore.syntax;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class EnumNotEqual {

    public static class CustomClassLoader extends ClassLoader {
        private final Path classDir;

        public CustomClassLoader(Path classDir) {
            this.classDir = classDir;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            Path classPath = classDir.resolve(name.replace('.', '/') + ".class");
            if (!Files.exists(classPath)) {
                return super.findClass(name);
            }
            try {
                byte[] classBytes = Files.readAllBytes(classPath);
                return defineClass(name, classBytes, 0, classBytes.length);
            } catch (IOException e) {
                throw new ClassNotFoundException("Cannot load class " + name, e);
            }
        }
    }

    @Test
    public void cloneEnum(){
        assertThrows(CloneNotSupportedException.class,  () -> MyEnum.ONE.myClone());
    }

    @Test
    public void loadEmunsOnDifferentClassLoaders() throws ClassNotFoundException {
        // Define the directory containing your compiled enum class
        Path classDir = Paths.get("target/classes/solovev/java/javacore/syntax/MyEnum.class");

        // Load MyEnum class with two different class loaders
        CustomClassLoader loader1 = new CustomClassLoader(classDir);
        CustomClassLoader loader2 = new CustomClassLoader(classDir);

        // Load MyEnum class from both class loaders
        Class<?> enumClass1 = loader1.loadClass("solovev.java.javacore.syntax.MyEnum");
        Class<?> enumClass2 = loader2.loadClass("solovev.java.javacore.syntax.MyEnum");

        // Retrieve enum instances for each class loader
        MyEnum enumConstants1 = (MyEnum) enumClass1.getEnumConstants()[0];
        MyEnum  enumConstants2 = (MyEnum) enumClass2.getEnumConstants()[0];

        assertEquals(MyEnum.ONE.ordinal(), enumConstants1.ordinal());
        assertEquals(MyEnum.ONE.ordinal(), enumConstants2.ordinal());

        //not working =(
        assertNotEquals(MyEnum.ONE, enumConstants1);
        assertNotEquals(MyEnum.ONE, enumConstants2);
        assertFalse(MyEnum.ONE == enumConstants1);
        assertFalse(MyEnum.ONE == enumConstants2);
    }


}
