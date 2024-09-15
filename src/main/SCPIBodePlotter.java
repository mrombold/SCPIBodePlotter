package main;

import instrumentation.Instrument;

public class SCPIBodePlotter {

    public static void main(String[] args) {
   
	    Instrument scope= new Instrument("10.11.13.220");
	    scope.connect();
	    scope.sendCommand("*IDN?");
	    System.out.println(scope.readMeasurement());
	    
	    scope.disconnect();
    }

}
