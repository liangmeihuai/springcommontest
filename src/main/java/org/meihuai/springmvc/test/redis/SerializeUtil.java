package org.meihuai.springmvc.test.redis;

import java.io.*;

/**
 * 序列化操作
 * com.haierubic.mall.util
 * @author chen
 */
public class SerializeUtil {
	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	public static Object unserialize(byte[] bytes) {

		ByteArrayInputStream bais = null;

		if(bytes == null){
			return null;
		}
		
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
	
	public static void main(String... args){
		System.out.println(SerializeUtil.unserialize(SerializeUtil.serialize(null)));
	}

}
