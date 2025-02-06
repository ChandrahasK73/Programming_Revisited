import java.io.*;
import java.util.*;
class UniqueElement{
    public static void main(String args[])throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        int[] arr = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        for(int i=0;i<n;i++){
            arr[i] = Integer.parseInt(st.nextToken().toString());
        }
        Arrays.sort(arr);
        for(int i=0; i<n; i+=2){
            if(i==n-1 || arr[i]!=arr[i+1]){
                System.out.println("Unique Element is: "+arr[i]);
                break;
            }
        }
    }
}