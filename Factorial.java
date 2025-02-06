import java.util.*;
import java.io.*;
import java.math.BigInteger;

class Factorial{

    public static String factorial(int n){
        ArrayList<Integer> result = new ArrayList<Integer>();
        result.add(1);

        for(int i=2;i<=n;i++){
            int carry =0;
            for(int j=0;j<result.size();j++){
                int temp = result.get(j)*i + carry;
                result.set(j, temp%10);
                carry = temp/10;
            }
            while(carry>0){
                result.add(carry%10);
                carry /= 10;
            }
        }

        String str = "";
        Iterator ltr = result.iterator();
        while(ltr.hasNext()){
            str += (int)ltr.next();
        }

        return new StringBuilder(str).reverse().toString();
    }

    public static void main(String args[])throws java.io.IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter a number to find the factorial for: ");
        int n= Integer.parseInt(br.readLine().trim());
        System.out.println(factorial(n));

        // Calculate 100!
        int number = 100; // The number for which factorial is calculated
        BigInteger factorial = BigInteger.ONE;

        // Loop to calculate factorial
        for (int i = 1; i <= number; i++) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
        }

        // Print the result
        System.out.println("100! = " + factorial);
    }
}