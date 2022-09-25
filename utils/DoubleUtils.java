import java.math.BigDecimal;

/**
 * double类型工具类
 * @author sunrui
 */
public class DoubleUtils {

    /**
     * double比较大小
     * @param a double变量a
     * @param b double变量b
     * @return 返回值为int，1表示a>b，0表示a=b，-1表示a<b
     */
    public static int doubleCmp(double a, double b) {
        // 误差0.000001可以接受
        if (Math.abs(a-b) < 0.000001) {
            return 0;
        }
        // 转换成BigDecimal进行精准比较
        BigDecimal bigDecimal1 = new BigDecimal(a);
        BigDecimal bigDecimal2 = new BigDecimal(b);
        return bigDecimal1.compareTo(bigDecimal2);
    }
}
