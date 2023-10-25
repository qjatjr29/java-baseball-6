package baseball.model;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class BallCount {

    private final int strike;
    private final int ball;

    private BallCount(final BaseBallNumber computerNumber, final BaseBallNumber inputNumber) {

        AtomicInteger strikeCount = new AtomicInteger(0);
        AtomicInteger ballCount = new AtomicInteger(0);

        calculateStrikeAndBall(computerNumber, inputNumber, strikeCount, ballCount);

        this.strike = strikeCount.get();
        this.ball = ballCount.get();
    }

    public static BallCount calculate(BaseBallNumber computerNumber, BaseBallNumber inputNumber) {
        return new BallCount(computerNumber, inputNumber);
    }

    public String getResultMessage() {
        StringBuilder returnMessage = new StringBuilder();
        if(!hasStrikeCount() && !hasBallCount()) return BallCountMessage.NOTHING.getMessage();
        if(hasBallCount()) returnMessage.append(String.format(BallCountMessage.BALL.getMessage(), ball));
        if(hasStrikeCount()) returnMessage.append(String.format(BallCountMessage.STRIKE.getMessage(), strike));
        returnMessage.append("\n");
        return returnMessage.toString();
    }

    private void calculateStrikeAndBall(BaseBallNumber computerNumber,
                                               BaseBallNumber inputNumber,
                                               AtomicInteger strike,
                                               AtomicInteger ball) {

        computerNumber.compare((computerNum, computerIndex) ->
                inputNumber.compare((inputNum, inputIndex) -> {
                    if(!Objects.equals(computerNum, inputNum)) return;
                    if(computerIndex.equals(inputIndex)) {
                        strike.getAndIncrement();
                    }
                    else {
                        ball.getAndIncrement();
                    }
                })
        );
    }

    private boolean hasStrikeCount() {
        return strike > 0;
    }

    private boolean hasBallCount() {
        return ball > 0;
    }
}
