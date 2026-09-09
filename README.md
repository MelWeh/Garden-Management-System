# Garden-Management-System
A JavaFX application that manages a virtual garden bed. Users can plant, remove, and track what vegetables they want in a grid-based layout.


## Features

- Interactive Grid Garden: Visual grid where vegetables can be planted or removed
- Vegetable Data: Loads vegetable data from a file which can edited to include more vegetables
- Undo/Redo: Undo functionality that uses Stack
- Action History: Action tracking using Queue
- File I/0: Saves garden layouts in a file
- Recursive Counting: Counts the amount of vegetables in the garden using recursion


## Usage

1. Click "Load Vegetables" to load vegetable list
2. Enter the dimensions for the garden grid and click "Create Garden"
3. Select vegetable of choice from right panel vegetable list.
4. Click grid cell to plant vegetable
5. Click "Undo" at top to reverse actions
6. Click "Save Garden" to export garden layout to CSV

## File Formatting

- Input: "files/7a_veg_info.txt" (comma separated data file)
- Output: "files/garden_save.csv" (garden layout)
