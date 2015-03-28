package com.softvision.botanica.common.pojo.util;

import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lorand.krucz
 * Date: 2/8/13
 * Time: 11:41 AM
 * WRITE STUFF FROM POJO INTO BUNDLE && READ STUFF FROM BUNDLE INTO OBJECT
 */
public class BundlePojoConverter {

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ WRITE STUFF FROM POJO INTO BUNDLE

    /**
     * Convert plain old java object to a bundle
     * @param pojo the input object
     * @return the output bundle
     */
    public static Bundle pojo2Bundle(Object pojo) {
        Bundle bundle = new Bundle();
        pojo2Bundle(pojo, bundle);
        return bundle;
    }

    /**
     * Convert plain old java object to a bundle
     * @param pojo the input object
     * @param bundle the pre-existing bundle to write into
     */
    public static void pojo2Bundle(Object pojo, Bundle bundle) {
        if((pojo == null) || (bundle == null)) return;

        if(List.class.isAssignableFrom(pojo.getClass())) {
            for (Object item : (List) pojo) {
                putObjectIntoBundle(Integer.toString(bundle.size()), bundle, item.getClass(), item);
            }
        } else if(Map.class.isAssignableFrom(pojo.getClass())) {
            for (Object key : ((Map) pojo).keySet()) {
                Object value = ((Map) pojo).get(key);
                putObjectIntoBundle(key.toString(), bundle, value.getClass(), value);
            }
        } else {
            Map<Bundled, Field> annotatedFields = getAnnotatedFields(pojo.getClass(), Bundled.class);
            for (Bundled bundled : annotatedFields.keySet()) {
                Field field = annotatedFields.get(bundled);

                Object fieldValue = null;
                //get the value of the field from the current pojo instance
                try {
                    field.setAccessible(true);
                    fieldValue = field.get(pojo);
                } catch (IllegalAccessException e) {
                    //leave fieldValue as null and do nothing else
                }

                putObjectIntoBundle(bundled.key(), bundle, field.getType(), fieldValue);
            }
        }
    }

    /**
     * puts an object content into a bundle
     * @param key the key used in the bundle
     * @param bundle the bundle to add the object to
     * @param objectClass the class of the added object (in case object is null)
     * @param object the object itself (may be null for certain non-primitive types)
     */
    private static void putObjectIntoBundle(String key, Bundle bundle, Class objectClass, Object object) {
        //put the appropriate bundle value into the bundle
        if(Boolean.class.isAssignableFrom(objectClass) || boolean.class.isAssignableFrom(objectClass)) {
            if(object != null)
                bundle.putBoolean(key, (Boolean) object);
        } else if(Bundle.class.isAssignableFrom(objectClass)) {
            bundle.putBundle(key, (Bundle) object);
        } else if(Byte.class.isAssignableFrom(objectClass) || byte.class.isAssignableFrom(objectClass)) {
            if(object != null)
                bundle.putByte(key, (Byte) object);
        } else if(Character.class.isAssignableFrom(objectClass) || char.class.isAssignableFrom(objectClass)) {
            if(object != null)
                bundle.putChar(key, (Character) object);
        } else if(Double.class.isAssignableFrom(objectClass) || double.class.isAssignableFrom(objectClass)) {
            if(object != null)
                bundle.putDouble(key, (Double) object);
        } else if(Float.class.isAssignableFrom(objectClass) || float.class.isAssignableFrom(objectClass)) {
            if(object != null)
                bundle.putFloat(key, (Float) object);
        } else if(Integer.class.isAssignableFrom(objectClass) || int.class.isAssignableFrom(objectClass)) {
            if(object != null)
                bundle.putInt(key, (Integer) object);
        } else if(Long.class.isAssignableFrom(objectClass) || long.class.isAssignableFrom(objectClass)) {
            if(object != null)
                bundle.putLong(key, (Long) object);
        } else if(Short.class.isAssignableFrom(objectClass) || short.class.isAssignableFrom(objectClass)) {
            if(object != null)
                bundle.putShort(key, (Short) object);
        } else if(String.class.isAssignableFrom(objectClass)) {
            bundle.putString(key, (String) object);
        } else if(List.class.isAssignableFrom(objectClass)) {
            bundle.putBundle(key, pojo2Bundle(object));
        } else if(Map.class.isAssignableFrom(objectClass)) {
            bundle.putBundle(key, pojo2Bundle(object));
        } else if(Parcelable.class.isAssignableFrom(objectClass)) {
            bundle.putParcelable(key, (Parcelable) object);
        } else if(Serializable.class.isAssignableFrom(objectClass)) {
            bundle.putSerializable(key, (Serializable) object);
        } else {
            bundle.putBundle(key, pojo2Bundle(object));
        }
    }

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ READ STUFF FROM BUNDLE INTO OBJECT

