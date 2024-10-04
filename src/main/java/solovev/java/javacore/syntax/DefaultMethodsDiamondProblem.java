package solovev.java.javacore.syntax;

public class DefaultMethodsDiamondProblem {

    interface Interface1 {
        void method1(String str);
        default void log() {
            System.out.println("The default method1" );
        }
        void sameInInterfaces();
    }
    interface Interface2 {
        void method2();
        default void log() {
            System.out.println("The default method2");
        }
        void sameInInterfaces();
    }
    static class Class1 implements Interface1, Interface2 {
        @Override
        public void method1(String str) {
        }

        @Override
        public void sameInInterfaces() {
        }

        @Override
        public void method2() {
        }

        @Override //WITHOUT this line compilation ERROR will occur
        public void log(){
            System.out.println("Class1") ;
        }
    }

    public static void main(String[] args) {
        var test = new Class1();
        test.log();
    }
}
