package solovev.java.javacore.syntax;

import lombok.SneakyThrows;

public enum MyEnum {
    ONE, TWO;
    @SneakyThrows
    public MyEnum myClone(){
        return (MyEnum) this.clone();
    }
}
