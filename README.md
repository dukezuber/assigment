# MSME Pipeline Scheduling Assignment

## Author

Zuber Khan

## Problem Description

This project solves a scheduling problem where tasks must be assigned to time slots under:

* Conflict constraints (no conflicting tasks in same slot)
* Resource constraints (CPU, RAM, GPU, Network)
* SLA time windows
* Penalty minimization

## Algorithm Used

**PRW-DSATUR (Priority Resource Window DSATUR)**

### Steps:

1. Sort tasks by priority, conflict degree, SLA window
2. Try all valid slots for each task
3. Check:

   * Conflict (F1)
   * Resource (F2)
   * SLA (F3)
4. Choose slot with minimum penalty
5. Apply local repair if needed

## NP-Hardness

The problem is NP-hard via reduction from **3-SAT**, incorporating:

* Conflict → logical consistency
* Resource → capacity constraint
* SLA → time restriction

## Benchmark Summary

| n   | K | Penalty | Runtime | Feasible |
| --- | - | ------- | ------- | -------- |
| 8   | 3 | 42.5    | 5ms     | Yes      |
| 200 | 5 | -       | 120ms   | No       |

##  How to Run

```bash
javac src/*.java
java src.Main
```

##  Structure

* src/ → Java implementation
* data/ → input JSON
* results/ → benchmark results
* tests/ → unit tests
* report/ → final PDF

##  Conclusion

A hybrid approach effectively balances feasibility and optimization in this NP-hard scheduling problem.
