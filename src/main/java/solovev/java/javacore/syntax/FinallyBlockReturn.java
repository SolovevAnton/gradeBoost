package solovev.java.javacore.syntax;

import org.junit.jupiter.api.Test;

public class FinallyBlockReturn {
    private int div(int n) {
        try {
            return 1 / n;
        } finally {
            System.out.println("In block");
        }
    }

    @Test
    void tryNormal() {
        System.out.println(div(1));
        /*out:
         In block
         1
         */
    }

    @Test
    void tryThrow() {
        System.out.println(div(0));

    }
}
