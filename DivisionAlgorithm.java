class DivisionAlgorithm
{
	public static int q=0;
	public static int r=0;
	private static byte[] registerM = new byte[Main.n];
	private static byte[] registerQ = new byte[Main.n];//q
	private static byte[] registerA = new byte[Main.n];//r
	private static byte[] negative = new byte[Main.n];
	private static void initialiseRegisters(byte[] dividend, byte[] divisor) 
	{
		// Negative 
		for(byte i=0;i<divisor.length;i++) 
			negative[i] = divisor[i];
		Main.optimisedTwosComplement(negative,negative.length);
		// M
		Main.clearRegister(registerM);
		for(byte i=0;i<divisor.length;i++) 
			registerM[i] = divisor[i];
		// S
		Main.clearRegister(registerQ);
		for(byte i=0;i<dividend.length;i++) 
			registerQ[i] = dividend[i];
		//A
		Main.clearRegister(registerA);
	}
	public static boolean isZero(byte[] x)
	{
		for(int i=0;i<x.length;i++)
			if(x[i]==1)
				return false;
		return true;
	}
	public static void divide(byte[] dividend,byte[] divisor)
	{
		int flag=0;
		int f=0;
		if(isZero(divisor))
		{
			System.out.println("divisor is zero");
			return;
		}
		if(dividend[0]==1)
		{
			Main.optimisedTwosComplement(dividend,dividend.length );
			flag=1;
		}
		if(divisor[0]==1)
		{
			Main.optimisedTwosComplement(divisor,divisor.length);
			f=1;
		}
		initialiseRegisters(dividend,divisor);
		int size=Main.n;
		while(size>0)
		{
			byte t=registerA[0];
			shiftLeft();
			if(t==1)
				Main.add(registerA,registerM);
			else
				Main.add(registerA,negative);
			t=registerA[0];
			if(t==1)
				registerQ[registerQ.length-1]=0;
			else
				registerQ[registerQ.length-1]=1;
			size--;
		}
		if(registerA[0]==1)
			Main.add(registerA,registerM);
		r=Main.decimal(registerA,registerA.length);
		if(flag==1)
			Main.optimisedTwosComplement(dividend,dividend.length);
		if(f==1)
			Main.optimisedTwosComplement(divisor,divisor.length);
		if((flag==1&&f==0)||(flag==0&&f==1))
			Main.optimisedTwosComplement(registerQ,registerQ.length);
		if(((registerQ[0]==1&&f==0)||(registerQ[0]==0&&f==1))&&!isZero(registerQ))
		{
			Main.optimisedTwosComplement(registerA,registerA.length);
			r=-r;
		}
		else if(flag==1)
		{
			Main.optimisedTwosComplement(registerA,registerA.length);
			r=-r;
		}
		q=Main.decimal(registerQ,registerQ.length);
	}
	public static String getr()
	{
		return Main.getString(registerA,registerA.length);
	}
	public static String getq()
	{
		return Main.getString(registerQ,registerQ.length);
	}
	//shift left AQ
	private static void shiftLeft()
	{
		byte a=registerQ[0];
		for(int i=registerA.length-1;i>=0;i--)
		{
			byte temp=registerA[i];
			registerA[i]=a;
			a=temp;
		}
		a=0;
		for(int i=registerQ.length-1;i>=0;i--)
		{
			byte temp=registerQ[i];
			registerQ[i]=a;
			a=temp;
		}
	}
}