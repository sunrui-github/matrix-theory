import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试类
 * @author sunrui
 */
public class TestOrthonormalBasis {
    public static void main(String[] args) {
        // testL2Norm();
        // testNormalize();
        // testWhetherZero();
        // testTimesCal();
        // testWhetherAllEqual();
        // testWhetherMatchZero();
        // testCmpTimes();

        // testDot();

    }

    public static void testDot() {
        ComplexNumber[] a0 = {new ComplexNumber(0.0, 1.0),
                new ComplexNumber(-1.0, 0.0),
                new ComplexNumber(0.0, 1.0)};
        ComplexNumber[] a1 = {new ComplexNumber(1.0, 0.0),
                new ComplexNumber(),
                new ComplexNumber(0.0, 1.0)};
        System.out.println(OrthonormalBasis.dot(a0, a1));
    }

    /**
     * 测试求l2范数
     */
    public static void testL2Norm() {
        // 输入向量（i, -1, i）
        ComplexNumber[] input = {new ComplexNumber(0,1),
                                    new ComplexNumber(-1,0),
                                    new ComplexNumber(0,1)};
        // 测试算得结果
        double output = OrthonormalBasis.l2Norm(input);
        // 期望结果 根号3
        double except = Math.sqrt(3);
        // 比较结果, 第三个double参数：允许误差
        Assert.assertEquals(except, output,  0.0001);
    }

    /**
     * 测试单位化
     */
    public static void testNormalize() {
        // 输入向量（i, -1, i）
        ComplexNumber[] input = {new ComplexNumber(0,1),
                new ComplexNumber(-1,0),
                new ComplexNumber(0,1)};
        // 计算方法返回
        ComplexNumber[] output = OrthonormalBasis.normalize(input);
        // 计算范数
        double l2 = OrthonormalBasis.l2Norm(input);
        // 期望结果
        ComplexNumber[] expect = {new ComplexNumber(0,1/l2),
                new ComplexNumber(-1/l2,0),
                new ComplexNumber(0,1/l2)};

        // 比较结果
        Assert.assertArrayEquals(output, expect);
    }

    /**
     * 测试是否为0向量
     */
    public static void testWhetherZero() {
        ComplexNumber[] input1 ={new ComplexNumber(), new ComplexNumber()};
        ComplexNumber[] input2 = {new ComplexNumber(1,0), new ComplexNumber()};
        System.out.println(OrthonormalBasis.whetherZero(input1));
        System.out.println(OrthonormalBasis.whetherZero(input2));
    }

    /**
     * 测试计算两个复数的倍数
     */
    public static void testTimesCal() {
        ComplexNumber a = new ComplexNumber(1.1, -2.0);
        ComplexNumber b = new ComplexNumber(2.2, -4.0);
        ComplexNumber c = new ComplexNumber(0, -4.0);
        ComplexNumber d = new ComplexNumber(0, -3.0);
        ComplexNumber e = new ComplexNumber();


        Assert.assertEquals(2.0, OrthonormalBasis.timesCal(a,b), 0.001);
        Assert.assertEquals(0.5, OrthonormalBasis.timesCal(b,a), 0.001);
        Assert.assertEquals(1.0, OrthonormalBasis.timesCal(b,b), 0.001);
        Assert.assertEquals(0.75, OrthonormalBasis.timesCal(c,d), 0.001);
        Assert.assertEquals(0.0, OrthonormalBasis.timesCal(c,e), 0.001);
        Assert.assertEquals(1.0, OrthonormalBasis.timesCal(c,c), 0.001);



    }

    /**
     * 测试double数组中的元素是否都相等
     */
    public static void testWhetherAllEqual() {
        double[] a = {0.1, 0.1, 0.1};
        double[] b = {0.1, 0.2, 0.1};
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        list2.add(1);
        System.out.println(OrthonormalBasis.whetherAllEqual(a, list1));
        System.out.println(OrthonormalBasis.whetherAllEqual(b, list2));
    }

    /**
     * 测试检查两个向量对应位置是否都是0
     */
    public static void testWhetherMatchZero() {
        ComplexNumber[] a = {new ComplexNumber(), new ComplexNumber(1.0, 2.0)};
        ComplexNumber[] b = {new ComplexNumber(), new ComplexNumber(2.0, 4.0)};
        System.out.println(OrthonormalBasis.whetherMatchZero(a, b, 0));
        System.out.println(OrthonormalBasis.whetherMatchZero(a, b, 1));
    }

    /**
     * 测试两个复数向量是否成比例
     */
    public static void testCmpTimes() {
        ComplexNumber[] a = {new ComplexNumber(), new ComplexNumber(1.0, 2.0)};
        ComplexNumber[] b = {new ComplexNumber(), new ComplexNumber(2.0, 4.0)};
        ComplexNumber[] c = {new ComplexNumber(), new ComplexNumber()};
        System.out.println(OrthonormalBasis.cmpTimes(a, b));
        System.out.println(OrthonormalBasis.cmpTimes(a, c));
        //System.out.println(OrthonormalBasis.cmpTimes(c, c));
    }

    /**
     * 测试极大线性无关组
     */
    public static void testOrthonormalize() {
        ComplexNumber[] a0 = {new ComplexNumber(1, 1), new ComplexNumber(), new ComplexNumber()};
        ComplexNumber[] a1 = {new ComplexNumber(2, 2), new ComplexNumber(), new ComplexNumber()};
        ComplexNumber[] a2 = {new ComplexNumber(1, -1), new ComplexNumber(), new ComplexNumber()};
        ComplexNumber[] a3 = {new ComplexNumber(), new ComplexNumber(1, 2), new ComplexNumber()};
        ComplexNumber[] a4 = {new ComplexNumber(), new ComplexNumber(), new ComplexNumber()};
    }







}
