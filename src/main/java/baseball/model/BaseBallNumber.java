package baseball.model;

import static baseball.config.BaseBallGameConfig.BASEBALL_MAX_NUMBER;
import static baseball.config.BaseBallGameConfig.BASEBALL_MIN_NUMBER;
import static baseball.config.BaseBallGameConfig.BASEBALL_NUMBER_SIZE;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class BaseBallNumber {
    private final int[] numbers;

    private BaseBallNumber(int[] numbers) {
        validateSize(numbers);
        validateWithinRange(numbers);
        this.numbers = numbers;
    }

    public static BaseBallNumber generateRandomNumbers() {
        List<Integer> randomNumbers = new ArrayList<>();

        while (randomNumbers.size() < BASEBALL_NUMBER_SIZE.getValue()) {
            int randomNumber = Randoms.pickNumberInRange(BASEBALL_MIN_NUMBER.getValue(),
                    BASEBALL_MAX_NUMBER.getValue());

            if (!randomNumbers.contains(randomNumber)) {
                randomNumbers.add(randomNumber);
            }
        }

        return new BaseBallNumber(
                randomNumbers.stream()
                        .mapToInt(Integer::intValue)
                        .toArray());
    }

    public static BaseBallNumber generateNumber(String inputString) {

        int[] numbers = inputString.chars()
                .map(Character::getNumericValue)
                .toArray();

        return new BaseBallNumber((numbers));
    }

    public void compare(BiConsumer<Integer,Integer> biConsumer){
        for(int i = 0; i < numbers.length; i++) {
            biConsumer.accept(numbers[i], i);
        }
    }

    public int[] getNumbers() {
        return numbers;
    }

    public int getBaseBallNumberCount() {
        return numbers.length;
    }

    // todo : 숫자야구게임을 위한 커스텀 예외 생성
    private void validateSize(int[] numbers) {
        if(numbers.length != BASEBALL_NUMBER_SIZE.getValue())
            throw new IllegalArgumentException();
    }

    private void validateWithinRange(int[] numbers) {
        for(int number : numbers) {
            if(number < BASEBALL_MIN_NUMBER.getValue() || number > BASEBALL_MAX_NUMBER.getValue())
                throw new IllegalArgumentException();
        }
    }

}
