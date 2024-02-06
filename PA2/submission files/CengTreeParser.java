import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CengTreeParser
{
    public static ArrayList<CengBook> parseBooksFromFile(String filename)
    {
        ArrayList<CengBook> bookList = new ArrayList<CengBook>();

        // You need to parse the input file in order to use GUI tables.
        // TODO: Parse the input file, and convert them into CengBooks

        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line_to_read = scanner.nextLine();
                String[] tmp = line_to_read.split("\\|");
                Integer key = Integer.parseInt(tmp[0]);
                String bookTitle = tmp[1];
                String author = tmp[2];
                String genre = tmp[3];

                CengBook cengbook = new CengBook(key, bookTitle, author, genre);
                bookList.add(cengbook);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bookList;
    }

    public static void startParsingCommandLine() throws IOException
    {
        // TODO: Start listening and parsing command line -System.in-.
        // There are 4 commands:
        // 1) quit : End the app, gracefully. Print nothing, call nothing, just break off your command line loop.
        // 2) add : Parse and create the book, and call CengBookRunner.addBook(newlyCreatedBook).
        // 3) search : Parse the bookID, and call CengBookRunner.searchBook(bookID).
        // 4) print : Print the whole tree, call CengBookRunner.printTree().

        // Commands (quit, add, search, print) are case-insensitive.
        Scanner scanner = new Scanner(System.in);
        String input;
        String inp_command;

        while(true) {
            input = scanner.nextLine();
            String[] inp_array = input.split("\\|");
            inp_command = inp_array[0];

            if(inp_command.equalsIgnoreCase("quit"))
                return;

            if(inp_command.equalsIgnoreCase("add")){
                Integer key = Integer.parseInt(inp_array[1]);
                String videoTitle = inp_array[2];
                String channelName = inp_array[3];
                String category = inp_array[4];
                CengBook cengbook = new CengBook(key, videoTitle, channelName, category);

                CengBookRunner.addBook(cengbook);

            }
            else if(inp_command.equalsIgnoreCase("search")){
                Integer key = Integer.parseInt(inp_array[1]);
                CengBookRunner.searchBook(key);

            }
            else if(inp_command.equalsIgnoreCase("print")) {
                CengBookRunner.printTree();
            }

        }

    }

}
