package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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

        System.out.println(String.join(" ", translatedWords));

        executorService.shutdown();
    }
}