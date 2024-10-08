package com.meow;
import java.io.IOException;
import java.util.Scanner;

/**
 * Class contains the Meow chatbot
 */
public class Meow {
    /**
     * enumerations for valid commands
     */
    public enum Command {
        TODO, DEADLINE, EVENT, LIST, DELETE, MARK, UNMARK, BYE, UPDATE
    }
    private Save save;
    private Ui ui;
    private TaskList tasks;
    private Parser parser;

    /**
     * Public constructor for meow chatbot
     * @throws IOException
     * @throws Meowception
     */
    public Meow() throws IOException, Meowception {
        tasks = new TaskList();
        ui = new Ui();
        try {
            save = new Save(tasks);
        } catch (Meowception e) {
            ui.showMeowceptionError(e.toString());
        } catch (IOException e) {
            ui.showMeowceptionError("Meowception 007: meow meow error creating file meow");
        }
        parser = new Parser(tasks);
    }

    /**
     * Runs the Meow chatbot
     * @throws Meowception
     */
    public void run() {
        parser = new Parser(tasks);
        ui.printStartMessage();
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while (!input.equals("bye")) {
            try {
                String msg = parser.validateCommand(input);
                ui.showTaskOutcomeMessage(msg);
            } catch (Meowception e) {
                ui.showMeowceptionError(e.toString());
            }
            input = sc.nextLine();
        }
        save.saveTasks(tasks);
        ui.printExitMessage();
        sc.close();
    }

    /*
     * Main method for Meow
     * @param String[] args
     * @return void
     */
    public static final void main(String[] args) throws Meowception, IOException {
        new Meow().run();
    }
    
    /**
     * Returns the response from the chatbot to GUI
     * @param input of user
     * @return String response from chatbot
     */
    public String getResponse(String input) {
        try {
            
            return parser.validateCommand(input);
        } catch (Meowception e) {
            return e.toString();
        }
    }

    /**
     * Returns the welcome message from the chatbot to GUI
     * @return String welcome message
     */
    public String getWelcomeMessage() {
        return ui.getStartingMessage();
    }
}
