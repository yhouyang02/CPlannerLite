package persistence;

import org.json.JSONObject;

// Represents a class that can be written into JSON data
// NOTE: This interface is implemented from Writable.java; Link below:
//       https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/Writable.java
public interface Writable {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();

}
