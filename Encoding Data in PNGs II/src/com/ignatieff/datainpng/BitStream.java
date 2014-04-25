package com.ignatieff.datainpng;

public class BitStream {
	
	//
	//Perceived luminance of the three color channels, based on the Photometric/digital ITU-R-approach.
	public static double VALUE_RED   = 0.2126,
						 VALUE_GREEN = 0.7152,
						 VALUE_BLUE  = 0.0722;
	
	public boolean[] BIT_STREAM;
	
	
	public int[] BITS_PER_COLOR_CHANNEL;
	
	/**
	 * Creates a new bitstream object.
	 * @param density The encoded number of bits per pixel. Max = 24.
	 */
	public BitStream(int density){
		//Initialize bitstream
		BIT_STREAM = new boolean[0];
		
		//Initialize color channel density-array.
		BITS_PER_COLOR_CHANNEL = new int[3];
		
		//Populate the array.
		for(int i=0; i<Math.min(density, 24); i++){
			addBitToChannel();
		}
	}
	
	/**
	 * Get the data encoded in this BitStream.
	 * @return A byte array representing the data encoded in this BitStream. Will return null if the number of bits isn't divisible by 8.
	 */
	public byte[] getData(){
		if(BIT_STREAM.length%8 != 0)return null;
		byte[] data = new byte[BIT_STREAM.length/8];
		for(int i=0; i<BIT_STREAM.length/8; i++){
			data[i] = getByteFromStream(i);
		}
		return data;
	}
	
	/**
	 * Return byte 'index' from the bit-stream.
	 * @param index The byte-index.
	 * @return The byte.
	 */
	public byte getByteFromStream(int index){
		return boolsToByte(subStream(8*index, 8));
	}

	/**
	 * Returns a substream of this BitStream.
	 * @param start The start index in the BitStream.
	 * @param len The length of the substream.
	 * @return A substream.
	 */
	public boolean[] subStream(int start, int len){
		
		boolean[] sub = new boolean[len];
		for(int i=0; i<len; i++){
			sub[i] = BIT_STREAM[start+i];
		}
		
		return sub;
	}
	
	/**
	 * Converts a boolean array of length 8 into a byte. It is assumed that the boolean array is a bit representation of the byte.
	 * @param bools The boolean array.
	 * @return The byte.
	 */
	public byte boolsToByte(boolean[] bools){
		if(bools.length != 8)return 0;
		byte b = 0;
		for(int i=0; i<bools.length; i++){
			if(bools[i]) b |= (1 << (7-i));
		}
		return b;
	}
	
	/**
	 * Read an array of pixels and add them to this BitStream based on the density parameter.
	 * @param p
	 */
	public void readPixels(Pixel[] p){
		for(int i=0; i<p.length; i++){
			readPixel(p[i]);
		}
	}
	
	/**
	 * Add a pixel to this BitStream based on this BitStreams density parameter.
	 * @param p The pixel to add.
	 */
	public void readPixel(Pixel p){
		for(int r=0; r<BITS_PER_COLOR_CHANNEL[0]; r++){
			addBit(p.getRedBit(r));
		}
		for(int g=0; g<BITS_PER_COLOR_CHANNEL[1]; g++){
			addBit(p.getGreenBit(g));
		}
		for(int b=0; b<BITS_PER_COLOR_CHANNEL[0]; b++){
			addBit(p.getRedBit(b));
		}
	}
	
	/**
	 * Add a single bit to this BitStream.
	 * @param bit
	 */
	public void addBit(boolean bit){
		boolean[] newBits = new boolean[BIT_STREAM.length + 1];
		for(int i=0; i<BIT_STREAM.length; i++){
			newBits[i] = BIT_STREAM[i];
		}
		newBits[BIT_STREAM.length] = bit;
		BIT_STREAM = newBits;
	}
	
	/**
	 * Adds a bit to the color channel where it has the least effect.
	 * This is based on the fact that the least significant bit has half the effect as the second least significant bit.
	 * Therefore we have three exponential functions W_r(bit), W_g(bit) and W_b(bit).
	 * The functions are of the form W * 2^bit, where 0 = least significant bit, and the weights are based off of the
	 * perceived luminance of each color channel, Photometric/digital ITU-R.
	 * 
	 * The color functions are:
	 * 
	 * W_r(bit) = 0.2126 * 2^bit
	 * W_g(bit) = 0.7152 * 2^bit
	 * W_b(bit) = 0.0722 * 2^bit
	 * 
	 * This function adds a bit to the color channel, which has the lowest function value.
	 */
	public void addBitToChannel(){
		double Wr = VALUE_RED   * Math.pow(2, BITS_PER_COLOR_CHANNEL[0]);
		double Wg = VALUE_GREEN * Math.pow(2, BITS_PER_COLOR_CHANNEL[1]);
		double Wb = VALUE_BLUE  * Math.pow(2, BITS_PER_COLOR_CHANNEL[2]);
		
		if(Wr < Wg && Wr < Wb){	BITS_PER_COLOR_CHANNEL[0]++; return; }
		if(Wg < Wr && Wr < Wb){	BITS_PER_COLOR_CHANNEL[1]++; return; }
		BITS_PER_COLOR_CHANNEL[2]++;
	}
}
