// Name: Dilara
// Surname: TOSUN
// Student ID: 20050111041

import java.io.*;
class Earthquake {
    private String location;
    private String date; // DD.MM.YYYY
    private String time; // HH:MM:SS
    private double magnitude;
    private double depth;
    public Earthquake next;
    public Earthquake prev;

    public Earthquake(String newLocation, String newDate, String newTime, double newMagnitude, double newDepth) {
        this.location = newLocation;
        this.date = newDate;
        this.time = newTime;
        this.magnitude = newMagnitude;
        this.depth = newDepth;
        prev=null;
        next=null;
    }

    // GETTERS
    public double getMagnitude() {return this.magnitude;}
    public double getDepth() {return this.depth;}
    public String getDate() {return this.date;}
    public String getLocation() {return this.location;}
    public String getTime() {return this.time;}
    public Earthquake getNext() {return this.next;}
    public Earthquake getPrev() {return this.prev;}

    //SETTERS
    public void setNext(Earthquake next) {this.next = next;}
    public void setPrev(Earthquake prev) {this.prev = prev;}

    //EQUAL FUNCTION
    public boolean equal(Earthquake e) {
        return Double.compare(magnitude, e.getMagnitude()) == 0 &&
                Double.compare(depth, e.getDepth()) == 0 && location.equals(e.getLocation()) &&
                date.equals(e.getDate()) && time.equals(e.getTime());
    }

    @Override
    public String toString() {
        return location + " " + date + " " + time + " Magnitude: " + magnitude + " Depth: " + depth + " km";
    }
}

////////////////////////////////////////////////////////////////
class EarthquakeLinkedList {

    private Earthquake head;
    private Earthquake tail;
    public EarthquakeLinkedList() {
        head = null;
        tail = null;
    }

    public void addLast(Earthquake e){
        if(head == null){
            head = e;
            tail = e;
            e.prev = null;
            e.next = null;
        } else {
            e.prev = tail;
            tail.next = e;
            tail = e;
        }
    }
    public void addFirst(Earthquake e){
        if(head == null){
            head = e;
            tail = e;
            e.prev = null;
            e.next = null;
        } else {
            e.next = head;
            head.prev = e;
            head = e;
        }
    }

