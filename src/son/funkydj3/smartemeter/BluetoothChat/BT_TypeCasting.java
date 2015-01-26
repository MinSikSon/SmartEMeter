package son.funkydj3.smartemeter.BluetoothChat;

import android.util.Log;

public class BT_TypeCasting {

	// *point* 30byte�� �������� ��
	public static String byteToHex(byte[] bytes) {
		byte[] tmpBytes;
		tmpBytes = bytes;
		int count = 0;
		/*
		 * for(byte b: tmpBytes){ Log.d("SON", "byteToHex : " + b); count++;
		 * if(count == 29) break; }
		 */
		// Log.d("SON",
		// "=========================================================");

		count = 0;
		StringBuilder sb = new StringBuilder();
		for (byte b : tmpBytes) {
			sb.append(String.format("%02X ", b));
			count++;
			if (count == 20)
				break;
		}
		// Log.d("SON", "��� : " + sb);
		// Log.d("SON",
		// "=========================================================");

		// System.out.println(sb.toString());
		// prints "FF 00 01 02 03 "
		return sb.toString();
	}

	// *point*
	// *point* String�� Hex byte[]�� �ٲٴ�..
	public static byte[] SendByteData(String hexString) {
		byte[] sendingThisByteArray = new byte[hexString.length() / 2];
		int count = 0;

		for (int i = 0; i < hexString.length() - 1; i += 2) {
			// grab the hex in pairs
			String output = hexString.substring(i, (i + 2));

			// convert the 2 characters in the 'output' string to the hex number
			int decimal = (int) (Integer.parseInt(output, 16));

			// place into array for sending
			sendingThisByteArray[count] = (byte) (decimal);

			// Log.d(TAG, "in byte array = " + sendingThisByteArray[count]);
			count++;
		}

		return sendingThisByteArray;
	}

