package org.ameet.akka.message;
/**
 * Created by achaub001c on 6/6/2016.
 * this is sent to Master to start the overall processing
 */
public class Initiate {
    private int i;

    public Initiate(int i) {
        this.i = i;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
