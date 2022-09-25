/**
 * 复数类及其运算的实现
 * @author sunrui
 */
public class ComplexNumber {
    /** 实部 */
    private double realPart;
    /** 虚部 */
    private double imaginaryPart;


    public ComplexNumber(){
        this.realPart = 0.0;
        this.imaginaryPart = 0.0;
    }
    public ComplexNumber(double realPart, double imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    /**
     * 得到该复数的共轭
     */
    public ComplexNumber conjugate() {
        return new ComplexNumber(this.realPart, 0.0 - this.imaginaryPart);
    }

    /**
     * 复数的加法
     * @param complexNumber 传入的复数
     * @return 复数计算的结果
     */
    public ComplexNumber add(ComplexNumber complexNumber) {
        if(complexNumber == null) {
            System.err.println("传入复数不可为null。");
            return new ComplexNumber();
        }
        return new ComplexNumber(this.realPart + complexNumber.realPart,
                this.imaginaryPart + complexNumber.imaginaryPart);
    }

    /**
     * 复数的减法
     * @param complexNumber 传入的复数
     * @return 复数计算的结果
     */
    public ComplexNumber minus(ComplexNumber complexNumber) {
        if(complexNumber == null) {
            System.err.println("传入复数不可为null。");
            return new ComplexNumber();
        }
        return new ComplexNumber(this.realPart - complexNumber.realPart,
                this.imaginaryPart - complexNumber.imaginaryPart);
    }

    /**
     * 复数的乘法
     * @param complexNumber 传入的复数
     * @return 复数计算的结果
     * c.实部 = a.实部 * b.实部 - a.虚部 * b.虚部
     * c.虚部 = a.虚部 * b.实部 + a.实部 * b.虚部
     */
    public ComplexNumber multiply(ComplexNumber complexNumber) {
        if(complexNumber == null) {
            System.err.println("传入复数不可为null。");
            return new ComplexNumber();
        }
        double newReal = this.realPart * complexNumber.realPart - this.imaginaryPart * complexNumber.imaginaryPart;
        double newImaginary = this.realPart * complexNumber.imaginaryPart + this.imaginaryPart * complexNumber.realPart;
        return new ComplexNumber(newReal, newImaginary);
    }

    /**
     * 复数的除法
     * @param complexNumber 传入的复数
     * @return 复数计算的结果
     * c.实部 = (a.实部 * b.实部 + a.虚部 * b.虚部) / (b.实部* b.实部 + b.虚部 * b.虚部)
     * c.虚部 = (a.虚部 * b.实部 - a.实部 * b.虚部) / (b.实部 * b.实部 + b.虚部 * b.虚部)
     */
    public ComplexNumber divide(ComplexNumber complexNumber) {
        if(complexNumber == null) {
            System.err.println("传入复数不可为null。");
            return new ComplexNumber();
        }
        if ((complexNumber.getRealPart() == 0) && (complexNumber.getImaginaryPart() == 0)) {
            System.err.println("除数不能为0！");
            return new ComplexNumber();
        }
        double temp = complexNumber.getRealPart() * complexNumber.getRealPart() + complexNumber.getImaginaryPart() * complexNumber.getImaginaryPart();
        double newReal = (this.realPart * complexNumber.getRealPart() + this.imaginaryPart * complexNumber.getImaginaryPart()) / temp;
        double newImaginary = (this.imaginaryPart * complexNumber.getRealPart() - this.realPart * complexNumber.getImaginaryPart()) / temp;
        return new ComplexNumber(newReal, newImaginary);
    }

    public String toString() {  //将一个复数显示为字符串
        return this.realPart + " + " + this.imaginaryPart + "i";
    }

    public boolean equals(Object obj) {  //比较一个对象是否和这个复数对象的值相等
        if (obj == null) {
            return false;
        }
        //首先判断a是不是一个复数对象，instanceof关键字是用来判断对象的类型
        if (obj instanceof ComplexNumber) {
            //如果a是复数对象，需要将它强制类型转换成复数对象，才能调用复数类提供的方法
            ComplexNumber b = (ComplexNumber) obj;
            if ((this.realPart == b.getRealPart()) && (this.imaginaryPart == b.getImaginaryPart())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 获得该复数对象的hashcode
     * 如果两个复数对象是equals的，那么它们的hashCode也必须相同
     * 两个值相等的复数对象通过toString()方法得到的输出字符串是一样的
     * 于是，可以把得到的字符串的hashCode当作复数对象的hashCode
     */
    public int hashCode() {

        return this.toString().hashCode();
    }
    public double getRealPart() {
        return realPart;
    }

    public void setRealPart(double realPart) {
        this.realPart = realPart;
    }

    public double getImaginaryPart() {
        return imaginaryPart;
    }

    public void setImaginaryPart(double imaginaryPart) {
        this.imaginaryPart = imaginaryPart;
    }



}
