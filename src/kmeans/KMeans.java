package kmeans;

import java.util.*;

/**
 * Created by OP on 18-12-14.
 */
public class KMeans implements ClusteringAlg {

    public List<Cluster> cluster(List<DataPoint> points, int k) throws IllegalArgumentException {
        List<Cluster> clusters = new ArrayList<Cluster>();

        // validate input
        if (points.size() < k) return clusters;
        if (k < 1) throw new IllegalArgumentException();

        // generate k clusters
        int dim = points.get(0).getDim();
        int pointNum = points.size();
        double[] centroids = new double[k];
        ArrayDeque<Integer> selectedPoints = new ArrayDeque<Integer>();
        Random random = new Random();

        for (int clusterIdx = 0; clusterIdx < k; clusterIdx++) {
            clusters.add(new Cluster(clusterIdx, dim)); // Add cluster
            // Sample a point (without repetition) to serve as the seed
            Integer selectedPoint = random.nextInt(pointNum);
            while (selectedPoints.contains(selectedPoint)) {
                selectedPoint = random.nextInt(pointNum);
            }
            selectedPoints.add(selectedPoint);
            // insert seed into cluster and refresh centroid
            clusters.get(clusterIdx).addPoint(points.get(selectedPoints.peekLast()));
            clusters.get(clusterIdx).refreshCentroid();
        }

        // assign points to the clusters
        for (int pointIdx = k; pointIdx < pointNum; pointIdx++) {
            closestCluster(clusters, points.get(pointIdx)).addPoint(points.get(pointIdx));
        }

        /** While loop (limited by max steps and checks for change)
         ->check each point if it has a closer cluster
         --> if it does, then move it to that cluster */
        boolean changeOccurred = true;
        int steps = 0;

        while (changeOccurred && steps < 400) {
            // update stopping conditions
            steps++;
            changeOccurred = false;

            // refresh centroids
            for (Cluster c : clusters) c.refreshCentroid();

            // check point assignment for each cluster
            for (Cluster c : clusters) {
                List<Cluster> targetClusters = new ArrayList<Cluster>();
                List<Integer> pointIDs = new ArrayList<Integer>();
                for (DataPoint p : c.getPoints()) {
                    // should we move this point to a different cluster?
                    Cluster closestCluster = closestCluster(clusters, p);
                    if (!closestCluster.equals(c)) {
                        // if so, store target cluster and current point id
                        targetClusters.add(closestCluster);
                        pointIDs.add(p.getId());
                    }
                }
                // move points
                if (pointIDs.size() > 0) {
                    changeOccurred = true;
                    int pIdx = 0;
                    for (Integer currID : pointIDs) {
                        // make sure we don't empty the cluster
                        if (c.getPointNum() > 1) {
                            // copy point to target cluster
                            targetClusters.get(pIdx).addPoint(c.getPoint(currID));
                            // remove point from source cluster
                            c.removePoint(currID);
                        }
                        pIdx++;
                    }
                }
            }
        }

        return clusters;
    }

    private Cluster closestCluster(List<Cluster> clusters, DataPoint point) {
        DistanceCalculator dist = new DistanceCalculatorEuclidean();
        Cluster minimumCluster = clusters.get(0);
        double minimumDistance = dist.calculate(minimumCluster.getCentroid(), point);
        for (Cluster currCluster : clusters) {
            double currDistance = dist.calculate(currCluster.getCentroid(), point);
            if (currDistance < minimumDistance) {
                minimumCluster = currCluster;
                minimumDistance = currDistance;
            }
        }
        return minimumCluster;
    }

    public static void main(String[] args) {

        //generate data
        Random random = new Random();
        List<DataPoint> dataPoints = new ArrayList<DataPoint>();

        for (int i = 0; i < 100000; i++) //Men
        {
            List<Double> doubles = new ArrayList<Double>();
            doubles.add(random.nextGaussian() * 10 + 180); // Height
            doubles.add(random.nextGaussian() * 7 + 80);   //Weight
            dataPoints.add(new DataPoint(i, doubles));
        }
        for (int i = 100000; i < 200000; i++) //Women
        {
            List<Double> doubles = new ArrayList<Double>();
            doubles.add(random.nextGaussian() * 8 + 150); // Height
            doubles.add(random.nextGaussian() * 5 + 40);  //Weight
            dataPoints.add(new DataPoint(i, doubles));
        }
        for (int i = 200000; i < 300000; i++) //Trees?
        {
            List<Double> doubles = new ArrayList<Double>();
            doubles.add(random.nextGaussian() * 15 + 250); // Height
            doubles.add(random.nextGaussian() * 20 + 900); //Weight
            dataPoints.add(new DataPoint(i, doubles));
        }
        // perform k-means clustering
        ClusteringAlg clusteringAlg = new KMeans();
        long startTime = System.nanoTime();
        List<Cluster> clusters = clusteringAlg.cluster(dataPoints, 3);
        long endTime = System.nanoTime();

        // display results
        for (Cluster c : clusters) System.out.println(c);
        System.out.println("Time(ms): " +(endTime - startTime)/1000000);

    }
}
