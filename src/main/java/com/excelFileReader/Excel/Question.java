package com.excelFileReader.Excel;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "questions")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

        private String code;
        private String questionText;
        private String questionImagePath;
        private String answer1Text;
        private String answer1ImagePath;
        private String answer2Text;
        private String answer2ImagePath;
        private String answer3Text;
        private String answer3ImagePath;
        private String answer4Text;
        private String answer4ImagePath;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getQuestionText() {
            return questionText;
        }

        public void setQuestionText(String questionText) {
            this.questionText = questionText;
        }

        public String getQuestionImagePath() {
            return questionImagePath;
        }

        public void setQuestionImagePath(String questionImagePath) {
            this.questionImagePath = questionImagePath;
        }

        public String getAnswer1Text() {
            return answer1Text;
        }

        public void setAnswer1Text(String answer1Text) {
            this.answer1Text = answer1Text;
        }

        public String getAnswer1ImagePath() {
            return answer1ImagePath;
        }

        public void setAnswer1ImagePath(String answer1ImagePath) {
            this.answer1ImagePath = answer1ImagePath;
        }

        public String getAnswer2Text() {
            return answer2Text;
        }

        public void setAnswer2Text(String answer2Text) {
            this.answer2Text = answer2Text;
        }

        public String getAnswer2ImagePath() {
            return answer2ImagePath;
        }

        public void setAnswer2ImagePath(String answer2ImagePath) {
            this.answer2ImagePath = answer2ImagePath;
        }

        public String getAnswer3Text() {
            return answer3Text;
        }

        public void setAnswer3Text(String answer3Text) {
            this.answer3Text = answer3Text;
        }

        public String getAnswer3ImagePath() {
            return answer3ImagePath;
        }

        public void setAnswer3ImagePath(String answer3ImagePath) {
            this.answer3ImagePath = answer3ImagePath;
        }

        public String getAnswer4Text() {
            return answer4Text;
        }

        public void setAnswer4Text(String answer4Text) {
            this.answer4Text = answer4Text;
        }

        public String getAnswer4ImagePath() {
            return answer4ImagePath;
        }

        public void setAnswer4ImagePath(String answer4ImagePath) {
            this.answer4ImagePath = answer4ImagePath;
        }


}
