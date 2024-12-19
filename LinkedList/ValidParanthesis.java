import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;
import java.io.InputStreamReader;
import java.io.BufferedReader;
class ValidParanthesis{
    public static boolean isValidparanthesis(String paranthesis){
        Stack<Character> st = new Stack<Character>();
        for(char ch: paranthesis.toCharArray()){
            if(ch=='(') st.push(ch);
            else if(ch == ')' && !st.isEmpty() && st.peek()=='(' ) st.pop();
            else if(ch ==')' && st.isEmpty()) return false;
        }
        return st.isEmpty();
    }
    public static void main(String args[])throws java.io.IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(isValidparanthesis(br.readLine().trim()));
    }
}

// )))(((
// ())(

// isOpen = true for all open braces
// (()())