# ChronoSphere // Digital Nexus v2.0

ChronoSphere is a sleek, modular desktop command dashboard built from scratch using **Java Swing**. It combines everyday utility tracking modules—like an operations scheduler, dynamic alerts, localized tools, and international time zone monitoring—into a single unified visual interface.

Instead of taking shortcuts with heavy external database systems or third-party graphic libraries, this project relies purely on raw **Object-Oriented Programming (OOP) logic**, modular backend data managers, and native Java File I/O operations.

---

## 🏗️ Project Architecture & Design Philosophy

The application is engineered using a decoupled structure inspired by the **Model-View-Controller (MVC)** pattern:
* **The View (`DashboardGUI.java`):** Handles the entire dark cyberpunk obsidian visual layout, custom button styling, input formatting, and window events.
* **The Models/Managers (Core backend files):** Completely isolated from the UI code. They manage the file manipulation, memory storage, logic computations, and data structures.

By separating the visual design from the engine logic, the application remains incredibly fast, easy to modify, and universally compatible with any machine running standard Java.

---

## 🎮 Deep-Dive Feature Breakdown

### 📊 1. System Operations Dashboard (Mainboard Overview)
This is the central cockpit of the application. 
* **The Engine:** It features a dedicated application timer that checks the local system time every 1,000 milliseconds, allowing the interface to display a beautiful live-updating clock and calendar on a background thread.
* **Dynamic Counters:** It links directly to your task log and alarm arrays to display live metrics showing your total active operational workloads at a glance.

### 🌐 2. Global Zone Monitor (World Clock Engine)
Tracks and converts time across multiple international target regions simultaneously.
* **The Engine:** Built using standard Java time frameworks (`ZonedDateTime` and `Map.Entry`), it takes your local system time, maps it against specific international time zone identifiers, and formats them side-by-side inside a clean text block window.

### 🕌 3. Chrono Prayer Watch (Localized Tracker)
Provides instantaneous lookup capabilities for major regional cities without requiring a slow internet connection.
* **The Engine:** Instead of querying external APIs, it utilizes an optimized static data lookup structure. When a user changes the city selection dropdown, the system immediately pulls the exact pre-mapped time schedules from memory and redraws the display vector without any lag.

### 📅 4. Central Task Matrix (Operation Scheduler)
A persistent schedule tracking log that keeps your entries safe even when the application is closed.
* **The Engine:** This module runs on raw Java File I/O (`BufferedReader` and `PrintWriter`). When you type a task, it appends it directly onto a flat-file database (`tasks.txt`). 
* **Data Manipulation:** It reads the text file into an array layout list to populate the UI. If a user selects a task and hits delete, the engine calculates the exact line index, removes it from the array memory, and perfectly overwrites the text file clean.

### ⏰ 5. Alarm Matrix System (Active Alerts)
A lightweight alert-logging system running inside the active runtime environment.
* **The Engine:** It registers target alert times directly into an active variables tracking array. It features manual intercept controls, giving users the power to highlight a specific active frequency slot and terminate it completely from system memory.

---

## 🛠️ Key Technical Concepts Applied

* **Multithreading & Timers:** Utilized asynchronous UI updating through background application ticks to handle the live dashboard clock without freezing the main user interface window.
* **Flat-File Databases:** Implemented persistent Stream I/O writers and readers to read and update structural configurations on local storage.
* **Event Handling:** Managed precise user interactions by writing explicit `ActionListener` inner classes to ensure structural logic stability across different Java compiler versions.
* **UI/UX Customization:** Hand-coded component styling, flat card backgrounds, custom input margins, and custom mouse-hover listeners to bypass generic operating system window limitations.

---

## 🚀 Installation & Running the Application

### Project Structure
Make sure all your files are unzipped directly into a flat folder directory on your machine like this:
```text
your-folder/
├── run.bat              (The automated launcher script)
├── Main.java            (Application entry point)
├── DashboardGUI.java    (Sleek UI window code)
├── TaskManager.java     (File handling logic)
├── AlarmManager.java    (Alert tracking logic)
├── PrayerTimeManager.java (Static data map lookup)
└── WorldClockManager.java (Time zone conversion logic)
