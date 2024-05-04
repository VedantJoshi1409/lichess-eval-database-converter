# lichess eval database converter

Converts data from https://database.lichess.org/#evals into a txt where each line is in the following format:\
fen, eval\
It does this by making the first move in the principle variation and outputting that fen, with the centipawn value.\
(Move making and fen handling ripped from my chess engine)

## Example
```
{
  "fen": "2bq1rk1/pr3ppn/1p2p3/7P/2pP1B1P/2P5/PPQ2PB1/R3R1K1 w - -",
  "evals": [
    {
      "pvs": [
        {
          "cp": 311,
          "line": "g2e4 f7f5 e4b7 c8b7 f2f3 b7f3 e1e6 d8h4 c2h2 h4g4"
        }
      ],
      "knodes": 206765,
      "depth": 36
    },
    {
      "pvs": [
        {
          "cp": 292,
          "line": "g2e4 f7f5 e4b7 c8b7 f2f3 b7f3 e1e6 d8h4 c2h2 h4g4"
        },
        {
          "cp": 277,
          "line": "f4g3 f7f5 e1e5 d8f6 a1e1 b7f7 g2c6 f8d8 d4d5 e6d5"
        }
      ],
      "knodes": 92958,
      "depth": 34
    },
    {
      "pvs": [
        {
          "cp": 190,
          "line": "h5h6 d8h4 h6g7 f8d8 f4g3 h4g4 c2e4 g4e4 g2e4 g8g7"
        },
        {
          "cp": 186,
          "line": "g2e4 f7f5 e4b7 c8b7 f2f3 b7f3 e1e6 d8h4 c2h2 h4g4"
        },
        {
          "cp": 176,
          "line": "f4g3 f7f5 e1e5 f5f4 g2e4 h7f6 e4b7 c8b7 g3f4 f6g4"
        }
      ],
      "knodes": 162122,
      "depth": 31
    }
  ]
}
```
## Converts to

2bq1rk1/pr3ppn/1p2p3/7P/2pPBB1P/2P5/PPQ2P2/R3R1K1 b - -, 311\
2bq1rk1/pr3ppn/1p2p3/7P/2pPBB1P/2P5/PPQ2P2/R3R1K1 b - -, 292\
2bq1rk1/pr3ppn/1p2p3/7P/2pP3P/2P3B1/PPQ2PB1/R3R1K1 b - -, 277\
2bq1rk1/pr3ppn/1p2p2P/8/2pP1B1P/2P5/PPQ2PB1/R3R1K1 b - -, 190\
2bq1rk1/pr3ppn/1p2p3/7P/2pPBB1P/2P5/PPQ2P2/R3R1K1 b - -, 186\
2bq1rk1/pr3ppn/1p2p3/7P/2pP3P/2P3B1/PPQ2PB1/R3R1K1 b - -, 176

## Requirements
Jackson Core - https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core\
Jackson Databind - https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind\
Jackson Annotations - https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-annotations\

## Usage
- Decompress the .zst file into a .jsonl, PeaZip is recommended.
- Fill in convert() method params in Main.java, and run it.
- The default is: ```convert(10000, 100000, "lichess_db_eval.jsonl", "output.txt");```