    /**
     * Read pojo from a bundle and populate a new POJO
     * @param bundle the bundle to read from
     * @param pojoClass the class of the object used to write into
     * @return the new object read from the bundle
     */
    public static <K> K bundle2Pojo(Bundle bundle, Class<K> pojoClass) {
        return bundle2Pojo(bundle, pojoClass, null);
    }

    /**
     * Read pojo from a bundle and populate a new POJO
     * @param bundle the bundle to read from
     * @param pojoClass the class of the object used to write into
     * @param compondClass if the pojo read from the bundle is of collection type, this is the class of the inner objects
     * @return the new object read from the bundle
     */
    private static <K> K bundle2Pojo(Bundle bundle, Class<K> pojoClass, Class compondClass) {
        K pojo = null;
        try {
            pojo = pojoClass.newInstance();
        } catch (InstantiationException e) {
            //do nothing just leave pojo null
        } catch (IllegalAccessException e) {
            //do nothing just leave pojo null
        }
        bundle2Pojo(bundle, pojo, compondClass);
        return pojo;
    }

    /**
     * Read pojo from a bundle and populate an existing Object
     * @param bundle the bundle to read from
     * @param pojo the object to write into
     */
    public static void bundle2Pojo(Bundle bundle, Object pojo) {
        bundle2Pojo(bundle, pojo, null);
    }

    /**
     * Read pojo from a bundle and populate an existing Object
     * @param bundle the bundle to read from
     * @param pojo the object to write into
     * @param compoundClass if the pojo read from the bundle is of collection type, this is the class of the inner objects
     */
    @SuppressWarnings("unchecked")
    private static void bundle2Pojo(Bundle bundle, Object pojo, Class compoundClass) {
        if((pojo == null) || (bundle == null)) return;

        if(List.class.isAssignableFrom(pojo.getClass())) {
            Integer current = 0;
            List list = (List) pojo;

            //obtain a sorted array of string keys from the bundle
            String[] keys = new String[bundle.size()];
            bundle.keySet().toArray(keys);
            int[] intKeys = new int[keys.length];
            for (int i = 0; i < keys.length; i++) {
            	intKeys[i] = Integer.parseInt(keys[i]);
            }
            java.util.Arrays.sort(intKeys);

            //go through the bundle elements
            for (Integer key : intKeys) {
                //if the current index exists inf the list
                if(list.size() > current) {
                    //obtain old item from list
                    Object oldItem = list.get(current);
                    //read new value into old item (in case od complex item, this will retain any non-overridden elements)
                    Object newItem = getObjcetFromBundle(key.toString(), bundle, oldItem, compoundClass, null);
                    list.set(current, newItem);
                } else {
                    //just read the new item
                    Object newItem = getObjcetFromBundle(key.toString(), bundle, null, compoundClass, null);
                    //add the new item to the list
                    list.add(newItem);
                }
                current++;
            }
        } else if(Map.class.isAssignableFrom(pojo.getClass())) {
            Map map = (Map) pojo;
            //go through the bundle elements
            for (String key : bundle.keySet()) {
                //try to take original value from the map if any
                Object oldObject = (map.containsKey(key))? map.get(key) : null;
                //deserialize a new object or into the old object... it depends
                map.put(key, getObjcetFromBundle(key, bundle, oldObject, compoundClass, null));
            }
        } else {
            Map<Bundled, Field> annotatedFields = getAnnotatedFields(pojo.getClass(), Bundled.class);
            for (Bundled bundled : annotatedFields.keySet()) {
                Field field = annotatedFields.get(bundled);
                Compound compound = field.getAnnotation(Compound.class);

                //only set the value of the field if the bundle contains a reference to it,
                // otherwise we risk setting a default value to null where the default should remain
                if(bundle.containsKey(bundled.key())) {
                    field.setAccessible(true);
                    Object oldValue = null;

                    try {
                        oldValue = field.get(pojo);
                    } catch (IllegalAccessException e) {
                        //do nothing, leave null for field value
                    }

                    Object fieldValue = getObjcetFromBundle(bundled.key(), bundle, oldValue, field.getType(), (compound == null)? null : compound.type());

                    try {
                        field.set(pojo, fieldValue);
                    } catch (IllegalAccessException e) {
                        //do nothing, leave default value for field on pojo
                    }
                }
            }
        }
    }

