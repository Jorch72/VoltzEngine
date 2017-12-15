package com.builtbroken.mc.framework.json.conversion.structures.map;

import com.builtbroken.mc.framework.json.conversion.JsonConverter;
import com.builtbroken.mc.framework.json.processors.JsonProcessor;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Used to convert json data to pos objects
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/11/2017.
 */
public class JsonConverterHashMap extends JsonConverter<HashMap>
{
    public JsonConverterHashMap()
    {
        super("HashMap");
    }

    @Override
    public HashMap convert(JsonElement element, String[] args)
    {
        HashMap map = new HashMap();
        if (args.length != 1)
        {
            throw new RuntimeException("JsonConverterList: arguments needs to contain at least 1 value containing of conversion type in order to function");
        }
        if (element.isJsonArray())
        {
            String key = args[0];
            String keyType = args[1];
            String value = args[2];
            String valueType = args[3];

            for (JsonElement e : element.getAsJsonArray())
            {
                if (e.isJsonObject())
                {
                    JsonObject object = e.getAsJsonObject();
                    JsonProcessor.ensureValuesExist(object, key, value);

                    JsonElement keyData = object.get(key);
                    JsonElement valueData = object.get(value);

                    Object keyObject = convertElement(keyType, keyData);  //TODO args?
                    Object valueObject = convertElement(valueType, valueData);  //TODO args?

                    if (keyObject == null)
                    {
                        throw new RuntimeException("JsonConverterHashMap: failed to convert key data '" + keyData + "' to type '" + keyType + "'");
                    }

                    if (valueObject == null)
                    {
                        throw new RuntimeException("JsonConverterHashMap: failed to convert value data '" + valueData + "' to type '" + valueType + "'");
                    }

                    map.put(keyObject, valueObject);
                }
                else
                {
                    throw new RuntimeException("JsonConverterHashMap: entries in json array must be json objects, example: map:[{o1}, {o2}, {o3}]");
                }
            }
        }
        else
        {
            throw new RuntimeException("JsonConverterHashMap: json element needs to be an array in order to convert to hash map");
        }
        return map;
    }

    @Override
    public JsonElement build(String type, Object data, String... args)
    {
        if (args.length != 1)
        {
            throw new RuntimeException("JsonConverterList: arguments needs to contain at least 1 value containing of conversion type in order to function");
        }
        if(data instanceof Map)
        {
            String key = args[0];
            String keyType = args[1];
            String value = args[2];
            String valueType = args[3];

            Map<Object, Object> map = ((Map) data);

            JsonArray array = new JsonArray();
            for(Map.Entry entry : map.entrySet())
            {
                JsonObject object = new JsonObject();
                object.add(key, buildElement(keyType, entry.getKey()));
                object.add(value, buildElement(valueType, entry.getValue()));
                array.add(object);
            }
            return array;
        }
        return null;
    }

}
