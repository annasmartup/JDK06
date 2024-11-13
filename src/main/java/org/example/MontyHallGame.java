package org.example;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.random.RandomDataGenerator;

import java.util.HashMap;

@Getter
@Setter
public class MontyHallGame {

    private HashMap<Integer, String> gameResults = new HashMap<>();
    private RandomDataGenerator randomDataGenerator = new RandomDataGenerator();

    // Симуляция одного шага игры Монти Холла
    public String playGame(boolean switchChoice) {
        // Дверь 1 ведет к машине, двери 0 - к козам
        int[] doors = {0, 0, 0};  // 0 - коза, 1 - машина
        int carPosition = randomDataGenerator.nextInt(0, 2);
        doors[carPosition] = 1;  // устанавливаем машину на случайную дверь

        // Игрок выбирает дверь
        int playerChoice = randomDataGenerator.nextInt(0, 2);

        // Ведущий открывает дверь с козой, которая не была выбрана игроком
        int hostChoice = getHostChoice(doors, playerChoice);

        // Если игрок решает поменять выбор
        if (switchChoice) {
            // Игрок меняет свой выбор на оставшуюся дверь
            playerChoice = getRemainingChoice(playerChoice, hostChoice);
        }

        // Проверка, выиграл ли игрок
        return doors[playerChoice] == 1 ? "Win" : "Lose";
    }

    // Выбор ведущего
    private int getHostChoice(int[] doors, int playerChoice) {
        for (int i = 0; i < 3; i++) {
            // Ведущий выбирает дверь с козой, не ту, которую выбрал игрок
            if (i != playerChoice && doors[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    // Получение оставшегося выбора игрока
    private int getRemainingChoice(int playerChoice, int hostChoice) {
        // Игрок меняет выбор на оставшуюся дверь
        for (int i = 0; i < 3; i++) {
            if (i != playerChoice && i != hostChoice) {
                return i;
            }
        }
        return -1;
    }

    // Запуск игры 1000 шагов
    public void runSimulation() {
        int totalWinsSwitch = 0;
        int totalLossesSwitch = 0;
        int totalWinsNoSwitch = 0;
        int totalLossesNoSwitch = 0;

        for (int i = 1; i <= 1000; i++) {
            String resultSwitch = playGame(true);  // Игрок меняет выбор
            String resultNoSwitch = playGame(false);  // Игрок не меняет выбор

            gameResults.put(i, "Step " + i + ": Switch - " + resultSwitch + ", No Switch - " + resultNoSwitch);

            if ("Win".equals(resultSwitch)) {
                totalWinsSwitch++;
            } else {
                totalLossesSwitch++;
            }
            if ("Win".equals(resultNoSwitch)) {
                totalWinsNoSwitch++;
            } else {
                totalLossesNoSwitch++;
            }
        }

        // Статистика
        System.out.println("Результат 1000 игр:");
        System.out.println("Со сменой двери: Победы = " + totalWinsSwitch + ", Поражения = " + totalLossesSwitch);
        System.out.println("Без смены двери: Победы = " + totalWinsNoSwitch + ", Поражения = " + totalLossesNoSwitch);
    }

}