	public static double _4HexToDec(String mHex) {
		double mDec = 0;
		double Hex_1000 = 0, Hex_0100 = 0, Hex_0010 = 0, Hex_0001 = 0;
		char[] mChar = mHex.toCharArray();
		if (mChar[0] == '0') {
			Hex_1000 = 4096 * 0;
		} else if (mChar[0] == '1') {
			Hex_1000 = 4096 * 1;
		} else if (mChar[0] == '2') {
			Hex_1000 = 4096 * 2;
		} else if (mChar[0] == '3') {
			Hex_1000 = 4096 * 3;
		} else if (mChar[0] == '4') {
			Hex_1000 = 4096 * 4;
		} else if (mChar[0] == '5') {
			Hex_1000 = 4096 * 5;
		} else if (mChar[0] == '6') {
			Hex_1000 = 4096 * 6;
		} else if (mChar[0] == '7') {
			Hex_1000 = 4096 * 7;
		} else if (mChar[0] == '8') {
			Hex_1000 = 4096 * 8;
		} else if (mChar[0] == '9') {
			Hex_1000 = 4096 * 9;
		} else if (mChar[0] == 'A') {
			Hex_1000 = 4096 * 10;
		} else if (mChar[0] == 'B') {
			Hex_1000 = 4096 * 11;
		} else if (mChar[0] == 'C') {
			Hex_1000 = 4096 * 12;
		} else if (mChar[0] == 'D') {
			Hex_1000 = 4096 * 13;
		} else if (mChar[0] == 'E') {
			Hex_1000 = 4096 * 14;
		} else if (mChar[0] == 'F') {
			Hex_1000 = 4096 * 15;
		}

		if (mChar[1] == '0') {
			Hex_0100 = 256 * 0;
		} else if (mChar[1] == '1') {
			Hex_0100 = 256 * 1;
		} else if (mChar[1] == '2') {
			Hex_0100 = 256 * 2;
		} else if (mChar[1] == '3') {
			Hex_0100 = 256 * 3;
		} else if (mChar[1] == '4') {
			Hex_0100 = 256 * 4;
		} else if (mChar[1] == '5') {
			Hex_0100 = 256 * 5;
		} else if (mChar[1] == '6') {
			Hex_0100 = 256 * 6;
		} else if (mChar[1] == '7') {
			Hex_0100 = 256 * 7;
		} else if (mChar[1] == '8') {
			Hex_0100 = 256 * 8;
		} else if (mChar[1] == '9') {
			Hex_0100 = 256 * 9;
		} else if (mChar[1] == 'A') {
			Hex_0100 = 256 * 10;
		} else if (mChar[1] == 'B') {
			Hex_0100 = 256 * 11;
		} else if (mChar[1] == 'C') {
			Hex_0100 = 256 * 12;
		} else if (mChar[1] == 'D') {
			Hex_0100 = 256 * 13;
		} else if (mChar[1] == 'E') {
			Hex_0100 = 256 * 14;
		} else if (mChar[1] == 'F') {
			Hex_0100 = 256 * 15;
		}

		if (mChar[2] == '0') {
			Hex_0010 = 16 * 0;
		} else if (mChar[2] == '1') {
			Hex_0010 = 16 * 1;
		} else if (mChar[2] == '2') {
			Hex_0010 = 16 * 2;
		} else if (mChar[2] == '3') {
			Hex_0010 = 16 * 3;
		} else if (mChar[2] == '4') {
			Hex_0010 = 16 * 4;
		} else if (mChar[2] == '5') {
			Hex_0010 = 16 * 5;
		} else if (mChar[2] == '6') {
			Hex_0010 = 16 * 6;
		} else if (mChar[2] == '7') {
			Hex_0010 = 16 * 7;
		} else if (mChar[2] == '8') {
			Hex_0010 = 16 * 8;
		} else if (mChar[2] == '9') {
			Hex_0010 = 16 * 9;
		} else if (mChar[2] == 'A') {
			Hex_0010 = 16 * 10;
		} else if (mChar[2] == 'B') {
			Hex_0010 = 16 * 11;
		} else if (mChar[2] == 'C') {
			Hex_0010 = 16 * 12;
		} else if (mChar[2] == 'D') {
			Hex_0010 = 16 * 13;
		} else if (mChar[2] == 'E') {
			Hex_0010 = 16 * 14;
		} else if (mChar[2] == 'F') {
			Hex_0010 = 16 * 15;
		}

		if (mChar[3] == '0') {
			Hex_0001 = 0;
		} else if (mChar[3] == '1') {
			Hex_0001 = 1;
		} else if (mChar[3] == '2') {
			Hex_0001 = 2;
		} else if (mChar[3] == '3') {
			Hex_0001 = 3;
		} else if (mChar[3] == '4') {
			Hex_0001 = 4;
		} else if (mChar[3] == '5') {
			Hex_0001 = 5;
		} else if (mChar[3] == '6') {
			Hex_0001 = 6;
		} else if (mChar[3] == '7') {
			Hex_0001 = 7;
		} else if (mChar[3] == '8') {
			Hex_0001 = 8;
		} else if (mChar[3] == '9') {
			Hex_0001 = 9;
		} else if (mChar[3] == 'A') {
			Hex_0001 = 10;
		} else if (mChar[3] == 'B') {
			Hex_0001 = 11;
		} else if (mChar[3] == 'C') {
			Hex_0001 = 12;
		} else if (mChar[3] == 'D') {
			Hex_0001 = 13;
		} else if (mChar[3] == 'E') {
			Hex_0001 = 14;
		} else if (mChar[3] == 'F') {
			Hex_0001 = 15;
		}

		mDec = Hex_1000 + Hex_0100 + Hex_0010 + Hex_0001;
		return mDec;
	}

