

package eu.basicairdata.graziano.gpslogger;


class SpikesChecker {
    private static final int NOT_AVAILABLE = -100000;

    private long    Good_Time               = NOT_AVAILABLE;   // The time of the last good value

    private double  Prev_Altitude           = NOT_AVAILABLE;   // the previous data loaded
    private long    Prev_Time               = NOT_AVAILABLE;
    private float   Prev_VerticalSpeed      = NOT_AVAILABLE;

    private double  New_Altitude            = NOT_AVAILABLE;   // the new (current) data loaded
    private long    New_Time                = NOT_AVAILABLE;
    private float   New_VerticalSpeed       = NOT_AVAILABLE;

    private long    Time_Interval           = NOT_AVAILABLE;    // Interval between fixes (in seconds)
    private float   VerticalAcceleration;

    private float MAX_ACCELERATION;     // The maximum vertical acceleration allowed
    private int STABILIZATION_TIME = 4;  // Stabilization window, in seconds. It must be > 0

    // Constructor
    SpikesChecker(float max_acceleration, int Stabilization_Time) {
        MAX_ACCELERATION = max_acceleration;
        STABILIZATION_TIME = Stabilization_Time;
    }

    void load(long Time, double Altitude) {
        if (Time > New_Time) {
            Prev_Time = New_Time;
            New_Time = Time;
            Prev_Altitude = New_Altitude;
            Prev_VerticalSpeed = New_VerticalSpeed;
        }

        Time_Interval = Prev_Time != NOT_AVAILABLE ? (New_Time - Prev_Time) / 1000 : NOT_AVAILABLE;
        New_Altitude = Altitude;

        if ((Time_Interval > 0) && (Prev_Altitude != NOT_AVAILABLE)) {
            New_VerticalSpeed = (float) (New_Altitude - Prev_Altitude) / Time_Interval;

            if (Prev_VerticalSpeed != NOT_AVAILABLE) {
                if (Time_Interval > 1000) VerticalAcceleration = NOT_AVAILABLE; // Prevent Vertical Acceleration value from exploding
                else VerticalAcceleration = 2 * (-Prev_VerticalSpeed * Time_Interval + (float)(New_Altitude - Prev_Altitude)) / (Time_Interval * Time_Interval);
            }
        }

        if (Math.abs(VerticalAcceleration) >= MAX_ACCELERATION) Good_Time = New_Time ;

        //Log.w("myApp", "[#] SpikesChecker.java - Vertical Acceleration = " + VerticalAcceleration);
        //Log.w("myApp", "[#] SpikesChecker.java - Validation window = " + (New_Time - Good_Time) / 1000);
    }

    boolean isValid() {
        return (New_Time - Good_Time) / 1000 >= STABILIZATION_TIME;
    }
}
