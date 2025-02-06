class TrieNode{
    TrieNode[] children;
    boolean isEndOfWord;

    public TrieNode(){
        children = new TrieNode[26];
        isEndOfWord = false;
    }
}

class Trie{
    private final TrieNode root;

    private Trie(){
        this.root = new TrieNode();
    }

    private void insert(String word){
        TrieNode current = root;
        for(char ch : word.toCharArray()){
            int index = ch-'a';
            if(current.children[index]==null){
                current.children[index] = new TrieNode();
            }
            current = current.children[index];
        }
        current.isEndOfWord = true;
    }

    private boolean search(String word){
        TrieNode current = root;
        for(char ch : word.toCharArray()){
            int index = ch-'a';
            if(current.children[index]==null){
                return false;
            }
            current = current.children[index];
        }
        return current.isEndOfWord;
    }

    private boolean isStartsWith(String word){
        TrieNode current = root;
        for(char ch: word.toCharArray()){
            int index = ch - 'a';
            if(current.children[index] == null){
                return false;
            }
            current = current.children[index];
        }
        return true;
    }

    public static void main(String args[]){
        Trie trie = new Trie();
        trie.insert("apple");
        trie.insert("banana");
        trie.insert("cherry");

        System.out.println("Found banana: "+ trie.search("banana"));
        System.out.println("Found avacado: "+ trie.search("avacado"));

        System.out.println("Found a word starting with app: "+trie.isStartsWith("app"));
        System.out.println("Found a word starting with cat: "+trie.isStartsWith("cat"));

    }
}