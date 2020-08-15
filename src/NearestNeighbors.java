import java.io.FileReader;
import java.util.Scanner;
import java.io.IOException;


public class NearestNeighbors {

	private static int    numberOfEntries = 75;
	private static int    numberOfMeasurements = 4;
	private static double trainingData[][];
	private static String trainingClassifications[];
	private static double testingData[][];
	private static String testingClassifications[];
	
	public NearestNeighbors() {
		
		trainingData = new double[numberOfEntries][numberOfMeasurements]; //contains all measurements for the training data
		trainingClassifications = new String[numberOfEntries];			  //contains the classifications for the training data
		testingData = new double[numberOfEntries][numberOfMeasurements];  //contains all measurements for the testing data
		testingClassifications = new String[numberOfEntries];			  //contains the classifications for the testing data so we can check our accuracy
	}
	
	public static void main(String[] args) throws IOException {
		//Prints out heading
		System.out.println("Programming Fundamentals");
		System.out.println("Name: Gabby Ryan");
		System.out.println("PROGRAMMING ASSIGNMENT 3");
		System.out.println(" ");
		
		Scanner scanner = new Scanner(System.in);
		NearestNeighbors cnnImpl = new NearestNeighbors();

		
		System.out.print("Enter the path for the training data: ");
		String 	  irisPath = scanner.nextLine();
		//getIrisData will parse the file and store data to the correct arrays
		cnnImpl.getIrisData(irisPath, trainingData, trainingClassifications);
		System.out.print("Enter the path for the testing data: ");
		irisPath = scanner.nextLine();
		cnnImpl.getIrisData(irisPath, testingData, testingClassifications);
		scanner.close();
		System.out.println(" ");
		System.out.println("EX#:  TRUE LABEL, PREDICTED LABEL");
		
		double classifyAcc = cnnImpl.calculateClassificationAcc();
		// print the classification accuracy
		System.out.println("ACCURACY: " + classifyAcc);
	}
	
	private double calculateClassificationAcc()  
	{
		double minDist = 999999;
		double correctClassification = 0;
		int closestSample = 0;
	
		// for each test entry
		for (int y = 0; y < numberOfEntries; y++)
		{
			minDist = 999999;
			//calculate the closest training example and store the index in the closestSample variable
			for (int x = 0; x < numberOfEntries; x++)
			{
				// index into the test and training arrays to get the measurements to calculate distance
				double calcDistance = distance(trainingData[x][0], trainingData[x][1], trainingData[x][2], trainingData[x][3], 
												testingData[y][0], testingData[y][1], testingData[y][2], testingData[y][3]);
				// check if our new calculated distance is closer than our current closestSample
				if(calcDistance < minDist)
				{
					minDist = calcDistance;
					closestSample = x;
				}
			}
			
			// Check our testClassifications for entry y against our traininClassification of our closestSample
			if (testingClassifications[y].equals(trainingClassifications[closestSample]))
			{
				// if they match we increment correctClassification
				correctClassification++;
			}

			// print our entry number (index + 1) this creates the test label and predicted label
			System.out.println( y + 1 + ": " + testingClassifications[y] + " " + trainingClassifications[closestSample]);
		}
		
		// calculates accuracy
		double classAccuracy  = correctClassification/numberOfEntries;
		return classAccuracy;
	}
	
	public void getIrisData(String irisFilePath, double data[][], String classification[]) throws IOException
	{
		Scanner irisDataReader = new Scanner(new FileReader(irisFilePath));
		int i = 0;

		// for all the data in the input file
		while(irisDataReader.hasNextLine())
		{
			String input = irisDataReader.nextLine();
			String[] attr = null;
					
			if (input != null)
			{
				attr = ((String) input).split(",");
			}
					
			if (attr != null && attr.length == 5)
			{
				// parse all measurements as doubles into our data array
				data[i][0] = Double.parseDouble(attr[0]);
				data[i][1] = Double.parseDouble(attr[1]);
				data[i][2] = Double.parseDouble(attr[2]);
				data[i][3] = Double.parseDouble(attr[3]);
				// add the classification to the classification array
				classification[i] = attr[4];
				i++;
			}
		}
	}
	
	public double distance(double slx, double swx, double plx, double pwx, double sly, double swy, double ply, double pwy)
	{
		double d1 = Math.pow((slx - sly), 2);
		double d2 = Math.pow((swx - swy), 2);
		double d3 = Math.pow((plx - ply), 2);
		double d4 = Math.pow((pwx - pwy),2);
		return Math.sqrt(d1 + d2 + d3 + d4);
	}
}