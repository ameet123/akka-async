package org.ameet.akka.message;

/**
 * Created by achaub001c on 6/6/2016.
 * This is sent by Master to the Workers
 */
public class Work {
    private final String url;

    public Work(String url) {

        this.url = url;
    }

    public String getUrl() {
        return url;
    }
    //    private final int start;
//    private final int nrOfElements;
//
//    public Work(int start, int nrOfElements) {
//        this.start = start;
//        this.nrOfElements = nrOfElements;
//
//    }
//
//    public int getStart() {
//        return start;
//    }
//
//    public int getNrOfElements() {
//        return nrOfElements;
//    }
}
