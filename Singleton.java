//If you want to access only single and same instance of a class, we need singleton class implemented like below

class Singleton{
    public static void main(String args[]){
        SingletonImpl obj1 = SingletonImpl.getInstance();
        SingletonImpl obj2 = SingletonImpl.getInstance();

        System.out.println(obj1==obj2
        );
    }
}

class SingletonImpl{
    //create only one static object of the SingletonImpl type
    static SingletonImpl obj = new SingletonImpl();

    //create a private constructor so that to be accessible only in this class.
    private SingletonImpl(){

    }

    //Create a method to return the instance of the Singleton class. Return the one and only instance all the time.
    public static SingletonImpl getInstance(){
        return obj;
    }
}