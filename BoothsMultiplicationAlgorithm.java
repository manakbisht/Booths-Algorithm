import java.util.Arrays;

public class BoothsMultiplicationAlgorithm {
    private static byte[] registerA = new byte[2*Main.n+1];
    private static byte[] registerS = new byte[2*Main.n+1];
    private static byte[] registerP = new byte[2*Main.n+1];
    private static byte[] negativeMultiplicand = new byte[Main.n];

    private static void clearRegister(byte[] register) {
        for(int i=0;i<register.length;i++) {
            register[i] = 0;
        }
    }

    private static void initialiseRegisters(byte[] multiplier, byte[] multiplicand) {
        // Negative Multiplicand
        for(byte i=0;i<multiplicand.length;i++) {
            negativeMultiplicand[i] = multiplicand[i];
        }
        Main.twosComplement(negativeMultiplicand);

        // A
        clearRegister(registerA);
        for(byte i=0;i<multiplicand.length;i++) {
            registerA[i] = multiplicand[i];
        }

        // S
        clearRegister(registerS);
        for(byte i=0;i<negativeMultiplicand.length;i++) {
            registerS[i] = negativeMultiplicand[i];
        }

        // P
        clearRegister(registerP);
        for(byte i=Main.n;i<registerP.length-1;i++) {
            registerP[i] = multiplier[i-Main.n];
        }
    }

    public static void multiply(byte[] multiplier, byte[] multiplicand) {
        initialiseRegisters(multiplier, multiplicand);
        byte check = Main.n;

        while (check!=0) {

            if (registerP[registerP.length-2]==0 && registerP[registerP.length-1]==1) {
                Main.add(registerP, registerA);
            } else if (registerP[registerP.length-2]==1 && registerP[registerP.length-1]==0) {
                Main.add(registerP, registerS);
            } else {
                ;
            }
            Main.shiftRight(registerP);
            check--;
        }
        System.out.println(decimal(registerP));
    }

    private static int decimal(byte[] x) {
        int sum = 0;
        // dropping the least significant bit
        for(int i=x.length-2;i>=0;i--) {
            sum += x[i]*Math.pow(2,x.length-i-2);
        }
        if (x[0]==1) return (int)(sum-Math.pow(2,x.length-1));
        return sum;
    }
}
