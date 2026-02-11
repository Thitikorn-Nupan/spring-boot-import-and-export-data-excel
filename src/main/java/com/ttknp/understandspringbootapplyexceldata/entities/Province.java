package com.ttknp.understandspringbootapplyexceldata.entities;

public class Province {

    private Integer no;
    private String nameEn;
    private String nameTh;
    private Long population;
    private Integer area;

    public Province(Integer no, String nameEn, String nameTh, Long population, Integer area) {
        this.no = no;
        this.nameEn = nameEn;
        this.nameTh = nameTh;
        this.population = population;
        this.area = area;
    }

    public Province() {
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameTh() {
        return nameTh;
    }

    public void setNameTh(String nameTh) {
        this.nameTh = nameTh;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Province{");
        sb.append("no=").append(no);
        sb.append(", nameEn='").append(nameEn).append('\'');
        sb.append(", nameTh='").append(nameTh).append('\'');
        sb.append(", population=").append(population);
        sb.append(", area=").append(area);
        sb.append('}');
        return sb.toString();
    }
}
