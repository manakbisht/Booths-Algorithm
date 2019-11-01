public class Main {
    public final static byte n = 12; // size of operands
    private static byte[] UNITY;

    static {
        UNITY = new byte[n];
        UNITY[n-1] = 1;
    }

    public static byte[] binary(int d) {
        int sign = d<0 ? 1 : 0;

        d = Math.abs(d);
        byte[] bin = new byte[n];
        byte i = n;
        i--;
        while (d!=0) {
            bin[i] = (byte) (d%2);
            d = d/2;
            i--;
        }

        if (sign==0) return bin;
        else return twosComplement(bin);
    }

    protected static byte[] twosComplement(byte[] bin) {
        for(byte i=0;i<Main.n;i++) {
            if (bin[i] == 0) bin[i] = 1;
            else bin[i] = 0;
        }
        add(bin, Main.UNITY);
        return bin;
    }

    protected static void add(byte[] x, byte[] y) {
        // y is added to x
        // ensure equal size
        byte carry = 0;
        for(byte i=1;i<=x.length;i++) {
            if (x[x.length-i]==0 && y[x.length-i]==0) {
                x[x.length-i] = carry;
                carry = 0;
            }
            else if (x[x.length-i]==1 && y[x.length-i]==1) {
                if (carry==1) {
                    x[x.length-i] = 1;
                    carry = 1;
                } else {
                    x[x.length-i] = 0;
                    carry = 1;
                }
            }
            else {
                if (carry==1) {
                    x[x.length-i] = 0;
                    carry = 1;
                } else {
                    x[x.length-i] = 1;
                }
            }
        }
    }

    protected static void shiftRight(byte[] x) {
        byte y = x[0];
        byte temp;
        for(byte i=1;i<x.length;i++) {
            temp = x[i];
            x[i] = y;
            y = temp;
        }
    }

    public static void main(String[] args) {
	// write your code here
       int x = -7;
       int y = 3;

       byte[] x2 = binary(x);
       byte[] y2 = binary(y);

       BoothsMultiplicationAlgorithm.multiply(x2,y2);
    }
}
