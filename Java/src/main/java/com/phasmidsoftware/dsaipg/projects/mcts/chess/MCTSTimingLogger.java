package com.phasmidsoftware.dsaipg.projects.mcts.chess;

import com.phasmidsoftware.dsaipg.projects.mcts.core.State;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MCTSTimingLogger {
	public static void main(String[] args) {
        int[] simulationsList = {10, 100, 500, 1000, 2000, 5000};
        int repetitions = 10; 

        List<String> rows = new ArrayList<>();
        rows.add("Simulations,AverageTime(μs)");

        for (int simulations : simulationsList) {
            long totalTimeNs = 0;

            for (int i = 0; i < repetitions; i++) {
                State<ChessGame> state = new ChessGame().start();
                MCTSPlayer ai = new MCTSPlayer(simulations);

                long start = System.nanoTime();
                ai.selectMove((ChessState) state);
                long end = System.nanoTime();

                totalTimeNs += (end - start);
            }

            long averageTimeMicro = totalTimeNs / repetitions / 1000; 
            System.out.printf("Simulations = %-5d → Avg Time = %d μs%n", simulations, averageTimeMicro);
            rows.add(simulations + "," + averageTimeMicro);
        }

        writeToCSV("timing_output_high_precision.csv", rows);
    }

    private static void writeToCSV(String filename, List<String> rows) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (String row : rows) {
                writer.write(row + "\n");
            }
            System.out.println("✅ High precision timing saved to: " + filename);
        } catch (IOException e) {
            System.err.println("❌ Error writing file: " + e.getMessage());
        }
    }
}