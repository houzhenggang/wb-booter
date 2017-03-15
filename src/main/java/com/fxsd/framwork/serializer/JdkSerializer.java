package com.fxsd.framwork.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author cjh
 * @version 1.0
 * @param <T> 类型
 */
public class JdkSerializer<T> implements Serializer<T>{

	public byte[] serialize(T obj) {
		byte[] buffer = null;
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null; 
		try {
			baos = new ByteArrayOutputStream(); 
			oos = new ObjectOutputStream(baos); 
			oos.writeObject(obj); 
			buffer = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return buffer;
	}

	@SuppressWarnings("unchecked")
	public T deserialize(byte[] bytes) {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
			return (T) ois.readObject(); 
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
