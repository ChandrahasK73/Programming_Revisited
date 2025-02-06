import java.util.HashMap;

class MapsImpl{
    public static void main(String[] args){
        int[] arr = {1, 1, 2, 3, 4, 4, 5};
        HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
        for(int i=0;i<arr.length;i++){
            if(!hm.containsKey(arr[i])){
                hm.put(arr[i], 1);
            }
            else{
                hm.put(arr[i], hm.get(arr[i])+1);
            }
        }

        for(Integer i : hm.keySet()){
            System.out.println(i+"->"+hm.get(i));
        }
    }
}