    /**
     * Extract a particular object from a bundle basded on the bundle key
     * @param key the string key used with the bundle
     * @param bundle the bundle to extract the object from
     * @param objectClass the expected class of the extracted object
     * @param compoundObjectClass if the extracted object is a collection, this is the class of the inner objects
     * @return the extracted object
     */
    @SuppressWarnings("unchecked")
    private static Object getObjcetFromBundle(String key, Bundle bundle, Object pojo, Class objectClass, Class compoundObjectClass) {
        //put the appropriate bundle value into the bundle
        if((List.class.isAssignableFrom(objectClass))
            ||
            (Map.class.isAssignableFrom(objectClass))) {
            if(pojo == null) {
                return bundle2Pojo(bundle.getBundle(key), objectClass, compoundObjectClass);
            } else {
                bundle2Pojo(bundle.getBundle(key), pojo, compoundObjectClass);
                return pojo;
            }
        } else if (Boolean.class.isAssignableFrom(objectClass)
                ||
                boolean.class.isAssignableFrom(objectClass)
                ||
                Bundle.class.isAssignableFrom(objectClass)
                ||
                Byte.class.isAssignableFrom(objectClass)
                ||
                byte.class.isAssignableFrom(objectClass)
                ||
                Character.class.isAssignableFrom(objectClass)
                ||
                char.class.isAssignableFrom(objectClass)
                ||
                Double.class.isAssignableFrom(objectClass)
                ||
                double.class.isAssignableFrom(objectClass)
                ||
                Float.class.isAssignableFrom(objectClass)
                ||
                float.class.isAssignableFrom(objectClass)
                ||
                Integer.class.isAssignableFrom(objectClass)
                ||
                int.class.isAssignableFrom(objectClass)
                ||
                Long.class.isAssignableFrom(objectClass)
                ||
                long.class.isAssignableFrom(objectClass)
                ||
                Short.class.isAssignableFrom(objectClass)
                ||
                short.class.isAssignableFrom(objectClass)
                ||
                Parcelable.class.isAssignableFrom(objectClass)
                ||
                String.class.isAssignableFrom(objectClass)) {
            return bundle.get(key);
        } else {
            if(pojo == null) {
                return bundle2Pojo(bundle.getBundle(key), objectClass, compoundObjectClass);
            } else {
                bundle2Pojo(bundle.getBundle(key), pojo, compoundObjectClass);
                return pojo;
            }
        }
    }

    /**
     * Get the fields from a type that are annotated with a certain annotation type
     * @param pojoClass the class for which fields are checked
     * @param annotationClass the annotation class we are looking for
     * @param <A> the annotation class type
     * @return a map that associates annotation instance to annotated field instance
     */
    private static <A extends Annotation> Map<A,Field> getAnnotatedFields(Class pojoClass, Class<A> annotationClass) {
        Map<A, Field> annotatedFields = new HashMap<A, Field>();
        Field[] fields = pojoClass.getDeclaredFields();
        for (Field field : fields) {
            A annotation = field.getAnnotation(annotationClass);
            if(annotation != null) {
                annotatedFields.put(annotation, field);
            }
        }

        return annotatedFields;
    }
}
