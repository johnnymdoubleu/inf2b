package imult;

public class StudentCode {
	public static BigInt add(BigInt A, BigInt B) {
		Unsigned c = new Unsigned(0); //assigning the carry value by setting it as 0 initially
		BigInt y = new BigInt(); //assigning the class BigInt to key in each digit
		int N = Math.max(A.length(), B.length()); //using the larger size digit and getting the 
		for (int i = 0; i < N; i++) { //running a for loop to add digit by digit
		  DigitAndCarry x = Arithmetic.addDigits(A.getDigit(i), B.getDigit(i), c); //adding each digit
		  c = x.getCarry(); //reassigning the value of carry
		  y.setDigit(i, x.getDigit()); //assigning the number into each digit of addition
	  }
		y.setDigit(N, c); //adding the last possible carry in the last digit
		return y; //return the addition of A and B
	}

	public static BigInt sub(BigInt A, BigInt B) {
		Unsigned c = new Unsigned(0); //assigning the carry value by setting it as 0 initially
		BigInt y = new BigInt(); //assigning the class BigInt to key in each digit
		for (int i = 0; i <A.length(); i++ ) { //running a for loop to subtract digit by digit
			DigitAndCarry x = Arithmetic.subDigits(A.getDigit(i), B.getDigit(i), c); //subtracting each digit
			c = x.getCarry(); //reassigning the value of carry
			y.setDigit(i, x.getDigit()); //assigning the number into each digit after subtraction
		}
		return y; //return the subtraction of A and B
	}

	public static BigInt koMul(BigInt A, BigInt B) {
		int N = A.length();
		int length = B.length();
		if (N<length) { //checking if the length of A is greater than length of B
			N = length; //if length of A is smaller than length of B we assume n = length of B
		}
		int nf = N/2; // dividing the length by half
		BigInt l = new BigInt(); //assigning the BigInt for l = A0 * B0
		BigInt h = new BigInt(); //assigning the BigInt for h = A1 * B1
		BigInt m = new BigInt(); //assigning the BigInt for m = ((A0+A1)*(B0+B1)) - l - h
		if (N > 1) { //the length of A and B must be greater than 1 to go on the Karatsuba Algorithm
			BigInt A0 = A.split(0, nf - 1); //Splitting BigInt A into A0 and A1 
			BigInt A1 = A.split(nf, N-1);
			BigInt B0 = B.split(0, nf - 1); //Splitting BigInt B into B0 and B1
			BigInt B1 = B.split(nf, N-1);
			l = koMul(A0, B0); // calculating l by recursive call of koMul method
			h = koMul(A1, B1); // calculating h by recursive call of koMul method
			BigInt mprime = koMul(add(A0, A1), add(B0,B1)); // calculating the product of two addition (A1+A0) & (B1+B0)
															// by recursive call of KoMulOpt method
			m = sub(mprime, (add(h,l))); //calculating m by subtracting l and h from mprime
		}
		else { //for the case when N = 1
			BigInt S = new BigInt(); //assigning the BigInt for the product of A and B
			S.setDigit(0, Arithmetic.mulDigits(A.getDigit(0), B.getDigit(0)).getDigit()); //last digit to be the digit multiplied
			S.setDigit(1, Arithmetic.mulDigits(A.getDigit(0), B.getDigit(0)).getCarry()); //first digit to be the carry
			return S; // BigInt value for multiplication of A and B 
		}
		m.lshift(nf); //performing a lshift on m to place the digits on the last half of the length
		h.lshift(nf * 2); //performing a lshift on h to place the digits on the first half of the length
		return add(add(l,h),m); //returning the product of A and B
	}

	public static BigInt koMulOpt(BigInt A, BigInt B) {
		if (A.length()>10 && B.length() > 10) { //to check that the length of BigInt A and BigInt B are both greater than 10
			int N = A.length();
			int length = B.length();
			if (N<length) { //checking if the length of A is greater than length of B
				N = length; //if length of A is smaller than length of B we assume n = length of B
			}
			int nf = N/2; // dividing the length by half
			BigInt l = new BigInt(); //assigning the BigInt for l = A0 * B0
			BigInt h = new BigInt(); //assigning the BigInt for h = A1 * B1
			BigInt m = new BigInt(); //assigning the BigInt for m = ((A0+A1)*(B0+B1)) - l - h
			if (N > 1) { //the length of A and B must be greater than 1 to go on the Karatsuba Algorithm
				BigInt A0 = A.split(0, nf - 1); //Splitting BigInt A into A0 and A1 
				BigInt A1 = A.split(nf, N-1);
				BigInt B0 = B.split(0, nf - 1); //Splitting BigInt B into B0 and B1
				BigInt B1 = B.split(nf, N-1);
				l = koMulOpt(A0, B0); // calculating l by recursive call of koMulOpt method
				h = koMulOpt(A1, B1); // calculating h by recursive call of koMulOpt method
				BigInt mprime = koMulOpt(add(A0, A1), add(B0,B1)); // calculating the product of two addition (A1+A0) & (B1+B0)
																   // by recursive call of KoMulOpt method
				m = sub(mprime, (add(h,l))); //calculating m by subtracting l and h from mprime
			}
			else { //for the case when N = 1
				BigInt S = new BigInt(); //assigning the BigInt for the product of A and B
				S.setDigit(0, Arithmetic.mulDigits(A.getDigit(0), B.getDigit(0)).getDigit()); //last digit to be the digit multiplied
				S.setDigit(1, Arithmetic.mulDigits(A.getDigit(0), B.getDigit(0)).getCarry()); //first digit to be the carry
				return S; // BigInt value for multiplication of A and B 
			}
			m.lshift(nf); //performing a lshift on m to place the digits on the last half of the length
			h.lshift(nf * 2); //performing a lshift on h to place the digits on the first half of the length
			return add(add(l,h),m); //returning the product of A and B
		}
		
		else {
			return Arithmetic.schoolMul(A, B); //if the digit size is less than 10 we simply perform school mutiplication
		}	
	}

	public static void main(String argv[]) throws java.io.FileNotFoundException {
	}
} // end StudentCode class
