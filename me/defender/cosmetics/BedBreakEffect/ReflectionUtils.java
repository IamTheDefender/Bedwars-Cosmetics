// 
// @author IamTheDefender
// 

package me.defender.cosmetics.BedBreakEffect;

import org.bukkit.Bukkit;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Constructor;

public final class ReflectionUtils
{
    private ReflectionUtils() {
    }
    
    public static Constructor<?> getConstructor(final Class<?> clazz, final Class<?>... parameterTypes) throws NoSuchMethodException {
       
		final @SuppressWarnings("rawtypes") Class[] primitiveTypes = DataType.getPrimitive(parameterTypes);
        Constructor<?>[] constructors;
        for (int length = (constructors = clazz.getConstructors()).length, i = 0; i < length; ++i) {
            final Constructor<?> constructor = constructors[i];
            if (DataType.compare(DataType.getPrimitive(constructor.getParameterTypes()), primitiveTypes)) {
                return constructor;
            }
        }
        throw new NoSuchMethodException("There is no such constructor in this class with the specified parameter types");
    }
    
    public static Constructor<?> getConstructor(final String className, final PackageType packageType, final Class<?>... parameterTypes) throws NoSuchMethodException, ClassNotFoundException {
        return getConstructor(packageType.getClass(className), parameterTypes);
    }
    
