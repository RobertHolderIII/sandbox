package sandbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * inspired by https://www.youtube.com/watch?v=XqFIvEoRnBs
 * @author holderh1
 *
 */

public class Berserk {


	public static void main(String[] args){
		findExpectedWins();
	}

	public static void findExpectedWins(){
		final int GAMES = 30;
		final int RUNS = 10000;
		Random rand = new Random();
		for (int winProbTimes100 = 30; winProbTimes100 <= 96; winProbTimes100+=2){
			double prob = winProbTimes100/100.0;
			int totalPoints = 0;
			int totalBerserkPoints = 0;
			
			for (int run = 0; run < RUNS; run++){
				int[] results = new int[GAMES];
				
				//populate results
				for (int i = 0; i < GAMES; i++){
					results[i] = rand.nextDouble()<prob ? 2 : 0;
				}
				//System.out.println("results : " + Arrays.toString(results));
				totalPoints += calculateScoreWithoutBerserk(results);
				totalBerserkPoints += calculateScoreWithBerserk(results);
			}
			System.out.println("\nexp points no berserk " + prob + " => " + (totalPoints/(double)RUNS));
			System.out.println("exp points w. berserk " + prob + " => " + (totalBerserkPoints/(double)RUNS));
		}
	}


	public static void compareWithAndWithoutBerserk(){

		int[] results0 = new int[]{0,3,2,0,
				2,3,4,0,
				2,2,0,2,
				0,3,3,0,
				3,2,0,3,
				2,5,0,3,
				3,0,2,2,
				5,0,3,0,
				2,3
		};
		int[] results1 = new int[]{0,2,0,3,0,3,3,5,5,0,3,0,3,2,5,5,5,5,5,5};

		int[] origResults = results1;

		ArrayList<Integer> zeros = new ArrayList<Integer>();
		for (int i = 0; i < origResults.length; i++){
			if (origResults[i] == 0){	
				zeros.add(i);
			}
		}

		int[] results = new int[origResults.length];
		for (int n = 0; n<=zeros.size(); n++){

			final int RUNS = 100000;
			int totaldiff = 0;
			for (int runs = 0; runs < RUNS; runs++){

				System.arraycopy(origResults, 0, results, 0, origResults.length);


				int origScore = calculateScoreWithBerserk(results);
				//System.out.println("orig: " + Arrays.toString(results));


				//randomly select N losses to be wins
				Collections.shuffle(zeros);
				for(int i = 0; i < n; i++){
					results[zeros.get(i)] = 2;
				}

				//calculate new score
				int totalScore = calculateScoreWithoutBerserk(results);

				//System.out.println("new : " + Arrays.toString(results));
				int diff = totalScore - origScore;
				//System.out.println("diff is " + diff);
				totaldiff += diff;
			}//end for each run
			System.out.println("additional " + n + " wins => avg " + String.format("%.2f",totaldiff/(double)RUNS) + " addl points");
		}//end for each
	}//end main

	public static int calculateScoreWithoutBerserk(int[] results){
		return calculateScore(results, false);
	}

	public static int calculateScore(int[] results, boolean berserk){
		int totalScore = 0;
		int streak = 0;
		for (int i = 0; i < results.length; i++){
			if (results[i] > 0){
				streak++;
				int mult = streak>2?2:1;
				results[i] = 2*mult+(berserk?1:0);
				totalScore += results[i];
			}
			else{
				streak = 0;
			}
		}
		//System.out.println(Arrays.toString(results) + " => " + totalScore + (berserk?" (berserk)":" (standard)"));
		return totalScore;
	}
	
	public static int calculateScoreWithBerserk(int[] results){
		return calculateScore(results, true);
	}
}
