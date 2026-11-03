package se.yrgo.bookclub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.yrgo.bookclub.domain.Book;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {
    private List<Book> exampleBooks = List.of(
            new Book("1984", "George Orwell", "Dystopian"),
            new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy"),
            new Book("Harry Potter and the Philosopher's Stone", "J.K. Rowling", "Fantasy"),
            new Book("The Great Gatsby", "F. Scott Fitzgerald", "Classic"),
            new Book("Brave New World", "Aldous Huxley", "Dystopian"),
            new Book("The Lord of the Rings", "J.R.R. Tolkien", "Fantasy")
    );

    @GetMapping("/home")
    public String showHome(Model model) {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("d MMM uuuu"));
        model.addAttribute("date", date);
        model.addAttribute("helloMsg", "Hello! Today is: ");

        return "home";
    }

    @GetMapping("/books")
    public String showAllBooks(Model model) {
        model.addAttribute("books", exampleBooks);

        return "booklist";
    }

    @GetMapping("/genre")
    public String genreForm(Model model, @RequestParam(required = false) String type) {
        if (type == null || type.isEmpty()) {
            model.addAttribute("books", exampleBooks);
            return "genre";
        }

        List<Book> searchResult = getFilteredResult(type);
        if (searchResult.isEmpty()) {
            model.addAttribute("errorMessage", "No books found in that genre!");
            return "genre";
        }

        model.addAttribute("books", searchResult);
        return "genre";
    }

    private List<Book> getFilteredResult(String type) {
        List<Book> searchResult = new ArrayList<>();

        if (type == null || type.isEmpty()) {
            return searchResult;
        }

        searchResult = exampleBooks.stream()
                .filter(book -> book.getGenre().equalsIgnoreCase(type))
                .toList();
        return searchResult;
    }


}
