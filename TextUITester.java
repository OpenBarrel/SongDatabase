import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class TextUITester {
//Below is the code that actually implements the redirection of System.in and System.out,
  // and you are welcome to ignore this code: focusing instead on how the constructor and
  // checkOutput() method is used int he example above.

  private PrintStream saveSystemOut; // store standard io references to restore after test
  private PrintStream saveSystemErr;
  private InputStream saveSystemIn;
  private ByteArrayOutputStream redirectedOut; // where output is written to durring the test
  private ByteArrayOutputStream redirectedErr;

  /**
   * Creates a new test object with the specified string of simulated user input text.
   * @param programInput the String of text that you want to simulate being typed in by the user.
   */
  public TextUITester(String programInput) {
      // backup standard io before redirecting for tests
      saveSystemOut = System.out;
      saveSystemErr = System.err;
      saveSystemIn = System.in;    
      // create alternative location to write output, and to read input from
      System.setOut(new PrintStream(redirectedOut = new ByteArrayOutputStream()));
      System.setErr(new PrintStream(redirectedErr = new ByteArrayOutputStream()));
      System.setIn(new ByteArrayInputStream(programInput.getBytes()));
  }

  /**
   * Call this method after running your test code, to check whether the expected
   * text was printed out to System.out and System.err.  Calling this method will 
   * also un-redirect standard io, so that the console can be used as normal again.
   * 
   * @return captured text that was printed to System.out and System.err durring test.
   */
  public String checkOutput() {
      try {
          String programOutput = redirectedOut.toString() + redirectedErr.toString();
          return programOutput;    
      } finally {
          // restore standard io to their pre-test states
          System.out.close();
          System.setOut(saveSystemOut);
          System.err.close();
          System.setErr(saveSystemErr);
          System.setIn(saveSystemIn);    
      }
  }
}
