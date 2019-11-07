import java.util.Arrays;
public class BoothsMultiplicationAlgorithm 
{
	public static int ans=0;
	private static byte[] registerA = new byte[2*Main.n+1];
	private static byte[] registerS = new byte[2*Main.n+1];
	private static byte[] registerP = new byte[2*Main.n+1];
	private static byte[] negativeMultiplicand = new byte[Main.n];
	private static void initialiseRegisters(byte[] multiplier, byte[] multiplicand) 
	{
		// Negative Multiplicand
		for(byte i=0;i<multiplicand.length;i++) 
			negativeMultiplicand[i] = multiplicand[i];
		Main.optimisedTwosComplement(negativeMultiplicand,negativeMultiplicand.length);
		// A
		Main.clearRegister(registerA);
		for(byte i=0;i<multiplicand.length;i++) 
			registerA[i] = multiplicand[i];
		// S
		Main.clearRegister(registerS);
		for(byte i=0;i<negativeMultiplicand.length;i++) 
			registerS[i] = negativeMultiplicand[i];
		// P
		Main.clearRegister(registerP);
		for(byte i=Main.n;i<registerP.length-1;i++) 
			registerP[i] = multiplier[i-Main.n];
	}
	public static String getans()
	{
		return Main.getString(registerP,registerP.length-1);
	}
	public static void multiply(byte[] multiplier, byte[] multiplicand) 
	{
		initialiseRegisters(multiplier, multiplicand);
		byte check = Main.n;
		while (check!=0) 
		{
			if (registerP[registerP.length-2]==0 && registerP[registerP.length-1]==1) 
				Main.add(registerP, registerA);
			else if (registerP[registerP.length-2]==1 && registerP[registerP.length-1]==0) 
				Main.add(registerP, registerS);
			Main.shiftRight(registerP);
			check--;
		}
		ans=Main.decimal(registerP,registerP.length-1);
	}
}