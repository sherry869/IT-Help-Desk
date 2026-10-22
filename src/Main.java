
import java.util.*;

class Complaint {
    int id;
    String name;
    String issue;
    String priority;

    Complaint(int id, String name, String issue, String priority) {
        this.id = id;
        this.name = name;
        this.issue = issue;
        this.priority = priority;
    }

    public String toString() {
        return "ID: " + id +
               " | Name: " + name +
               " | Issue: " + issue +
               " | Priority: " + priority;
    }
}

public class Main {

    static Queue<Complaint> normalQueue = new LinkedList<>();

    static PriorityQueue<Complaint> priorityQueue =
        new PriorityQueue<>((a, b) -> {
            int p1 = getPriorityValue(a.priority);
            int p2 = getPriorityValue(b.priority);
            return p2 - p1;
        });

    static LinkedList<Complaint> resolvedHistory = new LinkedList<>();

    static int nextId = 101;

    static int getPriorityValue(String priority) {
        if (priority.equalsIgnoreCase("Critical")) return 3;
        if (priority.equalsIgnoreCase("High")) return 2;
        return 1;
    }

    static void addComplaint(String name, String issue, String priority) {
        Complaint c = new Complaint(nextId++, name, issue, priority);

        if (priority.equalsIgnoreCase("Normal")) {
            normalQueue.add(c);
        } else {
            priorityQueue.add(c);
        }

        System.out.println("Complaint added successfully!");
        System.out.println(c);
    }

    static void processNextComplaint() {
        Complaint c;

        if (!priorityQueue.isEmpty()) {
            c = priorityQueue.poll();
        } else if (!normalQueue.isEmpty()) {
            c = normalQueue.poll();
        } else {
            System.out.println("No pending complaints.");
            return;
        }

        resolvedHistory.add(c);
        System.out.println("Complaint resolved:");
        System.out.println(c);
    }

    static void showPendingComplaints() {
        System.out.println("\n--- Priority Complaints ---");
        PriorityQueue<Complaint> copy = new PriorityQueue<>(priorityQueue);

        while (!copy.isEmpty()) {
            System.out.println(copy.poll());
        }

        System.out.println("\n--- Normal Complaints ---");
        for (Complaint c : normalQueue) {
            System.out.println(c);
        }
    }

    static void showResolvedHistory() {
        System.out.println("\n--- Resolved History ---");

        if (resolvedHistory.isEmpty()) {
            System.out.println("No resolved complaints yet.");
            return;
        }

        for (Complaint c : resolvedHistory) {
            System.out.println(c);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== IT HELP DESK SYSTEM =====");
            System.out.println("1. Add Complaint");
            System.out.println("2. Process Next Complaint");
            System.out.println("3. Show Pending Complaints");
            System.out.println("4. Show Resolved History");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Enter a number from 1 to 5: ");
                sc.next();
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter your name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter issue: ");
                    String issue = sc.nextLine();

                    System.out.print("Priority (Normal/High/Critical): ");
                    String priority = sc.nextLine().trim();

                    if (!priority.equalsIgnoreCase("Normal")
                            && !priority.equalsIgnoreCase("High")
                            && !priority.equalsIgnoreCase("Critical")) {
                        System.out.println("Invalid priority. Try again.");
                        break;
                    }

                    if (name.trim().isEmpty() || issue.trim().isEmpty()) {
                        System.out.println("Name and issue cannot be empty.");
                        break;
                    }

                    addComplaint(name.trim(), issue.trim(), priority);
                    break;

                case 2:
                    processNextComplaint();
                    break;

                case 3:
                    showPendingComplaints();
                    break;

                case 4:
                    showResolvedHistory();
                    break;

                case 5:
                    System.out.println("Exiting Help Desk System. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Enter 1 to 5.");
            }

        } while (choice != 5);

        sc.close();
    }
}
