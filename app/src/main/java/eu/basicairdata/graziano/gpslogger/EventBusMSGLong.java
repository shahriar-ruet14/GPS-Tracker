

package eu.basicairdata.graziano.gpslogger;

public class EventBusMSGLong {
    short MSGType = 0;
    long  id      = 0;
    long  Value   = 0;

    EventBusMSGLong (short _MSGType, long _id, long _Value) {
        MSGType = _MSGType;
        id      = _id;
        Value   = _Value;
    }
}