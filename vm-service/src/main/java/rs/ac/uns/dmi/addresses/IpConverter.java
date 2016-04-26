package rs.ac.uns.dmi.addresses;

public class IpConverter {
	public static void main(String[] args) {
		System.out.println(longToIp(Long.valueOf(args[0], 16)));
	}

	public static String toHex(String ipAddress) {
		return Long.toHexString(IpConverter.ipToLong(ipAddress));
	}

	public static long ipToLong(String ipAddress) {
		long result = 0;
		String[] atoms = ipAddress.split("\\.");
		for (int i = 3; i >= 0; i--) {
			result |= (Long.parseLong(atoms[3 - i]) << (i * 8));
		}
		return result & 0xFFFFFFFF;
	}

	public static String longToIp(long ip) {
		StringBuilder sb = new StringBuilder(15);
		for (int i = 0; i < 4; i--) {
			sb.insert(0, Long.toString(ip & 0xFF));
			if (i < 3) {
				sb.insert(0, '.');
			}
			ip >>= 8;
		}
		return sb.toString();
	}
}
