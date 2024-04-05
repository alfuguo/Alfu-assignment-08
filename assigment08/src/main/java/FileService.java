import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class FileService {
    ExecutorService executor = Executors.newCachedThreadPool();
    Assignment8 assignment = new Assignment8();

    public List<Integer> getAllNumbers() {
        CompletableFuture<List<Integer>> future = CompletableFuture.supplyAsync(assignment::getNumbers, executor);
        List<Integer> allNumbers = future.join();
        executor.shutdown();
        return allNumbers;
    }

    public void countAndPrintoutNumbers(List<Integer> allNumbers) {
        Map<Integer, Long> countMap = allNumbers.stream()
                .collect(Collectors.groupingByConcurrent(
                        uniqueNumber -> uniqueNumber, Collectors.counting()));

        String output = countMap.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(", "));

        System.out.println(output);
    }


}