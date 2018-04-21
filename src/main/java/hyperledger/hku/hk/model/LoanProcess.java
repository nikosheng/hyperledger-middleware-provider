package hyperledger.hku.hk.model;

import java.util.HashMap;
import java.util.Map;

public class LoanProcess {
    private String $class;
    private String loan;

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("$class", this.$class);
        map.put("loan", this.loan);
        return map;
    }

    public String get$class() {
        return $class;
    }

    public void set$class(String $class) {
        this.$class = $class;
    }

    public String getLoan() {
        return loan;
    }

    public void setLoan(String loan) {
        this.loan = loan;
    }
}
