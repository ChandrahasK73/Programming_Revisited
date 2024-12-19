import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
class MostWaterContainer{
    public static int fillContainer(int[] a, int n){
        int maxWater = Integer.MIN_VALUE;
        int i =0;
        int j =n-1;
        while(i<j){
            int multiplier = a[i]<a[j]?a[i]:a[j];
            int temp = (j-i)*multiplier;
            maxWater = Math.max(maxWater, temp);
            if(a[i]<a[j]) i++;
            else j--;
        }
        return maxWater;

    }
    public static void main(String args[])throws java.io.IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        int[] a= new int[n];
        int i=0;
        while(st.hasMoreTokens()){
            a[i++] = Integer.parseInt(st.nextToken());
        }
        System.out.println("Most Water that can be filled: "+fillContainer(a,n));
    }
}