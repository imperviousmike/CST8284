package cst8284.asgmt4.roomScheduler;

import java.util.Comparator;

/**
 * Comparator needed for various Collections functions. Sorting and searching
 * {@link cst8284.asgmt4.roomScheduler.RoomBooking} is necessary for optimizing
 * the many lookups needed for regular functionality.
 * {@link cst8284.asgmt4.roomScheduler.RoomBooking}s are to be sorted from
 * oldest dates to farthest future dates.
 * 
 * @author Michael Daly
 * @version 1.0
 * 
 */

public class SortRoomBookingsByCalendar implements Comparator<RoomBooking> {

	/**
	 * Compares two different start times of two different bookings to determine
	 * which one starts first. The overlap check is used specifically for checking
	 * for displaying a full days booking as it will match the first booking of a
	 * day that is stored. The {@code compareTo()} of a {@code Calendar} is used to
	 * determine all other cases.
	 * 
	 * @param r1 The booking we wish to use for comparison.
	 * @param r2 The booking we wish to compare against.
	 * 
	 * @return 0 if it is the exact same time frame, -1 if r1 is before r2 and 1 if
	 *         r1 is after r2.
	 * 
	 */

	@Override
	public int compare(RoomBooking r1, RoomBooking r2) {

		if (r1.getTimeBlock().overlaps(r2.getTimeBlock())) {
			return 0;
		}
		return r1.getTimeBlock().getStartTime().compareTo(r2.getTimeBlock().getStartTime());
	}

}
