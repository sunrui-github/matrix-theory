import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 输入任意一组向量，求规范正交基
 * @author sunrui
 */
public class OrthonormalBasis {

    public static void main(String[] args) {
        ComplexNumber[] a0 = {new ComplexNumber(0.0, 1.0),
                new ComplexNumber(-1.0, 0.0),
                new ComplexNumber(0.0, 1.0)};
        ComplexNumber[] a1 = {new ComplexNumber(1.0, 0.0),
                new ComplexNumber(),
                new ComplexNumber(0.0, 1.0)};
        ComplexNumber[] a2 = {new ComplexNumber(1.0, 0.0),
                new ComplexNumber(1.0, 0.0),
                new ComplexNumber(1.0, 0.0)};
        ComplexNumber[][] a = {a0, a1, a2};
        // 求极大线性无关组
        ComplexNumber[][] aRes = getMaxIndependence(a);
        // 规范正交化
        ComplexNumber[][] output = orthonormalize(a);
        System.out.println(Arrays.deepToString(output));
    }



    /**
     * 计算l2范数
     * @param b 需要计算l2范数的向量
     * @return 该向量的l2范数
     */
    public static double l2Norm(ComplexNumber[] b) {
        if (b == null || b.length == 0) {
            System.err.println("求l2范数时，传入向量不可为null。");
        }
        double sum = 0.0;
        for (int i = 0; i < b.length; i++) {
            // 复数的内积注意考虑
            sum += (b[i].getRealPart() * b[i].getRealPart() + b[i].getImaginaryPart() * b[i].getImaginaryPart());
        }
        return Math.sqrt(sum);
    }

    /**
     * 单位化
     * @param b 需要被单位化的向量
     * @return 单位化后的向量
     */
    public static ComplexNumber[] normalize(ComplexNumber[] b) {
        if (b == null || b.length == 0) {
            System.err.println("单位化时，传入向量不可为null。");
        }
        // 求l2范数
        double l2 = l2Norm(b);
        ComplexNumber[] bRes = new ComplexNumber[b.length];
        for (int i = 0; i < b.length; i++) {
            bRes[i] = new ComplexNumber(b[i].getRealPart() / l2, b[i].getImaginaryPart() / l2);
        }
        return bRes;
    }

    /**
     * 规范正交化
     * @param a 极大线性无关组
     * @return 规范正交基
     */
    @Contract(pure = true)
    public static ComplexNumber[][] orthonormalize(ComplexNumber[] @NotNull [] a) {
        int axis0 = a.length;
        int axis1 = a[0].length;
        ComplexNumber[][] output = new ComplexNumber[axis0][axis1];

        //对第一个向量进行规范正交化
        ComplexNumber[] beta1 = a[0];
        ComplexNumber[] epsilon1 = normalize(beta1);
        output[0] = epsilon1;
        // for循环对a中剩下的向量使用公式处理
        for (int i = 1; i < a.length; i++) {
            forEachOrthonormalize(output, a, i);
        }
        return output;
    }


    /**
     * 对第i个向量进行正交规范化
     * 公式：
     * beta_i = a_i - <a_i, epsilon1> * epsilon1
     *              - <a_i, epsilon2> * epsilon2
     *              -....
     *              -<a_i, epsilon_i-1> * epsilon_i-1
     * @param output 输出
     * @param a 输入
     * @param i 第i个位置
     */
    public static void forEachOrthonormalize(ComplexNumber[][] output, ComplexNumber[][] a, int i) {
        ComplexNumber[] a_i = a[i];
        ComplexNumber[] b_i = a[i];
        for (int j = i - 1; j >= 0 ; j--) {
            ComplexNumber[] epsilon_j = output[j];
            ComplexNumber dotComplex = dot(a_i, epsilon_j);
            for (int k = 0; k < b_i.length; k++) {
                b_i[k] = b_i[k].minus(epsilon_j[k].multiply(dotComplex));
            }

        }
        output[i] = normalize(b_i);
    }



