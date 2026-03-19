package shamu.analysis;

public class NumericalSort {

      public static void sort(double[] reals, String identifier) {

             if(identifier.equals ("insertion"))
                NumericalSort.insertionSort(reals);

	  }

      public static void insertionSort(double[] reals) {

		     for(int j = 0; j < reals.length; j++) {

		 	       double min  = reals[j];

                   int index = j;

				   for(int k = j; k < reals.length; k++) {

		 	            double nextValue  = reals[k];

                        if(nextValue < min)  {

                            min = nextValue;
                            index = k;

					     }

				    }

                    reals[index] = reals[j];
                    reals[j] = min;

		    }

      }

  }