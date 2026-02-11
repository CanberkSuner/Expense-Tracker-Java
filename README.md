# 💰 Expense Tracker System (Java & OOP)

**A robust Expense Tracking System developed using Java Swing and Advanced Object-Oriented Programming principles.**

This project demonstrates a comprehensive software engineering approach to personal finance management. It features a dual-interface design (GUI & Console) and utilizes a polymorphic architecture to handle various expense categories dynamically.

---

## 📖 Project Overview & Goal

The primary goal of this project is to implement a scalable and maintainable software solution for tracking personal finances. It addresses the need for real-time budget monitoring and categorized expense logging.

We designed a complete system using:
* **Java SE (JDK 17+)**
* **Java Swing** (for the Graphical User Interface)
* **OOP Principles** (Inheritance, Polymorphism, Abstraction)
* **Event-Driven Programming** (Action Listeners & Dynamic Updates)

---

## ⚙️ System Architecture & Engineering Decisions

### 1. Polymorphic Data Structure
The core of our design is the use of **Polymorphism** to manage data. Instead of creating separate lists for each category, the system uses a single abstract reference list.
* **Implementation:** `List<Expense> expenses`
* **Why Polymorphism?** This allows the system to treat `FoodExpense`, `TransportExpense`, and `BillExpense` objects uniformly at runtime. It significantly **reduces code duplication** and makes adding new categories (e.g., "HealthExpense") effortless without breaking existing code.

### 2. Abstract Base Architecture
We defined a strict contract using an **Abstract Base Class (`Expense`)**.
* It enforces that every subclass *must* implement the `getCategory()` method.
* This ensures data consistency across the entire application, preventing "undefined" expense types.

### 3. Expense Logic & Categorization Table
The system categorizes inputs based on specific subclasses. Here is the logic table:

| Class Name | Category Tag | Description Logic | Usage Example |
| :--- | :--- | :--- | :--- |
| **FoodExpense** | `Food` | Daily consumables | Lunch, Groceries, Dining out |
| **TransportExpense** | `Transport` | Commuting costs | Bus Ticket, Gas, Taxi |
| **BillExpense** | `Bill` | Recurring payments | Electricity, Water, Internet |

### 4. Dynamic GUI Rendering (Swing)
Instead of a static form, the **GUI (MainFrame)** uses a dynamic update mechanism.
* **Observer-like Pattern:** When a user adds an expense, the `User` object updates the underlying data model, and the `JTabbedPane` and `Header Panel` (Budget Status) repaint themselves instantly to reflect the new "Remaining Budget".

---

## 🔋 Operational Modes

The system supports two distinct modes of operation to demonstrate flexibility:

| Mode | Interface Type | Description |
| :--- | :--- | :--- |
| **Desktop Mode** | **GUI (Swing)** | Full graphical experience with forms, drop-downs, and visual dashboards. Best for end-users. |
| **Headless Mode** | **Console/Terminal** | Text-based interface running in the command line. Demonstrates the raw backend logic and data processing speed. |

---

## 📸 Interface Demonstration

### 1. User Initialization
The entry point where the user establishes their financial baseline.
<br>
<img src="Images/login.png" width="450" alt="User Login Screen">

### 2. Dashboard & Data Entry
The main control center. The top panel updates the budget in real-time, while the form validates inputs (preventing negative numbers).
<br>
<p float="left">
  <img src="Images/dashboard.png" width="500" alt="Dashboard View">
</p>

### 3. Backend Logic (Console Output)
Proof of the robust backend calculation and polymorphic `toString()` behavior.
<br>
<img src="Images/console.png" width="600" alt="Console Output">

---

## 🔌 Class Diagram & Logic Mapping

The project follows a hierarchical class structure:

* **Main Control:** `MainFrame.java` (GUI) / `ExpenseTracker.java` (Console)
* **Data Model:** `User.java` (Holds `List<Expense>` and `budget`)
* **Abstract Layer:** `Expense.java` (Base fields: amount, date, description)
* **Concrete Layers:** `FoodExpense`, `TransportExpense`, `BillExpense`

---

## 👤 Developer

This project was developed to demonstrate proficiency in **Java Software Development** and **Object-Oriented Design Patterns**.

* **Canberk** (Software Architecture & Implementation)

---
