package com.chenganrt.smartSupervision.widget.wheel;

/**   
 * [ Wheel changed listener interface.]
 * The onChanged() method is called whenever current wheel positions is changed:
 * New Wheel position is set; Wheel view is scrolled
 * 
 * @author: devin.hu
 * @version: 1.0
 * @date:   Oct 17, 2013    
 */
public interface OnWheelChangedListener {
	
	/**
	 * Callback method to be invoked when current item changed
	 * 
	 * @param wheel
	 *            the wheel view whose state has changed
	 * @param oldValue
	 *            the old value of current item
	 * @param newValue
	 *            the new value of current item
	 */
	void onChanged(WheelView wheel, int oldValue, int newValue);
}