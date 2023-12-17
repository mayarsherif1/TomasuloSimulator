# Tomasulo Simulator

## Overview
This Tomasulo Algorithm Simulator is a Java-based tool designed to simulate the execution of MIPS instructions using the Tomasulo algorithm. The simulator takes input from a text file containing MIPS instructions in assembly format and provides detailed output for each cycle. Additionally, it features a Graphical User Interface (GUI) built with Swift Window Builder to facilitate user interaction.

## Features

### Input Handling
- The simulator accepts input from a text file, allowing users to test different sequences of MIPS instructions easily.

### Graphical User Interface (GUI)
- A user-friendly GUI built with Swift Window Builder enables users to input parameters such as add/multiply latency and the number of reservation stations/buffers.

### Cycle-by-Cycle Output
- Detailed information is provided for each cycle, including:
  - Instructions table
  - Reservation stations/buffers
  - Register file status
  - Cache state
  - Instruction queue

### Hazard Handling
- The simulator effectively manages hazards such as Read-After-Write (RAW), Write-After-Read (WAR), and Write-After-Write (WAW) to ensure correct instruction execution.

### Bus Publication Strategy
- In cases where two instructions attempt to publish results on the bus simultaneously, the simulator employs a First-In-First-Out (FIFO) technique to prevent conflicts.

## Usage

### Running the Simulator
1. Compile and run the `Simulator` class.
2. provide the path to the text file containing MIPS instructions.

### GUI Interface
1. Launch the simulator with the GUI by running the `TomasuloApp` class.
2. Use the GUI to input latency and reservation stations/buffers parameters.
3. Load MIPS instructions from the file using the GUI.

## Future Enhancements

- Expand simulator support for additional MIPS instructions.
- Implement advanced optimization techniques.
- Collect and consider user feedback for GUI improvements and overall user experience.

## Contributors

- [Mayar Sherif]

