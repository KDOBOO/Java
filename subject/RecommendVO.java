package subject;

import java.util.Calendar;

public class RecommendVO { //정보

	   private String name;
	   private String number;
	   private String pub;
	   private String writer;
	   private String genre;
	   private Calendar regDate;
	   
	   public String getName() {
	      return name;
	   }
	   public void setName(String name) {
	      this.name = name;
	   }
	   public String getNumber() {
	      return number;
	   }
	   public void setNumber(String number) {
	      this.number = number;
	   }
	   public String getPub() {
	      return pub;
	   }
	   public void setPub(String pub) {
	      this.pub = pub;
	   }
	   public String getWriter() {
	      return writer;
	   }
	   public void setWriter(String writer) {
	      this.writer = writer;
	   }
	   public Calendar getRegDate() {
	      return regDate;
	   }
	   public void setRegDate(Calendar regDate) {
	      this.regDate = regDate;
	   }
	   public String getGenre() {
	      return genre;
	   }
	   public void setGenre(String genre) {
	      this.genre = genre;
	   }

	}