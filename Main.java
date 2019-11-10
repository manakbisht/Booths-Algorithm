import java.util.*;
import java.io.*;
public class Main 
{
	public final static byte n=12; // size of operands
	private static byte[] UNITY;
	static 
	{
		UNITY=new byte[n];
		UNITY[n-1] = 1;
	}
	//Converts a decimal number into binary and return the binary number in two complement
	public static byte[] binary(int d) 
	{
		int sign = d<0 ? 1 : 0;
		d = Math.abs(d);
		byte[] bin = new byte[n];
		byte i = n;
		i--;
		while (d!=0) 
		{
			try
			{
				bin[i] = (byte) (d%2);
				d = d/2;
				i--;
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				System.out.println("Number larger than expected");
				System.exit(0);
			}	
		}
		if (sign==0) 
			return bin;
		else 
			return optimisedTwosComplement(bin,bin.length);
	}
	//gives two complement of a number;
	public static byte[] optimisedTwosComplement(byte [] x,int length)
	{
		int i=length-1,flag=0;
		for(;i>=0;i--)
			if(x[i]==1)
				break;
		i--;
		for(;i>=0;i--)
		{
			if(x[i]==0)
				x[i]=1;
			else
				x[i]=0;
		}
		return x;
	}
	/*protected static byte[] twosComplement(byte[] bin) 
	{
		for(byte i=0;i<Main.n;i++) 
		{
			if (bin[i] == 0) 
				bin[i] = 1;
			else 
				bin[i] = 0;
		}
		add(bin, Main.UNITY);
		return bin;
	} */
	protected static void add(byte[] x, byte[] y) 
	{
		// y is added to x and saved to x
		// ensure equal size
		byte carry = 0;
		for(byte i=1;i<=x.length;i++) 
		{
			if (x[x.length-i]==0 && y[x.length-i]==0) 
			{
				x[x.length-i] = carry;
				carry = 0;
			}
			else if (x[x.length-i]==1 && y[x.length-i]==1) 
			{
				if (carry==1) 
				{
					x[x.length-i] = 1;
					carry = 1;
				} 
				else 
				{
					x[x.length-i] = 0;
					carry = 1;
				}
			}
			else 
			{
				if (carry==1) 
				{
					x[x.length-i] = 0;
					carry = 1;
				} 
				else 
				{
					x[x.length-i] = 1;
				}
			}
		}
	}
	protected static void shiftRight(byte[] x) 
	{
		byte y = x[0];
		byte temp;
		for(byte i=1;i<x.length;i++) 
		{
			temp = x[i];
			x[i] = y;
			y = temp;
		}
	}
	public static int decimal(byte[] x,int length) 
	{
		int sum = 0;
		int flag=0;
		if(x[0]==1)
		{
			optimisedTwosComplement(x,length);
			flag=1;
		}
		// dropping the least significant bit
		for(int i=length-1;i>=0;i--) 
		{
			sum += x[i]*Math.pow(2,length-i-1);
		}
		if(flag==1) 
		{
			optimisedTwosComplement(x,length);
			return -sum;
		}
		return sum;
	}
	public static void clearRegister(byte[] register) 
	{
		for(int i=0;i<register.length;i++) 
			register[i] = 0;
	}
	public static void printArray(byte[] x,int length)
	{
		for(int i=0;i<length;i++)
			System.out.print(x[i]);
		System.out.println();
	}
	public static void test() throws FileNotFoundException
	{
		PrintWriter out=new PrintWriter("Sample.txt");
		try
		{
			for(int i=-1000;i<=1000;i++)
			{
				for(int j=-1000;j<=1000;j++)
				{
					if(j==0)
						continue;
					byte[] x2 = binary(i);
					byte[] y2 = binary(j);
					BoothsMultiplicationAlgorithm.multiply(x2,y2);
					DivisionAlgorithm.divide(x2,y2);
					int a=i/j;
					int b=i%j;
					if(i*j!=BoothsMultiplicationAlgorithm.ans||a!=DivisionAlgorithm.q||b!=DivisionAlgorithm.r)
					{
						System.out.println(i+" "+j);
						System.out.println("Q="+a+" "+DivisionAlgorithm.q);
						System.out.println("R="+b+" "+DivisionAlgorithm.r);
						System.out.println("Multiplication: "+i*j+" "+BoothsMultiplicationAlgorithm.ans);
						System.out.println("NO");
						System.exit(0);
					}
					else
					{
						String ans="Number 1: "+i+" Number 2: "+j+"\n";
						out.println(getans(ans));
						System.out.print(". ");
					}
				}
			}
		}
		finally
		{
			out.close();
		}
	}
	public static String getString(byte x[],int length)
	{
		String a="";
		for(int i=0;i<length;i++)
			a+=String.valueOf(x[i]);
		return a;
	}
	public static void main(String[] args) throws FileNotFoundException
	{
		if(args.length!=0&&args[0].equals("test"))
			test();
		else
		{
			PrintWriter out=new PrintWriter(("Output.txt"));
			Scanner in=new Scanner(System.in);
			try
			{
				System.out.print("Enter 1st number:");
				int x=in.nextInt();
				System.out.print("Enter 2nd number:");
				int y=in.nextInt();
				byte x1[]=binary(x);
				byte x2[]=binary(y);
				BoothsMultiplicationAlgorithm.multiply(x1,x2);
				DivisionAlgorithm.divide(x1,x2);
				String ans="Number 1: "+x+" Number 2: "+y+"\n";
				String Ans=getans(ans);
				out.println(Ans);
				System.out.println(Ans);
			}
			catch(InputMismatchException e)
			{
				System.out.println("Wrong input");
			}
			finally
			{
				out.close();
			}

		}	
	}
	public static String getans(String ans)
	{
		ans+="Multiplication:\nBinary: ";
		ans+=BoothsMultiplicationAlgorithm.getans();
		ans+="\nDecimal: ";
		ans+=String.valueOf(BoothsMultiplicationAlgorithm.ans);
		ans+="\nDivision:\nRemainder:\nBinary: ";
		ans+=DivisionAlgorithm.getr();
		ans+="\nDecimal: ";
		ans+=String.valueOf(DivisionAlgorithm.r);
		ans+="\nQuotient:\nBinary: ";
		ans+=DivisionAlgorithm.getq();
		ans+="\nDecimal :";
		ans+=String.valueOf(DivisionAlgorithm.q);
		return ans;
	}

}
