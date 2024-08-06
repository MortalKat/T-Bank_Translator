package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Component
public class ApplicationRunner implements CommandLineRunner {

    @Autowired
    private TranslationService translationService;

    Connection con;
    Statement stmt;

    @Override
    public void run(String... args) throws Exception {
        Scanner scan = new Scanner(System.in);
        String langFrom = scan.next();
        String langTo = scan.nextLine().replaceAll("→","").replaceAll(" ", "");
        String empty = scan.nextLine();
        String text = scan.nextLine();
        List<String> words = Arrays.asList(text.split(" "));

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<CompletableFuture<String>> futures = words.stream()
                .map(word -> translationService.translateAsync(langFrom, langTo, word)
                        .thenApplyAsync(result -> {
                            return result;
                        }, executorService))
                .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        List<String> translatedWords = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        String outputText = String.join(" ", translatedWords);
        System.out.println(outputText);
        LocalDateTime timeStamp = LocalDateTime.now();
        String ipAddress = InetAddress.getLocalHost().getHostAddress();

        UserInfo user = new UserInfo(ipAddress, text, outputText, timeStamp);

        saveInSqlite(user);

        executorService.shutdown();
    }

    public void saveInSqlite(UserInfo user) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:translation.db");
        stmt = con.createStatement();
        String sql = "INSERT INTO requests (ip, input, output)" +
                "VALUES ('"+user.getIpAddress()+"', '"+
                user.getInputLine()+"', '"+
                user.getOutputLine()+"')";
        System.out.println(sql);
        stmt.executeUpdate(sql);
        stmt.close();
        con.close();

    }
}