	public static double _6HexToDec(String mHex) {
		double mDec = 0;
		double Hex_100000 = 0, Hex_010000 = 0, Hex_001000 = 0, Hex_000100 = 0, Hex_000010 = 0, Hex_000001 = 0;
		char[] mChar = mHex.toCharArray();
		if (mChar[4] == '0') {
			Hex_001000 = 0;
		} else if (mChar[4] == '1') {
			Hex_001000 = 4096 * 1;
		} else if (mChar[4] == '2') {
			Hex_001000 = 4096 * 2;
		} else if (mChar[4] == '3') {
			Hex_001000 = 4096 * 3;
		} else if (mChar[4] == '4') {
			Hex_001000 = 4096 * 4;
		} else if (mChar[4] == '5') {
			Hex_001000 = 4096 * 5;
		} else if (mChar[4] == '6') {
			Hex_001000 = 4096 * 6;
		} else if (mChar[4] == '7') {
			Hex_001000 = 4096 * 7;
		} else if (mChar[4] == '8') {
			Hex_001000 = 4096 * 8;
		} else if (mChar[4] == '9') {
			Hex_001000 = 4096 * 9;
		} else if (mChar[4] == 'A') {
			Hex_001000 = 4096 * 10;
		} else if (mChar[4] == 'B') {
			Hex_001000 = 4096 * 11;
		} else if (mChar[4] == 'C') {
			Hex_001000 = 4096 * 12;
		} else if (mChar[4] == 'D') {
			Hex_001000 = 4096 * 13;
		} else if (mChar[4] == 'E') {
			Hex_001000 = 4096 * 14;
		} else if (mChar[4] == 'F') {
			Hex_001000 = 4096 * 15;
		}

		if (mChar[5] == '0') {
			Hex_000100 = 0;
		} else if (mChar[5] == '1') {
			Hex_000100 = 256 * 1;
		} else if (mChar[5] == '2') {
			Hex_000100 = 256 * 2;
		} else if (mChar[5] == '3') {
			Hex_000100 = 256 * 3;
		} else if (mChar[5] == '4') {
			Hex_000100 = 256 * 4;
		} else if (mChar[5] == '5') {
			Hex_000100 = 256 * 5;
		} else if (mChar[5] == '6') {
			Hex_000100 = 256 * 6;
		} else if (mChar[5] == '7') {
			Hex_000100 = 256 * 7;
		} else if (mChar[5] == '8') {
			Hex_000100 = 256 * 8;
		} else if (mChar[5] == '9') {
			Hex_000100 = 256 * 9;
		} else if (mChar[5] == 'A') {
			Hex_000100 = 256 * 10;
		} else if (mChar[5] == 'B') {
			Hex_000100 = 256 * 11;
		} else if (mChar[5] == 'C') {
			Hex_000100 = 256 * 12;
		} else if (mChar[5] == 'D') {
			Hex_000100 = 256 * 13;
		} else if (mChar[5] == 'E') {
			Hex_000100 = 256 * 14;
		} else if (mChar[5] == 'F') {
			Hex_000100 = 256 * 15;
		}

		if (mChar[6] == '0') {
			Hex_000010 = 0;
		} else if (mChar[6] == '1') {
			Hex_000010 = 16 * 1;
		} else if (mChar[6] == '2') {
			Hex_000010 = 16 * 2;
		} else if (mChar[6] == '3') {
			Hex_000010 = 16 * 3;
		} else if (mChar[6] == '4') {
			Hex_000010 = 16 * 4;
		} else if (mChar[6] == '5') {
			Hex_000010 = 16 * 5;
		} else if (mChar[6] == '6') {
			Hex_000010 = 16 * 6;
		} else if (mChar[6] == '7') {
			Hex_000010 = 16 * 7;
		} else if (mChar[6] == '8') {
			Hex_000010 = 16 * 8;
		} else if (mChar[6] == '9') {
			Hex_000010 = 16 * 9;
		} else if (mChar[6] == 'A') {
			Hex_000010 = 16 * 10;
		} else if (mChar[6] == 'B') {
			Hex_000010 = 16 * 11;
		} else if (mChar[6] == 'C') {
			Hex_000010 = 16 * 12;
		} else if (mChar[6] == 'D') {
			Hex_000010 = 16 * 13;
		} else if (mChar[6] == 'E') {
			Hex_000010 = 16 * 14;
		} else if (mChar[6] == 'F') {
			Hex_000010 = 16 * 15;
		}

		if (mChar[7] == '0') {
			Hex_000001 = 0;
		} else if (mChar[7] == '1') {
			Hex_000001 = 1;
		} else if (mChar[7] == '2') {
			Hex_000001 = 2;
		} else if (mChar[7] == '3') {
			Hex_000001 = 3;
		} else if (mChar[7] == '4') {
			Hex_000001 = 4;
		} else if (mChar[7] == '5') {
			Hex_000001 = 5;
		} else if (mChar[7] == '6') {
			Hex_000001 = 6;
		} else if (mChar[7] == '7') {
			Hex_000001 = 7;
		} else if (mChar[7] == '8') {
			Hex_000001 = 8;
		} else if (mChar[7] == '9') {
			Hex_000001 = 9;
		} else if (mChar[7] == 'A') {
			Hex_000001 = 10;
		} else if (mChar[7] == 'B') {
			Hex_000001 = 11;
		} else if (mChar[7] == 'C') {
			Hex_000001 = 12;
		} else if (mChar[7] == 'D') {
			Hex_000001 = 13;
		} else if (mChar[7] == 'E') {
			Hex_000001 = 14;
		} else if (mChar[7] == 'F') {
			Hex_000001 = 15;
		}

		mDec = Hex_001000 + Hex_000100 + Hex_000010 + Hex_000001;
		return mDec;
	}

