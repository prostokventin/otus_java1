import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.atm.ATM;
import ru.atm.Cash;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AtmTest {

    @Test
    @DisplayName("Проверяем добавление денег в банкомат")
    void depositCashTest() {
        //given
        ATM atm = new ATM();
        Cash cash = new Cash();
        cash.addFifty(2);
        cash.addFiveThousand(2);

        //when
        atm.depositCash(cash);

        //then
        assertThat(atm.getBalance()).isEqualTo(10100);
    }

    @Test
    @DisplayName("Проверяем вывод баланса")
    void displayBalanceTest() {
        //given
        ATM atm = new ATM();
        Cash cash = new Cash();
        cash.addFifty(1);
        cash.addOneHundred(1);
        cash.addFiveHundred(1);
        cash.addOneThousand(1);
        cash.addFiveThousand(1);

        //when
        atm.depositCash(cash);

        //then
        assertThat(atm.getBalance()).isEqualTo(50 + 100 + 500 + 1000 + 5000);
    }

    @Test
    @DisplayName("Проверяем успешную выдачу денег")
    void successWithdrawCashTest() {
        //given
        ATM atm = new ATM();
        Cash cash = new Cash();
        cash.addFifty(1);
        cash.addOneHundred(1);
        cash.addFiveHundred(1);
        cash.addOneThousand(1);
        cash.addFiveThousand(1);
        atm.depositCash(cash);

        //when
        Cash withdrawal1 = atm.withdrawCash(5100);;
        Cash withdrawal2 = atm.withdrawCash(550);

        //then
        assertThat(withdrawal1.getNumber()).isEqualTo(5100);
        assertThat(withdrawal2.getNumber()).isEqualTo(550);
    }

    @Test
    @DisplayName("Проверяем неудачную выдачу денег")
    void errorWithdrawCashTest() {
        //given
        ATM atm = new ATM();
        Cash cash = new Cash();
        cash.addFifty(1);
        cash.addOneHundred(1);
        cash.addFiveHundred(1);
        cash.addOneThousand(1);
        cash.addFiveThousand(1);
        atm.depositCash(cash);

        //when
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            atm.withdrawCash(850);
        });

    }
}
