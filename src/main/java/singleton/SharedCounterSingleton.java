package singleton;

import misc.SharedCounter;
import misc.impl.SharedCounterImpl;

public class SharedCounterSingleton {
    private static final SharedCounter sharedCounter= new SharedCounterImpl();
    public static SharedCounter getSharedCounter(){
        return sharedCounter;
    }
}
