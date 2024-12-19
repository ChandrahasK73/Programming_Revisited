class Main{
    public static void main(String args[]){
        int[] arr1={1,2,3,4,5};
        int[] arr2={1,2,3,4,5,6};

        int n = arr1.length;

        for(int i=0;i<n/2;i++){
            int temp = arr1[n-i-1];
            arr1[n-i-1] = arr1[i];
            arr1[i] = temp;
        }

        for(int i=0;i<n;i++) System.out.print(arr1[i]+" ");
        System.out.println();
        n = arr2.length;

        for(int i=0;i<n/2;i++){
            int temp = arr2[n-i-1];
            arr2[n-i-1] = arr2[i];
            arr2[i] = temp;
        }

        for(int i=0;i<n;i++) System.out.print(arr2[i]+" ");
    }
}