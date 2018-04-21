package hyperledger.hku.hk.model;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class Process {
    private String $class;
    private String application;
    private String professor;
    private String bank;

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("$class", this.$class);
        map.put("application", this.application);
        if (StringUtils.isNotEmpty(this.professor)) {
            map.put("professor", this.professor);
        }
        if (StringUtils.isNotEmpty(this.bank)) {
            map.put("bank", this.bank);
        }
        return map;
    }

    public String get$class() {
        return $class;
    }

    public void set$class(String $class) {
        this.$class = $class;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}
