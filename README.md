# 🎰 Scratch Game - Java 17 CLI App

This project is a **console-based scratch card game**, developed using **Java 17**. It simulates a game where symbols are generated in a matrix, and based on your bet and config, it calculates rewards, applies bonus logic, and shows the final result in a clean JSON format.

---

## ✅ Requirements

- Java 17+
- Maven 3.x

---

## 🏗 Build Instructions

To build the app, go to project root and run:

```bash
mvn clean package
```

This will generate a `.jar` file inside the `target/` folder.

---

## ▶️ How to Run

Use the command below from terminal:

```bash
java -jar target/scratch-game-1.0-SNAPSHOT.jar --config config.json --betting-amount 100
```

Replace `100` with any amount you want to bet.

- `--config`: Path to the config JSON file  
- `--betting-amount`: Your betting amount (any positive number)

Make sure `config.json` is present in the root directory or provide full path.

---

## 📂 Sample Config (config.json)

A sample config file is already prepared, containing:

- Symbol definitions (standard and bonus)
- Symbol drop probabilities per cell
- Winning combinations (same symbol count or line matches)

You can customize symbol rewards, probabilities, or winning rules inside this JSON.

---

## 🧠 Game Logic

1. Load the configuration file
2. Generate a random matrix of symbols
3. Check for winning combinations (same symbol repeated or same row/column/diagonal)
4. Apply any bonus symbol if present
5. Print results as JSON

---

## 📄 Sample Output

```json
{
  "matrix" : [
    [ "F", "10x", "B" ],
    [ "B", "C", "D" ],
    [ "B", "F", "A" ]
  ],
  "reward" : 6000.0,
  "applied_winning_combinations" : {
    "B" : [ "same_symbol_3_times" ]
  },
  "applied_bonus_symbol" : "10x"
}
```

---

## 💡 Technologies Used

- Java 17 (with features like records, enhanced switch)
- Maven for build
- Jackson for JSON parsing
- Lombok for clean data models

---

## 📁 Project Structure

```text
.
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.dulaj.scratchgame/
│   │   │       ├── config/         # Game config classes
│   │   │       ├── engine/         # Core logic (runner, evaluator, generator)
│   │   │       ├── model/          # Enums, records (Cell, SymbolType etc.)
│   │   │       └── App.java        # Main entry point
│   └── test/                       # (Test cases - optional)
├── config.json                    # Sample config file
├── pom.xml                        # Maven build file
└── README.md                      # You're reading it!
```

---

## 🙌 Final Note

This project was developed by **Dulaj Kulathunga** for a coding test.  
I wrote this code myself without using any AI tools — just good old Java and a few cups of coffee.
Tried my best to keep the structure clean and make use of Java 17 features like records and the new switch syntax.

Feel free to run, review, and test it — and let me know if anything needs clarification!

---
