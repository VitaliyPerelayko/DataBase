package task7;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Expense {
    private int num;
    private LocalDate paydate;
    private int reseiver;
    private BigDecimal value;

    public Expense() {
    }

    public Expense(LocalDate paydate, int reseiver, BigDecimal value) {
        this.paydate = paydate;
        this.reseiver = reseiver;
        this.value = value;
    }

    public int getNum() {
        return num;
    }

    public int getReseiver() {
        return reseiver;
    }

    public LocalDate getPaydate() {
        return paydate;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setReseiver(int reseiver) {
        this.reseiver = reseiver;
    }

    public void setPaydate(LocalDate paydate) {
        this.paydate = paydate;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "num=" + num +
                ", paydate=" + paydate +
                ", reseiver=" + reseiver +
                ", value=" + value +
                '}';
    }
}
