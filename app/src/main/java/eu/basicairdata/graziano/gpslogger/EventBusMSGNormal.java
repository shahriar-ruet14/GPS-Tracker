
package eu.basicairdata.graziano.gpslogger;

public class EventBusMSGNormal {
    short MSGType = 0;
    long  id      = 0;

    EventBusMSGNormal (short _MSGType, long _id) {
        MSGType = _MSGType;
        id      = _id;
    }
}