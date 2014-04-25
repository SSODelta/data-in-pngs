package com.ignatieff.datainpng;

public class Pixel {
	public int RED, GREEN, BLUE;
	
	/**
	 * Creates a new PIXEL-object based off of an Integer representing the RGB values of a pixel.
	 * @param rgb The color channel as an Integer.
	 */
	public Pixel(int rgb){
		RED   = (rgb >> 16) & 0xFF;
		GREEN = (rgb >> 8)  & 0xFF;
		BLUE  = (rgb >> 0)  & 0xFF;
	}
	
	/**
	 * Set a specified bit of the red color channel of this pixel.
	 * @param index The index of the bit, where 0 = the least significant bit, and 7 = the most significant bit.
	 * @param state The state of the bit as a binary boolean value.
	 */
	public void setRedBit(int index, boolean state){
		if(index>7)return;
		RED = setBitOfInt(RED, index, state);
	}

	/**
	 * Set a specified bit of the green color channel of this pixel.
	 * @param index The index of the bit, where 0 = the least significant bit, and 7 = the most significant bit.
	 * @param state The state of the bit as a binary boolean value.
	 */
	public void setGreenBit(int index, boolean state){
		if(index>7)return;
		GREEN = setBitOfInt(GREEN, index, state);
	}

	/**
	 * Set a specified bit of the blue color channel of this pixel.
	 * @param index The index of the bit, where 0 = the least significant bit, and 7 = the most significant bit.
	 * @param state The state of the bit as a binary boolean value.
	 */
	public void setBlueBit(int index, boolean state){
		if(index>7)return;
		BLUE = setBitOfInt(BLUE, index, state);
	}
	
	/**
	 * Set a specified bit of a integer.
	 * @param integer The integer to manipulate.
	 * @param index The index of the bit, where 0 = the least significant bit, and 7 = the most significant bit.
	 * @param state The state of the bit as a binary boolean value.
	 * @return The manipulated integer.
	 */
	public static int setBitOfInt(int integer, int index, boolean state){
		if(state) return integer | (1 << index);
		return integer & ~(1 << index);
	}
	
	/**
	 * Get a specified bit from the red color channel.
	 * @param index The index of the bit, where 0 = the least significant bit, and 7 = the most significant bit.
	 * @return A boolean representing the bit of the integer, where true = 1, and false = 0.
	 */
	public boolean getRedBit(int index){
		return getBitOfInt(RED, index);
	}
	
	/**
	 * Get a specified bit from the green color channel.
	 * @param index The index of the bit, where 0 = the least significant bit, and 7 = the most significant bit.
	 * @return A boolean representing the bit of the integer, where true = 1, and false = 0.
	 */
	public boolean getGreenBit(int index){
		return getBitOfInt(GREEN, index);
	}
	
	/**
	 * Get a specified bit from the blue color channel.
	 * @param index The index of the bit, where 0 = the least significant bit, and 7 = the most significant bit.
	 * @return A boolean representing the bit of the integer, where true = 1, and false = 0.
	 */
	public boolean getBlueBit(int index){
		return getBitOfInt(BLUE, index);
	}
	
	/**
	 * Get a specified bit from an integer.
	 * @param integer The integer to read.
	 * @param index The index of the bit, where 0 = the least significant bit, and 7 = the most significant bit.
	 * @return A boolean representing the bit of the integer, where true = 1, and false = 0.
	 */
	public static boolean getBitOfInt(int integer, int index){
		return ((integer >> index) & 1) == 1;
	}
}
