package com.upbest.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.upbest.util.securedpreference.DefaultRecoveryHandler;
import com.upbest.util.securedpreference.SecuredPreferenceStore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * 本地化储存 偏好设置工具类（进行了加密）
 *
 * @author kang gui yang
 */
public class PreUtils {
    private volatile static PreUtils instance;
    //定义一个SharePreference对象
    private SharedPreferences sharedPres;

    /**
     * 使用的单例实现
     *
     * @return
     */
    public static PreUtils getInstance() {
        if (null == instance) {
            synchronized (PreUtils.class) {
                if (null == instance) {
                    instance = new PreUtils();
                }
            }
        }
        return instance;
    }

    //在AppBase里面初始化
    public void init(Context context) {
        try {
            //not mandatory, can be null too
            String storeFileName = "2F6CC6165d0E60E7";
            //not mandatory, can be null too
            String keyPrefix = "d627b3AD6C902C2C";
            //it's better to provide one, and you need to provide the same key each time after the first time
            byte[] seedKey = "AA359C41D9f9a5f6".getBytes();
            SecuredPreferenceStore.init(context, storeFileName, keyPrefix, seedKey, new DefaultRecoveryHandler());
            sharedPres = SecuredPreferenceStore.getSharedInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取实例
     */
    private SharedPreferences getSharedPreferences() {
        return sharedPres;
    }


    public static boolean contains(Context context, String key) {
        return SecuredPreferenceStore.getSharedInstance().contains(key);
    }

    //int类型
    public int getInt(String key, int defaultValue) {
        return sharedPres.getInt(key, defaultValue);
    }

    public boolean putInt(String key, int pValue) {
        return sharedPres.edit().putInt(key, pValue).commit();
    }

    //long类型
    public long getLong(String key, long defaultValue) {
        return sharedPres.getLong(key, defaultValue);
    }

    public boolean putLong(String key, Long defaultValue) {
        return sharedPres.edit().putLong(key, defaultValue).commit();
    }

    //boolean类型
    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPres.getBoolean(key, defaultValue);
    }

    public boolean putBoolean(String key, boolean pValue) {
        return sharedPres.edit().putBoolean(key, pValue).commit();
    }

    //String类型
    public boolean putString(String key, String pValue) {
        return sharedPres.edit().putString(key, pValue).commit();
    }

    public String getString(String key, String defaultValue) {
        return sharedPres.getString(key, defaultValue);
    }


    /**
     * 针对复杂类型存储<对象>
     *
     * @param key
     * @param objValue
     */
    public void putObj(String key, Object objValue) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(baos);
            out.writeObject(objValue);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            sharedPres.edit().putString(key, objectVal).apply();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @SuppressWarnings("unchecked")
    public <T> T getObject(String key, Class<T> clazz) {
        if (sharedPres.contains(key)) {
            String objectVal = sharedPres.getString(key, null);
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    /**
     * 根据值移除数据
     *
     * @param key key
     * @return true or false
     */
    public boolean remove(String key) {
        sharedPres.edit().remove(key).apply();
        return sharedPres.edit().commit();
    }


    /**
     * 清除当前文件的所有的数据
     */
    public void clear() {
        sharedPres.edit().clear().apply();
    }
}