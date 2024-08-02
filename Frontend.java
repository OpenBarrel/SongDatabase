import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Frontend implements FrontendInterface{

  private Scanner in;
  private BackendInterface backend;
  private String min = "min";
  private String max = "max";
  private String speed = "none";

  public Frontend(Scanner in, BackendInterface backend) {
      this.in = in;
      this.backend = backend;
  }
  
  @Override
  public void runCommandLoop() {
    boolean keepLooping=true;
    try {
      while(keepLooping) {
        displayMainMenu();
        String input=in.nextLine();
        if(input.equals("R")) {
          readFile();
        } else if(input.equals("G")) {
          getValues();
        } else if(input.equals("F")) {
          setFilter();
        } else if(input.equals("D")) {
          topFive();
        } else if(input.equals("Q")) {
          keepLooping=false;
        } else {
          System.out.println("Please enter the correct letter!");
        }
      }
    } catch(Exception e) {
      System.out.println("An error occurred");
    }
    
  }

  @Override
  public void displayMainMenu() {
    String menu = """
        
        ~~~ Command Menu ~~~
            [R]ead Data
            [G]et Songs by Energy [min - max]
            [F]ilter Fast Songs (by Min Speed: none)
            [D]isplay Five Most Danceable
            [Q]uit
        Choose command:""";
    menu=menu.replace("min",min).replace("max",max).replace("none",speed);
    System.out.print(menu + " ");
    
  }

  @Override
  public void readFile() {
    System.out.println("Please enter the name of the file: ");
    String name=in.nextLine();
    try {
      backend.readData(name);
      System.out.println(name);
      System.out.println("Done reading file.");
    } catch (IOException e) {
      System.out.println("IOExeption");
    }
  }

  @Override
  public void getValues() {
    System.out.println("Please enter energy range (MIN - MAX): ");
    try {
      String energy=in.nextLine();
      String[] list=energy.split("-");
      min=list[0].trim();
      max=list[1].trim();
      List<String> values = backend.getRange(Integer.parseInt(min),Integer.parseInt(max));
      System.out.println("5 songs found between "+ energy + ": ");
      int i=0;
      while(i<values.size()) {
        System.out.println(values.get(i));
        i++;
      }
    } catch(Exception e) {
      System.out.println("Invalid range");
    }
    
  }

  @Override
  public void setFilter() {
    System.out.println("Please enter min speed: ");
    try {
      speed=in.nextLine(); // changed this line
      List<String> values = backend.filterFastSongs(Integer.parseInt(speed));
      System.out.println("2 songs found between "+min+" - "+max+" at "+speed+" BPM or faster: ");
      int i=0;
      while(i<values.size()) {
        System.out.println(values.get(i));
        i++;
      }
    } catch(Exception e) {
      System.out.println("Invalid speed");
    }
  }

  @Override
  public void topFive() {
    try {
      List<String> values=backend.fiveMostDanceable();
      System.out.println("Top Five Most Danceable found between "+min+" = "+max+" at "+speed+" BMP or faster: ");
      int i=0;
      while(i<values.size()) {
        System.out.println(values.get(i));
        i++;
      }
    } catch(Exception e) {
      System.out.println("error");
    }
  }

}