    public static Object instantiateObject(final Class<?> clazz, final Object... arguments) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        return getConstructor(clazz, DataType.getPrimitive(arguments)).newInstance(arguments);
    }
    
    public static Object instantiateObject(final String className, final PackageType packageType, final Object... arguments) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
        return instantiateObject(packageType.getClass(className), arguments);
    }
    
    public static Method getMethod(final Class<?> clazz, final String methodName, final Class<?>... parameterTypes) throws NoSuchMethodException {
        final @SuppressWarnings("rawtypes") Class[] primitiveTypes = DataType.getPrimitive(parameterTypes);
        Method[] methods;
        for (int length = (methods = clazz.getMethods()).length, i = 0; i < length; ++i) {
            final Method method = methods[i];
            if (method.getName().equals(methodName) && DataType.compare(DataType.getPrimitive(method.getParameterTypes()), primitiveTypes)) {
                return method;
            }
        }
        throw new NoSuchMethodException("There is no such method in this class with the specified name and parameter types");
    }
    
    public static Method getMethod(final String className, final PackageType packageType, final String methodName, final Class<?>... parameterTypes) throws NoSuchMethodException, ClassNotFoundException {
        return getMethod(packageType.getClass(className), methodName, parameterTypes);
    }
    
    public static Object invokeMethod(final Object instance, final String methodName, final Object... arguments) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        return getMethod(instance.getClass(), methodName, DataType.getPrimitive(arguments)).invoke(instance, arguments);
    }
    
    public static Object invokeMethod(final Object instance, final Class<?> clazz, final String methodName, final Object... arguments) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        return getMethod(clazz, methodName, DataType.getPrimitive(arguments)).invoke(instance, arguments);
    }
    
    public static Object invokeMethod(final Object instance, final String className, final PackageType packageType, final String methodName, final Object... arguments) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
        return invokeMethod(instance, packageType.getClass(className), methodName, arguments);
    }
    
    public static Field getField(final Class<?> clazz, final boolean declared, final String fieldName) throws NoSuchFieldException, SecurityException {
        final Field field = declared ? clazz.getDeclaredField(fieldName) : clazz.getField(fieldName);
        field.setAccessible(true);
        return field;
    }
    
    public static Field getField(final String className, final PackageType packageType, final boolean declared, final String fieldName) throws NoSuchFieldException, SecurityException, ClassNotFoundException {
        return getField(packageType.getClass(className), declared, fieldName);
    }
    
    public static Object getValue(final Object instance, final Class<?> clazz, final boolean declared, final String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        return getField(clazz, declared, fieldName).get(instance);
    }
    
    public static Object getValue(final Object instance, final String className, final PackageType packageType, final boolean declared, final String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException {
        return getValue(instance, packageType.getClass(className), declared, fieldName);
    }
    
    public static Object getValue(final Object instance, final boolean declared, final String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        return getValue(instance, instance.getClass(), declared, fieldName);
    }
    
    public static void setValue(final Object instance, final Class<?> clazz, final boolean declared, final String fieldName, final Object value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        getField(clazz, declared, fieldName).set(instance, value);
    }
    
    public static void setValue(final Object instance, final String className, final PackageType packageType, final boolean declared, final String fieldName, final Object value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException {
        setValue(instance, packageType.getClass(className), declared, fieldName, value);
    }
    
    public static void setValue(final Object instance, final boolean declared, final String fieldName, final Object value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        setValue(instance, instance.getClass(), declared, fieldName, value);
    }
    
    public enum DataType
    {
        BYTE(Byte.TYPE, Byte.class),
        SHORT(Short.TYPE, Short.class),
        INTEGER(Integer.TYPE, Integer.class),
        LONG(Long.TYPE, Long.class),
        CHARACTER(Character.TYPE, Character.class),
        FLOAT(Float.TYPE, Float.class),
        DOUBLE(Double.TYPE, Double.class),
        BOOLEAN(Boolean.TYPE, Boolean.class);
        
        private static final Map<Class<?>, DataType> CLASS_MAP;
        private final Class<?> primitive;
        private final Class<?> reference;
        
        static {
            CLASS_MAP = new HashMap<Class<?>, DataType>();
            DataType[] values;
            for (int length = (values = values()).length, i = 0; i < length; ++i) {
                final DataType type = values[i];
                DataType.CLASS_MAP.put(type.primitive, type);
                DataType.CLASS_MAP.put(type.reference, type);
            }
        }
        
        DataType(final Class<?> primitive, final Class<?> reference) {
            this.primitive = primitive;
            this.reference = reference;
        }
        
        public Class<?> getPrimitive() {
            return this.primitive;
        }
        
        public Class<?> getReference() {
            return this.reference;
        }
        
        public static DataType fromClass(final Class<?> clazz) {
            return DataType.CLASS_MAP.get(clazz);
        }
        
        public static Class<?> getPrimitive(final Class<?> clazz) {
            final DataType type = fromClass(clazz);
            return (type == null) ? clazz : type.getPrimitive();
        }
        
        public static Class<?> getReference(final Class<?> clazz) {
            final DataType type = fromClass(clazz);
            return (type == null) ? clazz : type.getReference();
        }
        
        
		public static Class<?>[] getPrimitive(final Class<?>[] classes) {
            final int length = (classes == null) ? 0 : classes.length;
            final @SuppressWarnings("rawtypes") Class[] types = new Class[length];
            for (int index = 0; index < length; ++index) {
                types[index] = getPrimitive(classes[index]);
            }
            return types;
        }
        
        public static Class<?>[] getReference(final Class<?>[] classes) {
            final int length = (classes == null) ? 0 : classes.length;
            final @SuppressWarnings("rawtypes") Class[] types = new Class[length];
            for (int index = 0; index < length; ++index) {
                types[index] = getReference(classes[index]);
            }
            return types;
        }
        
        public static Class<?>[] getPrimitive(final Object[] objects) {
            final int length = (objects == null) ? 0 : objects.length;
            final @SuppressWarnings("rawtypes") Class[] types = new Class[length];
            for (int index = 0; index < length; ++index) {
                types[index] = getPrimitive(objects[index].getClass());
            }
            return types;
        }
        
        public static Class<?>[] getReference(final Object[] objects) {
            final int length = (objects == null) ? 0 : objects.length;
            final @SuppressWarnings("rawtypes") Class[] types = new Class[length];
            for (int index = 0; index < length; ++index) {
                types[index] = getReference(objects[index].getClass());
            }
            return types;
        }
        
        public static boolean compare(final Class<?>[] primary, final Class<?>[] secondary) {
            if (primary == null || secondary == null || primary.length != secondary.length) {
                return false;
            }
            for (int index = 0; index < primary.length; ++index) {
                final Class<?> primaryClass = primary[index];
                final Class<?> secondaryClass = secondary[index];
                if (!primaryClass.equals(secondaryClass) && !primaryClass.isAssignableFrom(secondaryClass)) {
                    return false;
                }
            }
            return true;
        }
    }
    
    public enum PackageType
    {
        MINECRAFT_SERVER("MINECRAFT_SERVER", 0, "net.minecraft.server." + getServerVersion()), 
        CRAFTBUKKIT("CRAFTBUKKIT", 1, "org.bukkit.craftbukkit." + getServerVersion()), 
        CRAFTBUKKIT_BLOCK("CRAFTBUKKIT_BLOCK", 2, PackageType.CRAFTBUKKIT, "block"), 
        CRAFTBUKKIT_CHUNKIO("CRAFTBUKKIT_CHUNKIO", 3, PackageType.CRAFTBUKKIT, "chunkio"), 
        CRAFTBUKKIT_COMMAND("CRAFTBUKKIT_COMMAND", 4, PackageType.CRAFTBUKKIT, "command"), 
        CRAFTBUKKIT_CONVERSATIONS("CRAFTBUKKIT_CONVERSATIONS", 5, PackageType.CRAFTBUKKIT, "conversations"), 
        CRAFTBUKKIT_ENCHANTMENS("CRAFTBUKKIT_ENCHANTMENS", 6, PackageType.CRAFTBUKKIT, "enchantments"), 
        CRAFTBUKKIT_ENTITY("CRAFTBUKKIT_ENTITY", 7, PackageType.CRAFTBUKKIT, "entity"), 
        CRAFTBUKKIT_EVENT("CRAFTBUKKIT_EVENT", 8, PackageType.CRAFTBUKKIT, "event"), 
        CRAFTBUKKIT_GENERATOR("CRAFTBUKKIT_GENERATOR", 9, PackageType.CRAFTBUKKIT, "generator"), 
        CRAFTBUKKIT_HELP("CRAFTBUKKIT_HELP", 10, PackageType.CRAFTBUKKIT, "help"), 
        CRAFTBUKKIT_INVENTORY("CRAFTBUKKIT_INVENTORY", 11, PackageType.CRAFTBUKKIT, "inventory"), 
        CRAFTBUKKIT_MAP("CRAFTBUKKIT_MAP", 12, PackageType.CRAFTBUKKIT, "map"), 
        CRAFTBUKKIT_METADATA("CRAFTBUKKIT_METADATA", 13, PackageType.CRAFTBUKKIT, "metadata"), 
        CRAFTBUKKIT_POTION("CRAFTBUKKIT_POTION", 14, PackageType.CRAFTBUKKIT, "potion"), 
        CRAFTBUKKIT_PROJECTILES("CRAFTBUKKIT_PROJECTILES", 15, PackageType.CRAFTBUKKIT, "projectiles"), 
        CRAFTBUKKIT_SCHEDULER("CRAFTBUKKIT_SCHEDULER", 16, PackageType.CRAFTBUKKIT, "scheduler"), 
        CRAFTBUKKIT_SCOREBOARD("CRAFTBUKKIT_SCOREBOARD", 17, PackageType.CRAFTBUKKIT, "scoreboard"), 
        CRAFTBUKKIT_UPDATER("CRAFTBUKKIT_UPDATER", 18, PackageType.CRAFTBUKKIT, "updater"), 
        CRAFTBUKKIT_UTIL("CRAFTBUKKIT_UTIL", 19, PackageType.CRAFTBUKKIT, "util");
        
        private final String path;
        
        PackageType(final String name, final int ordinal, final String path) {
            this.path = path;
        }
        
        PackageType(final String s, final int n, final PackageType parent, final String path) {
            this(s, n, parent + "." + path);
        }
        
        public String getPath() {
            return this.path;
        }
        
        public Class<?> getClass(final String className) throws ClassNotFoundException {
            return Class.forName(this + "." + className);
        }
        
        @Override
        public String toString() {
            return this.path;
        }
        
        public static String getServerVersion() {
            return Bukkit.getServer().getClass().getPackage().getName().substring(23);
        }
    }
}
