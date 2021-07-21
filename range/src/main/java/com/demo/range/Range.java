/*
 * 
 This task can be resolved using 3 most common methods: 
 
 	- Brute Force Algorithm: it is a technique that guarantees solutions for problems 
 		of any domain helps in solving the simpler problems and also provides a solution 
 		that can serve as a benchmark for evaluating other design techniques, but takes
 		a lot of run time and inefficient. 
 		Time Complexity O(n^2), Space Complexity O(n).
 		 
 	- Connected Components: Create a graph whose nodes are connected with other interval 
 		overlapping nodes. This approach is complicated and it is far from being well optimized. 
 		Time Complexity O(n^2), Space Complexity O(n^2). 
 		
 	- Sorting of Intervals: (PREFERRED) Sort the intervals on the basis of start value in ascending order. 
 		The possible conditions to verify the merging of intervals could be — If the current interval 
 		begins after the previous interval ends, then they do not overlap and we can append the
 		current interval to merged. Otherwise, they do overlap, and we merge them by updating the 
 		end of the previous interval if it is less than the end of the current interval. 
 		Stack or List could be used for storing merged intervals (ranges). 
 		
 		In my solution I avoided O(n) extra space for the stack by merging ranges "on-a-fly".
 		So, the solution does not allocate additional space (List in my case) for the output/result. 
 		Time Complexity O(nLogn), Space Complexity O(1). 
 		
 As for a good practice in my solution I used: 
 	- Lombok to reduce boilerplate code for model/data objects, e.g., 
 		it can generate getters and setters for those object automatically by using Lombok annotations. 
 	- Stream forEach(Consumer action) for code readability and optimization.
 	
 */

package com.demo.range;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Range
{
	private int head;
	private int tail;

	public static void main(String[] args)
	{
		List<Range> rangesOne = new ArrayList<Range>();
		List<Range> rangesTwo = new ArrayList<Range>();
		List<Range> rangesThree = new ArrayList<Range>();
		// note, that ranges deliberately initialized 
		// in sporadic order
		rangesOne.add(new Range(94200, 94299));
		rangesOne.add(new Range(94133, 94133));
		rangesOne.add(new Range(94600, 94699));

		rangesTwo.add(new Range(94226, 94399));
		rangesTwo.add(new Range(94133, 94133));
		rangesTwo.add(new Range(94200, 94299));

		// more complicated case
		// added overlapped and non overlapped ranges
		// to the left and the right sides of the collections
		// to test
		rangesThree.add(new Range(94226, 94399));
		rangesThree.add(new Range(94133, 94133));
		rangesThree.add(new Range(94200, 94299));
		rangesThree.add(new Range(94250, 94450));
		rangesThree.add(new Range(94480, 94500));
		rangesThree.add(new Range(94490, 94600));
		rangesThree.add(new Range(93520, 93580));
		rangesThree.add(new Range(93590, 93600));
		rangesThree.add(new Range(93595, 93680));

		mergeRanges(rangesOne);
		mergeRanges(rangesTwo);
		mergeRanges(rangesThree);
		
		// using stream to print out List content
		// note, that the result is printed in ascending order
		rangesOne.forEach(n -> System.out.print(String.format("[%d,%d] ", n.head, n.tail)));
		System.out.println();
		rangesTwo.forEach(n -> System.out.print(String.format("[%d,%d] ", n.head, n.tail)));
		System.out.println();
		rangesThree.forEach(n -> System.out.print(String.format("[%d,%d] ", n.head, n.tail)));

		// output: 
		// [94133,94133] [94200,94299] [94600,94699]
		// [94133,94133] [94200,94399]
		// [93520,93580] [93590,93680] [94133,94133] [94200,94450] [94480,94600]
	}

	public static void mergeRanges(List<Range> ranges)
	{
		// sort ranges in ascending order
		Collections.sort(ranges, Comparator.comparingInt(n -> n.head));

		// store index of the last element
		int index = 0;

		// loop over the List and merge the ranges
		for (int i = 1; i < ranges.size(); i++)
		{
			// it's not a first range,
			// and it overlaps with the previous one
			if (ranges.get(index).tail >= ranges.get(i).head)
			{
				// current and previous ranges merged
				Range mergedRange = new Range(Math.min(ranges.get(index).head, ranges.get(i).head), Math.max(ranges.get(index).tail, ranges.get(i).tail));
				ranges.set(index, mergedRange);
			}
			else
			{
				ranges.set(++index, ranges.get(i));
			}
		}

		// get rid of remains of the merged ranges
		while (ranges.size() - 1 > index)
		{
			ranges.remove(ranges.size() - 1);
		}
	}
}
