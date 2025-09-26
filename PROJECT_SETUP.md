# Hệ Thống Bán Vé - Ticket Management System

## Project Structure
```
HeThongBanVe/
├── src/
│   ├── ui/              # User Interface Layer
│   │   ├── Main.java    # Main Application Entry Point
│   │   ├── UserPanel.java
│   │   ├── SuKienPanel.java
│   │   └── VenuePanel.java
│   ├── data/            # Data Management Layer
│   │   └── DatabaseManager.java
│   ├── model/           # Data Model Classes
│   ├── connection/      # Database Connection Layer
│   └── dao/             # Data Access Objects
├── run.bat              # Windows Batch Script to Run
├── data.sql            # Sample Database Schema
└── README.md           # This File
```

## How to Run

### Method 1: Using Batch Script (Recommended)
```bash
.\run.bat
```

### Method 2: Manual Compilation and Execution
```bash
cd src
javac -cp . ui\*.java data\*.java model\*.java connection\*.java dao\*.java
java -cp . ui.Main
```

### Method 3: IDE Configuration
- **Main Class**: `ui.Main`
- **Working Directory**: `src/`
- **Classpath**: `src/`

## IDE Setup Instructions

### IntelliJ IDEA:
1. Open project folder as IntelliJ project
2. Set src/ as Source Root
3. Run Configuration:
   - Main class: `ui.Main`
   - Working directory: `<PROJECT_ROOT>/src`
   - Classpath: `<PROJECT_ROOT>/src`

### Eclipse:
1. Import as Java project
2. Set src/ as source folder
3. Run Configuration:
   - Main class: `ui.Main`
   - Working directory: `${workspace_loc:HeThongBanVe/src}`

### VS Code:
1. Install Java Extension Pack
2. Create .vscode/launch.json:
```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "Launch Main",
            "request": "launch",
            "mainClass": "ui.Main",
            "projectName": "HeThongBanVe",
            "cwd": "${workspaceFolder}/src",
            "classpath": ["${workspaceFolder}/src"]
        }
    ]
}
```

## Requirements
- Java JDK 8 or higher
- Windows OS (for run.bat)

## Features
- User Management (Quản lý Người dùng)
- Event Management (Quản lý Sự kiện)
- Venue Management (Quản lý Địa điểm)
- Modern Swing UI with Nimbus Look and Feel