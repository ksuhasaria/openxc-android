package com.openxc.messages;

import java.util.Map;

import android.os.Parcel;

import com.google.common.base.Objects;

public class SimpleVehicleMessage extends NamedVehicleMessage {
    public static final String VALUE_KEY = "value";

    private Object mValue;

    public SimpleVehicleMessage(Long timestamp, String name, Object value) {
        super(timestamp, name);
        mValue = value;
    }

    public SimpleVehicleMessage(String name, Object value) {
        this(null, name, value);
    }

    public SimpleVehicleMessage(Map<String, Object> values)
            throws InvalidMessageFieldsException {
        super(values);
        if(!containsRequiredFields(values)) {
            throw new InvalidMessageFieldsException(
                    "Missing keys for construction in values = " +
                    values.toString());
        }
        mValue = getValuesMap().remove(VALUE_KEY);
    }

    public Object getValue() {
        return mValue;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !super.equals(obj)) {
            return false;
        }

        final SimpleVehicleMessage other = (SimpleVehicleMessage) obj;
        return mValue.equals(other.mValue);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("timestamp", getTimestamp())
            .add("name", getName())
            .add("value", getValue())
            .add("values", getValuesMap())
            .toString();
    }

    protected static boolean containsRequiredFields(Map<String, Object> map) {
        return map.containsKey(VALUE_KEY);
        // TODO the parent constructor removes the name, so the our validation
        // fails. argh!
        //NamedVehicleMessage.containsRequiredFields(map)

    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeValue(getValue());
    }

    public void readFromParcel(Parcel in) {
        super.readFromParcel(in);
        mValue = in.readValue(null);
    }

    protected SimpleVehicleMessage(Parcel in)
            throws UnrecognizedMessageTypeException {
        readFromParcel(in);
    }

    protected SimpleVehicleMessage() { }
}