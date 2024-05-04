import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        convert(10000, 100000, "lichess_db_eval.jsonl", "output.txt");
    }

    static void convert(int statusUpdateIncrement, int amountLines, String inputFileName, String outputFileName) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Entry entry = objectMapper.readValue(new File(inputFileName), Entry.class);
            BufferedWriter out = new BufferedWriter(new FileWriter(outputFileName));
            int counter = 0;
            Board board;
            Board nextBoard;
            MoveList moveList;
            String move;
            int start;
            int end;

            while (entry != null) {
                counter++;
                if (counter % statusUpdateIncrement == 0) {
                    System.out.println(counter + " Lines Processed!");
                }
                if (counter > amountLines) {
                    break;
                }
                entry = objectMapper.readValue(new File("lichess_db_eval.jsonl"), Entry.class);
                board = new Board(entry.fen);
                moveList = MoveGeneration.getMoves(board);

                for (Eval eval : entry.evals) {
                    for (Position pv : eval.pvs) {
                        move = pv.line.split(" ")[0];
                        start = BitMethods.stringMoveToInt(move.substring(0, 2));
                        end = BitMethods.stringMoveToInt(move.substring(2, 4));

                        for (long longMove : moveList.moves) {
                            if (MoveList.getStartSquare(longMove) == start) {
                                if (MoveList.getEndSquare(longMove) == end) {
                                    nextBoard = new Board(board);
                                    nextBoard.makeMove(longMove);
                                    out.write(nextBoard.boardToFen() + ", " + pv.cp);
                                    out.newLine();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}