    public void delete(Earthquake e) {
        if (e == head) {
            head = e.getNext();
            head.setPrev(null);
        } else if (e == tail) {
            tail = e.getPrev();
            tail.setNext(null);
        } else {
            Earthquake prev = e.getPrev();
            Earthquake next = e.getNext();
            prev.setNext(next);
            next.setPrev(prev);
        }
    }
    public void query(String q) {
        String[] get = q.split(" ", -1);
        Earthquake temp = head;

        // To make code simpler and more readable
        boolean isQuery = get[0].equals("Query");
        boolean isAdd = get[0].equals("Add");
        boolean isDelete = get[0].equals("Delete");
        boolean isMagnitudeQuery = isQuery && get[1].equals("magnitude");
        boolean isDepthQuery = isQuery && get[1].equals("depth");
        boolean isLocationQuery = isQuery && get[1].equals("location");
        boolean isDateQuery = isQuery && get[1].equals("date");
        boolean isAllQuery = isQuery && get[1].equals("all");

        //to prevent run time ArrayOutOfIndexBound error
        if (get.length < 3) {
            if(!isAllQuery){
                System.out.println("Invalid query: " + q);
            }
            else{
                try {
                    try (FileWriter writer = new FileWriter("output.txt", true)) {
                        displayList();
                        System.out.println("-");
                        writer.write("-\n");
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.getMessage();
                }

            }
        }
        else{
            boolean isGreaterThan = get[2].equals(">");
            boolean isLessThan = get[2].equals("<");
            boolean isEqualTo = get[2].equals("=");

            try {
                try (FileWriter writer = new FileWriter("output.txt", true)) {
                    while (!isQuery && !isAdd && !isDelete) {
                        writer.write("-\n");
                        System.out.println("-");

                    }

                    if (isQuery) {
                        if (isMagnitudeQuery) {
                            while (!isGreaterThan && !isLessThan && !isEqualTo) {
                                System.out.println("-");
                                writer.write("-\n");
                            }

                            while (temp != null) {
                                if (isGreaterThan && temp.getMagnitude() > Double.parseDouble(get[3])) {
                                    System.out.println(temp);
                                    writer.write(temp + "\n");
                                } else if (isLessThan && temp.getMagnitude() < Double.parseDouble(get[3])) {
                                    System.out.println(temp);
                                    writer.write(temp + "\n");
                                } else if (isEqualTo && temp.getMagnitude() == Double.parseDouble(get[3])) {
                                    System.out.println(temp);
                                    writer.write(temp + "\n");
                                }
                                temp = temp.next;
                            }
                            writer.write("-\n");
                            System.out.println("-");
                        } else if (isDepthQuery) {
                            while (temp != null) {
                                if (isGreaterThan && temp.getDepth() > Double.parseDouble(get[3])) {
                                    System.out.println(temp);
                                    writer.write(temp + "\n");
                                } else if (isLessThan && temp.getDepth() < Double.parseDouble(get[3])) {
                                    System.out.println(temp);
                                    writer.write(temp + "\n");
                                } else if (isEqualTo && temp.getDepth() == Double.parseDouble(get[3])) {
                                    System.out.println(temp);
                                    writer.write(temp + "\n");
                                }
                                temp = temp.next;
                            }
                            System.out.println("-");
                            writer.write("-\n");
                        } else if (isDateQuery) {
                            while (temp != null) {
                                if (get[3].equals(temp.getDate())) {
                                    System.out.println(temp);
                                    writer.write(temp + "\n");
                                }
                                temp = temp.next;
                            }
                            System.out.println("-");
                            writer.write("-\n");
                        } else if (isLocationQuery) {
                            while (temp != null) {
                                if (get[3].equals(temp.getLocation())) {
                                    System.out.println(temp);
                                    writer.write(temp + "\n");
                                }
                                temp = temp.next;
                            }
                            System.out.println("-");
                            writer.write("-\n");
                        }
                    } else if (isDelete) {
                        Earthquake tempEarthquake = new Earthquake(get[1], get[2], get[3], Double.parseDouble(get[5]), Double.parseDouble(get[7]));
                        while (temp != null) {
                            if (temp.equal(tempEarthquake)) {
                                delete(temp);
                            }
                            temp = temp.next;
                        }
                        displayList();
                        System.out.println("-");
                        writer.write("-\n");
                    } else if (isAdd) {
                        addFirst(new Earthquake(get[1], get[2], get[3], Double.parseDouble(get[5]), Double.parseDouble(get[7])));
                        displayList();
                        System.out.println("-");
                        writer.write("-\n");
                    }
                }
            }catch (IOException e) {
                e.getMessage();
            }
        }
    }
    public void displayList() {
        try {
            try (FileWriter writer = new FileWriter("output.txt", true)) {
                Earthquake cur = head;
                while (cur != null) {
                    System.out.println(cur);
                    writer.write(cur + "\n");
                    cur = cur.getNext();
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.getMessage();
        }

    }
}
public class Test {
    public static EarthquakeLinkedList eList = new EarthquakeLinkedList();
    public static void readInput(String fileName) {
        try (BufferedReader read = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = read.readLine()) != null) {
                String[] get = line.split(" ", -1);
                Earthquake newEarthquake = new Earthquake(get[0], get[1], get[2], Double.parseDouble(get[4]), Double.parseDouble(get[6]));
                eList.addLast(newEarthquake);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void readQueries(String fileName) {
        try {
            BufferedReader read =new BufferedReader(new FileReader(fileName));
            String line = read.readLine();
            while (line != null) {
                eList.query(line);
                line = read.readLine();
            }
            read.close();
        }
        catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {

        readInput("input.txt"); //When I tested it, I used src/input.txt
        eList.displayList();

        try {
            try (FileWriter writer = new FileWriter("output.txt", true)) {
                writer.write("-\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        System.out.println("-");
        readQueries("request.txt");
    }
}