    /**
     * 求两个向量的内积<a, b> = 西格玛 a_i * b_i的共轭
     * @param a 向量a
     * @param b 向量b
     * @return 内积，复数类型
     */
    public static ComplexNumber dot(ComplexNumber[] a, ComplexNumber[] b) {
        if (a == null || b == null || a.length == 0 || b.length == 0) {
            System.err.println("向量求内积时，两个向量不能为null。");
        } else if (a.length != b.length) {
            System.err.println("向量求内积时，两个向量长度必须相等。");
        }
        ComplexNumber res = new ComplexNumber();

        for (int i = 0; i < a.length; i++) {
            ComplexNumber a_i = a[i];
            ComplexNumber b_i = b[i];
            ComplexNumber temp = a[i].multiply(b[i].conjugate());
            res.add(temp);
        }

        return res;
    }

    /**
     * 获得极大线性无关组，不必行最简
     * @param a 任意的一组向量组，a[0] a[1]是向量组的两个向量
     * @return 输入向量组的极大线性无关组
     */
    public static ComplexNumber[][] getMaxIndependence(ComplexNumber[][] a) {
        if (a == null || a.length == 0 || a[0].length == 0) {
            System.err.println("计算极大线性无关组时，传入向量组不可为null。");
        }
        // 行 列的长度
        int axis0Len = a.length;
        int axis1Len = a[0].length;
        // 跳过0向量
        // 如何获取极大线性无关组？是记录0向量和成比例向量，还是记录不成比例向量？选择记录0向量和成比例向量。
        // 记录成比例向量下标和0向量下标
        List<Integer> timesVectorList = new ArrayList<>(16);
        for (int i = 0; i < axis0Len && !timesVectorList.contains(i); i++) {
            // 边缘情况，外层扫描到0向量，加入
            if (whetherZero(a[i])) {
                timesVectorList.add(i);
                continue;
            }
            for (int j = i + 1; j < axis1Len; j++) {
                // 如果a[i] a[j]成比例，或者a[j]是0向量，那么我们记录j，在最外层循环跳过这个向量与其他向量的比较
                if ((whetherZero(a[j]) || cmpTimes(a[i], a[j])) && !timesVectorList.contains(j) ) {
                    timesVectorList.add(j);
                }
            }
        }
        // 返回极大线性无关组aRes
        ComplexNumber[][] aRes = new ComplexNumber[axis0Len - timesVectorList.size()][axis1Len];
        int k = 0;
        for (int i = 0; i < axis0Len; i++) {
            if (!timesVectorList.contains(i)) {
                aRes[k++] = a[i];
            }
        }
        return aRes;
    }

    /**
     * 比较两个向量是否成比例
     * @param a 向量a
     * @param b 向量b
     * @return boolean值
     */
    public static boolean cmpTimes(ComplexNumber[] a, ComplexNumber[] b) {
        boolean flag = true;
        if (a == null || b == null || a.length == 0 || b.length == 0) {
            System.err.println("比较两个向量是否成比例时，传入向量不可为null。");
        }
        if (a.length != b.length) {
            System.err.println("比较两个向量是否成比例时，两个向量长度应该相等。");
        }
        // 如果向量是0向量，跳过
        if (whetherZero(a) && whetherZero(b)) {
            System.err.println("比较两个向量是否成比例时，两个向量不应该同时是0向量。");
        }
        // 倍数数组，如果数组中每个元素都相等，那么代表两个向量成比例
        double[] timesArr = new double[a.length];
        // 遍历两个向量组，求倍数
        for (int i = 0; i < a.length; i++) {
            timesArr[i] = timesCal(a[i], b[i]);
        }

        // 判断两个向量是否成比例，跳过两个向量对应位置都是0.0的情况，记录这个位置
        List<Integer> allZeroIndexList = new ArrayList<>(16);
        // 1。遍历倍数数组，遇到0.0，那么表示两个向量不成比例
        for (int i = 0; i < timesArr.length; i++) {
            if (whetherMatchZero(a, b, i)) {
                allZeroIndexList.add(i);
                continue;
            }
            if (DoubleUtils.doubleCmp(timesArr[i], 0.0) == 0 ) {
                flag = false;
            }
        }
        // 2。如果有元素不相等，那么表示两个向量不成比例
        if (!whetherAllEqual(timesArr, allZeroIndexList)) {
            flag = false;
        }
        return flag;
    }


