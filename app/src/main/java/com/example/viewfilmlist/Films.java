package com.example.viewfilmlist;

public class Films {
    private String name;
    private String type;
    private String Rate;

    Films(String name, String type,String Rate){
        this.name = name;
        this.type = type;
        this.Rate = Rate;
    }

    // Работа с типом
    public void settype(String type) {
        this.type = type;
    }
    public String gettype() {
        return this.type;
    }

    // Работа с оценкой
    public void setRate(String Rate) {
        this.Rate = Rate;
    }
    public String getRate() {
        return this.Rate;
    }

    // Работа с названием
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
}
