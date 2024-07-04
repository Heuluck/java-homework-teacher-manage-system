package Teacher;

import java.util.ArrayList;
import utils.CustomArrayList;//自定义的工具类

public class Teacher {
    /**
     * 教师号
     */
    private String id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String sex;
    /**
     * 职称
     */
    private String rank;
    /**
     * 任教课程
     */
    private ArrayList<String> lessons;
    /**
     * 班级
     */
    private ArrayList<String> classes;
    /**
     * 班级数目
     */
    private int numOfClasses;
    /**
     * 理论课时
     */
    private int theoryClassLength;
    /**
     * 实验课时
     */
    private int labClassLength;
    /**
     * 单个教学任务总课时
     */
    private double totalLength;

    public Teacher(String id, String name, String sex, String rank, String lessons,
                   String classes, int theoryClassLength, int labClassLength) {
        super();
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.rank = rank;
        /* 转换为ArrayList然后去重 */
        this.lessons = CustomArrayList.deduplication(CustomArrayList.toList(lessons.split(";")));
        this.classes = CustomArrayList.deduplication(CustomArrayList.toList(classes.split(";")));
        this.numOfClasses = this.classes.size();
        this.theoryClassLength = theoryClassLength;
        this.labClassLength = labClassLength;
        if (this.numOfClasses <= 2)
            this.totalLength = 1.5 * (theoryClassLength + labClassLength);
        else if (this.numOfClasses == 3)
            this.totalLength = 2 * (theoryClassLength + labClassLength);
        else this.totalLength = 2.5 * (theoryClassLength + labClassLength);
    }

    public String getId() {
        return id;
    }

    public ArrayList<String> getClasses() {
        return classes;
    }

    public ArrayList<String> getLessons() {
        return lessons;
    }

    public int getLabClassLength() {
        return labClassLength;
    }

    public int getNumOfClasses() {
        return numOfClasses;
    }

    public int getTheoryClassLength() {
        return theoryClassLength;
    }

    public String getRank() {
        return rank;
    }

    public String getSex() {
        return sex;
    }

    public String getName() {
        return name;
    }

    public double getTotalLength() {
        return totalLength;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setLabClassLength(int labClassLength) {
        this.labClassLength = labClassLength;
    }

    public void setTheoryClassLength(int theoryClassLength) {
        this.theoryClassLength = theoryClassLength;
    }

    public void addClasses(String classes){
        this.classes.addAll(CustomArrayList.toList(classes.split(";")));
        CustomArrayList.deduplication(this.classes);
    }

    public void addLessons(String lessons){
        this.lessons.addAll(CustomArrayList.toList(lessons.split(";")));
        CustomArrayList.deduplication(this.lessons);
    }

    public void deleteClass(String singleClass){
        this.classes.remove(singleClass);
    }

    public void deleteLesson(String singleLesson){
        this.lessons.remove(singleLesson);
    }
}
