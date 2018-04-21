package hyperledger.hku.hk.model;

import java.util.HashMap;
import java.util.Map;

public class Apply {
    private String $class;
    private String loanValue;
    private String student;
    private String professor;

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("$class", this.$class);
        map.put("loanValue", this.loanValue);
        map.put("student", this.student);
        map.put("professor", this.professor);
        return map;
    }

    public String get$class() {
        return $class;
    }

    public void set$class(String $class) {
        this.$class = $class;
    }

    public String getLoanValue() {
        return loanValue;
    }

    public void setLoanValue(String loanValue) {
        this.loanValue = loanValue;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }
}
