package persistence;

import org.json.JSONObject;

// Represents a class that can be written into JSON data
public interface Writable {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();

}
