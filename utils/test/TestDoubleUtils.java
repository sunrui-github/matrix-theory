import org.junit.Assert;

public class TestDoubleUtils {

    public static void main(String[] args) {
        testDoubleCmp();
    }

    /**
     * 测试DoubleCmp
     */
    public static void testDoubleCmp() {
        double a = 0.234;
        double b = 0.234;
        double c = 0.123;
        System.out.println(DoubleUtils.doubleCmp(a, b));
        System.out.println(DoubleUtils.doubleCmp(a, c));
        System.out.println(DoubleUtils.doubleCmp(c, b));
    }


}
