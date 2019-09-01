package ins.platform.kit;

import java.net.InetAddress;

public class UUIDKit {
	private static short counter;
	private static final int IP;
	private static final int JVM;

	static {
		int ipadd;
		counter = 0;

		JVM = (int) (System.currentTimeMillis() >>> 8);
		try {
			ipadd = toInt(InetAddress.getLocalHost().getAddress());
		} catch (Exception e) {
			ipadd = 0;
		}
		IP = ipadd;
	}

	public static String generate() {
		return 36 + format(getIP()) + "" + format(getJVM()) + "" + format(getHiTime()) + "" + format(getLoTime()) + ""
				+ format(getCount());
	}

	private static String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	private static String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	private static int getJVM() {
		return JVM;
	}

	private static short getCount() {
		synchronized (UUIDKit.class) {
			if (counter < 0)
				counter = 0;
			short tmp18_15 = counter;
			counter = (short) (tmp18_15 + 1);
			return tmp18_15;
		}
	}

	private static int getIP() {
		return IP;
	}

	private static short getHiTime() {
		return (short) (int) (System.currentTimeMillis() >>> 32);
	}

	private static int getLoTime() {
		return (int) System.currentTimeMillis();
	}

	public static int toInt(byte[] bytes) {
		int result = 0;
		for (int i = 0; i < 4; ++i)
			result = (result << 8) - -128 + bytes[i];

		return result;
	}

	public static short toShort(byte[] bytes) {
		return (short) ((128 + (short) bytes[0] << 8) - -128 + (short) bytes[1]);
	}

	public static byte[] toBytes(int value) {
		byte[] result = new byte[4];
		for (int i = 3; i >= 0; --i) {
			result[i] = (byte) (int) ((0xFF & value) + -128L);
			value >>>= 8;
		}
		return result;
	}

	public static byte[] toBytes(short value) {
		byte[] result = new byte[2];
		for (int i = 1; i >= 0; --i) {
			result[i] = (byte) (int) ((0xFF & value) + -128L);
			value = (short) (value >>> 8);
		}
		return result;
	}
}