	public static double _8HexToDec(String mHex) {
		double mDec = 0;
		double Hex_1000_0000 = 0, Hex_0100_0000 = 0, Hex_0010_0000 = 0, Hex_0001_0000 = 0;
		double Hex_0000_1000 = 0, Hex_0000_0100 = 0, Hex_0000_0010 = 0, Hex_0000_0001 = 0;
		char[] mChar = mHex.toCharArray();

		if (mChar[0] == '0') {
			Hex_1000_0000 = 268435456 * 0;
		} else if (mChar[0] == '1') {
			Hex_1000_0000 = 268435456 * 1;
		} else if (mChar[0] == '2') {
			Hex_1000_0000 = 268435456 * 2;
		} else if (mChar[0] == '3') {
			Hex_1000_0000 = 268435456 * 3;
		} else if (mChar[0] == '4') {
			Hex_1000_0000 = 268435456 * 4;
		} else if (mChar[0] == '5') {
			Hex_1000_0000 = 268435456 * 5;
		} else if (mChar[0] == '6') {
			Hex_1000_0000 = 268435456 * 6;
		} else if (mChar[0] == '7') {
			Hex_1000_0000 = 268435456 * 7;
		} else if (mChar[0] == '8') {
			Hex_1000_0000 = 268435456 * 8;
		} else if (mChar[0] == '9') {
			Hex_1000_0000 = 268435456 * 9;
		} else if (mChar[0] == 'A') {
			Hex_1000_0000 = 268435456 * 10;
		} else if (mChar[0] == 'B') {
			Hex_1000_0000 = 268435456 * 11;
		} else if (mChar[0] == 'C') {
			Hex_1000_0000 = 268435456 * 12;
		} else if (mChar[0] == 'D') {
			Hex_1000_0000 = 268435456 * 13;
		} else if (mChar[0] == 'E') {
			Hex_1000_0000 = 268435456 * 14;
		} else if (mChar[0] == 'F') {
			Hex_1000_0000 = 268435456 * 15;
		}
		if (mChar[1] == '0') {
			Hex_0100_0000 = 16777216 * 0;
		} else if (mChar[1] == '1') {
			Hex_0100_0000 = 16777216 * 1;
		} else if (mChar[1] == '2') {
			Hex_0100_0000 = 16777216 * 2;
		} else if (mChar[1] == '3') {
			Hex_0100_0000 = 16777216 * 3;
		} else if (mChar[1] == '4') {
			Hex_0100_0000 = 16777216 * 4;
		} else if (mChar[1] == '5') {
			Hex_0100_0000 = 16777216 * 5;
		} else if (mChar[1] == '6') {
			Hex_0100_0000 = 16777216 * 6;
		} else if (mChar[1] == '7') {
			Hex_0100_0000 = 16777216 * 7;
		} else if (mChar[1] == '8') {
			Hex_0100_0000 = 16777216 * 8;
		} else if (mChar[1] == '9') {
			Hex_0100_0000 = 16777216 * 9;
		} else if (mChar[1] == 'A') {
			Hex_0100_0000 = 16777216 * 10;
		} else if (mChar[1] == 'B') {
			Hex_0100_0000 = 16777216 * 11;
		} else if (mChar[1] == 'C') {
			Hex_0100_0000 = 16777216 * 12;
		} else if (mChar[1] == 'D') {
			Hex_0100_0000 = 16777216 * 13;
		} else if (mChar[1] == 'E') {
			Hex_0100_0000 = 16777216 * 14;
		} else if (mChar[1] == 'F') {
			Hex_0100_0000 = 16777216 * 15;
		}
		if (mChar[2] == '0') {
			Hex_0010_0000 = 1048576 * 0;
		} else if (mChar[2] == '1') {
			Hex_0010_0000 = 1048576 * 1;
		} else if (mChar[2] == '2') {
			Hex_0010_0000 = 1048576 * 2;
		} else if (mChar[2] == '3') {
			Hex_0010_0000 = 1048576 * 3;
		} else if (mChar[2] == '4') {
			Hex_0010_0000 = 1048576 * 4;
		} else if (mChar[2] == '5') {
			Hex_0010_0000 = 1048576 * 5;
		} else if (mChar[2] == '6') {
			Hex_0010_0000 = 1048576 * 6;
		} else if (mChar[2] == '7') {
			Hex_0010_0000 = 1048576 * 7;
		} else if (mChar[2] == '8') {
			Hex_0010_0000 = 1048576 * 8;
		} else if (mChar[2] == '9') {
			Hex_0010_0000 = 1048576 * 9;
		} else if (mChar[2] == 'A') {
			Hex_0010_0000 = 1048576 * 10;
		} else if (mChar[2] == 'B') {
			Hex_0010_0000 = 1048576 * 11;
		} else if (mChar[2] == 'C') {
			Hex_0010_0000 = 1048576 * 12;
		} else if (mChar[2] == 'D') {
			Hex_0010_0000 = 1048576 * 13;
		} else if (mChar[2] == 'E') {
			Hex_0010_0000 = 1048576 * 14;
		} else if (mChar[2] == 'F') {
			Hex_0010_0000 = 1048576 * 15;
		}
		if (mChar[3] == '0') {
			Hex_0001_0000 = 65536 * 0;
		} else if (mChar[3] == '1') {
			Hex_0001_0000 = 65536 * 1;
		} else if (mChar[3] == '2') {
			Hex_0001_0000 = 65536 * 2;
		} else if (mChar[3] == '3') {
			Hex_0001_0000 = 65536 * 3;
		} else if (mChar[3] == '4') {
			Hex_0001_0000 = 65536 * 4;
		} else if (mChar[3] == '5') {
			Hex_0001_0000 = 65536 * 5;
		} else if (mChar[3] == '6') {
			Hex_0001_0000 = 65536 * 6;
		} else if (mChar[3] == '7') {
			Hex_0001_0000 = 65536 * 7;
		} else if (mChar[3] == '8') {
			Hex_0001_0000 = 65536 * 8;
		} else if (mChar[3] == '9') {
			Hex_0001_0000 = 65536 * 9;
		} else if (mChar[3] == 'A') {
			Hex_0001_0000 = 65536 * 10;
		} else if (mChar[3] == 'B') {
			Hex_0001_0000 = 65536 * 11;
		} else if (mChar[3] == 'C') {
			Hex_0001_0000 = 65536 * 12;
		} else if (mChar[3] == 'D') {
			Hex_0001_0000 = 65536 * 13;
		} else if (mChar[3] == 'E') {
			Hex_0001_0000 = 65536 * 14;
		} else if (mChar[3] == 'F') {
			Hex_0001_0000 = 65536 * 15;
		}
		if (mChar[4] == '0') {
			Hex_0000_1000 = 4096 * 0;
		} else if (mChar[4] == '1') {
			Hex_0000_1000 = 4096 * 1;
		} else if (mChar[4] == '2') {
			Hex_0000_1000 = 4096 * 2;
		} else if (mChar[4] == '3') {
			Hex_0000_1000 = 4096 * 3;
		} else if (mChar[4] == '4') {
			Hex_0000_1000 = 4096 * 4;
		} else if (mChar[4] == '5') {
			Hex_0000_1000 = 4096 * 5;
		} else if (mChar[4] == '6') {
			Hex_0000_1000 = 4096 * 6;
		} else if (mChar[4] == '7') {
			Hex_0000_1000 = 4096 * 7;
		} else if (mChar[4] == '8') {
			Hex_0000_1000 = 4096 * 8;
		} else if (mChar[4] == '9') {
			Hex_0000_1000 = 4096 * 9;
		} else if (mChar[4] == 'A') {
			Hex_0000_1000 = 4096 * 10;
		} else if (mChar[4] == 'B') {
			Hex_0000_1000 = 4096 * 11;
		} else if (mChar[4] == 'C') {
			Hex_0000_1000 = 4096 * 12;
		} else if (mChar[4] == 'D') {
			Hex_0000_1000 = 4096 * 13;
		} else if (mChar[4] == 'E') {
			Hex_0000_1000 = 4096 * 14;
		} else if (mChar[4] == 'F') {
			Hex_0000_1000 = 4096 * 15;
		}
		if (mChar[5] == '0') {
			Hex_0000_0100 = 256 * 0;
		} else if (mChar[5] == '1') {
			Hex_0000_0100 = 256 * 1;
		} else if (mChar[5] == '2') {
			Hex_0000_0100 = 256 * 2;
		} else if (mChar[5] == '3') {
			Hex_0000_0100 = 256 * 3;
		} else if (mChar[5] == '4') {
			Hex_0000_0100 = 256 * 4;
		} else if (mChar[5] == '5') {
			Hex_0000_0100 = 256 * 5;
		} else if (mChar[5] == '6') {
			Hex_0000_0100 = 256 * 6;
		} else if (mChar[5] == '7') {
			Hex_0000_0100 = 256 * 7;
		} else if (mChar[5] == '8') {
			Hex_0000_0100 = 256 * 8;
		} else if (mChar[5] == '9') {
			Hex_0000_0100 = 256 * 9;
		} else if (mChar[5] == 'A') {
			Hex_0000_0100 = 256 * 10;
		} else if (mChar[5] == 'B') {
			Hex_0000_0100 = 256 * 11;
		} else if (mChar[5] == 'C') {
			Hex_0000_0100 = 256 * 12;
		} else if (mChar[5] == 'D') {
			Hex_0000_0100 = 256 * 13;
		} else if (mChar[5] == 'E') {
			Hex_0000_0100 = 256 * 14;
		} else if (mChar[5] == 'F') {
			Hex_0000_0100 = 256 * 15;
		}
		if (mChar[6] == '0') {
			Hex_0000_0010 = 16 * 0;
		} else if (mChar[6] == '1') {
			Hex_0000_0010 = 16 * 1;
		} else if (mChar[6] == '2') {
			Hex_0000_0010 = 16 * 2;
		} else if (mChar[6] == '3') {
			Hex_0000_0010 = 16 * 3;
		} else if (mChar[6] == '4') {
			Hex_0000_0010 = 16 * 4;
		} else if (mChar[6] == '5') {
			Hex_0000_0010 = 16 * 5;
		} else if (mChar[6] == '6') {
			Hex_0000_0010 = 16 * 6;
		} else if (mChar[6] == '7') {
			Hex_0000_0010 = 16 * 7;
		} else if (mChar[6] == '8') {
			Hex_0000_0010 = 16 * 8;
		} else if (mChar[6] == '9') {
			Hex_0000_0010 = 16 * 9;
		} else if (mChar[6] == 'A') {
			Hex_0000_0010 = 16 * 10;
		} else if (mChar[6] == 'B') {
			Hex_0000_0010 = 16 * 11;
		} else if (mChar[6] == 'C') {
			Hex_0000_0010 = 16 * 12;
		} else if (mChar[6] == 'D') {
			Hex_0000_0010 = 16 * 13;
		} else if (mChar[6] == 'E') {
			Hex_0000_0010 = 16 * 14;
		} else if (mChar[6] == 'F') {
			Hex_0000_0010 = 16 * 15;
		}
		if (mChar[7] == '0') {
			Hex_0000_0001 = 1 * 0;
		} else if (mChar[7] == '1') {
			Hex_0000_0001 = 1 * 1;
		} else if (mChar[7] == '2') {
			Hex_0000_0001 = 1 * 2;
		} else if (mChar[7] == '3') {
			Hex_0000_0001 = 1 * 3;
		} else if (mChar[7] == '4') {
			Hex_0000_0001 = 1 * 4;
		} else if (mChar[7] == '5') {
			Hex_0000_0001 = 1 * 5;
		} else if (mChar[7] == '6') {
			Hex_0000_0001 = 1 * 6;
		} else if (mChar[7] == '7') {
			Hex_0000_0001 = 1 * 7;
		} else if (mChar[7] == '8') {
			Hex_0000_0001 = 1 * 8;
		} else if (mChar[7] == '9') {
			Hex_0000_0001 = 1 * 9;
		} else if (mChar[7] == 'A') {
			Hex_0000_0001 = 1 * 10;
		} else if (mChar[7] == 'B') {
			Hex_0000_0001 = 1 * 11;
		} else if (mChar[7] == 'C') {
			Hex_0000_0001 = 1 * 12;
		} else if (mChar[7] == 'D') {
			Hex_0000_0001 = 1 * 13;
		} else if (mChar[7] == 'E') {
			Hex_0000_0001 = 1 * 14;
		} else if (mChar[7] == 'F') {
			Hex_0000_0001 = 1 * 15;
		}
		mDec = Hex_1000_0000 + Hex_0100_0000 + Hex_0010_0000 + Hex_0001_0000
				+ Hex_0000_1000 + Hex_0000_0100 + Hex_0000_0010 + Hex_0000_0001;
		return mDec;
	}

	public static double V_Gain(double inV) {
		double outV = 0;
		double vGain = 0.6447;
		outV = Math.round(inV * vGain * 10000) / 10000; // �Ҽ��� x �ڸ� ���� �ݿø�
		return outV;
	}

}
