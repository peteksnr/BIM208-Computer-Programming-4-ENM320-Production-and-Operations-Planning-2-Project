# BIM208-Computer-Programming-4-ENM320-Production-and-Operations-Planning-2
# Optimal Lot Size and Reorder Point Calculator

## Overview
This project aims to develop a software application that determines the optimal lot size (𝑄) and reorder point (𝑅) using user-provided cost and demand data. The software prompts the user to input key parameters such as unit cost, ordering cost, penalty cost, interest rate, lead time, lead time demand, and lead time standard deviation. Utilizing these inputs, it calculates essential decision variables and performance measures, including holding costs, safety stock, and average costs. By leveraging the Z-Chart and Loss Function values, the software iteratively identifies the optimal solution, delivering precise and efficient inventory management recommendations.


## Features
- **Input Collection:** Prompts the user to enter key parameters, including unit cost, ordering cost, penalty cost, interest rate, lead time, lead time demand, and lead time standard deviation.
- **Annual Demand Calculation:** Calculates the annual demand using the provided lead time and demand values.
- **Z-Chart and Loss Function Integration:** Automatically retrieves normal distribution and loss function values during the iterative process.
- **Output Decision Variables and Performance Measures:** Generates and displays critical decision variables and performance metrics, such as optimal lot size, reorder point, safety stock, and various cost measures.

  - Optimal lot size (𝑄)
  - Reorder point (𝑅)
  - Number of iterations
  - Safety stock
  - Average annual holding, setup, and penalty costs
  - Average time between order placements
  - Proportion of order cycles with no stockout
  - Proportion of unmet demands

## Installation and Usage

### Prerequisites
- Java Development Kit (JDK) installed on your system
- A Java IDE (e.g., IntelliJ IDEA, VSCode) or a text editor
- Command-line interface (CLI) for running Java programs

### Instructions
1. **Clone the Repository:**
    ```bash
    git clone https://github.com/peteksnr/BIM208-Computer-Programming-4-ENM320-Production-and-Operations-Planning-2-Project.git
    ```

2.  **Compile the Java Program:**
    ```bash
    javac -cp src/main/java -d out src/main/java/org/example/Main.java
    ```

3. **Run the Java Program:**
    ```bash
    java -cp out org.example.Main
    ```



4. **Input Values:**
    The program will prompt you to enter the following values:
    - Unit cost (c)
    - Interest rate (i)
    - Penalty cost (p)
    - Ordering cost (K)
    - Lead time (T)
    - Mean demand (mean)
    - Standard deviation of demand (dev)

5. **View Results:**
    The program will output the optimal order quantity, reorder point, safety stock, and other performance measures.

### Example
**Input:**
```bash
Enter unit cost (c): 20
Enter interest rate (i): 0.25
Enter penalty cost (p): 20
Enter ordering cost (K): 100
Enter lead time (T): 4
Enter mean demand (mean): 500
Enter standard deviation of demand (dev): 100
```



**Output:**
```bash
Optimal lot size (Q): 287.75
Reorder point (R): 668.0
Number of iterations: 3
Safety stock: 168.0
Average annual holding cost: 1559.375
Average annual setup cost: 521.286
Average annual penalty cost: 198.089
Average time between order placements: 2.302 months
Proportion of order cycles with no stockout: 95.352%
Proportion of unmet demands: 0.0465
```


## File Structure
- `src/main/java/org/example/Main.java`: The main Java file containing the logic for the calculations.
- `src/main/java/org/example/zchart.csv`: The CSV file with Z-Chart and Loss Function values used in the iterative process.

## License
This project is licensed under the MIT License - see the LICENSE file for details.
