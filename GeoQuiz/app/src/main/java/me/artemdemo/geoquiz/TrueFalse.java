package me.artemdemo.geoquiz;

public class TrueFalse {

    /*
     * Prefix 'm' is used for fields
     * Prefix 's' is used for static fields
     *
     * You can edit it here:
     * File -> Settings -> Code Style -> Java -> Code Generation
     *
     * In order to generate Getters & Setters use following command:
     * Code -> Generate
     * (Generator will create Getters & Setters without prefixes)
     */
    private int mQuestion;
    private boolean mTrueQuestion;

    public TrueFalse( int question, boolean TrueQuestion ) {
        mQuestion = question;
        mTrueQuestion = TrueQuestion;
    }

    public int getQuestion() {
        return mQuestion;
    }

    public void setQuestion(int question) {
        mQuestion = question;
    }

    public boolean isTrueQuestion() {
        return mTrueQuestion;
    }

    public void setTrueQuestion(boolean trueQuestion) {
        mTrueQuestion = trueQuestion;
    }
}