    /**
     * 计算两个复数a, b的倍数，不成比例返回0，成比例返回 b/a
     * @param a ComplexNumber变量
     * @param b ComplexNumber变量
     * @return 倍数 double类型
     */
    public static double timesCal(@NotNull ComplexNumber a, @NotNull ComplexNumber b) {
        double aReal = a.getRealPart();
        double aImg = a.getImaginaryPart();
        double bReal = b.getRealPart();
        double bImg = b.getImaginaryPart();

        // 两个复数的实部和虚部分别计算比例
        double realTimes, imgTimes;
        // 两个复数实部、虚部是否都为0
        int realFlag = 0, imgFlag = 0;
        if (DoubleUtils.doubleCmp(aReal, 0.0) == 0 && DoubleUtils.doubleCmp(bReal, 0.0) == 0) {
            realFlag = 1;
        }
        if (DoubleUtils.doubleCmp(aImg, 0.0) == 0 && DoubleUtils.doubleCmp(bImg, 0.0) == 0) {
            imgFlag = 1;
        }
        // 实部倍数
        if (DoubleUtils.doubleCmp(aReal, 0.0) == 0 || DoubleUtils.doubleCmp(bReal, 0.0) == 0) {
            realTimes = 0.0;
        } else {
            realTimes = bReal / aReal;
        }
        // 虚部倍数
        if (DoubleUtils.doubleCmp(aImg, 0.0) == 0 || DoubleUtils.doubleCmp(bImg, 0.0) == 0) {
            imgTimes = 0.0;
        } else {
            imgTimes = bImg / aImg;
        }

        // 实部均为0，倍数是虚部倍数
        if (realFlag == 1) {
            return imgTimes;
        }
        // 虚部均为0，倍数是实部倍数
        if (imgFlag == 1) {
            return realTimes;
        }
        // 实部倍数 == 虚部倍数
        if (DoubleUtils.doubleCmp(realTimes, imgTimes) == 0) {
            return realTimes;
        } else { // 其余情况倍数为0
            return 0.0;
        }

    }

    /**
     * 判断向量是否为0向量 true代表a是0向量，false代表a不是0向量
     * @param a 复数向量
     * @return boolean值
     */
    @Contract(pure = true)
    public static boolean whetherZero(ComplexNumber @NotNull [] a) {
        boolean flag = true;
        for (int i = 0; i < a.length; i++) {
            double realPart = a[i].getRealPart();
            double imgPart = a[i].getImaginaryPart();
            // 遍历向量，只要有一个纬度的实部或者虚部不为0，那么这个向量就不是0向量
            if (DoubleUtils.doubleCmp(0.0, realPart) != 0 || DoubleUtils.doubleCmp(0.0, imgPart) != 0) {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 检查double数组中元素是否都相等
     * @param a double数组
     * @param list 跳过的下标
     * @return boolean类型 true表示元素均相等
     */
    public static boolean whetherAllEqual(double @NotNull [] a, List<Integer> list) {
        if (a.length == 0) {
            System.err.println("判断数组中元素是否都相等时，数组长度不能为0。");
        }
        for (int i = 0; i < a.length; i++) {
            // 跳过
            if (list != null && list.size() != 0 && list.contains(i)) {
                continue;
            }
            for (int j = i; j < a.length; j++) {
                // 跳过
                if (list != null && list.size() != 0 && list.contains(j)) {
                    continue;
                }
                if (DoubleUtils.doubleCmp(a[i], a[j]) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 检查两个向量某个下标位置是否均为0， true表示对应位置都是0
     * @param a 向量a
     * @param b 向量b
     * @param i 要检查的下标
     * @return boolean类型
     */
    public static boolean whetherMatchZero(ComplexNumber[] a, ComplexNumber[] b, int i) {
        if (a == null || b == null || a.length == 0 || b.length == 0 || a.length != b.length) {
            System.err.println("检查两个向量某个下标位置是否均为0时，向量应该存在且两个向量的长度应该相等。");
        }else if (i < 0 || i >= a.length) {
            System.err.println("检查两个向量某个下标位置是否均为0时，下标不该越界。");
        }
        ComplexNumber ai = a[i];
        ComplexNumber bi = b[i];
        double aReal = ai.getRealPart();
        double aImg = ai.getImaginaryPart();
        double bReal = bi.getRealPart();
        double bImg = bi.getImaginaryPart();
        if (DoubleUtils.doubleCmp(aReal, 0.0) == 0 && DoubleUtils.doubleCmp(aImg, 0.0) == 0
            && DoubleUtils.doubleCmp(bReal, 0.0) == 0 && DoubleUtils.doubleCmp(bImg, 0.0) == 0) {
            return true;
        }
        return false;
    }


}