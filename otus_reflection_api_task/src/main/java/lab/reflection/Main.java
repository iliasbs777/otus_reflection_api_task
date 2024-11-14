package lab.reflection;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            Class<TestClass> cl = TestClass.class;
            TestExecutor.execute(cl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}